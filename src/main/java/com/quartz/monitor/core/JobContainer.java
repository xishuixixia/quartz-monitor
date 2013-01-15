package com.quartz.monitor.core;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.quartz.monitor.object.Job;

public class JobContainer {
	
	private static Map<String, Job> jobMap = new ConcurrentHashMap<String, Job>();
	
	public static  void addJob(String uuid,Job job){
		jobMap.put(uuid, job);
	}
	public static Job getJobById(String uuid){
		return jobMap.get(uuid);
	}
	public static void removeJobById(String uuid){
		 jobMap.remove(uuid);
	}
	public static Map<String, Job> getJobMap(){
		return Collections.unmodifiableMap(jobMap);
	}
}
