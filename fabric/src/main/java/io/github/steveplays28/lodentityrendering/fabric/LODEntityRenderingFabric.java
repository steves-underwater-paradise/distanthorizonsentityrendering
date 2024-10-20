package io.github.steveplays28.lodentityrendering.fabric;

import io.github.steveplays28.lodentityrendering.LODEntityRendering;
import net.fabricmc.api.ModInitializer;

public class LODEntityRenderingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LODEntityRendering.initialize();
    }
}
