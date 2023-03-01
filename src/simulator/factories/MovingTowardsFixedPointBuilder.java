package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws >{

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ForceLaws createInstance(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		double g;
		Vector2D origen = null;
		String data = null;
		
		if(jsonObject.has("g")) {
			g = jsonObject.getDouble("g");
		}
		else{ 
			g = 9.81;
		}
		
		if(jsonObject.has("c")) {    
			data = jsonObject.getString("c");	
			
			String[] datos2 = data.replace(" ", "").replace("[", "").replace("]", "").split(",");
			origen = new Vector2D(Double.parseDouble(datos2[0]), Double.parseDouble(datos2[1]));
			
		}
		else {
			origen = new Vector2D();
		}
		
	    return new MovingTowardsFixedPoint(origen, g);
	}

	

}
