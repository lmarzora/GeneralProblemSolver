package ar.edu.itba.sia.gps;

import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;

import java.util.ArrayList;
import java.util.List;

public class GPSNode {

	private GPSState state;
	private GPSNode parent;
	private Integer height;
	private Integer cost;
	private GPSRule generationRule;
	private String id;
	private static long currentId;

	public GPSNode(final GPSState state) {
		this.state = state;
		this.height = 0;
		this.cost = 0;
		this.generationRule = null;
		this.id = String.valueOf(currentId++);
	}

	public GPSNode(final GPSState state, final GPSNode parent, final GPSRule rule) {
		this(state);
		this.parent = parent;
		this.height = parent.height + 1;
		this.cost = parent.cost + rule.getCost();
		this.generationRule = rule;
	}

	public String getSolution() {
		if (this.parent == null) {
			return this.state.toString();
		}
		return this.parent.getSolution() + this.state.toString();
	}

	public List<GPSRule> getPath() {
		if(this.height == 0)
			return new ArrayList<>();

		List<GPSRule> path = this.parent.getPath();
		path.add(generationRule);
		return path;
	}

	/*
	*
	* 	Getters & Setters.
	*
	* */

	public Integer getCost() {
		return cost;
	}

	public GPSNode getParent() {return parent;}

	public GPSState getState() {
		return state;
	}

	public Integer getHeight() {
		return height;
	}

	public String getId() {
		return id;
	}

	public GPSRule getGenerationRule() { return generationRule; }

	@Override
	public String toString() {
		return state.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GPSNode)) return false;
		GPSNode gpsNode = (GPSNode) o;
		return getState().equals(gpsNode.getState());
	}

	@Override
	public int hashCode() {
		int result = getState() != null ? getState().hashCode() : 0;
		result = 31 * result + (getParent() != null ? getParent().hashCode() : 0);
		result = 31 * result + (getHeight() != null ? getHeight().hashCode() : 0);
		result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
		result = 31 * result + (generationRule != null ? generationRule.hashCode() : 0);
		return result;
	}
}
