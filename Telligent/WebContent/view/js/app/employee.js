/*$('#mobilePhone').usphone();
$('#homePhone').usphone();
$('#workPhone').usphone();
$('#workMobilePhone').usphone();
$('#emergencyMobilePhone').usphone();
$('#emergencyHomePhone').usphone();*/

$(function(){
	$('#dateOfBirthBox').datebox().datebox('calendar').calendar({
		validator: function(date){
			var now = new Date();
			var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			return date<=d2;
		}
	});
});

$(document).ready(function(){
	highLightTab();
	$("#employeeSavetr").css("display","block");
	$("#personal").attr('class', 'buttonSelect');
	if(document.getElementById("successMessage").value == 'success'){
		alert("Employee Details Saved Successfully");
		closeloading();
		getEmployeeDetailsAjax(document.getElementById("employeeId").value);
	}else if(document.getElementById("errorMessage").value !=''){
		$('#effectiveDateBox').datebox('setValue', document.getElementById("effectiveDate").value);
		$('#dateOfBirthBox').datebox('setValue', document.getElementById("dateOfBirth").value);
		//$('#cityId').combobox('setValue', document.getElementById("city").value);
		//$('#stateId').combobox('setValue', document.getElementById("state").value);
		alert(document.getElementById("errorMessage").value);
	}else if(document.getElementById("employeeId").value != '' ){
		getEmployeeDetailsAjax(document.getElementById("employeeId").value);
	}
	fillAllDetails(document.getElementById("employeeId").value);
	empHistory(document.getElementById("employeeId").value);
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
	getEmployeeDetailsAjax(rec.id);
}
function getEmployeeDetailsAjax(id){
	loading();
	fillAllDetails(id);
	$.ajax({
		url:"getEmployeeDetails.htm?empId="+id,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			alert(obj);
		},
		success: function(obj){
			document.getElementById("updateble").value="yes";
			setEmployeeDetails(obj,id);
		}});
}
function setEmployeeDetails(obj,id){
	//var city = null;
	//getStateList();
	$.each(obj, function(i, item){
			if(i=='picture'){
  			}else if(i=='pictureBase64'){
  			  document.getElementById('image').src= 'data:image/bmp;base64,'+item;
	  		}else if(i=='effectiveDate'){
	  			$('#effectiveDateBox').datebox('setValue', item);
	  		}else if(i=='dateOfBirth'){
	  			$('#dateOfBirthBox').datebox('setValue', item);
	  		}else if(i=='city'){
	  			$('#cityId').combobox('setValue', item);
	  		}else if(i=='state'){
	  			$('#stateId').combobox('setValue', item);
	  		}else if(i=='employeeId'){
	  			$('#employeeId').combobox('setValue', item);
	  		}else if(i=='minor'){
		  		if(item)
		  			document.getElementById(i).checked = true;
		  		else
		  			document.getElementById(i).checked = false;
		  	}else if(i=='city'){
		  		city = item;
		  	}else{
		  		$("#"+i).val(item);   				  		
		  	}			  		
	});
	document.getElementById('employeeId').readOnly='true';
	document.getElementById('employeeId').title='Disabled on Edit';
	document.getElementById('operation').value='edit';
	var effDate = $('#effectiveDateBox').datebox('getValue');
	document.getElementById('empEffectiveDt').innerHTML= 'Effective Date &nbsp;&nbsp;&nbsp;&nbsp;   ' +effDate;
	$('#effectiveDateBox').datebox('setValue', '');
	//getCityList(document.getElementById('state').value,city);
	if(id!=null)
		empHistory(id);
	closeloading();
}
function empHistory(empId){
	$('#employeePersonalHistoryTable').datagrid('options').loadMsg = 'Processing, please wait .... ';  // change to other message
	$('#employeePersonalHistoryTable').datagrid('loading');  // 
	$.ajax({
		url:"getEmployeeDetailsHistory.htm?empId="+empId,
		type: "post",
		dataType: 'json',
		error: function(obj){
			$('#employeePersonalHistoryTable').datagrid('loaded');  // hide loading message
		},
		success: function(obj){
			if(obj.length > 0){
				removeGridMessage('#employeePersonalHistoryTable','');
				$('#employeePersonalHistoryTable').datagrid('loadData',obj); 
				$('#employeePersonalHistoryTable').datagrid('loaded');  // hide loading message				
			}else{
				showGridMessage('#employeePersonalHistoryTable','History Records not available');
				$('#employeePersonalHistoryTable').datagrid('loadData',obj); 
				$('#employeePersonalHistoryTable').datagrid('loaded');  // hide loading message
			}
		}});
}
function reset(){
	document.getElementById("updateble").value="yes";
	//document.getElementById("employeeForm").reset();
	document.forms[0].action="employee.htm";
	document.forms[0].method = "post";
	document.forms[0].submit();
}
function save(){
	$('#validationErrorMessageId').hide();
	if(document.getElementById("updateble").value == "no"){
		if(confirm("This record is older than the current record.  Please Confirm")){
			saveEmp();
		}
	}else{
		saveEmp();
	}
}
function saveEmp(){
	var effectiveDate = $('#effectiveDateBox').datebox('getValue');
	var dateOfBirth = $('#dateOfBirthBox').datebox('getValue');
	var city = document.getElementsByName('city')[0].value;
	var state = document.getElementsByName('state')[0].value;
	document.getElementById("effectiveDate").value=effectiveDate;
	document.getElementById("dateOfBirth").value=dateOfBirth;
	loading();
	if($('#employeeForm').valid() && dateOfBirth!='' && effectiveDate!='' && city!='' && state!=''){
		loading();
		$.ajax({
			url:"checkPrerequisites.htm",
			type: "post",
			data : $("#employeeForm").serialize(),
			enctype : 'multipart/form-data',
			error: function(obj){
				closeloading();
			},
			success: function(obj){
				closeloading();
				if(obj != ''){
					if(confirm(obj))
						save1();
				}else{
					save1();
				}
			}});
	}else{
		$('#validationErrorMessageId').show();
		closeloading();
		//alert('Mandatory fields are missing');
	}
}
function save1(){
	loading();
	var effectiveDate = $('#effectiveDateBox').datebox('getValue');
	var dateOfBirth = $('#dateOfBirthBox').datebox('getValue');
	document.getElementById("effectiveDate").value=effectiveDate;
	document.getElementById("dateOfBirth").value=dateOfBirth;
	document.forms[0].action="saveEmployeeDetails.htm";
	document.forms[0].method = "post";
	document.forms[0].submit();
}

