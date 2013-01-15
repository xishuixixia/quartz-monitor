<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				Cron Expression
			</li>
		</ul>
	</div>
	</form>
</div>
<div class="pageContent">
<form action="init" name="check" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this)">
	<div class="pageFormContent" layoutH="97">
		
		<dl class="nowrap">
			<dt>表达式：</dt>
			<dd>
				<input class="required" id="expression" name="expression" type="text" value="0 01 12 31 12 ?"/>
				<a id="show" class="btnLook" href="check?expression={expression}"   target="dialog" mask="true" title="日期">验证</a>	
			</dd>
		</dl>
	</div>
</form>
</div>