package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder{

	public StationaryBodyBuilder(String typeTag, String desc) {
		super("st_body", "Stationary body");
	}

	@Override
	protected StationaryBody createInstance(JSONObject data) {
		if(data == null) throw new IllegalArgumentException("[ERROR]: data is null");
		if(!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("v") || !data.has("m")) throw new IllegalArgumentException("[ERROR]: missing arguments");
		
		String id = data.getString("id");
		String gid = data.getString("gid");
		JSONArray paux = data.getJSONArray("p");
		Vector2D p = new Vector2D(paux.getDouble(0), paux.getDouble(1));
		double m = data.getDouble("m");
		
		return new StationaryBody(id, gid, p, m);
	}

}
