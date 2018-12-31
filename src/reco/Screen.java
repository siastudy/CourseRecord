package reco;

import org.apache.log4j.Logger;

import pojo.Constants;
import pojo.PageInfo;
import pojo.VideoItem;
import utils.ShellUtils;

/**
 * Invoke linux .sh script to record screen.
 * 
 */
public class Screen {

	private static Logger logger = Logger.getLogger(Screen.class);

	/**
	 * Record one video according to given VideoItem. Param1:screen width
	 * Param2:screen height Param3:video length Param4:video name Param5:video path
	 */
	public int recordOneVideo(VideoItem videoItem, String savePath) {
		logger.info("==========video to record===========");
		logger.info(videoItem.toString());
		logger.info("====================================");
		
		String width = String.valueOf(PageInfo.getPageSize()[0]);
		String height = String.valueOf(PageInfo.getPageSize()[1]);

		String vLength = String.valueOf(Integer.valueOf(videoItem.getVideoLength()).intValue() * 60);
		String vName = videoItem.getVideoName().replace(" ", "") + ".mkv";
		
		String[] cmd = { "sh", Constants.VIDEO_RECORD_SCRIPT, width, height, vLength, vName, savePath };
		
		ShellUtils.doInvoke(cmd);
		
		
		return 0;
	}

}
