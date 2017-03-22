package ar.edu.itba.sia.gps;

import ar.edu.itba.sia.gps.api.GPSState;

public class GPSNode {

	private GPSState state;

	private GPSNode parent;

	private Integer height;

	private Integer cost;

	public GPSNode(GPSState state) {
		this.state = state;
		this.height = 0;
		this.cost = 0;
	}

	public GPSNode(GPSState state, GPSNode parent, Integer cost) {
		this(state);
		this.parent = parent;
		this.height = parent.height + 1;
		this.cost = parent.cost + cost;
	}

	public Integer getCost() {
		return cost;
	}

	public GPSNode getParent() {
		return parent;
	}


	public GPSState getState() {
		return state;
	}

	public Integer getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return state.toString();
	}

	public String getSolution() {
		if (this.parent == null) {
			return this.state.toString();
		}
		return this.parent.getSolution() + this.state.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GPSNode other = (GPSNode) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

}
