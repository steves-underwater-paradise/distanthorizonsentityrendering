package io.github.steveplays28.lodentityrendering.client.event.world.entity;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface ClientWorldEntityEvent {
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
		void onLoad(@NotNull ClientWorld clientWorld, @NotNull Entity entity);
	}

	@FunctionalInterface
	interface EntityUnload {
		/**
		 * Invoked when an entity unloads.
		 *
		 * @param entity The {@link Entity} that stopped being rendered.
		 */
		void onUnload(@NotNull ClientWorld clientWorld, @NotNull Entity entity);
	}

	@FunctionalInterface
	interface EntityTick {
		/**
		 * Invoked before an entity ticks.
		 *
		 * @param entity The {@link Entity} that will tick.
		 */
		void onTick(@NotNull ClientWorld clientWorld, @NotNull Entity entity);
	}
}
