package io.github.steveplays28.distanthorizonsentityrendering.client.compat.distanthorizons.rendering.entity;

import com.seibel.distanthorizons.api.DhApi;
import com.seibel.distanthorizons.api.interfaces.render.IDhApiRenderableBoxGroup;
import com.seibel.distanthorizons.api.objects.math.DhApiVec3f;
import com.seibel.distanthorizons.api.objects.render.DhApiRenderableBox;
import dev.architectury.networking.NetworkManager;
import io.github.steveplays28.distanthorizonsentityrendering.client.entity.color.EntityAverageColorRegistry;
import io.github.steveplays28.distanthorizonsentityrendering.client.event.world.entity.DHERClientWorldEntityEvent;
import io.github.steveplays28.distanthorizonsentityrendering.client.util.entity.ClientEntityUtil;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity.DHERS2CEntityLoadPacket;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity.DHERS2CEntityTickPacket;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity.DHERS2CEntityUnloadPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class DHERClientEntityRenderableBoxGroupTracker {
	/**
	 * Stores {@link Entity} IDs->{@link IDhApiRenderableBoxGroup}s.
	 */
	private static final @NotNull Map<Integer, IDhApiRenderableBoxGroup> RENDERABLE_BOX_GROUPS = new HashMap<>();

	public static void initialize() {
		DHERClientWorldEntityEvent.ENTITY_LOAD.register(DHERClientEntityRenderableBoxGroupTracker::onClientWorldEntityLoad);
		DHERClientWorldEntityEvent.ENTITY_UNLOAD.register(DHERClientEntityRenderableBoxGroupTracker::onClientWorldEntityUnload);
		DHERClientWorldEntityEvent.ENTITY_TICK.register(DHERClientEntityRenderableBoxGroupTracker::onClientWorldEntityTick);
		NetworkManager.registerReceiver(NetworkManager.Side.S2C, DHERS2CEntityLoadPacket.getId(), (buf, context) -> {
			var entityLoadPacket = new DHERS2CEntityLoadPacket(buf);
			startTrackingEntity(
					entityLoadPacket.getEntityId(), entityLoadPacket.getEntityTextureId(), entityLoadPacket.getEntityPosition(),
					entityLoadPacket.getEntityBoundingBoxMin(), entityLoadPacket.getEntityBoundingBoxMax()
			);
		});
		NetworkManager.registerReceiver(
				NetworkManager.Side.S2C, DHERS2CEntityUnloadPacket.getId(),
				(buf, context) -> stopTrackingEntity(new DHERS2CEntityUnloadPacket(buf).getEntityId())
		);
		NetworkManager.registerReceiver(NetworkManager.Side.S2C, DHERS2CEntityTickPacket.getId(), (buf, context) -> {
			var entityTickPacket = new DHERS2CEntityTickPacket(buf);
			updateTrackingEntityPosition(entityTickPacket.getEntityId(), entityTickPacket.getEntityPosition());
		});
	}

	private static void onClientWorldEntityLoad(@NotNull ClientWorld clientWorld, @NotNull Entity entity) {
		if (ClientEntityUtil.isEntityClientPlayerOrCamera(entity)) {
			return;
		}

		@NotNull final var entityPosition = entity.getPos().toVector3f();
		final var entityPositionX = entityPosition.x();
		final var entityPositionY = entityPosition.y();
		final var entityPositionZ = entityPosition.z();
		@NotNull final var entityBoundingBox = entity.getBoundingBox();
//		startTrackingEntity(
//				entity.getId(), entity.getPos().toVector3f(),
//				new Vector3f(
//						(float) entityBoundingBox.minX - entityPositionX,
//						(float) entityBoundingBox.minY - entityPositionY,
//						(float) entityBoundingBox.minZ - entityPositionZ
//				),
//				new Vector3f(
//						(float) entityBoundingBox.maxX - entityPositionX,
//						(float) entityBoundingBox.maxY - entityPositionY,
//						(float) entityBoundingBox.maxZ - entityPositionZ
//				)
//		);
	}

	private static void onClientWorldEntityUnload(@NotNull ClientWorld clientWorld, @NotNull Entity entity) {
		if (ClientEntityUtil.isEntityClientPlayerOrCamera(entity)) {
			return;
		}

//		stopTrackingEntity(entity.getId());
	}

	private static void onClientWorldEntityTick(@NotNull ClientWorld clientWorld, @NotNull Entity entity) {
		if (ClientEntityUtil.isEntityClientPlayerOrCamera(entity)) {
			return;
		}

//		updateTrackingEntityPosition(entity.getId(), entity.getPos().toVector3f());
	}

	private static void startTrackingEntity(int entityId, @NotNull Identifier entityTextureIdentifier, @NotNull Vector3f entityPosition, @NotNull Vector3f entityBoundingBoxMin, @NotNull Vector3f entityBoundingBoxMax) {
		@Nullable var entityAverageTextureColor = EntityAverageColorRegistry.ENTITY_AVERAGE_COLOR_REGISTRY.get(entityTextureIdentifier);
		if (entityAverageTextureColor == null) {
			entityAverageTextureColor = Color.BLACK;
		}

		@NotNull final var renderableBoxGroup = DhApi.Delayed.customRenderObjectFactory.createForSingleBox(
				new DhApiRenderableBox(
						new DhApiVec3f(entityBoundingBoxMin.x(), entityBoundingBoxMin.y(), entityBoundingBoxMin.z()),
						new DhApiVec3f(entityBoundingBoxMax.x(), entityBoundingBoxMax.y(), entityBoundingBoxMax.z()),
						entityAverageTextureColor
				)
		);
		renderableBoxGroup.setOriginBlockPos(new DhApiVec3f(entityPosition.x(), entityPosition.y(), entityPosition.z()));
		RENDERABLE_BOX_GROUPS.put(entityId, renderableBoxGroup);

		@Nullable final var renderRegister = DhApi.Delayed.worldProxy.getSinglePlayerLevel().getRenderRegister();
		if (renderRegister == null) {
			return;
		}

		renderRegister.add(renderableBoxGroup);
	}

	private static void stopTrackingEntity(int entityId) {
		@Nullable final var renderableBoxGroup = RENDERABLE_BOX_GROUPS.get(entityId);
		if (renderableBoxGroup == null) {
			return;
		}

		@Nullable final var renderRegister = DhApi.Delayed.worldProxy.getSinglePlayerLevel().getRenderRegister();
		if (renderRegister == null) {
			return;
		}

		renderRegister.remove(renderableBoxGroup.getId());
		RENDERABLE_BOX_GROUPS.remove(entityId);
	}

	private static void updateTrackingEntityPosition(int entityId, @NotNull Vector3f entityPosition) {
		@Nullable final var renderableBoxGroup = RENDERABLE_BOX_GROUPS.get(entityId);
		if (renderableBoxGroup == null) {
			return;
		}

		RENDERABLE_BOX_GROUPS.get(entityId).setOriginBlockPos(new DhApiVec3f(entityPosition.x(), entityPosition.y(), entityPosition.z()));
	}
}
