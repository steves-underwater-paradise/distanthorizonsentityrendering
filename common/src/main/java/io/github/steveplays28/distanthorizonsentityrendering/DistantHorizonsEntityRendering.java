package io.github.steveplays28.distanthorizonsentityrendering;

import dev.architectury.registry.ReloadListenerRegistry;
import io.github.steveplays28.distanthorizonsentityrendering.client.compat.distanthorizons.rendering.entity.DHERClientEntityRenderableBoxGroupTracker;
import io.github.steveplays28.distanthorizonsentityrendering.client.resource.DHERResourceReloader;
import io.github.steveplays28.distanthorizonsentityrendering.server.DistantHorizonsEntityRenderingServer;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistantHorizonsEntityRendering {
	public static final String MOD_ID = "distanthorizonsentityrendering";
	public static final String MOD_NAMESPACE = "distanthorizonsentityrendering";
	public static final String MOD_NAME = "Distant Horizons: Entity Rendering";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void initialize() {
		LOGGER.info("Loading {}.", MOD_NAME);

		// TODO: Move into a client initializer
		ReloadListenerRegistry.register(ResourceType.CLIENT_RESOURCES, new DHERResourceReloader());
		// TODO: Move into a client initializer
		DHERClientEntityRenderableBoxGroupTracker.initialize();
		DistantHorizonsEntityRenderingServer.initialize();
	}
}
