<%@ include file="taglib.jsp" %>
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
<form:form commandName="employee" id="employeeForm" modelAttribute="employee" enctype="multipart/form-data">
<form:hidden path="successMessage"/>
<form:hidden path="errorMessage"/>
<form:hidden path="operation"/>
<input type="hidden" id="updateble">
<input type="hidden" id="screenName" value="personal">
<div id="contentArea">
  <div class="loader"></div>
  <div class="contentArea">
  <%@include file="employeeLeft.jsp" %>
  <div class="innerright" style="min-height: 480px" id="flow"> <a href="javascript:toggle()" title="Hide Nav 13" id="flowtab"></a>
      <div style="margin:0 0 100px 0px;cursor:auto;" id="tab">
        <div class="wrap" style="margin:0 0 100px 0px;">
		  <table  width="100%" border="0" cellspacing="0" cellpadding="0">
		  	<tr>
		  		<td>
		  			<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="E3E3E3" align="right">
			            <tr>
			              <th class="head1" height="25" align="left"> 
			              	<div class="innerpage-breadcrum"> 
			              		<span style="padding-top: 10px;">
			              			<a href="dashboard.htm">Dashboard</a> &nbsp;&gt;&nbsp; <a href="javascript:void(0);">Employee</a> &nbsp;&gt;&nbsp; <a href="javascript:void(0);" class="select">Employee Personal Details</a>
			              		</span> 
			              		<span style="float: right">
			              			<a href="#" onclick="javascript:reset()" class="easyui-linkbutton" iconCls="icon-add">Create Employee</a>
			   						<a href="#" onclick="javascript:save()" class="easyui-linkbutton" iconCls="icon-save">Save</a>			
			              		</span> 
			              	</div>
			              </th>
			            </tr>
			          </table>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>
		  			<%@include file="employeeButtons.jsp" %>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>
		  			<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="data-table-new">
              <tr>
                <th style="text-align: left;padding-left: 10px" class="head"> Employee Personal Information </th>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr id="validationErrorMessageId" style="display: none">
              	<td>
              		<div class=validationErrorMessage>Entry cannot be saved because Required data is missing</div>
              	</td>
              </tr>
              <tr>
                <td>
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="data-table-new">
	                    <tr>
	                      <td width="8%" height="34" nowrap="nowrap"><label>Name</label>
	                        <span style="color: red">*</span></td>
	                      <td width="14%"><form:input path="lastName" cssClass="required" maxlength="25" placeHolder="Last Name"/></td>
	                      <td width="14%"><form:input path="firstName" cssClass="required" maxlength="25" placeHolder="First Name"/></td>
	                      <td width="14%"><form:input path="middleName" maxlength="25" placeHolder="Middle Name"/></td>
	                      <td width="8%" nowrap="nowrap" style="width: 10%"><label>Effective Date</label>
	                        <span style="color: red">*</span></td>
	                      <td width="10%" ><form:hidden path="effectiveDate"/>
	                        <input id="effectiveDateBox" class="required easyui-datebox" required="true" width="150px"/>
	                      </td>
	                      <td width="14%" rowspan="4" align="center" valign="top" >
	                      	<img id="image" src="view/images/no_image.jpg" height="120" width="100"/><br />
	                        <input id="picture" type="file" name="picture" onchange="PreviewImage();" style="padding-left: 10px;" /></td>
	                    </tr>
	                    <tr style="height: 1px;">
	                      <td colspan="8" class="head">&nbsp;</td>
	                    </tr>
	                    <tr>
	                      <%-- <td width="8%" height="36" nowrap="nowrap"><label>Employee No</label>
	                        <span style="color: red">*</span></td>
	                      <td width="14%"><form:input path="employeeNo" cssClass="required"  maxlength="14"/></td> --%>
	                      <td width="8%" nowrap="nowrap"><label>Employee Id</label>
	                        <span style="color: red">*</span></td>
	                      <td width="14%"><form:input path="employeeId" cssClass="required" maxlength="14"/></td>
	                      <td width="8%" nowrap="nowrap"><label>Social Sec #</label></td>
	                      <td width="14%"><form:input path="socialSecNo" onKeyPress="return numbersonly(event, true,this.value)" maxlength="14"/></td>
	                      <td width="8%" nowrap="nowrap" style="width: 10%"><label>Badge No</label></td>
	                      <td width="14%" >
	                        <form:input path="badgeNo" maxlength="6"/>
	                      </td>	
	                    </tr>
	                    <tr>
	                      <td width="8%" height="30" nowrap="nowrap"><label>Date Of Birth</label>
	                        <span style="color: red">*</span></td>
	                      <td width="14%"><form:hidden path="dateOfBirth"/>
	                        <input id="dateOfBirthBox" class="required easyui-datebox" onblur="alert()" required="true" onkeypress="return false;" data-options="onSelect:dateOfBirthSelect" width="150px"/></td>
	                      <td width="8%" nowrap="nowrap"><label>Is Minor</label></td>
	                      <td width="14%" align="left"><form:checkbox path="minor" id="minor"  onclick="return false" title="Depends on Data of Birth Selection"/></td>
	                      <td width="8%">&nbsp;</td>
	                      <td width="14%">&nbsp;</td>
	                      <td width="8%" >&nbsp;</td>
	                      <td width="14%" >&nbsp;</td>
	                    </tr>
	                  </table></td>
	              </tr>
	              <tr>
	                <td colspan="8" class="head">&nbsp;</td>
	              </tr>
	              <tr>
	                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="data-table-new-1">
	                    <tr>
	                      <td width="50%" rowspan="2" align="left" valign="top"><fieldset class="fieldset">
	                        <legend align="center">Personal</legend>
	                        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="data-table-new-1">
	                          <tr>
	                            <td width="22%" align="left" valign="middle"><label>Home Phone</label></td>
	                            <td width="78%" align="left" valign="middle"><form:input path="homePhone" maxlength="15"  cssStyle="width:200px"/></td>
	                          </tr>
	                          <tr>
	                            <td align="left" valign="middle"><label>Cell Phone</label></td>
	                            <td align="left" valign="middle"><form:input path="mobilePhone" maxlength="15" cssStyle="width:200px"/></td>
	                          </tr>
	                          <tr>
	                            <td align="left" valign="middle"><label>Personal Email</label></td>
	                            <td align="left" valign="middle"><form:input path="personalEmail" maxlength="65" cssStyle="width:300px"/></td>
	                          </tr>
	                          <tr>
	                            <td align="left" valign="middle"><label>Address 1</label>
	                              <span style="color: red">*</span></td>
	                            <td align="left" valign="middle"><form:input path="addressLine1" cssClass="required" maxlength="65" cssStyle="width:300px"/></td>
	                          </tr>
	                          <tr>
	                            <td align="left" valign="middle"><label>Address 2</label></td>
	                            <td align="left" valign="middle"><form:input path="addressLine2" maxlength="65" cssStyle="width:300px"/></td>
	                          </tr>
	                          <tr>
	                            <td align="left" valign="middle"><label>City</label>
	                              <span style="color: red">*</span></td>
	                            <td align="left" valign="middle">
	                            	<input class="easyui-combobox required" name="city" id="cityId" style="width:300px" data-options="
										url:'searchCity.htm',
										method:'post',
										mode: 'remote',
										valueField: 'city',
										textField: 'city',
										selectOnNavigation:false
										">
	                            </td>
	                          </tr> 
	                          <tr>
	                            <td align="left" valign="middle"><label>State</label>
	                              <span style="color: red">*</span></td>
	                            <td align="left" valign="middle">
	                            	<input class="easyui-combobox required" name="state" id="stateId" style="width:300px" data-options="
										url: 'searchState.htm',
										method:'post',
										mode: 'remote',
										valueField: 'stateName',
										textField: 'stateName',
										selectOnNavigation:false,
										onBeforeLoad : onBeforeLoadState
										">
	                            </td>
	                          </tr>
	                          <%-- <tr>
	                            <td align="left" valign="middle"><label>State</label>
	                              <span style="color: red">*</span></td>
	                            <td align="left" valign="middle"><form:select path="state" id="state" cssClass="required"  onchange="getCityList(this.value,'')">
	                                <form:option value="">Select</form:option>
	                                <form:options id="stateList" items="${stateList}" itemValue="id" itemLabel="stateName"/>
	                              </form:select></td>
	                          </tr>
	                          <tr>
	                            <td align="left" valign="middle"><label>City</label>
	                              <span style="color: red">*</span></td>
	                            <td align="left" valign="middle"><form:select path="city" cssClass="required">
	                                <form:option value="">Select</form:option>
	                                <form:options items="${cityList}" itemValue="id" itemLabel="city"/>
	                              </form:select></td>
	                          </tr> --%>
	                          <tr>
	                            <td align="left" valign="middle"><label>ZIP Code</label>
	                              <span style="color: red">*</span></td>
	                            <td align="left" valign="middle"><form:input path="zipcode" cssClass="required" onKeyPress="return numbersonly(event, true,this.value)" maxlength="15"/></td>
	                          </tr>
	                        </table>
	                        </fieldset></td>
	                      <td width="50%" height="124" align="left" valign="top"><fieldset class="fieldset">
	                        <legend align="center">Work</legend>
	                        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="data-table-new-1">
	                          <tr>
	                            <td width="22%" align="left" valign="middle"><label>Phone</label></td>
	                            <td width="48%" align="left" valign="middle"><form:input path="workPhone" maxlength="15"/></td>
	                            <td width="10%" width="78%"  valign="middle"><label>Ext</label></td>
	                            <td width="30%" align="left" valign="middle"><form:input path="workExt"/></td>
	                          </tr>
	                          <tr>
	                            <td align="left" valign="middle"><label>Email</label></td>
	                            <td align="left" valign="middle" colspan="3"><form:input path="workEmail" maxlength="65" cssStyle="width:380px"/></td>
	                          </tr>
	                          <tr>
	                            <td align="left" valign="middle" nowrap="nowrap"><label>Cell Phone</label></td>
	                            <td align="left" valign="middle"><form:input path="workMobilePhone"  maxlength="15"/></td>
	                          </tr>
	                        </table>
	                        </fieldset></td>
	                    </tr>
	                    <tr>
	                      <td align="left" valign="top"><fieldset class="fieldset">
	                        <legend align="center">Emergency Contact</legend>
	                        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="data-table-new-1">
	                          <tr>
	                            <td width="25%" align="left" valign="middle"><label>Last Name</label> </td>
	                            <td width="25%" align="left" valign="middle"><form:input path="emergencyLastName" maxlength="25"/></td>
	                            <td width="25%" align="left" valign="middle"><label>First Name</label> </td>
	                            <td width="25%" align="left" valign="middle"><form:input path="emergencyFirstName" maxlength="25"/></td>
	                          </tr>
	                          <tr>
	                            <td align="left" valign="middle"><label>Relationship</label></td>
	                            <td colspan="3" align="left" valign="middle">
	                            	<form:select path="emergencyRelationShip">
					    				<form:option value="">Select RelationShip</form:option>
					    				<form:options items="${relationshipList}" itemValue="id" itemLabel="value"/>
					    			</form:select>
	                            </td>
	                          </tr>
	                          <tr>
	                            <td align="left" valign="middle"><label>Home Phone</label> </td>
	                            <td align="left" valign="middle"><form:input path="emergencyHomePhone" maxlength="15"/></td>
	                            <td align="left" valign="middle"><label>Cell Phone</label> </td>
	                            <td align="left" valign="middle"><form:input path="emergencyMobilePhone" maxlength="15"/></td>
	                          </tr>
	                          <tr>
	                            <td align="left" valign="middle"><label>Email</label></td>
	                            <td colspan="3" align="left" valign="middle"><form:input path="emergencyEmail" cssStyle="width:300px"/></td>
	                          </tr>
	                        </table>
	                        </fieldset></td>
	                    </tr>
	                  </table></td>
	              </tr>
	              <tr>
	                <td>&nbsp;</td>
	              </tr>
	            </table>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>
		  			<table id="employeePersonalHistoryTable"  class="easyui-datagrid" title="History Table"  style="width:100%;height:200px;table-layout: fixed;"
								data-options="collapsible:true
												,method: 'post'
												,pagination:false
												,emptyMsg: 'No records found'
												,collapsed:false " >
		              <thead data-options="frozen:true">
		                <tr>
		                  <th data-options="field:'effectiveDate',width:100"  formatter="formatDetail">Eff Date</th>
		                  <th data-options="field:'employeeId',width:100">Emp Id</th>
		                  <th data-options="field:'seqNo',width:100" hidden="true">seq no</th>
		                </tr>
		              </thead>
		              <thead>
		                <tr>
		                  <th data-options="field:'badgeNo',width:100">Badge</th>
		                  <th data-options="field:'socialSecNo',width:100">Social Sec No</th>
		                  <th data-options="field:'firstName',width:100">First Name</th>
		                  <th data-options="field:'middleName',width:100">Middle Name</th>
		                  <th data-options="field:'lastName',width:100">Last Name</th>
		                  <th data-options="field:'homePhone',width:100">Home Phone</th>
		                  <th data-options="field:'mobilePhone',width:100">Mobile Phone</th>
		                  <th data-options="field:'addressLine1',width:100">Address 1</th>
		                  <th data-options="field:'addressLine2',width:100">Address 2</th>
		                  <th data-options="field:'city',width:100">City</th>
		                  <th data-options="field:'state',width:100">State</th>
		                  <th data-options="field:'zipcode',width:100">ZIP</th>
		                  <th data-options="field:'personalEmail',width:100">Perosonal Email</th>
		                  <th data-options="field:'dateOfBirth',width:100">DOB</th>
		                  <th data-options="field:'minor',width:100">Minor</th>
		                  <th data-options="field:'workPhone',width:100">Work Phone</th>
		                  <th data-options="field:'workMobilePhone',width:100">Work Mobile Phone</th>
		                  <th data-options="field:'workEmail',width:100">Work Email</th>
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
<script type="text/javascript" src="view/js/app/employee.js"></script>
