package utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static String cutImage(String srcImageFile, String result, int x, int y, int width, int height) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File(srcImageFile));

			int srcWidth = bi.getWidth();
			int srcHeight = bi.getHeight();

			if (srcWidth > 0 && srcHeight > 0) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				ImageFilter filter = new CropImageFilter(x, y, width, height);
				Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), filter));
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null);
				g.dispose();
				ImageIO.write(tag, "PNG", new File(result));

				File outFile = new File(result);
				if (outFile.exists()) {
					return result;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
