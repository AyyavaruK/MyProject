
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Basic Tree - jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="view/css/jquery/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="view/css/jquery/demo.css">
	<script type="text/javascript" src="view/js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="view/js/jquery/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>Basic Tree</h2>
	<p>Click the arrow on the left to expand or collapse nodes.</p>
	<div style="margin: 20px 0;"></div>
	<div class="easyui-panel" style="padding: 5px">
		<ul class="easyui-tree">
				<li>
					<span>My Teams</span>
					<ul>
						<li data-options="state:'closed'"><span>Team 1</span>
							<ul>
								<li><span>Emp 1</span></li>
								<li><span>Emp 2</span></li>
								<li><span>Emp 3</span></li>
							</ul>
						</li>
					</ul>
					
					<ul>
						<li data-options="state:'closed'"><span>Team 2</span>
							<ul>
								<li><span>Emp 1</span></li>
								<li><span>Emp 2</span></li>
								<li><span>Emp 3</span></li>
							</ul>
						</li>
					</ul>
					
				</li>
		</ul>
	</div>

</body>
</html>