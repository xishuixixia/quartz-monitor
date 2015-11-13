package com.quartz.monitor.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.quartz.monitor.core.QuartzInstanceContainer;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.quartz.monitor.core.JobContainer;
import com.quartz.monitor.object.Job;
import com.quartz.monitor.object.QuartzInstance;
import com.quartz.monitor.object.Result;
import com.quartz.monitor.object.Scheduler;
import com.quartz.monitor.util.JsonUtil;
import com.quartz.monitor.util.Tools;

public class JobAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private  static Logger log = Logger.getLogger(JobAction.class);
	private String uuid;
	private  List<Job> jobList = new ArrayList<Job>();
	private  Map<String, Job> jobMap;
	private Job job;
	private Set<String> jobSet = new HashSet<String>();
	
	
	private Integer pageNum = 1;//当前页数
	private Integer numPerPage = 20;//每页的数量
	private Integer pageCount;//总页数
	private Integer size;

	public String listAll() throws Exception {
		Map<String, QuartzInstance> quartzInstanceMap = QuartzInstanceContainer.getQuartzInstanceMap();
		if(quartzInstanceMap.size() == 0){
			Result result = new Result();
			result.setMessage("请先配置Quartz");
			result.setCallbackType("");
			JsonUtil.toJson(new Gson().toJson(result));
			return null;
		}
		for(QuartzInstance instance : quartzInstanceMap.values()) {
			List<Scheduler> schedulers = instance.getSchedulerList();
			if (schedulers != null && schedulers.size() > 0) {
				for (int i = 0; i < schedulers.size(); i++) {
					Scheduler scheduler = schedulers.get(i);
					List<Job> temp = instance.getJmxAdapter().getJobDetails(instance, scheduler);
					if(temp != null) {
						for (Job job : temp) {
							String id = Tools.generateUUID();
							job.setUuid(id);
							JobContainer.addJob(id, job);
							jobList.add(job);
						}
					}
				}
			}
		}

		pageCount = Tools.getPageSize(jobList.size(), numPerPage);
		if (pageNum < 1) {
			pageNum = 1;
		}
		if (pageNum > pageCount) {
			pageNum = pageCount;
		}
		log.info("job size:" + jobList.size());
		size = jobList.size();
		return "list";
	}

	public String list() throws Exception {
		QuartzInstance instance = Tools.getQuartzInstance();
		if(instance == null){
			new InitAction().execute();
			instance = Tools.getQuartzInstance();
		}
		if(instance == null){
			
			Result result = new Result();
			result.setMessage("请先配置Quartz");
			result.setCallbackType("");
			JsonUtil.toJson(new Gson().toJson(result));
			return null;
		}
		List<Scheduler> schedulers = instance.getSchedulerList();
		 if (schedulers != null && schedulers.size() > 0)
         {
			 log.info(" schedulers list size:"+schedulers.size());
             for (int i = 0; i < schedulers.size(); i++)
             {
                 Scheduler scheduler = schedulers.get(i);
                 List<Job> temp = instance.getJmxAdapter().getJobDetails(instance, scheduler);
                 for(Job job : temp){
                	 String id = Tools.generateUUID();
                	 job.setUuid(id);
                	 JobContainer.addJob(id, job);
                	 jobList.add(job);
                 }
             }
         }
		 
		pageCount = Tools.getPageSize(jobList.size(), numPerPage);
		if (pageNum < 1) {
			pageNum = 1;
		}
		if (pageNum > pageCount) {
			pageNum = pageCount;
		}
		log.info("job size:" + jobList.size());
		size = jobList.size();
		return "list";
	}
	
	
	public String start() throws Exception {

		QuartzInstance instance = Tools.getQuartzInstance();
		Job job = JobContainer.getJobById(uuid);
		instance.getJmxAdapter().startJobNow(instance, instance.getSchedulerByName(job.getSchedulerName()), job);
		
		Result result = new Result();
		result.setStatusCode("200");
		result.setMessage("执行成功");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}

	public String delete() throws Exception {

		
		QuartzInstance instance = Tools.getQuartzInstance();
		
		Job job = JobContainer.getJobById(uuid);
		JobContainer.removeJobById(uuid);
		log.info("delete a quartz job!");
		instance.getJmxAdapter().deleteJob(instance, instance.getSchedulerByName(job.getSchedulerName()), job);
		Result result = new Result();
		result.setMessage("删除成功");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}
	
	public String pause() throws Exception {

		QuartzInstance instance = Tools.getQuartzInstance();

		Job job = JobContainer.getJobById(uuid);
		log.info("pause a quartz job!");
		instance.getJmxAdapter().pauseJob(instance, instance.getSchedulerByName(job.getSchedulerName()), job);
		Result result = new Result();
		result.setMessage("Job已暂停");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}
	
	public String resume() throws Exception {

		QuartzInstance instance = Tools.getQuartzInstance();

		Job job = JobContainer.getJobById(uuid);
		log.info("resume a quartz job!");
		instance.getJmxAdapter().resumeJob(instance, instance.getSchedulerByName(job.getSchedulerName()), job);
		
		Result result = new Result();
		result.setMessage("Job已恢复");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}
	
	public String show() throws Exception {

		jobMap = JobContainer.getJobMap();

		for(Map.Entry<String, Job> entry : jobMap.entrySet()){
			jobSet.add(entry.getValue().getSchedulerName());
		}
		log.info("get job map for add jsp,size:"+jobMap.size());
		log.info("get schedule name set for add jsp,size:"+jobSet.size());
		return "add";
	}
	public String add() throws Exception {

		QuartzInstance instance = Tools.getQuartzInstance();

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", job.getJobName());
		map.put("group", job.getGroup());
		map.put("description",job.getDescription());
		map.put("jobClass", JobContainer.getJobById(job.getJobClass()).getJobClass());
//		map.put("jobDetailClass", "org.quartz.Job");
		map.put("durability", true);
		map.put("jobDetailClass", "org.quartz.impl.JobDetailImpl");
		instance.getJmxAdapter().addJob(instance, instance.getSchedulerByName(job.getSchedulerName()),map);
		log.info("add job successfully!");
		
		Result result = new Result();
		result.setMessage("添加成功");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}
	
	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public Integer getPageNum() {
		return pageNum;
	}


	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}


	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}


	public Integer getPageCount() {
		return pageCount;
	}


	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}


	public Integer getSize() {
		return size;
	}


	public void setSize(Integer size) {
		this.size = size;
	}

	public Map<String, Job> getJobMap() {
		return jobMap;
	}


	public void setJobMap(Map<String, Job> jobMap) {
		this.jobMap = jobMap;
	}


	public Job getJob() {
		return job;
	}


	public void setJob(Job job) {
		this.job = job;
	}


	public Set<String> getJobSet() {
		return jobSet;
	}


	public void setJobSet(Set<String> jobSet) {
		this.jobSet = jobSet;
	}
	
}
