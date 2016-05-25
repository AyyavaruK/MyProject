<%@ include file="../taglib.jsp" %>
<title>Dashboard</title>
<link href="view/css/colorbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="view/css/popup.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/icon.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/demo.css">
<script type="text/javascript" src="view/js/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="view/js/app/telligentCommon.js"></script>
<script type="text/javascript" src="view/js/popup.js"></script>
<form:form commandName="securityGroup" id="securityGroup" modelAttribute="securityGroup" enctype="multipart/form-data">
<form:hidden path="successMessage"/>
<form:hidden path="errorMessage"/>
<form:hidden path="operation"/>
<form:hidden path="seqNo"/>
<input type="hidden" id="updateble">
<div id="contentArea">
<div class="loader"></div>
<div class="contentArea">
	<%@include file="referenceTablesLeft.jsp" %>
 	<div class="innerright" style="min-height: 480px" id="flow">
 	<a href="javascript:toggle()" title="Hide Nav 13" id="flowtab"></a>
 	<div style="margin:0px;cursor:auto;" id="tab">
	    <div class="wrap">
		<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right">
	      <tr>
	        <th class="head1" height="25" align="left">
	        <div class="innerpage-breadcrum">
				<a href="dashboard.htm">Dashboard</a> &nbsp;&gt;&nbsp; 
				<a href="javascript:void(0);">Reference Tables</a> &nbsp;&gt;&nbsp; 
				<a href="javascript:void(0);" class="select">Security Group</a>
				<span style="float: right">
					<a href="#" onclick="javascript:reset()" class="easyui-linkbutton" iconCls="icon-add">Add Security Group</a>
    				<a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>
				</span>
			</div>
			</th>
	      </tr>
	    </table>
	    <table width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new" align="right" style="padding-left: 10px;">
	      <tr>
		     <th colspan="6" style="text-align: left;padding-left: 10px" class="head">
    			Security Group
    		 </th>
	      </tr>
	      <tr>
	      
		     <td style="width:20%" nowrap="nowrap" align="left">
    			<label>Security Group</label><span class="mandatory">*</span><form:input path="name" cssClass="required" maxlength="65"/>
    		 </td>
    		 <td>
    			<label>Description</label><form:input path="description"  maxlength="65"/>
    		 </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Merit Admin</label>
    		 </td>
    		 <td><form:checkbox path="meritAdmin" id="meritAdmin"/> </td>
		     <td>
    			<label>Performance Manager</label>
    		 </td>
    		 <td><form:checkbox path="perfManager" id="perfManager"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Bonus Manager</label>
    		 </td>
    		 <td><form:checkbox path="bonusManager" id="bonusManager"/> </td>
		     <td>
    			<label>Succession Manager</label>
    		 </td>
    		 <td><form:checkbox path="successionManager" id="successionManager"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Teams</label>
    		 </td>
    		 <td><form:checkbox path="teams" id="teams"/> </td>
		     <td>
    			<label>Employees</label>
    		 </td>
    		 <td><form:checkbox path="employees" id="employees"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>System Tables</label>
    		 </td>
    		 <td><form:checkbox path="systemTables" id="systemTables"/> </td>
		     <td>
    			<label>Security</label>
    		 </td>
    		 <td><form:checkbox path="security" id="security"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Merit Reports</label>
    		 </td>
    		 <td><form:checkbox path="meritReports" id="meritReports"/> </td>
		     <td>
    			<label>Bonus Reports</label>
    		 </td>
    		 <td><form:checkbox path="bonusReports" id="bonusReports"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Succession Reports</label>
    		 </td>
    		 <td><form:checkbox path="successionReports" id="successionReports"/> </td>
		     <td>
    			<label>Employee Reports</label>
    		 </td>
    		 <td><form:checkbox path="employeeReports" id="employeeReports"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>System Reports</label>
    		 </td>
    		 <td><form:checkbox path="systemReports" id="systemReports"/> </td>
	      </tr>
	      <tr>
	      	<td colspan="8">
	      		<table id="dataTable"  class="easyui-datagrid"  title="Security Group Details Table"  style="width:100%;height:170px;table-layout: fixed;"
						data-options="collapsible:true
										,method: 'post'
										,pagination:false
										,emptyMsg: 'No records found'
										,fitColumns:true" >
						<thead data-options="frozen:true">
							<tr>
								<th data-options="field:'seqNo',width:100" hidden="true">id</th>
								<th data-options="field:'name',width:100" formatter="formatDetail">Security Group Name</th>
							</tr>
						</thead>
						<thead>
							<tr>
								<th data-options="field:'description',width:100">Description</th>
								<th data-options="field:'meritAdmin',width:100">Merit Admin</th>
								<th data-options="field:'perfManager',width:150">Performance Manager</th>
								<th data-options="field:'bonusManager',width:150">Bonus Manager</th>
								<th data-options="field:'successionManager',width:150">Succession Manager</th>
								<th data-options="field:'teams',width:100">Teams</th>
								<th data-options="field:'employees',width:100">Employees</th>
								<th data-options="field:'systemTables',width:120">System Tables</th>
								<th data-options="field:'security',width:100">Security</th>
								<th data-options="field:'meritReports',width:150">Merit Reports</th>
								<th data-options="field:'bonusReports',width:150">Bonus Reports</th>
								<th data-options="field:'successionReports',width:150">Succession Reports</th>
								<th data-options="field:'employeeReports',width:150">Employee Reports</th>
								<th data-options="field:'systemReports',width:150">System Reports</th>
								<th data-options="field:'updatedDate',width:150">Updated Date</th>
								<th data-options="field:'updatedBy',width:150">Updated By</th>
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
<script type="text/javascript">
$(document).ready(function(){
	getDetails();
});
function save(){
	if($('#securityGroup').valid()){
		loading();
		$.ajax({
			url:"saveSecurityGroup.htm",
			type: "post",
			data : $("#securityGroup").serialize(),
			error: function(obj){
				alert(obj);
				closeloading();
			},
			success: function(obj){
				var val = obj.split(':;');
				if(val[0] == 'success'){
					alert(val[1]);
					reset();
				}else
					alert(val[1]);
				closeloading();
				getDetails();
			}});
	}
}
function getDetails(){
		$('#dataTable').datagrid('options').loadMsg = 'Processing, please wait .... ';  // change to other message
		$('#dataTable').datagrid('loading');  // 
		$.ajax({
			url:'getSecurityGroupDetails.htm',
			type: "post",
			dataType: 'json',
			error: function(obj){
				$('#dataTable').datagrid('loaded');  // hide loading message
			},
			success: function(obj){
				$('#dataTable').datagrid('loadData',obj); 
				$('#dataTable').datagrid('loaded');  // hide loading message
			}});
}
function getDetailsById(id){
	loading();
	document.getElementById("seqNo").value=id;
	$.ajax({
		url:'getSecurityGroupDetailsById.htm?id='+id,
		type: "post",
		dataType: 'json',
		error: function(obj){
		},
		success: function(obj){
			$.each(obj, function(i, item){
				if($("#"+i).attr("type") == "checkbox")
					$("#"+i).prop("checked", item);
				else
					$("#"+i).val(item);  
			});
			document.getElementById("name").readOnly=true;
			document.getElementById("operation").value="update";
			closeloading();
		}});
}
function reset(){
	document.forms[0].action='securityGroup.htm';
	document.forms[0].method = "post";
	document.forms[0].submit();
}
function formatDetail(value,row,index){
	return '<a href="#" onclick="javascript:displayExistRecord('+row.seqNo+')">'+value+'</a>';
}
function displayExistRecord(id){
	getDetailsById(id);
}
</script>
