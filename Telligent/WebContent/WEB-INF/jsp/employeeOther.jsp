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
<form:form commandName="employeeOther" id="employeeOther" modelAttribute="employeeOther" enctype="multipart/form-data">
<form:hidden path="successMessage"/>
<form:hidden path="errorMessage"/>
<form:hidden path="operation"/>
<input type="hidden" id="updateble">
<div id="contentArea">
<div class="loader"></div>
<div class="contentArea">
	<%@include file="employeeLeft.jsp" %>
 	<div class="innerright" style="min-height: 480px" id="flow">
	 	<a href="javascript:toggle()" title="Hide Nav 13" id="flowtab"></a>
	 	<div style="margin:0px;cursor:auto;" id="tab">
	    <div class="wrap">
	    	<table  width="100%" border="0" cellspacing="0" cellpadding="0">
	    		<tr>
	    			<td>
	    				 <table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right">
						      <tr>
						        <th class="head1" height="25" align="left">
						        <div class="innerpage-breadcrum">
									<a href="dashboard.htm">Dashboard</a> &nbsp;&gt;&nbsp; 
									<a href="javascript:void(0);">Employee</a> &nbsp;&gt;&nbsp; 
									<a href="javascript:void(0);" class="select">Employee Other Details</a>
									<!-- <span style="float: right"><a href="dashboard.htm">Back</a></span>  -->
									<span style="float: right">
				   						<a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>			
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
	    				<table   width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new">
							<tr>
								<th colspan="8" style="text-align: left;padding-left: 10px" class="head">
						    			Employee Other
						    	</th>
							</tr>
							<tr>
					    		<td colspan="8">
					    		<!-- 	<a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>  -->
					    			<span id="empEffectiveDt" style="float: right; color: orange; padding-right: 20px"> </span>
					    		</td>
					    	</tr>
					    	<tr id="validationErrorMessageId" style="display: none">
				              	<td colspan="8">
				              		<div class=validationErrorMessage>Entry cannot be saved because Required data is missing</div>
				              	</td>
				            </tr>
							<tr>
								<td><label>Employee</label></td>
								<td><form:input path="employeeId" readonly="true" cssClass="required input-disabled" placeholder="Employee Id"/></td>
								<td><form:input path="lastName" readonly="true" cssClass="input-disabled" placeholder="Last Name"/></td>
								<td><form:input path="firstName" readonly="true"  cssClass="input-disabled" placeholder="First Name"/></td>
								<td><form:input path="middleName" readonly="true"  cssClass="input-disabled" placeholder="Middle Name"/></td>
								<td><label>Effective Date</label></td>
								<td>
									<form:hidden path="effectiveDate"/>
						    		<input id="effectiveDateBox" class="required easyui-datebox" required="true" width="150px"/>
						    	</td>
							</tr>
							<tr>
								<th colspan="8" style="text-align: left;padding-left: 10px" class="head">
						    	</th>
							</tr>
						</table>   
						<table   width="100%" border="0" cellspacing="0" cellpadding="2" class="data-table-new">
							<tr>
								<td style="width: 15%"><label>Gender</label><span style="color:red">*</span></td>
								<td style="width: 15%">
									<form:select path="gender" cssClass="required">
					    				<form:option value="">Select</form:option>
					    				<form:option value="male">Male</form:option>
					    				<form:option value="female">Female</form:option>
					    			</form:select>
								</td>
								<td style="width: 15%"><label>CitizenShip</label> </td>
								<td style="width: 21%">
									<form:select path="citizenShip" onchange="citizenChange(this.value)">
					    				<form:option value="">Select</form:option>
					    				<form:option value="yes">Yes</form:option>
					    				<form:option value="no">No</form:option>
					    			</form:select>
					    			<form:select path="city" cssClass="required">
					    				<form:option value="">Select City</form:option>
					    				<form:options items="${cityList}" itemLabel="city" itemValue="id"></form:options>
					    			</form:select>
						    	</td>
						    	<td style="width: 15%"><label>Veteran Status</label></td>
								<td>	
									<form:select path="veteranStatus">
					    				<form:option value="">Select</form:option>
					    				<form:options items="${veteranList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
						    	</td>
							</tr>
							<tr>
								<td style="width: 15%"><label>Ethinicity</label></td>
								<td style="width: 15%">
									<form:select path="ethinicity">
					    				<form:option value="">Select</form:option>
					    				<form:options items="${ethinicityList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
								</td>
								<td style="width: 15%"><label>Visa Type</label> </td>
								<td style="width: 21%" colspan="4">
									<form:select path="visaType">
					    				<form:option value="">Select</form:option>
					    				<form:options items="${visaTypeList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
						    	</td>
							</tr>
							
							<tr>
								<td style="width: 15%"><label>Marital Status</label></td>
								<td style="width: 15%">
									<form:select path="maritalStatus">
					    				<form:option value="">Select</form:option>
					    				<form:options items="${maritalList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
								</td>
								<td style="width: 15%"><label>i9 Exp Date</label> </td>
								<td style="width: 21%" colspan="4">
									<form:hidden path="i9ExpDate"/>
									<input id="i9ExpDateBox" class="easyui-datebox" width="150px"/>
						    	</td>
							</tr>
							
							<tr>
								<td style="width: 15%"><label>Disabled</label></td>
								<td style="width: 15%">
									<form:select path="disability" onchange="disabilityChange(this.value)">
					    				<form:option value="">Select</form:option>
					    				<form:option value="yes">Yes</form:option>
					    				<form:option value="no">No</form:option>
					    			</form:select>
								</td>
								<td style="width: 15%" id="disabilityId"><label>Disability Description</label> </td>
								<td style="width: 21%" colspan="4">
									<form:input path="disabilityDesc" cssClass="required" maxlength="65"/>
						    	</td>
							</tr>
						</table>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>
	    				<table id="employeeOtherHistoryTable"  class="easyui-datagrid" title="History Table"  style="width:100%;height:170px;table-layout: fixed;"
									data-options="collapsible:true
													,method: 'post'
													,pagination:false
													,emptyMsg: 'No records found'
													,collapsed:false " >
									<thead data-options="frozen:true">
										<tr>
											<th data-options="field:'effectiveDate',width:100"  formatter="formatDetail">Eff Date</th>
											<th data-options="field:'seqNo',width:100" hidden="true">seq no</th>
										</tr>
									</thead>
									<thead>
										<tr>
											<th data-options="field:'gender',width:100">Gender</th>
											<th data-options="field:'ethinicity',width:100">Ethinicity</th>
											<th data-options="field:'maritalStatus',width:100">Marital Status</th>
											<th data-options="field:'citizenShip',width:100">CitizenShip</th>
											<th data-options="field:'visaType',width:100">Visa Type</th>
											<th data-options="field:'i9ExpDate',width:100">i9 Exp Date</th>
											<th data-options="field:'veteranStatus',width:100">Veteran Status</th>
											<th data-options="field:'disability',width:100">Disability</th>
											<th data-options="field:'disabilityDesc',width:100">Disability Description</th>
											<th data-options="field:'updatedDate',width:100">Updated Date</th>
			                  				<th data-options="field:'updatedBy',width:100">Updated By</th>
											<!-- <th data-options="field:'emergencyLastName',width:100">Emergency Last Name</th>
											<th data-options="field:'emergencyFirstName',width:100">Emergency First Name</th>
											<th data-options="field:'emergencyRelationShip',width:100">Emergency RelationShip</th>
											<th data-options="field:'emergencyHomePhone',width:100">Emergency Home Phone</th>
											<th data-options="field:'emergencyMobilePhone',width:100">Emergency Mobile Phone</th>
											<th data-options="field:'emergencyEmail',width:100">emergencyEmail</th> -->
										</tr>
									</thead>
						</table>
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
<script type="text/javascript">
$(document).ready(function(){
	highLightTab();
	$("#otherData").attr('class', 'buttonSelect');
	$("#disabilityId").toggle(false);
	$("#disabilityDesc").toggle(false);
	$("#city").toggle(false);
	var empId = document.getElementById("employeeId").value;
	fillAllDetails(empId);
	if(empId!=null && empId !="")
		getEmployeeOtherDetails(empId);
});
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
	fillAllDetails(rec.id);
	loading();
	// need to change from ajax to submit
	$.ajax({
		url:"getEmployeeDetails.htm?empId="+rec.id,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			alert(obj);
		},
		success: function(obj){
			document.getElementById("updateble").value="yes";
			$.each(obj, function(i, item){
				if(i=='firstName' ||i=='lastName' ||i=='middleName' ||i=='employeeId'){
			  		$("#"+i).val(item);   				  		
			  	}			  		
			});
			getEmployeeOtherDetails(rec.id);
		}});
}
function getEmployeeOtherDetails(rec){
	loading();
	// need to change from ajax to submit
	$.ajax({
		url:"getEmployeeOtherDetails.htm?empId="+rec,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			alert(obj);
		},
		success: function(obj){
			document.getElementById("updateble").value="yes";
			setEmpOtherDetails(obj);
			if(rec!=null)
				empHistory(rec);
			closeloading();
		}});
}
function empHistory(empId){
	$('#employeeOtherHistoryTable').datagrid('options').loadMsg = 'Processing, please wait .... ';  // change to other message
	$('#employeeOtherHistoryTable').datagrid('loading');  // 
	$.ajax({
		url:"getEmployeeOtherDetailsHistory.htm?empId="+empId,
		type: "post",
		dataType: 'json',
		error: function(obj){
			$('#employeeOtherHistoryTable').datagrid('loaded');  // hide loading message
		},
		success: function(obj){
			if(obj.length > 0){
				removeGridMessage('#employeeOtherHistoryTable','');
				$('#employeeOtherHistoryTable').datagrid('loadData',obj); 
				$('#employeeOtherHistoryTable').datagrid('loaded');  // hide loading message				
			}else{
				showGridMessage('#employeeOtherHistoryTable','History Records not available');
				$('#employeeOtherHistoryTable').datagrid('loadData',obj); 
				$('#employeeOtherHistoryTable').datagrid('loaded');  // hide loading message
			}
		}});
}
function formatDetail(value,row,index){
	return '<a href="#" onclick="javascript:getEmployeeOtherDetailsFromHistoryAjax('+row.seqNo+')">'+value+'</a>';
}
function getEmployeeOtherDetailsFromHistoryAjax(id){
	loading();
	$.ajax({
		url:"getEmployeeOtherDetailsFromHistoryAjax.htm?seqNo="+id,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			alert(obj);
		},
		success: function(obj){
			document.getElementById("updateble").value="no";
			setEmpOtherDetails(obj);
			closeloading();
		}});
}
function setEmpOtherDetails(obj){
	$.each(obj, function(i, item){
	  	if(i=='i9ExpDate'){
	  		$('#i9ExpDateBox').datebox('setValue', item);
	  		document.getElementById("i9ExpDate").value = item;
	  	}else if(i=='effectiveDate'){
  			//document.getElementById(i).value=effectiveDate;
  			$('#effectiveDateBox').datebox('setValue', item);
  		}else if(i=='citizenShip'){
  			$("#"+i).val(item);
  			citizenChange(item);
  		}else if(i=='disability'){
  			$("#"+i).val(item);
  			disabilityChange(item);
  		}else if($("#"+i) != undefined)
			$("#"+i).val(item);  
	  	
	});
	var effDate = $('#effectiveDateBox').datebox('getValue');
	if (effDate != null && effDate !=''){
	document.getElementById('empEffectiveDt').innerHTML= 'Effective Date &nbsp;&nbsp;&nbsp;&nbsp;   ' +effDate;
	}
	$('#effectiveDateBox').datebox('setValue', '');
}
function save(){
	var empId = $("#employeeId").val();
	var effDate = $('#effectiveDateBox').datebox('getValue');;
	if(!$('#employeeOther').valid() || effDate==''){
		$('#validationErrorMessageId').show();
		return false;
	}
	if(document.getElementById("updateble").value == "no"){
		if(confirm("This record is older than the current record.  Please Confirm")){
			$.ajax({
				url:"checkEffectiveDate.htm?empId="+empId+"&effDate="+effDate+"&tableName=EMP_OTHER_DATA_HIS",
				type: "post",
				error: function(obj){
					closeloading();
				},
				success: function(obj){
					closeloading();
					if(obj == 'true'){
						if(confirm('Effective Date less than that of an existing record and History record is being inserted'))
							saveEmp();
					}else{
						saveEmp();
					}
				}});
		}
	}else{
		$.ajax({
			url:"checkEffectiveDate.htm?empId="+empId+"&effDate="+effDate+"&tableName=EMP_OTHER_DATA",
			type: "post",
			error: function(obj){
				closeloading();
			},
			success: function(obj){
				closeloading();
				if(obj == 'true'){
					if(confirm('Effective Date less than that of an existing record and History record is being inserted'))
						saveEmp();
				}else{
					saveEmp();
				}
			}});
	}
}
function saveEmp(){
	var effectiveDate = $('#effectiveDateBox').datebox('getValue');
	var i9ExpDate = $('#i9ExpDateBox').datebox('getValue');
	$('#validationErrorMessageId').hide();
	if($('#employeeOther').valid() && effectiveDate!=''){
		document.getElementById("effectiveDate").value=effectiveDate;
		document.getElementById("i9ExpDate").value=i9ExpDate;
		loading();
		$.ajax({
			url:"saveEmployeeOtherDetails.htm",
			type: "post",
			data : $("#employeeOther").serialize(),
			error: function(obj){
				alert(obj);
				closeloading();
			},
			success: function(obj){
				if(obj == "Details Saved Succuessfully"){
					alert("Details saved successfully");
					empHistory(document.getElementById("employeeId").value);
				}else{
					var str = obj.split(":;");
					alert(str[1]);
				}
				var effDate = $('#effectiveDateBox').datebox('getValue');
				if (effDate != null && effDate !=''){
				document.getElementById('empEffectiveDt').innerHTML= 'Effective Date &nbsp;&nbsp;&nbsp;&nbsp;   ' +effDate;
				}
				$('#effectiveDateBox').datebox('setValue', '');
				closeloading();
			}});
	}else{
		$('#validationErrorMessageId').show();
	}
}
function disabilityChange(val){
	if(val == 'yes'){
		$("#disabilityId").toggle(true);
		$("#disabilityDesc").toggle(true);
	}else{
		$("#disabilityId").toggle(false);
		$("#disabilityDesc").toggle(false);
	}
}
function citizenChange(val){
	if(val == 'no'){
		$("#city").toggle(true);
	}else{
		$("#city").toggle(false);
	}
}
</script>
