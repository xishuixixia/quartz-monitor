package com.quartz.monitor.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.TabularDataSupport;

import com.quartz.monitor.object.*;
import org.apache.log4j.Logger;

import com.quartz.monitor.util.JMXUtil;
import com.quartz.monitor.util.QuartzUtil;
import com.quartz.monitor.util.Tools;

/**
 * http://www.quartz-scheduler.org/api/2.0.0/index.html?org/quartz/jobs/ee/jmx/JMXInvokerJob.html
 * @author guolei
 *
 */
public class QuartzJMXAdapterImpl implements QuartzJMXAdapter {

	private static Logger log = Logger.getLogger(QuartzJMXAdapterImpl.class);

	@Override
	public String getVersion(QuartzInstance quartzInstance, ObjectName objectName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Job> getJobDetails(QuartzInstance quartzInstance, Scheduler scheduler) throws Exception {
		List<Job> jobs = null;
		JMXInput jmxInput = new JMXInput(quartzInstance, null, "AllJobDetails", null,
				scheduler.getObjectName());
		TabularDataSupport tdata = null;
		try {
			tdata = (TabularDataSupport) JMXUtil.callJMXAttribute(jmxInput);
		} catch (Exception e){
		}
		if (tdata != null) {
			jobs = new ArrayList<Job>();
			for (Iterator<Object> it = tdata.values().iterator(); it.hasNext();) {
				Object object = (Object) it.next();
				if (!(object instanceof CompositeDataSupport)) {
					continue;
				}
				CompositeDataSupport compositeDataSupport = (CompositeDataSupport) object;
				Job job = new Job();
				job.setSchedulerName(scheduler.getName());
				job.setQuartzInstanceId(scheduler.getQuartzInstanceUUID());
				job.setSchedulerInstanceId(scheduler.getInstanceId());
				job.setJobName((String) JMXUtil.convertToType(compositeDataSupport, "name"));
				log.info("job name:"+job.getJobName());
				job.setDescription((String) JMXUtil.convertToType(compositeDataSupport,"description"));
				job.setDurability(((Boolean) JMXUtil.convertToType(compositeDataSupport,"durability")).booleanValue());
				job.setShouldRecover(((Boolean) JMXUtil.convertToType(compositeDataSupport,"shouldRecover")).booleanValue());
				job.setGroup((String) JMXUtil.convertToType(compositeDataSupport, "group"));
				job.setJobClass((String) JMXUtil.convertToType(compositeDataSupport, "jobClass"));

				// get Next Fire Time for job
				List<Trigger> triggers = this.getTriggersForJob(quartzInstance, scheduler,
						job.getJobName(), job.getGroup());

				if(triggers == null || triggers.size() == 0){
					job.setState("NONE");
				}else{
					job.setState(getTriggerState(quartzInstance,scheduler,triggers.get(0)));
				}

				log.info("job state:"+job.getState());
				try {
					if (triggers != null && triggers.size() > 0) {
						Date nextFireTime = QuartzUtil.getNextFireTimeForJob(triggers);
						job.setNextFireTime(nextFireTime);
						job.setNumTriggers(triggers.size());
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}

				log.debug("Loaded job: " + job);
				jobs.add(job);
			}
		}
		return jobs;
	}

	@Override
	public Scheduler getSchedulerById(QuartzInstance quartzInstance, String scheduleID)
			throws Exception {

		List<Scheduler> list = quartzInstance.getSchedulerList();
		if (list != null && list.size() > 0)
	      {
	         for (int i = 0; i < list.size(); i++)
	         {
	            Scheduler s = (Scheduler) list.get(i);
	            if (s.getInstanceId().equals(scheduleID))
	            {
	               return s;
	            }
	         }
	      }
		return null;
	}

	@Override
	public List<Trigger> getTriggersForJob(QuartzInstance quartzInstance, Scheduler scheduler,
			String jobName, String groupName) throws Exception {

	      List<Trigger> triggers = null;

	      JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{String.class.getName(), String.class.getName()}, "getTriggersOfJob", new Object[]{jobName, groupName}, scheduler.getObjectName());
	      @SuppressWarnings("unchecked")
		 List<CompositeDataSupport> list = (List<CompositeDataSupport>) JMXUtil.callJMXOperation(jmxInput);
	      if (list != null && list.size() > 0)
	      {
	    	 log.info("-------"+jobName+" trigger size:"+list.size());
	         triggers = new ArrayList<Trigger>();
	         for (int i = 0; i < list.size(); i++)
	         {
	            CompositeDataSupport compositeDataSupport = (CompositeDataSupport) list.get(i);
	            Trigger trigger = new Trigger();
	            trigger.setCalendarName((String) JMXUtil.convertToType(compositeDataSupport, "calendarName"));
	            log.info("-------"+jobName+" trigger's calendar name:"+trigger.getCalendarName());
	            trigger.setDescription((String) JMXUtil.convertToType(compositeDataSupport, "description"));
	            trigger.setEndTime((Date) JMXUtil.convertToType(compositeDataSupport, "endTime"));
	            trigger.setFinalFireTime((Date) JMXUtil.convertToType(compositeDataSupport, "finalFireTime"));
	            trigger.setFireInstanceId((String) JMXUtil.convertToType(compositeDataSupport, "fireInstanceId"));
	            trigger.setGroup((String) JMXUtil.convertToType(compositeDataSupport, "group"));
	            trigger.setJobGroup((String) JMXUtil.convertToType(compositeDataSupport, "jobGroup"));
	            trigger.setJobName((String) JMXUtil.convertToType(compositeDataSupport, "jobName"));
	            log.info("-------"+jobName+" trigger's job name:"+trigger.getJobName());
	            trigger.setMisfireInstruction(((Integer) JMXUtil.convertToType(compositeDataSupport, "misfireInstruction")).intValue());
	            trigger.setName((String) JMXUtil.convertToType(compositeDataSupport, "name"));
	            log.info("-------"+jobName+" trigger's  name:"+trigger.getName());
	            trigger.setNextFireTime((Date) JMXUtil.convertToType(compositeDataSupport, "nextFireTime"));
	            log.info("-------"+jobName+" trigger's  nextFireTime:"+trigger.getNextFireTime());
	            trigger.setPreviousFireTime((Date) JMXUtil.convertToType(compositeDataSupport, "previousFireTime"));
	            trigger.setPriority(((Integer) JMXUtil.convertToType(compositeDataSupport, "priority")).intValue());
	            trigger.setStartTime((Date) JMXUtil.convertToType(compositeDataSupport, "startTime"));


	            try
	            {
	               JMXInput stateJmxInput = new JMXInput(quartzInstance, new String[]{String.class.getName(), String.class.getName()}, "getTriggerState", new Object[]{trigger.getName(), trigger.getGroup()}, scheduler.getObjectName());
	               String state = (String) JMXUtil.callJMXOperation(stateJmxInput);
	               trigger.setSTriggerState(state);
	            }
	            catch (Throwable tt)
	            {
	               trigger.setSTriggerState(Trigger.STATE_GET_ERROR);
	            }

	            //删除group为"now"的trigger
	            if(trigger.getGroup().equals("now")){
	            	deleteTrigger(quartzInstance, scheduler, trigger);
	            }else{
	            	 triggers.add(trigger);
	            }
	         }
	      }
	      return triggers;
	}

	@Override
	public void attachListener(QuartzInstance quartzInstance, String scheduleID) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Scheduler getSchedulerByJmx(QuartzInstance quartzInstance, ObjectName objectName)
			throws Exception {
		  Scheduler scheduler = new Scheduler();
	      MBeanServerConnection connection = quartzInstance.getMBeanServerConnection();
	      scheduler.setObjectName(objectName);
	      scheduler.setName((String) connection.getAttribute(objectName, "SchedulerName"));
	      scheduler.setInstanceId((String) connection.getAttribute(objectName, "SchedulerInstanceId"));
	      scheduler.setJobStoreClassName((String) connection.getAttribute(objectName, "JobStoreClassName"));
	      scheduler.setThreadPoolClassName((String) connection.getAttribute(objectName, "ThreadPoolClassName"));
	      scheduler.setThreadPoolSize((Integer) connection.getAttribute(objectName, "ThreadPoolSize"));
	      scheduler.setShutdown((Boolean) connection.getAttribute(objectName, "Shutdown"));
	      scheduler.setStarted((Boolean) connection.getAttribute(objectName, "Started"));
	      scheduler.setStandByMode((Boolean) connection.getAttribute(objectName, "StandbyMode"));
	      scheduler.setQuartzInstanceUUID(quartzInstance.getUuid());
	      scheduler.setVersion(this.getVersion(quartzInstance, objectName));
	      return scheduler;
	}

	@Override
	public void startJobNow(QuartzInstance quartzInstance, Scheduler scheduler,Job job) throws Exception {
		log.info("call start job.......");
		//HashMap map =new HashMap();
		//JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{String.class.getName(), String.class.getName(),"java.util.Map"}, "triggerJob", new Object[]{job.getJobName(), job.getGroup(),map}, scheduler.getObjectName());
		HashMap<String,Object> triggerMap = new HashMap<String,Object>();
		String triggerName = Tools.generateUUID();
		log.info(" start now trigger name  is " + triggerName);
		triggerMap.put("name",triggerName);
		triggerMap.put("group","now");
		triggerMap.put("description","立即执行");
		triggerMap.put("triggerClass", "org.quartz.impl.triggers.SimpleTriggerImpl");
		//triggerMap.put("calendarName", "");
		//triggerMap.put("startTime", new Date());
		//triggerMap.put("endTime", new Date());
		//triggerMap.put("repeatCount", Integer.valueOf(0));
		//triggerMap.put("repeatInterval",Long.valueOf(0));

		triggerMap.put("jobName", job.getJobName());
		triggerMap.put("jobGroup", job.getGroup());

		//Map<String,Object> jobMap = QuartzUtil.convertJob2Map(job);
//		JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{"java.util.Map","java.util.Map"}, "scheduleBasicJob", new Object[]{jobMap,triggerMap}, scheduler.getObjectName());
//	    JMXUtil.callJMXOperation(jmxInput);

		JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{"java.lang.String","java.lang.String","java.util.Map"}, "scheduleJob", new Object[]{job.getJobName(),job.getGroup(),triggerMap}, scheduler.getObjectName());
	    JMXUtil.callJMXOperation(jmxInput);

//	    JMXInput jmxInput1 = new JMXInput(quartzInstance, new String[]{"java.lang.String","java.lang.String"}, "unscheduleJob", new Object[]{triggerName,"manager"}, scheduler.getObjectName());
//	    JMXUtil.callJMXOperation(jmxInput1);

//	    JMXInput jmxInput1 = new JMXInput(quartzInstance, new String[]{"java.lang.String","java.lang.String","java.lang.String","java.lang.String"}, "scheduleJob", new Object[]{job.getJobName(),job.getGroup(),"christmasDayJob","default"}, scheduler.getObjectName());
//	    JMXUtil.callJMXOperation(jmxInput1);
	}

