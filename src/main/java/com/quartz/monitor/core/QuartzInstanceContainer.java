package com.quartz.monitor.core;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.quartz.monitor.conf.QuartzConfig;
import com.quartz.monitor.object.QuartzInstance;

public class QuartzInstanceContainer {

	private static Map<String, QuartzInstance> quartzInstanceMap = new ConcurrentHashMap<String, QuartzInstance>();
	private static Map<String, QuartzConfig> configMap = new ConcurrentHashMap<String, QuartzConfig>();

	public static void addQuartzInstance(String id, QuartzInstance instance) {
		quartzInstanceMap.put(id, instance);
	}

	public static Map<String, QuartzInstance> getQuartzInstanceMap() {
		return Collections.unmodifiableMap(quartzInstanceMap);
	}
	public static void removeQuartzInstance(String uuid){
		quartzInstanceMap.remove(uuid);
	}
	
	public static void addQuartzConfig(QuartzConfig config) {
		
		configMap.put(config.getUuid(),config);
	}
	
	public static Map<String, QuartzConfig> getConfigMap() {
		return Collections.unmodifiableMap(configMap);
	}
	public static QuartzConfig getQuartzConfig(String uuid){
		return configMap.get(uuid);
	}
	public static void removeQuartzConfig(String uuid){
		 configMap.remove(uuid);
	}
}
