package io.github.steveplays28.lodentityrendering.client.resource;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import io.github.steveplays28.lodentityrendering.LODEntityRendering;
import io.github.steveplays28.lodentityrendering.client.entity.color.EntityAverageColorRegistry;
import io.github.steveplays28.lodentityrendering.client.resource.json.EntityAverageColor;
import io.github.steveplays28.lodentityrendering.client.util.image.BufferedImageUtil;
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
public class LODEntityRenderingResourceReloader implements ResourceReloader {
	private static final @NotNull String JSON_FILE_SUFFIX = ".json";
	private static final @NotNull String PNG_FILE_SUFFIX = ".png";
	private static final @NotNull String ENTITY_TEXTURES_FOLDER_NAME = "textures/entity";
	private static final @NotNull String ENTITY_AVERAGE_COLORS_FOLDER_NAME = "average_colors/entity";

	/**
	 * Asynchronously process and load resource-based data.
	 * The code must be thread-safe and not modify game state!
	 *
	 * @param synchronizer    The {@link Synchronizer} which should be used for this stage.
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
			// TODO: Move into EntityAverageColorRegistry#register using a client-side resource reload event
			EntityAverageColorRegistry.ENTITY_AVERAGE_COLOR_REGISTRY.clear();
			loadAndRegisterAverageEntityTextureColorsFromJson(resourceManager);
			sampleAndRegisterAverageEntityTextureColorsFromEntityTextures(resourceManager);

			synchronizer.whenPrepared(null);
			return null;
		}, applyExecutor);
	}

	private void loadAndRegisterAverageEntityTextureColorsFromJson(@NotNull ResourceManager resourceManager) {
		@NotNull final var averageEntityTextureColorJsonFiles = resourceManager.findResources(
				ENTITY_AVERAGE_COLORS_FOLDER_NAME, identifier -> identifier.toString().endsWith(JSON_FILE_SUFFIX));
		for (@NotNull final var averageEntityTextureColorJsonFilePath : averageEntityTextureColorJsonFiles.keySet()) {
			@NotNull final var entityTexturePathSplit = averageEntityTextureColorJsonFilePath.getPath().replace(JSON_FILE_SUFFIX, "").split(
					"/");
			@NotNull final var entityIdentifier = new Identifier(
					averageEntityTextureColorJsonFilePath.getNamespace(), entityTexturePathSplit[entityTexturePathSplit.length - 1]
			);
			if (EntityAverageColorRegistry.ENTITY_AVERAGE_COLOR_REGISTRY.containsKey(entityIdentifier)) {
				continue;
			}

			try {
				@NotNull final var gson = new Gson().newBuilder().setFieldNamingPolicy(
						FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
				EntityAverageColorRegistry.ENTITY_AVERAGE_COLOR_REGISTRY.put(entityIdentifier, gson.fromJson(
						averageEntityTextureColorJsonFiles.get(averageEntityTextureColorJsonFilePath).getReader(),
						EntityAverageColor.class
				).getAverageColor());
			} catch (IOException e) {
				LODEntityRendering.LOGGER.error(
						"Exception thrown while trying to load an entity's average color from JSON ({}):\n{}", entityIdentifier, e);
			}
		}
	}

	private void sampleAndRegisterAverageEntityTextureColorsFromEntityTextures(@NotNull ResourceManager resourceManager) {
		@NotNull final var entityTextures = resourceManager.findResources(
				ENTITY_TEXTURES_FOLDER_NAME, identifier -> identifier.toString().endsWith(PNG_FILE_SUFFIX));
		for (@NotNull final var entityTexturePath : entityTextures.keySet()) {
			@NotNull final var entityTexturePathSplit = entityTexturePath.getPath().replace(PNG_FILE_SUFFIX, "").split("/");
			String entityName;
			if (entityTexturePathSplit.length > 3) {
				entityName = entityTexturePathSplit[entityTexturePathSplit.length - 2];
			} else {
				entityName = entityTexturePathSplit[entityTexturePathSplit.length - 1];
			}

			@NotNull final var entityIdentifier = new Identifier(
					entityTexturePath.getNamespace(), entityName
			);
			if (EntityAverageColorRegistry.ENTITY_AVERAGE_COLOR_REGISTRY.containsKey(entityIdentifier)) {
				continue;
			}

			try {
				EntityAverageColorRegistry.ENTITY_AVERAGE_COLOR_REGISTRY.put(
						entityIdentifier,
						BufferedImageUtil.getAverageColor(ImageIO.read(entityTextures.get(entityTexturePath).getInputStream()))
				);
			} catch (IOException e) {
				LODEntityRendering.LOGGER.error(
						"Exception thrown while trying to load entity texture ({}):\n{}", entityIdentifier, e);
			}
		}
	}
}
