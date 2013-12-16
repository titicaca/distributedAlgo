import java.awt.Color;

import teachnet.algorithm.BasicAlgorithm;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of Weighted Reference Counting Algorithm
 */
public class WeightedReferenceCount extends BasicAlgorithm {


	public static final Color INIT_COLOR = Color.WHITE;
	public static final Color REF_COLOR = Color.BLUE;
	public static final Color OBJ_COLOR = Color.ORANGE;
	
	public static final int MAX_SLEEP_TIME = 10;
	
	Color color = null;
	String caption;
	
	int id;
	int weight = 0;
	int cnt = 0;
	
	ReferencedObject object = null;
	boolean hasReference = false;
	
	public void setup(java.util.Map<String, Object> config)
	{
		id = (Integer) config.get("node.id");
		color = INIT_COLOR;
		
		//node 0 holds the token initially
		if(id == 0) {
			object = new ReferencedObject();
		}
		
		this.updateState();
	}
	
	private void updateState() {
		if(this.object != null) {
			if(this.object.collectable()) {
				this.caption = "Collectable!";
				this.color = Color.GREEN;
			}
			else {
				this.caption = this.object.toString();
				this.color = OBJ_COLOR;
			}
		}
		else {
			if(this.hasReference) {
				this.caption = ""+this.weight;
				this.color = REF_COLOR;
			}
			else {
				this.caption = "";
				this.color = INIT_COLOR;
			}
		}
	}
	
	/**
	 * A Request Generator
	 * The trick is to wait for a Null message object which will never come.
	 * When timeout, generate a new request and reset the wait time to a new random number.
	 * 
	 * @param msg
	 */
	@Override
	public void timeout(Object msg) {
		if(this.object != null)
			return;
		if(cnt == 2)
			return;
		
		if(this.hasReference) {
			if(this.weight>0) {
				for(int i=0; i<this.checkInterfaces(); ++i)
					send(i, new ReferenceMessage(ReferenceMessage.DEREFERENCE, this.weight));
				this.weight = 0;
				this.hasReference = false;
				this.updateState();
	
				cnt++;
			}
			else {
				long sleepTime = this.getRandom().nextInt(MAX_SLEEP_TIME);
				this.setTimeout(sleepTime, null);
			}
		}
		else {
			for(int i=0; i<this.checkInterfaces(); ++i)
				send(i, new ReferenceMessage(ReferenceMessage.REQUEST, 0));
			this.hasReference = true;
			
			long sleepTime = this.getRandom().nextInt(MAX_SLEEP_TIME);
			this.setTimeout(sleepTime, null);

			cnt++;
		}

	}

	@Override
	public void initiate() {
		if(this.object != null)
			return;
		long sleepTime = this.getRandom().nextInt(MAX_SLEEP_TIME);
		this.setTimeout(sleepTime, null);
	}

	@Override
	public void receive(int arg0, Object arg1) {
		ReferenceMessage msg = (ReferenceMessage)arg1;
		
		if(this.object == null) {
			//remote entity
			if(msg.isConfirm()) {
				this.weight = msg.getWeight();
			}
		}
		else {
			//object holder
			if(msg.isReference()) {
				send(arg0, new ReferenceMessage(ReferenceMessage.CONFIRM, object.addReference()));
			}
			if(msg.isDereference()) {
				this.object.removeReference(msg.getWeight());
			}
		}
		this.updateState();
	}

}
