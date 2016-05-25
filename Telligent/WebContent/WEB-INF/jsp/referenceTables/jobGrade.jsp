<%@ include file="../taglib.jsp" %>
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
<form:form commandName="jobGradeDTO" id="jobGradeDTO" modelAttribute="jobGradeDTO" enctype="multipart/form-data">
<form:hidden path="successMessage"/>
<form:hidden path="errorMessage"/>
<form:hidden path="operation"/>
<form:hidden path="id"/>
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
				<a href="javascript:void(0);" class="select">Job Grade</a>
				<span style="float: right">
				<a href="#" onclick="javascript:reset()" class="easyui-linkbutton" iconCls="icon-add">Add Job Grade</a>
				<a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>	
				</span>		
			</div>
			</th>
	      </tr>
	    </table>
	    <table width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new" align="right">
	      <tr>
		     <th colspan="6" style="text-align: left;padding-left: 10px" class="head">
    			Job Grade
    		 </th>
	      </tr>
	      <tr>
		     <td style="width:20%" nowrap="nowrap">
    			<label>Grade</label><span class="mandatory">*</span>
    		 </td>
    		 <td style="width:20%"><form:input path="value" cssClass="required" maxlength="65"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Primary Job</label>
    		 </td>
    		 <td>
    		 	<form:select path="primaryJobId"  cssClass="required">
    		 		<form:option value="">Select</form:option>
    		 		<form:options items="${primaryJobList}" itemValue="id" itemLabel="value"/>
    		 	</form:select>
    		 </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Description</label>
    		 </td>
    		 <td><form:input path="description"  maxlength="65"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Rate Frequency</label>
    		 </td>
    		 <td>
    		 	<form:select path="rateFreq" cssClass="required">
				    	<form:option value="">Select</form:option>
				    	<form:options items="${payFreqList}" itemValue="id" itemLabel="value"/>
				</form:select>
    		 </td>
	      </tr>
	       <tr>
		     <td>
    			<label>Minimum</label>
    		 </td>
    		 <td><form:input path="minimum"  maxlength="65" onKeyPress="return numbersonly(event, true,this.value)"/> </td>
	      </tr>
		  <tr>
		     <td>
    			<label>Midpoint</label>
    		 </td>
    		 <td><form:input path="midpoint"  maxlength="65" onKeyPress="return numbersonly(event, true,this.value)"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Maximum</label>
    		 </td>
    		 <td><form:input path="maximum"  maxlength="65" onKeyPress="return numbersonly(event, true,this.value)"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Is Active</label>
    		 </td>
    		 <td><form:checkbox path="isActive" id="isActive" value=""/> </td>
	      </tr>
	      <tr>
	      	<td>&nbsp;</td>
	      	<td rowspan="5">
	      		<table border="1" align="left">
				  <tr>
					<th width="100px" bgcolor="#00B0F0" style="text-align: center;">Low</th>
					<th width="100px" bgcolor="#00B0F0" style="text-align: center;">High</th>
				  </tr>
				  <tr>
					<td><form:input path="quartileLow1"  onKeyPress="return numbersonly(event, true,this.value)"/> </td>
					<td><form:input path="quartileHigh1"  onKeyPress="return numbersonly(event, true,this.value)"/> </td>
				  </tr>
					<tr>
						<td><form:input path="quartileLow2"  onKeyPress="return numbersonly(event, true,this.value)"/> </td>
						<td><form:input path="quartileHigh2"  onKeyPress="return numbersonly(event, true,this.value)"/> </td>
					</tr>
					<tr>
						<td><form:input path="quartileLow3"  onKeyPress="return numbersonly(event, true,this.value)"/> </td>
						<td><form:input path="quartileHigh3"  onKeyPress="return numbersonly(event, true,this.value)"/> </td>
					</tr>
					<tr>
						<td><form:input path="quartileLow4"  onKeyPress="return numbersonly(event, true,this.value)"/> </td>
						<td><form:input path="quartileHigh4"  onKeyPress="return numbersonly(event, true,this.value)"/> </td>
					</tr>
				</table>
	      	</td>
	      </tr>
	      <tr>
	      	<td><label>1st Quartile</label></td>
	      </tr>
	       <tr>
	      	<td><label>2nd Quartile</label></td>
	      </tr>
	       <tr>
	      	<td><label>3rd Quartile</label></td>
	      </tr>
	       <tr>
	      	<td><label>4th Quartile</label></td>
	      </tr>
	      <tr>
	      	<td colspan="8">
	      		<table id="dataTable"  class="easyui-datagrid"  title="Job Grade Details Table"  style="width:100%;height:270px;table-layout: fixed;"
						data-options="collapsible:true
										,method: 'post'
										,pagination:false
										,emptyMsg: 'No records found'
										,fitColumns:true" >
						<thead>
							<tr>
								<th data-options="field:'id',width:100" hidden="true">id</th>
								<th data-options="field:'value',width:100" formatter="formatDetail">Job Grade</th>
								<th data-options="field:'primaryJobId',width:100">Primary Job</th>
								<th data-options="field:'description',width:100">Description</th>
								<th data-options="field:'rateFreq',width:100">Rate Frequency</th>
								<th data-options="field:'minimum',width:100">Minimum</th>
								<th data-options="field:'midpoint',width:100">Midpoint</th>
								<th data-options="field:'maximum',width:100">Maximum</th>
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
	getJobDetails('getJobGradeDetails.htm');
});
function getJobDetails(url){
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
function getJobDetailsById(url){
	loading();
	$.ajax({
		url:url,
		type: "post",
		dataType: 'json',
		error: function(obj){
		},
		success: function(obj){
			$.each(obj, function(i, item){
				if(i == 'isActive'){
					if(item == "true")
						document.getElementById('isActive').checked = true;
					else
						document.getElementById('isActive').checked = false;
				}else
					$("#"+i).val(item);  
			});
			document.getElementById("operation").value="update";
			document.getElementById("value").readOnly=true;
			closeloading();
		}});
}
function save(){
	if($('#jobGradeDTO').valid()){
		loading();
		if(document.getElementById("isActive").checked)
			document.getElementById("isActive").value = true;
		else
			document.getElementById("isActive").value = false;
		$.ajax({
			url:"saveJobGrade.htm",
			type: "post",
			data : $("#jobGradeDTO").serialize(),
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
				getJobDetails('getJobGradeDetails.htm');
			}});
	}
}
function reset(){
	document.forms[0].action='jobGrade.htm';
	document.forms[0].method = "post";
	document.forms[0].submit();
}
function formatDetail(value,row,index){
	return '<a href="#" onclick="javascript:displayExistRecord('+row.id+')">'+value+'</a>';
}
function displayExistRecord(id){
	getJobDetailsById('getJobGradeDetailsById.htm?id='+id);
}
</script>
