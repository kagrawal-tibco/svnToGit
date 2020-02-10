package com.tibco.cep.dashboard.plugin.internal;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALView;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPageSelectorComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALPartition;
import com.tibco.cep.dashboard.psvr.plugin.Builder;
import com.tibco.cep.dashboard.psvr.plugin.BuilderResult;
import com.tibco.cep.dashboard.psvr.plugin.PlugIn;
import com.tibco.cep.dashboard.psvr.plugin.BuilderResult.SEVERITY;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;

public class DefaultBuilder extends Builder {

	private int spanThreshold;

	private int firstPartitionClassicLayoutSpan;

	private int emergencySpan;

	private DefaultBuilder() {
		super(new DefaultPlugIn());
		emergencySpan = (Integer) DefaultPlugInProperies.EMERGENCY_SPAN.getDefaultValue();
		spanThreshold = (Integer) DefaultPlugInProperies.SPAN_THRESHOLD.getDefaultValue();
		firstPartitionClassicLayoutSpan = (Integer) DefaultPlugInProperies.CLASSIC_LAYOUT_FIRST_PARTITION_SPAN.getDefaultValue();
	}

	protected DefaultBuilder(PlugIn plugIn) {
		super(plugIn);
		emergencySpan = (Integer) DefaultPlugInProperies.EMERGENCY_SPAN.getValue(this.plugIn.getProperties());
		spanThreshold = (Integer) DefaultPlugInProperies.SPAN_THRESHOLD.getValue(this.plugIn.getProperties());
		firstPartitionClassicLayoutSpan = (Integer) DefaultPlugInProperies.CLASSIC_LAYOUT_FIRST_PARTITION_SPAN.getValue(this.plugIn.getProperties());
	}

	@Override
	public List<BuilderResult> build(TokenRoleProfile profile) throws MALException {
		MALView viewsConfig = profile.getViewsConfigHelper().getViewsConfig();
		return adjustSpans(viewsConfig);
	}

	private List<BuilderResult> adjustSpans(MALView viewsConfig) {
		List<BuilderResult> results = new LinkedList<BuilderResult>();
		for (MALPage page : viewsConfig.getAccessiblePage()) {
			int[] spans = getSpans(page.getPartition());
			int[] newspans = recomputeSpansForClassicLayout(page, spans);
			if (newspans == null) {
				newspans = betterRecomputeSpan(spans);
			}
			for (int i = 0; i < newspans.length; i++) {
				if (newspans[i] != spans[i]) {
					page.getPartition(i).setSpan(newspans[i] + "%");
					results.add(new BuilderResult(SEVERITY.INFO, "Adjusted span of " + page.getPartition(i) + " under " + page + " to " + newspans[i] + "%", page.getPartition(i)));
				}
			}
			for (MALPartition partition : page.getPartition()) {
				spans = getSpans(partition.getPanel());
				newspans = betterRecomputeSpan(spans);
				for (int i = 0; i < newspans.length; i++) {
					if (newspans[i] != spans[i]) {
						partition.getPanel(i).setSpan(newspans[i] + "%");
						results.add(new BuilderResult(SEVERITY.INFO, "Adjusted span of " + partition.getPanel(i) + " under " + page + "/" + partition + " to " + newspans[i] + "%", partition));
					}
				}
			}
		}
		return results;

	}

	private int[] recomputeSpansForClassicLayout(MALPage page, int[] spans) {
		if (page.getPartitionCount() > 1) {
			MALPartition firstPartition = page.getPartition(0);
			int firstPartitionSpan = spans[0];
			if (firstPartitionSpan <= spanThreshold && firstPartition.getPanelCount() == 1) {
				// the first span is less then threshold and has only one panel
				if (firstPartition.getPanel(0).getComponentCount() == 1) {
					// check if the panel contains only one component and it is
					// a MALPageSetSelectorComponent
					if (firstPartition.getPanel(0).getComponent(0) instanceof MALPageSelectorComponent) {
						// yes we are dealing with the classic dashboard layout
						int[] newspans = new int[spans.length];
						System.arraycopy(spans, 0, newspans, 0, spans.length);
						for (int i = 1; i < newspans.length; i++) {
							if (newspans[i] <= spanThreshold) {
								// we have atleast one partition with under
								// threshold span
								// update the first to be 20%
								newspans[0] = firstPartitionClassicLayoutSpan;
								break;
							}
						}
						// recompute all the spans
						newspans = betterRecomputeSpan(newspans);
						return newspans;
					}
				}
			}
		}
		return null;
	}

	private int[] getSpans(MALPartition[] partitions) {
		if (partitions == null || partitions.length == 0) {
			return new int[0];
		}
		int[] spans = new int[partitions.length];
		for (int i = 0; i < spans.length; i++) {
			String span = partitions[i].getSpan();
			if (span.endsWith("%") == true) {
				span = span.substring(0, span.length() - 1);
			}
			try {
				spans[i] = Integer.parseInt(span);
			} catch (NumberFormatException e) {
				spans[i] = 1;
			}
		}
		return spans;
	}

