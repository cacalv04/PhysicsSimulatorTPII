package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder{

	public MovingBodyBuilder() {
		super("mv_body", "Moving body");
	}

	@Override
	protected MovingBody createInstance(JSONObject data) throws IllegalArgumentException{
		if(data == null) throw new IllegalArgumentException("[ERROR]: data is null");
		if(!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("v") || !data.has("m")) throw new IllegalArgumentException("[ERROR]: missing arguments");
		
		String id = data.getString("id");
		String gid = data.getString("gid");
		JSONArray paux = data.getJSONArray("p");
		Vector2D p = new Vector2D(paux.getDouble(0), paux.getDouble(1));
		JSONArray vaux = data.getJSONArray("v");
		Vector2D v = new Vector2D(vaux.getDouble(0), vaux.getDouble(1));
		double m = data.getDouble("m");
		
		return new MovingBody(id, gid, v, p, new Vector2D(0,0), m);
	}
}
