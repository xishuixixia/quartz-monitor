<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
	$.pdialog.close("triggerList");
</script>
<h2 class="contentTitle">添加Trigger</h2>


<div class="pageContent">
	
	<form method="post" action="trigger/add" class="pageForm required-validate" onsubmit="return validateCallback(this)">
		<div class="pageFormContent nowrap" layoutH="97">

			<dl>
				<dt>名称：</dt>
				<dd>
					<input type="hidden" name="jobId" value="${jobId}"/>
					<input type="text" name="triggerInput.name" maxlength="20" class="required" alt="请输入Trigger名称"/>
					<span class="info">必填</span>
				</dd>
			</dl>
			<dl>
				<dt>组名：</dt>
				<dd>
					<input type="text" name="triggerInput.group" class="required" alt="请输入Trigger组名"/>
					<span class="info">必填</span>
				</dd>
			</dl>
			<dl>
				<dt>触发条件：</dt>
				<br/>
				<dd>
					<label><input type="radio" name="triggerInput.dateFlag" value="1" checked="checked"/>触发一次</label>
					<input type="text" name="triggerInput.date" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
				</dd>
			</dl>
			<dl>
			<dt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dt>
				<dd>
					<label><input type="radio" name="triggerInput.dateFlag" value="2"/>Cron Expression</label>
					<input type="text" name="triggerInput.cron"  alt="请输入Cron Expression"/>
				</dd>
			</dl>
			
			<dl>
				<dt>备注信息：</dt>
				<dd>
					<textarea name="triggerInput.description" cols="40" rows="4" class="textInput"></textarea>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
