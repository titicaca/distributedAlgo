import teachnet.algorithm.BasicAlgorithm;
import java.awt.Color;

public class Flooding extends BasicAlgorithm {
	
	public static final Color INIT_COLOR = Color.WHITE;
	public static final Color INFORMED_COLOR = Color.RED;
	public static final Color COMPLETE_COLOR = Color.GREEN;
	
	int id;
	int count;
	boolean informed;
	int parent;
	
	Color color = null;
	String caption;
	
	private boolean isInitiator() {
		return id == 0;
	}
	
	private void setInformed() {
		this.informed = true;
		this.color = INFORMED_COLOR;
	}
	

	public void setup(java.util.Map<String, Object> config)
	{
		id = (Integer) config.get("node.id");
		if(this.isInitiator())
			caption = "Initiator";
		else
			caption = "" + id;
		color = INIT_COLOR;
	}
	
	@Override
	public void initiate() {
		informed = false;
		count = 0;
		parent = -1;
		
		if(this.isInitiator()) {
			this.setInformed();
			
			for(int i=0; i<this.checkInterfaces(); ++i) {
				send(i, new FloodingMsg(FloodingMsg.EXPLORER));
			}
		}
	}

	@Override
	public void receive(int interf, Object message) {
		
		FloodingMsg msg = (FloodingMsg)message;
		
		if(msg.isConfirmation()) {
			count++;
			if(!this.isInitiator() && count+1==this.checkInterfaces()) {
				send(parent, new FloodingMsg(FloodingMsg.CONFIRMATION) );
				done();
			}
			
			if(this.isInitiator() && count == this.checkInterfaces()) {
				done();
			}
		}
		else {
			if(!informed) {
				this.setInformed();
				this.parent = interf;
				for(int i=0; i<this.checkInterfaces(); ++i) {
					if(i != this.parent)
						send(i, new FloodingMsg(FloodingMsg.EXPLORER));
				}
			}
			else {
				send(interf, new FloodingMsg(FloodingMsg.CONFIRMATION));
			}
			
		}
	}
	
	public void done() {
		this.color = COMPLETE_COLOR;
	}

}
