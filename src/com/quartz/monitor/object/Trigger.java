package com.quartz.monitor.object;

import java.util.Date;

public class Trigger
{
   public static final String STATE_GET_ERROR = "Q_ERROR";

   
   private String uuid;
   private String jobId;
   private String calendarName;
   private String description;
   private Date endTime;
   private Date finalFireTime;
   private String fireInstanceId;
   private String group;
   private String jobGroup;
   private String jobName;
   private int misfireInstruction;
   private String name;
   private Date nextFireTime;
   private Date previousFireTime;
   private int priority;
   private Date startTime;

   /**
    * One of:
    * BLOCKED
    * COMPLETE
    * ERROR
    * NONE
    * NORMAL
    * PAUSED
    */
   private String sTriggerState;

   public String getCalendarName()
   {
      return calendarName;
   }

   public void setCalendarName(String calendarName)
   {
      this.calendarName = calendarName;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public Date getEndTime()
   {
      return endTime;
   }

   public void setEndTime(Date endTime)
   {
      this.endTime = endTime;
   }

   public Date getFinalFireTime()
   {
      return finalFireTime;
   }

   public void setFinalFireTime(Date finalFireTime)
   {
      this.finalFireTime = finalFireTime;
   }

   public String getFireInstanceId()
   {
      return fireInstanceId;
   }

   public void setFireInstanceId(String fireInstanceId)
   {
      this.fireInstanceId = fireInstanceId;
   }

   public String getGroup()
   {
      return group;
   }

   public void setGroup(String group)
   {
      this.group = group;
   }

   public String getJobGroup()
   {
      return jobGroup;
   }

   public void setJobGroup(String jobGroup)
   {
      this.jobGroup = jobGroup;
   }

   public String getJobName()
   {
      return jobName;
   }

   public void setJobName(String jobName)
   {
      this.jobName = jobName;
   }

   public int getMisfireInstruction()
   {
      return misfireInstruction;
   }

   public void setMisfireInstruction(int misfireInstruction)
   {
      this.misfireInstruction = misfireInstruction;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Date getNextFireTime()
   {
      return nextFireTime;
   }

   public void setNextFireTime(Date nextFireTime)
   {
      this.nextFireTime = nextFireTime;
   }

   public Date getPreviousFireTime()
   {
      return previousFireTime;
   }

   public void setPreviousFireTime(Date previousFireTime)
   {
      this.previousFireTime = previousFireTime;
   }

   public int getPriority()
   {
      return priority;
   }

   public void setPriority(int priority)
   {
      this.priority = priority;
   }

   public Date getStartTime()
   {
      return startTime;
   }

   public void setStartTime(Date startTime)
   {
      this.startTime = startTime;
   }

   public String getSTriggerState()
   {
      return sTriggerState;
   }

   public void setSTriggerState(String sTriggerState)
   {
      this.sTriggerState = sTriggerState;
   }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	@Override
   public String toString()
    {
      final StringBuilder sb = new StringBuilder();
      sb.append("Trigger");
      sb.append("{calendarName='").append(calendarName).append('\'');
      sb.append(", description='").append(description).append('\'');
      sb.append(", endTime=").append(endTime);
      sb.append(", finalFireTime=").append(finalFireTime);
      sb.append(", fireInstanceId='").append(fireInstanceId).append('\'');
      sb.append(", group='").append(group).append('\'');
      sb.append(", jobGroup='").append(jobGroup).append('\'');
      sb.append(", jobName='").append(jobName).append('\'');
      sb.append(", misfireInstruction=").append(misfireInstruction);
      sb.append(", name='").append(name).append('\'');
      sb.append(", nextFireTime=").append(nextFireTime);
      sb.append(", previousFireTime=").append(previousFireTime);
      sb.append(", priority=").append(priority);
      sb.append(", startTime=").append(startTime);
      sb.append(", sTriggerState='").append(sTriggerState).append('\'');
      sb.append('}');
      return sb.toString();
   }
}
