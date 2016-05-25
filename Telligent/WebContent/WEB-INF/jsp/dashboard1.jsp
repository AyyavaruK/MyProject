	<title>Basic DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="view/css/jquery/demo.css">
	<script type="text/javascript" src="view/js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="view/js/jquery/jquery.easyui.min.js"></script>
	<h2>Basic DataGrid</h2>
	<p>The DataGrid is created from markup, no JavaScript code needed.</p>
	<div style="margin:20px 0;"></div>
	
		<table id="tt" class="easyui-datagrid" title="Basic DataGrid" style="width:700px;height:250px"
			data-options="rownumbers:true
							,singleSelect:true
							,collapsible:true
							,url:'test.htm'
							,method:'get'
							,pagination:true
							,pageSize:20">
			<thead>
				<tr>
					<th data-options="field:'employeeId',width:80">Employee ID</th>
					<th data-options="field:'password',width:100">Password</th>
					<th data-options="field:'role',width:80,align:'right'">Role</th>
					<th data-options="field:'userName',width:80,align:'right'">User Name</th>
				</tr>
			</thead>
		</table>
		<div id="content" class="easyui-panel" style="height: 200px"
			data-options="href:'test.htm?page=1&rows=10'"></div>
		<div class="easyui-pagination" style="border: 1px solid #ccc;"
			data-options="
		    total: 2000,
		    pageSize: 10,
		    onSelectPage: function(pageNumber, pageSize){
		    $('#content').panel('refresh', 'test.htm?page='+pageNumber+'&rows='+pageSize);
		    }">
		</div>
