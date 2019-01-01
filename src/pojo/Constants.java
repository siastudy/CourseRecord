package pojo;

public class Constants {

	public static final int[] PLAY_BUTTON_XY = { 290,505 };

	public static final int ALLOW_RANGE = 10;

	public static final int GET_XY_INTERVAL = 50;

	public static final String APP_ID = "14***07";
	public static final String API_KEY = "yYIgGTHp****Txa69G";
	public static final String SECRET_KEY = "ncA******ubn";

	public static final String SCREENSHOT_NAME = "screenshot_time.png";

	public static final String SCREENSHOT_LOCATION = "/home/vincent/jiayu-video/snapshots";

	public static final String SCREENSHOT_FORMAT = "PNG";

	public static final String AUDIO_FILE_LOCATION = "/home/vincent/jiayu-video/audio-temp";

	public static final String AUDIO_FILE_NAME = "baidu_out_time";

	public static final String AUDIO_FILE_FORMAT = "mp3";

	// startPoint -2, (216,216,216)
	// dell == acer
	public static final String COURSE_HEAD_RING_LINE = "#D8D8D8";

	// boundary of the circle, START point, (102,102,102)
	// dell == acer
	public static final String COURSE_HEAD_RING_ROUND = "#666666";

	// point of the outer boundary, startPoint -1, (104,104,104)
	// dell pc:public static final String COURSE_HEAD_RING_OUTER_BOUND = "#686868";
	// acer pc: (102,102,102)
	public static final String COURSE_HEAD_RING_OUTER_BOUND = "#666666";

	// point of the inner boundary startPoint +1, (251,251,251)
	// dell:public static final String COURSE_HEAD_RING_INNER_BOUND = "#FBFBFB";
	// acer: 255, 255,255
	//DENOVO 255255255
	public static final String COURSE_HEAD_RING_INNER_BOUND = "#FFFFFF";

	// px space between one course from the next one
	public static final int RING_DISTANCE = 35;

	// white background color: (255,255,255)
	public static final String PAGE_BACKGROUND_WHITE = "#FFFFFF";

	// scroll bar background(241,241,241)
	// dell: public static final String SCROLL_BAR_BACKGROUND = "#F1F1F1";
	// acer:(195,196,196)
	public static final String SCROLL_BAR_BACKGROUND = "#C3C4C4";

	// acer only: 182,182,179
	//ALSO DENOVO1
	public static final String SCROLL_BAR_BORDER = "#B6B6B3";

	// video length area top-left point
	public static final int[] VIDEO_LENGTH_TOP_LEFT = { 274, 714 };

	// video length area down-right point
	public static final int[] VIDEO_LENGTH_DOWN_RIGHT = { 396, 745 };
	
	
	//PLAY BUTTON IN THE MIDDLE
	public static final int[] CENTER_TRI_ANGLE= {290,505};

	// Location where recorded video saves
	public static final String RECORD_VIDOE_SAVE_PATH = "/home/vincent/jiayu-video/videos";

	// scroll mouse middle button for more lines
	public static final int SCROLL_AMOUNT = 6;

	// 255,241,241
	//245236237
	public static final String SELECTED_PINK_BG = "#F5ECED";
	
	public static final String VIDEO_RECORD_SCRIPT="/home/vincent/os-scripts/record-video.sh";
	
	public static final String CHECK_FFMEPG_RUNNING_STATUS="/home/vincent/os-scripts/check-ffmpeg-running.sh";
	
	public static final String VIDEO_RECORD_LOCATION = "/home/vincent/jiayu-video";
	
	
	public static final String FFMPEG_COMMAND_TEMPLATE = "ffmpeg -video_size WIDTH x HEIGHT  -framerate 25 -f x11grab -i :0.0 -f pulse -i alsa_output.pci-0000_00_1b.0.analog-stereo.monitor -c:a libmp3lame -t LENGTH LOCATION/FILENAME";

}
