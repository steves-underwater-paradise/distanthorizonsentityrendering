package io.github.steveplays28.distanthorizonsentityrendering.server;

import io.github.steveplays28.distanthorizonsentityrendering.server.entity.DHERServerEntityTracker;

public class DistantHorizonsEntityRenderingServer {
	@SuppressWarnings({"unused", "FieldCanBeLocal"})
	private static DHERServerEntityTracker serverEntityTracker;

	public static void initialize() {
		serverEntityTracker = new DHERServerEntityTracker();
	}
}
