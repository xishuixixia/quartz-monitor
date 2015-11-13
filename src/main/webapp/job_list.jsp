<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<form id="pagerForm" method="post" action="job/list">
	<input type="hidden" name="pageNum" value="1"/>
	<input type="hidden" name="numPerPage" value="20" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="demo_page1.html" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				Quartz监控管理工具
			</li>
		</ul>
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=request.getContextPath()%>/job/show" target="dialog" mask="true"><span>添加</span></a></li>
			<li><a class="delete" href="<%=request.getContextPath()%>/job/delete.action?uuid={sid_user}" target="ajaxTodo" title="确定要删除吗？" warn="请选择一个用户" rel="triggerList"  mask="true"><span>删除</span></a></li>
<%-- 			<li><a class="edit" href="<%=request.getContextPath()%>/quartz/show.action?uuid={sid_user}" target="dialog" warn="请选择一记录"><span>修改</span></a></li> --%>
			<li class="line">line</li>
			<!--<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>-->
			<li><a class="icon" href="javascript:$.printBox('w_list_print')"><span>打印</span></a></li>
			<li class="line">line</li>
			<li><a class="icon"  href="<%=request.getContextPath()%>/job/list.action" target="navTab" title="任务列表" rel="page1"><span>刷新</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
				<th width="80" orderField="name" class="asc">名称</th>
				<th width="60" orderField="num" class="desc">所属组</th>
				<th width="100">下一次触发时间</th>
				<th width="10">Triggers</th>
				<th width="10">Durable</th>
				<th width="80">所属Scheduler</th>
				<th width="100" align="middle">操作</th>
				<th width="70">描述</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="jobList" id="job">
			<tr target="sid_user" rel="${job.uuid }">
				<td>${job.jobName }</td>
				<td>${job.group }</td>
				<td><s:date name="#job.nextFireTime"   format="yyyy-MM-dd HH:mm:ss"/></td>
				<td align='middle'><a href="<%=request.getContextPath()%>/trigger/list?jobId=${job.uuid}" target="dialog" title="Trigger列表" mask="true" rel="triggerList" width="800">${job.numTriggers }</a></td>
				<td>${job.durability }</td>
				<td>${job.schedulerName }</td>
				<td align="middle">
				<a href="<%=request.getContextPath()%>/job/start.action?uuid=${job.uuid}" target="ajaxTodo">执行</a>
				<s:if test="#job.state == 'NORMAL'">
    				 <a href="<%=request.getContextPath()%>/job/pause.action?uuid=${job.uuid}" target="ajaxTodo">暂停</a>
				</s:if>
				<s:elseif test="#job.state == 'COMPLETE'">
				</s:elseif>
				<s:elseif test="#job.state == 'PAUSED'">
				      <a href="<%=request.getContextPath()%>/job/resume.action?uuid=${job.uuid}" target="ajaxTodo">恢复</a>
				</s:elseif>
				<s:else>
      				未知
				</s:else>
				</td>
				<td>${job.description }</td>
			</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
	
	<div class="panelBar" >
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共${size }条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${pageCount }" numPerPage="20" pageNumShown="10" currentPage="${pageNum }"></div>
	</div>
</div>
