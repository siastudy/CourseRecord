package test;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import org.junit.Test;

import pojo.Constants;
import pojo.PageInfo;
import pojo.SnapshotParam;
import reco.AnalysePage;
import reco.ScreenImage;
import reco.StreamGobbler;
import utils.ColorUtils;
import utils.ImageUtils;
import utils.OCRUtils;
import utils.PageUtils;
import utils.ShellUtils;
import utils.VoiceUtils;

public class CommonTest {
	
	@Test
	public void isrunnigTest() {
		
		String[] checkCmd = { "sh", Constants.CHECK_FFMEPG_RUNNING_STATUS };
		int res = ShellUtils.doInvoke(checkCmd);

		System.out.println("res from shell:"+res);
	}
	
	

	@Test
	public void execShell() {

		// String cmd = "sh ffmpeg -nostdin -video_size 100 x 100 -framerate 25 -f
		// x11grab -i :0.0 -f pulse -i
		// alsa_output.pci-0000_00_1b.0.analog-stereo.monitor -c:a libmp3lame -t 60
		// /home/vincent/asdfa898j.mkv";

		// String cmd = "ls -l";
		String[] cmd = { "sh", "/home/vincent/os-scripts/record-video.sh", "1024", "768", "20", "4.mkv",
				"/home/vincent" };

		try {
			Process p = Runtime.getRuntime().exec(cmd);

			// Get error output
			StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERR");

			// Get info output
			StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

			// Kick them off
			errorGobbler.start();
			outputGobbler.start();

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

			// Check if got any error
			int exitVal = p.waitFor();
			
			

			System.out.println("ExitValue:" + exitVal);
			

			
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("content:"+line);
			}

			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			
		}
	}


	@Test
	public void shellUtils() {

//execute a shell scirpt to record video
/*		String cmd1 = "sh /home/vincent/os-scripts/record-video.sh";
		
		String width = "1024";
		String height = "768";
		String length = "100";
		String name = "bjsx22z.mkv";
		String path = "/home/vincent";

		String[] command1 = { "sh", "-c", cmd1 + " "+width+ " " +height + " " +length+ " "+name+ " "+path };
		
		ShellUtils.pbRun(command1);*/
		
		//execute a command
		
		
// execute a shell scirpt to terminate a running ffmpeg process
		
		
		

	}

	@Test
	public void mouseScroll() throws InterruptedException {

		Thread.sleep(2000);

		int[] pos = new int[] { 150, 200 };

		PageUtils.scrollMouse(pos, 3);

	}

	@Test
	public void testArray() {
		int[][] array = new int[][] { { 1, 2, 3 }, { 1, 2, 3, } };

		System.out.println("array.length:" + array.length);
		System.out.println("array[array.length].length:" + array[array.length - 1].length);

	}

	@Test
	public void cutImageTest() {
		ImageUtils.cutImage("E:\\temp\\2018-11-12_121117.png", "E:\\temp\\2018-11-12_121117_new.png", 0, 0, 400, 400);
	}

	@Test
	public void getVideoItemInfo() {

		String imagePath = "E:\\temp\\4.png";
		AnalysePage ap = new AnalysePage();
		ap.doAnalyse(imagePath);
	}

	@Test
	public void getSnapshotTest() throws InterruptedException {

		Thread.sleep(4000);

		SnapshotParam sParamLength = new SnapshotParam();
		sParamLength.setTopLeft(new int[] { 0, 0 });
		sParamLength.setDownRight(new int[] { 1366, 768 });
		// disable following setters to use default value
		// sParamLength.setFileLocation(Constants.SCREENSHOT_LOCATION);
		// sParamLength.setFilenamePattern("6_time_.png");
		// sParamLength.setImgFormat("PNG");

		String videoLengthPic = PageUtils.getSnapshotByXY(sParamLength);

		System.out.println("videoLengthPic" + videoLengthPic);

	}

	@Test
	public void getImagePixelTest() {
		// ImagePixelUtils.getScreenPixel(100, 345);
		// System.out.println(x + " - ");

		BufferedWriter bw;

		try {

			String imagePath = "/home/vincent/jiayu-video/snapshots/screenshot_1542459132646.png";

			String outPath = "/home/vincent/jiayu-video/snapshots/screenshot_1542459132646.txt";

			ScreenImage iPix = new ScreenImage();

			// String[][] imagePixel = iPix.getImagePixel(imagePath);
			String[][] imagePixel = iPix.getImagePixelRGB(imagePath);

			Writer writer = new FileWriter(new File(outPath));

			bw = new BufferedWriter(writer);

			for (int i = 0; i < imagePixel.length; i++) {
				for (int j = 0; j < imagePixel[0].length; j++) {
					bw.append(imagePixel[i][j]);
					bw.append("\t");
				}
				bw.append("\n");
			}

			bw.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void rgbToHexTest() {

		File outFile = new File("E:\\temp\\Hexout.txt");

		File inFile = new File("E:\\temp\\pixelOutput4.txt");

		BufferedReader br;
		BufferedWriter bw;
		Reader reader;
		Writer writer;

		try {
			reader = new FileReader(inFile);
			writer = new FileWriter(outFile);

			br = new BufferedReader(reader);
			bw = new BufferedWriter(writer);

			String readLine;
			String[] readArray;
			String[] colorUnit;
			while ((readLine = br.readLine()) != null) {
				readArray = readLine.split("\t");
				for (int i = 0; i < readArray.length; i++) {
					colorUnit = readArray[i].split("\\,");
					String hexColorStr = ColorUtils.toHexFromColor(new int[] { Integer.valueOf(colorUnit[0]),
							Integer.valueOf(colorUnit[1]), Integer.valueOf(colorUnit[2]) });
					bw.append(hexColorStr);
					bw.append("\t");
				}
				bw.append("\n");
			}
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void audioPlayTest() {

		VoiceUtils.playVoice("F:\\temp\\audio\\baidu_out_1541825010854.mp3");
	}

	@Test
	public void text2VoiceTest() {

		VoiceUtils.getVoice("锟斤拷锟�");

	}

	@Test
	public void getTokenTest() {
		System.out.println(VoiceUtils.getToken());
	}

	@Test
	public void snapshotXYTest() throws InterruptedException {
		String imgLocation = PageUtils.getSnapShot(new int[] { 973, 641 }, new int[] { 1304, 655 });
		System.out.println("image location:" + imgLocation);
	}

	/*
	 * If one class property is static, all its instances can access the static
	 * value. XX static property can be accessed by Classname.propertyname directly
	 */
	@Test
	public void testPageInfo() {

		ScreenImage iPixel = new ScreenImage();

		PageInfo.setPageRGB(iPixel.getImagePixel("E:\\temp\\1.png"));

		System.out.println(PageInfo.getPageRGB()[0][1]);

	}

	@Test
	public void OCRUtilsTest() {

		// Integer res = OCRUtils.readImgToText("samples/feifei.png");

		OCRUtils.readImgHD("E:\\temp\\2018-11-12_121106_new.png", 2);

	}

	@Test
	public void pageUtilsTest() {

		while (true) {

			Integer[] xyRes = PageUtils.locateCourseXY();

			if (xyRes == null) {
				System.out.println("terminating gesture found, terminating...");
				break;
			}

			try {
				Robot robot = new Robot();

				Thread.sleep(2000);

				robot.mouseMove(xyRes[0], xyRes[1]);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);

			} catch (AWTException | InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void getCurrentCursorPosition() {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Point p = MouseInfo.getPointerInfo().getLocation();

		System.out.println(p.x + "," + p.y);

	}

}
