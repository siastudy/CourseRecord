package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CharacterRep {

	public static void main(String[] args) {

		String file0Path = "E:\\temp\\sample.txt";
		String file1Path = "E:\\temp\\sample1.txt";
		String file2Path = "E:\\temp\\sample2.txt";
		String file3Path = "E:\\temp\\sample3.txt";
		String file4Path = "E:\\temp\\sample4.txt";
		String file5Path = "E:\\temp\\sample5.txt";

		File file0 = new File(file0Path);
		File file1 = new File(file1Path);
		File file2 = new File(file2Path);
		File file3 = new File(file3Path);
		File file4 = new File(file4Path);
		File file5 = new File(file5Path);

		// step 1: replace ")(" with Tab
		toReplace(file0, file1, ")(", "\t");

		// step 2: remove first 4 characters
		if (file1.exists()) {
			rmPrefix(file1, file2, "(");
		}

		// step 3: remove "("
		if (file2.exists()) {
			removeChar(file2, file3, "(", "");
		}

		// step 3: rmove "("
		if (file3.exists()) {
			removeChar(file3, file4, ")", "");
		}
		
		if(file4.exists()) {
			removeChar(file4, file5, ",", "");
		}

	}

	/*
	 * Remove 1st several characters
	 */
	private static void rmPrefix(File srcFile, File tarFile, String toRm) {

		BufferedReader br;
		BufferedWriter bw;
		try {
			 br = new BufferedReader(new FileReader(srcFile));
			 bw = new BufferedWriter(new FileWriter(tarFile));
			String lineRes;
			while ((lineRes = br.readLine()) != null) {

				bw.append(lineRes, lineRes.indexOf(toRm), lineRes.length());
				bw.append("\n");
			}

			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void removeChar(File srcFile, File tarFile, String oldChar, String newChar) {

		BufferedReader br;
		BufferedWriter bw;
		try {
			br = new BufferedReader(new FileReader(srcFile));
			bw = new BufferedWriter(new FileWriter(tarFile));
			String lineRes;
			while ((lineRes = br.readLine()) != null) {
				if (lineRes.contains(oldChar)) {
					bw.append(lineRes.replace(oldChar, ""));
					bw.append("\n");
				}
			}
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void toReplace(File srcFile, File tarFile, String oldStr, String newStr) {

		BufferedReader br;
		BufferedWriter bw;
		
		try {
			br = new BufferedReader(new FileReader(srcFile));
			bw = new BufferedWriter(new FileWriter(tarFile));
			String lineRes;
			while ((lineRes = br.readLine()) != null) {

				if (lineRes.contains(oldStr)) {
					bw.append(lineRes.replace(oldStr, newStr));
					bw.append("\n");
				}
			}
			bw.flush();
			bw.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
