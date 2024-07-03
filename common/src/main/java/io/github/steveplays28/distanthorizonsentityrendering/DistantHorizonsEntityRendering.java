package io.github.steveplays28.distanthorizonsentityrendering;

import com.seibel.distanthorizons.api.methods.events.DhApiEventRegister;
import com.seibel.distanthorizons.api.methods.events.abstractEvents.DhApiAfterDhInitEvent;
import io.github.steveplays28.distanthorizonsentityrendering.compat.distanthorizons.event.DHERAfterDhInitEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistantHorizonsEntityRendering {
	public static final String MOD_ID = "distanthorizonsentityrendering";
	public static final String MOD_NAME = "Distant Horizons: Entity Rendering";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void initialize() {
		LOGGER.info("Loading {}.", MOD_NAME);

		DhApiEventRegister.on(DhApiAfterDhInitEvent.class, new DHERAfterDhInitEventHandler());
	}
}
