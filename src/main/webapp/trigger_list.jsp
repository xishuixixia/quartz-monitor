<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<script type="text/javascript">

function dialogAjax(json){
	$.pdialog.reloadDialog("triggerList");
}
function closeTriggerList(){
	$.pdialog.close("triggerList");
}
</script>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="trigger/addShow?jobId=${jobId }" target="navTab" rel="addTrigger"><span>添加</span></a></li>
			<li><a class="delete"  href="<%=request.getContextPath()%>/trigger/delete.action?uuid={sid_user}" callback="dialogAjax" target="ajaxTodo" title="确定要删除吗?" fresh="true"><span>删除</span></a></li>
		</ul>
	</div>
	<input type="hidden" name="jobId" value="${jobId }"/>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="80">Trigger名称</th>
				<th width="80">Trigger组名</th>
				<th width="130">上一次触发时间</th>
				<th width="130">下一次触发时间</th>
				<th width="130">开始时间</th>
				<th>描述</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="triggerList" id="trigger">
			<tr target="sid_user" rel="${trigger.uuid }">
				<td align="middle">${trigger.name }</td>
				<td align="middle">${trigger.group }</td>
				<td><s:date name="#trigger.previousFireTime"   format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="#trigger.nextFireTime"   format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="#trigger.startTime"   format="yyyy-MM-dd HH:mm:ss"/></td>
				<td align="middle">${trigger.description }</td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
</div>
