package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder{

	public MovingTowardsFixedPointBuilder(String typeTag, String desc) {
		super("mtfp", "Moving towards fixed point");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object createInstance(JSONObject data) {
		// TODO Auto-generated method stub
		if(data == null) throw new IllegalArgumentException("[ERROR]: data is null");
		if(!data.has("c") || !data.has("g")) throw new IllegalArgumentException("[ERROR]: missing arguments");
		
		JSONArray caux = data.getJSONArray("c");
		Vector2D c = new Vector2D(caux.getDouble(0), caux.getDouble(1));
		double g = data.getDouble("g");
		
		return new MovingTowardsFixedPoint(c, g);

	}

}
