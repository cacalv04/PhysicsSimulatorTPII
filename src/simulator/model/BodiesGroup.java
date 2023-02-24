package simulator.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class BodiesGroup {

	private String ID;
	private ForceLaws laws;
	private List<Body> bodies;
	
	public BodiesGroup(String id, ForceLaws fl) {
		
		try {
			validArguments(id, fl);
			this.ID = id;
			this.laws = fl;
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public String getId() {
		return ID;
	}

	public void setForceLaws(ForceLaws laws) {
		if(laws == null)throw new IllegalArgumentException("[ERROR]: force laws are null");
		this.laws = laws;
	}

	void addBody(Body b) {
		if(b == null)throw new IllegalArgumentException("[ERROR]: the body is null");
		if (bodies.contains(b)) throw new IllegalArgumentException("[ERROR]: the body already exists");
		bodies.add(b);
	}
	
	void advance(double dt) {
		if (dt < 0)throw new IllegalArgumentException("[ERROR]: delta time is negative");
		for(Body b : bodies) {
			b.resetForce();
		}
		laws.apply(bodies);
		for(Body b : bodies) {
			b.advance(dt);
		}
	}
	
	public JSONObject getState() {
		JSONObject bgroup = new JSONObject();
		
		bgroup.put("id", this.ID);
		JSONArray bodies_array = new JSONArray();
		for (Body b : bodies) {
			bodies_array.put(b.getId());
		}
		bgroup.put("bodies", bodies_array);
		
		return bgroup;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	private void validArguments(String id2, ForceLaws fl) {
		// TODO Auto-generated method stub
		if(id2 == null || fl == null) throw new IllegalArgumentException("[ERROR]: Illegal arguments for bodies Group");
		if(id2.trim().length()>0) throw new IllegalArgumentException("[ERROR]: id not including a character not void");
	}
}
