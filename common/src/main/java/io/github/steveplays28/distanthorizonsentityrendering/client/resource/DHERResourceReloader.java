package io.github.steveplays28.distanthorizonsentityrendering.client.resource;

import io.github.steveplays28.distanthorizonsentityrendering.DistantHorizonsEntityRendering;
import io.github.steveplays28.distanthorizonsentityrendering.client.entity.color.EntityAverageColorCache;
import io.github.steveplays28.distanthorizonsentityrendering.client.util.image.BufferedImageUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Environment(EnvType.CLIENT)
public class DHERResourceReloader implements ResourceReloader {
	private static final @NotNull String PNG_FILE_SUFFIX = ".png";
	private static final @NotNull String ENTITY_TEXTURES_FOLDER_NAME = "textures/entity";

	/**
	 * Asynchronously process and load resource-based data.
	 * The code must be thread-safe and not modify game state!
	 *
	 * @param synchronizer    The {@link Synchronizer} which may be used for this stage.
	 * @param resourceManager The {@link ResourceManager} used during reloading.
	 * @param prepareProfiler The {@link Profiler} which may be used for this stage.
	 * @param applyProfiler   The {@link Profiler} which may be used for this stage.
	 * @param prepareExecutor The {@link Executor} which should be used for this stage.
	 * @param applyExecutor   The {@link Executor} which should be used for this stage.
	 * @return A {@link CompletableFuture} representing the completed result.
	 */
	@Override
	public @NotNull CompletableFuture<Void> reload(@NotNull Synchronizer synchronizer, @NotNull ResourceManager resourceManager, @NotNull Profiler prepareProfiler, @NotNull Profiler applyProfiler, @NotNull Executor prepareExecutor, @NotNull Executor applyExecutor) {
		return CompletableFuture.supplyAsync(() -> {
			// TODO: Move into EntityAverageColorCache#register using a client-side resource reload event
			EntityAverageColorCache.ENTITY_AVERAGE_COLORS.clear();

			@NotNull final var mobTextures = resourceManager.findResources(
					ENTITY_TEXTURES_FOLDER_NAME, identifier -> identifier.toString().endsWith(PNG_FILE_SUFFIX));
			for (@NotNull final var mobTexturePath : mobTextures.keySet()) {
				@NotNull final var mobTexturePathSplit = mobTexturePath.getPath().replace(PNG_FILE_SUFFIX, "").split("/");
				@NotNull final var mobIdentifier = new Identifier(
						mobTexturePath.getNamespace(), mobTexturePathSplit[mobTexturePathSplit.length - 2]
				);
				if (EntityAverageColorCache.ENTITY_AVERAGE_COLORS.containsKey(mobIdentifier)) {
					continue;
				}

				try {
					EntityAverageColorCache.ENTITY_AVERAGE_COLORS.put(
							mobIdentifier,
							BufferedImageUtil.getAverageColor(ImageIO.read(mobTextures.get(mobTexturePath).getInputStream()))
					);
				} catch (IOException e) {
					DistantHorizonsEntityRendering.LOGGER.error(
							"Exception thrown while trying to load mob texture ({}):\n{}", mobIdentifier, e);
				}
			}

			synchronizer.whenPrepared(null);
			return null;
		}, applyExecutor);
	}
}
