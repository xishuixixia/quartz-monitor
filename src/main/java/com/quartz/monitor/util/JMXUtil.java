package com.quartz.monitor.util;

import java.net.MalformedURLException;
import java.util.Date;

import javax.management.MBeanServerConnection;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXServiceURL;

import com.quartz.monitor.object.QuzrtzInstanceStatus;
import org.apache.commons.lang.StringUtils;

import com.quartz.monitor.conf.QuartzConfig;
import com.quartz.monitor.object.JMXInput;
import com.quartz.monitor.object.QuartzInstance;

public class JMXUtil {

	public static JMXServiceURL createQuartzInstanceConnection(QuartzConfig quartzConfig)
			throws MalformedURLException {
		StringBuffer stringBuffer = new StringBuffer().append("service:jmx:rmi:///jndi/rmi://")
				.append(quartzConfig.getHost()).append(":").append(quartzConfig.getPort())
				.append("/jmxrmi");
		JMXServiceURL jmxServiceURL = new JMXServiceURL(stringBuffer.toString());
		return jmxServiceURL;
	}

	public static boolean isSupported(String version) {
		return StringKit.isNotEmpty(version) && version.startsWith("2");
	}

	public static Object callJMXAttribute(JMXInput jmxInput) throws Exception {
		QuartzInstance quartzInstance = jmxInput.getQuartzInstanceConnection();
		MBeanServerConnection connection = quartzInstance.getMBeanServerConnection();
		try {
			Object object = connection.getAttribute(jmxInput.getObjectName(), jmxInput.getOperation());
			jmxInput.getQuartzInstanceConnection().setStatus(QuzrtzInstanceStatus.OK);
			return object;
		} catch (Exception e){
			jmxInput.getQuartzInstanceConnection().setStatus(QuzrtzInstanceStatus.Fail);
			return null;
		}
	}

	public static Object callJMXOperation(JMXInput jmxInput) throws Exception {
		QuartzInstance quartzInstance = jmxInput.getQuartzInstanceConnection();
		MBeanServerConnection connection = quartzInstance.getMBeanServerConnection();
		try{
            Object object = connection.invoke(jmxInput.getObjectName(), jmxInput.getOperation(),
                    jmxInput.getParameters(), jmxInput.getSignature());
            jmxInput.getQuartzInstanceConnection().setStatus(QuzrtzInstanceStatus.OK);
            return object;
        } catch (Exception e) {
			jmxInput.getQuartzInstanceConnection().setStatus(QuzrtzInstanceStatus.Fail);
			return null;
		}
	}

	public static Object convertToType(CompositeDataSupport compositeDataSupport, String key) {
		if (compositeDataSupport.getCompositeType().getType(key).getClassName()
				.equals("java.lang.String")) {
			return StringUtils.trimToEmpty((String) compositeDataSupport.get(key));
		} else if (compositeDataSupport.getCompositeType().getType(key).getClassName()
				.equals("java.lang.Boolean")) {
			return compositeDataSupport.get(key);
		} else if (compositeDataSupport.getCompositeType().getType(key).getClassName()
				.equals("java.util.Date")) {
			return (Date) compositeDataSupport.get(key);
		} else if (compositeDataSupport.getCompositeType().getType(key).getClassName()
				.equals("java.lang.Integer")) {
			return (Integer) compositeDataSupport.get(key);
		} else if (compositeDataSupport.getCompositeType().getType(key).getClassName()
				.equals("java.lang.Long")) {
			return (Long) compositeDataSupport.get(key);
		}
		return new Object();
	}
}
