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
<form:form commandName="employment" id="employment" modelAttribute="employment" enctype="multipart/form-data">
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
								<a href="javascript:void(0);" class="select">Employement Details</a>
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
						    			Employement
						    	</th>
							</tr>
							<tr>
					    		<td colspan="8">
					    			<!-- <a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>  -->
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
								<td><form:input path="employeeId" readonly="true" placeholder="Employee Id" cssClass="required input-disabled"/></td>
								<td><form:input path="lastName" readonly="true" cssClass="input-disabled"  placeholder="Last Name"/></td>
								<td><form:input path="firstName" readonly="true" cssClass="input-disabled"  placeholder="First Name"/></td>
								<td><form:input path="middleName" readonly="true" cssClass="input-disabled"  placeholder="Middle Name"/></td>
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
						<table   width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new">
							<tr>
								<td style="width: 12%"><label>Employement Action</label></td>
								<td style="width: 15%">
									<form:select path="statusCode" >
					    				<form:option value="">Select</form:option>
					    				<form:options items="${empActionList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
								</td>
								<td style="width: 14%"><label>Employement Action Reason</label></td>
								<td style="width: 30%">
									<form:select path="statusReason">
					    				<form:option value="">Select</form:option>
					    				<form:options items="${empActionReasonList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
						    	</td>
							</tr>
							<tr>
								<th colspan="8" style="text-align: left;padding-left: 10px" class="head">
						    	</th>
							</tr>
						</table>
						<table   width="100%" border="0" cellspacing="0" cellpadding="2" class="data-table-new">
							<tr>
								<td style="width: 15%"><label>Status</label></td>
								<td style="width: 15%">
									<form:select path="status">
					    				<form:option value="">Select</form:option>
					    				<form:options items="${statusList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
								</td>
								<td style="width: 15%"><label>FLSA Category</label> </td>
								<td style="width: 21%">
									<form:select path="FLSACategory">
					    				<form:option value="">Select</form:option>
					    				<form:options items="${FLSACategoryList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
						    	</td>
						    	<td style="width: 15%"><label>Leave Status Code</label></td>
								<td>	
									<form:select path="leaveStatusCode">
					    				<form:option value="">Select</form:option>
					    				<form:options items="${leaveStatusCodeList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
						    	</td>
							</tr>
							<tr>
								<td style="width: 15%"><label>Hire Date</label></td>
								<td style="width: 15%">
									<form:hidden path="mostRecentHireDate"/>
						    		<input id="mostRecentHireDateBox" class="easyui-datebox" width="150px"/>
								</td>
								<td style="width: 15%"><label>Classification</label> </td>
								<td style="width: 21%">
									<form:select path="classification">
					    				<form:option value="">Select</form:option>
					    				<form:options items="${classificationList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
						    	</td>
						    	<td style="width: 15%"><label>Leave Reason</label></td>
								<td>	
									<form:select path="leaveReason">
					    				<form:option value="">Select</form:option>
					    				<form:options items="${leaveStatusReasonList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
						    	</td>
							</tr>
							<tr>
								<td style="width: 15%"><label>Previous Hire Date</label></td>
								<td style="width: 15%">
									<form:hidden path="lastHireDate"/>
						    		<input id="lastHireDateBox" class="easyui-datebox" width="150px"/>
								</td>
								<td style="width: 15%">&nbsp;</td>
								<td style="width: 21%">&nbsp;</td>
								<%-- <td style="width: 15%"><label>Employement Category</label> </td>
								<td style="width: 21%">
									<form:select path="employmentCategory">
					    				<form:option value="">Select</form:option>
					    				<form:options items="${employementCategoryList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
						    	</td> --%>
						    	<td style="width: 15%"><label>Leave Start Date</label></td>
								<td>
									<form:hidden path="leaveStartDate"/>
						    		<input id="leaveStartDateBox" class="easyui-datebox" width="150px"/>
								</td>
							</tr>
							<tr>
								<td style="width: 15%"><label>Seniority Date</label></td>
								<td style="width: 15%">
									<form:hidden path="seniorityDate"/>
						    		<input id="seniorityDateBox" class="easyui-datebox" width="150px"/>
								</td>
								<td style="width: 15%"><label>Full Time Equivalency</label> </td>
								<td style="width: 21%">
									<form:input path="fullTimeEquivalency" onKeyPress="return numbersonly(event, true,this.value)" title="Numbers between 0 and 1"/>
						    	</td>
						    	<td style="width: 15%"><label>Expected Leave End Date</label></td>
								<td>
									<form:hidden path="expectedLeaveEndDate"/>
						    		<input id="expectedLeaveEndDateBox" class="easyui-datebox" width="150px"/>
								</td>
							</tr>
							<tr>
								<td style="width: 15%"><label>Benefit Start Date</label></td>
								<td style="width: 15%" colspan="5">
									<form:hidden path="benefitStartDate"/>
						    		<input id="benefitStartDateBox" class="easyui-datebox" width="150px"/>
								</td>
							</tr>
							<tr>
								<td style="width: 15%"><label>Terminination Date</label></td>
								<td style="width: 15%" colspan="5">
									<form:hidden path="terminationDate"/>
						    		<input id="terminationDateBox" class="easyui-datebox" width="150px"/>
								</td>
							</tr>
							
							<tr>
								<th colspan="6" style="text-align: left;padding-left: 10px" class="head">
						    	</th>
							</tr>
						</table>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>
	    				<table id="employementHistoryTable"  class="easyui-datagrid" title="History Table"  style="width:100%;height:170px;table-layout: fixed;"
									data-options="collapsible:true
													,method: 'post'
													,pagination:false
													,emptyMsg: 'No records found'
													,collapsed:false " >
									<thead data-options="frozen:true">
										<tr>
											<th data-options="field:'effectiveDate',width:100"  formatter="formatDetail">Eff Date</th>
											<th data-options="field:'employeeId',width:100">Emp Id</th>
											<th data-options="field:'seqNo',width:100" hidden="true">seq no</th>
										</tr>
									</thead>
									<thead>
										<tr>
											<th data-options="field:'statusCode',width:100">Employement Action</th>
											<th data-options="field:'statusReason',width:100">Employement Action Reason</th>
											<th data-options="field:'status',width:100">Status</th>
											<th data-options="field:'mostRecentHireDate',width:100">Hire Date</th>
											<th data-options="field:'lastHireDate',width:100">Last Hire Date</th>
											<th data-options="field:'seniorityDate',width:100">Seniority Date</th>
											<th data-options="field:'benefitStartDate',width:100">Benefit Start Date</th>
											<th data-options="field:'terminationDate',width:100">Termination Date</th>
											<th data-options="field:'FLSACategory',width:100">FLSA Category</th>
											<th data-options="field:'classification',width:100">Classification</th>
											<th data-options="field:'employmentCategory',width:100">Category</th>
											<th data-options="field:'fullTimeEquivalency',width:100">FTE</th>
											<th data-options="field:'leaveStatusCode',width:100">Leave Status Code</th>
											<th data-options="field:'leaveReason',width:100">Leave Reason</th>
											<th data-options="field:'leaveStartDate',width:100">Leave Start Date</th>
											<th data-options="field:'expectedLeaveEndDate',width:100">Leave End Date</th>
											<th data-options="field:'updatedDate',width:100">Updated Date</th>
											<th data-options="field:'updatedBy',width:100">Updated By</th>
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
	//$("#createEmp").css("display","none");
	$("#employement").attr('class', 'buttonSelect');
	var empId = document.getElementById("employeeId").value;
	fillAllDetails(empId);
	if(empId!=null && empId !="")
		getEmployementDetails(empId);
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
			getEmployementDetails(rec.id);
		}});
}
function save(){
	var empId = $("#employeeId").val();
	var effDate = $('#effectiveDateBox').datebox('getValue');;
	if(!$('#employment').valid() || effDate==''){
		$('#validationErrorMessageId').show();
		return false;
	}
	if(document.getElementById("updateble").value == "no"){
		if(confirm("This record is older than the current record.  Please Confirm")){
			loading();
			$.ajax({
				url:"checkEffectiveDate.htm?empId="+empId+"&effDate="+effDate+"&tableName=EMP_EMPLOYEMENT_HIS",
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
		loading();
		$.ajax({
			url:"checkEffectiveDate.htm?empId="+empId+"&effDate="+effDate+"&tableName=EMP_EMPLOYEMENT",
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
	$('#validationErrorMessageId').hide();
	var fte = $("#fullTimeEquivalency").val();
	if(fte<0 || fte>1){
		alert("Full Time Equivalence must between 0 and 1");
		return false;
	}
	var effectiveDate = $('#effectiveDateBox').datebox('getValue');
	var mostRecentHireDate = $('#mostRecentHireDateBox').datebox('getValue');
	var lastHireDate = $('#lastHireDateBox').datebox('getValue');
	var leaveStartDate = $('#leaveStartDateBox').datebox('getValue');
	var seniorityDate = $('#seniorityDateBox').datebox('getValue');
	var expectedLeaveEndDate = $('#expectedLeaveEndDateBox').datebox('getValue');
	var benefitStartDate = $('#benefitStartDateBox').datebox('getValue');
	var terminationDate = $('#terminationDateBox').datebox('getValue');
	if($('#employment').valid() && effectiveDate!=''){
		document.getElementById("effectiveDate").value=effectiveDate;
		document.getElementById("mostRecentHireDate").value=mostRecentHireDate;
		document.getElementById("lastHireDate").value=lastHireDate;
		document.getElementById("leaveStartDate").value=leaveStartDate;
		document.getElementById("seniorityDate").value=seniorityDate;
		document.getElementById("expectedLeaveEndDate").value=expectedLeaveEndDate;
		document.getElementById("benefitStartDate").value=benefitStartDate;
		document.getElementById("terminationDate").value=terminationDate;
		loading();
		$.ajax({
			url:"saveEmployeeEmployement.htm",
			type: "post",
			data : $("#employment").serialize(),
			error: function(obj){
				alert(obj);
				closeloading();
			},
			success: function(obj){
				getEmployementDetails(document.getElementById("employeeId").value);
				//empHistory(document.getElementById("employeeId").value);
				alert(obj);
				closeloading();
			}});
	}else{
		$('#validationErrorMessageId').show();
	}
}
function getEmployementDetails(rec){
	loading();
	// need to change from ajax to submit
	$.ajax({
		url:"getEmployementDetails.htm?empId="+rec,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			alert(obj);
		},
		success: function(obj){
			document.getElementById("updateble").value="yes";
			setEmployementDetails(obj);
			if(rec!=null)
				empHistory(rec);
			closeloading();
		}});
}
function setEmployementDetails(obj){
	$.each(obj, function(i, item){
		if(i=='effectiveDate')
  			$('#effectiveDateBox').datebox('setValue', item);
		else if(i=='mostRecentHireDate')
	  		$('#mostRecentHireDateBox').datebox('setValue', item);
		else if(i=='lastHireDate')
	  		$('#lastHireDateBox').datebox('setValue', item);
		else if(i=='leaveStartDate')
	  		$('#leaveStartDateBox').datebox('setValue', item);
		else if(i=='seniorityDate')
	  		$('#seniorityDateBox').datebox('setValue', item);
		else if(i=='expectedLeaveEndDate')
	  		$('#expectedLeaveEndDateBox').datebox('setValue', item);
		else if(i=='benefitStartDate')
	  		$('#benefitStartDateBox').datebox('setValue', item);
		else if(i=='terminationDate')
	  		$('#terminationDateBox').datebox('setValue', item);
  		else if($("#"+i) != undefined)
			$("#"+i).val(item);  
	});
	var effDate = $('#effectiveDateBox').datebox('getValue');
	if (effDate != null && effDate !=''){
	document.getElementById('empEffectiveDt').innerHTML= 'Effective Date &nbsp;&nbsp;&nbsp;&nbsp;   ' +effDate;
	}
	$('#effectiveDateBox').datebox('setValue', '');
}
function empHistory(empId){
	$('#employementHistoryTable').datagrid('options').loadMsg = 'Processing, please wait .... ';  // change to other message
	$('#employementHistoryTable').datagrid('loading');  // 
	$.ajax({
		url:"getEmployementDetailsHistory.htm?empId="+empId,
		type: "post",
		dataType: 'json',
		error: function(obj){
			$('#employementHistoryTable').datagrid('loaded');  // hide loading message
		},
		success: function(obj){
			if(obj.length > 0){
				removeGridMessage('#employementHistoryTable','');
				$('#employementHistoryTable').datagrid('loadData',obj); 
				$('#employementHistoryTable').datagrid('loaded');  // hide loading message				
			}else{
				showGridMessage('#employementHistoryTable','History Records not available');
				$('#employementHistoryTable').datagrid('loadData',obj); 
				$('#employementHistoryTable').datagrid('loaded');  // hide loading message
			}
		}});
}
function formatDetail(value,row,index){
	return '<a href="#" onclick="javascript:getEmployementDetailsFromHistoryAjax('+row.seqNo+')">'+value+'</a>';
}
function getEmployementDetailsFromHistoryAjax(id){
	loading();
	$.ajax({
		url:"getEmployementDetailsFromHistoryAjax.htm?seqNo="+id,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			alert(obj);
		},
		success: function(obj){
			document.getElementById("updateble").value="no";
			setEmployementDetails(obj);
			closeloading();
		}});
}
</script>
