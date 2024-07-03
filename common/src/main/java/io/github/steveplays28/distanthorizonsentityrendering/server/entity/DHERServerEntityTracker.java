package io.github.steveplays28.distanthorizonsentityrendering.server.entity;

import dev.architectury.networking.NetworkManager;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity.DHERS2CEntityTickPacket;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity.DHERS2CEntityUnloadPacket;
import io.github.steveplays28.distanthorizonsentityrendering.server.event.world.entity.DHERServerWorldEntityEvent;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity.DHERS2CEntityLoadPacket;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

public class DHERServerEntityTracker {
	static {
		DHERServerWorldEntityEvent.ENTITY_LOAD.register(DHERServerEntityTracker::onEntityLoad);
		DHERServerWorldEntityEvent.ENTITY_UNLOAD.register(DHERServerEntityTracker::onEntityUnload);
		DHERServerWorldEntityEvent.ENTITY_TICK.register(DHERServerEntityTracker::onEntityTick);
	}

	private static void onEntityLoad(@NotNull ServerWorld serverWorld, @NotNull Entity entity) {
		NetworkManager.sendToPlayers(
				serverWorld.getPlayers(), DHERS2CEntityLoadPacket.getId(),
				new DHERS2CEntityLoadPacket(
						entity.getId(), entity.getPos().toVector3f(),
						(float) entity.getBoundingBox().getAverageSideLength()
				).writeBuf()
		);
	}

	private static void onEntityUnload(@NotNull ServerWorld serverWorld, @NotNull Entity entity) {
		NetworkManager.sendToPlayers(
				serverWorld.getPlayers(), DHERS2CEntityUnloadPacket.getId(), new DHERS2CEntityUnloadPacket(entity.getId()).writeBuf());
	}

	private static void onEntityTick(@NotNull ServerWorld serverWorld, @NotNull Entity entity) {
		NetworkManager.sendToPlayers(
				serverWorld.getPlayers(), DHERS2CEntityTickPacket.getId(),
				new DHERS2CEntityTickPacket(entity.getId(), entity.getPos().toVector3f()).writeBuf()
		);
	}
}
