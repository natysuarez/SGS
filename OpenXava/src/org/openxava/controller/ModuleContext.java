package org.openxava.controller;

import java.util.*;
import javax.servlet.http.*;
import org.apache.commons.logging.*;
import org.openxava.controller.meta.*;
import org.openxava.util.*;
import org.openxava.web.servlets.*;

/**
 * Context with life of session and private for every module.
 * 
 * @author Javier Paniza
 */

public class ModuleContext implements java.io.Serializable { 
	
	private static Log log = LogFactory.getLog(ModuleContext.class);
	final private static ThreadLocal<String> currentWindowId = new ThreadLocal<String>(); 
	
	static {
		MetaControllers.setContext(MetaControllers.WEB);		
	}
	
	private transient Map contexts = null; 
	private transient Map globalContext = null; 
	private String lastUsedWindowId; 
	private String windowIdForNextTime = null; 

	/**
	 * Return a object associated to the specified module
	 * in 'application' and 'module' of request.
	 */
	public Object get(HttpServletRequest request, String objectName) throws XavaException {  
		String application = request.getParameter("application");
		if (Is.emptyString(application)) {
			throw new XavaException("application_and_module_required_in_request");
		}
		String module = request.getParameter("module");
		if (Is.emptyString(module)) {
			throw new XavaException("application_and_module_required_in_request");
		}		
		return get(application, module, objectName);		
	}
	
	/**
	 * Return a object asociate to the specified module
	 * in 'application' and 'module' of request.
	 */
	public Object get(HttpServletRequest request, String objectName, String className) throws XavaException { 
		String application = request.getParameter("application");
		if (Is.emptyString(application)) {
			throw new XavaException("application_and_module_required_in_request");
		}
		String module = request.getParameter("module");
		if (Is.emptyString(module)) {
			throw new XavaException("application_and_module_required_in_request");
		}		
		return get(application, module, objectName, className);		
	}
	
	
	public Object get(String application, String module, String objectName, String className) throws XavaException {
		Map context = getContext(application, module, objectName); 
		Object o = context.get(objectName);
		if (o == null) {
			o = createObjectFromClass(className);
			context.put(objectName, o);			
		}
		return o;
	}
	
	

