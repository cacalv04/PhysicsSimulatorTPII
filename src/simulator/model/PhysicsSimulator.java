package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class PhysicsSimulator {
	
	private double dt;
	private ForceLaws laws;
	private Map<String, BodiesGroup> groupsMap;
	private double actualTime;
	private List<String> BG;

	public PhysicsSimulator(ForceLaws fl, double dt) {
		try {
			argumentsCheck(dt, fl);  //funcion que comprueba la validez de los datos de entrada
			this.dt = dt;
			this.laws = fl;
			this.actualTime = 0.0;
			this.groupsMap = new HashMap<>();
			this.BG = new ArrayList<>();
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public void advance() {
		for(BodiesGroup g : groupsMap.values()) {
			g.advance(dt);
		}
		actualTime += dt;
	}
	
	public void addGroup(String id) {  		//se comprueba que el grupo no exista ya
		if(groupsMap.containsKey(id)) throw new IllegalArgumentException("[ERROR]: the group id arlready exists");
		BodiesGroup bg = new BodiesGroup(id, laws);
		groupsMap.put(id, bg);
		BG.add(id);
	}
	
	public void addBody(Body b) {  		//se comprueba que el grupo al que se quiere aniadir el cuerpo exista en el simulador
		if(!groupsMap.containsKey(b.getgId()))throw new IllegalArgumentException("[ERROR]: the body group does not exists");
		groupsMap.get(b.getgId()).addBody(b);
	}
	
	public void setForceLaws(String id, ForceLaws fl) {		//se comprueba que el grupo sobre el que se quiere aplicar la fuerza exista en el simulador
		if(!groupsMap.containsKey(id))throw new IllegalArgumentException("[ERROR]: the body group does not exists");
		groupsMap.get(id).setForceLaws(fl);
	}
	
	public JSONObject getState() {
		JSONObject JS = new JSONObject();
		
		JS.put("time", this.actualTime);
		JSONArray groups_array = new JSONArray();
		for (String b : BG) {
			groups_array.put(groupsMap.get(b).getState());
		}
		JS.put("groups", groups_array);
		
		return JS;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	private void argumentsCheck(double dt, ForceLaws fl) {		//se comprueba que dt sea mayor o igual a cero y que la ley no sea nula
		if(dt <= 0) throw new IllegalArgumentException("[ERROR]: delta time not valid");
		if (fl == null) throw new IllegalArgumentException("[ERROR]: force law are null");
	}
}



