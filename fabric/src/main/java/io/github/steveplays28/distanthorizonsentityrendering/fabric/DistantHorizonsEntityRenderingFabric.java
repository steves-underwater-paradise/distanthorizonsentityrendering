package io.github.steveplays28.distanthorizonsentityrendering.fabric;

import io.github.steveplays28.distanthorizonsentityrendering.DistantHorizonsEntityRendering;
import net.fabricmc.api.ModInitializer;

public class DistantHorizonsEntityRenderingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DistantHorizonsEntityRendering.initialize();
    }
}
