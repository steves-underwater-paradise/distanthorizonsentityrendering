package io.github.steveplays28.lodentityrendering.forge.client.util.resource;

import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;

@OnlyIn(Dist.CLIENT)
public class ResourcePackUtil {
	private static final @NotNull String GENERATED_MOD_ID_PREFIX = "generated";
	private static final @NotNull String RESOURCE_PACKS_FOLDER_NAME = "resourcepacks";

	public static @NotNull IModFileInfo getResourcePackInformation(@NotNull Identifier resourcePackId) {
		if (FMLLoader.isProduction()) {
			return ModList.get().getModFileById(resourcePackId.getNamespace());
		}

		for (IModInfo mod : ModList.get().getMods()) {
			if (!mod.getModId().startsWith(GENERATED_MOD_ID_PREFIX) || !resourcePackFileExists(
					mod, String.format("%s/%s", RESOURCE_PACKS_FOLDER_NAME, resourcePackId.getPath()))) {
				continue;
			}

			return mod.getOwningFile();
		}

		return ModList.get().getModFileById(resourcePackId.getNamespace());
	}

	public static boolean resourcePackFileExists(@NotNull IModInfo info, @NotNull String path) {
		return Files.exists(info.getOwningFile().getFile().findResource(path.split("/")));
	}
}
