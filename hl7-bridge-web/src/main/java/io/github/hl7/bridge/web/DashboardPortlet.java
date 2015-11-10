package io.github.hl7.bridge.web;

import java.io.IOException;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=DashboardPortlet",
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class DashboardPortlet extends MVCPortlet {
	
	@Override
	public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
		Map<String, Integer> counter = DashboardRepository.getInstance().getMessageCounter();
		request.setAttribute("counter", counter);
		super.render(request, response);
	}
	
}