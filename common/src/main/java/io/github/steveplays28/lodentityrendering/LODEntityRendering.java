package io.github.steveplays28.lodentityrendering;

import io.github.steveplays28.lodentityrendering.server.LODEntityRenderingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LODEntityRendering {
	public static final String MOD_ID = "lodentityrendering";
	public static final String MOD_NAME = "LOD Entity Rendering";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void initialize() {
		LOGGER.info("Loading {}.", MOD_NAME);

		LODEntityRenderingServer.initialize();
	}
}