function dateOfBirthSelect(date){
	var age = calculateAge(date.getMonth()+1,date.getDate(),date.getFullYear());
	if(age >= 18){
		document.getElementById('minor').checked = false;
	}else{
		document.getElementById('minor').checked = true;
	}
}
function calculateAge(birthMonth, birthDay, birthYear)
{
  var todayDate = new Date();
  var todayYear = todayDate.getFullYear();
  var todayMonth = todayDate.getMonth();
  var todayDay = todayDate.getDate();
  var age = todayYear - birthYear;
  if (todayMonth < birthMonth - 1)
  {
    age--;
  }
  if (birthMonth - 1 == todayMonth && todayDay < birthDay)
  {
    age--;
  }
  return age;
}
function PreviewImage() {
    var oFReader = new FileReader();
    oFReader.readAsDataURL(document.getElementById("picture").files[0]);

    oFReader.onload = function (oFREvent) {
        document.getElementById("image").src = oFREvent.target.result;
    };
}
function getStateList(){
	$.ajax({
		url:"getStateListAjax.htm",
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
		},
		success: function(data){
			var len = data.length;
			var html = "";
            for(var i=0; i<len; i++){
                 html += '<option value="' + data[i].id + '">' + data[i].stateName + '</option>';
             }
            $('#state').append(html);
            closeloading();
		}});
}
function getCityList(val,city){
	$('#city').empty();
	$('#city').append('<option value="">Select</option>');
	loading();
	$.ajax({
		url:"getCityDetails.htm?stateId="+val,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
		},
		success: function(data){
			var len = data.length;
			var html = "";
            for(var i=0; i<len; i++){
                 html += '<option value="' + data[i].id + '">' + data[i].city + '</option>';
             }
            $('#city').append(html);
            $("#city").val(city); 
            closeloading();
		}});
}
function formatDetail(value,row,index){
	return '<a href="#" onclick="javascript:getEmployeeDetailsFromHistoryAjax('+row.seqNo+')">'+value+'</a>';
}
function getEmployeeDetailsFromHistoryAjax(id){
	loading();
	$.ajax({
		url:"getEmployeeDetailsFromHistoryAjax.htm?seqNo="+id,
		type: "post",
		dataType: 'json',
		error: function(obj){
			closeloading();
			alert(obj);
		},
		success: function(obj){
			document.getElementById("updateble").value="no";
			setEmployeeDetails(obj,null);
		}});
}
function onBeforeLoadState(param){
	param["city"] = document.getElementsByName('city')[0].value;
}