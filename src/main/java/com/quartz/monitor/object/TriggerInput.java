package com.quartz.monitor.object;

import java.util.Date;

public class TriggerInput {

	private String name;
	private String group;
	private Date date;
	private Integer dateFlag;
	private String cron;
	private String description;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getDateFlag() {
		return dateFlag;
	}
	public void setDateFlag(Integer dateFlag) {
		this.dateFlag = dateFlag;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
