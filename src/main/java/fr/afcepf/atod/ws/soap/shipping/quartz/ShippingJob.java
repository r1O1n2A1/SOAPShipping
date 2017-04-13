package fr.afcepf.atod.ws.soap.shipping.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import fr.afcepf.atod.ws.soap.shipping.util.RESTUtil;

public class ShippingJob implements Job {
	private static Logger logger = Logger.getLogger(ShippingJob.class);
	@Override
	public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
		logger.info("------------------------------------");
		logger.info("Shipping job start: " + jobCtx.getFireTime());
		JobDetail jobDetail = jobCtx.getJobDetail();
		logger.info("MyJob end: " + jobCtx.getJobRunTime() + ", key: " + jobDetail.getKey());
		logger.info("MyJob next scheduled time: " + jobCtx.getNextFireTime());
		logger.info("--------------------------------------------------------------------");
		
		RESTUtil.goToShippingApp();
	
	}

}
