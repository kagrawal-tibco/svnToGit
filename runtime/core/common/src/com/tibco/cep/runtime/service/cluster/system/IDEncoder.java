/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 30/6/2010
 */

package com.tibco.cep.runtime.service.cluster.system;


/**
 * 
 * @author bgokhale
 * Encodes a typeId and id into a long (last 14 bits used to encode typeid)
 * Used by EntityIdGenerator for generating ids for a given classid.
 *
 * 
 */
public class IDEncoder {
	

	private static int TYPEIDMASK = 0x3FFF;
	private static int TYPEIDBITS = 14;
	
	/**
	 * Decode the typeid from the given key
	 * @param key key from which to decode
	 * @return typeId decoded typeid
	 */
	public static int decodeTypeId (long key) {
		return (int)key & TYPEIDMASK;
	}
	
	/**
	 * Encode the typeid into the long value thats returned
	 * @param id	object's id part
	 * @param typeId object's typeid part
	 * @return 
	 */
	public static long encodeTypeId (long id, long typeId) {
		id = (id << TYPEIDBITS) | typeId;
		return id;
	}
	/**
	 * Decode the id part from the long that is passed
	 * @param idTypeId long from which to decode
	 * @return
	 */
	public static long removeTypeId(long idTypeId) {
		return idTypeId >> TYPEIDBITS;
	}

	public static void main(String [] args) {
		IDEncoder encoder = new IDEncoder();
		long id1 = Long.parseLong(args[0]);
		long typeId = Long.parseLong(args[1]);
		
		long encoded = encoder.encodeTypeId(id1, typeId);
		
		long id3 = encoder.decodeTypeId(encoded);
		
		long id4 = encoder.removeTypeId(encoded);
		
		if (id1 != id4 || id3 != typeId) {
			System.out.println("Error");
		} else {
			System.out.println("Success!");
		}
		
	}
}
