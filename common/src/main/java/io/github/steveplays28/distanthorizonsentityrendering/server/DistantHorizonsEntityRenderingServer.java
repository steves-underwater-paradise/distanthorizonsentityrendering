package io.github.steveplays28.distanthorizonsentityrendering.server;

import dev.architectury.event.events.common.LifecycleEvent;
import io.github.steveplays28.distanthorizonsentityrendering.server.entity.DHERServerEntityTracker;

public class DistantHorizonsEntityRenderingServer {
	@SuppressWarnings("unused")
	private static DHERServerEntityTracker serverEntityTracker;

	public static void initialize() {
		LifecycleEvent.SERVER_STARTING.register(instance -> serverEntityTracker = new DHERServerEntityTracker());
		LifecycleEvent.SERVER_STOPPED.register(instance -> serverEntityTracker = null);
	}
}
