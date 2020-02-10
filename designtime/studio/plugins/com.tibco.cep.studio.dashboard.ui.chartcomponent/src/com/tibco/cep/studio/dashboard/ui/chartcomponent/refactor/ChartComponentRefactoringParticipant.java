package com.tibco.cep.studio.dashboard.ui.chartcomponent.refactor;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.refactoring.ComponentRefactoringParticipant;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartComponentRefactoringParticipant extends ComponentRefactoringParticipant {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
	public ChartComponentRefactoringParticipant() {
		super(BEViewsElementNames.EXT_CHART_COMPONENT, 
				BEViewsElementNames.TEXT_OR_CHART_COMPONENT, 
				BEViewsElementNames.CHART_COMPONENT, 
				BEViewsElementNames.TEXT_COMPONENT, 
				BEViewsElementNames.DATA_SOURCE,
				BEViewsElementNames.METRIC
		);
	}

	@Override
	protected String changeTitle() {
		return "Chart Changes:";
	}

	@Override
	protected List<LocalElement> getAffectedComponents(IProject project) throws Exception {
		LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(project);
		return coreFactory.getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
	}

	@Override
	protected boolean updateRelatedComponentReferences(URI affectedComponentURI, Component affectedComponent, Component referredComponent, URI refactoredURI) {
		EList<BEViewsElement> relatedComponents = affectedComponent.getRelatedElement();
		for (int i = 0; i < relatedComponents.size(); i++) {
			BEViewsElement relatedComponent = relatedComponents.get(i);
			if (checkEquals(referredComponent, relatedComponent)) {
				Component proxyObject = (Component) createProxyToNewPath(refactoredURI, affectedComponentURI, referredComponent.eClass());
				relatedComponents.set(i, proxyObject);
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean deleteRelatedComponentReference(Component component, EObject relatedComponent) {
		EList<BEViewsElement> relatedComponents = component.getRelatedElement();
		ListIterator<?> listIterator = relatedComponents.listIterator();
		while (listIterator.hasNext()) {
			EObject existingRelatedComponent = (EObject) listIterator.next();
			if (checkEquals(existingRelatedComponent, relatedComponent) == true) {
				listIterator.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	protected List<DataConfig> getDataConfigs(SeriesConfig seriesConfig) {
		TwoDimSeriesConfig twoDimSeriesConfig = (TwoDimSeriesConfig) seriesConfig;
		List<DataConfig> dataConfigs = new LinkedList<DataConfig>();
		dataConfigs.add(twoDimSeriesConfig.getCategoryDataConfig());
		dataConfigs.addAll(twoDimSeriesConfig.getValueDataConfig());
		return dataConfigs;
	}

	public static void main(String[] args) {
		Pattern p = Pattern.compile("\\{[[\\S]&&[^\\}]]*\\}");
		// Pattern p = Pattern.compile("\\{sss[,\\S]*\\}");
		String[] ss = new String[] { "{Me}={You}", "Foo {sss} Bar", "{sss,number}", "{sss,number,yyyy-MM-dd'T'HH:mm:ss.SSSZ}", "{sss,number,#,##0.0#;-(#,##0.0#)} foo {rrr,number,ddd}" };
		for (String s : ss) {
			// String all = s.replaceAll("\\{sss[,\\S]*\\}", "Yahoo");
			Matcher matcher = p.matcher(s);
			List<String> args1 = new LinkedList<String>();
			while (matcher.find() == true) {
				StringBuilder match = new StringBuilder(matcher.group());
				match.replace(0, 1, "");
				int idx = match.indexOf(",");
				if (idx == -1) {
					idx = match.length() - 1;
				}
				match.replace(idx, match.length(), "");
				args1.add(match.toString());
			}
			System.out.println("[" + s + "]::[" + args1 + "]");

		}
	}
}