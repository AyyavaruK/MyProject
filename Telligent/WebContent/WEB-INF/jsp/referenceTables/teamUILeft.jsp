<div class="innerleft" style="min-height: 480px" id="col1">
    	<table class="data-table-new" cellspacing="1" cellpadding="5" id="employeeSearchTable">
    		<tr>
		        <th colspan="2" class="head" height="25" align="left">Search</th>
		    </tr>
		    <tr>
		    	<td><label>Team Name</label></td>
		    	<td>
		    		<input class="easyui-combobox" id="lastNameInputId" style="width:200px" data-options="
						url:'searchTeamName.htm',
						method:'post',
						mode: 'remote',
						valueField: 'teamId',
						textField: 'teamName',
						selectOnNavigation:false,
						onSelect:searchTeamName
						">
		    	</td>
		    </tr>
		    <tr>
		    	<td><label>Team Parent</label></td>
		    	<td>
		    		<input class="easyui-combobox" id="firstNameInputId" style="width:200px" data-options="
						url:'searchTeamParent.htm',
						method:'post',
						mode: 'remote',
						valueField: 'teamId',
						textField: 'teamParent',
						selectOnNavigation:false,
						onSelect:searchTeamParent
						">
		    	</td>
		    </tr>
    	</table>  
	</div>
