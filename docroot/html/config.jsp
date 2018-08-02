<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<portlet:defineObjects />

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />
<aui:form action="<%=configurationURL %>" method="post" name="formulario">
	<aui:fieldset label="Configuração de envio de E-mail">
		<aui:input required="true" name="email" label="E-mail" value='<%=portletPreferences.getValue("email", StringPool.BLANK) %>'>
			<aui:validator name="email"/>
		</aui:input>
	</aui:fieldset>
	<br />
	<aui:button value="Salvar" type="submit"/>
</aui:form>
