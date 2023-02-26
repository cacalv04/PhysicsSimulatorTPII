package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	private double g;

	public NewtonUniversalGravitation(double g){
		this.g = g;
		
		try {
			validArguments(g);
			this.g = g;
		}catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	@Override
	public void apply(List<Body> bs) {
		Vector2D fuerza = new Vector2D(0, 0);
		double aux;
		
		for(Body b1 : bs) {
			if(b1.getMass() == 0.0) {
				//REVISAR
				b1.setAcceleration(fuerza);
				b1.setVelocity(fuerza);
			}
			else {
				//CALCULAMOS FUERZA
				for(Body b2: bs) {
					if(!b2.equals(b1) && b2.getPosition().distanceTo(b1.getPosition()) > 0){
						aux = (b1.getMass() * b2.getMass()) / (b2.getPosition().distanceTo(b1.getPosition()) * b2.getPosition().distanceTo(b1.getPosition()));
						fuerza = fuerza.plus(b2.getPosition().minus(b1.getPosition()).direction().scale(aux));
						b2.setAcceleration(fuerza.scale(1 / b1.getMass()));
					}
				}
			}
		}
	}
	
	private void validArguments(double g) {
		if(g < 0 ) throw new IllegalArgumentException("[ERROR]: an argument value is null or negative");
	}
}
