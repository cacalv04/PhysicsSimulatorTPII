package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body{
	
	private Vector2D ace;

	public MovingBody(String ID, String GID, Vector2D pos, Vector2D speed, double mass) throws IllegalArgumentException {
		super(ID, GID, pos, speed, mass);
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
			pos = pos.plus(speed.scale(dt).plus(ace.scale(0.5 * dt *dt)));		//p + v*t + 0.5*dt*dt
			speed = speed.plus(ace.scale(dt));									//v + a*dt
	}
	
	public Vector2D getAce() {
		return ace;
	}
}
