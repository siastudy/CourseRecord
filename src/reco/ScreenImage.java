package reco;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import utils.ColorUtils;

public class ScreenImage {
	/**
	 * Get pixel value of each dot in a picture for the given picture path.
	 * Return String-Value color of each dot
	 * 
	 * @throws Exception
	 */
	public String[][] getImagePixel(String imagePath) {

		String[][] pageRGB = null;
		
		try {
			File file = new File(imagePath);
			BufferedImage bi = ImageIO.read(file);

			int width = bi.getWidth();
			int height = bi.getHeight();
			int minx = bi.getMinX();
			int miny = bi.getMinY();

			pageRGB = new String[width][height];

			int r = 0;
			int g = 0;
			int b = 0;

			for (int i = minx; i < width; i++) {
				// bw.write("i=" + i);
				for (int j = miny; j < height; j++) {
					int pixel = bi.getRGB(i, j);
					r = (pixel & 0xff0000) >> 16;
					g = (pixel & 0xff00) >> 8;
					b = (pixel & 0xff);

					pageRGB[i][j] = ColorUtils.toHexFromColor(new int[] { r, g, b });
				}
			}
			return pageRGB;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pageRGB;

	}
	
	
	/**
	 * Return 255,255,255 style pixel color
	 * */
	public String[][] getImagePixelRGB(String imagePath) {

		String[][] pageRGB = null;
		
		try {
			File file = new File(imagePath);
			BufferedImage bi = ImageIO.read(file);

			int width = bi.getWidth();
			int height = bi.getHeight();
			int minx = bi.getMinX();
			int miny = bi.getMinY();

			pageRGB = new String[width][height];

			int r = 0;
			int g = 0;
			int b = 0;

			for (int i = minx; i < width; i++) {
				// bw.write("i=" + i);
				for (int j = miny; j < height; j++) {
					int pixel = bi.getRGB(i, j);
					r = (pixel & 0xff0000) >> 16;
					g = (pixel & 0xff00) >> 8;
					b = (pixel & 0xff);

					pageRGB[i][j] = r+","+g+","+b;
				}
			}
			return pageRGB;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pageRGB;

	}

	/**
	 * @param x
	 * @param y
	 * @return 
	 * @throws AWTException
	 */
	public static int getScreenPixel(int x, int y) throws AWTException {
		Robot rb = null;
		rb = new Robot();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension di = tk.getScreenSize();
		System.out.println(di.width);
		System.out.println(di.height);
		Rectangle rec = new Rectangle(0, 0, di.width, di.height);
		BufferedImage bi = rb.createScreenCapture(rec);
		int pixelColor = bi.getRGB(x, y);

		return 16777216 + pixelColor;
	}

}
