package io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity;

import io.github.steveplays28.distanthorizonsentityrendering.DistantHorizonsEntityRendering;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.DHERPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class DHERS2CEntityLoadPacket implements DHERPacket {
	private static final @NotNull Identifier id = new Identifier(DistantHorizonsEntityRendering.MOD_NAMESPACE, "entity_load_packet");

	private final int entityId;
	private final @NotNull Vector3f entityPosition;
	private final float entityBoundingBoxAverageSideLength;

	public DHERS2CEntityLoadPacket(int entityId, @NotNull Vector3f entityPosition, float entityBoundingBoxAverageSideLength) {
		this.entityId = entityId;
		this.entityPosition = entityPosition;
		this.entityBoundingBoxAverageSideLength = entityBoundingBoxAverageSideLength;
	}

	public DHERS2CEntityLoadPacket(@NotNull PacketByteBuf buf) {
		this.entityId = buf.readInt();
		this.entityPosition = buf.readVector3f();
		this.entityBoundingBoxAverageSideLength = buf.readFloat();
	}

	public static @NotNull Identifier getId() {
		return id;
	}

	@Override
	public @NotNull PacketByteBuf writeBuf() {
		var buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeInt(entityId);
		buf.writeVector3f(entityPosition);
		buf.writeFloat(entityBoundingBoxAverageSideLength);
		return buf;
	}

	public int getEntityId() {
		return entityId;
	}

	public @NotNull Vector3f getEntityPosition() {
		return entityPosition;
	}

	public float getEntityBoundingBoxAverageSideLength() {
		return entityBoundingBoxAverageSideLength;
	}
}
