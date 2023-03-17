package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class PhysicsSimulator implements Observable<SimulatorObserver>{
	
	private double dt;
	private ForceLaws laws;
	private Map<String, BodiesGroup> groupsMap;
	private Map<String, BodiesGroup> groupsMapRO;
	private double actualTime;
	private List<String> BG;
	private List<SimulatorObserver> observersList;

	public PhysicsSimulator(ForceLaws fl, double dt) {
		try {
			argumentsCheck(dt, fl);  //funcion que comprueba la validez de los datos de entrada
			this.dt = dt;
			this.laws = fl;
			this.actualTime = 0.0;
			this.groupsMap = new HashMap<>();
			this.groupsMapRO = Collections.unmodifiableMap(groupsMap);
			this.BG = new ArrayList<>();
			this.observersList = new ArrayList<>();
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	@Override
	public void addObserver(SimulatorObserver o) {
		// TODO Auto-generated method stub
		if(!observersList.contains(o)) {
			observersList.add(o);
		}
		o.onRegister(groupsMapRO, actualTime, dt);
	}

	@Override
	public void removeObserver(SimulatorObserver o) {
		// TODO Auto-generated method stub
		observersList.remove(o);
	}
	
	public void reset() {
		groupsMap.clear();
		BG.clear();
		actualTime = 0.0;
		for(SimulatorObserver i : observersList) {
			i.onReset(groupsMapRO, actualTime, dt);
		}
	}
	
	public void setDeltaTime(double dt) {
		if(dt <= 0) throw new IllegalArgumentException("[ERROR]: dt must be positive");
		this.dt = dt;
		
		for(SimulatorObserver i : observersList) {
			i.onDeltaTimeChanged(dt);
		}
	}
	
	public void advance() {
		for(BodiesGroup g : groupsMap.values()) {
			g.advance(dt);
		}
		actualTime += dt;
		
		for(SimulatorObserver i : observersList) {
			i.onAdvance(groupsMapRO, actualTime);;
		}
	}
	
	public void addGroup(String id) {  		//se comprueba que el grupo no exista ya
		if(groupsMap.containsKey(id)) throw new IllegalArgumentException("[ERROR]: the group id arlready exists");
		BodiesGroup bg = new BodiesGroup(id, laws);
		groupsMap.put(id, bg);
		BG.add(id);
		
		for(SimulatorObserver i : observersList) {
			i.onGroupAdded(groupsMapRO, bg);
		}
	}
	
	public void addBody(Body b) {  		//se comprueba que el grupo al que se quiere aniadir el cuerpo exista en el simulador
		if(!groupsMap.containsKey(b.getgId()))throw new IllegalArgumentException("[ERROR]: the body group does not exists");
		groupsMap.get(b.getgId()).addBody(b);
		
		for(SimulatorObserver i : observersList) {
			i.onBodyAdded(groupsMapRO, b);
		}
	}
	
	public void setForceLaws(String id, ForceLaws fl) {		//se comprueba que el grupo sobre el que se quiere aplicar la fuerza exista en el simulador
		if(!groupsMap.containsKey(id))throw new IllegalArgumentException("[ERROR]: the body group does not exists");
		groupsMap.get(id).setForceLaws(fl);
		
		for(SimulatorObserver i : observersList) {
			i.onForceLawsChanged(groupsMap.get(id));
		}
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



