package io.github.steveplays28.distanthorizonsentityrendering.forge;

import io.github.steveplays28.distanthorizonsentityrendering.DistantHorizonsEntityRendering;
import io.github.steveplays28.distanthorizonsentityrendering.forge.client.DistantHorizonsEntityRenderingClientForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;

@Mod(DistantHorizonsEntityRendering.MOD_ID)
public class DistantHorizonsEntityRenderingForge {
	public DistantHorizonsEntityRenderingForge(@NotNull IEventBus eventBus) {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			DistantHorizonsEntityRenderingClientForge.onInitializeClient(eventBus);
		}

		DistantHorizonsEntityRendering.initialize();
	}
}
