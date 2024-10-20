package io.github.steveplays28.lodentityrendering.forge;

import io.github.steveplays28.lodentityrendering.LODEntityRendering;
import io.github.steveplays28.lodentityrendering.forge.client.LODEntityRenderingClientForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;

@Mod(LODEntityRendering.MOD_ID)
public class LODEntityRenderingForge {
	public LODEntityRenderingForge(@NotNull IEventBus eventBus) {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			LODEntityRenderingClientForge.onInitializeClient(eventBus);
		}

		LODEntityRendering.initialize();
	}
}
