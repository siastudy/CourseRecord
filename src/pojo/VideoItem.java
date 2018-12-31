package pojo;

public class VideoItem {
	
	private boolean isSelected = false;
	
	private int itemNumber = 0;

	private int[] startPoint = new int[2];

	private int[] endPoint = new int[2];
	
	private int[] startLine = new int[2];
	
	private int[] endLine = new int[2];

	private int videoLength = 0;

	private String videoName = null;
	
	public VideoItem(int itemNumber, int[] startPoint, int[] endPoint, boolean isSelected) {
		this.itemNumber = itemNumber;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.isSelected = isSelected;
	}
	

	public int[] getStartLine() {
		return startLine;
	}

	public void setStartLine(int[] startLine) {
		this.startLine = startLine;
	}

	public int[] getEndLine() {
		return endLine;
	}

	public void setEndLine(int[] endLine) {
		this.endLine = endLine;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public int[] getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(int[] startPoint) {
		this.startPoint = startPoint;
	}

	public int[] getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(int[] endPoint) {
		this.endPoint = endPoint;
	}

	public int getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(int videoLength) {
		this.videoLength = videoLength;
	}
	
	
	
	
	public boolean getIsSelected() {
		return isSelected;
	}


	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}


	public String toString() {
		
		String res = "itemNumber:"+itemNumber+"\n"
				+ "startPoint:(" +startPoint[0]+","+startPoint[1]+")"+"\n"
				+"endPoint:("+endPoint[0]+","+endPoint[1]+")"+"\n"
				+"startLine("+startLine[0]+","+startLine[1]+")"+"\n"
				+"endLine("+endLine[0]+","+endLine[1]+")"+"\n"
				+"videoLength:"+videoLength+"\n"
				+"videoName:"+videoName;
		return res;
	}
}
