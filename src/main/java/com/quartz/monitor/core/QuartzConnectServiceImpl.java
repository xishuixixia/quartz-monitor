package com.quartz.monitor.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.log4j.Logger;

import com.quartz.monitor.conf.QuartzConfig;
import com.quartz.monitor.object.QuartzInstance;
import com.quartz.monitor.object.Scheduler;
import com.quartz.monitor.util.JMXUtil;

/**
 * 处理应用与Quartz的连接（使用JMX）
 * @author guolei
 *
 */
public class QuartzConnectServiceImpl implements QuartzConnectService
{
   static Logger log = Logger.getLogger(QuartzConnectServiceImpl.class);

   @Override
   public QuartzInstance initInstance(QuartzConfig config) throws Exception
   {
      Map<String, String[]> env = new HashMap<String, String[]>();
      env.put(JMXConnector.CREDENTIALS, new String[]{config.getUserName(), config.getPassword()});
      JMXServiceURL jmxServiceURL = JMXUtil.createQuartzInstanceConnection(config);
      //JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL, env);
      JMXConnector connector = connectWithTimeout(jmxServiceURL, env, 5);
      MBeanServerConnection connection = connector.getMBeanServerConnection();

      ObjectName mBName = new ObjectName("quartz:type=QuartzScheduler,*");
      Set<ObjectName> names = connection.queryNames(mBName, null);
      QuartzInstance quartzInstance = new QuartzInstance();
      quartzInstance.setMBeanServerConnection(connection);
      quartzInstance.setJmxConnector(connector);

      List<Scheduler> schList = new ArrayList<Scheduler>();
      for (ObjectName objectName : names)   // for each scheduler.
      {
         QuartzJMXAdapter jmxAdapter = QuartzJMXAdapterFactory.initQuartzJMXAdapter(objectName, connection);
         quartzInstance.setJmxAdapter(jmxAdapter);

         Scheduler scheduler = jmxAdapter.getSchedulerByJmx(quartzInstance, objectName);
         schList.add(scheduler);

         // attach listener
//         connection.addNotificationListener(objectName, listener, null, null);
         log.info("added listener " + objectName.getCanonicalName());
        //QuartzInstance.putListener(listener);
      }
      quartzInstance.setSchedulerList(schList);
      return quartzInstance;
   }


   JMXConnector connectWithTimeout(final JMXServiceURL jmxServiceURL,
                                   final Map<String,?> environment,
                                   int timeoutSecond)
           throws InterruptedException, ExecutionException, TimeoutException {
      ExecutorService executor = Executors.newSingleThreadExecutor(new DaemonThreadFactory());
      Future<JMXConnector> future = executor.submit(new Callable<JMXConnector>() {
         public JMXConnector call() throws IOException {
            return JMXConnectorFactory.connect(jmxServiceURL,environment);
         }
      });
      JMXConnector connector = future.get(timeoutSecond, TimeUnit.SECONDS);
      executor.shutdownNow();
      return connector;
   }


   class DaemonThreadFactory implements ThreadFactory {
      DaemonThreadFactory() {
      }

      public Thread newThread(Runnable r) {
         Thread daemonThread = new Thread(r);
         daemonThread.setDaemon(true);
         return daemonThread;
      }
   }
}
