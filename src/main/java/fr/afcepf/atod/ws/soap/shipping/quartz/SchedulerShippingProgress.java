package fr.afcepf.atod.ws.soap.shipping.quartz;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerShippingProgress {
	private static Logger logger = Logger.getLogger(SchedulerShippingProgress.class);
	public SchedulerShippingProgress() {
		//empty
	}
	
	// create a schedule
	public Scheduler createScheduler() {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			logger.error(e);
		}
		return scheduler;
	}
	
	public void setStartScheduler(Scheduler scheduler) {
		if (scheduler != null) {
			try {
				scheduler.start();
			} catch (SchedulerException e) {
				logger.error(e);
			}
		}
	}
}
