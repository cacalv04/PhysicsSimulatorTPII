package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	
	private Vector2D vP;
	private double g;

	public MovingTowardsFixedPoint(Vector2D c, double g) {
		
		try {
			argumentsCheck(c, g);
			vP = new Vector2D(c);
			this.vP = new Vector2D(c);
			this.g = g;
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	@Override
	public void apply(List<Body> bs) {
		// TODO Auto-generated method stub
		for(Body b : bs) {
			b.addForce(vP.minus(b.getPosition()).direction().scale(g * b.getMass()));	//m*g*(c - d)
		}
	}
	
	private void argumentsCheck(Vector2D c, double g) {
		if(c == null || g <= 0) throw new IllegalArgumentException("[ERROR]: Invalid arguments for MTFP force law");
	}

}
