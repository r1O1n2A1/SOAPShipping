package fr.afcepf.atod.ws.soap.shipping;
import org.junit.Test;

import fr.afcepf.atod.ws.soap.shipping.impl.SoapShippingService;
import fr.afcepf.atod.ws.soap.shipping.util.RESTUtil;

public class ShippingTest {
	private SoapShippingService service = new SoapShippingService();
	private static final int KEY_ADDRESS = 1;

	@Test(expected = Exception.class)
	public void testNominal() throws Exception {
		service.orderShipping(null);
	}	
	
	@Test
	public void testIsShippingNominalCase() throws Exception {
		service.getIdShippingOrderFromApp(String.valueOf(KEY_ADDRESS));
	}
}
