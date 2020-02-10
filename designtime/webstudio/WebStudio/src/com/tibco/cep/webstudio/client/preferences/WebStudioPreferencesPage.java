package com.tibco.cep.webstudio.client.preferences;

import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.Offline;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.tibco.cep.webstudio.client.PortalLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.model.User;
import com.tibco.cep.webstudio.client.portal.ApplicationPreferencePortlet;
import com.tibco.cep.webstudio.client.portal.DashboardAppearancePortlet;
import com.tibco.cep.webstudio.client.portal.NotificationPortlet;
import com.tibco.cep.webstudio.client.portal.PreferencesPortlet;
import com.tibco.cep.webstudio.client.portal.RoleConfigurationPortlet;
import com.tibco.cep.webstudio.client.util.WebStudioShortcutUtil;

public class WebStudioPreferencesPage extends HLayout {

	private PortalLayout portalLayout;
	private DashboardAppearancePortlet dashboardAppearencePortlet;
	private PreferencesPortlet preferencesPortlet;
	private RoleConfigurationPortlet roleConfigurationPortlet;
	private ApplicationPreferencePortlet applicationPreferencePortlet;
	private NotificationPortlet notificationsPortlet;

	public WebStudioPreferencesPage(User user) {
		super(2);
		setWidth100();
		setHeight100();
		setCanDragResize(true);
		setShowHover(false);
		setOverflow(Overflow.AUTO);
		this.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				WebStudioShortcutUtil.handleKeyPressEvent(event);
			}
		});
		this.addDrawHandler(new DrawHandler() {

			@Override
			public void onDraw(DrawEvent event) {
				WebStudioPreferencesPage.this.focus();
				WebStudioPreferencesPage.this.setCanFocus(true);
			}
		});
		initializePortalPage(user);
	}

	private void initializePortalPage(User user) {
		HLayout layout = new HLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setStyleName("ws-portal");

		createPortalLayout(layout, user);

		addMember(layout);
	}

	public void createPortalLayout(HLayout conentsCanvas, final User user) {
		setTitle(user.getUserName() + "'s settings page");

		String prop = "2";// WebStudioUtils.getPortalNumColumnsProperty();
		Object object = Offline.get(prop);
		if (object instanceof String) {
			portalLayout = new PortalLayout(Integer.parseInt((String) object));
		} else {
			int numColumns = 2;
			Offline.put(prop, numColumns);
			portalLayout = new PortalLayout(numColumns);
		}
		portalLayout.setWidth100();
		portalLayout.setHeight100();
		portalLayout.setMargin(20);
		portalLayout.setMembersMargin(20);

		// create portlets...
		createPortlets(portalLayout);

		conentsCanvas.addMember(portalLayout);
	}

	private void createPortlets(final PortalLayout portalLayout) {
		createConfigurationPortlet(portalLayout);
		createPreferencesPortlet(portalLayout);
		if(ApplicationPreferenceHelper.hasPortletPremission()) {
			createApplicationPreferencesPortlet(portalLayout);
		}
		
		
	}

	private void createConfigurationPortlet(PortalLayout portalLayout) {
    	dashboardAppearencePortlet = new DashboardAppearancePortlet(portalLayout);
		portalLayout.addPortlet(dashboardAppearencePortlet);
	}

	private void createPreferencesPortlet(PortalLayout portalLayout) {
		preferencesPortlet = new PreferencesPortlet();
		portalLayout.addPortlet(preferencesPortlet);
	}
	
	private void createApplicationPreferencesPortlet(PortalLayout portalLayout) {
    	applicationPreferencePortlet = new ApplicationPreferencePortlet(portalLayout);
    	portalLayout.addPortlet(applicationPreferencePortlet);
    }
	
	/**
	 * Update the portlet list in Dashboard Apprearence portlet
	 */
	public void updatePortletListInAppearancePortlet() {
		dashboardAppearencePortlet.updatePortletList();
	}

	/**
	 * Clear SCS Preferences
	 */
	public void clearSCSPreferences() {
		preferencesPortlet.clearSCSFields();
	}

	/**
	 * Hide the prefrence page
	 */
	public void hidePreferencesPage(Rectangle rectangle) {
		final Rectangle rect = WebStudio.get().getMainLayout().getRect();

		// create an outline around the clicked button
		final Canvas outline = new Canvas();
		outline.setLeft(rect.getLeft());
		outline.setTop(rect.getTop());
		outline.setWidth(rect.getWidth());
		outline.setHeight(rect.getHeight());
		outline.setBorder("2px solid #8289A6");
		outline.draw();
		outline.bringToFront();

		outline.animateRect(rectangle.getLeft(), rectangle.getTop(), rectangle.getWidth(), rectangle.getHeight(), new AnimationCallback() {

					@Override
					public void execute(boolean earlyFinish) {
						hide();
						outline.destroy();
					}
				});
	}
}
