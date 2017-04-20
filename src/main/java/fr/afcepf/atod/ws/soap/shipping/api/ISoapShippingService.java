package fr.afcepf.atod.ws.soap.shipping.api;

import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace="shipping.soap.ws.atod.afcepf.fr")
public interface ISoapShippingService {
	@WebMethod(operationName="setShipping")
	@WebResult(name="priceShipping")
	String orderShipping(@WebParam(name="detailsOrder")Map<String,String> detailsOrder)
		throws Exception;
	@WebMethod(operationName="followOrder")
	@WebResult(name="statusShippingOrder")
	String followOrderShipping(@WebParam(name="detailOrder")Map<String,String> detailsOrder);
	@WebMethod(operationName="getIdShipping")
	@WebResult(name="isExistingIdShipping")
	boolean getIdShippingOrderFromApp(@WebParam(name = "idShipping")String idShipping) throws Exception;
}
