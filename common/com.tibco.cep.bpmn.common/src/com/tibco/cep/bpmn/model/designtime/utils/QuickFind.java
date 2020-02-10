package com.tibco.cep.bpmn.model.designtime.utils;


import java.util.ArrayList;
import java.util.List;

public class QuickFind {
	
	public static  <T> List<T> quickFind(List<Pair<T>> pairs,T start,T end) {
		final List<T> index = new ArrayList<T>();
		List<T> resultChain = new ArrayList<T>();
		List<Pair<Integer>> xid = getItemArray(pairs, index);
		if(!index.contains(start)|| !index.contains(end)) {
			return resultChain;
		}
		List<Pair<Integer>> outpair = quickFind(xid);
//		for(Pair<Integer> op:outpair) {
//			System.out.println("  "+index.get(op.getP())+" "+index.get(op.getQ()));
//		}
		final int st = index.indexOf(start),ed = index.indexOf(end);
		final List<Integer> chain = new ArrayList<Integer>();
		buildChain(outpair, st,ed, chain);
		for(int i = 0; i < chain.size(); i++) {
			//System.out.println(index.get(chain.get(i)));
			resultChain.add(index.get(chain.get(i)));
		}
		return resultChain;
	}

	private static <T> List<Pair<Integer>> getItemArray(List<Pair<T>> pairs,
			final List<T> index) {
		List<Pair<Integer>>  xid = new ArrayList<Pair<Integer>>();
		for(Pair<T> pair:pairs) {
			final Pair<T> p = pair;
			if(!index.contains(pair.getP())) {
				index.add(pair.getP());
			}
			if(!index.contains(pair.getQ())) {
				index.add(pair.getQ());
			}
//			System.out.println(index.indexOf(pair.getP())+" "+index.indexOf(pair.getQ()));
			xid.add(new Pair<Integer>(index.indexOf(p.getP()),index.indexOf(pair.getQ())));
		}
		return xid;
	}

	private static boolean buildChain(List<Pair<Integer>> outpair, int st, int ed,List<Integer> chain) {
		for(int i =0 ; i < outpair.size() ; i++) {
			if(outpair.get(i).getP() == st) {
				chain.add(st);
			} else {
				continue;
			}
			if(outpair.get(i).getQ() == ed) {
				chain.add(ed);
				return true;
			} else {
				boolean retval =  buildChain(outpair,outpair.get(i).getQ(),ed,chain);
				if(retval) {
					return retval;
				}
			}
		}
		return false;
	}

	private static List<Pair<Integer>> quickFind(List<Pair<Integer>> xid) {
		int id[] = new int[xid.size()*2];
		List<Pair<Integer>> cid = new ArrayList<Pair<Integer>>();
		for (int i = 0; i < id.length; i++)
			id[i] = i;
		for (Pair<Integer> pair: xid) {
			int i, j, p = pair.getP(), q = pair.getQ(); 
			for (i = p; i != id[i]; i = id[i]); 
			for (j = q; j != id[j]; j = id[j]); 
			if (i == j) continue; 
			id[i] = j; 
//			System.out.println(" " + p + " " + q);
			cid.add(new Pair<Integer>(p,q));
		}
		return cid;
	}
	
	public static void main(String[] args) {
//		int [][] pairs = new int[][] { {3,4},{4,9},{8,0},{2,3},{5,6},{2,9},{5,9},{7,3},{4,8},{5,6},{0,2},{6,1} };
		int [][] pairs = new int[][] {{2,4},{4,6},{4,7},{4,3},{6,9},{7,9},{9,8},{8,1},{3,11},{11,12},{12,1}};
		List<Pair<Integer>> plist = new ArrayList<Pair<Integer>>();
		for(int[] pair:pairs) {
			Pair<Integer> p = new Pair<Integer>(pair[0],pair[1]);
			plist.add(p);
		}
//		System.out.println(quickFind(plist, 2, 9));
		System.out.println(quickFind(plist, 7, 1));
	}	
	
	
} 
