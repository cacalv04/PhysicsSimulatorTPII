package simulator.model;

import simulator.misc.Vector2D;

public class StationaryBody extends Body{

	public StationaryBody(String ID, String GID, Vector2D pos, double mass) throws IllegalArgumentException{
		super(ID, GID, pos, new Vector2D(0, 0), mass);
		// TODO Auto-generated constructor stub
	}

	@Override
	void advance(double dt) {
		// TODO Auto-generated method stub
		
	}

	
}
