package io.github.steveplays28.lodentityrendering.networking.packet.s2c.world.entity;

import io.github.steveplays28.lodentityrendering.LODEntityRendering;
import io.github.steveplays28.lodentityrendering.networking.packet.LODEntityRenderingPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class LODEntityRenderingS2CEntityUnloadPacket implements LODEntityRenderingPacket {
	private static final @NotNull Identifier id = new Identifier(LODEntityRendering.MOD_ID, "entity_unload_packet");

	private final int entityId;

	public LODEntityRenderingS2CEntityUnloadPacket(int entityId) {
		this.entityId = entityId;
	}

	public LODEntityRenderingS2CEntityUnloadPacket(@NotNull PacketByteBuf buf) {
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
