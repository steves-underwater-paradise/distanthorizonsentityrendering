package io.github.steveplays28.distanthorizonsentityrendering.networking.packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public interface DHERPacket {
	@NotNull
	static Identifier getId() {
		return new Identifier("example", "packet");
	}

	@NotNull PacketByteBuf writeBuf();
}
