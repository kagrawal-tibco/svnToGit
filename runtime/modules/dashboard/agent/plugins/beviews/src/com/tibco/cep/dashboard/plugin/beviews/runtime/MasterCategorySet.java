package com.tibco.cep.dashboard.plugin.beviews.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.tibco.cep.dashboard.psvr.vizengine.CategoryTick;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class MasterCategorySet {

	public static enum DIFFERENCE {
		NONE, ADD, ORDER, DELETE
	};

	protected String identifier;

	protected ArrayList<CategoryTick> residentTicks;

	public MasterCategorySet(String identifier) {
		super();
		this.identifier = identifier;
		this.residentTicks = new ArrayList<CategoryTick>();
	}

	public void setTicks(Collection<CategoryTick> ticks) {
		residentTicks.clear();
		residentTicks.addAll(ticks);
	}

	public DIFFERENCE computeDifference(Logger logger, Collection<CategoryTick> ticks) {
		int residentTicksCnt = residentTicks.size();
		int incomingTicksCnt = ticks.size();
		if (residentTicksCnt < incomingTicksCnt) {
			//the incoming is more then the resident
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Found new values in " + ticks + " compared to " + residentTicks + " for " + identifier + "...");
			}
			return DIFFERENCE.ADD;
		}
		if (residentTicksCnt > incomingTicksCnt) {
			//the incoming is less then the resident
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Found less values in " + ticks + " compared to " + residentTicks + " for " + identifier + "...");
			}
			return DIFFERENCE.DELETE;
		}
		//check if resident and incoming content match
		if (residentTicks.containsAll(ticks) == false) {
			//the resident and tick collections are not same content wise
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Found new values in " + ticks + " compared to " + residentTicks + " for " + identifier + "...");
			}
			return DIFFERENCE.ADD;
		}
		//resident and ticks are same content wise check for ordering
		//note that ticks could be a sub set of resident category ticks and spread out (rather than be continuous)
		Set<CategoryTick> incomingTicksInResident = new LinkedHashSet<CategoryTick>(residentTicks);
		//removing non ticks from the resident ticks whilst maintaing the order
		incomingTicksInResident.retainAll(ticks);
		//compare the order
		Iterator<CategoryTick> incomingTicksInResidentIterator = incomingTicksInResident.iterator();
		Iterator<CategoryTick> tickIterator = ticks.iterator();
		while (incomingTicksInResidentIterator.hasNext() == true && tickIterator.hasNext() == true) {
			if (incomingTicksInResidentIterator.next().equals(tickIterator.next()) == false) {
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Found values in " + ticks + " in a different order compared to " + residentTicks + " for " + identifier + "...");
				}
				return DIFFERENCE.ORDER;
			}
		}
		return DIFFERENCE.NONE;
	}

	/*public void merge(Logger logger, Collection<CategoryTick> ticks){
		DIFFERENCE difference = computeDifference(logger, ticks);
		//check if we have no resident ticks or difference is ORDER , then simply replace
		if (residentTicks == null || difference.compareTo(DIFFERENCE.ORDER) == 0) {
			//we have no resident ticks or difference is ORDER , then simply replace
			setTicks(ticks);
			return;
		}
		//we have add difference
		if (residentTicks.containsAll(ticks) == true && residentTicks.size() == ticks.size()) {
			// we are working with the same set, do nothing
			return;
		}
		Iterator<CategoryTick> ticksIterator = ticks.iterator();
		int tickCounter = 0;
		while (ticksIterator.hasNext()) {
			//go over each tick
			CategoryTick currentTick = ticksIterator.next();
			//does resident tick set contain current tick
			int categoryTicksIdx = residentTicks.indexOf(currentTick);
			if (categoryTicksIdx != -1 && categoryTicksIdx != tickCounter) {
				// yes the resident tick set contains the current tick, but the positions do not match,
				//remove the tick from resident tick set
				residentTicks.remove(categoryTicksIdx);
				categoryTicksIdx = -1;
			}
			if (categoryTicksIdx == -1){
				//we need to merge current tick into the resident tick set
				//we need to find the injection index using the adjacent ticks
				int injectionIndex = -1;
				//we will use the tick above/left of the current tick to compute injection point
				int adjacentKeyIndex = tickCounter - 1;
				if (adjacentKeyIndex >= 0) {
					//find the tick before current tick from the ticks
					CategoryTick tickBeforeMissingTick = ticks.get(adjacentKeyIndex);
					//get the position of the adjacent tick in the resident tick set
					injectionIndex = residentTicks.indexOf(tickBeforeMissingTick) + 1;
					if (injectionIndex != -1 && logger.isEnabledFor(Level.DEBUG) == true) {
						//we found the adjacent tick in resident set , so the injection index is one after that
						logger.log(Level.DEBUG, "Found " + tickBeforeMissingTick + " as the key before " + currentTick + ", injection index is " + injectionIndex + " for " + identifier + "...");
					}
				}
				if (injectionIndex == -1) {
					//we did not find the tick above/left of the current tick so we will try to find the tick below/right of the current tick
					adjacentKeyIndex = tickCounter + 1;
					if (adjacentKeyIndex < ticks.size()) {
						//find the tick after current tick from the ticks
						CategoryTick tickAfterMissingTick = ticks.get(adjacentKeyIndex);
						//get the position of the adjacent tick in the resident tick set
						injectionIndex = residentTicks.indexOf(tickAfterMissingTick);
						if (injectionIndex != -1 && logger.isEnabledFor(Level.DEBUG) == true) {
							//we found the adjacent tick in resident set , so the injection index is one before that
							logger.log(Level.DEBUG, "Found " + tickAfterMissingTick + " as the key after " + currentTick + ", injection index is " + injectionIndex + " for " + identifier + "...");
						}
					}
				}
				if (injectionIndex < 0 || injectionIndex >= residentTicks.size()) {
					//we did not find a good injection index, so we add the current tick to the end of the resident set
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Appending " + currentTick + " to end of set for " + identifier + "...");
					}
					residentTicks.add(currentTick);
				} else {
					//we did find a good injection index, so we add the current tick to the resident set
					residentTicks.add(injectionIndex, currentTick);
				}
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Set Is" + residentTicks + " for " + identifier + "...");
				}
			} else {
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Found " + currentTick + ", hence skipping it for " + identifier + "...");
				}
			}
			tickCounter++;
		}
	}*/

	public void clear() {
		residentTicks.clear();
	}

	public Collection<CategoryTick> ticks() {
		return Collections.unmodifiableList(residentTicks);
	}

	public boolean isEmpty() {
		return residentTicks.isEmpty();
	}

/*	public static void main(String[] args) {
		List<CategoryTick> base = Arrays.asList(new CategoryTick("S1","S1","S1"),new CategoryTick("India","India","India"),new CategoryTick("USA","USA","USA"));

		List<CategoryTick> S2 = Arrays.asList(new CategoryTick("Iran","Iran","Iran"),new CategoryTick("Canada","Canada","Canada"),new CategoryTick("USA","USA","USA"),new CategoryTick("Burma","Burma","Burma"));
		MasterCategorySet set = new MasterCategorySet("Foo");
		set.setTicks(S1);
		set.merge(new ConsoleLogger(), S2);
		System.out.println(set.ticks());
	}
*/

}