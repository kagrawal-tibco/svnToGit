/**
 * 
 */
package com.tibco.cep.liveview.as;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.event.SpaceEvent;
import com.tibco.as.space.event.SpaceEvent.EventType;
import com.tibco.cep.runtime.model.serializers.SerializableLite;

/**
 * @author vpatil
 *
 */
public class TupleContent implements SerializableLite {
	private String spaceName;
	private Tuple tuple;
	private EventType eventType;
	private int bufferLength;
	
	public TupleContent() {}
	
	public TupleContent(SpaceEvent spaceEvent) {
		this(spaceEvent.getSpaceName(), spaceEvent.getTuple(), spaceEvent.getType());
	}
	
	public TupleContent(String spaceName, Tuple tuple, EventType eventType) {
		this.spaceName = spaceName;
		this.tuple = tuple;
		this.eventType = eventType;
	}
	
	@Override
	public void readExternal(DataInput dataIn) throws IOException {
		this.spaceName = dataIn.readUTF();
		this.bufferLength = dataIn.readInt();
		this.eventType = Enum.valueOf(EventType.class, dataIn.readUTF());
		
		byte[] tupleBuff = new byte[this.bufferLength];
		dataIn.readFully(tupleBuff);
		
		this.tuple = Tuple.create();
		try {
			this.tuple.deserialize(tupleBuff);
		} catch (ASException asException) {
			throw new RuntimeException(asException);
		}
	}
	
	@Override
	public void writeExternal(DataOutput dataOut) throws IOException {
		dataOut.writeUTF(spaceName);
		byte[] tupleBuff = null;
		try {
			tupleBuff = tuple.serialize();
		} catch (ASException asException) {
			throw new RuntimeException(asException);
		}
		bufferLength = tupleBuff.length;
		dataOut.writeInt(bufferLength);
		dataOut.writeUTF(eventType.name());
		dataOut.write(tupleBuff);
	}

	public String getSpaceName() {
		return spaceName;
	}

	public Tuple getTuple() {
		return tuple;
	}

	public EventType getEventType() {
		return eventType;
	}
}
