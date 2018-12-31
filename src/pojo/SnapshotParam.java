package pojo;

public class SnapshotParam {

	/*
	 * x,y x1,y1 location orifilname format
	 */
	private int[] topLeft;
	private int[] downRight;
	private String fileLocation;
	private String filenamePattern;
	private String imgFormat;
	
	//Constructor to initialize params from Constants config file when new instance
	public SnapshotParam(){
		this.fileLocation = Constants.SCREENSHOT_LOCATION;
		this.filenamePattern = Constants.SCREENSHOT_NAME;
		this.imgFormat = Constants.SCREENSHOT_FORMAT;
	}
	
	public int[] getTopLeft() {
		return topLeft;
	}
	public void setTopLeft(int[] topLeft) {
		this.topLeft = topLeft;
	}
	public int[] getDownRight() {
		return downRight;
	}
	public void setDownRight(int[] downRight) {
		this.downRight = downRight;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getFilenamePattern() {
		return filenamePattern;
	}
	public void setFilenamePattern(String filenamePattern) {
		this.filenamePattern = filenamePattern;
	}
	public String getImgFormat() {
		return imgFormat;
	}
	public void setImgFormat(String imgFormat) {
		this.imgFormat = imgFormat;
	}

}
