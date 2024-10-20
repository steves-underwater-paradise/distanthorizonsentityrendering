package io.github.steveplays28.distanthorizonsentityrendering.networking.packet.s2c.world.entity;

import io.github.steveplays28.distanthorizonsentityrendering.DistantHorizonsEntityRendering;
import io.github.steveplays28.distanthorizonsentityrendering.networking.packet.DHERPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class DHERS2CEntityLoadPacket implements DHERPacket {
	private static final @NotNull Identifier id = new Identifier(DistantHorizonsEntityRendering.MOD_ID, "entity_load_packet");

	private final int entityId;
	private final @NotNull Identifier entityTextureId;
	private final @NotNull Vector3f entityPosition;
	private final @NotNull Vector3f entityBoundingBoxMin;
	private final @NotNull Vector3f entityBoundingBoxMax;

	public DHERS2CEntityLoadPacket(int entityId, @NotNull Identifier entityTextureId, @NotNull Vector3f entityPosition, @NotNull Vector3f entityBoundingBoxMin, @NotNull Vector3f entityBoundingBoxMax) {
		this.entityId = entityId;
		this.entityTextureId = entityTextureId;
		this.entityPosition = entityPosition;
		this.entityBoundingBoxMin = entityBoundingBoxMin;
		this.entityBoundingBoxMax = entityBoundingBoxMax;
	}

	public DHERS2CEntityLoadPacket(@NotNull PacketByteBuf buf) {
		this.entityId = buf.readInt();
		this.entityTextureId = buf.readIdentifier();
		this.entityPosition = buf.readVector3f();
		this.entityBoundingBoxMin = buf.readVector3f();
		this.entityBoundingBoxMax = buf.readVector3f();
	}

	public static @NotNull Identifier getId() {
		return id;
	}

	@Override
	public @NotNull PacketByteBuf writeBuf() {
		var buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeInt(entityId);
		buf.writeIdentifier(entityTextureId);
		buf.writeVector3f(entityPosition);
		buf.writeVector3f(entityBoundingBoxMin);
		buf.writeVector3f(entityBoundingBoxMax);
		return buf;
	}

	public int getEntityId() {
		return entityId;
	}

	public @NotNull Identifier getEntityTextureId() {
		return entityTextureId;
	}

	public @NotNull Vector3f getEntityPosition() {
		return entityPosition;
	}

	public @NotNull Vector3f getEntityBoundingBoxMin() {
		return entityBoundingBoxMin;
	}

	public @NotNull Vector3f getEntityBoundingBoxMax() {
		return entityBoundingBoxMax;
	}
}
