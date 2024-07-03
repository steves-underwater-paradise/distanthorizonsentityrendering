package io.github.steveplays28.distanthorizonsentityrendering.mixin.server.world;

import io.github.steveplays28.distanthorizonsentityrendering.server.event.world.entity.DHERServerWorldEntityEvent;
import net.minecraft.entity.Entity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {
	protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
		super(
				properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess,
				maxChainedNeighborUpdates
		);
	}

	/**
	 * Called before vanilla has started ticking the entity.
	 *
	 * @param entity The {@link Entity} that will be tracked.
	 * @param ci     The {@link CallbackInfo} for this {@link Inject}.
	 */
	@Inject(method = "tickEntity", at = @At(value = "HEAD"))
	private void distanthorizonsentityrendering$invokeEntityTickEvent(@NotNull Entity entity, @NotNull CallbackInfo ci) {
		DHERServerWorldEntityEvent.ENTITY_TICK.invoker().onTick((ServerWorld) (Object) this, entity);
	}

	@Mixin(targets = "net/minecraft/server/world/ServerWorld$ServerEntityHandler")
	public static abstract class ServerEntityHandlerMixin {
		@Shadow
		@Final
		ServerWorld field_26936;

		/**
		 * Called before vanilla has started tracking the entity.
		 *
		 * @param entity The {@link Entity} that will be tracked.
		 * @param ci     The {@link CallbackInfo} for this {@link Inject}.
		 */
		@Inject(method = "startTracking(Lnet/minecraft/entity/Entity;)V", at = @At(value = "HEAD"))
		private void distanthorizonsentityrendering$invokeEntityLoadEvent(@NotNull Entity entity, @NotNull CallbackInfo ci) {
			DHERServerWorldEntityEvent.ENTITY_LOAD.invoker().onLoad(this.field_26936, entity);
		}

		/**
		 * Called before vanilla has stopped tracking the entity.
		 *
		 * @param entity The {@link Entity} that will no longer be tracked.
		 * @param ci     The {@link CallbackInfo} for this {@link Inject}.
		 */
		@Inject(method = "stopTracking(Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"))
		private void distanthorizonsentityrendering$invokeEntityUnloadEvent(@NotNull Entity entity, @NotNull CallbackInfo ci) {
			DHERServerWorldEntityEvent.ENTITY_UNLOAD.invoker().onUnload(this.field_26936, entity);
		}
	}
}
