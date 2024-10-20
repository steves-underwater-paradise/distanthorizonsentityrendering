package io.github.steveplays28.lodentityrendering.client.util.image;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImageUtil {
	public static @NotNull Color getAverageColor(@NotNull BufferedImage bufferedImage) {
		final int pixelStep = 5;
		int sampledPixels = 0;
		long sumRed = 0, sumGreen = 0, sumBlue = 0;
		for (int x = 0; x < bufferedImage.getWidth(); x++) {
			for (int y = 0; y < bufferedImage.getHeight(); y++) {
				if (x % pixelStep == 0 && y % pixelStep == 0) {
					@NotNull final var pixelColor = new Color(bufferedImage.getRGB(x, y));
					sumRed += pixelColor.getRed();
					sumGreen += pixelColor.getGreen();
					sumBlue += pixelColor.getBlue();
					sampledPixels++;
				}
			}
		}

		return new Color(
				Math.round((float) sumRed / sampledPixels), Math.round((float) sumGreen / sampledPixels),
				Math.round((float) sumBlue / sampledPixels)
		);
	}
}
