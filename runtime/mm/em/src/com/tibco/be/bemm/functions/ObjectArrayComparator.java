package com.tibco.be.bemm.functions;

import java.util.Comparator;

class ObjectArrayComparator implements Comparator<Object[]>{
	
	private int[] fieldIndexes;
	
	private boolean[] fieldOrder;
	
	ObjectArrayComparator(String[][] sortSpec) {
		fieldIndexes = new int[sortSpec.length];
		fieldOrder = new boolean[sortSpec.length];
		for (int i = 0; i < sortSpec.length; i++) {
			String[] singleSortSpec = sortSpec[i];
			fieldIndexes[i] = Integer.parseInt(singleSortSpec[0]);
			fieldOrder[i] = Boolean.parseBoolean(singleSortSpec[1]);
		}
	}	

	public int compare(Object[] o1, Object[] o2) {
		if (o1 != null && o2 != null){
			int i = 0;
			for (int j = 0; j < fieldIndexes.length; j++) {
				Comparable value1 = (Comparable) o1[fieldIndexes[j]];
				Comparable value2 = (Comparable) o2[fieldIndexes[j]];
				int comparision = 0;
				if (value1 != null && value2 != null){
					comparision = value1.compareTo(value2);
				}
				else if (value1 == null){
					comparision = -1;
				}
				else {
					comparision = 1;
				}
				if (fieldOrder[j] == false){
					comparision = -comparision;
				}
				i = i + comparision;
				if (i != 0){
					break;
				}
			}
			return i;
		}
		else if (o1 == null){
			return -1;
		}
		else {
			return 1;
		}
	}
	
//	public static void main(String[] args) {
//		String[][] spec = new String[][]{
//				new String[]{"3","true"},
//				new String[]{"1","true"},
//		};
//		
//		List<Object[]> preSort = new LinkedList<Object[]>();
//		preSort.add(new Object[]{"hostname","NameA",10,10});
//		preSort.add(new Object[]{"hostname","NameB",10,1});
//		preSort.add(new Object[]{"hostname","NameD",10,1});
//		preSort.add(new Object[]{"hostname","NameC",10,1});
//		
//		System.out.println(preSort);
//		
//		List<Object[]> postSort = new LinkedList<Object[]>(preSort);
//		Collections.sort(postSort, new ObjectArrayComparator(spec));
//		System.out.println(postSort);
//		
//		spec = new String[][]{
//				new String[]{"3","false"},
//				new String[]{"1","true"},
//		};
//
//		postSort = new LinkedList<Object[]>(preSort);
//		Collections.sort(postSort, new ObjectArrayComparator(spec));
//		System.out.println(postSort);
//		
//	}	
}
