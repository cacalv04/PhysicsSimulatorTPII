package simulator.control;

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
	
	private PhysicsSimulator physicsSimulator;
	private Factory<Body> bodiesFactory;
	private Factory<ForceLaws> forceLawsFactory;

	public Controller(PhysicsSimulator ps, Factory<Body> fb, Factory<ForceLaws> ff) {
		this.physicsSimulator = ps;
		this.forceLawsFactory = ff;
		this.bodiesFactory = fb;
	}
	
	public void loadData(InputStream in) {
		try {
			JSONObject jsonInupt = new JSONObject(new JSONTokener(in));   //jsonInupt contiene lo que hay en todo el fichero in
			
			JSONArray groups = jsonInupt.getJSONArray("groups");    //groups es un array de todos los grupos que hay en formato JSON
			JSONArray bodies = jsonInupt.getJSONArray("bodies");
			
			for(int i = 0; i < groups.length(); i++) {
				physicsSimulator.addGroup(groups.getString(i));
			}
			for(int i = 0; i < groups.length(); i++) {
				physicsSimulator.addBody(bodiesFactory.createInstance(bodies.getJSONObject(i)));
			}
			//puede que falte la clave laws
		}
		catch (IllegalArgumentException ex) {
			throw ex;
		}
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		
		p.println(physicsSimulator.getState());
		
		for(int i = 1; i <= n; i++) {
			p.println(physicsSimulator.getState());
		}
		p.println("]");
		p.println("}");
	}
}
