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
				<a href="javascript:void(0);" class="select">Merit Admin Guidelines</a>
				<span style="float: right">
				<a href="#" onclick="javascript:reset()" class="easyui-linkbutton" iconCls="icon-add">Add Merit Admin Guidelines</a>
				<a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>	
				</span>		
			</div>
			</th>
	      </tr>
	    </table>
	    <table width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new" align="left">
	    	<tr>
			     <th colspan="8" style="text-align: left;padding-left: 10px" class="head">
	    			Merit Admin Guidelines
	    		 </th>
		      </tr>
		     <tr>
				<td style="width: 10%" nowrap="nowrap"><label>Pay Entity</label><span style="color: red">*</span>
					<form:select path="payEntity" id="payEntity1" cssClass="required">
	    				<form:option value="">Select</form:option>
	    				<form:options items="${payEntityList}" itemValue="id" itemLabel="value"/>
	    			</form:select>
				</td>
				<td style="width: 12%" nowrap="nowrap"><label>Merit Period</label><span style="color: red">*</span></td>
				<td>
					<form:input path="meritPeriod"  cssClass="required" maxlength="4" onKeyPress="return numbersonly(event, true,this.value)"/> 
				</td>
				<td style="width: 12%" nowrap="nowrap"><label>Performance Rating</label><span style="color: red">*</span></td>
				<td>	
					<form:input path="performanceRating"  cssClass="required" maxlength="1" /></td>
				<td style="width: 20%" nowrap="nowrap"><label>Target Population PCT</label></td>
				<td>	
					<form:input path="targetPopulationPct" maxlength="20" onKeyPress="return numbersonly(event, true,this.value)"/>
				</td>
			</tr>
	    </table>
	    <table width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new" align="left">
		<tr>
		     <th colspan="5" style="text-align: center;" >
    			Recommended Pay Increase %
    		 </th>
	      </tr>
		 <tr>
		 	<td>&nbsp;</td>
		  	<td>
		  		<label>
			  		First Quartile &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					2nd Quartile &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					3rd Quartile &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					4th Quartile &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</label>
			</td>
		</tr>
		 <tr>
		  	<td align="right"><label style="float: right;">Maximum</label></td>
			<td colspan="4">
				<form:input path="firstQuartileMax"  maxlength="4" onKeyPress="return numbersonly(event, true,this.value)"/> 
				<form:input path="secondQuartileMax"  maxlength="4" onKeyPress="return numbersonly(event, true,this.value)"/> 
				<form:input path="thirdQuartileMax"  maxlength="4" onKeyPress="return numbersonly(event, true,this.value)"/> 
				<form:input path="fourthQuartileMax"  maxlength="4" onKeyPress="return numbersonly(event, true,this.value)"/> 
			</td>
		  </tr>
		   <tr>
		  	<td><label>Minimum</label></td>
			<td colspan="4">
				<form:input path="firstQuartileMin"  maxlength="4" onKeyPress="return numbersonly(event, true,this.value)"/> 
				<form:input path="secondQuartileMin"  maxlength="4" onKeyPress="return numbersonly(event, true,this.value)"/> 
				<form:input path="thirdQuartileMin"  maxlength="4" onKeyPress="return numbersonly(event, true,this.value)"/> 
				<form:input path="fourthQuartileMin"  maxlength="4" onKeyPress="return numbersonly(event, true,this.value)"/> 
			</td>
		  </tr>
	      <tr>
	      	<td colspan="8">
	      		<table id="dataTable"  class="easyui-datagrid"  title="Merit Admin Entries"  style="width:100%;height:270px;table-layout: fixed;"
						data-options="collapsible:true
										,method: 'post'
										,pagination:false
										,emptyMsg: 'No records found'
										,fitColumns:true" >
						<thead>
							<tr>
								<th data-options="field:'payEntity',width:100"  hidden="true">Pay entity</th>
								<th data-options="field:'payEntityLabel',width:100">Pay entity</th>
								<th data-options="field:'meritPeriod',width:100" formatter="formatDetail">Merit Period</th>
								<th data-options="field:'performanceRating',width:100">Performance Rating</th>
								<th data-options="field:'targetPopulationPct',width:100">Target Population Pct</th>
								<th data-options="field:'firstQuartileMin',width:100">First Quartile Min</th>
								<th data-options="field:'firstQuartileMax',width:100">First Quartile Max</th>
								<th data-options="field:'secondQuartileMin',width:100">Second Quartile Min</th>
								<th data-options="field:'secondQuartileMax',width:100">Second Quartile Max</th>
								<th data-options="field:'thirdQuartileMin',width:100">Third Quartile Min</th>
								<th data-options="field:'thirdQuartileMax',width:100">Third Quartile Max</th>
								<th data-options="field:'fourthQuartileMin',width:100">Four Quartile Min</th>
								<th data-options="field:'fourthQuartileMax',width:100">Four Quartile Max</th>
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
	getDetails('getMeritAdminGuidelinesDetails.htm');
});
function save(){
	if($('#mapdto').valid()){
		loading();
		var meritPeriod = $("#meritPeriod").val();
		if(meritPeriod >(new Date().getFullYear())){
			alert("Merit Period should not be less than the current year");
			closeloading();
			return false;
		}
		var firstQuartileMax = $("#firstQuartileMax").val();
		var firstQuartileMin = $("#firstQuartileMin").val();
		if(firstQuartileMin !=0 && firstQuartileMax !=0 && firstQuartileMin>firstQuartileMax){
			alert("First Quartile Maximum is not less than First Quartile Minimum");
			closeloading();
			return false;
		}
		var secondQuartileMax = $("#secondQuartileMax").val();
		var secondQuartileMin = $("#secondQuartileMin").val();
		if(secondQuartileMin !=0 && secondQuartileMax !=0 && secondQuartileMin>secondQuartileMax){
			alert("Second Quartile Maximum is not less than Second Quartile Minimum");
			closeloading();
			return false;
		}
		var thirdQuartileMax = $("#thirdQuartileMax").val();
		var thirdQuartileMin = $("#thirdQuartileMin").val();
		if(thirdQuartileMin !=0 && thirdQuartileMax !=0 && thirdQuartileMin>thirdQuartileMax){
			alert("Third Quartile Maximum is not less than Third Quartile Minimum");
			closeloading();
			return false;
		}
		var fourthQuartileMax = $("#fourthQuartileMax").val();
		var fourthQuartileMin = $("#fourthQuartileMin").val();
		if(fourthQuartileMin !=0 && fourthQuartileMax !=0 && fourthQuartileMin>fourthQuartileMax){
			alert("Fourth Quartile Maximum is not less than Fourth Quartile Minimum");
			closeloading();
			return false;
		}
		$.ajax({
			url:"saveMeritAdminGuidelines.htm",
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
				getDetails('getMeritAdminGuidelinesDetails.htm');
			}});
	}
}
function reset(){
	document.forms[0].action='meritAdminGuidelines.htm';
	document.forms[0].method = "post";
	document.forms[0].submit();
}
function formatDetail(value,row,index){
	var text = '\''+row.payEntity+','+row.meritPeriod+','+row.performanceRating+'\'';
	return '<a href="#" onclick="javascript:displayExistRecord('+text+')">'+value+'</a>';
}
function displayExistRecord(text){
	loading();
	var arr = text.split(",");
	var payEntity = arr[0];
	var meritPeriod = arr[1];
	var performanceRating = arr[2];
	$.ajax({
		url:'getMeritAdminGuidelinesDetailsById.htm?merit_period='+meritPeriod+'&payEntity='+payEntity+'&performanceRating='+performanceRating,
		type: "post",
		dataType: 'json',
		error: function(obj){
		},
		success: function(obj){
			$.each(obj, function(i, item){
				if(i == "payEntity")
					$("#"+i+"1").val(item);
				else
					$("#"+i).val(item);  
			});
			document.getElementById("operation").value="update";
			closeloading();
		}});
	
}
</script>
