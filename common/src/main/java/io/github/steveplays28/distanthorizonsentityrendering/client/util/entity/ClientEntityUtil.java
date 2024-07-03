package io.github.steveplays28.distanthorizonsentityrendering.client.util.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ClientEntityUtil {
	public static boolean isEntityClientPlayerOrCamera(@NotNull Entity entity) {
		@Nullable var clientCamera = MinecraftClient.getInstance().cameraEntity;
		if (clientCamera != null) {
			return entity.getId() == clientCamera.getId();
		}

		@Nullable var clientPlayer = MinecraftClient.getInstance().player;
		if (clientPlayer != null) {
			return entity.getId() == clientPlayer.getId();
		}

		return false;
	}
}
