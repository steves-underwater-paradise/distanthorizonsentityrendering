package io.github.steveplays28.distanthorizonsentityrendering.server.event.world.entity;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

public interface DHERServerWorldEntityEvent {
	/**
	 * @see EntityLoad
	 */
	Event<EntityLoad> ENTITY_LOAD = EventFactory.createLoop();
	/**
	 * @see EntityUnload
	 */
	Event<EntityUnload> ENTITY_UNLOAD = EventFactory.createLoop();
	/**
	 * @see EntityTick
	 */
	Event<EntityTick> ENTITY_TICK = EventFactory.createLoop();

	@FunctionalInterface
	interface EntityLoad {
		/**
		 * Invoked when an entity loads.
		 *
		 * @param entity The {@link Entity} that started being rendered.
		 */
		void onLoad(@NotNull ServerWorld serverWorld, @NotNull Entity entity);
	}

	@FunctionalInterface
	interface EntityUnload {
		/**
		 * Invoked when an entity unloads.
		 *
		 * @param entity The {@link Entity} that stopped being rendered.
		 */
		void onUnload(@NotNull ServerWorld serverWorld, @NotNull Entity entity);
	}

	@FunctionalInterface
	interface EntityTick {
		/**
		 * Invoked before an entity ticks.
		 *
		 * @param entity The {@link Entity} that will tick.
		 */
		void onTick(@NotNull ServerWorld serverWorld, @NotNull Entity entity);
	}
}
