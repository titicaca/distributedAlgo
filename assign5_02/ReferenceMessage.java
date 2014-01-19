import java.awt.Color;

import teachnet.simulator.Message;
import teachnet.view.renderer.Shape;


public class ReferenceMessage extends Message {
	public static final Color REQ_COLOR = Color.BLUE;
	public static final Color CONFIRM_COLOR = Color.ORANGE;
	public static final Color DEREF_COLOR = Color.RED;
	
	public static final int REQUEST = 1;
	public static final int DEREFERENCE = 2;
	public static final int CONFIRM = 3;
	
	int weight;
	int type;
	
	Color color = REQ_COLOR;
	Shape shape = Shape.CIRCLE;
	
	public ReferenceMessage(int type, int weight) {
		this.type = REQUEST;
		this.weight = weight;
		
		if(type == DEREFERENCE) {
			this.type = DEREFERENCE;
			this.color = DEREF_COLOR;
		}
		else if(type == CONFIRM) {
			this.type = CONFIRM;
			this.color = CONFIRM_COLOR;
		}
	}
	
	public boolean isReference() {
		return this.type == REQUEST;
	}
	
	public boolean isConfirm() {
		return this.type == CONFIRM;
	}
	
	public boolean isDereference() {
		return this.type == DEREFERENCE;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public String toString() {
		if(this.isReference()) 
			return "";
		else
			return ""+weight;
	}
	
	
}
