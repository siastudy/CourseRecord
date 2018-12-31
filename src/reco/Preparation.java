package reco;

import pojo.PageInfo;

/**
 * Get necessary screen pixel info to mock click and record screen.
 */
public class Preparation {

	/**
	 * Set page size info according to given page.
	 */
	public static void setPageInfo(String imagePath) {

		PageInfo.setPageRGB(new ScreenImage().getImagePixel(imagePath));

	}

	public void getStartTime() {

	}

	public void getBgGrey() {

	}

	public void getScrollbarBg() {

	}

}
