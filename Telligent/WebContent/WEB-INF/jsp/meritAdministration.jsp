<%@ include file="taglib.jsp" %>
<title>Dashboard</title>
<!-- <link href="view/css/style.css" rel="stylesheet" type="text/css" />
<link href="view/css/colorbox.css" rel="stylesheet" type="text/css" />
<script src="view/js/jquery/jquery.colorbox.js"></script>-->
<link href="view/css/style.css" rel="stylesheet" type="text/css" />
<link href="view/css/colorbox.css" rel="stylesheet" type="text/css" />
<script src="view/js/jquery/jquery.colorbox.js"></script>
<script src="view/js/popup.js"></script> 
<link rel="stylesheet" type="text/css" href="view/css/popup.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/icon.css">
<link rel="stylesheet" type="text/css" href="view/css/jquery/demo.css">
<script type="text/javascript" src="view/js/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="view/js/app/telligentCommon.js"></script>

<div id="contentArea">
<div class="loader"></div>
<div class="contentArea">
	<div class="innerleftmerit"  id="col1">
	<input type="hidden" id="isActive"/>
     <table class="leftAccordion" cellspacing="1" cellpadding="5" id="teamListTable">
      <tr>
        <th class="head" height="25" align="left">My Teams</th>
      </tr>
      <c:forEach items="${teams}" var="item" varStatus="i">
	      <tr>
	        <td height="20">
	        	<input type="checkbox" name="teamCheckBoxName" id="teamCheckBoxId${i.index}"  value="${item.teamId}">${item.teamName}
	        	<%-- <a href="javascript:showTeamEmployees('${item.teamName}','${item.teamId}');">${item.teamName}</a> --%>
	        </td>
	      </tr>
      </c:forEach>
    </table>  
 </div>
 <div class="innerrightmerit"  id="flow">
 	<a href="javascript:toggle()" title="Hide Nav 13" id="flowtab"></a>
 	<div style="margin:0px;cursor:auto;" id="tab">
        <div class="wrap">  
        <!-- <a href="#popUp" id="popUphrefId" class="button inline" onclick="setValues();"  style="display: none;">Update Rate</a> -->
        <a href="#salRangePopUp" id="salRangePopUphrefId" class="button salRangePopUpClass" onclick="showSalRange();"  style="display: none;">General Guidelines</a>
    	<table  width="100%" border="0" cellspacing="0" cellpadding="0">
    		<tr>
    			<td colspan="3">
    				<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right">
				      <tr>
				      
				        <th class="head1" height="25" align="left">
				        <div class="innerpage-breadcrum">
							<a href="dashboard.htm;">Dashboard</a> &nbsp;&gt;&nbsp; 
							<a href="meritAdministration.htm">Merit Administration</a> &nbsp;&gt;&nbsp; 
							<a href="javascript:void(0);" class="select">Employee Salary Planning</a>
							<span style="float: right">
								<input type="button" class="button" id="iconSaveId" value="Save" onclick="updateEmployeeDetailsSelected()">
								<input type="button" class="button" id="approveAll" value="Approval All" onclick="approveAll()">
								<!--  <input type="button" class="button" id="sendForApproval" value="Send for Approval" onclick="javascript:sendForApproval()">  -->
								<a href="#viewListPopUp" id="viewListRefId" class="viewListPopUpClass button" onclick="sendForApproval();">Send for Approval</a>
								<input type="button" class="button" id="printOrReports" value="Print / Reports">
							</span>
						</div>
						</th>
				      </tr>
				    </table>
    			</td>
    		</tr>
    		<tr>
    			<td style="width: 45%">
    				<table id="guideLinesTable"  class="easyui-datagrid" title="General Guidelines"   style="width:100%;height:215px;table-layout: fixed;"
						data-options="collapsible:true
										,method: 'post'
										,url: 'salaryPositionRangeDetails.htm'
										,pagination:false
										,emptyMsg: 'No records found'
										,fitColumns:true
										,rowStyler:greyOutValues" >
										<!-- 
										,collapsed:false -->
							<thead style="width: 5000">
								<tr>
									<th colspan="2" data-options="field:'type',width:500"></th> 
									<th colspan="4" data-options="width:186"><b>Position in Salary Range</b></th>
								</tr>
							</thead>
							<thead>
								<tr>
									<th data-options="field:'overallPerformanceRating'" align="left" >Overall <br/> Performance<br/> Rating</th>
									<th data-options="field:'aggregateExpected'" align="right" >Aggregate <br/> Expected Ratings <br/>Distribution @ Co.level</th>
									<th data-options="field:'firstQuartile'" align="right" >1 st Quartile</th>
									<th data-options="field:'secondQuartile'" align="right" >2 nd Quartile</th>
									<th data-options="field:'thirdQuartile'" align="right" >3 rd Quartile</th>
									<th data-options="field:'fourthQuartile'" align="right" >4 th Quartile</th>
								</tr>
	 					</thead>
					</table>
    			</td>
    			<td style="width: 27%">
    				<table id="budgetTable"  class="easyui-datagrid" title="Ratings & Increase Summary" style="width:100%;height:215px;padding-left: 100px"
						data-options="collapsible:true
										,method: 'get'
										,pagination:false
										,emptyMsg: 'No records found'
										,fitColumns:true
										,rowStyler:greyOutValues">
						<thead>
							<tr>
								<th colspan="3">Hourly</th>
								<th colspan="3">Office</th>
							</tr>
						</thead>
						<thead data-options="frozen:true">
							<tr>
								<th data-options="field:'type'" >Performance <br/> Rating</th>
							</tr>
						</thead>
						<thead>
							<tr>
								<th data-options="field:'hourlyA'" align="right" >A</th>
								<th data-options="field:'hourlyB'" align="right" >B</th>
								<th data-options="field:'hourlyC'" align="right" >C</th>
								<th data-options="field:'officeA'" align="right" >A</th>
								<th data-options="field:'officeB'" align="right" >B</th>
								<th data-options="field:'officeC'" align="right" >C</th>
							</tr>
						</thead>
					</table>
    			</td>
    			<td style="width: 27%">
    				<table id="budgetTable1"  class="easyui-datagrid" title="Budget Summary" style="width:100%;height:215px; padding-left: 100px"
						data-options="collapsible:true
										,method: 'get'
										,pagination:false
										,emptyMsg: 'No records found'
										,fitColumns:true">
						<thead data-options="frozen:true">
							<tr>
								<th data-options="field:'anualBudgetType'"  align="left" >Annual Budget</th>
							</tr>
						</thead>
						<thead>
							<tr >
								<th data-options="field:'currentBudget'" align="right" >Current</th>
								<th data-options="field:'newBudget'" align="right" >New</th>
								<th data-options="field:'changeBudget'" align="right" >%Change</th>
							</tr>
						</thead>
					</table>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="3">
    				<table id="tt" class="easyui-datagrid" title="Employee Salary Planning" style="width:100%;height:300px;table-layout: fixed;"
							data-options="collapsible:true
											,remoteSort:false
											,onLoadSuccess:onLoadSuccess
											,emptyMsg: 'No records found'
											,pageSize:20
											,onResizeColumn:onResizeColumn
											,onClickCell: onClickCell
											,toolbar:'#tb'
											,iconCls: 'icon-edit'
											,onSelect:rowSelected
											,onUnselect:rowSelected
											,onSelectAll:rowSelected
											,onUnSelectAll:rowSelected
											,onCheckAll:rowSelected
											,onUncheckAll:rowSelected
											,onCheckAll:rowSelected
											,checkOnSelect: false
											,selectOnCheck: false
											,rowStyler:rowStyler">
							<thead data-options="frozen:true">
								<tr>
									<th sortable="true" data-options="field:'ck',checkbox:'true'" ></th>
									<th sortable="true" data-options="field:'id'" hidden="true" align="right">Id</th>
									<th sortable="true" data-options="field:'employeeId',width:${gridViewMap['employeeId']}"  align="right">Employee ID</th>
									<th sortable="true" data-options="field:'coworker_name',width:${gridViewMap['coworker_name']}"  align="left">Employee Name</th>
								</tr>
							</thead>
							<thead>
								<tr>
									<th sortable="true" data-options="field:'type',width:${gridViewMap['type']}" align="left">Type</th>
									<th sortable="true" data-options="field:'rate',width:${gridViewMap['rate']}" align="right">Current Rate</th>
									<th sortable="true" data-options="field:'perfGrade',editor:{type:'combobox',options:{valueField:'value',textField:'name',data:perfGradeList}},styler:perfGradeCellStyler,width:${gridViewMap['perfGrade']}" align="left" >Perf Grade</th>
									<th sortable="true" data-options="field:'incrementPercentage',editor:{type:'numberbox',options:{precision:2,filter:numberFilter},styler:incrementCellStyler,width:${gridViewMap['incrementPercentage']}}" align="right">Incr %</th>
									<th sortable="true" data-options="field:'newRate',width:${gridViewMap['newRate']}" align="right">New Rate</th>
									<th sortable="true" data-options="field:'lumsum',width:${gridViewMap['lumsum']}" align="right">Lum Sum</th>
									<th sortable="true" data-options="field:'jobTitle',width:${gridViewMap['jobTitle']}" align="left">Primary Job</th>
									<th sortable="true" data-options="field:'updatedDate',width:${gridViewMap['updatedDate']}" align="left">Updated Date</th>
									<th sortable="true" data-options="field:'grade',width:${gridViewMap['grade']}" align="left">Grade</th>
									<th sortable="true" data-options="field:'compaRatio',width:${gridViewMap['compaRatio']}" align="right">Compa Ratio</th>
									<th sortable="true" data-options="field:'minimum',width:${gridViewMap['minimum']}" align="right">Minimum</th>
									<th sortable="true" data-options="field:'midpoint',width:${gridViewMap['midpoint']}" align="right">Midpoint</th>
									<th sortable="true" data-options="field:'maximum',width:${gridViewMap['maximum']}" align="right">Maximum</th>
									<th sortable="true" data-options="field:'quartile',width:${gridViewMap['quartile']}" align="left">Quartile</th>
									<th sortable="true" data-options="field:'category',width:80" align="left">Category</th>
									<!--<th sortable="true" data-options="field:'primaryJob',width:80" align="left">Primary Job</th> -->									
									<th sortable="true" data-options="field:'team',width:80" align="left">Team</th>
									<th sortable="true" data-options="field:'payEntity',width:80" align="left">Pay Entity</th>
									<th sortable="true" data-options="field:'org1',width:80" align="left">Org1</th>
									<th sortable="true" data-options="field:'org2',width:80" align="left">Org2</th>
									<th sortable="true" data-options="field:'org3',width:80" align="left">Org3</th>
									<th sortable="true" data-options="field:'org4',width:80" align="left">Org4</th>
									<th sortable="true" data-options="field:'org5',width:80" align="left">Org5</th>
									<th sortable="true" data-options="field:'org6',width:80" align="left">Org6</th>
									<th sortable="true" data-options="field:'org7',width:80" align="left">Org7</th>
									<th sortable="true" data-options="field:'org8',width:80" align="left">Org8</th>
									<th sortable="true" data-options="field:'org9',width:80" align="left">Org9</th>
									<th sortable="true" data-options="field:'org10',width:80" align="left">Org10</th>
									<th sortable="true" data-options="field:'currentApprovalLevel',width:120" align="left">Current Approval Level</th>
									<th sortable="true" data-options="field:'approvedBy',width:80" align="left">Approved By</th>
									<th sortable="true" data-options="field:'approvedDate',width:80" align="left">Approved Date</th>
									<th sortable="true" data-options="field:'submittedDate',width:80" align="left">Submitted Date</th>
									<th sortable="true" data-options="field:'submittedBy',width:80" align="left">Submitted By</th>
									<th sortable="true" data-options="field:'currentSubmitLevel',width:120" align="left">Current Submit Level</th>
									<th sortable="true" data-options="field:'meritPeriod',width:80" align="left">Merit Period</th>
									<th sortable="true" data-options="field:'errorCode',width:80" align="left" >Error Code</th>
									<th data-options="field:'currentApprovalLevelDisableStatus'" hidden="true" align="right">currentApprovalLevelDisableStatus</th>
								</tr>
							</thead>
						</table>
    			</td>
    		</tr>
    	</table>
   <!--  <table>
    	<tr>
    		<td><input type="button" class="button" value="Assign Default Merit Increases" onclick="javascript:getSelected();"></td>
    		<td><input type="button" class="button" value="Validate Merit Increases"></td>
    		<td><input type="button" class="button" value="Send for Approval" onclick="javascript:sendForApproval()"></td>
    		<td><input type="button" class="button" value="Print / Reports"></td>
            <td><input type="button" class="button" value="Update Rate" onclick="update();" ></td> 
            <td><input type="button" class="button" value="General Guidelines" onclick="showSalRangeButton();" ></td>
    	</tr>
    </table> -->
    
    <!-- <form id="updateRateForm">
	    <table  width="100%" border="0" cellspacing="0" cellpadding="0" id="updateRateTableId" style="display: none;" class="data-table"> 
	       <tr>
	  			<td valign="middle" width="20%" >Enter Grade / Increase % <span class="mandatory">*</span></td>
		        <td valign="middle" width="10%">
		        	<select id="perfGrade">
		        		<option value="">Select</option>
		        		<option value="A">A</option>
		        		<option value="B">B</option>
		        		<option value="C">C</option>
		        	</select>
		        </td>
		        <td valign="middle" width="20%"><input id="incrementPercentage" name="incrementPercentage" onKeyPress="return numbersonly(event, true,this.value)"></input></td>
		        <td valign="middle" height="2" ><input type="button" onclick="javascript:updateEmployeeDetails();" value="Apply" title="Apply Selected Rows with Same Values" class="loginButton"/></td>
		        <td valign="middle" height="2" ><input type="button" onclick="javascript:updateEmployeeDetailsSelected();" value="Apply Selected" title="Apply Selected Rows with Selected Values" class="loginButton"/></td>
		      </tr>
	  	</table>
    </form> -->
		<div class="footer"></div>
  </div>
  </div>
  </div>
  <div style="display: none;">
  <div id="salRangePopUp"  style="padding:10px; background:#fff;" style="width:100px">
  	<br clear="all"/>
	<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right" >
	  <tr>
	     <th class="head1" height="25" align="left">
		  	<div class="innerpage-breadcrum">
		  		<table>
		  			<tr><td>General Guidelines</td></tr>
		  		</table>
		  	</div>
		 </th>
	  </tr>
	</table>
  	<br clear="all"/>
 	<table cellspacing="0" cellpadding="5" border="1" >
 		<tr>
 			<td style="width: 20%" colspan="2"></td>
 			<td colspan="4" class="innertop2" style="text-align: center;color: white">Position in Salary Range</td>
 		</tr>
 		<tr class="innertop2">
 			<td style="width: 10%;text-align: center;color: white">Overall Performance Rating</td>
 			<td style="width: 10%;text-align: center;color: white">Aggregate Expected Ratings Distribution @ Co.level</td>
 			<td style="width: 10%;text-align: center;color: white">1 st Quartile</td>
 			<td style="width: 10%;text-align: center;color: white">2 nd Quartile</td>
 			<td style="width: 10%;text-align: center;color: white">3 rd Quartile</td>
 			<td style="width: 10%;text-align: center;color: white">4 th Quartile</td>
 		</tr>
 		
 		<c:forEach  items="${salaryPositionRangeDetails}" var="item" varStatus="i">
 			<tr>
	 			<td style="width: 10%;text-align: center;">${item.overallPerformanceRating}</td>
	 			<td style="width: 10%;text-align: center;">${item.aggregateExpected}</td>
	 			<td style="width: 10%;text-align: center;">${item.firstQuartile}</td>
	 			<td style="width: 10%;text-align: center;">${item.secondQuartile}</td>
	 			<td style="width: 10%;text-align: center;">${item.thirdQuartile}</td>
	 			<td style="width: 10%;text-align: center;">${item.fourthQuartile}</td>
 			</tr>
 		</c:forEach>
 	
 	</table>
  </div>             
  </div>
  
