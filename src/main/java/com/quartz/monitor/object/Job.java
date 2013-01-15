package com.quartz.monitor.object;

import java.util.Date;
import java.util.Map;

public class Job
{

   private String uuid;
   private String quartzInstanceId;
   private String schedulerInstanceId;
   private String schedulerName;
   private String description;
   private boolean durability;
   private String group;
   private String jobClass;
   private String jobName;
   private boolean shouldRecover;
   private Map<String,Object> jobDataMap;
   private Date nextFireTime;
   private int numTriggers;
   private String state;

   public String getQuartzInstanceId()
   {
      return quartzInstanceId;
   }

   public void setQuartzInstanceId(String quartzInstanceId)
   {
      this.quartzInstanceId = quartzInstanceId;
   }

   public String getSchedulerInstanceId()
   {
      return schedulerInstanceId;
   }

   public void setSchedulerInstanceId(String schedulerInstanceId)
   {
      this.schedulerInstanceId = schedulerInstanceId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public boolean isDurability()
   {
      return durability;
   }

   public void setDurability(boolean durability)
   {
      this.durability = durability;
   }

   public String getGroup()
   {
      return group;
   }

   public void setGroup(String group)
   {
      this.group = group;
   }

   public String getJobClass()
   {
      return jobClass;
   }

   public void setJobClass(String jobClass)
   {
      this.jobClass = jobClass;
   }

   public String getJobName()
   {
      return jobName;
   }

   public void setJobName(String jobName)
   {
      this.jobName = jobName;
   }

   public boolean isShouldRecover()
   {
      return shouldRecover;
   }

   public void setShouldRecover(boolean shouldRecover)
   {
      this.shouldRecover = shouldRecover;
   }

   public Map<String,Object> getJobDataMap()
   {
      return jobDataMap;
   }

   public void setJobDataMap(Map<String,Object> jobDataMap)
   {
      this.jobDataMap = jobDataMap;
   }

   public int getNumTriggers()
   {
      return numTriggers;
   }

   public void setNumTriggers(int numTriggers)
   {
      this.numTriggers = numTriggers;
   }

   public Date getNextFireTime()
   {
      return nextFireTime;
   }

   public void setNextFireTime(Date nextFireTime)
   {
      this.nextFireTime = nextFireTime;
   }

	public String getSchedulerName() {
		return schedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Job");
		sb.append("{quartzInstanceId='").append(quartzInstanceId).append('\'');
		sb.append(", schedulerInstanceId='").append(schedulerInstanceId).append('\'');
		sb.append(", description='").append(description).append('\'');
		sb.append(", durability=").append(durability);
		sb.append(", group='").append(group).append('\'');
		sb.append(", jobClass='").append(jobClass).append('\'');
		sb.append(", jobName='").append(jobName).append('\'');
		sb.append(", shouldRecover=").append(shouldRecover);
		sb.append(", jobDataMap=").append(jobDataMap);
		sb.append(", nextFireTime=").append(nextFireTime);
		sb.append(", numTriggers=").append(numTriggers);
		sb.append('}');
		return sb.toString();
   }
}
