import java.awt.Color;

import teachnet.algorithm.BasicAlgorithm;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of Distributed Deadlock Detection Algorithm in a logical ring
 */
public class DeadlockDetection extends BasicAlgorithm {

	public static final Color DEADLOCK_COLOR = Color.RED;
	public static final Color DONE_COLOR = Color.GREEN;
	
	Color color = null;
	String caption;
	
	boolean waiting;
	
	int id;
	
	int numProcesses;
	
	public void setup(java.util.Map<String, Object> config)
	{
		id = (Integer) config.get("node.id");
		numProcesses = (Integer) config.get("numProcesses");
		
		color = DEADLOCK_COLOR;
		caption = "" + id;
		
		waiting = true;
	}
	
	private void sendToNext(Object msg) {
		send(1, msg);
	}
	
	private void sendToPrev(Object msg) {
		send(0, msg);
	}
	
	@Override
	public void initiate() {
		this.sendToNext(new ProbeMessage(this.id, this.id, (this.id+1)%numProcesses));
	}

	@Override
	public void receive(int interf, Object msg) {
		if(msg instanceof ProbeMessage) {
			ProbeMessage probe = (ProbeMessage) msg;
			
			if(this.waiting) {
				if(probe.waitingProcess == this.id) {
					//probe message return to initiator, dead lock detected
					//resolve deadlock by releasing the resource waited by predecessor
					sendToPrev(this.id);
				}
				else {
					//pass the probe message onto successor
					probe.sender = this.id;
					probe.receiver = (this.id+1)%numProcesses;
					sendToNext(probe);
				}
			}
		}
		else if (msg instanceof Integer) {
			//receive resource
			if(this.waiting) {
				this.waiting = false;
				this.color = DONE_COLOR;
				sendToPrev(this.id);
			}
		}
	}

}