	private Object createObjectFromClass(String className) throws XavaException {
		try {
			return Class.forName(className).newInstance();
		}
		catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new XavaException("create_error", className);
		}
	}

	/**
	 * If does not exist the it create one, as defined in controllers.xml. <p>
	 */	
	public Object get(String application, String module, String objectName) throws XavaException {
		Map context = getContext(application, module, objectName);
		Object o = context.get(objectName);
		if (o == null) {
			o = createObject(objectName);
			context.put(objectName, o);			
		}
		return o;
	}
	
	public boolean exists(String application, String module, String objectName) throws XavaException {
		Map context = getContext(application, module, objectName); 
		return context.containsKey(objectName);
	}
	
	public boolean exists(HttpServletRequest request, String objectName) throws XavaException {
		String application = request.getParameter("application");
		if (Is.emptyString(application)) {
			throw new XavaException("application_and_module_required_in_request");
		}
		String module = request.getParameter("module");
		if (Is.emptyString(module)) {
			throw new XavaException("application_and_module_required_in_request");
		}		
		return exists(application, module, objectName);		
	}	


	public void put(HttpServletRequest request, String objectName, Object value) throws XavaException {
		String application = request.getParameter("application");
		if (Is.emptyString(application)) {
			throw new XavaException("application_and_module_required_in_request");
		}
		String module = request.getParameter("module");
		if (Is.emptyString(module)) {
			throw new XavaException("application_and_module_required_in_request");
		}				
		Map context = getContext(application, module, objectName);
		context.put(objectName, value);
	}
		
	public void put(String application, String module, String objectName, Object value) throws XavaException {
		Map context = getContext(application, module, objectName);
		context.put(objectName, value);
	}

	public void remove(HttpServletRequest request, String objectName) throws XavaException {
		String application = request.getParameter("application");
		if (Is.emptyString(application)) {
			throw new XavaException("application_and_module_required_in_request");
		}
		String module = request.getParameter("module");
		if (Is.emptyString(module)) {
			throw new XavaException("application_and_module_required_in_request");
		}				
		Map context = getContext(application, module, objectName); 
		context.remove(objectName);
	}
		
	public void remove(String application, String module, String objectName) throws XavaException {
		Map context = getContext(application, module, objectName); 
		context.remove(objectName);
	}
	
	/**
	 * 
	 * @since 4.1.2
	 */
	public String getCurrentModule(HttpServletRequest request) {
		String module;
		String currentModule = request.getParameter("module");	
		do {
			module = currentModule;
			currentModule = (String) get(request.getParameter("application"), currentModule, "xava_currentModule");
		}
		while (!Is.empty(currentModule));
		return module;
	}
			
	private Object createObject(String objectName) throws XavaException{			
		return MetaControllers.getMetaObject(objectName).createObject();
	}

	private Map getContext(String application, String module, String objectName) throws XavaException {
		if (isGlobal(objectName)) {
			return getGlobalContext();
		}
		if (currentWindowId.get() == null) currentWindowId.set(lastUsedWindowId);
		else lastUsedWindowId = currentWindowId.get();
		String id = application + "/" + module + "/" + currentWindowId.get();
		Map context = (Map) getContexts().get(id);
		if (context == null) {
			context = new HashMap();			
			getContexts().put(id, context);
		}
		return context;
	}

	private boolean isGlobal(String objectName) throws XavaException {
		try {
			return MetaControllers.getMetaObject(objectName).isGlobal();
		}
		catch (ElementNotFoundException ex) { 
			return false;
		}
	}

	/**
	 * Used for application scope objects.
	 */
	private Map getGlobalContext() {
		if (globalContext == null) {
			globalContext = new HashMap();
		}
		return globalContext;
	}
	
	private Map getContexts() {
		if (contexts == null) {
			contexts = new HashMap();
		}
		return contexts;
	}

	
	/**
	 * All objects with this name in all the active modules of the user session.
	 */	
	public Collection getAll(String objectName) { 
		Collection allContexts = new ArrayList();
		if (contexts == null || contexts.isEmpty()) return allContexts;		
		
		Iterator it = contexts.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry context = (Map.Entry) it.next();
			Object object = ((Map)context.getValue()).get(objectName);
			if (object != null) allContexts.add(object);
		}
		return allContexts;
	}
	
	public String getWindowId(HttpServletRequest request) {
		// If we change this method we should try with Customer module: 
		//		New, change image, open Customer module in other browser tab, it should show the list, not the detail
		// That is the state of browser tabs is independent after we upload an image. 
		// This case cannot be tested with HtmlUnit, in some detail it does not behave as a real browser for this case.
		
		String alreadyInPageWindowId = (String) request.getAttribute("xava.new.window.id");
		if (alreadyInPageWindowId != null) {
			return alreadyInPageWindowId;
		}
		String windowId = Servlets.getCookie(request, "XAVA_WINDOW_ID"); 
		if (Is.emptyString(windowId)) {
			if (windowIdForNextTime != null) {				
				windowId = windowIdForNextTime;
			}
			else {
				windowId = Long.toHexString(System.currentTimeMillis()); // Better than UUID because is shorter and impossible to duplicate because a user cannot open two tabs at once in the same browser
			}
			request.setAttribute("xava.new.window.id", windowId);	
		}
		windowIdForNextTime = null; 
		return windowId;
	}
	
	public static void setCurrentWindowId(HttpServletRequest request) {
		setCurrentWindowId(request.getHeader("xava_window_id")); 
	}
	
	public static void setCurrentWindowId(String id) {
		currentWindowId.set("null".equals(id)?null:id); 
	}
	
	public static void cleanCurrentWindowId() {
		currentWindowId.set(null); 
	}

	
	public void dontGenerateNewWindowIdNextTime() {
		windowIdForNextTime = lastUsedWindowId; 
	}

}
