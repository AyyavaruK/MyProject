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
				<a href="javascript:void(0);" class="select">Merit Period Update</a>
				<span style="float: right">
				<a href="#" onclick="javascript:reset()" class="easyui-linkbutton" iconCls="icon-add">Update Merit Period</a>
				<a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>	
				</span>		
			</div>
			</th>
	      </tr>
	    </table>
	    <table width="100%" border="0" cellspacing="1" cellpadding="5" class="data-table-new" align="right">
	      <tr>
		     <th colspan="6" style="text-align: left;padding-left: 10px" class="head">
    			Update Merit Period
    		 </th>
	      </tr>
	     
	      <tr>
		     <td style="width:20%" nowrap="nowrap">
    			<label>Merit Period</label><span class="mandatory">*</span>
    		 </td>
    		 <td>
    		 	<form:select path="value" cssClass="required">
    		 		<form:option value="">Select Merit period</form:option>
    		 		<c:forEach var="i" begin="2015" end="2100" step="1">
    		 			<form:option value="${i}">${i}</form:option>
    		 		</c:forEach>
    		 	</form:select>
    		 	&nbsp;&nbsp;<span style="color: green;padding-right: 2px;"><b>Current Merit Period</b> : ${currentMeritPeriod}</span>
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
function save(){
	if($('#mapdto').valid()){
		loading();
		$.ajax({
			url:"saveMeritPeriodUpdate.htm",
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
			}});
	}
}
function reset(){
	document.forms[0].action='meritPeriodUpdate.htm';
	document.forms[0].method = "post";
	document.forms[0].submit();
}
</script>
