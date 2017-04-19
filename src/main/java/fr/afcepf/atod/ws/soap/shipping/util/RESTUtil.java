package fr.afcepf.atod.ws.soap.shipping.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
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


	/**
	 * CAN BE DONE WITH A PUT ?
	 * Un lapin et un renard sont dans un terrier...
	 * Le lapin a tres faim... tout comme le renard..
	 * @param infos
	 * @return
	 */
	public static String postToShippingApp(Map<String,String> infos) {
		String returnStatusShipping = ConstantUtiles.EMPTY_STR;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String paramsToStr = createStringFromOrderDetails(infos);
		String hashUrl = encodingUrlToShippingApp(paramsToStr);
		HttpGet get = new HttpGet(ConstantUtiles.URI_SHIPPING_SITE_FROM_WINE + hashUrl);

		try {
			get.addHeader("content-type","application/json");
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
				if(output.contains(ConstantUtiles.RESPONSE_ID_COMMAND)) {
					returnStatusShipping = output.split(",")[ConstantUtiles.NUMBER_ONE]
							.split(ConstantUtiles.POINT_STR)[ConstantUtiles.NUMBER_ONE];
				}
			}
			httpClient.close();
			br.close();
		} catch (UnsupportedEncodingException | ClientProtocolException e) {
			logger.info(e);

		} catch (IOException e) {
			logger.error(e);
		}
		return returnStatusShipping;

	}
	
	/**
	 * Check is a shipping id has been 
	 * previously created
	 * @param idShipping
	 * @return
	 */
	public static boolean isIdShippingFromShippinApp(String idShipping) {
		String returnStatusShipping = ConstantUtiles.EMPTY_STR;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String hashUrl = encodingUrlToShippingApp(idShipping);
		HttpGet get = new HttpGet(ConstantUtiles.URI_SHIPPING_SITE_FROM_WINE + 
				ConstantUtiles.URI_SHIPPING + hashUrl);
		get.addHeader("content-type","application/json");
		HttpResponse response;
		try {
			response = httpClient.execute(get);
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
				if(output.contains(idShipping + ConstantUtiles.SPACE_STR + 
						ConstantUtiles.IS_SHIPPING)) {
					returnStatusShipping = idShipping + ConstantUtiles.SPACE_STR + 
							ConstantUtiles.IS_SHIPPING;
				}
			}
			httpClient.close();
			br.close();
		} catch (UnsupportedEncodingException | ClientProtocolException e) {
			logger.info(e);
		} catch (IOException e) {
			logger.error(e);
		}		
		if (returnStatusShipping.equalsIgnoreCase(idShipping 
				+ ConstantUtiles.SPACE_STR 
				+ ConstantUtiles.IS_SHIPPING)){
			return true;
		}
		return false;
	}

	/**
	 * Change infos sent to the Shipping app through REST WS
	 * @param infos
	 * @return
	 */
	private static String createStringFromOrderDetails(Map<String, String> infos) {
		StringBuilder strOrderDetails = new StringBuilder();
		strOrderDetails.append(ConstantUtiles.EMPTY_STR);
		if (infos != null) {
			for (Entry<String, String> str: infos.entrySet()) {
				strOrderDetails.append("_" + str.getKey() + ConstantUtiles.POINT_STR +str.getValue());
			}
		} else {
			strOrderDetails.append("emptyURL");
		}
		String retour = strOrderDetails.toString().replace("|", "&");
		return retour.replace(ConstantUtiles.SPACE_STR, ConstantUtiles.DOLLAR_STR);

	}

	/**
	 * encode URL for security purpose
	 * @param encodedTarget
	 * @return
	 */
	private static String encodingUrlToShippingApp(String encodedTarget) {
		// encode data on your side using BASE64
		String   strEncoded = Base64.getEncoder().encodeToString(encodedTarget.getBytes());
		logger.info("encoded value is " + strEncoded );
		return strEncoded;
	}

	// open browser from java code according to the OS
	public static void openUrl(String url) throws IOException {
		String os = getSystemOS();		
		if (os.indexOf(ConstantUtiles.WINDOWS) >= 0) {
			// windows
			Runtime rt = Runtime.getRuntime();
			rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
		} else if (os.indexOf(ConstantUtiles.MAC) >= 0){
			// mac
			Runtime rt = Runtime.getRuntime();
			rt.exec( "open" + url);
		} else if (os.indexOf(ConstantUtiles.LINUX) >= 0 || 
				os.indexOf(ConstantUtiles.UNIX) >=0 ){
			// linux
			Runtime rt = Runtime.getRuntime();
			String[] browsers = {"epiphany", "konqueror",
					"netscape","opera","links","lynx","chromium-browser",
					"firefox", "mozilla",};

			StringBuffer cmd = new StringBuffer();
			for (int i=0; i<browsers.length; i++)
				cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");

			rt.exec(new String[] { "sh", "-c", cmd.toString() });
		} else {
			return;
		}
	}

	public static String getSystemOS() {
		return System.getProperty("os.name").toLowerCase();
	}
}
