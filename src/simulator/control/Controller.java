package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

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
			
			//sim.reset();
			
			JSONArray groups = jsonInput.getJSONArray("groups");		  //groups es un array de todos los grupos que hay en formato JSON
			for(int i = 0; i < groups.length(); i++) {
				sim.addGroup(groups.getString(i));							// se recorre el array de los objetos "groups" y se añaden al simulador
			}
			
			if(jsonInput.has("laws")) {
				JSONArray laws = jsonInput.getJSONArray("laws");		  // laws coge todos los objetos que contenga la clave laws
				for(int i = 0; i < laws.length(); i++) {				// se rrecorren todas la leyes que vengan en la clave laws, y se le aplica al cuerpo que indique la clave "id" dentro de cada objeto law
 					sim.setForceLaws(laws.getJSONObject(i).getString("id"), lawsfac.createInstance(laws.getJSONObject(i).getJSONObject("laws")));
				}
			}
			
			JSONArray bodies = jsonInput.getJSONArray("bodies");			// bodies coge todos los objetos JSON que contiene  la clave bodies		
			for(int i = 0; i < bodies.length(); i++) {
				sim.addBody(bodyfac.createInstance(bodies.getJSONObject(i))); // se recorre el array de objetos bodies y se aniaden al simulador 
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
		//p.print(", ");
		
		for(int i = 1; i <= n; i++) {
			p.print(", ");
			sim.advance();
			p.println(sim.getState());
		}
		
		p.println("]");
		p.println("}");
	}
	
	public void reset() {
		sim.reset();
	}
	
	public void setDeltaTime(double dt) {
		sim.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		sim.addObserver(o);
	}
	
	public void removeObserver(SimulatorObserver o) {
		sim.removeObserver(o);
	}
	
	public List<JSONObject> getForceLawsInfo(){
		return lawsfac.getInfo();
	}
	
	public void setForcesLaws(String gId, JSONObject info) {
		sim.setForceLaws(gId, lawsfac.createInstance(info));
	}
	
	public void run(int n) {
		for(int i=0; i<n; i++) sim.advance();
	}
}