<div style="display: none;">
		<div id="viewListPopUp"  style="padding:10px; background:#fff;">
			<br clear="all"/>
		  	<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right" class="data-table" >
			  <thead>
			  	<tr>
			  		<td class="head" colspan="3">
			  			View Approver List
			  		</td>
			  	</tr>
			  </thead>
			  <tbody id="tbodyId">
			  	<!-- <tr>
			  		<td colspan="10">
			  			<table id="empApproveTable"  class="easyui-datagrid" title="Approver List"  style="width:100%;height:200px;table-layout: fixed;"
								data-options="collapsible:true
												,method: 'post'
												,pagination:false
												,emptyMsg: 'No records found'
												,fitColumns:true
												,collapsed:false " >
			              <thead>
			                <tr>
			                  <th data-options="field:'empID'">Emp Id</th>
			                  <th data-options="field:'meritApprovalLevel'">Approval Level</th>
			               </tr>
			              </thead>
			             </table>
			  		</td>
			  	</tr> -->
			  </tbody>
			</table>
		</div>
		</div>
  </div>
</div> 
 <!-- <div id="tb" style="height:auto" align="right">
						<a href="javascript:void(0)" class="easyui-linkbutton" id="iconSaveId" data-options="iconCls:'icon-save',plain:true" onclick="updateEmployeeDetailsSelected()">Save   </a>
					</div> -->
<script type="text/javascript" src="view/js/app/meritAdministration.js"></script>
<script type="text/javascript" src="view/js/docknavigation.js"></script>
  
<!-- </div> -->
