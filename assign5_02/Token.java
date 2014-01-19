import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import teachnet.simulator.Message;
import teachnet.view.renderer.Shape;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of Token
 */
public class Token extends Message {
	

	Color color = Color.ORANGE;
	Shape shape = Shape.CIRCLE;
	
	public int numProcesses = 0;
	public int[] L;
	public List<Integer> Q;
	
	public Token(int numProcesses) {
		this.numProcesses = numProcesses;
		
		this.L = new int[this.numProcesses];
		for(int i=0; i<this.numProcesses; ++i) {
			L[i] = 0;
		}
		
		this.Q = new ArrayList<Integer>();
	}
	
	public Token(int numProcesses, int[] l, List<Integer> q) {
		this.numProcesses = numProcesses;
		
		this.L = Arrays.copyOf(l, numProcesses);
		this.Q = new ArrayList<Integer>();
		this.Q.addAll(q);
	}
	
	public Token getClone() {
		return new Token(this.numProcesses, this.L, this.Q);
	}
	
	public boolean containRequest(int pId) {
		return this.Q.contains((Object)pId);
	}
	
	
	public void updateQ(int[] R) {
		for(int i=0; i<L.length && i<R.length; ++i) {
			if(R[i] > L[i]) {
				if(!this.containRequest(i))
					this.Q.add(i);
			}
		}
	}
	
	public boolean hasNextRequest() {
		return !this.Q.isEmpty();
	}
	
	public Color getColor() {
		return color;
	}

	public String toString() {
		String result = "";
		if(Q.isEmpty()) {
			result = "Q:null  L:";
		}
		else {
			result = "Q: ";
			for(int i : Q) {
				result = result + i + ",";
			}
			result += "  L: ";
		}
		
		for(int i=0; i<L.length; ++i) {
			result = result + "(" + i + ","+ L[i]+ ") ";
		}
		return result;
	}
}
