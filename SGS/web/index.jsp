<%@page import="com.openxava.phone.web.Browsers"%>
<%@page import="org.openxava.util.Users"%>

<jsp:useBean id="modules" class="com.openxava.naviox.Modules" scope="session"/>

<%
Users.setCurrent(request); 
String module = Users.getCurrent() == null?"SignIn":modules.getCurrent(request);
String url = Browsers.isMobile(request)?"phone":"m/" + module;
%>

<script type="text/javascript">
window.location="<%=url%>";
</script>
