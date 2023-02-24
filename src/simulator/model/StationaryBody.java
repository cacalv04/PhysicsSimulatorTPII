package simulator.model;

import simulator.misc.Vector2D;

public class StationaryBody extends Body{

	public StationaryBody(String ID, String gID, Vector2D pos, double mass) {
		super(ID, gID, new Vector2D(0, 0), pos, new Vector2D(0, 0), mass);
	}

	@Override
	void advance(double dt) {}
}
