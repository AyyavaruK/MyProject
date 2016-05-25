/**
 * Author Spothu
 */
function numbersonly(e, decimal,value) {

	var key;
	var keychar;

	if (window.event) {
		key = window.event.keyCode;
	}
	else if (e) {
		key = e.which;
	}
	else {
		return true;
	}
	keychar = String.fromCharCode(key);

	if ((key==null) || (key==0) || (key==8) ||  (key==9) || (key==13) || (key==27) ) {
		return true;
	}
	else if ((("0123456789").indexOf(keychar) > -1)) {
		return true;
	}
	else if (decimal && (keychar == ".")) { 
		for (var i=0;i<value.length;i++){
			if(value.charAt(i)==keychar){
				return false;
				break;
			}
		}
		return true;
	}
	else
		return false;
}

//function to accept just 0-9 nos
// Number Validation
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
//End Number Validation		



function highLightTab(){
	$("#personal").attr('class', 'button');
	$("#compensation").attr('class', 'button');
	$("#employement").attr('class', 'button');
	$("#position").attr('class', 'button');
	$("#otherData").attr('class', 'button');
}

function showGridMessage(target,displayText) {
	var vc = $(target).datagrid('getPanel').children(
			'div.datagrid-view');
	vc.children('div.datagrid-empty').remove();
	if (!$(target).datagrid('getRows').length) {
		var d = $('<div class="datagrid-empty"></div>').html(displayText).appendTo(vc);
		d.css({
			position : 'absolute',
			left : 0,
			top : 50,
			width : '100%',
			textAlign : 'center'
		});
	} else {
		vc.children('div.datagrid-empty').remove();
	}
}
function removeGridMessage(target){
	var vc = $(target).datagrid('getPanel').children(
	'div.datagrid-view');
	vc.children('div.datagrid-empty').remove();
}
function fillAllDetails(empId){
	$.ajax({
		url:"fillAllDetails.htm?empId="+empId,
		type: "post",
		dataType: 'text',
		error: function(obj){
		},
		success: function(obj){
			var val = obj.split(",");
			$('#lastNameInputId').combobox('setValue',val[0]);
			$('#firstNameInputId').combobox('setValue',val[1]);
			$('#employeeInpuId').combobox('setValue',val[2]);
			$('#teamInputId').combobox('setValue',val[3]);
			$('#searchUserId').combobox('setValue',val[4]);
		}});
}