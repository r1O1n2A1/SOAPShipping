package fr.afcepf.atod.ws.soap.shipping.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public class QuartzJobScheduler {
	private static Logger logger = Logger.getLogger(QuartzJobScheduler.class);
	private int timer ;
	public QuartzJobScheduler(){
		// empty constructor
	}
	
	public void fireJob() {
		// create scheduler
		SchedulerShippingProgress schedulerClass = new SchedulerShippingProgress();
		Scheduler scheduler = schedulerClass.createScheduler();
		schedulerClass.setStartScheduler(scheduler);
		// define the job
		JobDetail jobDetail = JobBuilder.newJob(ShippingJob.class)
				.withIdentity("shippingJob", "afcepf")
				.build();
		// define trigger
		ShippingTrigger shippingTriggerClass = new ShippingTrigger();
		Trigger trigger = shippingTriggerClass.createTrigger(timer);
		// tell quartz to schedule our job using our trigger
		try {
			scheduler.scheduleJob(jobDetail,trigger);
		} catch (SchedulerException e) {
			logger.error(e);
		}
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}	
}
