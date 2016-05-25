<%@ include file="taglib.jsp" %>
<title>Dashboard</title>
<link href="view/css/style.css" rel="stylesheet" type="text/css" />
<link href="view/css/colorbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="view/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="view/js/jquery/jquery.validate.js"></script>
<script src="view/js/jquery/jquery.colorbox.js"></script>

<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/icon.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/demo.css">
<script type="text/javascript" src="view/js/jquery/jquery.easyui.min.js"></script>
	
<div class="contentArea">
  <div class="innerleft">
    <table class="leftAccordion" cellspacing="1" cellpadding="5" >
      <tr>
        <th class="head" height="25" align="left">His Teams</th>
      </tr>
      <c:forEach items="${teams}" var="item" varStatus="i">
      <tr>
        <td height="20"><a href="javascript:showTeamEmployees('${item.teamName}','${item.teamId}');">${item.teamName}</a></td>
      </tr>
      </c:forEach>
    </table>
  </div>
  <div class="innerright">
    <table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right">
      <tr>
        <th class="head1" height="25" align="left">Employee Salaries</th>
      </tr>
    </table>
    <table>
    <tr>
    	<td height="20"><a class="inline cboxElement" href="#inline_content"><img alt="" width="100px" title="Add Employee" height="100px" src="view/images/addEmployee.png"></a></td>
    </tr>
    </table>
    <table id="tt" class="easyui-datagrid" title="Employee List" style="width:100%;height:350px"
			data-options="collapsible:true
							,url:'getTeamEmployees.htm?teamName=${teamName}&teamId=${teamId}'
							,method: 'get'
							,pagination:true
							,emptyMsg: 'No records found'
							,pageSize:20">
			<thead>
				<tr>
					<th data-options="field:'employeeId',width:150">Employee ID</th>
					<th data-options="field:'employeeName',width:150">Employee Name</th>
					<th data-options="field:'lastName',width:150,align:'right'">Last Name</th>
					<th data-options="field:'email',width:150,align:'right'">Email</th>
					<th data-options="field:'salary',width:150,align:'right'">Salary</th>
				</tr>
			</thead>
		</table>
  </div>
</div>
<div style="display:none">
  <div id="inline_content" style="padding:10px; background:#fff;">
    <form:form action="employeeSubmit.htm" method="post" commandName="employeeForm">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td align="left" valign="middle" width="17%" height="35">Employee Name</td>
	        <td align="left" valign="middle" width="3%">:</td>
	        <td align="left" valign="middle" width="80%"><form:input path="employeeName" cssClass="textfield"/></td>
	      </tr>
	      <tr>
	        <td align="left" valign="middle" height="35">Last Name</td>
	        <td align="left" valign="middle">:</td>
	        <td align="left" valign="middle"><form:input path="lastName" class="textfield"/></td>
	      </tr>
	      <tr>
	        <td align="left" valign="middle" height="35">E-mail</td>
	        <td align="left" valign="middle">:</td>
	        <td align="left" valign="middle"><form:input path="email" class="textfield"/></td>
	      </tr>
	      <tr>
	        <td align="left" valign="middle" height="35">Team Name</td>
	        <td align="left" valign="middle">:</td>
	        <td align="left" valign="middle">
	        	<form:select path="teamId" class="textfield">
	        		<form:option value="-1">Select</form:option>
	        		<c:forEach items="${employeeForm.teamsList}" var="item" varStatus="i">
	        			<form:option value="${item.teamId}">${item.teamName}</form:option>
	        		</c:forEach>
	        	</form:select>
	        </td>
	      </tr>
	      <tr>
	        <td align="left" valign="middle" height="35">Salary</td>
	        <td align="left" valign="middle">:</td>
	        <td align="left" valign="middle"><form:input path="salary" class="textfield"/></td>
	      </tr>
	      <tr>
	        <td align="left" valign="middle" height="35">&nbsp;</td>
	        <td align="left" valign="middle">&nbsp;</td>
	        <td align="left" valign="middle"><input type="button" onclick="javascript:saveEmployeeDetails();" value="Submit" class="loginButton"/></td>
	      </tr>
	    </table>
    </form:form>
  </div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$(".inline").colorbox({inline:true, width:"80%"});
		$("#click").click(function(){ 
			$('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
			return false;
		});
		
		$("a").click(function() {
		      // remove classes from all
		      $("a").removeClass("active");
		      // add class to the one we clicked
		      $(this).addClass("active");
		   });
		
	});
	function showTeamEmployees(teamName,teamId){
		$.ajax({
			url:"getTeamEmployees.htm?teamName="+teamName+"&teamId="+teamId,
			type: "GET",
			dataType: 'json',
			error: function(obj){
			},
			success: function(obj){
				$('#tt').datagrid('loadData',obj); 
				$('#tt').prop('title', teamName+" Employee List");
			}});
		
	}
	function saveEmployeeDetails(){
		$.ajax({
			url:"saveEmployeeDetails.htm",
			type: "post",
			data: $('#employeeForm').serialize(),
			error: function(obj){
				alert(obj);
			},
			success: function(obj){
				alert(obj);
				window.parent.jQuery.colorbox.close(); 
				return false;
			}}); 
	}
</script>