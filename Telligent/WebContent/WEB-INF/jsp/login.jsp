<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="taglib.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Talent Manager</title>
<link href="view/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="view/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="view/js/jquery/jquery.validate.js"></script>
</head>
<body style="background:#1A6398;">
<form id="loginForm" method="post" action="<c:url value='/j_spring_security_check'/>" >
<div class="mainContainer">
  <div class="innerContainer">
    <div class="logoSection">
      <h1>Talent Manager</h1>
    </div>
    <div class="loginBox">
      <div class="loginstrip">Login</div>
      <br clear="all"/>
      <table width="85%" border="0" cellspacing="0" cellpadding="0" align="center">
      	<c:if test="${sessionScope[\"SPRING_SECURITY_LAST_EXCEPTION\"].message eq 'User is disabled'}">
      			<div class="loginerror" style="text-align: center;">Account is Suspended. Please contact Admin.</div>
      	</c:if>
      	<c:if test="${sessionScope[\"SPRING_SECURITY_LAST_EXCEPTION\"].message eq 'Bad credentials'}">
      			<div class="loginerror" style="text-align: center;">Invalid Credentials</div>
      	</c:if>
      	<c:if test="${sessionScope[\"SPRING_SECURITY_LAST_EXCEPTION\"].message eq 'UserDetailsService returned null, which is an interface contract violation'}">
      		<div class="loginerror" style="text-align: center;">Invalid Credentials</div>
      	</c:if>
      	<%-- <c:if test="${!empty message}"><div class="loginerror" style="text-align: center;">${message}</div></c:if> --%>
        <tr>
          <td align="left" valign="middle" colspan="3">&nbsp;</td>
        </tr>
        
        <tr>
          <th width="19%" height="40" align="left" valign="middle">Username</th>
          <td width="4%" align="center" valign="middle">:</td>
          <td width="77%" align="left" valign="middle"> <input type="text" class="textfield required" name="j_username" id="userName"/></td>
        </tr>
        <tr>
          <th height="40" align="left" valign="middle">Password</th>
          <td align="center" valign="middle">:</td>
          <td align="left" valign="middle"> <input type="password" class="textfield required" name="j_password" id="password"/></td>
        </tr>
        <tr>
          <td align="left" valign="middle" colspan="3" height="5"></td>
        </tr>
        <tr>
          <td align="left" valign="middle">&nbsp;</td>
          <td align="center" valign="middle">&nbsp;</td>
          <td align="left" valign="middle">
          	<input type="submit" value="Submit" class="loginButton"/>
          		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="forgotPassword.htm"><b>Forgot Password ?</a>
          	</td>
        </tr>
      </table>
    </div>
  </div>
  <div class="copyRight">Copyright &copy; 2014 Telligent Solutions. All Rights Reserved.</div>
</div>
</form>
<script type="text/javascript">
	 $(document).ready(function(){
		    $("#loginForm").validate({
				rules: {
					j_username:{
						required:true
					},
					j_password:{
						required:true
					}
				},
		        messages: {
					j_username:{
						required:"Please enter username."
					},
					j_password:{
						required:"Please enter password."
					}
				}
			});
		  });
</script>
</body>
</html>