	@Override
	public void deleteJob(QuartzInstance quartzInstance, Scheduler scheduler, Job job)
			throws Exception {
		JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{"java.lang.String","java.lang.String"}, "deleteJob", new Object[]{job.getJobName(),job.getGroup()}, scheduler.getObjectName());
	    JMXUtil.callJMXOperation(jmxInput);
	}

	@Override
	public void deleteTrigger(QuartzInstance quartzInstance, Scheduler scheduler, Trigger trigger)
			throws Exception {

	    JMXInput jmxInput1 = new JMXInput(quartzInstance, new String[]{"java.lang.String","java.lang.String"}, "unscheduleJob", new Object[]{trigger.getName(),trigger.getGroup()}, scheduler.getObjectName());
	    JMXUtil.callJMXOperation(jmxInput1);

	}

	@Override
	public String getTriggerState(QuartzInstance quartzInstance, Scheduler scheduler, Trigger trigger)
			throws Exception {
	    JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{"java.lang.String","java.lang.String"}, "getTriggerState", new Object[]{trigger.getName(),trigger.getGroup()}, scheduler.getObjectName());
	    String state = (String)JMXUtil.callJMXOperation(jmxInput);
	    return state;
	}

	@Override
	public void addTriggerForJob(QuartzInstance quartzInstance, Scheduler scheduler, Job job,
			Map<String,Object> triggerMap) throws Exception {
		//Map<String,Object> jobMap = QuartzUtil.convertJob2Map(job);

		/**
		jobMap.put("name", jobMap.get("name")+"Test");

		triggerMap.put("jobName", jobMap.get("name"));
		//这种方法会删除job原有的trigger
		JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{"java.util.Map","java.util.Map"}, "scheduleBasicJob", new Object[]{jobMap,triggerMap}, scheduler.getObjectName());
	    JMXUtil.callJMXOperation(jmxInput);
	    **/
		//必须指定trigger的class，也就是必须有存在的trigger

		//JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{"java.util.Map","java.util.Map"}, "scheduleJob", new Object[]{jobMap,triggerMap}, scheduler.getObjectName());
		JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{"java.lang.String","java.lang.String","java.util.Map"}, "scheduleJob", new Object[]{job.getJobName(),job.getGroup(),triggerMap}, scheduler.getObjectName());
	    JMXUtil.callJMXOperation(jmxInput);
