package fr.afcepf.atod.ws.soap.shipping;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import fr.afcepf.atod.ws.soap.shipping.quartz.QuartzJobScheduler;

public class QuartzShippingTest {
	private static Logger logger = Logger.getLogger(QuartzShippingTest.class);
	public QuartzShippingTest() {
		//empty
	}
	@Ignore
	@Test
	public void testShippingJob() {
		QuartzJobScheduler jobToLaunch = new QuartzJobScheduler();
		jobToLaunch.setTimer(2);
		jobToLaunch.fireJob();
	}
}
