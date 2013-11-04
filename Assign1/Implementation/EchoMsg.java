import java.awt.Color;

import teachnet.view.renderer.Shape;


public class EchoMsg {

	public final static int EXPLORER = 1;
	public final static int ECHO = 2;
	
	int type;
	
	Color color;
	Shape shape = Shape.RHOMBUS;
	
	static int counter = 0;
	int id;
	
	public EchoMsg(int type) {
		this.type = EXPLORER;
		this.shape = Shape.RHOMBUS;
		this.color = Color.RED;
		
		if(type == ECHO) {
			this.type = ECHO;
			this.shape = Shape.CIRCLE;
			this.color = Color.GREEN;
		}
	}
	
	public boolean isConfirmation() {
		return this.type == ECHO;
	}
	
	public Color getColor() {
		return color;
	}

	public String toString() {
		if(this.type == EXPLORER)
			return "Explorer";
		else
			return "Echo";
	}
}
