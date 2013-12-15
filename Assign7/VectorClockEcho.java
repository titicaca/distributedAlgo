import java.awt.Color;

import teachnet.algorithm.BasicAlgorithm;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of a Vector Clock on Echo Algorithm
 */
public class VectorClockEcho extends BasicAlgorithm {

	public static final Color INIT_COLOR = Color.WHITE;
	public static final Color INFORMED_COLOR = Color.RED;
	public static final Color COMPLETE_COLOR = Color.GREEN;
	
	int id;
	int count = 0;
	boolean informed = false;
	boolean isInitiator = false;
	int parent = -1;
	
	int numProcesses = 0;
	int[] vector;
	
	Color color = null;
	int markInterface = -1;
	String caption;
	
	public void setup(java.util.Map<String, Object> config)
	{
		id = (Integer) config.get("node.id");
		numProcesses = (Integer) config.get("numProcesses");
		color = INIT_COLOR;
		vector = new int[numProcesses];
		for(int i=0; i<vector.length; ++i) {
			vector[i] = 0;
		}
		
		updateCaption();
	}
	
	@Override
	public void initiate() {
		this.isInitiator = true;
		this.setInformed(-1);

		for(int i=0; i<this.checkInterfaces(); ++i) {
			localSend(i, EchoMsg.EXPLORER);
		}
	}

	@Override
	public void receive(int interf, Object message) {
		
		EchoMsg msg = (EchoMsg)message;
		
		hitLocalEvent();
		updateVector(msg.getTimeStamp());

		if(!informed) {
			this.setInformed(interf);
			this.parent = interf;
			for(int i=0; i<this.checkInterfaces(); ++i) {
				if(i != this.parent)
					localSend(i, EchoMsg.EXPLORER);
			}
		}
		count++;
		if(count == this.checkInterfaces()) {
			if(!this.isInitiator) {
				localSend(parent, EchoMsg.ECHO);
			}
			this.done();
		}
	}
	
	private void setInformed(int markInterf) {
		this.informed = true;
		this.markInterface = markInterf;
		this.color = INFORMED_COLOR;
	}
	
	private void updateVector(int[] v) {
		for(int i=0; i<vector.length; ++i) {
			if(v[i]>vector[i]) {
				vector[i] = v[i];
			}
		}
		updateCaption();
	}
	
	private synchronized void localSend(int interf, int type) {
		hitLocalEvent();
		send(interf, new EchoMsg(type, vector));
		updateCaption();
	}
	
	private synchronized void hitLocalEvent() {
		vector[id]++;
	}
	
	private void updateCaption() {
		this.caption = "" + id + " [" + vector[0];
		for(int i=1; i<vector.length; ++i) {
			caption = caption + ","+ vector[i];
		}
		caption += "]";
	}
	
	public void done() {
		this.color = COMPLETE_COLOR;
	}

}
