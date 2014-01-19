import java.awt.Color;

import teachnet.algorithm.BasicAlgorithm;
import teachnet.simulator.Message;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of Improved Token Ring Algorithm
 */
public class ImprovedTokenRing extends BasicAlgorithm {
	
	public static final Color INIT_COLOR = Color.WHITE;
	public static final Color REQUEST_COLOR = Color.BLUE;
	public static final Color TOKEN_COLOR = Color.ORANGE;
	
	public static final int MAX_SLEEP_TIME = 5;
	
	Color color = null;
	String caption;
	
	int id;
	int numProcesses = 0;
	volatile int seqNum = 0;
	int maxRequestCnt = 0;
	
	int[] R;
	
	Token token = null;
	boolean hasToken = false;
	boolean hasRequest = false;
	
	public void setup(java.util.Map<String, Object> config)
	{
		id = (Integer) config.get("node.id");
		numProcesses = (Integer) config.get("numProcesses");
		color = INIT_COLOR;
		R = new int[numProcesses];
		for(int i=0; i<numProcesses;++i)
			R[i] = 0;
		
		//limit the number of requests each node allowed to generate
		this.maxRequestCnt = this.getRandom().nextInt(4);

		this.updateCaption();
		
		//node 0 holds the token initially
		if(id == 0) {
			token = new Token(numProcesses);
			this.getToken(token);
		}
	}
	
	private void getToken(Token t) {
		this.hasToken = true;
		this.token = t;
		this.color = TOKEN_COLOR;
	}
	
	private Token releaseToken() {
		this.hasToken = false;
		this.token.updateQ(this.R);
		if(this.hasRequest)
			this.color = REQUEST_COLOR;
		else
			this.color = INIT_COLOR;
		Token result = this.token;
		this.token = null;
		return result;
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
		if(!hasToken && !hasRequest && seqNum < this.maxRequestCnt) {
			seqNum ++;
		
			this.sendNewRequest(seqNum);
		
		}
//		if(seqNum < this.maxRequestCnt) {
			long sleepTime = this.getRandom().nextInt(MAX_SLEEP_TIME);
			this.setTimeout(sleepTime, null);
//		}
	}

	@Override
	public void initiate() {
		
		//start generating requests
		if(!hasToken && !hasRequest && seqNum < this.maxRequestCnt) {
			int sleepTime = this.getRandom().nextInt(MAX_SLEEP_TIME);
			this.setTimeout(sleepTime, null);
		}
	}

	@Override
	public void receive(int interf, Object arg1) {
		if(arg1 instanceof Token) {
			this.getToken((Token)arg1);
			
			if(this.hasRequest) {
				this.setServed();
				token.L[this.id] = this.R[this.id];
				token.Q.remove((Object)this.id);
			}
			
			token.updateQ(this.R);

			if(token.hasNextRequest())
				sendToNext(this.releaseToken());
		}
		else if(arg1 instanceof TokenRequest) {
			TokenRequest req = (TokenRequest) arg1;
			
			//R[i] is the largest sequence number of P[i]
			if(R[req.getProcessId()] < req.getSequenceNumber())
				R[req.getProcessId()] = req.getSequenceNumber();
			
			
			//broadcast request
			if(req.getProcessId() != id)
				sendToNext(req);
			
			//release token
			if(this.hasToken) {
				sendToNext(this.releaseToken());
			}
		}
		else {
			System.out.println("Invalid Message Type!");
		}
		this.updateCaption();
	}
	
	/**
	 * Send a message to successor
	 * Here the fixed interface 1 is assumed to be the path to next node (while 0 for previous node)
	 * @param msg
	 */
	private void sendToNext(Message msg) {
		send(1, msg);
	}
	
	private void sendNewRequest(int seq) {
		R[id] = seq;
		sendToNext(new TokenRequest(id, seq));
		color = REQUEST_COLOR;
		this.hasRequest = true;
		this.updateCaption();
	}
	
	private void setServed() {
		this.hasRequest = false;
		this.color = TOKEN_COLOR;
		this.updateCaption();
	}
	
	private void updateCaption() {
		caption = ""+id+":\n";
		for(int i=0; i<R.length; ++i) {
			caption = caption + "(" + i + ","+ R[i]+ ")\n";
		}
	}
}
