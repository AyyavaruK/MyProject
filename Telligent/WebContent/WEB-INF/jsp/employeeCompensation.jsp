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
<form:form commandName="employeeComp" id="employeeComp" modelAttribute="employeeComp" enctype="multipart/form-data">
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
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	    	<tr>
	    		<td>
	    			<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right">
				      <tr>
				        <th class="head1" height="25" align="left">
				        <div class="innerpage-breadcrum">
							<a href="dashboard.htm">Dashboard</a> &nbsp;&gt;&nbsp; 
							<a href="javascript:void(0);">Employee</a> &nbsp;&gt;&nbsp; 
							<a href="javascript:void(0);" class="select">Employee Compensation Details</a>
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
					    			Employee Compensation
					    	</th>
						</tr>
						<tr>
				    		<td colspan="8">
				    			<!-- <a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>  -->
				    			<span id="empEffectiveDt" style="float: right; color: orange; padding-right: 20px"> </span> 
				    		</td>
				    	</tr>
				    	<tr id="compValidationErrorMessageId" style="display: none">
			              	<td colspan="8">
			              		<div class=validationErrorMessage>Entry cannot be saved because Required data is missing</div>
			              	</td>
			            </tr>
						<tr>
							<td><label>Employee</label></td>
							<td><form:input path="employeeId" readonly="true" cssClass="required input-disabled" placeholder="Employee Id"/></td>
							<td><form:input path="lastName" readonly="true" cssClass="input-disabled" placeholder="Last Name"/></td>
							<td><form:input path="firstName" readonly="true" cssClass="input-disabled" placeholder="First Name"/></td>
							<td><form:input path="middleName" readonly="true" cssClass="input-disabled" placeholder="Middle Name"/></td>
							<td nowrap="nowrap"><label>Effective Date</label><span style="color: red">*</span></td>
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
							<td style="width: 12%" nowrap="nowrap"><label>Compensation Action</label><span style="color: red">*</span></td>
							<td style="width: 15%">
								<form:select path="compActionType" cssClass="required" >
				    				<form:option value="">Select</form:option>
				    				<form:options items="${compActionList}" itemValue="id" itemLabel="value"/>
				    			</form:select>
							</td>
							<td style="width: 14%" nowrap="nowrap"><label>Compensation Action Reason</label><span style="color: red">*</span></td>
							<td style="width: 30%">
								<form:select path="compActionReason" cssClass="required">
				    				<form:option value="">Select</form:option>
				    				<form:options items="${compActionReasonList}" itemValue="id" itemLabel="value"/>
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
							<td style="width: 15%"><label>Pay Entity</label><span style="color: red">*</span></td>
							<td style="width: 15%">
								<form:select path="payEntity" cssClass="required">
				    				<form:option value="">Select</form:option>
				    				<form:options items="${payEntityList}" itemValue="id" itemLabel="value"/>
				    			</form:select>
							</td>
							<td style="width: 15%" nowrap="nowrap"><label style="float: right;">Last Perf Evaluation Date</label> </td>
							<td style="width: 21%">
								<form:hidden path="lastperfEvaluationDate"/>
					    		<input id="lastperfEvaluationDateBox" class="easyui-datebox" width="150px"/>
					    	</td>
					    	<td style="width: 15%"><label>Scheduled Hours</label><span style="color: red">*</span></td>
							<td>	
								<form:input path="scheduledHours" cssClass="required" onKeyPress="return numbersonly(event, true,this.value)"/>
					    	</td>
						</tr>
						<tr>
							<td><label>Pay Group</label><span style="color: red">*</span></td>
							<td>	
								<form:select path="payGroup" cssClass="required">
				    				<form:option value="">Select</form:option>
				    				<form:options items="${payGroupList}" itemValue="id" itemLabel="value"/>
				    			</form:select>
							</td>
							<td><label style="float: right;">Performance Grade</label> </td>
							<td>	
								<form:select path="grade">
				    				<form:option value="">Select</form:option>
				    				<form:options items="${gradeList}" itemValue="id" itemLabel="value"/>
				    			</form:select>
					    	</td>
					    	<td nowrap="nowrap"><label>Scheduled Hours Frequency</label><span style="color: red">*</span></td>
							<%-- <td><form:input path="hoursFrequency" cssClass="required" onKeyPress="return numbersonly(event, true,this.value)"/></td> --%>
							<td>	
								<form:select path="hoursFrequency" cssClass="required">
				    				<form:option value="">Select</form:option>
				    				<form:options items="${baseRateFreqList}" itemValue="id" itemLabel="value"/>
				    			</form:select>
							</td>
						</tr>
						<tr>
							<td><label>Rate Frequency</label><span style="color: red">*</span></td>
							<td>
								<form:select path="payFrequency" cssClass="required">
				    				<form:option value="">Select</form:option>
				    				<form:options items="${payFreqList}" itemValue="id" itemLabel="value"/>
				    			</form:select>
							</td>
							<td><label style="float: right;" nowrap="nowrap">Next Performance Date</label> </td>
							<td>
								<form:hidden path="nextEvalDueDate"/>
					    		<input id="nextEvalDueDateBox" class="easyui-datebox" width="150px"/>
					    	</td>
					    	<td><label>Pay Period Hours</label></td>
					    	<td>
								<form:input path="payPeriodHours" readonly="true" onKeyPress="return numbersonly(event, true,this.value)"/>
					    	</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
					    	<td><label>Weekly Hours</label></td>
							<td>
								<form:input path="weeklyHours" readonly="true" onKeyPress="return numbersonly(event, true,this.value)"/>
					    	</td>
						</tr>
						<tr>
							<th colspan="6" style="text-align: left;padding-left: 10px" class="head">
					    	</th>
						</tr>
					</table>
					<table  width="100%" border="0" cellspacing="0" cellpadding="2" class="data-table-new">
						<tr>
							<td style="width: 15%"><label>Base Rate</label><span style="color: red">*</span></td>
							<td style="width: 15%">
								<form:input path="baseRate" cssClass="required" onKeyPress="return numbersonly(event, true,this.value)"/>
							</td>
							<td style="width:15%"><label style="float: right;">Default Earning Code<span style="color: red">*</span></label> </td>
							<td style="width:21%">
								<form:select path="defaultEarningCode" cssClass="required">
				    				<form:option value="">Select</form:option>
				    				<form:options items="${defaultEarningCodeList}" itemValue="id" itemLabel="value"/>
				    			</form:select>
					    	</td>
					    	<td style="width: 15%"><label>Performance Plan</label></td>
							<td>	
								<form:select path="performacePlan">
				    				<form:option value="">Select</form:option>
				    				<form:options items="${perfPlanList}" itemValue="id" itemLabel="value"/>
				    			</form:select>
					    	</td>
						</tr>
						<tr>
							<td><label>Base Rate Frequency</label><span style="color: red">*</span></td>
							<td>	
								<form:select path="baseRateFrequency" cssClass="required">
				    				<form:option value="">Select</form:option>
				    				<form:options items="${baseRateFreqList}" itemValue="id" itemLabel="value"/>
				    			</form:select>
							</td>
							<td><label style="float: right;">Eligible Job Group</label> </td>
							<td>	
								<form:select path="eligibleJobGroup">
				    				<form:option value="">Select</form:option>
				    				<form:options items="${jobGroupList}" itemValue="id" itemLabel="value"/>
				    			</form:select>
					    	</td>
					    	<td><label>Bonus Plan</label></td>
							<td>
								<form:select path="bonusPlan">
				    				<form:option value="">Select</form:option>
				    				<form:options items="${bonusPlanList}" itemValue="id" itemLabel="value"/>
				    			</form:select>
							</td>
						</tr>
						<tr>
							<td><label>Period Rate</label></td>
							<td>
								<form:input path="periodRate" readonly="true" onKeyPress="return numbersonly(event, true,this.value)"/>
							</td>
							<td><label style="float: right;">Use Job Rate</label> </td>
							<td>
								<form:select path="useJobRate">
				    				<form:option value="">Select</form:option>
				    				<form:option value="Yes">Yes</form:option>
				    				<form:option value="No">No</form:option>
				    			</form:select>
					    	</td>
					    	<td>&nbsp;</td>
					    	<td>&nbsp;</td>
						</tr>
						<tr>
					    	<td><label>Hourly Rate</label></td>
							<td>
								<form:input path="hourlyRate" onKeyPress="return numbersonly(event, true,this.value)"/>
					    	</td>
					    	<td ><label>Comp Ratio</label></td>
							<td >
								<form:input path="compRatio" readonly="true" onKeyPress="return numbersonly(event, true,this.value)"/>
					    	</td>
					    	<td><label>Quartile</label></td>
							<td>
								<form:input path="quartile" readonly="true"/>
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
	    			<table id="employeeCompensationHistoryTable" class="easyui-datagrid" title="History Table"  style="width:100%;height:200px;table-layout: fixed;"
							data-options="collapsible:true
											,method: 'post'
											,pagination:false
											,emptyMsg: 'No records found'
											,collapsed:false " >
							<thead data-options="frozen:true">
								<tr>
									<th data-options="field:'effectiveDate',width:100" formatter="formatDetail">Eff Date</th>
									<th data-options="field:'employeeId',width:100">Emp Id</th>
								</tr>
							</thead>
							<thead>
								<tr>
									<th data-options="field:'compActionType',width:130">Comp Action Type</th>
									<th data-options="field:'compActionReason',width:130">Comp Action Reason</th>
									<th data-options="field:'payEntity',width:100">Pay Entity</th>
									<th data-options="field:'lastperfEvaluationDate',width:140">Last Perf Evaluation Date</th>
									<th data-options="field:'scheduledHours',width:100">Scheduled Hours</th>
									<th data-options="field:'payGroup',width:100">Pay Group</th>
									<th data-options="field:'grade',width:110">Performance Grade</th>
									<th data-options="field:'hoursFrequency',width:100">Hours Frequency</th>
									<th data-options="field:'payFrequency',width:100">Pay Frequency</th>
									<th data-options="field:'nextEvalDueDate',width:120">Next Performance Date</th>
									<th data-options="field:'payPeriodHours',width:100">Pay Period Hours</th>
									<th data-options="field:'weeklyHours',width:100">Weekly Hours</th>
									<th data-options="field:'baseRate',width:100">Base Rate</th>
									<th data-options="field:'defaultEarningCode',width:120">Default Earning Code</th>
									<th data-options="field:'performacePlan',width:100">Performance Plan</th>
									<th data-options="field:'baseRateFrequency',width:120">Base Rate Frequency</th>
									<th data-options="field:'eligibleJobGroup',width:100">Eligible Job Group</th>
									<th data-options="field:'bonusPlan',width:100">Bonus Plan</th>
									<th data-options="field:'periodRate',width:100">Period Rate</th>
									<th data-options="field:'hourlyRate',width:100">Hourly Rate</th>
									<th data-options="field:'compRatio',width:100">Comp Ratio</th>
									<th data-options="field:'quartile',width:100">Quartile</th>
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
	fillAllDetails(document.getElementById("employeeId").value);
	empCompHistory(document.getElementById("employeeId").value);
	$("#compensation").attr('class', 'buttonSelect');
	$('#nextEvalDueDateBox').datebox('setValue', document.getElementById("nextEvalDueDate").value);
	$('#lastperfEvaluationDateBox').datebox('setValue', document.getElementById("lastperfEvaluationDate").value);
	if ($("#effectiveDate").val() != null && $("#effectiveDate").val() !=''){
	document.getElementById('empEffectiveDt').innerHTML= 'Effective Date &nbsp;&nbsp;&nbsp;&nbsp; ' +$("#effectiveDate").val();
	}
	$('#effectiveDateBox').datebox('setValue', '');
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
	getEmployeeDetails1(rec.id);
}

