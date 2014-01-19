import java.awt.Color;

import teachnet.simulator.Message;
import teachnet.view.renderer.Shape;


public class TreeMessage extends Message {
	
	Color color = Color.ORANGE;
	Shape shape = Shape.CIRCLE;
	
	public int root,level,father;
	
	public TreeMessage(int r, int l, int f) {
		root = r;
		level = l;
		father = f;
	}
	
	public String toString() {
		return "("+this.root+","+this.level+","+this.father+")";
	}

}
