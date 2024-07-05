package io.github.steveplays28.distanthorizonsentityrendering.forge.client.event.resource;

import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

import static io.github.steveplays28.distanthorizonsentityrendering.client.DistantHorizonsEntityRenderingClient.BUILT_IN_RESOURCE_PACK_NAME;

@OnlyIn(Dist.CLIENT)
public class DHERResourcePackEventHandler {
	@SubscribeEvent
	public void onAddPackFinders(@NotNull AddPackFindersEvent event) {
		// Register a built-in default resource pack
		event.addRepositorySource(new FileResourcePackProvider(Path.of(BUILT_IN_RESOURCE_PACK_NAME), ResourceType.CLIENT_RESOURCES,
				ResourcePackSource.BUILTIN
		));
	}
}
