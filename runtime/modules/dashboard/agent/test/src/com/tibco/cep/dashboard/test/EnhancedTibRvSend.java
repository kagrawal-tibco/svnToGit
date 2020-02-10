package com.tibco.cep.dashboard.test;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvRvdTransport;
import com.tibco.tibrv.TibrvTransport;

public class EnhancedTibRvSend {

	String service = null;
	String network = null;
	String daemon = null;

	String FIELD_NAME = "DATA";

	public EnhancedTibRvSend(String args[]) {
		// parse arguments for possible optional
		// parameters. These must precede the subject
		// and message strings
		int i = get_InitParams(args);

		// we must have at least one subject and one message
		if (i > args.length - 2)
			usage();

		// open Tibrv in native implementation
		try {
			Tibrv.open(Tibrv.IMPL_NATIVE);
		} catch (TibrvException e) {
			System.err.println("Failed to open Tibrv in native implementation:");
			e.printStackTrace();
			System.exit(0);
		}

		// Create RVD transport
		TibrvTransport transport = null;
		try {
			transport = new TibrvRvdTransport(service, network, daemon);
		} catch (TibrvException e) {
			System.err.println("Failed to create TibrvRvdTransport:");
			e.printStackTrace();
			System.exit(0);
		}

		// Create the message
		TibrvMsg msg = new TibrvMsg();

		// Set send subject into the message
		try {
			msg.setSendSubject(args[i++]);
		} catch (TibrvException e) {
			System.err.println("Failed to set send subject:");
			e.printStackTrace();
			System.exit(0);
		}

		try {
			// Send one message for each parameter
			while (i < args.length) {
				String[] split = args[i].split(":");
				if (split.length == 2){
					try {
						msg.add(split[0], Double.parseDouble(split[1]));
					} catch (NumberFormatException e) {
						msg.add(split[0], split[1]);
					}
				}
				else {
					msg.update(FIELD_NAME, args[i]);
				}
				i++;
			}
			System.out.println("Publishing: subject=" + msg.getSendSubject() + " \"" + msg + "\"");				
			transport.send(msg);			
		} catch (TibrvException e) {
			System.err.println("Error sending a message:");
			e.printStackTrace();
			System.exit(0);
		}

		// Close Tibrv, it will cleanup all underlying memory, destroy
		// transport and guarantee delivery.
		try {
			Tibrv.close();
		} catch (TibrvException e) {
			System.err.println("Exception dispatching default queue:");
			e.printStackTrace();
			System.exit(0);
		}

	}

	// print usage information and quit
	void usage() {
		System.err.println("Usage: java EnhancedTibRvSend [-service service] [-network network]");
		System.err.println("            [-daemon daemon] <subject> <messages>");
		System.exit(-1);
	}

	int get_InitParams(String[] args) {
		int i = 0;
		while (i < args.length - 1 && args[i].startsWith("-")) {
			if (args[i].equals("-service")) {
				service = args[i + 1];
				i += 2;
			} else if (args[i].equals("-network")) {
				network = args[i + 1];
				i += 2;
			} else if (args[i].equals("-daemon")) {
				daemon = args[i + 1];
				i += 2;
			} else
				usage();
		}
		return i;
	}

	public static void main(String args[]) {
		new EnhancedTibRvSend(args);
	}

}
