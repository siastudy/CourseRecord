package utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import pojo.Constants;

public class HttpUtils {
	
	private static final Logger logger = Logger.getLogger(HttpUtils.class);
	
	public static String getRequest(String addr, String[][] params) {
		 
	    CloseableHttpClient httpClient = HttpClients.createDefault();
	 
	    String entityStr = null;
	    CloseableHttpResponse response = null;
	 
	    try {
	        URIBuilder uriBuilder = new URIBuilder(addr);
	        /*uriBuilder.addParameter("name", "root");
	        uriBuilder.addParameter("password", "123456");*/
	        List<NameValuePair> list = new LinkedList<>();
	        
	        for(int i = 0; i < params.length; i++) {
	        	
	        	list.add(new BasicNameValuePair(params[i][0], params[i][1]));
	        	
	        }
	        
	        uriBuilder.setParameters(list);
	 
	        HttpGet httpGet = new HttpGet(uriBuilder.build());
	 
	        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
	        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
	 
	        response = httpClient.execute(httpGet);
	        HttpEntity entity = response.getEntity();
	        
	        System.out.println("Result Content Type......");
	        System.out.println("entity.getContentType().getName()"+entity.getContentType().getName());
	        System.out.println("entity.getContentType().getValue()"+entity.getContentType().getValue());
	        System.out.println("Result Content Type......");

	        String resultType = entity.getContentType().getValue();
	        
	        if("application/json".equals(resultType)) {
		        entityStr = EntityUtils.toString(entity, "UTF-8");
		        System.out.println("entityStr:"+entityStr);
		        return entityStr;
	        	
	        } else if("audio/mp3".equals(resultType)) {
	        	
	        	String outputFilePath = Constants.AUDIO_FILE_LOCATION+"\\"+Constants.AUDIO_FILE_NAME.replace("time", System.currentTimeMillis()+".mp3");
	        	
	        	File outFile = new File(outputFilePath);
	        	
	        	FileUtils.copyInputStreamToFile(entity.getContent(), outFile);
	        	
	        	if(outFile.exists()) {
	        		return outputFilePath;
	        	}
	        }
	    } catch (ClientProtocolException e) {
	        logger.error("ClientProtocolException");
	        e.printStackTrace();
	    } catch (ParseException e) {
	    	logger.error("ParseException");
	        e.printStackTrace();
	    } catch (URISyntaxException e) {
	    	logger.error("URISyntaxException");
	        e.printStackTrace();
	    } catch (IOException e) {
	    	logger.error("IOException");
	        e.printStackTrace();
	    } finally {
	        if (null != response) {
	            try {
	                response.close();
	                httpClient.close();
	            } catch (IOException e) {
	            	logger.error("IOException");
	                e.printStackTrace();
	            }
	        }
	    }
	    return entityStr;
	}
}