	private int[] getSpans(MALPanel[] panels) {
		if (panels == null || panels.length == 0) {
			return new int[0];
		}
		int[] spans = new int[panels.length];
		for (int i = 0; i < spans.length; i++) {
			String span = panels[i].getSpan();
			if (span.endsWith("%") == true) {
				span = span.substring(0, span.length() - 1);
			}
			try {
				spans[i] = Integer.parseInt(span);
			} catch (NumberFormatException e) {
				spans[i] = 1;
			}
		}
		return spans;
	}

	@SuppressWarnings("unused")
	private int[] recomputeSpans(int[] spans) {
		int[] recomputedspans = new int[spans.length];
		if (spans.length == 1 && spans[0] != 100) {
			recomputedspans[0] = 100;
		} else {
			int remainingspan = 100;
			double totalWeight = 0;
			for (int i = 0; i < spans.length; i++) {
				if (spans[i] > spanThreshold) {
					remainingspan = remainingspan - spans[i];
				} else {
					totalWeight = totalWeight + spans[i];
				}
			}
			if (remainingspan != 0) {
				double minWeight = remainingspan / totalWeight;
				for (int i = 0; i < spans.length; i++) {
					if (spans[i] <= spanThreshold) {
						recomputedspans[i] = (int) Math.ceil(minWeight * spans[i]);
						if (i + 1 < spans.length) {
							remainingspan = remainingspan - recomputedspans[i];
							totalWeight = totalWeight - spans[i];
							minWeight = remainingspan / totalWeight;
						}
					} else {
						recomputedspans[i] = spans[i];
					}
				}
			} else {
				recomputedspans = spans;
			}
		}
		return recomputedspans;
	}

	private int[] betterRecomputeSpan(int[] spans) {
		int[] recomputedspans = new int[spans.length];
		if (spans.length == 1 && spans[0] != 100) {
			recomputedspans[0] = 100;
		} else {
			// extract remaining span and total weight
			int remainingspan = 100;
			double totalWeight = 0;
			for (int i = 0; i < spans.length; i++) {
				if (spans[i] > spanThreshold) {
					remainingspan = remainingspan - spans[i];
				} else {
					totalWeight = totalWeight + spans[i];
				}
			}
			if (remainingspan == 0 && totalWeight == 0) {
				return spans;
			}
			int roundOffError = 100;
			// check if we excess space and under threshold spans
			if (remainingspan > 0 && totalWeight > 0) {
				// yes, we should distribute the excess space to all under
				// threshold spans proportionately
				double minWeight = remainingspan / totalWeight;

				for (int i = 0; i < spans.length; i++) {
					if (spans[i] <= spanThreshold) {
						recomputedspans[i] = (int) Math.ceil(minWeight * spans[i]);
					} else {
						recomputedspans[i] = spans[i];
					}
					roundOffError = roundOffError - recomputedspans[i];
				}
			} else {
				// no remaining <= 0
				int reduction = remainingspan;
				double emergencySpanDistribution = 1;
				if (totalWeight > 0) {
					// we have span(s) less then the threshold
					reduction = remainingspan - emergencySpan;
					emergencySpanDistribution = emergencySpan / totalWeight;
				}
				double usedUpSpan = 100 - reduction;
				for (int i = 0; i < spans.length; i++) {
					int span = spans[i];
					if (spans[i] <= spanThreshold) {
						// we have a less then threshold span, adjust its span
						// based on emergency space
						span = (int) Math.ceil(emergencySpanDistribution * span);
					}
					// adjust the final span based on reduction and its current
					// proportion
					span = span + (int) Math.floor(((span / usedUpSpan) * reduction));
					roundOffError = roundOffError - span;
					recomputedspans[i] = span;
				}
			}
			// we will add the rounding error gap to the last span
			if (roundOffError != 0) {
				recomputedspans[spans.length - 1] = recomputedspans[spans.length - 1] + roundOffError;
			}
		}
		return recomputedspans;
	}

	public static void main(String[] args) {
		DefaultBuilder builder = new DefaultBuilder();
		int[][] spans = new int[][] { new int[] { 1, 1 }, new int[] { 1, 20 }, new int[] { 20, 20 }, new int[] { 20, 30 }, new int[] { 20, 80 }, new int[] { 20, 100 }, new int[] { 1, 1, 1 }, new int[] { 1, 20, 1 },
				new int[] { 30, 20, 1 }, new int[] { 40, 60, 1 }, new int[] { 40, 80, 1 }, new int[] { 40, 80, 50 }, new int[] { 40, 30, 30 }, new int[] { 20, 50, 40 }, };

		for (int[] span : spans) {
			System.out.println(Arrays.toString(span) + " better recomputed as " + Arrays.toString(builder.betterRecomputeSpan(span)));
		}
	}

}