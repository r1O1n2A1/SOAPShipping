package fr.afcepf.atod.ws.soap.shipping.impl;

import java.util.Map;

import javax.jws.WebService;

import fr.afcepf.atod.ws.soap.shipping.api.ISoapShippingService;
import fr.afcepf.atod.ws.soap.shipping.util.RESTUtil;

@WebService(targetNamespace="shipping.soap.ws.atod.afcepf.fr",
		endpointInterface="fr.afcepf.atod.ws.soap.shipping.api.ISoapShippingService")
public class SoapShippingService implements ISoapShippingService {

	@Override
	public void orderShipping(Map<String, String> detailsOrder) {
		RESTUtil.postToShippingApp(detailsOrder);
	}

	@Override
	public String followOrderShipping(Map<String, String> detailsOrder) {
		return null;
	}

}
