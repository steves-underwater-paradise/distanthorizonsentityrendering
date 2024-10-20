package io.github.steveplays28.lodentityrendering.networking.packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public interface LODEntityRenderingPacket {
	@SuppressWarnings("unused")
	@NotNull
	static Identifier getId() {
		return new Identifier("example", "packet");
	}

	@NotNull PacketByteBuf writeBuf();
}
