package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class PhysicsSimulator {
	
	private double dt;
	private ForceLaws laws;
	private Map<String, BodiesGroup> groupsMap;
	private double actualTime;
	private List<String> BG;

	public PhysicsSimulator(double dt, ForceLaws fl) {
		try {
			validArguements(dt, fl);
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
			actualTime += dt;
		}
	}
	
	public void addGroup(String id) {
		if(groupsMap.containsKey(id)) throw new IllegalArgumentException("[ERROR]: the group id arlready exists");
		BodiesGroup bg = new BodiesGroup(id, laws);
		groupsMap.put(id, bg);
		BG.add(id);
	}
	
	public void addBody(Body b) {
		if(!groupsMap.containsKey(b.getGID()))throw new IllegalArgumentException("[ERROR]: the body group does not exists");
		groupsMap.get(b.getGID()).addBody(b);
	}
	
	public void setForceLaw(String id, ForceLaws fl) {
		if(!groupsMap.containsKey(id))throw new IllegalArgumentException("[ERROR]: the body group does not exists");
		groupsMap.get(id).setLaws(fl);
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
	
	private void validArguements(double dt, ForceLaws fl) {
		if(dt < 0) throw new IllegalArgumentException("[ERROR]: delta time not valid");
		if (fl == null) throw new IllegalArgumentException("[ERROR]: force law are null");
	}
}
