package fr.afcepf.atod.ws.soap.shipping;
import org.junit.Test;

import fr.afcepf.atod.ws.soap.shipping.impl.SoapShippingService;
import fr.afcepf.atod.ws.soap.shipping.util.RESTUtil;

public class ShippingTest {
	private SoapShippingService service = new SoapShippingService();
	
	@Test
	public void testNominal() {
		service.orderShipping(null);
	}	
}
