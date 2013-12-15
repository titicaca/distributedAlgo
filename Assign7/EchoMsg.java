import java.awt.Color;

import teachnet.view.renderer.Shape;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of echo messages with a vector clock time stamp
 */
public class EchoMsg {

	public final static int EXPLORER = 1;
	public final static int ECHO = 2;
	
	int type;
	
	Color color;
	Shape shape = Shape.RHOMBUS;
	
	static int counter = 0;
	int id;
	int[] stamp;
	
	public EchoMsg(int type, int[] stamp) {
		this.type = EXPLORER;
		this.shape = Shape.RHOMBUS;
		this.color = Color.RED;
		
		if(type == ECHO) {
			this.type = ECHO;
			this.shape = Shape.CIRCLE;
			this.color = Color.GREEN;
		}
		
		this.stamp = stamp;
	}
	
	public boolean isConfirmation() {
		return this.type == ECHO;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int[] getTimeStamp() {
		return this.stamp;
	}

	public String toString() {
		String result = "";
		if(this.type == EXPLORER) {
			result = "Explorer ["+stamp[0];
			for(int i=1; i<stamp.length; ++i) {
				result = result + ","+stamp[i]; 
			}
			result += "]";
		}
		else {
			result = "Echo ["+stamp[0];
			for(int i=1; i<stamp.length; ++i) {
				result = result + ","+stamp[i]; 
			}
			result += "]";
		}
		return result;
	}
}
