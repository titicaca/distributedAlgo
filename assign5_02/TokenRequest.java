import java.awt.Color;

import teachnet.simulator.Message;
import teachnet.view.renderer.Shape;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of a Token Request
 */
public class TokenRequest extends Message {

	Color color = Color.BLUE;
	Shape shape = Shape.RHOMBUS;
	
	int processId;
	int seq;
	
	public TokenRequest(int p, int seq) {
		this.processId = p;
		this.seq = seq;
	}
	
	public int getProcessId() {
		return this.processId;
	}
	
	public int getSequenceNumber() {
		return this.seq;
	}
	
	public Color getColor() {
		return color;
	}

	public String toString() {
		return "pid:"+ this.processId + ", seq:" + this.seq;
	}

}
