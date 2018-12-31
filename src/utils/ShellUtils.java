package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import reco.StreamGobbler;

public class ShellUtils {

	private static final Logger logger = Logger.getLogger(ShellUtils.class);

	/*
	 * Invoke shell and get return result.
	 */
	public static int doInvoke(String[] cmd) {

		System.out.println("command to run:" + cmd);

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

			String res;

			while ((res = br.readLine()) != null) {
				System.out.println("res:" + res);
			}

			// Check if got any error
			int exitVal = p.waitFor();

			System.out.println("ExitValue:" + exitVal);
			
			return exitVal;

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return -1;

	}

	/**
	 * Run process builder with process builder. String[]: {"sh", "-c", "sh
	 * scriptname.sh param1 param2"}
	 */
	public static int pbRun(String[] cmd) {

		List<String> cmdList = new ArrayList<>();

		for (String str : cmd) {

			cmdList.add(str);
		}

		ProcessBuilder pb = new ProcessBuilder();

		pb.command(cmdList);

		Process p;

		try {
			p = pb.start();

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

			// Get error output
			StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERR");

			// Get info output
			StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

			// Kick them off
			errorGobbler.start();
			outputGobbler.start();

			// StringBuilder out = new StringBuilder();

			// String line = null;
			//
			// while ((line = br.readLine()) != null) {
			// out.append(line);
			// }

			// String shellRes = out.toString();

		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;

	}

}
