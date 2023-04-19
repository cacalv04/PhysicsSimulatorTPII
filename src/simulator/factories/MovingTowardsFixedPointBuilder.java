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
		Vector2D aux = null;
		
		if(jsonObject.has("g")) {
			g = jsonObject.getDouble("g");
		}
		else{ 
			g = 9.81;
		}
		
		if(jsonObject.has("c")) {    
			aux = new Vector2D(jsonObject.getJSONArray("c").getDouble(0), jsonObject.getJSONArray("c").getDouble(1));
		}
		else {
			aux = new Vector2D(0, 0);
		}
		
	    return new MovingTowardsFixedPoint(aux, g);
	}

	public JSONObject getInfo() {
		JSONObject js = new JSONObject();
		js.put("type", "mtfp");
		js.put("desc", "Moving towards a fixed point");
		JSONObject js2 = new JSONObject();
		js2.put("c", "the point towards which bodies move (e.g., [100.0,50.0])");
		js2.put("g", "the length of the acceleration vector (a number)");
		js.put("data", js2);
		
		return js;
	}

}
