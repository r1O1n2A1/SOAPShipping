package fr.afcepf.atod.ws.soap.shipping.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.ws.rs.ClientErrorException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

public final class  RESTUtil {
	private static Logger logger = Logger.getLogger(RESTUtil.class);
	
	private RESTUtil() {
		//empty on
	}

	public static void goToShippingApp()  {
		try {
			ConstantUtiles.openUrl(ConstantUtiles.URI_SHIPPING_SITE);
		} catch (IOException e) {
			logger.info(e);
		}	
	}

	public static String postToShippingApp(Map<String,String> infos) {
		String returnStatusShipping = "";
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String paramsToStr = createStringFromOrderDetails(infos);
//		HttpPost post = new HttpPost(ConstantUtiles.URI_SHIPPING_SITE_FROM_WINE);
		HttpGet get = new HttpGet(ConstantUtiles.URI_SHIPPING_SITE_FROM_WINE + paramsToStr);
		
		try {
//			StringEntity params = new StringEntity(paramsToStr);
//			post.addHeader("content-type","application/json");
//			post.setEntity(params);
			get.addHeader("content-type","application/json");
//			HttpResponse response = httpClient.execute(post);
			HttpResponse response = httpClient.execute(get);

			if (response.getStatusLine().getStatusCode() < 200 
					|| response.getStatusLine().getStatusCode() > 300) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(
					new InputStreamReader((response.getEntity().getContent())));

			String output;
			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				logger.info(output);
			}
			returnStatusShipping = response.getStatusLine().toString();
			httpClient.close();
			br.close();
		} catch (UnsupportedEncodingException | ClientProtocolException e) {
			logger.info(e);
		
		} catch (IOException e) {
			logger.error(e);
		}
		return returnStatusShipping;

	}

	private static String createStringFromOrderDetails(Map<String, String> infos) {
		String strOrderDetails = "coucoutoi";
//		for (String str : infos.keySet()) {
//			strOrderDetails = str + "|";
//		}
		return strOrderDetails;
	}
}
