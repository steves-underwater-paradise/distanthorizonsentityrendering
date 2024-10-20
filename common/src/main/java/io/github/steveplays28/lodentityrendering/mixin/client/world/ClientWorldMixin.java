package io.github.steveplays28.lodentityrendering.mixin.client.world;

import io.github.steveplays28.lodentityrendering.client.event.world.entity.ClientWorldEntityEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
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

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.class)
abstract class ClientWorldMixin extends World {
	protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
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
	private void lodentityrendering$invokeEntityTickEvent(@NotNull Entity entity, @NotNull CallbackInfo ci) {
		ClientWorldEntityEvent.ENTITY_TICK.invoker().onTick((ClientWorld) (Object) this, entity);
	}

	@Environment(EnvType.CLIENT)
	@Mixin(targets = "net/minecraft/client/world/ClientWorld$ClientEntityHandler")
	abstract static class ClientWorldEntityHandlerMixin {
		@Shadow
		@Final
		ClientWorld field_27735;

		/**
		 * Called before vanilla has started tracking the entity.
		 *
		 * @param entity The {@link Entity} that will be tracked.
		 * @param ci     The {@link CallbackInfo} for this {@link Inject}.
		 */
		@Inject(method = "startTracking(Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
		private void lodentityrendering$invokeEntityLoadEvent(@NotNull Entity entity, @NotNull CallbackInfo ci) {
			ClientWorldEntityEvent.ENTITY_LOAD.invoker().onLoad(this.field_27735, entity);
		}

		/**
		 * Called before vanilla has stopped tracking the entity.
		 *
		 * @param entity The {@link Entity} that will no longer be tracked.
		 * @param ci     The {@link CallbackInfo} for this {@link Inject}.
		 */
		@Inject(method = "stopTracking(Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"))
		private void lodentityrendering$invokeEntityUnloadEvent(@NotNull Entity entity, @NotNull CallbackInfo ci) {
			ClientWorldEntityEvent.ENTITY_UNLOAD.invoker().onUnload(this.field_27735, entity);
		}
	}
}
