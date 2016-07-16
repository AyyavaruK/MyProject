<%@ include file="taglib.jsp" %>
<title>Dashboard</title>
<link href="view/css/colorbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="view/css/popup.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/icon.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/demo.css">
<script type="text/javascript" src="view/js/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="view/js/jquery/jquery.usphone.js"></script>
<script type="text/javascript" src="view/js/app/telligentCommon.js"></script>
<script type="text/javascript" src="view/js/popup.js"></script>
<form:form commandName="employee" id="employeeForm" modelAttribute="employee" enctype="multipart/form-data">
<form:hidden path="successMessage"/>
<form:hidden path="errorMessage"/>
<form:hidden path="operation"/>
<input type="hidden" id="updateble">
<input type="hidden" id="screenName" value="personal">
<div id="contentArea">
  <div class="loader"></div>
  <div class="contentArea">
	  <%@include file="employeeLeft.jsp" %>
	  <div class="innerright" style="min-height: 480px" id="flow"> <a href="javascript:toggle()" title="Hide Nav 13" id="flowtab"></a>
      <div style="margin:0 0 100px 0px;cursor:auto;" id="tab">
        <div class="wrap" style="margin:0 0 100px 0px;">
		  <table  width="100%" border="0" cellspacing="0" cellpadding="0">
		  	<tr>
		  		<td>
		  			<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right">
			            <tr>
			              <th class="head1" height="25" align="left"> 
			              	<div class="innerpage-breadcrum"> 
			              		<span style="padding-top: 10px;">
			              			<a href="dashboard.htm">Dashboard</a> &nbsp;&gt;&nbsp; <a href="javascript:void(0);">Employee</a> &nbsp;&gt;&nbsp; <a href="javascript:void(0);" class="select">Finger Prints</a>
			              		</span> 
			              	</div>
			              </th>
			            </tr>
			          </table>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>
		  			<%@include file="employeeButtons.jsp" %>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>
		  			<jsp:plugin 
					code="applet.JSGDApplet.class" 
					codebase="."
					archive="AppletDemo.jar,FDxSDKPro.jar,AbsoluteLayout.jar,mysql-connector-java-5.1.18.jar" 
					type="applet" 
					width="1000"
					height="500"
					align    = "middle">
					<jsp:fallback>
				        Plugin tag OBJECT or EMBED not supported by browser.
				    </jsp:fallback>
					</jsp:plugin>
		  		</td>
		  	</tr>
		  	</table>
		  </div>
		 </div>
		 </div>
	</div>
</div>
</form:form>
<script type="text/javascript" src="view/js/docknavigation.js"></script>
