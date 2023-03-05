package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MovingTowardsFixedPoint createInstance(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		double g;
		Vector2D c = null;
		String data = null;
		
		if(jsonObject.has("g")) { 		//se comprueba que traiga la clave g, si no se usa el valor predeterminado
			g = jsonObject.getDouble("g");
		}
		else{ 
			g = 9.81;
		}
		
		if(jsonObject.has("c")) {    	//se comprueba que traiga la clave c, si no se usa el valor predeterminado
			data = jsonObject.getString("c");	
			
			String[] datos2 = data.replace(" ", "").replace("[", "").replace("]", "").split(",");
			c = new Vector2D(Double.parseDouble(datos2[0]), Double.parseDouble(datos2[1]));
			
		}
		else {
			c = new Vector2D(0, 0);
		}
		
	    return new MovingTowardsFixedPoint(c, g);
	}

	

}
