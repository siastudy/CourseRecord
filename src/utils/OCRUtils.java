package utils;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

import pojo.Constants;

public class OCRUtils {

	private static final Logger logger = Logger.getLogger(OCRUtils.class);

	private static AipOcr client = new AipOcr(Constants.APP_ID, Constants.API_KEY, Constants.SECRET_KEY);

	/**
	 */
	public static String readImgHD(String imgName, int type) {
		HashMap<String, String> options = new HashMap<String, String>();
		// options.put("detect_direction", "true");
		// options.put("probability", "true");

		String image = imgName;
		JSONObject res = client.basicAccurateGeneral(image, options);

		try {
			JSONArray jsonArray = (JSONArray) res.get("words_result");
			JSONObject jsonObject = jsonArray.getJSONObject(0);

			if (type == 1) {

				try {
					String videoLengthStr = jsonObject.getString("words").split("\\/")[1].substring(0, 8);
					return String.valueOf(TimeUtils.getMinutesFromHMS(videoLengthStr));
				} catch (StringIndexOutOfBoundsException e) {
					String videoLengthStr = jsonObject.getString("words").split("\\/")[1].substring(0, 5);

					logger.info("OCR result: String form of video time" + jsonObject.getString("words"));

					return String.valueOf(TimeUtils.getMinutesFromHMS(videoLengthStr));
				}
			} else if (type == 2) {
				String recoRes = jsonObject.getString("words_result");
				return recoRes;
			}

		} catch (JSONException | ArrayIndexOutOfBoundsException e) {
			System.out.println("did not find result from baidu return.");
			System.out.println("baidu first result:" + res);
		}
		return null;
	}

	/**
	 * 读取图片成文字, 普通精度
	 */
	public static String readImg(String imgName, int type) {

		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		HashMap<String, String> options = new HashMap<String, String>();
		options.put("language_type", "CHN_ENG");
		// options.put("detect_direction", "true");
		// options.put("detect_language", "true");
		// options.put("probability", "true");

		String courseName;

		JSONObject res = client.basicGeneral(imgName, options);

		JSONArray jsonArray = (JSONArray) res.get("words_result");

		courseName = (String) jsonArray.getJSONObject(0).get("words");

		return courseName;
	}

}
