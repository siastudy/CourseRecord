package reco;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import org.apache.log4j.Logger;

import pojo.Constants;
import pojo.PageInfo;
import pojo.SnapshotParam;
import pojo.VideoItem;
import utils.OCRUtils;
import utils.PageUtils;
import utils.ShellUtils;

public class Start {

	private static final Logger logger = Logger.getLogger(Start.class);

	public static List<VideoItem> courseItemList;

	public static void main(String[] args) throws InterruptedException {

		Thread.sleep(3000);

		snapAnalyseRecord();

	}

	public static String snapFullScreen() {

		// Get screen size
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();
		int height = (int) screensize.getHeight();

		// also save page width height info to pageInfo
		PageInfo.setPageSize(new int[] { width, height });

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Do snapshot to current window
		 */
		SnapshotParam sParamFull = new SnapshotParam();
		sParamFull.setTopLeft(new int[] { 0, 0 });
		sParamFull.setDownRight(new int[] { 0 + width, 0 + height });

		// disable following setter to use default value
		/*
		 * sParamFull.setFileLocation(Constants.SCREENSHOT_LOCATION);
		 * sParamFull.setFilenamePattern(Constants.SCREENSHOT_NAME);
		 * sParamFull.setImgFormat(Constants.SCREENSHOT_FORMAT);
		 */

		return PageUtils.getSnapshotByXY(sParamFull);

	}

	private static void snapAnalyseRecord() throws InterruptedException {

		AnalysePage analyse = new AnalysePage();

		

		String snapPicname = snapFullScreen();

		/*
		 * Analyse Screenshot
		 */
		courseItemList = analyse.doAnalyse(snapPicname);

		// Record screen.
		recordOneByOne(courseItemList);

	}

