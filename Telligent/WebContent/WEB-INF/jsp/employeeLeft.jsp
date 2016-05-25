<script type="text/javascript">
function searchLastNameSelect(){
	alert()

}
</script>

<div class="innerleft" style="min-height: 480px" id="col1">
    	<table class="data-table-new" cellspacing="1" cellpadding="5" id="employeeSearchTable">
    		<tr>
		        <th colspan="2" class="head" height="25" align="left">Search</th>
		    </tr>
		    <tr>
		    	<td><label>Last Name</label></td>
		    	<td>
		    		<input class="easyui-combobox" id="lastNameInputId" style="width:200px" data-options="
						url:'searchLastName.htm',
						method:'post',
						mode: 'remote',
						valueField: 'id',
						textField: 'value',
						selectOnNavigation:false,
						onSelect:searchLastNameSelect
						">
		    	</td>
		    </tr>
		    <tr>
		    	<td><label>First Name</label></td>
		    	<td>
		    		<input class="easyui-combobox" id="firstNameInputId" style="width:200px" data-options="
						url:'searchFirstName.htm',
						method:'post',
						mode: 'remote',
						valueField: 'id',
						textField: 'value',
						selectOnNavigation:false,
						onSelect:searchFirstNameSelect
						">
		    	</td>
		    </tr>
		    <tr>
		    	<td><label>Employee Id</label></td>
		    	<td>
		    		<input class="easyui-combobox" id="employeeInpuId" style="width:200px" data-options="
						url:'searchEmpId.htm',
						method:'post',
						mode: 'remote',
						valueField: 'id',
						textField: 'value',
						selectOnNavigation:false,
						onSelect:searchEmpIdSelect
						">
		    	</td>
		    </tr>
		    <tr>
		    	<td><label>Team Name</label></td>
		    	<td>
		    		<input class="easyui-combobox" id="teamInputId" style="width:200px" data-options="
						url:'searchTeamEmployees.htm',
						method:'post',
						mode: 'remote',
						valueField: 'id',
						textField: 'value',
						selectOnNavigation:false,
						onSelect:searchTeamEmployeesSelect
						">
		    	</td>
		    </tr>
		<tr>
			<td align="left"><form:checkbox path="includeInactive" id="includeInactive" value="" />
			<label>Include InActive</label></td>
			<td><form:checkbox path="excludeActivate" id="excludeActivate" value="" />
			<label>Exclude Active</label></td>
		</tr>
	</table>  
	</div>