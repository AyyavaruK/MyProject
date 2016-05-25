<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<%@ include file="taglib.jsp" %>
<title>User Dashboard</title>
<link href="view/css/style.css" rel="stylesheet" type="text/css" />
<form id="formId">
	<div class="contentArea1">
	<div class="boxesArea">
	 <security:authorize ifAnyGranted="ShowMeritAdmin">
	<a href="meritAdministration.htm"><div class="boxes">Merit Administration</div></a>
	</security:authorize>
	<security:authorize ifAnyGranted="ShowPerformanceManager">
	<a href="javascript:void(0);"><div class="boxes">Performance Manager</div></a>
	</security:authorize>
	<security:authorize ifAnyGranted="ShowBonusManager">
	<a href="javascript:void(0);"><div class="boxes">Bonus/Payout Administration</div></a>
	</security:authorize>
	<security:authorize ifAnyGranted="ShowSuccession">
	<a href="javascript:void(0);"><div class="boxes">Succession</div></a>
	</security:authorize>
	<%-- <security:authorize ifAnyGranted="ShowTeams">
	<a href="showTeams.htm"><div class="boxes">Show Teams</div></a>
	</security:authorize> --%>
	<security:authorize ifAnyGranted="ShowEmployee">
	<a href="employee.htm"><div class="boxes">Employee</div></a>
	</security:authorize>
	<security:authorize ifAnyGranted="ShowReferenceTables">
	<a href="referenceTables.htm"><div class="boxes">Reference Tables</div></a>
	</security:authorize>
	<security:authorize ifAnyGranted="AppUser">
	<a href="#" onclick="javascript:submit('appUser.htm')"><div class="boxes">App User</div></a>
	</security:authorize>
	<security:authorize ifAnyGranted="ShowTeams">
	<a href="teamUI.htm"><div class="boxes">Team Definition</div></a>
	</security:authorize>
	<!-- <a href="#" onclick="javascript:submit('securityGroup.htm')"><div class="boxes">Security Group</div></a> -->
	</div>
	</div>
</form>
<script>
function submit(url){
	document.forms[0].action=url;
	document.forms[0].method = "post";
	document.forms[0].submit();
}
</script>

