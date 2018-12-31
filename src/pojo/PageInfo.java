package pojo;

public class PageInfo {
	
	private static int[] playBtn;

	private static int[][] textAreaXY;

	private static int ringLineX = 0;

	private static String[][] pageRGB;
	
	private static int[] pageSize;

	static {
		System.out.println("This is static block.");
		textAreaXY = new int[][] { { Constants.VIDEO_LENGTH_TOP_LEFT[0], Constants.VIDEO_LENGTH_TOP_LEFT[1] },
				{ Constants.VIDEO_LENGTH_DOWN_RIGHT[0], Constants.VIDEO_LENGTH_DOWN_RIGHT[1], } };
		playBtn = Constants.PLAY_BUTTON_XY;
	}

	public static String[][] getPageRGB() {
		return pageRGB;
	}

	public static void setPageRGB(String[][] pageRGB) {
		PageInfo.pageRGB = pageRGB;
	}

	public static int[] getPlayBtn() {
		return playBtn;
	}

	public static void setPlayBtn(int[] playBtn) {
		PageInfo.playBtn = playBtn;
	}

	public static int[][] getTextAreaXY() {
		return textAreaXY;
	}

	public static void setTextAreaXY(int[][] textAreaXY) {
		PageInfo.textAreaXY = textAreaXY;
	}

	public static int getRingLineX() {
		return ringLineX;
	}

	public static void setRingLineX(int ringLineX) {
		PageInfo.ringLineX = ringLineX;
	}

	public static int[] getPageSize() {
		return pageSize;
	}

	public static void setPageSize(int[] pageSize) {
		PageInfo.pageSize = pageSize;
	}

}
