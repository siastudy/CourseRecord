package utils;

import java.awt.Color;

public class ColorUtils {
	
	/**
	 * Convert (255,255,255) style color to Hex value
	 * */
	public static String toHexFromColor(int[] color){
		String r,g,b;
		StringBuilder su = new StringBuilder();
		r = Integer.toHexString(color[0]);
		g = Integer.toHexString(color[1]);
		b = Integer.toHexString(color[2]);
		r = r.length() == 1 ? "0" + r : r;
		g = g.length() ==1 ? "0" +g : g;
		b = b.length() == 1 ? "0" + b : b;
		r = r.toUpperCase();
		g = g.toUpperCase();
		b = b.toUpperCase();
		su.append("#");
		su.append(r);
		su.append(g);
		su.append(b);
		//0xFF0000FF
		return su.toString();
	}
	
	
	public static Color toColorFromString(String colorStr){
		colorStr = colorStr.substring(4);
		Color color =  new Color(Integer.parseInt(colorStr, 16)) ;
		//java.awt.Color[r=0,g=0,b=255]
		return color;
	}
}
