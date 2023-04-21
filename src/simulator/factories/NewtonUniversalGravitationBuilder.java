package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	
	private double g = 6.67E-11 ; 

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton’s law of universal gravitation");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected NewtonUniversalGravitation createInstance(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
		if(jsonObject.has("G")) {  		//si el JSON trae el valor G, se coge, si no se usa el predeterminado
			//if()
			g = jsonObject.getDouble("G");   
		}
		else { 
			return new NewtonUniversalGravitation(g);
		}
		
	    return new NewtonUniversalGravitation(g);
	}
	
	public JSONObject getInfo() {
		JSONObject js = new JSONObject();
		js.put("type", "nlug");
		js.put("desc", "Newton's law of universal gravitation");
		JSONObject js2 = new JSONObject();
		js2.put("G", "the gravitational constant " + g);
		js.put("data", js2);
		
		return js;
	}
}