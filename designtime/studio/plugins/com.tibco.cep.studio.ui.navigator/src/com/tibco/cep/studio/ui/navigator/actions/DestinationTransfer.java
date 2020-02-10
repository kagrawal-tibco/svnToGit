package com.tibco.cep.studio.ui.navigator.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;

/**
 * 
 * @author sasahoo
 *
 */
public class DestinationTransfer extends ByteArrayTransfer {

    /**
     * Singleton instance.
     */
    private static final DestinationTransfer instance = new DestinationTransfer();

    // Create a unique ID to make sure that different Eclipse
    // applications use different "types" of <code>DestinationTransfer</code>
    private static final String TYPE_NAME = "destination-transfer-format:" + System.currentTimeMillis() + ":" + instance.hashCode();

    private static final int TYPEID = registerType(TYPE_NAME);

    private IWorkspace workspace = ResourcesPlugin.getWorkspace();

    /**
     * Creates a new transfer object.
     */
    private DestinationTransfer() {
    }

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance
     */
    public static DestinationTransfer getInstance() {
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
        if (!(data instanceof ChannelDestinationNode[])) {
            return;
        }
        ChannelDestinationNode[] destNodes = (ChannelDestinationNode[]) data;
        /**
         *   For each destination:
         *  (String) path of destination
         */

        int destinationCount = destNodes.length;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(out);

            //write the number of destinations
            dataOut.writeInt(destinationCount);

            //write each destination
            for (int i = 0; i < destNodes.length; i++) {
                writeDestination(dataOut, destNodes[i]);
            }

            //cleanup
            dataOut.close();
            out.close();
            byte[] bytes = out.toByteArray();
            super.javaToNative(bytes, transferData);
        } catch (IOException e) {
            //it's best to send nothing if there were problems
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.dnd.ByteArrayTransfer#nativeToJava(org.eclipse.swt.dnd.TransferData)
     */
    protected Object nativeToJava(TransferData transferData) {
    	/**
         *   For each destination:
         *  (String) path of destination
         */
        byte[] bytes = (byte[]) super.nativeToJava(transferData);
        if (bytes == null) {
			return null;
		}
        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(bytes));
        try {
            int count = in.readInt();
            Destination[] results = new Destination[count];
            for (int i = 0; i < count; i++) {
                results[i] = readDestination(in);
            }
            return results;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Reads a destination from the given stream.
     *
     * @param dataIn the input stream
     * @return the destination
     * @exception IOException if there is a problem reading from the stream
     */
    private Destination readDestination(DataInputStream dataIn) throws IOException {
        String destPath = dataIn.readUTF();
        String name = destPath.substring(destPath.lastIndexOf("/") + 1);
        String path = destPath.substring(0, destPath.lastIndexOf("/"));
        
        IFile file =  workspace.getRoot().getFile(new Path(path));
        Channel channel =  (Channel)IndexUtils.getEntity(file.getProject().getName(), IndexUtils.getFullPath(file));
        for (Destination dest : channel.getDriver().getDestinations()) {
        	if (dest.getName().equals(name)) {
        		return dest;
        	}
        }
       throw new IllegalArgumentException(
                "Unknown destination type in DestinationTransfer.readDestination");
    }

    /**
     * Writes the given destination to the given stream.
     *
     * @param dataOut the output stream
     * @param destination the destination
     * @exception IOException if there is a problem writing to the stream
     */
    private void writeDestination(DataOutputStream dataOut, ChannelDestinationNode destinationNode)
            throws IOException {
    	Destination destination  = (Destination)destinationNode.getEntity();
    	Channel channel = destination.getDriverConfig().getChannel();
    	IResource resource = IndexUtils.getFile(channel.getOwnerProjectName(), channel);
        dataOut.writeUTF(resource.getFullPath().toString() + "/" + destination.getName());
    }
}