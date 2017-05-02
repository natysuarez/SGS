<%@page import="org.openxava.controller.meta.MetaControllerElement"%>
<%@ include file="imports.jsp"%>

<%@ page import="org.openxava.controller.meta.MetaAction" %>
<%@ page import="org.openxava.util.XavaPreferences"%>
<%@ page import="org.openxava.util.Is"%>
<%@ page import="org.openxava.controller.meta.MetaSubcontroller"%>
<%@ page import="java.util.Collection"%>
<%@ page import="org.openxava.web.Ids"%>

<jsp:useBean id="context" class="org.openxava.controller.ModuleContext" scope="session"/>
<jsp:useBean id="style" class="org.openxava.web.style.Style" scope="request"/>

<%
org.openxava.controller.ModuleManager manager = (org.openxava.controller.ModuleManager) context.get(request, "manager", "org.openxava.controller.ModuleManager");
manager.setSession(session);
boolean onBottom = false;
String mode = request.getParameter("xava_mode");
if (mode == null) mode = manager.isSplitMode()?"detail":manager.getModeName();
boolean headerButtonBar = !manager.isSplitMode() || mode.equals("list");
boolean listFormats = !manager.isSplitMode() && mode.equals("list"); 

if (manager.isButtonBarVisible()) {
%>
	<div class="<%=style.getButtonBar()%>">
	<div id="<xava:id name='controllerElement'/>">
	<span style="float: left">
	<%
	Collection<MetaControllerElement> elements = manager.getMetaControllerElements(); 
	for (MetaControllerElement element : elements){
		if (!element.appliesToMode(mode)) continue;
		if (element instanceof MetaAction){
			MetaAction action = (MetaAction) element;
			if (action.isHidden()) continue;
			if (action.hasImage() || action.hasIcon()) {	
			%>
			<jsp:include page="barButton.jsp">
				<jsp:param name="action" value="<%=action.getQualifiedName()%>"/>
			</jsp:include>		
			<%
			}
		}
		else if (element instanceof MetaSubcontroller){
			MetaSubcontroller subcontroller = (MetaSubcontroller) element;
			if (subcontroller.hasActionsInThisMode(mode)){
			%>
			<jsp:include page="subButton.jsp">
				<jsp:param name="controller" value="<%=subcontroller.getControllerName()%>"/>
				<jsp:param name="image" value="<%=subcontroller.getImage()%>"/>
				<jsp:param name="icon" value="<%=subcontroller.getIcon()%>"/>
			</jsp:include>
			<%
			}
		}
	}
	%>
	</span>
	</div>

	<div id="<xava:id name='modes'/>">
	<span style="float: right">
	<span style="float: left;" class="<%=style.getListFormats()%>">
	<%
	if (listFormats) { 	
		String tabObject = request.getParameter("tabObject");
		tabObject = (tabObject == null || tabObject.equals(""))?"xava_tab":tabObject;
		org.openxava.tab.Tab tab = (org.openxava.tab.Tab) context.get(request, tabObject);
		Collection<String> editors = org.openxava.web.WebEditors.getEditors(tab.getMetaTab());
		for (String editor: editors) {
			String icon = editor.equals("Charts")?"chart-line":"table-large";
			String selected = editor.equals(tab.getEditor())?style.getSelectedListFormat():"";
			if (Is.emptyString(editor)) editor = "__NONAME__"; 
	%>
	<xava:link action="ListFormat.select" argv='<%="editor=" + editor%>' cssClass="<%=selected%>">	
		<i class="mdi mdi-<%=icon%>" onclick="openxava.onSelectListFormat(event)"></i>
	</xava:link>			
	<%				
		}
	}	
	%>
	</span>
		
	<%
	java.util.Stack previousViews = (java.util.Stack) context.get(request, "xava_previousViews"); 
	if (headerButtonBar && previousViews.isEmpty()) { 
		String positionClass = null;		
		java.util.Collection actions = manager.getMetaActionsMode();
		java.util.Iterator itActions = actions.iterator();
		if (style.isOnlyOneButtonForModeIfTwoModes() && actions.size() == 2) {
			while (itActions.hasNext()) {
				MetaAction action = (MetaAction) itActions.next();
				String modeNameAction = action.getName().startsWith("detail")?"detail":action.getName();
				if (!modeNameAction.equals(manager.getModeName())) {
				%>
		<jsp:include page="barButton.jsp">
			<jsp:param name="action" value="<%=action.getQualifiedName()%>"/>
		</jsp:include>
				&nbsp;						
				<%					
					break;
				}
			}
		}
		else while (itActions.hasNext()) {
			MetaAction action = (MetaAction) itActions.next();
			if (positionClass == null) {
				positionClass = style.getFirst();			
			}
			else if (!itActions.hasNext()) positionClass = style.getLast();
			else positionClass = "";						
			%>			
			<span class="<%=positionClass%>">			
			<%
			String modeNameAction = action.getName().startsWith("detail")?"detail":action.getName();
			if (modeNameAction.equals(manager.getModeName())) {			
			%>
			<span class="<%=style.getActive()%>">
				<a href="javascript:void(0)" class="<%=style.getButtonBarModeButton()%>">
					<div class="<%=style.getButtonBarActiveModeButtonContent()%>">
					&nbsp;&nbsp;
					<%=action.getLabel(request)%>
					&nbsp;&nbsp;
					</div>
				</a>
			</span>			
			<%
			}
			else {	
			%>  							
			<xava:link action="<%=action.getQualifiedName()%>" cssClass="<%=style.getButtonBarModeButton()%>">
				&nbsp;&nbsp;							 			
				<%=action.getLabel(request)%>
				&nbsp;&nbsp;					
			</xava:link>			
			<%
			}
			%>			 
			</span>			
			<%
		}
	}	

	String language = request.getLocale().getLanguage();
	String href = XavaPreferences.getInstance().getHelpPrefix(); 
	String suffix = XavaPreferences.getInstance().getHelpSuffix(); 
	String target = XavaPreferences.getInstance().isHelpInNewWindow() ? "_blank" : "";
	if (href.startsWith("http:") || href.startsWith("https:")) {
		if (href.endsWith("_")) href = href + language;
		if (!Is.emptyString(suffix)) href = href + suffix;
	}
	else {
		href = 
			"/" + manager.getApplicationName() + "/" + 
			href +
			manager.getModuleName() +
			"_" + language + 
			suffix;
	} 	
	if (XavaPreferences.getInstance().isHelpAvailable() && style.isHelpAvailable()) { 	
		String helpImage = null;
		if (style.getHelpImage() != null) helpImage = !style.getHelpImage().startsWith("/")?request.getContextPath() + "/" + style.getHelpImage():style.getHelpImage();
	%>
		<span class="<%=style.getHelp()%>">  
			<a href="<%=href%>" target="<%=target%>">
				<% if (helpImage == null) { %>
				<i class="mdi mdi-help-circle"/></i>
				<% } else { %>
				<a href="<%=href%>" target="<%=target%>"><img src="<%=helpImage%>"/></a>
				<% } %>
			</a>
		</span>
	<%
	}
	%>
	&nbsp;
	</span>		
	</div>	<!-- modes -->
	</div>
	
<% } // end isButtonBarVisible %>