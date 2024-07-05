package io.github.steveplays28.distanthorizonsentityrendering.forge.client;

import io.github.steveplays28.distanthorizonsentityrendering.client.DistantHorizonsEntityRenderingClient;
import io.github.steveplays28.distanthorizonsentityrendering.forge.client.event.resource.DHERResourcePackEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DistantHorizonsEntityRenderingClientForge {
	public static void onInitializeClient(@NotNull IEventBus eventBus) {
		DistantHorizonsEntityRenderingClient.initialize();
		eventBus.register(DHERResourcePackEventHandler.class);
	}
}
