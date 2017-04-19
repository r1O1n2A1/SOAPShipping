package fr.afcepf.atod.ws.soap.shipping.impl;

import java.io.IOException;
import java.util.Map;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import fr.afcepf.atod.ws.soap.shipping.api.ISoapShippingService;
import fr.afcepf.atod.ws.soap.shipping.util.ConstantUtiles;
import fr.afcepf.atod.ws.soap.shipping.util.RESTUtil;

@WebService(targetNamespace="shipping.soap.ws.atod.afcepf.fr",
endpointInterface="fr.afcepf.atod.ws.soap.shipping.api.ISoapShippingService")
public class SoapShippingService implements ISoapShippingService {
	private static Logger logger = Logger.getLogger(SoapShippingService.class);
	@Override
	public void orderShipping(Map<String, String> detailsOrder) throws Exception {
		String returnUrl = RESTUtil.postToShippingApp(detailsOrder);
		if (!returnUrl.equalsIgnoreCase(ConstantUtiles.EMPTY_STR)) {
			try {
				RESTUtil.openUrl(ConstantUtiles.URI_SHIPPING_SITE +
						ConstantUtiles.CHAR_REDIRECT +
						returnUrl.replace(ConstantUtiles.SPACE_STR, ConstantUtiles.EMPTY_STR));
			} catch (IOException e) {
				logger.error(e);
			}
		} else {
			throw new Exception("shipping could not be done");
		}
	}

	@Override
	public String followOrderShipping(Map<String, String> detailsOrder) {
		// empty add quartz V2
		return null;
	}

	@Override
	public boolean getIdShippingOrderFromApp(String idShipping) throws Exception {
		try {
			return RESTUtil.isIdShippingFromShippinApp(idShipping);
		}
		catch (Exception ex) {
			logger.error(ex);
			return false;
		}
	}

}
