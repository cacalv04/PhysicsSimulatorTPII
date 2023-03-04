package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	private double g;

	public NewtonUniversalGravitation(double g){
		this.g = g;
		
		try {
			argumentsCheck(g);
			this.g = g;
		}catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	@Override
	public void apply(List<Body> bs) {
		
		for(Body b1 : bs) {
			for(Body b2: bs) {
				if (b1!=b2) {
					b1.addForce(force(b1, b2));
				}
			}
		}
	}
	
	private Vector2D force(Body a, Body b) {
		Vector2D delta = b.getPosition().minus(a.getPosition());
		double dist = delta.magnitude();
		double magnitude = dist>0 ? (g * a.getMass() * b.getMass()) / (dist * dist) : 0.0;
		return delta.direction().scale(magnitude);
	}
	
	public String toString() {
		return "Newton's Universal Gravitation with G = " + g;
	}
	
	private void argumentsCheck(double g) {
		if(g <= 0 ) throw new IllegalArgumentException("[ERROR]: an argument value is null or negative");
	}
}
