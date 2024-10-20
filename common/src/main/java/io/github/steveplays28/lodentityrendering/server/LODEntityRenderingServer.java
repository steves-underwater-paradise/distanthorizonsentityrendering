package io.github.steveplays28.lodentityrendering.server;

import dev.architectury.event.events.common.LifecycleEvent;
import io.github.steveplays28.lodentityrendering.server.entity.LODEntityRenderingServerEntityTracker;

public class LODEntityRenderingServer {
	@SuppressWarnings("unused")
	private static LODEntityRenderingServerEntityTracker serverEntityTracker;

	public static void initialize() {
		LifecycleEvent.SERVER_STARTING.register(instance -> serverEntityTracker = new LODEntityRenderingServerEntityTracker());
		LifecycleEvent.SERVER_STOPPED.register(instance -> serverEntityTracker = null);
	}
}
