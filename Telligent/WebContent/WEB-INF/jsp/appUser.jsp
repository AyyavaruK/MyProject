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
<form:form commandName="appUser" id="appUser" modelAttribute="appUser" enctype="multipart/form-data">
<form:hidden path="successMessage"/>
<form:hidden path="errorMessage"/>
<form:hidden path="operation"/>
<input type="hidden" id="updateble">
<div id="contentArea">
<div class="loader"></div>
<div class="contentArea">
	<%@include file="appUserLeft.jsp" %>
 	<div class="innerright" style="min-height: 480px" id="flow">
	 	<a href="javascript:toggle()" title="Hide Nav 13" id="flowtab"></a>
	 	<div style="margin:0 0 100px 0px;cursor:auto;" id="tab">
	    <div class="wrap">
		<div>
			<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right">
		      <tr>
		        <th class="head1" height="25" align="left">
		        <div class="innerpage-breadcrum">
					<a href="dashboard.htm">Dashboard</a> &nbsp;&gt;&nbsp; 
					<a href="javascript:void(0);" class="select">App User</a>
					<span style="float: right">
						<a href="#" onclick="javascript:reset()" class="easyui-linkbutton" iconCls="icon-add">Add App User</a>
	    				<a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>
					</span>
				</div>
				</th>
		      </tr>
		    </table>
			<table width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new" align="right">
		      	<tr>
				     <th colspan="6" style="text-align: left;padding-left: 10px" class="head">
		    			Application User Record
		    		 </th>
	    		</tr>
	    		<tr id="validationErrorMessageId" style="display: none">
	              	<td colspan="8">
	              		<div class=validationErrorMessage>Entry cannot be saved because Required data is missing</div>
	              	</td>
	            </tr> 
	    		<tr>
	    			<td style="width:10%" nowrap="nowrap"><label>User Id</label><span class="mandatory">*</span></td>
	    			<td><form:input path="userId" cssClass="required"/> </td>
	    			<td style="width:10%" nowrap="nowrap"><label>Password</label><span class="mandatory">*</span></td>
	    			<td><form:password path="password" cssClass="required"/></td>
	    			<td></td>
	    			<td><input type="button" class="buttonSelect" onclick="javascript:resetPwd()" value="Reset Password" id="resetPassword"></td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>Employee Id</label><span class="mandatory">*</span></td>
	    			<td>
	    				<form:hidden path="employeeId"/>
	    				<input class="easyui-combobox" name ="empID" id="empID" style="width:200px" data-options="
							url:'searchEmpId.htm',
							method:'post',
							mode: 'remote',
							valueField: 'id',
							textField: 'value',
							selectOnNavigation:false,
							onSelect:selectEmpId
							">
	    			</td>
	    			<td colspan="6" rowspan="16" valign="top">
	    				<table class="data-table-appUser">
	    					<tr  class="head" height="20px">
	    						<th>Team</th>
	    						<th>Merit Approval Level</th>
	    						<th>Effective Date</th>
	    						<th>End Date</th>
	    					</tr>
	    					<c:forEach items="${appUser.appUserList}" var="typeListitem" varStatus="i">
	    						<tr>
	    							<td>
	    								<select name="appUserList[${i.index}].team" id="${i.index}team">
	    									<option value="">Select</option>
	    									<c:forEach begin="0" end="${teamList.size()}" var="item" items="${teamList}">
				                               <option value="${item.teamName}">
				                               		<c:out value="${item.teamName}"/>
				                               </option>
				                             </c:forEach>
	    									
	    								</select>
	    							</td>
	    							<td>
	    								<select name="appUserList[${i.index}].meritApprovalLevel" id="${i.index}meritApprovalLevel">
	    									<option value="">Select</option>
	    									<c:forEach begin="0" var="j" step="1" end="99">
				                               <option value="${j}">
				                               		<c:out value="${j}"/>
				                               </option>
				                             </c:forEach>
	    								</select>
	    							</td>
	    							<td>
	    								<input id="effectiveDateBox${i.index}" name="appUserList[${i.index}].effectiveDate" class="easyui-datebox" width="150px"/>
	    							</td>
	    							<td>
	    								<input id="endDateBox${i.index}" name="appUserList[${i.index}].endDate" class="easyui-datebox" width="150px"/>
	    							</td>
	    						</tr>
	    					</c:forEach>
	    				</table>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>Effective Date</label><span class="mandatory">*</span></td>
					<td>
						<form:hidden path="effectiveDate"/>
			    		<input id="effectiveDateBox" class="required easyui-datebox" required="true" width="150px"/>
			    	</td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>Status</label><span class="mandatory">*</span></td>
	    			<td>
	    				<form:select path="status" cssClass="required">
	    					<form:option value="">Select</form:option>
	    					<form:option value="A">Active</form:option>
	    					<form:option value="I">Inactive</form:option>
	    					<form:option value="S">Suspended for password voilation</form:option>
	    				</form:select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>Merit Admin Approval Role</label><span class="mandatory">*</span></td>
	    			<td>
	    				<form:select path="meritAdminApprovalRole"  cssClass="required">
	    					<form:option value="">Select</form:option>
	    					 <c:forEach begin="0" var="i" step="1" end="99">
                               <option value="${i}">
                               		<c:out value="${i}"/>
                               </option>
                             </c:forEach>
	    				</form:select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>Email ID</label></td>
	    			<td><form:input path="emailId" /> </td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>User Ip</label></td>
	    			<td><form:input path="userIp"/> </td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>Bad Login Count</label></td>
	    			<td><form:input path="badLoginCount" onKeyPress="return numbersonly(event, true,this.value)"/> </td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>Security Group</label></td>
	    			<td>
	    				<form:select path="securityGroup">
	    					<form:option value="">Select</form:option>
	    					<form:options items="${securityGroupList}" itemValue="name" itemLabel="name"/>
	    				</form:select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>General Role</label></td>
	    			<td>
	    				<form:select path="generalRole">
	    					<form:option value="">Select</form:option>
	    					<form:option value="sysAdmin">Sys Admin</form:option>
	    					<form:option value="hrAdmin">HR Admin</form:option>
	    					<form:option value="meritAdmin">Merit Admin</form:option>
	    				</form:select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>Performance Admin Role</label></td>
	    			<td>
	    				<form:select path="performanceAdminRole">
	    					<form:option value="">Select</form:option>
	    					<form:options items="${performanceAdminRoleList}" itemValue="id" itemLabel="value"/>
	    				</form:select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>Bonus Admin Role</label></td>
	    			<td>
	    				<form:select path="bonusAdminRole">
	    					<form:option value="">Select</form:option>
	    					<form:options items="${bonusAdminRoleList}" itemValue="id" itemLabel="value"/>
	    				</form:select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td nowrap="nowrap"><label>Succession Role</label></td>
	    			<td>
	    				<form:select path="successionRole">
	    					<form:option value="">Select</form:option>
	    					<form:options items="${successionRoleList}" itemValue="id" itemLabel="value"/>
	    				</form:select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td colspan="8">
	    				<table id="dataTable"  class="easyui-datagrid"  title="App User Details Table"  style="width:100%;height:270px;table-layout: fixed;"
								data-options="collapsible:true
												,method: 'post'
												,pagination:false
												,emptyMsg: 'No records found'
												,fitColumns:true" >
								<thead>
									<tr>
										<th data-options="field:'userId',width:100" formatter="formatDetail">User Id</th>
										<th data-options="field:'employeeId',width:100">Employee Id</th>
										<th data-options="field:'effectiveDate',width:100">Effective Date</th>
										<th data-options="field:'status',width:100">Status</th>
										<th data-options="field:'meritAdminApprovalRole',width:100">Merit Admin Approval Role</th>
										<th data-options="field:'emailId',width:100">Email Id</th>
										<th data-options="field:'userIp',width:100">User IP</th>
										<th data-options="field:'badLoginCount',width:100">Bad Login Count</th>
										<th data-options="field:'securityGroup',width:100">Security Group</th>
										<th data-options="field:'generalRole',width:100">General Role</th>
										<th data-options="field:'performanceAdminRole',width:100">Performance Admin Role</th>
										<th data-options="field:'bonusAdminRole',width:100">Bonus Admin Role</th>
										<th data-options="field:'successionRole',width:100">Succession Role</th>
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
</div> 
</form:form>
<script type="text/javascript" src="view/js/docknavigation.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	getAppUserDetails('getAppUserDetails.htm');
});
function getAppUserDetails(url){
	loading();
	$('#dataTable').datagrid('options').loadMsg = 'Processing, please wait .... ';  // change to other message
	$('#dataTable').datagrid('loading');  // 
	$.ajax({
		url:url,
		type: "post",
		dataType: 'json',
		error: function(obj){
			$('#dataTable').datagrid('loaded');  // hide loading message
			closeloading();
		},
		success: function(obj){
			$('#dataTable').datagrid('loadData',obj); 
			$('#dataTable').datagrid('loaded');  // hide loading message
			closeloading();
		}});
}
function searchLastNameSelect(rec){
	$('#firstNameInputId').combobox('clear');
	$('#employeeInpuId').combobox('clear');
	$('#teamInputId').combobox('clear');
	$('#searchUserId').combobox('clear');
	getUserDetails(rec.id,'');
}
function searchFirstNameSelect(rec){
	$('#lastNameInputId').combobox('clear');
	$('#employeeInpuId').combobox('clear');
	$('#teamInputId').combobox('clear');
	$('#searchUserId').combobox('clear');
	getUserDetails(rec.id,'');
}
function searchEmpIdSelect(rec){
	$('#lastNameInputId').combobox('clear');
	$('#firstNameInputId').combobox('clear');
	$('#teamInputId').combobox('clear');
	$('#searchUserId').combobox('clear');
	getUserDetails(rec.id,'');
}
function searchUserIdSelect(rec){
	$('#lastNameInputId').combobox('clear');
	$('#firstNameInputId').combobox('clear');
	$('#teamInputId').combobox('clear');
	getUserDetails(rec.id,'');
}
function searchTeamEmployeesSelect(rec){
	$('#lastNameInputId').combobox('clear');
	$('#firstNameInputId').combobox('clear');
	$('#employeeInpuId').combobox('clear');
	$('#searchUserId').combobox('clear');
	getUserDetails(rec.id,'');
}
function selectEmpId(rec){
	document.getElementById("employeeId").value = rec.id;
}
function validateLoop(){
	 for(var i=0; i<10; i++){
	    	var team = $("#"+i+'team').val();
	    	var approvalLevel = $("#"+i+'meritApprovalLevel').val();
	    	var effDate = $('#effectiveDateBox'+i).datebox('getValue');
	    	if(team != ''){
	    		if(approvalLevel == ''){
	    			alert("Please select Merit Approval Level for Team "+team+" at Row "+(i+1));
	    			return false;
	    		}
	    		if(effDate == ''){
	    			alert("Please Enter Effective Date for Team "+team+" at Row "+(i+1));
	    			return false;
	    		}
	    	}else{
	    		return true;
	    	}
	 }
}
function save(){
	$('#validationErrorMessageId').hide();
	var effectiveDate = $('#effectiveDateBox').datebox('getValue');
	var empId = $('#empID').combobox('getValue');
	document.getElementById("employeeId").value=empId;
	if($('#appUser').valid() && effectiveDate!='' && empId!=''){
		if(validateLoop()){
			document.getElementById("effectiveDate").value=effectiveDate;
			loading();
			$.ajax({
				url:"saveAppUserDetails.htm",
				type: "post",
				data : $("#appUser").serialize(),
				error: function(obj){
					alert(obj);
					closeloading();
				},
				success: function(obj){
					alert(obj);
					getAppUserDetails('getAppUserDetails.htm');
					//reset();
					closeloading();
				}});
		}
	}else{
		$('#validationErrorMessageId').show();
	}
}
function getUserDetails(id,userId){
	fillAllDetails(id);
	loading();
	// need to change from ajax to submit
	$.ajax({
		url:"getUserDetails.htm?empId="+id+"&userId="+userId,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			alert("Error While getting User Details.");
		},
		success: function(obj){
			//document.getElementById("updateble").value="yes";
			//alert(obj);
			setUserDetails(obj,id);
			closeloading();
		}});
}

