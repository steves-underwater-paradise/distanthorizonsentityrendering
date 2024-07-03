package io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity;

import io.github.steveplays28.distanthorizonsentityrendering.DistantHorizonsEntityRendering;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.DHERPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class DHERS2CEntityTickPacket implements DHERPacket {
	private static final @NotNull Identifier id = new Identifier(DistantHorizonsEntityRendering.MOD_NAMESPACE, "entity_tick_packet");

	private final int entityId;
	private final @NotNull Vector3f entityPosition;

	public DHERS2CEntityTickPacket(int entityId, @NotNull Vector3f entityPosition) {
		this.entityId = entityId;
		this.entityPosition = entityPosition;
	}

	public DHERS2CEntityTickPacket(@NotNull PacketByteBuf buf) {
		this.entityId = buf.readInt();
		this.entityPosition = buf.readVector3f();
	}

	public static @NotNull Identifier getId() {
		return id;
	}

	@Override
	public @NotNull PacketByteBuf writeBuf() {
		var buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeInt(entityId);
		buf.writeVector3f(entityPosition);
		return buf;
	}

	public int getEntityId() {
		return entityId;
	}

	public @NotNull Vector3f getEntityPosition() {
		return entityPosition;
	}
}
