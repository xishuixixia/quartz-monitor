package com.quartz.monitor.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.quartz.monitor.core.JobContainer;
import com.quartz.monitor.core.TriggerContainer;
import com.quartz.monitor.object.Job;
import com.quartz.monitor.object.QuartzInstance;
import com.quartz.monitor.object.Result;
import com.quartz.monitor.object.Scheduler;
import com.quartz.monitor.object.Trigger;
import com.quartz.monitor.object.TriggerInput;
import com.quartz.monitor.util.JsonUtil;
import com.quartz.monitor.util.Tools;

public class TriggerAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private  static Logger log = Logger.getLogger(TriggerAction.class);
	private String uuid;
	private String jobId;
	private  List<Trigger> triggerList = new ArrayList<Trigger>();

	private TriggerInput triggerInput;
	
	public String list() throws Exception {

		QuartzInstance instance = Tools.getQuartzInstance();
		List<Scheduler> schedulers = instance.getSchedulerList();
		log.info(" schedulers list size:"+schedulers.size());

		Job job = JobContainer.getJobById(jobId);
		Scheduler scheduler = instance.getSchedulerByName(job.getSchedulerName());

		List<Trigger> temp = instance.getJmxAdapter().getTriggersForJob(instance, scheduler,job.getJobName(), job.getGroup());
		if(temp == null || temp.size() == 0){
			return "list";
		}
		for (Trigger trigger : temp) {
			String id = Tools.generateUUID();
			trigger.setUuid(id);
			trigger.setJobId(jobId);
			TriggerContainer.addTrigger(id, trigger);
			triggerList.add(trigger);
		}
		log.info("job[" + job.getJobName() + "]'s trigger size:" + triggerList.size());
		return "list";
	}

	public String addShow() throws Exception {
		return "list";
	}

	public String add() throws Exception {

		QuartzInstance instance = Tools.getQuartzInstance();
		
		HashMap<String, Object> triggerMap = new HashMap<String, Object>();
		triggerMap.put("name", triggerInput.getName());
		triggerMap.put("group",triggerInput.getGroup());
		triggerMap.put("description", triggerInput.getDescription());
		if(triggerInput.getDateFlag() == 1){
			triggerMap.put("startTime", triggerInput.getDate());
			triggerMap.put("triggerClass", "org.quartz.impl.triggers.SimpleTriggerImpl");
		}else{
			triggerMap.put("cronExpression", triggerInput.getCron());
			triggerMap.put("triggerClass", "org.quartz.impl.triggers.CronTriggerImpl");
		}
		Job job = JobContainer.getJobById(jobId);
		triggerMap.put("jobName", job.getJobName());
		triggerMap.put("jobGroup", job.getGroup());
		
		instance.getJmxAdapter().addTriggerForJob(instance, instance.getSchedulerByName(job.getSchedulerName()), job,triggerMap);
		
		log.info("add trigger for job:"+job.getJobName());
		
		
		Result result = new Result();
		result.setMessage("添加成功");
		result.setNavTabId("triggerList");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		
		
		return null;
	}

	public String delete() throws Exception {
		
		QuartzInstance instance = Tools.getQuartzInstance();
		
		Trigger trigger = TriggerContainer.getTriggerById(uuid);
		TriggerContainer.removeTriggerById(uuid);

		Job job = JobContainer.getJobById(trigger.getJobId());
		instance.getJmxAdapter().deleteTrigger(instance, instance.getSchedulerByName(job.getSchedulerName()), trigger);
		log.info("delete job["+trigger.getJobName()+"]'s trigger!");
		Result result = new Result();
		result.setMessage("删除成功");
		result.setNavTabId("triggerList");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
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


	public List<Trigger> getTriggerList() {
		return triggerList;
	}


	public void setTriggerList(List<Trigger> triggerList) {
		this.triggerList = triggerList;
	}


	public TriggerInput getTriggerInput() {
		return triggerInput;
	}

	public void setTriggerInput(TriggerInput triggerInput) {
		this.triggerInput = triggerInput;
	}

}
