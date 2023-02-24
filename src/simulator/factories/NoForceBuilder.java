package simulator.factories;

import org.json.JSONObject;

import simulator.model.NoForce;

public class NoForceBuilder extends Builder {

	public NoForceBuilder(String typeTag, String desc) {
		super("nf", "No force");
	}

	@Override
	protected NoForce createInstance(JSONObject data) {
		if(data == null) throw new IllegalArgumentException("[ERROR]: data is null");
		return new NoForce();
	}

}
