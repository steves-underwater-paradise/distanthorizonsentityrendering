package io.github.steveplays28.distanthorizonsentityrendering.forge.client.event.resource;

import io.github.steveplays28.distanthorizonsentityrendering.DistantHorizonsEntityRendering;
import io.github.steveplays28.distanthorizonsentityrendering.forge.client.util.resource.ResourcePackUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.resource.PathPackResources;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.steveplays28.distanthorizonsentityrendering.DistantHorizonsEntityRendering.*;
import static io.github.steveplays28.distanthorizonsentityrendering.client.DistantHorizonsEntityRenderingClient.BUILT_IN_RESOURCE_PACK_NAMESPACE;

@OnlyIn(Dist.CLIENT)
public class DHERResourcePackEventHandler {
	private static final @NotNull String RESOURCE_PACKS_FOLDER = "resourcepacks";
	private static final @NotNull Identifier BUILT_IN_RESOURCE_PACK_ID = new Identifier(DistantHorizonsEntityRendering.MOD_ID, BUILT_IN_RESOURCE_PACK_NAMESPACE);

	@SubscribeEvent
	public static void onAddPackFinders(@NotNull AddPackFindersEvent event) {
		if (event.getPackType() != ResourceType.CLIENT_RESOURCES) {
			return;
		}

		// Register a built-in default resource pack
		@NotNull final ResourcePackProfile.PackFactory resourcePackFactory = id -> new PathPackResources(
				id, true,
				ResourcePackUtil.getResourcePackInformation(BUILT_IN_RESOURCE_PACK_ID).getFile().findResource(
						RESOURCE_PACKS_FOLDER,
						BUILT_IN_RESOURCE_PACK_NAMESPACE
				)
		);
		@Nullable final var resourcePackMetadata = ResourcePackProfile.loadMetadata(BUILT_IN_RESOURCE_PACK_NAMESPACE, resourcePackFactory);
		if (resourcePackMetadata == null) {
			DistantHorizonsEntityRendering.LOGGER.error(
					"Error occurred while trying to register {}'s default built-in resource pack ({}): resourcePackMetadata == null\n{}",
					MOD_NAME, BUILT_IN_RESOURCE_PACK_ID, new Throwable().getStackTrace()
			);
			return;
		}

		// TODO: Enable the built-in default resource pack by default
		MinecraftClient.getInstance().getResourcePackManager().addPackFinder(profileAdder -> profileAdder.accept(ResourcePackProfile.of(
				DistantHorizonsEntityRendering.MOD_ID, Text.literal(BUILT_IN_RESOURCE_PACK_ID.toString()), false,
				resourcePackFactory, resourcePackMetadata, ResourceType.CLIENT_RESOURCES, ResourcePackProfile.InsertionPosition.BOTTOM,
				false, ResourcePackSource.BUILTIN
		)));
	}
}
