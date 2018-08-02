<%@taglib 	uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib 	uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib   uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@taglib 	uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@taglib 	uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@taglib 	uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@taglib 	uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>

<%@page 	import="javax.portlet.PortletURL" %>
<%@page 	import="javax.portlet.ActionRequest" %>
<%@page 	import="javax.portlet.PortletRequest"%>
<%@page 	import="javax.portlet.RenderRequest"%>
<%@page 	import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page 	import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page 	import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page 	import="com.liferay.portal.kernel.util.Validator"%>
<%@page 	import="com.liferay.portal.kernel.util.StringPool"%>
<%@page 	import="com.liferay.portal.kernel.util.Constants"%>
<%@page 	import="com.liferay.portal.util.PortalUtil"%>

<%@page 	import="java.util.List"%>
<%@page 	import="java.util.ArrayList"%>

<liferay-theme:defineObjects />
<portlet:defineObjects />

