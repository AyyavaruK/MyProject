/**
 * 
 */
$.extend($.fn.datagrid.methods, {
		editCell : function(jq, param) {
			return jq.each(function() {
				var opts = $(this).datagrid('options');
				var fields = $(this).datagrid('getColumnFields', true).concat(
						$(this).datagrid('getColumnFields'));
				for (var i = 0; i < fields.length; i++) {
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor1 = col.editor;
					if (fields[i] != param.field) {
						col.editor = null;
					}
				}
				$(this).datagrid('beginEdit', param.index);
				for (var i = 0; i < fields.length; i++) {
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor = col.editor1;
				}
			});
		}
	});

	var editIndex = undefined;
	function endEditing() {
		if (editIndex == undefined) {
			return true;
		}
		if ($('#tt').datagrid('validateRow', editIndex)) {
			$('#tt').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickCell(index, field) {
		if($('#tt').datagrid('getRows')[index].currentApprovalLevelDisableStatus == "false")
			return false;
		if (endEditing()) {
			$('#tt').datagrid('editCell', {
				index : index,
				field : field
			});
			//.datagrid('updateRow',{index: index,row:{}});
			editIndex = index;
		}
	}
	function update() {
		$('#tt').datagrid('acceptChanges');
		var rows = $('#tt').datagrid('getSelections');
		for (var i = 0; i < rows.length; i++) {
			alert(rows[i].perfGrade+"==="+rows[i].incrementPercentage);
		}
	}

var perfGradeList = [
		    {value:'A',name:'A'},
		    {value:'B',name:'B'},
		    {value:'C',name:'C'}
		];
var updateList = new Array();
$(document).ready(function(){
	  //$('#container').layout();
	  showGridMessage('#tt','Please select Team on left side panel to display your team employees');
	  showGridMessage('#budgetTable','Please select Team on left side panel to display team wise Summary');
	  showGridMessage('#budgetTable1','Please select Team on left side panel to display team wise budget');
	  
	  $(".viewListPopUpClass").colorbox({inline:true, width:"50%",height:"50%"});
	    $("#click").click(function(){
	            $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
	            return true;
	    });
});
String.prototype.startsWith = function (str){
      return this.indexOf(str) == 0;
};
$("#teamListTable input[type='checkbox']").click( function(){
	var numberOfChBox = $("#teamListTable input[type='checkbox']").length;
    var checkArray = ""; 
	for(var i = 0; i < numberOfChBox; i++) {
	     if($('#teamCheckBoxId'+i).is(':checked')) {
            if(checkArray != "")
	    	 	checkArray = checkArray+$('#teamCheckBoxId'+i).val()+",";
            else
            	checkArray = $('#teamCheckBoxId'+i).val()+",";
        }
    }
	checkArray = checkArray.substring(0, checkArray.length-1);
	showTeamEmployees("", checkArray);
});
function showTeamEmployeesAjax(){
	closeloading();
	var numberOfChBox = $("#teamListTable input[type='checkbox']").length;
    var checkArray = ""; 
	for(var i = 0; i < numberOfChBox; i++) {
	     if($('#teamCheckBoxId'+i).is(':checked')) {
            if(checkArray != "")
	    	 	checkArray = checkArray+$('#teamCheckBoxId'+i).val()+",";
            else
            	checkArray = $('#teamCheckBoxId'+i).val()+",";
        }
    }
	checkArray = checkArray.substring(0, checkArray.length-1);
	showTeamEmployees("", checkArray);
}
  $('#teamListTable a ').click(function() {
		$('#teamListTable a').removeClass('selectLink');
		$(this).closest('a').addClass('selectLink');
	});
function getSelected(){
  var row = $('#tt').datagrid('getSelected');
	  if (row){
	 	 $.messager.alert('Info', row.coworker_name+":"+row.employeeId+":"+row.supervisor);
	  }
  }
function sendForApproval(){
    var teamId ='';
    var rows = $('#tt').datagrid('getChecked');
    for(var i=0; i<rows.length; i++){
    	teamId =rows[i].team;
    }
   
  	$.ajax({
		url:"getApprovalLevels.htm?teamId="+teamId,
		type: "post",
		dataType: 'json',
		error: function(obj){
			$('#empApproveTable').datagrid('loaded');  // hide loading message
			closeloading();
			alert(obj);
		},
		success: function(obj){
			var count = 0;
			var html='';
			if(obj.length == 0){
				html = html+'<tr><td colspan="2" style="text-align: center;">No Records found to select</td></tr>';
			}else{
				html = html +'<tr><th style="width:5%"></th><th style="width:45%">';
				html = html +'Employee</th>';
				html = html +'<th style="width:50%">Merit Approval Level</th></tr>';
				$.each(obj, function(i, item){
						html = html +'<tr><td style="width:5%">';
						html = html +'<input type="radio" name="valueD" value="'+item.empID+'#'+item.meritApprovalLevel+'"></input></td>';
						html = html +'<td style="width:45%">';
						html = html +item.empID+'</td>';
						html = html +'<td style="width:50%">';
						html = html +item.meritApprovalLevel+'</td></tr>';
				});
			}
			html = html+"<tr><td colspan='3' style='text-align: center;'><input type='button' value='Send' class='button' onclick='selectedValueForApproval()'></td></tr>";
			$('#tbodyId').html(html);
			
			closeloading();
			$(".viewListPopUpClass").colorbox({inline:true, width:"50%",height:"50%"});
		    $("#click").click(function(){
		            $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
		            return true;
		    });
		}});
	}
	function selectedValueForApproval(){
		loading();
		var radios = document.getElementsByName('valueD');
		for (var i=0, len=radios.length; i<len; i++) {
			
	        if (radios[i].checked ) { 
	        	var values = radios[i].value;
	        	var submittedTo = values.split('#')[0];
	        	var submittedLevel = values.split('#')[1];
	        	$('#tt').datagrid('acceptChanges');
	           	updateList = new Array();
	        	var rows = $('#tt').datagrid('getChecked');
	        	var team = '';
	           	if(rows.length > 0){
	           		loading();
	           		for(var i=0; i<rows.length; i++){
	        			  var salarPlanningDTO = new Object();
	        		      salarPlanningDTO.employeeId = rows[i].employeeId;
	            		  salarPlanningDTO.newRate = rows[i].newRate;
	            		  salarPlanningDTO.rate = rows[i].rate;
	            		  salarPlanningDTO.maximum = rows[i].maximum;
	            		  salarPlanningDTO.lumsum = rows[i].lumsum;
	            		  salarPlanningDTO.coworker_name = rows[i].coworker_name;
	            		  salarPlanningDTO.type = rows[i].type;
	            		  salarPlanningDTO.perfGrade = rows[i].perfGrade;
	            		  salarPlanningDTO.incrementPercentage = rows[i].incrementPercentage;
	            		  salarPlanningDTO.meritPeriod = rows[i].meritPeriod;
	            		  salarPlanningDTO.quartile = rows[i].quartile;
	            		  salarPlanningDTO.payEntity = rows[i].payEntity;
	            		  salarPlanningDTO.team = rows[i].team;
	            		  team = rows[i].team;
	            		  updateList.push(salarPlanningDTO);
	            	}
	             	$.ajax({
	         			url:"sendForApproval.htm?teamName="+team+"&teamId="+team+"&submittedTo="+submittedTo+"&submittedLevel="+submittedLevel,
	         			type: "post",
	         			data: JSON.stringify(updateList) ,
	         			contentType : "application/json; charset=utf-8",
	         			error: function(obj){
	         				console.log("error");
	         				alert(obj);
	         			},
	         			success: function(obj){
	         				alert(obj);
	         				showTeamEmployeesAjax();
	         				$('#cboxClose').click();
	         				return false;
	         			}});
	             	closeloading();
	           	}
	        	
	        }
		}
	}
	function rowSelected(index,row){
		$('#tt').datagrid('checkRow',index);
		$('#tt').datagrid('selectRow',index);
	}
  	/*function rowSelected(){
  		var rows = $('#tt').datagrid('getSelections');
  		if(rows.length > 0){
  			 $("#updateRateTableId").toggle(true);
  		}else{
  			$("#updateRateTableId").toggle(false);
  		}
  	}*/
   function updateEmployeeDetails(){
	   updateList = new Array();
	    var incrementPercentage = $("#incrementPercentage").val().trim(); 
      	var perfGrade = $("#perfGrade").val().trim();
      	if(incrementPercentage == '' && perfGrade == '')
      		alert("Please select either Performance Grade or Enter  Percentage");
   		else{
   			loading();
       		var rows = $('#tt').datagrid('getSelections');
               	for(var i=0; i<rows.length; i++){
   	      			  var salarPlanningDTO = new Object();
   	      		      salarPlanningDTO.employeeId = rows[i].employeeId;
   	          		  salarPlanningDTO.newRate = rows[i].newRate;
   	          		  salarPlanningDTO.rate = rows[i].rate;
   	          		  salarPlanningDTO.maximum = rows[i].maximum;
   	          		  salarPlanningDTO.lumsum = rows[i].lumsum;
   	          		  salarPlanningDTO.perfGrade = perfGrade;
   	          		  salarPlanningDTO.incrementPercentage = incrementPercentage;
   	          		  salarPlanningDTO.meritPeriod = rows[i].meritPeriod;
   	          		  salarPlanningDTO.quartile = rows[i].quartile;
   	          		  salarPlanningDTO.payEntity = rows[i].payEntity;
   	          		  updateList.push(salarPlanningDTO);
   	          	}
               	$.ajax({
           			url:"updateEmployeeDetails.htm",
           			type: "post",
           			data: JSON.stringify(updateList) ,
           			contentType : "application/json; charset=utf-8",
           			error: function(obj){
           				console.log("error");
           				alert(obj);
           			},
           			success: function(obj){
           				alert(obj);
           				//location.reload(true);
           				$("#updateRateTableId").toggle(false);
           				$("#incrementPercentage").val(""); 
           	          	$("#perfGrade").val("");
           				showTeamEmployeesAjax();
           				return false;
           			}});
    }
   }
   function updateEmployeeDetailsSelected(){
	    $('#tt').datagrid('acceptChanges');
	   	updateList = new Array();
		var rows = $('#tt').datagrid('getSelections');
       	if(rows.length > 0){
       		loading();
       		for(var i=0; i<rows.length; i++){
    			  var salarPlanningDTO = new Object();
    		      salarPlanningDTO.employeeId = rows[i].employeeId;
        		  salarPlanningDTO.newRate = rows[i].newRate;
        		  salarPlanningDTO.rate = rows[i].rate;
        		  salarPlanningDTO.maximum = rows[i].maximum;
        		  salarPlanningDTO.lumsum = rows[i].lumsum;
        		  salarPlanningDTO.type = rows[i].type;
        		  salarPlanningDTO.perfGrade = rows[i].perfGrade;
        		  salarPlanningDTO.incrementPercentage = rows[i].incrementPercentage;
        		  salarPlanningDTO.meritPeriod = rows[i].meritPeriod;
        		  salarPlanningDTO.quartile = rows[i].quartile;
        		  salarPlanningDTO.payEntity = rows[i].payEntity;
        		  updateList.push(salarPlanningDTO);
        	}
         	$.ajax({
     			url:"updateEmployeeDetails.htm",
     			type: "post",
     			data: JSON.stringify(updateList) ,
     			contentType : "application/json; charset=utf-8",
     			error: function(obj){
     				console.log("error");
     				alert(obj);
     			},
     			success: function(obj){
     				alert(obj);
     				//location.reload(true);
     				/*$("#updateRateTableId").toggle(false);
     				$("#incrementPercentage").val(""); 
     	          	$("#perfGrade").val("");*/
     				showTeamEmployeesAjax();
     				return false;
     			}});
       	}else{
       		alert("No Changes to Apply");
       	}
   }
   function setValuesButton(){
   	$('#popUphrefId').click();
   }
   function setValues(){
         var rows = $('#tt').datagrid('getSelections');
         if(rows.length == 1){
       	  $(".inline").colorbox({inline:true, width:"40%"});
             $("#click").click(function(){
                     $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
                     return true;
             });
         }else if(rows.length > 1){
       	  if(confirm("You have selected multiple. All records will update with same Rate. Do you wish to continue ?")){
       		  $(".inline").colorbox({inline:true, width:"40%"});
                 $("#click").click(function(){
                         $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
                         return true;
                 });
       	  }else
             	return false;
         }else{
             alert("Please select row for further process");
             window.parent.jQuery.colorbox.close();
             return false;
         }
   }
   
   function anualBudgetSummary(){
	$.ajax({
		url:"anualBudgetSummary.htm",
		type: "get",
		data: $('#budgetSummary').serialize(),
		error: function(obj){
			alert(obj);
		},
		success: function(obj){
			alert(obj);
			window.parent.jQuery.colorbox.close();
			return false;
		}});
}
   function showSalRangeButton(){
   	$('#salRangePopUphrefId').click();
   }
   function showSalRange(){
  	 $(".salRangePopUpClass").colorbox({inline:true, width:"80%"});
	
       //Example of preserving a JavaScript event for inline calls.
       $("#click").click(function(){
               $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
               return true;
       });
  }
   function showDashboard(){
	   $('#tt').datagrid('acceptChanges');
		var rows = $('#tt').datagrid('getSelections');
		if(rows.length >= 1) {
			if(confirm("Do you want to Save the changes ?")){
				updateEmployeeDetailsSelected();
				return false;
			}else{
				showDashBoardPage();
			}
		}else{
			showDashBoardPage();
		}
   }
   function showDashBoardPage(){
	   document.forms[0].action = "dashboard.htm";
	   document.forms[0].method = "get";
	   document.forms[0].submit(); 
   }
  function showTeamEmployees(teamName,teamId){
	//$('#tb').css("display","block");
	  $('#iconSaveId').removeAttr('disabled');
	$('#approveAll').removeAttr('disabled');
	$('#viewListRefId').removeAttr('disabled');
	$('#iconSaveId').attr("class","button");
	$('#approveAll').attr("class","button");
	$('.my-viewListRefId').attr('disabled', true);
	//$('#viewListRefId').attr("class","button");
    $('#tt').datagrid('options').loadMsg = 'Processing, please wait .... ';  // change to other message
    $('#tt').datagrid('loading');  // display loading message
   
    $('#budgetTable1').datagrid('options').loadMsg = 'Processing, please wait .... ';  // change to other message
    $('#budgetTable1').datagrid('loading');  // 
   
    $('#budgetTable').datagrid('options').loadMsg = 'Processing, please wait .... ';  // change to other message
    $('#budgetTable').datagrid('loading');  // 
	$.ajax({
		url:"getSalaryPlanningDetails.htm?teamName="+teamName+"&teamId="+teamId,
		type: "post",
		dataType: 'json',
		error: function(obj){
			$('#tt').datagrid('loaded');  // hide loading message
		},
		success: function(obj){
			removeGridMessage("#tt");
			$('#tt').datagrid('loadData',obj); 
			$('#tt').prop('title', teamName+" Employee List");
			$('#tt').datagrid('loaded');  // hide loading message
			loadBudgetSummaryTeamWise(teamName,teamId);
		}});
}
   function loadBudgetSummaryTeamWise(teamName,teamId){
   	$.ajax({
		url:"anualBudgetSummary.htm?teamName="+teamName+"&teamId="+teamId,
		type: "post",
		dataType: 'json',
		error: function(obj){
			$('#budgetTable1').datagrid('loaded');  // hide loading message
		},
		success: function(obj){
			removeGridMessage("#budgetTable1");
			$('#budgetTable1').datagrid({
				rowStyler:function(index,row){
					if (row.anualBudgetType == 'Total'){
						return 'color:blue;font-weight:bold;';
					}else{
						return 'color:#989898;';	
					}
				}
			});
			$('#budgetTable1').datagrid('loadData',obj); 
			$('#budgetTable1').prop('title', teamName+" Employee List");
			$('#budgetTable1').datagrid('loaded');  // hide loading message
			loadRatingsAndIncreaseSummaryTeamWise(teamName,teamId);
		}});
   }
   function loadRatingsAndIncreaseSummaryTeamWise(teamName,teamId){
   	$.ajax({
		url:"ratingsAndIncreaseSummary.htm?teamName="+teamName+"&teamId="+teamId,
		type: "post",
		dataType: 'json',
		error: function(obj){
			$('#budgetTable').datagrid('loaded');  // hide loading message
		},
		success: function(obj){
			removeGridMessage("#budgetTable");
			$('#budgetTable').datagrid('loadData',obj); 
			$('#budgetTable').prop('title', teamName+" Employee List");
			$('#budgetTable').datagrid('loaded');  // hide loading message
		}});
   }
   
function onResizeColumn(field,width){
	$.ajax({
		url:"updateSalaryPlanningColumnWidth.htm?field="+field+"&width="+width,
		type: "GET",
		dataType: 'json',
		error: function(obj){
		},
		success: function(obj){
		}});
}
function rowStyler(index,row){
	if(row.currentApprovalLevelDisableStatus == "false"){
		//alert();
		//$('#tb').css("display","false");
		$('#iconSaveId').attr("disabled","false");
		$('#approveAll').attr("disabled","false");
		//$('#sendForApproval').attr("disabled","false");
		$('#viewListRefId').attr("disabled","false");
		$('#iconSaveId').attr("class","button1");
		$('#approveAll').attr("class","button1");
		//$('#sendForApproval').attr("class","button1");
		//$('#viewListRefId').attr("class","button1");
		//$('.my-viewListRefId').attr('disabled', false);
		$('.my-viewListRefId').bind('click', false);
	}
	if(row.errorCode == "ERROR1" || row.errorCode == "ERROR2" || row.errorCode == "ERROR3" || row.errorCode == "ERROR4" ){
		return 'background-color:#FFEB9C;color:#000;';
	}else if(row.errorCode == "ERROR0"){
		return 'background-color:#D8E4BC;color:#000;';
	}else{
		return 'background-color:#fff;color:#000;';
	}
}
function perfGradeCellStyler(value,row,index){
	if(row.currentApprovalLevelDisableStatus == "false")
		return 'color:#989898;';
	else
		return 'color:red;';
}
function incrementCellStyler(value,row,index){
	if(row.currentApprovalLevelDisableStatus == "false")
		return 'color:#989898;';
	else
		return 'color:red;';
}
function approveAll(){
	$('#tt').datagrid('acceptChanges');
   	updateList = new Array();
	var rows = $('#tt').datagrid('getChecked');
	var team = '';
   	if(rows.length > 0){
   		loading();
   		for(var i=0; i<rows.length; i++){
			  var salarPlanningDTO = new Object();
		      salarPlanningDTO.employeeId = rows[i].employeeId;
    		  salarPlanningDTO.newRate = rows[i].newRate;
    		  salarPlanningDTO.rate = rows[i].rate;
    		  salarPlanningDTO.maximum = rows[i].maximum;
    		  salarPlanningDTO.lumsum = rows[i].lumsum;
    		  salarPlanningDTO.type = rows[i].type;
    		  salarPlanningDTO.perfGrade = rows[i].perfGrade;
    		  salarPlanningDTO.incrementPercentage = rows[i].incrementPercentage;
    		  salarPlanningDTO.meritPeriod = rows[i].meritPeriod;
    		  salarPlanningDTO.quartile = rows[i].quartile;
    		  salarPlanningDTO.payEntity = rows[i].payEntity;
    		  team = rows[i].team;
    		  updateList.push(salarPlanningDTO);
    	}
     	$.ajax({
 			url:"approveAll.htm?teamName="+team+"&teamId="+team,
 			type: "post",
 			data: JSON.stringify(updateList) ,
 			contentType : "application/json; charset=utf-8",
 			error: function(obj){
 				console.log("error");
 				alert(obj);
 			},
 			success: function(obj){
 				alert(obj);
 				showTeamEmployeesAjax();
 				return false;
 			}});
   	}else{
   		alert("No Changes to Apply");
   	}
}
function sorter(){
	alert();
}
function onLoadSuccess(){
	var rows = $('#tt').datagrid('getRows');
   	if(rows.length > 0){
   		for(var i=0; i<rows.length; i++){
   			if(rows[i].errorCode == "ERROR1")
   				$('#datagrid-row-r4-1-'+i).prop('title', 'Increase Percent is Less than recommended Minimum');
   			else if(rows[i].errorCode == "ERROR2")
				$('#datagrid-row-r4-1-'+i).prop('title', 'Increase Percent is greater than recommended Maximum');
			else if(rows[i].errorCode == "ERROR0")
				$('#datagrid-row-r4-1-'+i).prop('title', 'Validated');
			else if(rows[i].errorCode == "ERROR3")
				$('#datagrid-row-r4-1-'+i).prop('title', 'Employee Pay is below minimum');
			else if(rows[i].errorCode == "ERROR4")
				$('#datagrid-row-r4-1-'+i).prop('title', 'Employee Pay is above Maximum');
   		}
   	}
}
function numberFilter(evt){
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 47 && charCode < 58)
		return true;
	else if(charCode == 46 || charCode == 35 || charCode == 36 || charCode == 37 || charCode == 39 || charCode == 8)
		return true;
	else
		return false;
}
function greyOutValues(index,row){
	return 'color:#989898;';
}
