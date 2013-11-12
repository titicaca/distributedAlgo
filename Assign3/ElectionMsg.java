import java.awt.Color;

import teachnet.simulator.Message;
import teachnet.view.renderer.Shape;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Message being sent by the 3-Phase Election Algorithm
 */
public class ElectionMsg extends Message {

	public final static int EXPLORER = 1;
	public final static int CONTRACTION = 2;
	public final static int INFORM = 3;
	
	int type;
	int msg;
	
	Color color;
	Shape shape = Shape.RHOMBUS;
	
//	int id;
	
	public ElectionMsg(int type, int msg) {
		this.msg = msg;
		
		this.type = EXPLORER;
		this.shape = Shape.RECTANGLE;
		this.color = Color.YELLOW;
		
		if(type == CONTRACTION) {
			this.type = CONTRACTION;
			this.shape = Shape.RHOMBUS;
			this.color = Color.RED;
		}
		else if (type == INFORM) {
			this.type = INFORM;
			this.shape = Shape.CIRCLE;
			this.color = Color.GREEN;
		}
	}
	
	public boolean isExplorer() {
		return this.type == EXPLORER;
	}
	
	public boolean isInform() {
		return this.type == INFORM;
	}
	
	public boolean isContraction() {
		return this.type == CONTRACTION;
	}
	
	public int getType() {
		return this.type;
	}
	
	public int getMsg() {
		return this.msg;
	}
	
	public Color getColor() {
		return color;
	}

	public String toString() {
		if(this.type == EXPLORER)
			return "EXPLORER";
		else if(this.type == CONTRACTION)
			return "CONTRACTION "+this.msg;
		else if(this.type == INFORM)
			return "INFORM "+this.msg;
		else return "UNKNOWN";
	}
}
