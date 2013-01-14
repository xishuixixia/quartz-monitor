<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<div class="pageContent">
	<form method="post" action="<%=request.getContextPath()%>/job/add.action" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>job名称：</label>
				<input name="job.jobName" class="required" type="text" size="30"  alt="请输入job名称" />
			</div>
			<div class="unit">
				<label>所属组：</label>
				<input type="hidden" name="job."/>
				<input type="text" class="required" name="job.group" alt="请输入组名"/>
			</div>
			<div class="unit">
				<label>描述：</label>
				<textarea name="job.description" cols="30" rows="2" class="textInput"></textarea>
			</div>
			<div class="unit">
				<label>job类型：</label>
				<select name="job.jobClass" class="required">
					<s:iterator value="jobMap" id="map">
					   <option value="${value.uuid }">${value.jobName }</option>
					</s:iterator>
				</select>
			</div>
			<div class="unit">
				<label>所属Schedule：</label>
				<select name="job.schedulerName" class="required">
					<s:iterator value="jobSet" id="schedulerName">
					   <option value="${schedulerName }">${schedulerName }</option>
					</s:iterator>
				</select>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
