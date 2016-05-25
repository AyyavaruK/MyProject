<%@ include file="taglib.jsp" %>
<title>Dashboard</title>
<link href="view/css/colorbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="view/css/popup.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/icon.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/demo.css">
<script type="text/javascript" src="view/js/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="view/js/app/telligentCommon.js"></script>
<script type="text/javascript" src="view/js/popup.js"></script>
<form:form commandName="employee">
<div id="contentArea">
<div class="loader"></div>
<div class="contentArea">
	<div class="innerleft"  id="col1">
    	<table class="leftAccordion" cellspacing="1" cellpadding="5" id="employeeSearchTable">
    		<tr>
		        <th colspan="2" class="head" height="25" align="left">Search</th>
		    </tr>
		    <tr>
		    	<td>Last Name</td>
		    	<td>
		    		<input class="easyui-combobox" id="lastNameInputId" style="width:200px" data-options="
						url:'searchLastName.htm',
						method:'post',
						mode: 'remote',
						valueField: 'id',
						textField: 'value',
						selectOnNavigation:false,
						onSelect:searchLastNameSelect
						">
		    	</td>
		    </tr>
		    <tr>
		    	<td>First Name</td>
		    	<td>
		    		<input class="easyui-combobox" id="firstNameInputId" style="width:200px" data-options="
						url:'searchFirstName.htm',
						method:'post',
						mode: 'remote',
						valueField: 'id',
						textField: 'value',
						selectOnNavigation:false,
						onSelect:searchFirstNameSelect
						">
		    	</td>
		    </tr>
		    <tr>
		    	<td>Employee Id</td>
		    	<td>
		    		<input class="easyui-combobox" id="employeeInpuId" style="width:200px" data-options="
						url:'searchEmpId.htm',
						method:'post',
						mode: 'remote',
						valueField: 'id',
						textField: 'value',
						selectOnNavigation:false,
						onSelect:searchEmpIdSelect
						">
		    	</td>
		    </tr>
		    <tr>
		    	<td>Team Name</td>
		    	<td>
		    		<input class="easyui-combobox" id="teamInputId" style="width:200px" data-options="
						url:'searchTeamEmployees.htm',
						method:'post',
						mode: 'remote',
						valueField: 'id',
						textField: 'value',
						selectOnNavigation:false,
						onSelect:searchTeamEmployeesSelect
						">
		    	</td>
		    </tr>
    	</table>  
	</div>
 	<div class="innerright"  id="flow">
	 	<a href="javascript:toggle()" title="Hide Nav 13" id="flowtab"></a>
	 	<div style="margin:0px;cursor:auto;" id="tab">
	    <div class="wrap">  
		    <table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right">
		      <tr>
		        <th class="head1" height="25" align="left">
		        <div class="innerpage-breadcrum">
					<a href="dashboard.htm">Dashboard</a> &nbsp;&gt;&nbsp; 
					<a href="javascript:void(0);">Employee</a> &nbsp;&gt;&nbsp; 
					<a href="javascript:void(0);" class="select">Employee Details</a>
					<span style="float: right"><a href="dashboard.htm">Back</a></span>
				</div>
				</th>
		      </tr>
		    </table>
		    <table  width="100%" border="0" cellspacing="1" cellpadding="5" style="padding-top: 40px" id="employeeInformationTable" class="data-table">
		    	<tr>
		    		<th colspan="6" style="text-align: left;padding-left: 10px" class="head">
		    			Employee Information
		    		</th>
		    	</tr>
		    	<tr>
		    		<td style="width: 20%">Employee Id</td>
		    		<td style="width: 30%"><form:input path="employeeId"/></td>
		    		<td style="width: 20%">Employee No.</td>
		    		<td style="width: 30%"><form:input path="employeeNo"/></td>
		    	</tr>
		    	<tr>
		    		<td>Last Name</td>
		    		<td><form:input path="lastName"/></td>
		    		<td>First Name</td>
		    		<td><form:input path="employeeName"/></td>
		    	</tr>
		    	<tr>
		    		<td>Badge No.</td>
		    		<td><form:input path="badgeNo"/></td>
		    		<td>Social Secure No.</td>
		    		<td><form:input path="socialSecNo"/></td>
		    	</tr>
		    	<tr>
		    		<td>Date Of Birth</td>
		    		<td><form:input path="dateOfBirth"/></td>
		    		<td>Effective Date</td>
		    		<td><form:input path="effectiveDate"/></td>
		    	</tr>
		    	<tr>
		    		<td>Citizenship</td>
		    		<td><form:input path="citizenship"/></td>
		    		<td>Ethinicity</td>
		    		<td><form:input path="ethinicity"/></td>
		    	</tr>
		    	<tr>
					<td>Marital Status</td>
		    		<td><form:input path="maritalStatus"/></td>
		    		<td>Veteran Status</td>
		    		<td><form:input path="veteranStatus"/></td>
		    	</tr>
		    	<tr>
		    		<td>Is Minor</td>
		    		<td><form:checkbox path="minor" id="minor"/></td>
		    		<td>Gender</td>
		    		<td><form:input path="gender"/></td>
		    	</tr>
		    	<tr>
		    		<td>Visa Type</td>
		    		<td><form:input path="visaType"/></td>
		    		<td>Visa Expiry Date</td>
		    		<td><form:input path="visaExpDate"/></td>
		    	</tr>
		    	<tr>
		    		<td>Disabled</td>
		    		<td><form:input path="disabled"/></td>
		    		<td>Disabled Description</td>
		    		<td><form:input path="disablityDesc"/></td>
		    	</tr>
		    </table>
		    
		     <table  width="100%" border="0" cellspacing="1" cellpadding="5" style="padding-top: 40px" id="personalInformationTable" class="data-table">
		    	<tr>
		    		<th colspan="6" style="text-align: left;padding-left: 10px" class="head">
		    			Personal Information
		    		</th>
		    	</tr>
		    	<tr>
		    		<td style="width: 20%">Home Phone</td>
		    		<td style="width: 30%"><form:input path="homePhone"/></td>
		    		<td style="width: 20%">Cell Phone</td>
		    		<td style="width: 30%"><form:input path="cellPhone"/></td>
		    	</tr>
		    	<tr>
		    		<td>Email</td>
		    		<td><form:input path="personalEmail"/></td>
		    		<td>&nbsp;</td>
		    		<td>&nbsp;</td>
		    	</tr>
		    	<tr>
		    		<td>Address Line 1</td>
		    		<td><form:input path="addressLine1"/></td>
		    		<td>Address Line 2</td>
		    		<td><form:input path="addressLine2"/></td>
		    	</tr>
		    	<tr>
		    		<td>City</td>
		    		<td><form:input path="city"/></td>
		    		<td>State</td>
		    		<td><form:input path="state"/></td>
		    	</tr>
		    	<tr>
		    		<td>Zip Code</td>
		    		<td><form:input path="zipcode"/></td>
		    		<td></td>
		    		<td></td>
		    	</tr>
		    </table>
		    <table  width="100%" border="0" cellspacing="1" cellpadding="5" style="padding-top: 40px" id="workInformationTable" class="data-table">
		    	<tr>
		    		<th colspan="6" style="text-align: left;padding-left: 10px" class="head">
		    			Work Information
		    		</th>
		    	</tr>
		    	<tr>
		    		<td style="width: 20%">Work Phone</td>
		    		<td style="width: 30%"><form:input path="workPhone"/></td>
		    		<td style="width: 20%">Work Cell phone</td>
		    		<td style="width: 30%"><form:input path="workCellPhone"/></td>
		    	</tr>
		    	<tr>
		    		<td>Work Extension</td>
		    		<td><form:input path="workExt"/></td>
		    		<td>Work Email</td>
		    		<td><form:input path="workEmail"/></td>
		    	</tr>
		    </table>
		    <table  width="100%" border="0" cellspacing="1" cellpadding="5" style="padding-top: 40px" id="emergencyInformationTable" class="data-table">
		    	<tr>
		    		<th colspan="6" style="text-align: left;padding-left: 10px" class="head">
		    			Emergency Information
		    		</th>
		    	</tr>
		    	<tr>
		    		<td style="width: 20%">Last Name</td>
		    		<td style="width: 30%"><form:input path="emergencyLastName"/></td>
		    		<td style="width: 20%">First Name</td>
		    		<td style="width: 30%"><form:input path="emergencyFirstName"/></td>
		    	</tr>
		    	<tr>
		    		<td>Cell Phone</td>
		    		<td><form:input path="emergencyCellPhone"/></td>
		    		<td>Home Phone</td>
		    		<td><form:input path="emergencyHomePhone"/></td>
		    	</tr>
		    	<tr>
		    		<td>RelationShip</td>
		    		<td><form:input path="emergencyRelationShip"/></td>
		    		<td>Email</td>
		    		<td><form:input path="emergencyEmail"/></td>
		    	</tr>
		    </table>
	  </div>
	  </div>
  </div>
  </div>
