package io.github.steveplays28.lodentityrendering.util.fabric;

import io.github.steveplays28.lodentityrendering.util.ModUtil;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Implements {@link ModUtil}.
 */
@SuppressWarnings("unused")
public class ModUtilImpl {
	/**
	 * Checks if a mod is present during loading.
	 */
	public static boolean isModPresent(String id) {
		return FabricLoader.getInstance().isModLoaded(id);
	}
}
