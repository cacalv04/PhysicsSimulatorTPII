package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body{
	
	private Vector2D ace;

	public MovingBody(String ID, String GID, Vector2D speed, Vector2D pos, double mass) {
		super(GID, GID, speed, pos, mass);
	}

	@Override
	void advance(double dt) {
		// TODO Auto-generated method stub
		if(mass == 0) {
			ace = new Vector2D(0, 0);
		}
		else {
			
			ace = force.scale(1.0/mass);
			
		}
			pos = pos.plus(speed.scale(dt)).plus(ace.scale(0.5 * dt *dt));
	}
}