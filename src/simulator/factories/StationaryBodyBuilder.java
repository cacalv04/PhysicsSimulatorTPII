package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body>{

	public StationaryBodyBuilder() {
		super("st_body", "Stationary body");
	}

	@Override
	protected StationaryBody createInstance(JSONObject data) {
		if(data == null) throw new IllegalArgumentException("[ERROR]: data is null"); //se comrpueba que data no sea nulo y contenga los campos necesarios
		if(!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("m")) throw new IllegalArgumentException("[ERROR]: missing arguments");
		
		String id = data.getString("id");
		String gid = data.getString("gid");
		JSONArray paux = data.getJSONArray("p");
		Vector2D p = new Vector2D(paux.getDouble(0), paux.getDouble(1));
		
		//se comeprueba que el vector posicion sea 2D
		if(paux.length() != 2) throw new IllegalArgumentException("[ERROR]: p not 2D");
		
		double m = data.getDouble("m");
		
		return new StationaryBody(id, gid, p, m);
	}

}
