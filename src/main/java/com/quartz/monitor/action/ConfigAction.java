package com.quartz.monitor.action;

import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.quartz.monitor.conf.QuartzConfig;
import com.quartz.monitor.core.QuartzConnectService;
import com.quartz.monitor.core.QuartzConnectServiceImpl;
import com.quartz.monitor.core.QuartzInstanceContainer;
import com.quartz.monitor.object.QuartzInstance;
import com.quartz.monitor.object.Result;
import com.quartz.monitor.util.JsonUtil;
import com.quartz.monitor.util.Tools;
import com.quartz.monitor.util.XstreamUtil;

public class ConfigAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private  static Logger log = Logger.getLogger(ConfigAction.class);
	
	private String uuid;
	private String host;
	private int port;
	private String username;
	private String password;
	
	
	private Map<String,QuartzConfig> configMap;
	private Map<String,QuartzInstance> instanceMap;


	public String add() throws Exception {

		String id = Tools.generateUUID();
		QuartzConfig quartzConfig = new QuartzConfig(id,host, port, username,password);
		QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
		try {
			QuartzInstance quartzInstance = quartzConnectService.initInstance(quartzConfig);
			//QuartzInstanceService.putQuartzInstance(quartzInstance);
			QuartzInstanceContainer.addQuartzConfig(quartzConfig);
			QuartzInstanceContainer.addQuartzInstance(id, quartzInstance);
			log.info("add a quartz info!");


			XstreamUtil.object2XML(quartzConfig);

			Result result = new Result();
			result.setNavTabId("main");
			result.setMessage("添加成功");
			JsonUtil.toJson(new Gson().toJson(result));
		} catch (Exception e) {
			Result result = new Result();
			result.setNavTabId("main");
			result.setMessage("添加失败:" + e.getMessage());
			JsonUtil.toJson(new Gson().toJson(result));
		}
		return null;
	}
	
	public String list() throws Exception {

		configMap = QuartzInstanceContainer.getConfigMap();
		instanceMap = QuartzInstanceContainer.getQuartzInstanceMap();

		log.info("get config map info.map size:"+configMap.size());
		log.info("get instance map info.map size:" + instanceMap.size());

		return "list";
	}

	
	public String show() throws Exception {

		QuartzConfig quartzConfig = QuartzInstanceContainer.getQuartzConfig(uuid);
		log.info("get a quartz info! uuid:"+uuid);
		uuid = quartzConfig.getUuid();
		host = quartzConfig.getHost();
		port = quartzConfig.getPort();
		username = quartzConfig.getUserName();
		password = quartzConfig.getPassword();
		return "show";
	}
	
	public String update() throws Exception {

		QuartzConfig quartzConfig = new QuartzConfig(uuid,host, port, username,password);
		QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
		try {
			QuartzInstance quartzInstance = quartzConnectService.initInstance(quartzConfig);
			QuartzInstanceContainer.addQuartzConfig(quartzConfig);
			QuartzInstanceContainer.addQuartzInstance(uuid, quartzInstance);
			log.info("update a quartz info!");

			XstreamUtil.object2XML(quartzConfig);

			Result result = new Result();
			result.setMessage("修改成功");
			JsonUtil.toJson(new Gson().toJson(result));
		} catch (Exception e) {
			Result result = new Result();
			result.setNavTabId("main");
			result.setMessage("修改失败:" + e.getMessage());
			JsonUtil.toJson(new Gson().toJson(result));
		}
		return null;
	}
	
	public String delete() throws Exception {
		Result result = new Result();
		log.info("delete a quartz info!");
		if(XstreamUtil.removeXml(uuid)){
			QuartzInstanceContainer.removeQuartzConfig(uuid);
			QuartzInstanceContainer.removeQuartzInstance(uuid);
			result.setMessage("删除成功");
		}else{
			result.setMessage("删除失败");
		}
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}
	
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, QuartzConfig> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, QuartzConfig> configMap) {
		this.configMap = configMap;
	}

	public Map<String, QuartzInstance> getInstanceMap() {
		return instanceMap;
	}

	public void setInstanceMap(Map<String, QuartzInstance> instanceMap) {
		this.instanceMap = instanceMap;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
}
