package test;

import org.junit.Test;

import pojo.PageInfo;
import pojo.PageRGB;

public class BaseTest {
	
	@Test
	public void pageInfoTest() {
		
		PageInfo info = new PageInfo();
		
		System.out.println("info.getPlayBtn():\n"+info.getPlayBtn()[0]+","+info.getPlayBtn()[1]);
		System.out.println("info.getTextAreaXY():\n"+info.getTextAreaXY()[0][0]+","+info.getTextAreaXY()[0][1]+"\n"+info.getTextAreaXY()[1][0]+","+info.getTextAreaXY()[1][1]);
	}
	
	
	

	
	@Test
	public void test2DArrayLength() {

		PageRGB[][] pageRGB = new PageRGB[300][10];

		pageRGB[0][0] = new PageRGB(1, 2, 3);
		pageRGB[0][1] = new PageRGB(1, 2, 3);
		pageRGB[0][2] = new PageRGB(1, 2, 3);
		pageRGB[0][3] = new PageRGB(1, 2, 3);
		pageRGB[0][4] = new PageRGB(1, 2, 3);

		pageRGB[1][0] = new PageRGB(1, 2, 3);
		pageRGB[1][1] = new PageRGB(1, 2, 3);
		pageRGB[1][2] = new PageRGB(1, 2, 3);
		pageRGB[1][3] = new PageRGB(1, 2, 3);
		pageRGB[1][4] = new PageRGB(1, 2, 3);

		System.out.println(pageRGB.length);
		System.out.println(pageRGB[0].length);
	}
	
	
	/*
	 * Find length of the array after make it to null
	 * */
	@Test
	public void testArrayEmpty() {
		
		int[] array1 = new int[2];
		array1[0] = 1;
		array1[1] = 1;
		
		System.out.println("arrayLength:"+array1.length);
		System.out.println("array[0]:"+array1[0]);
		System.out.println("array[1]:"+array1[1]);
		
		

		int[] array2 = new int[2];
		
		System.out.println("arrayLength:"+array2.length);
		System.out.println("array[0]:"+array2[0]);
		System.out.println("array[1]:"+array2[1]);
		
		
		
		
		
		
		
	}

}
