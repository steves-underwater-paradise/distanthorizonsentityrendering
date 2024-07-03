package io.github.steveplays28.distanthorizonsentityrendering.client.compat.distanthorizons.rendering.entity;

import com.seibel.distanthorizons.api.DhApi;
import com.seibel.distanthorizons.api.interfaces.render.IDhApiRenderableBoxGroup;
import com.seibel.distanthorizons.api.objects.math.DhApiVec3f;
import com.seibel.distanthorizons.api.objects.render.DhApiRenderableBox;
import io.github.steveplays28.distanthorizonsentityrendering.client.event.world.entity.DHERClientWorldEntityEvent;
import io.github.steveplays28.distanthorizonsentityrendering.client.util.entity.ClientEntityUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DHERClientEntityRenderableBoxGroupTracker {
	/**
	 * Stores {@link Entity} IDs->{@link IDhApiRenderableBoxGroup}s.
	 */
	private static final @NotNull Map<Integer, IDhApiRenderableBoxGroup> RENDERABLE_BOX_GROUPS = new HashMap<>();

	public static void initialize() {
		DHERClientWorldEntityEvent.ENTITY_LOAD.register(DHERClientEntityRenderableBoxGroupTracker::onEntityLoad);
		DHERClientWorldEntityEvent.ENTITY_UNLOAD.register(DHERClientEntityRenderableBoxGroupTracker::onEntityUnload);
		DHERClientWorldEntityEvent.ENTITY_TICK.register(DHERClientEntityRenderableBoxGroupTracker::onEntityTick);
	}

	private static void onEntityLoad(@NotNull ClientWorld clientWorld, @NotNull Entity entity) {
		if (ClientEntityUtil.isEntityClientPlayerOrCamera(entity)) {
			return;
		}

		@NotNull var entityPosition = entity.getPos().toVector3f();
		@NotNull var renderableBoxGroup = DhApi.Delayed.renderRegister.createForSingleBox(
				new DhApiRenderableBox(new DhApiVec3f(), (float) entity.getBoundingBox().getAverageSideLength(), Color.CYAN));
		renderableBoxGroup.setOriginBlockPos(new DhApiVec3f(entityPosition.x(), entityPosition.y(), entityPosition.z()));
		RENDERABLE_BOX_GROUPS.put(entity.getId(), renderableBoxGroup);
		DhApi.Delayed.renderRegister.add(renderableBoxGroup);
	}

	private static void onEntityUnload(@NotNull ClientWorld clientWorld, @NotNull Entity entity) {
		if (ClientEntityUtil.isEntityClientPlayerOrCamera(entity)) {
			return;
		}

		var entityId = entity.getId();
		DhApi.Delayed.renderRegister.remove(RENDERABLE_BOX_GROUPS.get(entityId).getId());
		RENDERABLE_BOX_GROUPS.remove(entityId);
	}

	private static void onEntityTick(@NotNull ClientWorld clientWorld, @NotNull Entity entity) {
		if (ClientEntityUtil.isEntityClientPlayerOrCamera(entity)) {
			return;
		}

		@Nullable var renderableBoxGroup = RENDERABLE_BOX_GROUPS.get(entity.getId());
		if (renderableBoxGroup == null) {
			return;
		}

		@NotNull var entityPosition = entity.getPos().toVector3f();
		renderableBoxGroup.setOriginBlockPos(new DhApiVec3f(entityPosition.x(), entityPosition.y(), entityPosition.z()));
	}
}
