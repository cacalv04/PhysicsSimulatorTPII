package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body>{

	public MovingBodyBuilder() {
		super("mv_body", "Moving body");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Body createInstance(JSONObject data) {
		// TODO Auto-generated method stub
		if(data == null) throw new IllegalArgumentException("[ERROR]: data is null");
		if(!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("v") || !data.has("m")) throw new IllegalArgumentException("[ERROR]: missing arguments");
		
		String id = data.getString("id");
		String gid = data.getString("gid");
		JSONArray paux = data.getJSONArray("p");
		if(paux.length() != 2) throw new IllegalArgumentException("[ERROR]: p not 2D");

		Vector2D p = new Vector2D(paux.getDouble(0), paux.getDouble(1));
		JSONArray vaux = data.getJSONArray("v");
		if(vaux.length() != 2) throw new IllegalArgumentException("[ERROR]: p not 2D");

		Vector2D v = new Vector2D(vaux.getDouble(0), vaux.getDouble(1));
		double m = data.getDouble("m");
		
		return new MovingBody(id, gid ,p, v, m);
	}

}
