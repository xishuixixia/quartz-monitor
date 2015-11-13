<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<form id="pagerForm" method="post" action="quartz/list">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
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
			<li><a class="add" href="addInstance.jsp" target="dialog" rel="addInstance"><span>添加</span></a></li>
			<li><a class="delete" href="<%=request.getContextPath()%>/quartz/delete.action?uuid={sid_user}" target="ajaxTodo" title="确定要删除吗？" warn="请选择一个用户"><span>删除</span></a></li>
			<li><a class="edit" href="<%=request.getContextPath()%>/quartz/show.action?uuid={sid_user}" target="dialog" warn="请选择一记录"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			<li><a class="icon" href="javascript:$.printBox('w_list_print')"><span>打印</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="navTab" href="<%=request.getContextPath()%>/quartz/list.action" title="配置列表" rel="main"><span>刷新</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
				<th width="100" orderField="num" class="desc" align="middle">配置名称</th>
				<th width="100" align="middle">主机名</th>
				<th width="100" align="middle">端口</th>
				<th width="100" align="middle">用户名</th>
				<th width="100" align="middle">密码</th>
				<th width="100" align="middle">状态</th>
				<th width="100" align="middle">操作</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="configMap" id="map">
			<tr target="sid_user" rel="<s:property value="value.uuid"/>">
				<td align="middle"><s:property value="value.name"/></td>
				<td align="middle"><s:property value="value.host"/></td>
				<td align="middle"><s:property value="value.port"/></td>
				<td align="middle"><s:property value="value.username"/></td>
				<td align="middle"><s:property value="value.password"/></td>
				<td align="middle"><s:property value="instanceMap.get(value.uuid).status"/></td>
				<td align="middle">
				<a class="delete" href="<%=request.getContextPath()%>/init/reconnect?uuid=${value.uuid}" target="ajaxTodo" fresh="true">重新连接</a>
                <s:if test="#session.configId == value.uuid">
                    当前配置
                </s:if>
                <s:else>
                    <a class="delete" href="<%=request.getContextPath()%>/init/config?uuid=${value.uuid}"  target="ajaxTodo" fresh="true">设为默认
                    </a>
                </s:else>
				</td>
			</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
</div>
