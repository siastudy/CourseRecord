package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.json.JSONObject;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class VoiceUtils {

	private static String token;

	public static String getToken() {

		String tokenAddr = "https://openapi.baidu.com/oauth/2.0/token";

		String[][] params = new String[][] { new String[] { "grant_type", "client_credentials" },
				new String[] { "client_id", "yxRYZVtLVWf4kOgRRsAYooIu" },
				new String[] { "client_secret", "lpN4HVAi35AFQnonRsrzziGj2gQ1hD4k" } };

		String res = HttpUtils.getRequest(tokenAddr, params);

		return new JSONObject(res).getString("access_token");

	}

	public static void getVoice(String text) {

		String reqPath = "http://tsn.baidu.com/text2audio";
		
		//TODO remove after testing
		token = "24.3fce7911002bf7bb250a78b9a59b62b8.2592000.1544415449.282335-11624256";

		String[][] params = new String[][] { 
				new String[] { "lan", "zh" },
				new String[] { "ctp", "1" },
				new String[] { "cuid", "abcdxxx" },
				new String[] { "tok", token },
				new String[] { "tex", text },
				new String[] { "vol", "9" },
				new String[] { "per", "0" },
				new String[] { "spd", "5" },
				new String[] { "pit", "5" },
				new String[] { "aue", "3" }
				
		};

		String audioFilePath = HttpUtils.getRequest(reqPath, params);
		
		System.out.println("audio file path is:"+audioFilePath);

		playVoice(audioFilePath);
	}
	
	
	
	public static void playVoice(String voiceFile) {

		Player player;

		BufferedInputStream buffer;
		try {
			if(voiceFile.split("\\.")[1].equals("mp3")) {
				
				buffer = new BufferedInputStream(new FileInputStream(voiceFile));

				player = new Player(buffer);
				player.play();
				
			} else if (voiceFile.split("\\.").equals(".wav")) {
				
				@SuppressWarnings("unused")
				final JFXPanel fxPanel = new JFXPanel();

				Media hit = new Media(new File(voiceFile).toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(hit);
				mediaPlayer.play();
			}
			
		} catch (FileNotFoundException | JavaLayerException e) {
			e.printStackTrace();
		}
	}
	

}
