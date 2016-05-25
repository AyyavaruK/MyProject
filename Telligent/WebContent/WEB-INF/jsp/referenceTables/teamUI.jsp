<%@ include file="../taglib.jsp" %>
<title>Dashboard</title>

<link href="view/css/style.css" rel="stylesheet" type="text/css" />
<link href="view/css/colorbox.css" rel="stylesheet" type="text/css" />
<script src="view/js/jquery/jquery.colorbox.js"></script>
<link rel="stylesheet" type="text/css" href="view/css/popup.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/icon.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/demo.css">
<script type="text/javascript" src="view/js/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="view/js/jquery/jquery.usphone.js"></script>
<script type="text/javascript" src="view/js/app/telligentCommon.js"></script>
<script type="text/javascript" src="view/js/popup.js"></script>
<form:form commandName="teamUI" id="teamUI" modelAttribute="teamUI" >
<form:hidden path="successMessage"/>
<form:hidden path="errorMessage"/>
<form:hidden path="operation" id="operation"/>
<input type="hidden" id="updateble">
<form:hidden path="teamId" id="teamId"/>
<div id="contentArea">
<div class="loader"></div>
<div class="contentArea">
	<%@include file="teamUILeft.jsp" %>
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
									<a href="javascript:void(0);">Team</a> &nbsp;&gt;&nbsp; 
									<a href="javascript:void(0);" class="select">Team Details</a>
									<span style="float: right">
				   						<a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>
				   						<a href="#" onclick="javascript:reset()" class="easyui-linkbutton" iconCls="icon-cancel">Reset</a>			
				              		</span> 
								</div>
								</th>
						      </tr>
						    </table>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>
	    				<table   width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new">
							<tr>
								<th colspan="8" style="text-align: left;padding-left: 10px" class="head">
						    			Teams
						    	</th>
							</tr>
							<tr id="validationErrorMessageId" style="display: none">
				              	<td colspan="8">
				              		<div class=validationErrorMessage>Entry cannot be saved because Required data is missing</div>
				              	</td>
				            </tr>
							<tr>
								<td style="width:5%"><label>Active</label></td>
								<td style="width: 10px;">
									<form:checkbox path="active" id="active" value=""/>
								</td>
								<%-- <td style="width: 20px"><label>Delete</label></td>
								<td style="width:10px;" colspan="5">
									<form:checkbox path="delete" id="delete" value=""/>
								</td> --%>
							</tr>
						</table>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>
	    				<table   width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new">
	    					<tr>
	    						<td style="width:10%"><label>Team Name</label><span class="mandatory">*</span></td>
	    						<td style="width:20%"><form:input path="teamName" cssClass="required"></form:input></td>
	    						<td style="width:15%" nowrap="nowrap"><label>Team Description</label></td>
	    						<td style="width:20%"><form:input path="description"/> </td>
	    						<td style="width:10%"><label>Team Parent</label></td>
	    						<td>
	    							<form:select path="teamParent">
	    								<form:option value="">Select</form:option>
	    							</form:select>
	    						</td>
	    					</tr>
	    				</table>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>
	    				<table width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new">
	    					<tr>
	    						<td style="width:10%">&nbsp;</td>
				    			<td style="width:10%"><input type="button" class="button1" value="Field Name" style="width:100px !important"/></td>
				    			<td style="width:10%;text-align: left;"><input type="button" class="button1" value="Operator" style="width:90px !important"/></td>
				    			<td style="width:40%"><input type="button" class="button1" style="width: 350px !important;" value="Values"/></td>
				    			<td style="width:30%;text-align: left;"><input type="button" class="button1" value="Conjuction" style="width:75px !important"//></td>
	    					</tr>
	    					<tr>
	    						<td><label>Filter Field 1</label></td>
				    			<td>
				    				<form:select path="field1"  cssStyle="width:100px !important;">
				    					<form:option value="">Select</form:option>
				    					<c:forEach var="i" begin="1" end="10" step="1">
					    		 			<form:option value="ORG_LEVEL_${i}">org${i}</form:option>
					    		 		</c:forEach>
				    				</form:select>
				    			</td>
				    			<td style="text-align: left;">
				    				<form:select path="operator1">
				    					<form:option value="">Select</form:option>
				    					<form:option value="=">Equals</form:option>
				    					<form:option value="not in">Not In</form:option>
				    					<form:option value="in">In</form:option>
				    				</form:select>
				    			</td>
				    			<td>
				    			<input class="easyui-searchbox" id="value1" name="value1" data-options="prompt:'Please Click on search icon to select values',searcher:doSearchValue1" style="width:350px !important"></input>
				    			<%-- <form:input path="value1" cssStyle="width:350px !important"/> --%>
				    			</td>
				    			<td style="text-align: left;">
				    				<form:select path="conjuction1">
				    					<form:option value="">Select</form:option>
				    					<form:option value="and">And</form:option>
				    					<form:option value="or">Or</form:option>
				    				</form:select>
				    			</td>
	    					</tr>
	    					<tr>
	    						<td><label>Filter Field 2</label></td>
				    			<td>
				    				<form:select path="field2"  cssStyle="width:100px !important;">
				    					<form:option value="">Select</form:option>
				    					<c:forEach var="i" begin="1" end="10" step="1">
					    		 			<form:option value="ORG_LEVEL_${i}">org${i}</form:option>
					    		 		</c:forEach>
				    				</form:select>
				    			</td>
				    			<td style="text-align: left;">
				    				<form:select path="operator2">
				    					<form:option value="">Select</form:option>
				    					<form:option value="=">Equals</form:option>
				    					<form:option value="not in">Not In</form:option>
				    					<form:option value="in">In</form:option>
				    				</form:select>
				    			</td>
				    			<td><input class="easyui-searchbox" id="value2" name="value2" data-options="prompt:'Please Click on search icon to select values',searcher:doSearchValue2" style="width:350px !important"></input></td>
				    			<td style="text-align: left;">
				    				<form:select path="conjuction2">
				    					<form:option value="">Select</form:option>
				    					<form:option value="and">And</form:option>
				    					<form:option value="or">Or</form:option>
				    				</form:select>
				    			</td>
	    					</tr>
	    					<tr>
	    						<td><label>Filter Field 3</label></td>
				    			<td>
				    				<form:select path="field3"  cssStyle="width:100px !important;">
				    					<form:option value="">Select</form:option>
				    					<c:forEach var="i" begin="1" end="10" step="1">
					    		 			<form:option value="ORG_LEVEL_${i}">org${i}</form:option>
					    		 		</c:forEach>
				    				</form:select>
				    			</td>
				    			<td style="text-align: left;">
				    				<form:select path="operator3">
				    					<form:option value="">Select</form:option>
				    					<form:option value="=">Equals</form:option>
				    					<form:option value="not in">Not In</form:option>
				    					<form:option value="in">In</form:option>
				    				</form:select>
				    			</td>
				    			<td><input class="easyui-searchbox" id="value3" name="value3" data-options="prompt:'Please Click on search icon to select values',searcher:doSearchValue3" style="width:350px !important"></input></td>
				    			<td style="text-align: left;">
				    				<form:select path="conjuction3">
				    					<form:option value="">Select</form:option>
				    					<form:option value="and">And</form:option>
				    					<form:option value="or">Or</form:option>
				    				</form:select>
				    			</td>
	    					</tr>
	    					<tr>
	    						<td><label>Filter Field 4</label></td>
				    			<td>
				    				<form:select path="field4" cssStyle="width:100px !important;">
				    					<form:option value="">Select</form:option>
				    					<c:forEach var="i" begin="1" end="10" step="1">
					    		 			<form:option value="ORG_LEVEL_${i}">org${i}</form:option>
					    		 		</c:forEach>
				    				</form:select>
				    			</td>
				    			<td style="text-align: left;">
				    				<form:select path="operator4">
				    					<form:option value="">Select</form:option>
				    					<form:option value="=">Equals</form:option>
				    					<form:option value="not in">Not In</form:option>
				    					<form:option value="in">In</form:option>
				    				</form:select>
				    			</td>
				    			<td><input class="easyui-searchbox" id="value4" name="value4" data-options="prompt:'Please Click on search icon to select values',searcher:doSearchValue4" style="width:350px !important"></input></td>
				    			<td style="text-align: left;">
				    				<form:select path="conjuction4">
				    					<form:option value="">Select</form:option>
				    					<form:option value="and">And</form:option>
				    					<form:option value="or">Or</form:option>
				    				</form:select>
				    			</td>
	    					</tr>
	    					<tr>
	    						<td><label>Filter Field 5</label></td>
				    			<td>
				    				<form:select path="field5"  cssStyle="width:100px !important;">
				    					<form:option value="">Select</form:option>
				    					<c:forEach var="i" begin="1" end="10" step="1">
					    		 			<form:option value="ORG_LEVEL_${i}">org${i}</form:option>
					    		 		</c:forEach>
				    				</form:select>
				    			</td>
				    			<td style="text-align: left;">
				    				<form:select path="operator5">
				    					<form:option value="">Select</form:option>
				    					<form:option value="=">Equals</form:option>
				    					<form:option value="not in">Not In</form:option>
				    					<form:option value="in">In</form:option>
				    				</form:select>
				    			</td>
				    			<td><input class="easyui-searchbox" id="value5" name="value5" data-options="prompt:'Please Click on search icon to select values',searcher:doSearchValue5" style="width:350px !important"></input></td>
				    			<td style="text-align: left;">
				    				&nbsp;
				    				<%-- <form:select path="conjuction5">
				    					<form:option value="">Select</form:option>
				    					<form:option value="and">And</form:option>
				    					<form:option value="or">Or</form:option>
				    				</form:select> --%>
				    			</td>
	    					</tr>
	    					<tr>
				    			<td colspan="5" style="text-align: center;">
				    				<!-- <input type="button" class="button" value="View List of Employees for Selected Filters" onclick="javascript:viewListOfEmployeesForSelectedFilter()"/> -->
				    				<a href="#viewListPopUp" id="viewListRefId" class="viewListPopUpClass" onclick="viewListOfEmployeesForSelectedFilter();">View List of Employees for Selected Filters</a>
				    			</td>
				    		</tr>
	    				</table>
	    			</td>
	    		</tr>
	    		<tr>
			      	<td colspan="8">
			      		<table id="dataTable"  class="easyui-datagrid"  title="Team Details"  style="width:100%;height:270px;table-layout: fixed;"
								data-options="collapsible:true
												,method: 'post'
												,pagination:false
												,emptyMsg: 'No records found'
												,fitColumns:true" >
								<thead>
									<tr>
										<th data-options="field:'teamId',width:100" hidden="true">id</th>
										<th data-options="field:'teamName',width:100" formatter="formatDetail">Team Name</th>
										<th data-options="field:'description',width:100">Description</th>
										<th data-options="field:'teamParent',width:100">Team Parent</th>
										<th data-options="field:'active',width:100">Active</th>
										<!-- <th data-options="field:'delete',width:100">Delete</th> -->
										<th data-options="field:'updatedDate',width:100">Updated Date</th>
										<th data-options="field:'updatedBy',width:100">Updated By</th>
									</tr>
								</thead>
					</table> 
			      	</td>
			      </tr>
	    	</table>
		</div>
		<div style="display: none;">
		<div id="viewListPopUp"  style="padding:10px; background:#fff;">
			<br clear="all"/>
		  	<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right" class="data-table" >
			  <thead>
			  	<tr>
			  		<td class="head">
			  			View Employees
			  		</td>
			  	</tr>
			  </thead>
			  <tbody id="tbodyId">
			  	<tr>
			  		<td colspan="10">
			  			<table id="empTable"  class="easyui-datagrid" title="Filtered Employees List"  style="width:100%;height:200px;table-layout: fixed;"
								data-options="collapsible:true
												,method: 'post'
												,pagination:false
												,emptyMsg: 'No records found'
												,fitColumns:true
												,collapsed:false " >
			              <thead>
			                <tr>
			                  <th data-options="field:'employeeId',width:100">Emp Id</th>
			                  <th data-options="field:'firstName',width:100">First Name</th>
			                  <th data-options="field:'middleName',width:100">Middle Name</th>
			                  <th data-options="field:'lastName',width:100">Last Name</th>
			               </tr>
			              </thead>
			             </table>
			  		</td>
			  	</tr>
			  </tbody>
			</table>
		</div>
		</div>
		<div style="display: none;">
		<div id="valuePopUp"  style="padding:10px; background:#fff;">
			<br clear="all"/>
			<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right" class="data-table" >
				  <thead>
				  	<tr>
				  		<td class="head" colspan="2">
				  			Select Values
				  		</td>
				  	</tr>
				  </thead>
				  <tbody id="tbodyValueId">
				  </tbody>
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
	getTeamDetails();
	$('.searchbox-button').prop('href','#valuePopUp');
	getTeamParentList('',false);
	
	$(".searchbox-button").colorbox({inline:true, width:"50%",height:"50%"});
    $("#click").click(function(){
            $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
            return true;
    });
	
	$(".viewListPopUpClass").colorbox({inline:true, width:"50%",height:"50%"});
    $("#click").click(function(){
            $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
            return true;
    });
    
});
var type = "";
function getTeamParentList(id,flag){
	loading();
	$.ajax({
		url:"getTeamParentList.htm?teamId="+id,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			alert(obj);
		},
		success: function(obj){
				var options = '';
				options += '<option value="">Select</option>';
				$.each(obj, function(i, item){
		        	options += '<option value="' + obj[i].teamId + '">' + obj[i].teamName + '</option>';
		      	});
		      	$("#teamParent").html(options);
		      	if(flag)
		      		getTeamDetailsById(id);
				closeloading();
		}});
}
function searchTeamName(rec){
	$("#teamName").prop("readonly","true");
	getTeamParentList(rec.teamId,true);
}
function searchTeamParent(rec){
	getTeamParentList(rec.teamId,true);
}
function getTeamDetails(){
	$('#dataTable').datagrid('options').loadMsg = 'Processing, please wait .... ';  // change to other message
	$('#dataTable').datagrid('loading');  // 
	$.ajax({
		url:"getTeamDetails.htm",
		type: "post",
		dataType: 'json',
		error: function(obj){
			alert();
			$('#dataTable').datagrid('loaded');  // hide loading message
		},
		success: function(obj){
			$('#dataTable').datagrid('loadData',obj); 
			$('#dataTable').datagrid('loaded');  // hide loading message
		}});
}
function formatDetail(value,row,index){
	return '<a href="#" onclick="javascript:getTeamDetailsById('+row.teamId+')">'+value+'</a>';
}
function getTeamDetailsById(id){
	document.getElementById("teamId").value = id;
	loading();
	//getTeamParentList(id);
	$.ajax({
		url:"getTeamDetailsById.htm?teamId="+id,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			getTeamParentList('',false);
			alert(obj);
		},
		success: function(obj){
			$.each(obj, function(i, item){
				if(i == 'active'){
					if(item)
						document.getElementById('active').checked = true;
					else
						document.getElementById('active').checked = false;
				}/* else if(i == 'delete'){
					if(item)
						document.getElementById('delete').checked = true;
					else
						document.getElementById('delete').checked = false;
				} */else if(i == 'value1' || i == 'value2' || i == 'value3' || i == 'value4' || i == 'value5'){
					$("#"+i).searchbox('setValue', item);
				}else
		  			$("#"+i).val(item);   				  		
			});
			document.getElementById("operation").value="update";
			closeloading();
		}});
}
function reset(){
	document.forms[0].action='teamUI.htm';
	document.forms[0].method = "post";
	document.forms[0].submit();
}
function save(){
	if(!validateFilters()){
		alert("Please check the filters.");
		return false;
	}
	$('#validationErrorMessageId').hide();
	if($('#teamUI').valid()){
		loading();
		if(document.getElementById("active").checked)
			document.getElementById("active").value = true;
		else
			document.getElementById("active").value = false;
		/* if(document.getElementById("delete").checked)
			document.getElementById("delete").value = true;
		else
			document.getElementById("delete").value = false; */
		$.ajax({
			url:"saveTeam.htm",
			type: "post",
			data : $("#teamUI").serialize(),
			error: function(obj){
				alert(obj);
				closeloading();
			},
			success: function(obj){
				var val = obj.split(':;');
				if(val[0] == 'success'){
					alert(val[1]);
					//reset();
				}else
					alert(val[1]);
				getTeamDetails();
				closeloading();
			}});
	}else{
		$('#validationErrorMessageId').show();
		return false;
	}
}
function viewListOfEmployeesForSelectedFilter(){
	if(!validateFilters()){
		alert("Please check the filters.");
		return false;
	}
	loading();
	$('#empTable').datagrid('options').loadMsg = 'Processing, please wait .... ';  // change to other message
	$('#empTable').datagrid('loading');  //
	removeGridMessage("#empTable");
	$.ajax({
		url:"viewListOfEmployeesForSelectedFilter.htm",
		type: "post",
		dataType: 'json',
		data : $("#teamUI").serialize(),
		error: function(obj){
			$('#empTable').datagrid('loaded');  // hide loading message
			closeloading();
			alert(obj);
		},
		success: function(obj){
			$("#empTable").datagrid().width = '100%';
			$('#empTable').datagrid('loadData',obj); 
			$('#empTable').datagrid('loaded');  // hide loading message
			if(obj.length == 0){
				showGridMessage('#empTable','No Records Found');
			}else{
				removeGridMessage("#empTable");
			}
			closeloading();
			$(".viewListPopUpClass").colorbox({inline:true, width:"50%",height:"50%"});
		    $("#click").click(function(){
		            $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
		            return true;
		    });
		}});
}
function selectValues(val){
	if(type == "radio"){
		var radios = document.getElementsByName('valueD');
		for (var i=0, len=radios.length; i<len; i++) {
	        if (radios[i].checked ) { // radio checked?
	        	$("#value"+val).searchbox('setValue',radios[i].value);
	        	break;
	        }
	    }
	}else if(type == "checkbox"){
		var radios = document.getElementsByName('valueD');
		var temp = '';
		for (var i=0, len=radios.length; i<len; i++) {
	        if (radios[i].checked ) { // radio checked?
	        		temp = temp+radios[i].value+",";
	        }
	    }
		temp = temp.substring(0, temp.length-1);
		$("#value"+val).searchbox('setValue',temp);
	}
	$('#cboxClose').click();
}
function doSetValues(obj,index){
	var text = $("#value"+index).searchbox('getValue'); 
	var arr = text.split(",");
	var count = 0;
	var html='';
	if(obj.length == 0){
		html = html+'<tr><td colspan="2" style="text-align: center;">No Records found to select</td></tr>';
	}else{
		$.each(obj, function(i, item){
			if(count == 0){
				html = html +'<tr><td style="width:50%">';
				if(arr.indexOf(item.value) != -1)
					html = html +'<input type="'+type+'" name="valueD" checked="true" value="'+item.value+'">'+item.value+'</input></td>';
				else
					html = html +'<input type="'+type+'" name="valueD" value="'+item.value+'">'+item.value+'</input></td>';
				count++;
			}else if(count == 1){
				html = html +'<td style="width:50%">';
				if(arr.indexOf(item.value) != -1)
					html = html +'<input type="'+type+'" name="valueD" checked="true" value="'+item.value+'">'+item.value+'</input></td></tr>';
				else
					html = html +'<input type="'+type+'" name="valueD" value="'+item.value+'">'+item.value+'</input></td></tr>';
				count--;
			}
		});
	}
	html = html+"<tr><td colspan='2' style='text-align: center;'><input type='button' value='Select' class='button' onclick='selectValues("+index+")'></td></tr>";
	$('#tbodyValueId').html(html);
	closeloading();
	$(".searchbox-button").colorbox({inline:true, width:"50%",height:"50%"});
    $("#click").click(function(){
            $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
            return true;
    });
}
function doSearchValue1(){
	loading();
	var val = $('#field1').val();
	var operator = $('#operator1').val();
	if(val == '' || operator == ''){
		alert('Please select both field and operator for Filter Field 1');
		closeloading();
		return false;
	}else{
		if(operator == '=')
			type = "radio";
		else
			type = "checkbox";
		$.ajax({
			url:"doSearchOrgValue.htm?tableName="+val,
			type: "post",
			dataType: 'json',
			error: function(obj){
				closeloading();
				alert(obj);
			},
			success: function(obj){
				doSetValues(obj,1);
			}});
	}
}
function doSearchValue2(){
	loading();
	var val = $('#field2').val();
	var operator = $('#operator2').val();
	if(val == '' || operator == ''){
		alert('Please select both field and operator for Filter Field 2');
		closeloading();
		return false;
	}else{
		if(operator == '=')
			type = "radio";
		else
			type = "checkbox";
		$.ajax({
			url:"doSearchOrgValue.htm?tableName="+val,
			type: "post",
			dataType: 'json',
			error: function(obj){
				closeloading();
				alert(obj);
			},
			success: function(obj){
				doSetValues(obj,2);
			}});
	}
}
function doSearchValue3(){
	loading();
	var val = $('#field3').val();
	var operator = $('#operator3').val();
	if(val == '' || operator == ''){
		alert('Please select both field and operator for Filter Field 3');
		closeloading();
		return false;
	}else{
		if(operator == '=')
			type = "radio";
		else
			type = "checkbox";
		$.ajax({
			url:"doSearchOrgValue.htm?tableName="+val,
			type: "post",
			dataType: 'json',
			error: function(obj){
				closeloading();
				alert(obj);
			},
			success: function(obj){
				doSetValues(obj,3);
			}});
	}
}
function doSearchValue4(){
	loading();
	var val = $('#field4').val();
	var operator = $('#operator4').val();
	if(val == '' || operator == ''){
		alert('Please select both field and operator for Filter Field 4');
		closeloading();
		return false;
	}else{
		if(operator == '=')
			type = "radio";
		else
			type = "checkbox";
		$.ajax({
			url:"doSearchOrgValue.htm?tableName="+val,
			type: "post",
			dataType: 'json',
			error: function(obj){
				closeloading();
				alert(obj);
			},
			success: function(obj){
				doSetValues(obj,4);
			}});
	}
}
function doSearchValue5(){
	loading();
	var val = $('#field5').val();
	var operator = $('#operator5').val();
	if(val == '' || operator == ''){
		alert('Please select both field and operator for Filter Field 5');
		closeloading();
		return false;
	}else{
		if(operator == '=')
			type = "radio";
		else
			type = "checkbox";
		$.ajax({
			url:"doSearchOrgValue.htm?tableName="+val,
			type: "post",
			dataType: 'json',
			error: function(obj){
				closeloading();
				alert(obj);
			},
			success: function(obj){
				doSetValues(obj,5);
			}});
	}
}
function validateFilters(){
	var conjuction1 = $('#conjuction1').val();
	var conjuction2 = $('#conjuction2').val();
	var conjuction3 = $('#conjuction3').val();
	var conjuction4 = $('#conjuction4').val();
	var field1 = $('#field1').val();
	var field2 = $('#field2').val();
	var field3 = $('#field3').val();
	var field4 = $('#field4').val();
	var field5 = $('#field5').val();
	var operator1 = $('#operator1').val();
	var operator2 = $('#operator2').val();
	var operator3 = $('#operator3').val();
	var operator4 = $('#operator4').val();
	var operator5 = $('#operator5').val();
	
	if(field2 == '' && operator2 =='' && conjuction1 !='')
		return false;
	else if(field3 == '' && operator3 =='' && conjuction2 !='')
		return false;
	else if(field4 == '' && operator4 =='' && conjuction3 !='')
		return false;
	else if(field5 == '' && operator5 =='' && conjuction4 !='')
		return false;
	else if(field2 != '' && operator2 !='' && conjuction1 =='')
		return false;
	else if(field3 != '' && operator3 !='' && conjuction2 =='')
		return false;
	else if(field4 != '' && operator4 !='' && conjuction3 =='')
		return false;
	else if(field5 != '' && operator5 !='' && conjuction4 =='')
		return false;
	else if(field1 != '' && operator1 =='')
		return false;
	else if(field2 != '' && operator2 =='')
		return false;
	else if(field3 != '' && operator3 =='')
		return false;
	else if(field4 != '' && operator4 =='')
		return false;
	else if(field5 != '' && operator5 =='')
		return false;
	return true;
}
</script>
