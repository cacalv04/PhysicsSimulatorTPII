package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;

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
			
			for(int i = 0; i < groups.length(); i++) {
				physicsSimulator.addGroup(bodiesFactory.createInstance(bodies.getJSONObject(i)));
			}
		}
		catch (IllegalArgumentException ex) {
			throw ex;
		}
	}
	
	public void run(int n, OutputStream out) {
		
	}
}
