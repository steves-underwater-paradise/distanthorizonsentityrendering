package io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity;

import io.github.steveplays28.distanthorizonsentityrendering.DistantHorizonsEntityRendering;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.DHERPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class DHERS2CEntityUnloadPacket implements DHERPacket {
	private static final @NotNull Identifier id = new Identifier(DistantHorizonsEntityRendering.MOD_NAMESPACE, "entity_unload_packet");

	private final int entityId;

	public DHERS2CEntityUnloadPacket(int entityId) {
		this.entityId = entityId;
	}

	public DHERS2CEntityUnloadPacket(@NotNull PacketByteBuf buf) {
		this.entityId = buf.readInt();
	}

	public static @NotNull Identifier getId() {
		return id;
	}

	@Override
	public @NotNull PacketByteBuf writeBuf() {
		var buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeInt(entityId);
		return buf;
	}

	public int getEntityId() {
		return entityId;
	}
}
