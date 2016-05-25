<!DOCTYPE HTML>
<html>
<head>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<link href="view/css/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<script type="text/javascript">
</script>
<script type="text/javascript" src="view/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="view/js/jquery/jquery.validate.js"></script>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" align="center" width="100%">
	<tr>
		<td id="layoutheadertd" height="30" colspan="2"><tiles:insertAttribute name="header" />
		</td>
	</tr>
	<tr>
		<td id="layoutbodytd"><tiles:insertAttribute name="body" /></td>
	</tr>
	<tr>
		<td id="layoutfootertd"  height="30" colspan="2"><tiles:insertAttribute name="footer" />
		</td>
	</tr>
</table>
<script type="text/javascript">
$(document).ready(function(){
	var val = $("#layoutbodytd").height()+20;
	$("#refTableTbodyId").height(val-40);
	$(".innerleftmerit").height(val);
	$(".innerrightmerit").height(val);
	$(".innerleft").height(val);
	$(".innerright").height(val);
});
</script>
</body>
</html>
