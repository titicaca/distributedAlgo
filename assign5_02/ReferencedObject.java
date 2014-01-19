import java.util.ArrayList;
import java.util.List;

/**
 * Group 11
 * Yuwen Chen,    352038
 * Fangzhou Yang, 352040
 * Xugang Zhou,   352032
 * 
 * Implementation of Referenced Object for Weighted Reference Counting
 */
public class ReferencedObject {
	private List<Integer> weights;
	
	public ReferencedObject() {
		this.weights = new ArrayList<Integer>();
	}
	
	public int addReference() {
		if(this.weights.isEmpty()) {
			this.weights.add(1);
			return 1;
		}
		else {
			int removed = this.weights.remove(0);
			removed++;
			this.weights.add(removed);
			return removed;
		}
	}
	
	public void removeReference(int returnWeight) {
		if(this.weights.isEmpty() || !this.weights.contains(returnWeight)) {
			this.weights.add(returnWeight);
			return;
		}
		while(this.weights.contains(returnWeight)) {
			this.weights.remove((Object)returnWeight);
			returnWeight--;
		}
		if(returnWeight > 0)
			this.weights.add(returnWeight);
	}
	
	public boolean collectable() {
		return this.weights.isEmpty();
	}
	
	public String toString() {
		String result = "";
		if(!this.weights.isEmpty()) {
			result += "Weights:";
			for(int itr: this.weights) {
//				if(itr != this.currentWeight)
					result = result + " "+itr;
			}
		}
		return result;
	}
}
