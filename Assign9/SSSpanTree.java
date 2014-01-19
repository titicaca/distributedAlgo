import java.awt.Color;

import teachnet.algorithm.BasicAlgorithm;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of Self-Stabilizing Span Tree Algorithm
 */
public class SSSpanTree extends BasicAlgorithm {
	
	public static final Color ROOT_COLOR = Color.GREEN;
	public static final Color NORMAL_COLOR = Color.black;
	public static final Color FAULTY_COLOR = Color.RED;
	
	//Timeout value
	double RHO = 2.0;
	int failedPeriod = 4;
	volatile int deadCounter = 2;
	
	Color color = null;
	String caption;
	int markInterface = -1;
	
	int id;
	
	boolean isRoot = false;
	boolean isFaulty = false;
	volatile boolean silent = false;
	
	int root,level,father,counter;

	public void setup(java.util.Map<String, Object> config)
	{
		id = (Integer) config.get("node.id");
		caption = "" + id;
		
		int faulty = (Integer)config.get("failedNode");
		RHO = (Integer)config.get("rho");
		failedPeriod = (Integer)config.get("failedPeriod");
		deadCounter = failedPeriod;
		
		if(id == faulty) {
			isFaulty = true;
		}
		
		this.root = id;
		this.level = 1;
		this.father = id;
	}
	
	@Override
	public void initiate() {
		
		this.setTimeout(RHO, null);

	}
	
	private void setAsRoot() {
		this.isRoot = true;
		this.color = ROOT_COLOR;
	}
	
	private void notRoot() {
		this.isRoot = false;
		this.color = NORMAL_COLOR;
	}

	@Override
	public void receive(int interf, Object m) {
		TreeMessage msg = (TreeMessage)m;
		
		if(!this.silent) {
			if(this.lessThanMsg(msg.root, msg.level+1, msg.father) || this.id<msg.root) {
				//Ignores message
			}
			else if(this.equalMsg(msg.root, msg.level+1, msg.father)) {
				this.counter = 2;
				this.markInterface = interf;
				
				this.notRoot();
				for(int i=0; i<this.checkInterfaces(); ++i) {
					if(i != interf) {
						send(i, new TreeMessage(this.root, this.level, this.id));
					}
				}
			}
			else {
				this.root = msg.root;
				this.level = msg.level+1;
				this.father = msg.father;
				
				this.counter = 2;
				this.markInterface = interf;
	
				this.notRoot();
				for(int i=0; i<this.checkInterfaces(); ++i) {
					if(i != interf) {
						send(i, new TreeMessage(this.root, this.level, this.id));
					}
				}
			}
		}
		this.updateCaption();

	}
	
	private void updateCaption() {
		this.caption = "" + this.id + "->" + this.root + "," + this.level + "," + this.father;
	}
	
	@Override
	public void timeout(Object msg) {
		if(this.isFaulty) {
			deadCounter--;
			if(deadCounter>0 && this.silent) {
				this.setTimeout(RHO, null);
				return;
			}
			else if(deadCounter == 0) {
				deadCounter = failedPeriod;
				if(this.silent) {
					this.silent = false;
					this.color = NORMAL_COLOR;
				}
				else {
					this.silent = true;
					this.color = FAULTY_COLOR;
					this.markInterface = -1;
					
					this.setTimeout(RHO, null);
					return;
				}
			}
		}

		if (this.counter <= 0) {
			this.root = id;
			this.level = 1;
			this.father = id;

			this.setAsRoot();
			for (int i = 0; i < this.checkInterfaces(); ++i) {
				send(i, new TreeMessage(this.root, this.level, this.father));
			}
		} else if (this.counter == 1) {
			this.counter = 0;
		} else if (this.counter >= 2) {
			this.counter = 1;
		}
		this.setTimeout(RHO, null);
		/*
		if (!this.isFaulty || !this.initialDone) {
			this.initialDone = true;
			if (this.counter <= 0) {
				this.root = id;
				this.level = 1;
				this.father = id;

				this.setAsRoot();
				for (int i = 0; i < this.checkInterfaces(); ++i) {
					send(i, new TreeMessage(this.root, this.level, this.father));
				}
			} else if (this.counter == 1) {
				this.counter = 0;
			} else if (this.counter >= 2) {
				this.counter = 1;
			}
			if(this.isFaulty) 
				this.setTimeout(failedTime-RHO, null);
			else
				this.setTimeout(RHO, null);
		}
		else {
			if(this.silent) {
				this.silent = false;
				this.color = NORMAL_COLOR;
				if (this.counter <= 0) {
					this.root = id;
					this.level = 1;
					this.father = id;

					this.setAsRoot();
					for (int i = 0; i < this.checkInterfaces(); ++i) {
						send(i, new TreeMessage(this.root, this.level, this.father));
					}
				}
			}
			else {
				this.silent = true;
				this.color = FAULTY_COLOR;
				this.markInterface = -1;
			}
			this.setTimeout(failedTime, null);
		}*/
		this.updateCaption();

	}

	private boolean lessThanMsg(int r, int l, int f) {
		return (
				(this.root<r) 
				|| ((this.root==r) && (this.level<l))
				|| ((this.root==r) && (this.level==l) && (this.father<f))
				);
	}
	
	private boolean equalMsg(int r, int l, int f) {
		return (
				(this.root==r) 
				&& (this.level==l) 
				&& (this.father==f)
				);
	}

}
