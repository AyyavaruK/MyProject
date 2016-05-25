<%@ include file="taglib.jsp"%>
<form:form id="changePassword" commandName="changePassword">
<div class="contentArea1">
<div class="boxesArea">
<table width="100%" border="0" cellspacing="0" cellpadding="10" class="maintable">
	<tr>
		<td class="head">Change Password</td>
	</tr>
	<tr>
		<td>
			<input type="password" id="password" style="display: none" value="<security:authentication property="principal.password"/>"/>
			<c:choose>
				<c:when test="${!empty message && message =='Password Changed successfully'}">
					<div class="message">${message} &nbsp;.Please click <a href="<c:url value="j_spring_security_logout"/>">here</a> to relogin</div>
				</c:when>
				<c:otherwise>
					<table width="100%" border="0" cellspacing="0" cellpadding="10" class="data-table">
						<tr>
							<td>Old Password</td>
							<td><input type="password" id="oldPassword" name="oldPassword" class="required"/></td>
						</tr>
						<tr>
							<td>New Password</td>
							<td><form:password path="newPassword" id="newPassword" cssClass="required"/></td>
						</tr>
						<tr>
							<td>Confirm New Password</td>
							<td><input type="password" id="confirmPassword" name="confirmPassword" class="required"/></td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="button" onclick="javascript:changePassword()" value="Submit"/>
								&nbsp;
								<input type="button" onclick="document.forms[0].action='dashboard.htm';document.forms[0].method = 'get';document.forms[0].submit();" value="Cancel"/>
							</td>
						</tr>
					</table>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>
	
	
</div>
</div>
	<script type="text/javascript">
		function changePassword(){
			if($("#changePassword").valid()){
				if($("#password").val() != $("#oldPassword").val()){
					alert("Current and Old password does not match");
				}else if($("#newPassword").val() != $("#confirmPassword").val()){
					alert("New and confirm password does not match");
				}else if($("#password").val() == $("#newPassword").val()){
					alert("Current and New password should not same");
				}else{
					document.forms[0].action="changePassword.htm";
					document.forms[0].method = "post";
					document.forms[0].submit();
				}
			}
		}
	</script>
</form:form>