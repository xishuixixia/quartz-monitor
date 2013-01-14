package com.quartz.monitor.core;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public class QuartzJMXAdapterFactory
{
   /**
    * Currently creates the v2.0.0 adapter. In the future, we will need to have an adapter map that returns the correct
    * adapter object to use depending on version.
    *
    * @param objectName
    * @param connection
    * @return
    * @throws Exception
    */
   public static QuartzJMXAdapter initQuartzJMXAdapter(ObjectName objectName, MBeanServerConnection connection) throws Exception
   {
      QuartzJMXAdapter jmxAdapter = new QuartzJMXAdapterImpl();
      return jmxAdapter;
   }
}