</div> 
</form:form>
<script type="text/javascript" src="view/js/docknavigation.js"></script>
<script type="text/javascript">
	function searchLastNameSelect(rec){
		$('#firstNameInputId').combobox('clear');
		$('#employeeInpuId').combobox('clear');
		$('#teamInputId').combobox('clear');
		getEmployeeDetails(rec);
	}
	function searchFirstNameSelect(rec){
		$('#lastNameInputId').combobox('clear');
		$('#employeeInpuId').combobox('clear');
		$('#teamInputId').combobox('clear');
		getEmployeeDetails(rec);
	}
	function searchEmpIdSelect(rec){
		$('#lastNameInputId').combobox('clear');
		$('#firstNameInputId').combobox('clear');
		$('#teamInputId').combobox('clear');
		getEmployeeDetails(rec);
	}
	function searchTeamEmployeesSelect(rec){
		$('#lastNameInputId').combobox('clear');
		$('#firstNameInputId').combobox('clear');
		$('#employeeInpuId').combobox('clear');
		getEmployeeDetails(rec);
	}
	function getEmployeeDetails(rec){
		loading();
		$.ajax({
   			url:"getEmployeeDetails.htm?empId="+rec.id,
   			type: "post",
   			dataType: 'json',
   			error: function(obj){
   				console.log("error");
   				alert(obj);
   			},
   			success: function(obj){
   				$.each(obj, function(i, item){
   				  	if(i=='minor'){
   				  		if(item)
   				  			document.getElementById(i).checked = true;
   				  		else
   				  			document.getElementById(i).checked = false;
   				  	}else{
   				  		$("#"+i).val(item);   				  		
   				  	}
   				});
   				closeloading();
   			}});
	}
</script>  
<!-- </div> -->
