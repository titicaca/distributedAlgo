import java.awt.Color;

import teachnet.algorithm.BasicAlgorithm;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of 2-Phase Commit distributed transaction algorithm
 */

public class TwoPhaseCommit extends BasicAlgorithm {
	
	private static final int TIMEOUT = 2;
	
	private static final Color COORDINATOR_COLOR = Color.YELLOW;
	private static final Color COMMIT_COLOR = Color.GREEN;
	private static final Color ABORT_COLOR = Color.RED;
	
	int id;
	String caption;
	Color color = null;
	
	int numProcesses;
	
	boolean isCoordinator = false;
	boolean isInitiator = false;
	
	int stage = 0;
	
	int commitCnt = 0;
	int abortCnt = 0;
	
	public void setup(java.util.Map<String, Object> config)
	{
		id = (Integer) config.get("node.id");
		numProcesses = (Integer) config.get("numProcesses");
		
		caption = "" + id;
		color = Color.white;
		
		if(id == 0) {
			color = COORDINATOR_COLOR;
			isCoordinator = true;
		}
	}

	@Override
	public void initiate() {
		isInitiator = true;
		sendToAll(new CommitMessage(CommitMessage.COMMIT_COMMAND));
	}
	
	private void sendToAll(CommitMessage msg) {
		for(int i=0; i<this.checkInterfaces(); ++i) {
			send(i, msg);
		}
	}
	
	@Override
	public void timeout(Object msg) {
		this.color = Color.white;
		if(!isInitiator) {
			return;
		}
		if(stage%3 == 2) {
			sendToAll(new CommitMessage(CommitMessage.ABORT_COMMAND));
		}
		else {
			sendToAll(new CommitMessage(CommitMessage.COMMIT_COMMAND));
		}
	}

	@Override
	public void receive(int interf, Object arg1) {
		CommitMessage msg = (CommitMessage)arg1;
		
		if(this.isCoordinator) {
			switch(msg.type) {
			case CommitMessage.COMMIT_COMMAND:
				sendToAll(new CommitMessage(CommitMessage.INITIAL));
				break;
			case CommitMessage.ABORT_COMMAND:
				sendToAll(new CommitMessage(CommitMessage.ABORT_PREPARE));
				break;
			case CommitMessage.COMMIT_VOTE:
				commitCnt++;
				if(commitCnt == numProcesses-1) {
					commitCnt = 0;
					abortCnt = 0;
					sendToAll(new CommitMessage(CommitMessage.COMMIT_PREPARE));
				}
				else if(commitCnt+abortCnt == numProcesses-1) {
					commitCnt = 0;
					abortCnt = 0;
					sendToAll(new CommitMessage(CommitMessage.ABORT_PREPARE));
				}
				break;
			case CommitMessage.ABORT_VOTE:
				abortCnt++;
				if(commitCnt+abortCnt == numProcesses-1) {
					commitCnt = 0;
					abortCnt = 0;
					sendToAll(new CommitMessage(CommitMessage.ABORT_PREPARE));
				}
				break;
			}
		}
		else {
			switch(msg.type) {
			case CommitMessage.INITIAL:
				this.color = Color.white;
				if(isInitiator && stage%3 == 1) {
					sendToAll(new CommitMessage(CommitMessage.ABORT_VOTE));
				}
				else {
					sendToAll(new CommitMessage(CommitMessage.COMMIT_VOTE));
				}
				break;
			case CommitMessage.COMMIT_PREPARE:
				this.color = COMMIT_COLOR;
				stage++;
				this.setTimeout(TIMEOUT, null);
				break;
			case CommitMessage.ABORT_PREPARE:
				this.color = ABORT_COLOR;
				stage++;
				this.setTimeout(TIMEOUT, null);
				break;
			}
			
		}
	}

}
