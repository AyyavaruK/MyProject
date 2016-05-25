<%@ include file="taglib.jsp"%>
<div class="header">
<div class="innertop">&nbsp;</div>
<div class="innertop1"><h1>Talent Manager</h1>
	<div class="logout-section">
		<input type="hidden"   id="pictureBase64" value="<security:authentication property='principal.pictureBase64'/>">
		 <div>
		 	<ul class="drop">
				  <li><img id="headerImage" alt="" border="0" width="40"  height="43" />
				    <ul>
				      <li><a href="changePassword.htm" style="display: block;color: #fff;text-decoration: none;">Change Password</a></li>
				      <li><a href="<c:url value="j_spring_security_logout"/>" style="display: block;color: #fff;text-decoration: none;">Logout</a></li>
				    </ul>
				  </li>
				</ul>
		 </div>
	</div>
	<span style="vertical-align: text-bottom;" class="welcome">Welcome <security:authentication property="principal.userName"/></span>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		var val = $("#pictureBase64").val();
		if(val !=''){
			$("#headerImage").attr("src","data:image/bmp;base64,"+val);
		}else{
			$("#headerImage").attr("src","view/images/profile-pic.png");
		}
	});
</script>