//		JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{"java.util.Map"}, "newTrigger", new Object[]{triggerMap}, scheduler.getObjectName());
//	    JMXUtil.callJMXOperation(jmxInput);
	}

	@Override
	public void pauseJob(QuartzInstance quartzInstance, Scheduler scheduler, Job job)
			throws Exception {
			JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{"java.lang.String","java.lang.String"}, "pauseJob", new Object[]{job.getJobName(),job.getGroup()}, scheduler.getObjectName());
		    JMXUtil.callJMXOperation(jmxInput);
	}

	@Override
	public void resumeJob(QuartzInstance quartzInstance, Scheduler scheduler, Job job)
			throws Exception {
		JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{"java.lang.String","java.lang.String"}, "resumeJob", new Object[]{job.getJobName(),job.getGroup()}, scheduler.getObjectName());
	    JMXUtil.callJMXOperation(jmxInput);
	}

	@Override
	public void addJob(QuartzInstance instance, Scheduler scheduler,
			Map<String, Object> jobMap) throws Exception {
		JMXInput jmxInput = new JMXInput(instance, new String[]{"java.util.Map","boolean"}, "addJob", new Object[]{jobMap,false}, scheduler.getObjectName());
	    JMXUtil.callJMXOperation(jmxInput);
	}
}
