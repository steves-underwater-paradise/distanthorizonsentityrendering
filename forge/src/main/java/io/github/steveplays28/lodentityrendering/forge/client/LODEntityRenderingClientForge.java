package io.github.steveplays28.lodentityrendering.forge.client;

import io.github.steveplays28.lodentityrendering.client.LODEntityRenderingClient;
import io.github.steveplays28.lodentityrendering.forge.client.event.resource.LODEntityRenderingResourcePackEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LODEntityRenderingClientForge {
	public static void onInitializeClient(@NotNull IEventBus eventBus) {
		LODEntityRenderingClient.initialize();
		eventBus.register(LODEntityRenderingResourcePackEventHandler.class);
	}
}
