package com.quartz.monitor.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.quartz.monitor.core.QuartzConnectService;
import com.quartz.monitor.core.QuartzConnectServiceImpl;
import com.quartz.monitor.object.QuzrtzInstanceStatus;
import org.apache.struts2.ServletActionContext;
import org.quartz.CronExpression;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.quartz.monitor.conf.QuartzConfig;
import com.quartz.monitor.core.QuartzInstanceContainer;
import com.quartz.monitor.object.QuartzInstance;
import com.quartz.monitor.object.Result;
import com.quartz.monitor.util.JsonUtil;

public class InitAction  extends ActionSupport {

	private static final long serialVersionUID = 1L;
	Map<String,QuartzConfig> configMap ;
	private String uuid;
	private String expression;
	private List<Date> dateList =new ArrayList<Date>();
	
	@Override
	public String execute() throws Exception {
		configMap = QuartzInstanceContainer.getConfigMap();
		
		Map<String,QuartzInstance> quartzInstanceMap = QuartzInstanceContainer.getQuartzInstanceMap();
		if(quartzInstanceMap == null || quartzInstanceMap.size() == 0){
			return super.execute();
		}
		Set<String> keySet = quartzInstanceMap.keySet();
		String key = null;
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
            key =  it.next();
            break;
        }
		HttpServletRequest request=ServletActionContext.getRequest();//得到request对象
		HttpSession session=request.getSession();//通过request得到session对象
		session.setAttribute("configName", QuartzInstanceContainer.getQuartzConfig(key).getName());
		session.setAttribute("configId", key);
		return super.execute();
	}

	public String reconnect() throws Exception {
		QuartzInstanceContainer.removeQuartzInstance(uuid);
		QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
		QuartzConfig config = QuartzInstanceContainer.getQuartzConfig(uuid);
		try {
			QuartzInstance quartzInstance = quartzConnectService.initInstance(config);
			QuartzInstanceContainer.addQuartzInstance(uuid, quartzInstance);
			Result result = new Result();
			if (QuzrtzInstanceStatus.OK == quartzInstance.getStatus()) {
				result.setMessage("连接成功");
			} else {
				result.setMessage("连接失败");
			}
			result.setCallbackType("");
			JsonUtil.toJson(new Gson().toJson(result));
		} catch (Exception e) {
			Result result = new Result();
			result.setMessage("连接失败:" + e.getMessage());
			result.setCallbackType("");
			JsonUtil.toJson(new Gson().toJson(result));
		}
		return null;
	}

	public String config() throws Exception {
		
		QuartzConfig config = QuartzInstanceContainer.getQuartzConfig(uuid);
		
		HttpServletRequest request=ServletActionContext.getRequest();//得到request对象
		HttpSession session=request.getSession();//通过request得到session对象
		session.setAttribute("configName", config.getName());
		session.setAttribute("configId", uuid);
		
		
		Result result = new Result();
		result.setMessage("设置成功");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}
	
	public String check() throws Exception {
		
        CronExpression cronExpression = new CronExpression(expression);
 
        Date date = new Date();
        
        for(int i = 0; i< 15; i++){
        	date = cronExpression.getNextValidTimeAfter(date);
        	dateList.add(date);
        }
		return "success";
	}
	
	

	public Map<String, QuartzConfig> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, QuartzConfig> configMap) {
		this.configMap = configMap;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public List<Date> getDateList() {
		return dateList;
	}
	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}
	
}
