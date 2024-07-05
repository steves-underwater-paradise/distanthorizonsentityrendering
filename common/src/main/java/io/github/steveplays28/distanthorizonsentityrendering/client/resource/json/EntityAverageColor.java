package io.github.steveplays28.distanthorizonsentityrendering.client.resource.json;

import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class EntityAverageColor {
	@SuppressWarnings("unused")
	private @Nullable String averageColor;

	private transient @Nullable Color averageColorCached;

	public @Nullable Color getAverageColor() {
		if (averageColor == null) {
			return null;
		}
		if (averageColorCached == null) {
			var parsedAverageColor = io.github.steveplays28.distanthorizonsentityrendering.util.Color.parse(averageColor);
			averageColorCached = new Color(parsedAverageColor.red, parsedAverageColor.green, parsedAverageColor.blue);
		}

		return averageColorCached;
	}
}