function getEmployeeDetails1(id){
	fillAllDetails(id);
	loading();
	// need to change from ajax to submit
	
	empCompHistory(id);
	$.ajax({
		url:"getEmployeeCompensation.htm?empId="+id,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			alert("Error While getting Compensation Details.");
		},
		success: function(obj){
			document.getElementById("updateble").value="yes";
			$.each(obj, function(i, item){
				if(i=='lastperfEvaluationDate'){
			  		$('#lastperfEvaluationDateBox').datebox('setValue', item);
			  		document.getElementById("lastperfEvaluationDate").value = item;
			  	}else if(i=='nextEvalDueDate'){
			  		$('#nextEvalDueDateBox').datebox('setValue', item);
			  		document.getElementById("nextEvalDueDateBox").value = item;
		  		}
			  	else if(i=='effectiveDate'){
		  			$('#effectiveDateBox').datebox('setValue', item);
		  		}
		  		else if($("#"+i) != undefined)
					$("#"+i).val(item); 
			});
			var effDate = $('#effectiveDateBox').datebox('getValue');
			if (effDate != null && effDate !='')
				if( effDate != null && effDate != ''){
			document.getElementById('empEffectiveDt').innerHTML= 'Effective Date &nbsp;&nbsp;&nbsp;&nbsp;   ' +effDate;
				}
			$('#effectiveDateBox').datebox('setValue', '');
			closeloading();
		}});
}
function save(){
	var empId = $("#employeeId").val();
	var effDate = $('#effectiveDateBox').datebox('getValue');
	if(!$('#employeeComp').valid() || effDate==''){
		$('#compValidationErrorMessageId').show();
		return false;
	}
	

	if(document.getElementById("updateble").value == "no"){
		if(confirm("This record is older than the current record.  Please Confirm")){
			loading();
			$.ajax({
				url:"checkEffectiveDate.htm?empId="+empId+"&effDate="+effDate+"&tableName=EMP_COMPENSATION_HIS",
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
			url:"checkEffectiveDate.htm?empId="+empId+"&effDate="+effDate+"&tableName=EMP_COMPENSATION",
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
	$('#compValidationErrorMessageId').hide();
	var effectiveDate = $('#effectiveDateBox').datebox('getValue');
	document.getElementById("lastperfEvaluationDate").value = $('#lastperfEvaluationDateBox').datebox('getValue');
	document.getElementById("nextEvalDueDate").value = $('#nextEvalDueDateBox').datebox('getValue');
	if($('#employeeComp').valid() && effectiveDate!=''){
		document.getElementById("effectiveDate").value=effectiveDate;
		loading();
		$.ajax({
			url:"saveEmployeeCompDetails.htm",
			type: "post",
			data : $("#employeeComp").serialize(),
			error: function(obj){
				alert(obj);
				closeloading();
			},
			success: function(obj){
				alert(obj);
				closeloading();
				getEmployeeDetails1(document.getElementById("employeeId").value);
				empCompHistory(document.getElementById("employeeId").value);
			}});
	}else{
		$('#compValidationErrorMessageId').show();
	}
}
/* function save(){
	if(document.getElementById("updateble").value == "no"){
		alert("You cannot update the History record.");
		return false;
	}
} */

function empCompHistory(empId){
	$('#employeeCompensationHistoryTable').datagrid('options').loadMsg = 'Processing, please wait .... ';  // change to other message
	$('#employeeCompensationHistoryTable').datagrid('loading');  // 
	$.ajax({
		url:"getEmployeeCompensationHistory.htm?empId="+empId,
		type: "post",
		dataType: 'json',
		error: function(obj){
			$('#employeeCompensationHistoryTable').datagrid('loaded');  // hide loading message
		},
		success: function(obj){
			if(obj.length > 0){
				removeGridMessage('#employeeCompensationHistoryTable','');
				$('#employeeCompensationHistoryTable').datagrid('loadData',obj); 
				$('#employeeCompensationHistoryTable').datagrid('loaded');  // hide loading message				
			}else{
				showGridMessage('#employeeCompensationHistoryTable','History Records not available');
				$('#employeeCompensationHistoryTable').datagrid('loadData',obj); 
				$('#employeeCompensationHistoryTable').datagrid('loaded');  // hide loading message
			}
		}});
}
function formatDetail(value,row,index){
	return '<a href="#" onclick="javascript:getEmployeeCompDetailsFromHistoryAjax('+row.seqNo+')">'+value+'</a>';
}

function getEmployeeCompDetailsFromHistoryAjax(id){
	loading();
	$.ajax({
		url:"getEmployeeCompDetailsFromHistoryAjax.htm?seqNo="+id,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			alert(obj);
		},
		success: function(obj){
			document.getElementById("updateble").value="no";
			setEmpCompDetails(obj,id);
			closeloading();
		}});
}

function setEmpCompDetails(obj,id){
	$.each(obj, function(i, item){
	  	if(i=='nextEvalDueDate'){
	  		$('#nextEvalDueDateBox').datebox('setValue', item);
	  		document.getElementById("nextEvalDueDate").value = item;
	  	}else if(i=='effectiveDate'){
  			$('#effectiveDateBox').datebox('setValue', item);
  		}else if(i=='lastperfEvaluationDate'){
  			$('#lastperfEvaluationDateBox').datebox('setValue', item);
  		}else if($("#"+i) != undefined)
			$("#"+i).val(item);  
	  	
	});
	var effDate = $('#effectiveDateBox').datebox('getValue');
	if (effDate != null && effDate !='')
	document.getElementById('empEffectiveDt').innerHTML= 'Effective Date &nbsp;&nbsp;&nbsp;&nbsp;   ' +effDate;
	$('#effectiveDateBox').datebox('setValue', '');
}

</script>
