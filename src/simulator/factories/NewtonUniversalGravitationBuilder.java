package simulator.factories;

import org.json.JSONObject;

import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder{

	public NewtonUniversalGravitationBuilder(String typeTag, String desc) {
		super("nlug", "Newton law of universal gravitation");
	}

	@Override
	protected NewtonUniversalGravitation createInstance(JSONObject data) {
		if(data == null) throw new IllegalArgumentException("[ERROR]: data is null");
		if(!data.has("g")) throw new IllegalArgumentException("[ERROR]: missing arguments");
		
		double g = data.getDouble("g");
		
		return new NewtonUniversalGravitation(g);
	}

}
