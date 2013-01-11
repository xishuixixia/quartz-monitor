<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<div class="pageContent">
		<s:iterator value="dateList" id="date">
		<div class="unit">
				<s:date name="#date"   format="yyyy-MM-dd HH:mm:ss"/>
		</div>
		</s:iterator>
	</table>
</div>
