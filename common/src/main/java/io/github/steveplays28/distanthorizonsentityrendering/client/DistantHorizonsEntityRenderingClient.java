package io.github.steveplays28.distanthorizonsentityrendering.client;

import dev.architectury.registry.ReloadListenerRegistry;
import io.github.steveplays28.distanthorizonsentityrendering.client.compat.distanthorizons.rendering.entity.DHERClientEntityRenderableBoxGroupTracker;
import io.github.steveplays28.distanthorizonsentityrendering.client.resource.DHERResourceReloader;
import net.minecraft.resource.ResourceType;
import org.jetbrains.annotations.NotNull;

public class DistantHorizonsEntityRenderingClient {
	public static final @NotNull String BUILT_IN_RESOURCE_PACK_NAME = "default";

	public static void initialize() {
		ReloadListenerRegistry.register(ResourceType.CLIENT_RESOURCES, new DHERResourceReloader());
		DHERClientEntityRenderableBoxGroupTracker.initialize();
	}
}