function setUserDetails(obj,id){
	$.each(obj, function(i, item){
	  	if(i=='effectiveDate'){
  			$('#effectiveDateBox').datebox('setValue', item);
  		}else if(i=='employeeId'){
  			$('#empID').combobox('setValue', item);
  		}else if(i=='appUserList'){
  			$.each(item, function(key, value){
  				$('#'+key+'team').val(value.team);
  				$('#effectiveDateBox'+key+'').datebox('setValue',value.effectiveDate);
  				$('#'+key+'meritApprovalLevel').val(value.meritApprovalLevel);
  				$('#endDateBox'+key+'').datebox('setValue',value.endDate);
  				
  			});
  		}else if($("#"+i) != undefined){
			$("#"+i).val(item);  
  		}
	  	document.getElementById("userId").readOnly='true';
	});
}
function reset(){
	//getTeamList();
	document.forms[0].action='appUser.htm';
	document.forms[0].method = "post";
	document.forms[0].submit();
}
function resetPwd(){
	loading();
	$.ajax({
		url:"resetPassword.htm?userId="+$("#userId").val(),
		type: "post",
		dataType: 'text',
		error: function(obj){
			closeloading();
			alert("Error While Resetting Password.");
		},
		success: function(obj){
			if(obj=="success"){
				alert("Password resetted successfully. Please check your mail");
			}else{
				alert("Error While Resetting Password.");
			}
			closeloading();
		}});
}
function getUserDetails1(userId){
	getUserDetails('', userId);
}
function formatDetail(value,row,index){
	return '<a href="#" onclick="javascript:getUserDetails1(\''+value+'\')">'+value+'</a>';
}
</script>
