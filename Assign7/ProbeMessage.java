import java.awt.Color;

import teachnet.simulator.Message;
import teachnet.view.renderer.Shape;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of Probe Message in Distributed Deadlock Detection
 */

public class ProbeMessage extends Message {
	
	Color color = Color.ORANGE;
	Shape shape = Shape.CIRCLE;
	
	public int waitingProcess;
	public int sender;
	public int receiver;
	
	public ProbeMessage(int w, int s, int r) {
		this.waitingProcess = w;
		this.sender = s;
		this.receiver = r;
	}
	
	public int getWaitingProcess() {
		return this.waitingProcess;
	}
	
	public int getSender() {
		return this.sender;
	}
	
	public int getReceiver() {
		return this.receiver;
	}
	
	public String toString() {
		return "("+this.waitingProcess+","+this.sender+","+this.receiver+")";
	}

}
