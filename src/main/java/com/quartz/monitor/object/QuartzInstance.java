package com.quartz.monitor.object;

import java.util.List;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;

import com.quartz.monitor.core.QuartzJMXAdapter;

public class QuartzInstance
{
   private String uuid;
   private MBeanServerConnection mBeanServerConnection;
   private QuartzJMXAdapter jmxAdapter;
   private List<Scheduler> schedulerList;
   private QuzrtzInstanceStatus status;

   /**
    * needed for shutdown. *
    */
   private JMXConnector jmxConnector;

   public MBeanServerConnection getMBeanServerConnection()
   {
      return mBeanServerConnection;
   }

   public void setMBeanServerConnection(MBeanServerConnection mBeanServerConnection)
   {
      this.mBeanServerConnection = mBeanServerConnection;
   }

   public QuartzJMXAdapter getJmxAdapter()
   {
      return jmxAdapter;
   }

   public void setJmxAdapter(QuartzJMXAdapter jmxAdapter)
   {
      this.jmxAdapter = jmxAdapter;
   }

   public List<Scheduler> getSchedulerList()
   {
      return schedulerList;
   }

   public Scheduler getSchedulerByName(String name)
   {
	   if (schedulerList != null && schedulerList.size() > 0)
	      {
	         for (int i = 0; i < schedulerList.size(); i++)
	         {
	            Scheduler s = (Scheduler) schedulerList.get(i);
	            if (s.getName().equals(name))
	            {
	               return s;
	            }
	         }
	      }
	   return null;
   }
   public void setSchedulerList(List<Scheduler> schedulerList)
   {
      this.schedulerList = schedulerList;
   }

   public JMXConnector getJmxConnector()
   {
      return jmxConnector;
   }

   public void setJmxConnector(JMXConnector jmxConnector)
   {
      this.jmxConnector = jmxConnector;
   }

   public String getUuid() {
	  return uuid;
   }

   public void setUuid(String uuid) {
	  this.uuid = uuid;
   }

   public QuzrtzInstanceStatus getStatus() {
      return status;
   }

   public void setStatus(QuzrtzInstanceStatus status) {
      this.status = status;
   }

   @Override
   public String toString()
   {
      final StringBuilder sb = new StringBuilder();
      sb.append("QuartzInstance");
      sb.append("{mBeanServerConnection=").append(mBeanServerConnection);
      sb.append(", jmxAdapter=").append(jmxAdapter);
      sb.append(", schedulerList=").append(schedulerList);
      sb.append(", status=").append(status);
      sb.append('}');
      return sb.toString();
   }
}
