import java.awt.Color;

import teachnet.algorithm.BasicAlgorithm;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of 3-Phase Election Algorithm for tree
 */
public class TreeElection extends BasicAlgorithm{
	
	public static final Color SLEEP_COLOR = Color.WHITE;
	public static final Color ACTIVE_COLOR = Color.YELLOW;
	public static final Color CONTRACTION_COLOR = Color.RED;
	public static final Color INFORMED_COLOR = Color.GREEN;
	
	int max = 0;
	int id;
	
	boolean isInitiator = false;
	boolean isLeaf = false;
	boolean isExplored = false;
	
	int remainContracts = 0;
	boolean[] contractRecord = null;
	
	Color color = null;
	String caption = "";
	int markInterface = -1;
	
	public void setup(java.util.Map<String, Object> config) {
		id = (Integer) config.get("node.id");		
		max = id;
		caption = "ID:" + id + " Max:" + max;
		color = SLEEP_COLOR;
		
		if(this.checkInterfaces() == 1) {
			this.isLeaf = true;
		}
		
		this.remainContracts = this.checkInterfaces();
		this.contractRecord = new boolean[this.remainContracts];
		for(int i=0; i<this.remainContracts; ++i) {
			this.contractRecord[i] = false;
		}
	}
	
	@Override
	public void initiate() {
		color = ACTIVE_COLOR;
		caption = "ID:" + id + " Max:" + max + "(Initiator)";
		this.isInitiator = true;
		this.isExplored = true;
		
		this.sendToAll(new ElectionMsg(ElectionMsg.EXPLORER, id));
		
		if(this.isLeaf) {
			this.setContracted();
			this.sendToAll(new ElectionMsg(ElectionMsg.CONTRACTION, id));
		}
	}
	
	@Override
	public void receive(int interf, Object arg1) {
		ElectionMsg msg = (ElectionMsg) arg1;
		int elected = msg.getMsg();
		
		//Explorer Phase
		if(msg.isExplorer()) {

			if(!this.isExplored) {
				this.setExplored();
				this.sendToAllExcept(interf, msg);			
				//a leaf answer an explorer with a contraction
				if(this.isLeaf) {
					this.setContracted();
					send(interf, new ElectionMsg(ElectionMsg.CONTRACTION, this.id));
				}
			}			
		}
		//Contraction Phase
		else if(msg.isContraction()) {
			this.setContracted();
			if(this.max < elected) {
				this.setMax(elected);
			}
			this.remainContracts--;
			this.contractRecord[interf] = true;
			if(this.remainContracts == 1) {
				for(int i=0; i<this.checkInterfaces(); ++i) {
					if(!this.contractRecord[i]) {
						send(i, new ElectionMsg(ElectionMsg.CONTRACTION, max));
						break;
					}
				}
			}
			//two contractions meet on one edge
			else if(this.remainContracts == 0) {
				this.setInformed();
				this.markInterface = interf;
				this.sendToAllExcept(interf, new ElectionMsg(ElectionMsg.INFORM, this.max));
			}
		}
		//Informing Phase
		else if(msg.isInform()) {
			this.setMax(elected);
			this.setInformed();
			this.sendToAllExcept(interf, msg);
		}
	}
	
	private void sendToAll(ElectionMsg msg) {
		for(int i=0; i<this.checkInterfaces(); ++i) {
			send(i, msg);
		}
	}
	
	private void sendToAllExcept(int except, ElectionMsg msg) {
		for(int i=0; i<this.checkInterfaces(); ++i) {
			if(i != except)
				send(i, msg);
		}
	}
	

	private void setExplored() {
		if(this.remainContracts != this.checkInterfaces()) return;
		this.isExplored = true;
		this.color = ACTIVE_COLOR;
	}
	
	private void setContracted() {
		this.color = CONTRACTION_COLOR;
	}
	
	private void setMax(int m) {
		this.max = m;
		this.caption = "ID:"+id+" Max:"+max;
	}
	
	private void setInformed() {
		this.color = INFORMED_COLOR;
	}
}
