package utils;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import pojo.Constants;
import pojo.SnapshotParam;
import pojo.VideoItem;

public class PageUtils {

	private static final Logger logger = Logger.getLogger(PageUtils.class);

	/**
	 * Do mouse left click on given xy point
	 */
	public static void clickByGivenXY(int[] xyRes) {
		
		logger.info("~~~~Point:"+xyRes[0]+","+xyRes[1]+" is clicked.");
		
		
		try {
			Robot robot = new Robot();

			robot.mouseMove(xyRes[0], xyRes[1]);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Scroll mouse middle button for a specific scale
	 */
	public static void scrollMouse(int[] position, int scale) {
		
		if(position != null && position.length >0) {
			
			logger.info("scrolling position is:("+position[0]+","+position[1]+")");

			Robot robot;
			try {
				robot = new Robot();
				robot.mouseMove(position[0], position[1]);

				Thread.sleep(2000);
				robot.mouseWheel(scale);
			} catch (AWTException | InterruptedException | NullPointerException e) {
				e.printStackTrace();
				logger.error("Mouse scrolling error occured.  \n current position to scroll mouse is: \n (" + position[0]
						+ "," + position[1]);
			}
			
		} else {
			logger.info("scroll error, current position is null");
		}
	}

	/**
	 * Get screen snapshot by pixel location Param1: leftTop xy locatoin Param2:
	 * rightDown xy location Return: Path of image file.
	 */
	public static String getSnapShot(int[] leftTop, int[] rightDown) {
		try {
			Robot robot = new Robot();
			int width = Math.abs(leftTop[0] - rightDown[0]);
			int height = Math.abs(leftTop[1] - rightDown[1]);

			System.out.println("lefttopx" + leftTop[0]);
			System.out.println("lefttopy:" + leftTop[1]);
			System.out.println("width:" + width);
			System.out.println("height:" + height);

			Rectangle rectangle = new Rectangle(leftTop[0], leftTop[1], width, height);

			BufferedImage bufferedImage = robot.createScreenCapture(rectangle);

			String fileName = Constants.SCREENSHOT_NAME.replace("time", String.valueOf(System.currentTimeMillis()));

			String fileLocation = Constants.SCREENSHOT_LOCATION;

			ImageIO.write(bufferedImage, Constants.SCREENSHOT_FORMAT, new File(fileLocation + "/" + fileName));

			File file = new File(fileLocation + "/" + fileName);
			if (file.exists()) {
				return fileLocation + "/" + fileName;
			}

		} catch (AWTException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	
	/*
	 * Get snapshot according to left-top xy and right-down xy
	 */
	public static String getSnapshotByXY(SnapshotParam param) {
		try {
			Robot robot = new Robot();

			int width = Math.abs(param.getTopLeft()[0] - param.getDownRight()[0]);
			int height = Math.abs(param.getTopLeft()[1] - param.getDownRight()[1]);

			Rectangle rectangle = new Rectangle(param.getTopLeft()[0], param.getTopLeft()[1], width, height);

			BufferedImage bufferedImage = robot.createScreenCapture(rectangle);

			String fileName = param.getFilenamePattern().replace("time", String.valueOf(System.currentTimeMillis()));

			String fileLocation = param.getFileLocation();

			ImageIO.write(bufferedImage, param.getImgFormat(), new File(fileLocation + "/" + fileName));

			File file = new File(fileLocation + "/" + fileName);
			if (file.exists()) {
				return fileLocation + "/" + fileName;
			}

		} catch (AWTException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	/*
	 * get representing x,y for selected line
	 */
	public static Integer[] locateCourseXY() {

		List<Integer[]> courseList = getCourseTableXY();

		if (courseList == null) {
			return null;
		} else {
			return getRepXY(courseList);
		}

	}

	/**
	 * Get click point with given video item zone previously stored in VideoItem.
	 */
	public static int[] getItemClickPoint(VideoItem videoItem) {

		int[] startPoint = videoItem.getStartPoint();

		int[] endLine = videoItem.getEndLine();

		int x = startPoint[0] + Math.abs(startPoint[0] - endLine[0]) / 2;
		int y = startPoint[1] + Math.abs(startPoint[1] - endLine[1]) / 2;

		return new int[] { x, y };
	}

	/*
	 * end selecting loop on discovering cursor path
	 */
	public static void endSelection() {

	}

	public static List<Integer[]> getCourseTableXY() {

		List<Integer[]> xyList = new ArrayList<Integer[]>();

		int recordStatus = 0;

		while (true) {

			/*
			 * 0: cursor is/was not moving 1: cursor is not moving
			 */

			try {

				Point p1 = MouseInfo.getPointerInfo().getLocation();

				Thread.sleep(Constants.GET_XY_INTERVAL);

				Point p2 = MouseInfo.getPointerInfo().getLocation();

				/*
				 * if p1.x <= p2.x, && recording lable = false do nothing
				 * 
				 * else, valid point, start recording, save point to arraylist lable recording =
				 * true
				 * 
				 * if p1.x == p2.x && recording lable = true, stop recording.
				 */

				if (p2.x - p1.x > 4 && Math.abs(p1.y - p2.y) < 2) {
					// recording
					recordStatus = 1;
					xyList.add(new Integer[] { p1.x, p1.y });

					System.out.println("putting xy to list");

				} else if (recordStatus == 1 && xyList.size() > 10) {// was recording and just stopped

					System.out.println("was recording and now stopped");

					// mark status to
					return xyList;
				} else if (p2.x - p1.x < 5 && Math.abs(p1.y - p2.y) > 100) {
					SoundUtils.makeSound("du");
					return null;
				} else {
					System.out.println("mouse pinter is not moving");
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static Integer[] getRepXY(List<Integer[]> list) {

		System.out.println("getting representing xy");

		int listSize = list.size();
		int[] xArray = new int[listSize];
		int[] yArray = new int[listSize];

		// get xArray, yArray from list
		for (int i = 0; i < listSize; i++) {
			xArray[i] = list.get(i)[0];
			yArray[i] = list.get(i)[1];
		}

		Arrays.sort(xArray);
		Arrays.sort(yArray);

		System.out.println("representing x:" + xArray[xArray.length / 2]);
		System.out.println("representing y:" + (yArray[yArray.length - 1] + yArray[0]) / 2);

		SoundUtils.makeSound("di");

		return new Integer[] { xArray[xArray.length / 2], (yArray[yArray.length - 1] + yArray[0]) / 2 };

	}
}
