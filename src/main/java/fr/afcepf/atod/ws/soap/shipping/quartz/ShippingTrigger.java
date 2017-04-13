package fr.afcepf.atod.ws.soap.shipping.quartz;

import org.apache.log4j.Logger;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class ShippingTrigger {
	private static Logger logger = Logger.getLogger(ShippingTrigger.class);
	public ShippingTrigger() {
		//empty
	}
	public Trigger createTrigger(int minutes) {
		return TriggerBuilder.newTrigger()
				.withIdentity("shippinTrigger","afcepf")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInMinutes(minutes).repeatForever())
				.build();
	}
}
