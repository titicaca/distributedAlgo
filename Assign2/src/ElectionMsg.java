import java.awt.Color;

import teachnet.simulator.Message;
import teachnet.view.renderer.Shape;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Message being sent by the Election Algorithm
 */
public class ElectionMsg extends Message {

	public final static int ELECTION = 1;
	public final static int NOTIFICATION = 2;
	
	int type;
	int msg;
	
	Color color;
	Shape shape = Shape.RHOMBUS;
	
	int id;
	
	public ElectionMsg(int type, int msg) {
		this.msg = msg;
		
		this.type = ELECTION;
		this.shape = Shape.RHOMBUS;
		this.color = Color.RED;
		
		if(type == NOTIFICATION) {
			this.type = NOTIFICATION;
			this.shape = Shape.CIRCLE;
			this.color = Color.GREEN;
		}
	}
	
	public boolean isNotification() {
		return this.type == NOTIFICATION;
	}
	
	public int getMsg() {
		return this.msg;
	}
	
	public Color getColor() {
		return color;
	}

	public String toString() {
		if(this.type == ELECTION)
			return "ELECT "+this.msg;
		else
			return "NOTI "+this.msg;
	}
}
