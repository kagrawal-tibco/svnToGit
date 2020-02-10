package com.tibco.cep.bpmn.ui.transfer;

import java.awt.datatransfer.Clipboard;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
public class BPMNProcessTransfer extends ByteArrayTransfer {

	/**
     * Singleton instance.
     */
    private static final BPMNProcessTransfer instance = new BPMNProcessTransfer();

    private static final String TYPE_NAME = "process-transfer-format:" + System.currentTimeMillis() + ":" + instance.hashCode();

    private static final int TYPEID = registerType(TYPE_NAME);
    
    private ProcessDataFlavour[] processes;
    
    @SuppressWarnings("unused")
	private TSENode[] nodes;// for loading inmemory nodes
    
	private Clipboard clipboard; //for processing canvas clipboard objects
	
	private String relativePath;

	private Map<String, Object> copyMap;

	/**
     * Creates a new transfer object.
     */
    private BPMNProcessTransfer() {
    	copyMap = new HashMap<String, Object>();
    }

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance
     */
    public static BPMNProcessTransfer getInstance() {
        return instance;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.dnd.Transfer#getTypeIds()
     */
    protected int[] getTypeIds() {
        return new int[] { TYPEID };
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.dnd.Transfer#getTypeNames()
     */
    protected String[] getTypeNames() {
        return new String[] { TYPE_NAME };
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.dnd.ByteArrayTransfer#javaToNative(java.lang.Object, org.eclipse.swt.dnd.TransferData)
     */
    protected void javaToNative(Object data, TransferData transferData) {
        if (!(data instanceof ProcessDataFlavour[])) {
            return;
        }
        processes = (ProcessDataFlavour[]) data;
        int processObjectsCount = processes.length;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(out);
            dataOut.writeInt(processObjectsCount);
            for (ProcessDataFlavour processDataFlavour :  processes) {
            	if (processDataFlavour.getManager().getEditor().getEditorInput() instanceof BpmnEditorInput) {
            		BpmnEditorInput bpmnEditorInput =  (BpmnEditorInput)processDataFlavour.getManager().getEditor().getEditorInput();
            		String path = bpmnEditorInput.getFile().getProjectRelativePath().toString();
            		if (relativePath != null && !relativePath.equalsIgnoreCase(path)) {
            			relativePath = path;
            		} else {
            			relativePath = path;
            		}
            	}
            	Clipboard tempClipboard = processDataFlavour.getClipboard();
            	clipboard = tempClipboard;
            	writeProcessObject(dataOut,processDataFlavour.getEObject());
            }
            dataOut.close();
            out.close();
            byte[] bytes = out.toByteArray();
            super.javaToNative(bytes, transferData);
        } catch (IOException e) {
        	BpmnUIPlugin.debug(this.getClass().getName(), e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.dnd.ByteArrayTransfer#nativeToJava(org.eclipse.swt.dnd.TransferData)
     */
    protected Object nativeToJava(TransferData transferData) {
        byte[] bytes = (byte[]) super.nativeToJava(transferData);
        if (bytes == null) {
			return null;
		}
        List<EObject> list = new ArrayList<EObject>();
        for (ProcessDataFlavour processDataFlavour :  processes) {
        	list.add(processDataFlavour.getEObject());
        }
        EObject[] eObjects = new EObject[list.size()];
        list.toArray(eObjects);
        
        return eObjects;
    }
    
    public Clipboard getClipboard() {
		return clipboard;
	}

	public void setClipboard(Clipboard clipboard) {
		this.clipboard = clipboard;
	}
	 
    public String getRelativePath() {
		return relativePath;
	}
    
	public Map<String, Object> getCopyMap() {
		return copyMap;
	}
    
    /**
     * @param dataOut
     * @param tsObject
     * @throws IOException
     */
    protected void writeProcessObject(DataOutputStream dataOut, EObject tsObject) throws IOException {
//    	byte[] contents = CompareUtils.getEObjectContents(tsObject);
//    	dataOut.write(contents);
     }
}