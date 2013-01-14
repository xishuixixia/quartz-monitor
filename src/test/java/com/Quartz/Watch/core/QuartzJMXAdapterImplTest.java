package com.Quartz.Watch.core;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.quartz.monitor.conf.QuartzConfig;
import com.quartz.monitor.core.QuartzConnectService;
import com.quartz.monitor.core.QuartzConnectServiceImpl;
import com.quartz.monitor.object.JMXInput;
import com.quartz.monitor.object.QuartzInstance;
import com.quartz.monitor.object.Scheduler;
import com.quartz.monitor.util.JMXUtil;

public class QuartzJMXAdapterImplTest {

	QuartzConfig config = null;
	QuartzInstance quartzInstance = null;
	
	@Before
	public  void setUpBeforeClass() throws Exception {
		 try {
			 config = new QuartzConfig("11","127.0.0.1", 2911, "", "");
			QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
			quartzInstance = quartzConnectService.initInstance(config);
			System.out.println("------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testGetVersion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetJobDetails() throws Exception {
		
		
		List<Scheduler> schedulers = quartzInstance.getSchedulerList();
		
		System.out.println(schedulers.size());
		JMXInput jmxInput = new JMXInput(quartzInstance, new String[] { String.class.getName() },
				"AllJobDetails", new Object[] { schedulers.get(0).getUuidInstance() },schedulers.get(0).getObjectName());
		
		Object o1 = JMXUtil.callJMXAttribute(jmxInput);
		Object o2 = JMXUtil.callJMXOperation(jmxInput);
		System.out.println(o1);
		System.out.println(o2);
	}

	@Test
	public void testGetScheduler() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTriggersForJob() {
		fail("Not yet implemented");
	}

	@Test
	public void testAttachListener() {
		fail("Not yet implemented");
	}

}
