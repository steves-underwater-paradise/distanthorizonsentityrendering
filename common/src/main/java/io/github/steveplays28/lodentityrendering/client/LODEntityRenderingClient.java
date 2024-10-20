package io.github.steveplays28.lodentityrendering.client;

import dev.architectury.registry.ReloadListenerRegistry;
import io.github.steveplays28.lodentityrendering.client.compat.distanthorizons.rendering.entity.ClientEntityRenderableBoxGroupTracker;
import io.github.steveplays28.lodentityrendering.client.resource.LODEntityRenderingResourceReloader;
import net.minecraft.resource.ResourceType;
import org.jetbrains.annotations.NotNull;

public class LODEntityRenderingClient {
	public static final @NotNull String BUILT_IN_RESOURCE_PACK_ID = "default";

	public static void initialize() {
		ReloadListenerRegistry.register(ResourceType.CLIENT_RESOURCES, new LODEntityRenderingResourceReloader());
		ClientEntityRenderableBoxGroupTracker.initialize();
	}
}
