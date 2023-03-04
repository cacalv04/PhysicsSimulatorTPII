package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton’s law of universal gravitation");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected NewtonUniversalGravitation createInstance(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		double g = 9.81;
		if(jsonObject.has("G")) {
			//String data = jsonObject.getString("G");

			g = jsonObject.getDouble("G");    //Ya comprueba que es un double en forceLawDialog
		}
		else { 
			return new NewtonUniversalGravitation(g);
		}
		
	    return new NewtonUniversalGravitation(g);
	}
}