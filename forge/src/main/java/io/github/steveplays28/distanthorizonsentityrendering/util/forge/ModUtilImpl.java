package io.github.steveplays28.distanthorizonsentityrendering.util.forge;

import io.github.steveplays28.distanthorizonsentityrendering.util.ModUtil;
import net.minecraftforge.fml.loading.LoadingModList;

/**
 * Implements {@link ModUtil}.
 */
@SuppressWarnings("unused")
public class ModUtilImpl {
	/**
	 * Checks if a mod is present during loading.
	 */
	public static boolean isModPresent(String id) {
		return LoadingModList.get().getModFileById(id) != null;
	}
}