	private static void recordOneByOne(List<VideoItem> courseItemList) throws InterruptedException {

		int[] clickPoint = null;

		int startPoint = 0;

		int recordRes = -1;
		/*
		 * Before clicking, find whether there are selected items or not. If there is,
		 * start clicking from the (1st)selected one, else start from the first item in
		 * the list.
		 */
		for (VideoItem item : courseItemList) {

			if (item.getIsSelected() == true) {
				startPoint = item.getItemNumber();
				break;
			}
		}

		VideoItem videoItem;

		for (int i = startPoint; i < courseItemList.size(); i++) {
			videoItem = courseItemList.get(i);

			clickPoint = PageUtils.getItemClickPoint(videoItem);

			// click video item's central area
			if (clickPoint != null) {
				PageUtils.clickByGivenXY(clickPoint);
			}

			try {
				/*
				 * Assemble video length info to videoItem
				 */
				// sleep 10s to wait for the network buffer.
				Thread.sleep(10000);

				/*
				 * Video length info appear on play bar at the bottom of the page Do snapshot
				 * for video time area OCR snaped page Assemble video length info into videoItem
				 */
				SnapshotParam sParamLength = new SnapshotParam();
				sParamLength.setTopLeft(Constants.VIDEO_LENGTH_TOP_LEFT);
				sParamLength.setDownRight(Constants.VIDEO_LENGTH_DOWN_RIGHT);

				String videoLengthPic = PageUtils.getSnapshotByXY(sParamLength);

				int currentVideoLength = 130;
				
				
// disable baidu video length pic recoginze, video length is set to 120 mins				
//				String vLength;
//
//				//get string from of video length returned from baidu cloud.
//				vLength = OCRUtils.readImgHD(videoLengthPic, 1);
//				
//				//if no valid data was found, give video length as 120 mins
//				if(vLength == null || vLength.length() == 0) {
//					currentVideoLength = 120;
//					logger.info("start: line:141:video length info is null, skip to next one");
//					logger.info("give video length as 120 mins");
//				} else {
//					currentVideoLength = Integer.valueOf(OCRUtils.readImgHD(videoLengthPic, 1)).intValue();
//					System.out.println("Current video length:"+currentVideoLength);
//					//video length is abnormal, skip 
//					if(currentVideoLength > 180) {
//						logger.error("Video:"+videoItem.getVideoName()+", length info is wrong, length:"+currentVideoLength);
//						logger.error("give video legth as 120mins");
//						currentVideoLength = 120;
//					}
//				}
//				
				// Assemble total Minutes of video to current VideoItem
				videoItem.setVideoLength(currentVideoLength);

				// logger.info("Full info for videoItem:" + videoItem.toString());

				Thread.sleep(10000);
				// Click play btn
				logger.info("click play button");
				PageUtils.clickByGivenXY(Constants.PLAY_BUTTON_XY);
				
				Thread.sleep(3000);

				
				// HEX value of every resolution point
				String[][] pageRGB = new ScreenImage().getImagePixel(snapFullScreen());
				
				
				while(pageRGB[Constants.CENTER_TRI_ANGLE[0]][Constants.CENTER_TRI_ANGLE[1]] == Constants.PAGE_BACKGROUND_WHITE
						&& pageRGB[Constants.CENTER_TRI_ANGLE[0]
								+ 1][Constants.CENTER_TRI_ANGLE[1]] == Constants.PAGE_BACKGROUND_WHITE
						&& pageRGB[Constants.CENTER_TRI_ANGLE[0]
								+ 2][Constants.CENTER_TRI_ANGLE[1]] == Constants.PAGE_BACKGROUND_WHITE
						&& pageRGB[Constants.CENTER_TRI_ANGLE[0]
								+ 3][Constants.CENTER_TRI_ANGLE[1]] == Constants.PAGE_BACKGROUND_WHITE
						&& pageRGB[Constants.CENTER_TRI_ANGLE[0]
								+ 4][Constants.CENTER_TRI_ANGLE[1]] == Constants.PAGE_BACKGROUND_WHITE
						&& pageRGB[Constants.CENTER_TRI_ANGLE[0]
								+ 5][Constants.CENTER_TRI_ANGLE[1]] == Constants.PAGE_BACKGROUND_WHITE) {
					
					// Central play button is not clicked, video is not playing
					// reclick the target item
					PageUtils.clickByGivenXY(clickPoint);
					Thread.sleep(10000);
					PageUtils.clickByGivenXY(Constants.PLAY_BUTTON_XY);
					
					pageRGB = new ScreenImage().getImagePixel(snapFullScreen());
					
					System.out.println("Central play button is not clicked, video is not playing");
					
				}
				
			} catch (NumberFormatException | InterruptedException e) {
				// logger.error("String cannot convert to int, skip to next one");
				continue;
			}
			/*
			 * Play and Record video by information provided in VideoItem
			 */
			Screen recordScreen = new Screen();

			recordScreen.recordOneVideo(videoItem, Constants.RECORD_VIDOE_SAVE_PATH);

			// wait for the video end
			// Disable because there is shell-is-runing while loop
			// Thread.sleep(videoItem.getVideoLength());

			// if there is ffmpeg process runing

			String[] checkCmd = { "sh", Constants.CHECK_FFMEPG_RUNNING_STATUS };

			int ffmpegStatus = 1;

			while (ffmpegStatus == 1) {
				ffmpegStatus = ShellUtils.doInvoke(checkCmd);
				System.out.println("There is ffmpeg process runing...");
				Thread.sleep(5000);
			}
		}

		if (clickPoint != null) {

			// logger.info("scroll for more is invoked");
			scrollForMore(clickPoint, Constants.SCROLL_AMOUNT);

			Thread.sleep(5000);

			// empty container:
			courseItemList.clear();

			// start new run:
			snapAnalyseRecord();
		}
	}

	/**
	 * Scroll mouse for more videos If it's private static, the static method will
	 * run once the program started.
	 */
	// TODO
	private static void scrollForMore(int[] pos, int amount) {

		PageUtils.scrollMouse(pos, amount);

	}

}
