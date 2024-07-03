package io.github.steveplays28.distanthorizonsentityrendering.client.compat.distanthorizons.rendering.entity;

import com.seibel.distanthorizons.api.DhApi;
import com.seibel.distanthorizons.api.interfaces.render.IDhApiRenderableBoxGroup;
import com.seibel.distanthorizons.api.objects.math.DhApiVec3f;
import com.seibel.distanthorizons.api.objects.render.DhApiRenderableBox;
import dev.architectury.networking.NetworkManager;
import io.github.steveplays28.distanthorizonsentityrendering.client.event.world.entity.DHERClientWorldEntityEvent;
import io.github.steveplays28.distanthorizonsentityrendering.client.util.entity.ClientEntityUtil;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity.DHERS2CEntityLoadPacket;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity.DHERS2CEntityTickPacket;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity.DHERS2CEntityUnloadPacket;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
					entityLoadPacket.getEntityId(), entityLoadPacket.getEntityPosition(),
					entityLoadPacket.getEntityBoundingBoxAverageSideLength()
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

//		startTrackingEntity(entity.getId(), entity.getPos().toVector3f(), (float) entity.getBoundingBox().getAverageSideLength());
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

	private static void startTrackingEntity(int entityId, @NotNull Vector3f entityPosition, float entityBoundingBoxAverageSideLength) {
		@NotNull var renderableBoxGroup = DhApi.Delayed.renderRegister.createForSingleBox(
				new DhApiRenderableBox(new DhApiVec3f(), entityBoundingBoxAverageSideLength, Color.CYAN));
		renderableBoxGroup.setOriginBlockPos(new DhApiVec3f(entityPosition.x(), entityPosition.y(), entityPosition.z()));
		RENDERABLE_BOX_GROUPS.put(entityId, renderableBoxGroup);
		DhApi.Delayed.renderRegister.add(renderableBoxGroup);
	}

	private static void stopTrackingEntity(int entityId) {
		@Nullable var renderableBoxGroup = RENDERABLE_BOX_GROUPS.get(entityId);
		if (renderableBoxGroup == null) {
			return;
		}

		// TODO: Fix a crash in DH's renderer when removing a renderable box group during an upload
//		DhApi.Delayed.renderRegister.remove(renderableBoxGroup.getId());
		RENDERABLE_BOX_GROUPS.remove(entityId);
	}

	private static void updateTrackingEntityPosition(int entityId, @NotNull Vector3f entityPosition) {
		@Nullable var renderableBoxGroup = RENDERABLE_BOX_GROUPS.get(entityId);
		if (renderableBoxGroup == null) {
			return;
		}

		RENDERABLE_BOX_GROUPS.get(entityId).setOriginBlockPos(new DhApiVec3f(entityPosition.x(), entityPosition.y(), entityPosition.z()));
	}
}
