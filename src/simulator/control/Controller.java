package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

public class Controller {
	
	private PhysicsSimulator sim;
	private Factory<Body> bodyfac;
	private Factory<ForceLaws> lawsfac;

	public Controller(PhysicsSimulator ps, Factory<Body> fb, Factory<ForceLaws> ff) {
		this.sim = ps;
		this.lawsfac = ff;
		this.bodyfac = fb;
	}
	
	public void loadData(InputStream in) {
		try {
			JSONObject jsonInput = new JSONObject(new JSONTokener(in));   //jsonInupt contiene lo que hay en todo el fichero in
			
			JSONArray groups = jsonInput.getJSONArray("groups");//groups es un array de todos los grupos que hay en formato JSON
			
			if(jsonInput.has("laws")) {
				JSONArray laws = jsonInput.getJSONArray("laws");
				
				//REVISAR
				for(int i = 0; i < laws.length(); i++) {
					sim.setForceLaws(laws.getJSONObject(i).getString("id"), lawsfac.createInstance(laws.getJSONObject(i).getJSONObject("laws")));
				}
			}
			
			JSONArray bodies = jsonInput.getJSONArray("bodies");
			
			for(int i = 0; i < groups.length(); i++) {
				sim.addGroup(groups.getString(i));
			}
			
			for(int i = 0; i < groups.length(); i++) {
				sim.addBody(bodyfac.createInstance(bodies.getJSONObject(i)));
			}
		}
		catch (IllegalArgumentException ex) {
			throw ex;
		}
	}
	
	public void run(int n, OutputStream out) {
		
		if(out == null){
			out = new OutputStream(){
				@Override
				public void write(int b) throws IOException {	}
			};
		}
		
		PrintStream p = new PrintStream(out);
		p.println("{ \"states\": [");
		p.println(sim.getState());
		p.print(", ");
		
		for(int i = 1; i <= n; i++) {
			p.println(sim.getState());
			sim.advance();
			p.print(", ");
		}
		
		p.println("] }");
	}
}
