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
<form:form commandName="mapdto" id="mapdto" modelAttribute="mapdto" enctype="multipart/form-data">
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
				<a href="javascript:void(0);" class="select">Employement Action</a>
				<span style="float: right">
					<a href="#" onclick="javascript:reset()" class="easyui-linkbutton" iconCls="icon-add">Add Employement Action</a>
    				<a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>
				</span>
			</div>
			</th>
	      </tr>
	    </table>
	    <table width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new" align="right">
	      <tr>
		     <th colspan="6" style="text-align: left;padding-left: 10px" class="head">
    			Employement Action
    		 </th>
	      </tr>
	      <tr>
		     <td style="width:20%" nowrap="nowrap">
    			<label>Employement Action</label><span class="mandatory">*</span>
    		 </td>
    		 <td><form:input path="value" cssClass="required" maxlength="65"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Description</label>
    		 </td>
    		 <td><form:input path="description"  maxlength="65"/> </td>
	      </tr>
	      <tr>
		     <td>
    			<label>Is Active</label>
    		 </td>
    		 <td><form:checkbox path="isActive" id="isActive" value=""/> </td>
	      </tr>
	      <tr>
	      	<td colspan="8">
	      		<table id="dataTable"  class="easyui-datagrid"  title="Employement Action Details Table"  style="width:100%;height:270px;table-layout: fixed;"
						data-options="collapsible:true
										,method: 'post'
										,pagination:false
										,emptyMsg: 'No records found'
										,fitColumns:true" >
						<thead>
							<tr>
								<th data-options="field:'id',width:100" hidden="true">id</th>
								<th data-options="field:'value',width:100" formatter="formatDetail">Employement Action</th>
								<th data-options="field:'description',width:100">Description</th>
								<th data-options="field:'isActive',width:100">Is Active</th>
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
	getDetails('getEmployementActionDetails.htm');
});
function save(){
	if($('#mapdto').valid()){
		loading();
		if(document.getElementById("isActive").checked)
			document.getElementById("isActive").value = true;
		else
			document.getElementById("isActive").value = false;
		$.ajax({
			url:"saveEmployementAction.htm",
			type: "post",
			data : $("#mapdto").serialize(),
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
				getDetails('getEmployementActionDetails.htm');
			}});
	}
}
function reset(){
	document.forms[0].action='employementAction.htm';
	document.forms[0].method = "post";
	document.forms[0].submit();
}
function formatDetail(value,row,index){
	return '<a href="#" onclick="javascript:displayExistRecord('+row.id+')">'+value+'</a>';
}
function displayExistRecord(id){
	getDetailsById('getEmployementActionDetailsById.htm?id='+id);
}
</script>
