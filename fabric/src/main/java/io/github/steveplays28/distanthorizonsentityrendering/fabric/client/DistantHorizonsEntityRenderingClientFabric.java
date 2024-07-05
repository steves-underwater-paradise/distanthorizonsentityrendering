package io.github.steveplays28.distanthorizonsentityrendering.fabric.client;

import io.github.steveplays28.distanthorizonsentityrendering.client.DistantHorizonsEntityRenderingClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import static io.github.steveplays28.distanthorizonsentityrendering.DistantHorizonsEntityRendering.MOD_ID;
import static io.github.steveplays28.distanthorizonsentityrendering.DistantHorizonsEntityRendering.MOD_NAME;
import static io.github.steveplays28.distanthorizonsentityrendering.client.DistantHorizonsEntityRenderingClient.BUILT_IN_RESOURCE_PACK_NAMESPACE;

public class DistantHorizonsEntityRenderingClientFabric implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		var modContainer = FabricLoader.getInstance().getModContainer(MOD_ID);
		if (modContainer.isEmpty()) {
			throw new IllegalStateException(
					String.format("%s's mod container (mod ID: %s) is empty. %s is unable to register a built-in default resource pack.",
							MOD_NAME, MOD_ID, MOD_NAME
					));
		}

		// Register a built-in default resource pack
		ResourceManagerHelper.registerBuiltinResourcePack(
				new Identifier(MOD_ID, BUILT_IN_RESOURCE_PACK_NAMESPACE), modContainer.get(), ResourcePackActivationType.DEFAULT_ENABLED);
		DistantHorizonsEntityRenderingClient.initialize();
	}
}
