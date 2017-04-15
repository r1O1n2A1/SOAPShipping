package fr.afcepf.atod.ws.soap.shipping.util;

import java.io.IOException;


public final class ConstantUtiles {

	public static final String URI_SHIPPING_SITE = "http://localhost:8080/";
	public static final String URI_SHIPPING_SITE_FROM_WINE 
		= "http://localhost:8080/wine/order/";
	public static final String WINDOWS = "win";
	public static final String MAC = "mac";
	public static final String LINUX = "nix";
	public static final String UNIX = "nux";
	public static final String EMPTY_STR = "";
	public static final String DOLLAR_STR = "$";
	public static final String SPACE_STR = " ";
	public static final String POINT_STR = ":";
	public static final String RESPONSE_ID_COMMAND = "id: ";
	public static final int NUMBER_ONE = 1;
	public static final String CHAR_REDIRECT = "#/";
	
	private ConstantUtiles() {
		// empty constructor
	}

	
	// open browser from java code according to the OS
	public static void openUrl(String url) throws IOException {
		String os = getSystemOS();		
		if (os.indexOf(WINDOWS) >= 0) {
			// windows
			Runtime rt = Runtime.getRuntime();
			rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
		} else if (os.indexOf(MAC) >= 0){
			// mac
			Runtime rt = Runtime.getRuntime();
			rt.exec( "open" + url);
		} else if (os.indexOf(LINUX) >= 0 || 
				os.indexOf(UNIX) >=0 ){
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
