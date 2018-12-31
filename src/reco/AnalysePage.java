
package reco;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pojo.Constants;
import pojo.PageInfo;
import pojo.VideoItem;
import utils.ImageUtils;
import utils.OCRUtils;

public class AnalysePage {

	private static final Logger logger = Logger.getLogger(AnalysePage.class);

	/**
	 * Analyze page screenshot to identify: 1. start, end point\n 2. Videoname \n
	 * Video Length info is not provided and is null. *
	 */
	public List<VideoItem> doAnalyse(String imagePath) {

		List<VideoItem> videoItemList = new ArrayList<VideoItem>();

		ScreenImage iPixel = new ScreenImage();

		String[][] pageRGB = iPixel.getImagePixel(imagePath);

		videoItemList = assembleRingHead(pageRGB, videoItemList);

		videoItemList = assembleCourseItemEnd(videoItemList, pageRGB);

		videoItemList = assembleVItemName(videoItemList, imagePath);

		return videoItemList;
	}

	/*
	 * Analyse page pixel file and find head xy location
	 */
	public List<VideoItem> assembleRingHead(String[][] pageRGB, List<VideoItem> videoItemList) {

		int ringLineX = 0;
		//reset selected item to null at new round.
		try {
			if (PageInfo.getRingLineX() == 0) {
				// System.out.println("pageRGB.size()" + pageRGB.length);
				int isRingLine = 0;

				for (int i = 0; i < pageRGB.length; i++) {
					for (int j = 0; j < pageRGB[0].length; j++) {
						if (pageRGB[i][j].equals(Constants.COURSE_HEAD_RING_LINE)
								&& pageRGB[i][j].equals(Constants.COURSE_HEAD_RING_LINE)
								&& pageRGB[i][j].equals(Constants.COURSE_HEAD_RING_LINE)) {
							isRingLine++;
						}
					}
					if (isRingLine > (pageRGB[0].length) * 0.18) {
						// Ring line found
						PageInfo.setRingLineX(i);
						// System.out.println("ringLineX found:" + PageInfo.getRingLineX());
						break;
					}
				}

			} else {
				// using reviously saved value
				// logger.info("Using cached value for ringLineX:"+PageInfo.getRingLineX());
				ringLineX = PageInfo.getRingLineX();
			}

			// Forgot to add this line,
			ringLineX = PageInfo.getRingLineX();

			/*
			 * Find center XY of each ring according to ring line found 1. Find start point
			 * 2. Find end point
			 */
			int number = -1;

			int div = Constants.RING_DISTANCE;

			// logger.info("looking for starting point");

			/*
			 * Look for start point of the circle
			 */
			for (int k = 2; k < pageRGB[0].length - 1; k++) {

				if ((pageRGB[ringLineX][k].equals(Constants.COURSE_HEAD_RING_ROUND))// (102,102,102)
						&& (pageRGB[ringLineX][k - 1].equals(Constants.COURSE_HEAD_RING_OUTER_BOUND))// (104,104,104)
						&& (pageRGB[ringLineX][k - 2].equals(Constants.COURSE_HEAD_RING_LINE))// (216,216,216)
						&& (pageRGB[ringLineX][k + 1].equals(Constants.COURSE_HEAD_RING_INNER_BOUND))) {// (251,251,251)

					/*
					 * Need to make startPoint as a new object at each loop. If not, all startPoint,
					 * endPoint added in :videoItemList.add(new VideoItem(number, startPoint,
					 * endPoint)); Will be the updated by following values.
					 */
					int[] startPoint = new int[] { ringLineX, k };

					// logger.info("starting point found"+startPoint[0]+","+startPoint[1]);

					/*
					 * Look for endpoint according to former startpoint
					 */
					if (startPoint != null) {
						for (int m = startPoint[1] + 1; m < startPoint[1] + 5 + div; m++) {
							if (pageRGB[ringLineX][m].equals(Constants.COURSE_HEAD_RING_ROUND) // (102,102,102)
									&& pageRGB[ringLineX][m - 1].equals(Constants.COURSE_HEAD_RING_INNER_BOUND) // (251,251,251)
									&& pageRGB[ringLineX][m - 2].equals(Constants.PAGE_BACKGROUND_WHITE) // (255,255,255)
									&& pageRGB[ringLineX][m + 1].equals(Constants.COURSE_HEAD_RING_OUTER_BOUND)) {// (104,104,104)

								/*
								 * Need to make endPoint as a new object at each loop. If not, all startPoint,
								 * endPoint added in :videoItemList.add(new VideoItem(number, startPoint,
								 * endPoint)); Will be the updated by following values.
								 */
								int[] endPoint = new int[] { ringLineX, m };

								// logger.info("endpoint found:"+endPoint[0]+","+endPoint[1]);

								// change diviation to actual value:
								div = (endPoint[1] - startPoint[1]);

								number++;

								if (startPoint[0] != 0 && startPoint[1] != 0 && endPoint[0] != 0 && endPoint[1] != 0) {
									// save start point and end point to VideoItem
									videoItemList.add(new VideoItem(number, startPoint, endPoint, false));
								}
								break;
							}
						}
					}
					/*
					 * Or, if the video item is selected, its background color is different from
					 * unselected ones.
					 */
				} else if ((pageRGB[ringLineX][k].equals(Constants.COURSE_HEAD_RING_ROUND))// (102,102,102)
						&& (pageRGB[ringLineX][k - 1].equals(Constants.COURSE_HEAD_RING_ROUND))// (102,102,102)
						&& (pageRGB[ringLineX][k - 2].equals(Constants.SELECTED_PINK_BG))// (255,241,241)
						&& (pageRGB[ringLineX][k + 1].equals(Constants.COURSE_HEAD_RING_INNER_BOUND))) {// (255,255,255)
					/*
					 * Need to make startPoint as a new object at each loop. If not, all startPoint,
					 * endPoint added in :videoItemList.add(new VideoItem(number, startPoint,
					 * endPoint)); Will be the updated by following values.
					 */
					int[] startPoint = new int[] { ringLineX, k };

					// logger.info("starting point found"+startPoint[0]+","+startPoint[1]);

					/*
					 * Look for endpoint according to former startpoint
					 */
					if (startPoint != null) {
						for (int m = startPoint[1] + 1; m < startPoint[1] + 5 + div; m++) {
							if (pageRGB[ringLineX][m].equals(Constants.COURSE_HEAD_RING_ROUND) // (102,102,102)
									&& pageRGB[ringLineX][m - 1].equals(Constants.PAGE_BACKGROUND_WHITE) // (255,255,255)
									&& pageRGB[ringLineX][m - 2].equals(Constants.PAGE_BACKGROUND_WHITE) // (255,255,255)
									&& pageRGB[ringLineX][m + 1].equals(Constants.COURSE_HEAD_RING_ROUND)// (102,102,102)
									&& pageRGB[ringLineX][m + 2].equals(Constants.SELECTED_PINK_BG)) {// (255,241,241)

								/*
								 * Need to make endPoint as a new object at each loop. If not, all startPoint,
								 * endPoint added in :videoItemList.add(new VideoItem(number, startPoint,
								 * endPoint)); Will be the updated by following values.
								 */
								int[] endPoint = new int[] { ringLineX, m };

								// logger.info("endpoint found:"+endPoint[0]+","+endPoint[1]);

								// change diviation to actual value:
								div = (endPoint[1] - startPoint[1]);

								number++;

								if (startPoint[0] != 0 && startPoint[1] != 0 && endPoint[0] != 0 && endPoint[1] != 0) {
									// save start point and end point to VideoItem
									videoItemList.add(new VideoItem(number, startPoint, endPoint, true));
								}
								break;
							}
						}
					}
				}
			}
			return videoItemList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * Find end border according the ring-head and ring-end
	 */
	public List<VideoItem> assembleCourseItemEnd(List<VideoItem> itemList, String[][] pageRGB) {

		// logger.info("looking for start line");
		// logger.info("amount of video found is:"+itemList.size());
		// logger.info("Amount of pixel pints found on screenshot
		// is:("+pageRGB[0]+","+pageRGB[1]+")");

		for (VideoItem item : itemList) {
			// find startLine
			int startPointY = item.getStartPoint()[1];
			for (int i = item.getStartPoint()[0]; i < pageRGB.length - 2; i++) {
				if (pageRGB[i][startPointY].equals(Constants.SCROLL_BAR_BORDER)
						&& pageRGB[i + 1][startPointY].equals(Constants.SCROLL_BAR_BACKGROUND)
						// && pageRGB[i + 2][startPointY].equals(Constants.SCROLL_BAR_BACKGROUND)
						&& pageRGB[i - 1][startPointY].equals(Constants.PAGE_BACKGROUND_WHITE)
						&& pageRGB[i - 2][startPointY].equals(Constants.PAGE_BACKGROUND_WHITE)) {

					int[] startLine = new int[] { i, startPointY };
					// upper end border is found:
					item.setStartLine(startLine);
					// logger.info("start line found:" + startLine[0] + "," + startLine[1]);
				} 
			}

			// find end line:
			int endPointY = item.getEndPoint()[1];
			for (int j = item.getEndPoint()[0]; j < pageRGB.length - 2; j++) {
				if (pageRGB[j][endPointY].equals(Constants.SCROLL_BAR_BORDER)
						&& pageRGB[j + 1][endPointY].equals(Constants.SCROLL_BAR_BACKGROUND)
						// && pageRGB[j + 2][endPointY].equals(Constants.SCROLL_BAR_BACKGROUND)
						&& pageRGB[j - 1][endPointY].equals(Constants.PAGE_BACKGROUND_WHITE)
						&& pageRGB[j - 2][endPointY].equals(Constants.PAGE_BACKGROUND_WHITE)) {

					int[] endLine = new int[] { j, endPointY };

					// lower end border is found:
					item.setEndLine(endLine);

					// logger.info("end line found:" + endLine[0] + "," + endLine[1]);
				}
			}
		}
		return itemList;
	}

	// do snapshot according to starting&ending xy position
	public List<VideoItem> assembleVItemName(List<VideoItem> list, String imageSrc) {

		String videoName;

		String cutImg;

		String newFilename;

		for (VideoItem videoItem : list) {

			newFilename = imageSrc.replace(imageSrc, imageSrc.replace(".", System.currentTimeMillis() + "."));

			int startPointX = videoItem.getStartPoint()[0];
			int startPointY = videoItem.getStartPoint()[1];
			int endLineUp = videoItem.getEndLine()[0];
			int endLineDown = videoItem.getEndLine()[1];
			int width = Math.abs(startPointX - endLineUp);
			int height = Math.abs(startPointY - endLineDown);

			cutImg = ImageUtils.cutImage(imageSrc, newFilename, startPointX + 10, startPointY - 5, width - 33,
					height + 5);

			// read image to video name(text)
			// videoName = OCRUtils.readImgHD(cutImg, 2);

			videoName = OCRUtils.readImg(cutImg, 2);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(videoName != null) {
				// assemble video item name to list
				videoItem.setVideoName(videoName);
				// logger.info("file name found:"+videoName);
			} else {
				System.out.println("video name recognized is null");
			}

		}

		return list;
	}

}
