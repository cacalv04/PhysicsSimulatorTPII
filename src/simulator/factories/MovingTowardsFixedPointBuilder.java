package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{
	
	private double g = 9.81;
	private Vector2D c = null;

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MovingTowardsFixedPoint createInstance(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
		String data = null;
		
		if(jsonObject.has("g")) { 		//se comprueba que traiga la clave g, si no se usa el valor predeterminado
			g = jsonObject.getDouble("g");
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

	public JSONObject getInfo() {
		JSONObject js = new JSONObject();
		js.put("type", "mtfp");
		js.put("desc", "Moving towards a fixed point");
		JSONObject js2 = new JSONObject();
		js2.put("c", "the point towards which bodies move" + c.toString());
		js2.put("g", "the length of the acceleration vector" + g);
		js.put("data", js2);
		
		return js;
	}

}
