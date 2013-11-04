import java.awt.Color;
import teachnet.view.renderer.Shape;

public class FloodingMsg extends teachnet.simulator.Message{
	
	public final static int EXPLORER = 1;
	public final static int CONFIRMATION = 2;
	
	int type;
	
	Color color;
	Shape shape = Shape.RHOMBUS;
	
	static int counter = 0;
	int id;
	
	public FloodingMsg(int type) {
		this.type = EXPLORER;
		this.shape = Shape.RHOMBUS;
		this.color = Color.RED;
		
		if(type == CONFIRMATION) {
			this.type = CONFIRMATION;
			this.shape = Shape.CIRCLE;
			this.color = Color.GREEN;
		}
	}
	
	public boolean isConfirmation() {
		return this.type == CONFIRMATION;
	}
	
	public Color getColor() {
		return color;
	}

	public String toString() {
		if(this.type == EXPLORER)
			return "Explorer";
		else
			return "Confirmation";
	}

}
