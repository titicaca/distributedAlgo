import java.awt.Color;

import teachnet.simulator.Message;
import teachnet.view.renderer.Shape;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Messages used in 2-Phase Commit distributed transaction algorithm
 */

public class CommitMessage extends Message {

	public final static int INITIAL = 0;
	public final static int COMMIT_COMMAND = 1;
	public final static int ABORT_COMMAND = 2;
	public final static int COMMIT_VOTE = 3;
	public final static int ABORT_VOTE = 4;
	public final static int COMMIT_PREPARE = 5;
	public final static int ABORT_PREPARE = 6;
	
	public int type;
	
	Color color = Color.ORANGE;
	Shape shape = Shape.CIRCLE;
	
	public CommitMessage(int type) {
		this.type = type;
		switch(type) {
		case INITIAL:
			break;
		case COMMIT_COMMAND:
			this.shape = Shape.RECTANGLE;
			this.color = Color.green;
			break;
		case COMMIT_VOTE:
			this.shape = Shape.CIRCLE;
			this.color = Color.green;
			break;
		case COMMIT_PREPARE:
			this.shape = Shape.RHOMBUS;
			this.color = Color.green;
			break;
		case ABORT_COMMAND:
			this.shape = Shape.RECTANGLE;
			this.color = Color.red;
			break;
		case ABORT_VOTE:
			this.shape = Shape.CIRCLE;
			this.color = Color.red;
			break;
		case ABORT_PREPARE:
			this.shape = Shape.RHOMBUS;
			this.color = Color.red;
			break;
		}
	}
	
	public String toString() {

		switch(type) {
		case INITIAL:
			return "Initial";
		case COMMIT_COMMAND:
			return "Commit Command";
		case COMMIT_VOTE:
			return "Vote Commit";
		case COMMIT_PREPARE:
			return "Commit Prepare";
			
		case ABORT_COMMAND:
			return "Abort Command";
		case ABORT_VOTE:
			return "Vote Abort";
		case ABORT_PREPARE:
			return "Abort Prepare";
		}
		return "";
	}
}
