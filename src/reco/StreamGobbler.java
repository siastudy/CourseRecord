package reco;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class StreamGobbler extends Thread{
	
	
	InputStream is;
	String type;
	OutputStream os;
	
	
	//Constructor
	public StreamGobbler(InputStream is, String type){
		this.is = is;
		this.type = type;
		this.os = null;
	}
	
	
	//Constructor
	public StreamGobbler(InputStream is, String type, OutputStream os){
		this.is = is;
		this.type = type;
		this.os = os;
	}
	
	
	public void run() {
		
		PrintWriter pw = null;
		if(os != null) {
			pw = new PrintWriter(os);
		}
		
		
		InputStreamReader isr  = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		
		
		try {
			while((line = br.readLine()) != null) {
				
				if(pw != null) {
					pw.print(line);
				}
				System.out.println(type+">"+line);
			}
			if(pw != null) {
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
	}
	

}
