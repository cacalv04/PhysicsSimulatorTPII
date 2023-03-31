package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup implements Iterable<Body>{

	private String ID;
	private ForceLaws laws;
	private List<Body> bodies;
	private List<Body> bodiesRO;
	
	public BodiesGroup(String id, ForceLaws fl){
		
		try {
			argumentsCheck(id, fl);   //funcion con la que se comprueba la validez de los datos de entrada
			this.ID = id;
			this.laws = fl;
			bodies = new ArrayList<Body>();
			bodiesRO = Collections.unmodifiableList(bodies);
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
		//if (bodies.contains(b)) throw new IllegalArgumentException("[ERROR]: the body already exists");
		for(Body n : bodies) {
			if(b.getId() == n.getId()) {				
				throw new IllegalArgumentException("[ERROR]: the bodie's id already exists");
			}
		}
		bodies.add(b);
	}
	
	void advance(double dt) {  //se resetan los cuerpos, se aplica la ley y se avanzan los cuerpos
		if (dt <= 0)throw new IllegalArgumentException("[ERROR]: delta time is negative");
		for(Body b : bodies) {
			b.resetForce();
		}
		laws.apply(bodies);
		for(Body b : bodies) {
			b.advance(dt);
		}
	}
	
	public String getForceLawsInfo() {
		return laws.toString();
	}
	
	public JSONObject getState() {
		JSONObject bgroup = new JSONObject();
		
		bgroup.put("id", this.ID);
		JSONArray bodies_array = new JSONArray();
		for (Body b : bodies) {
			bodies_array.put(b.getState());
		}
		bgroup.put("bodies", bodies_array);
		
		return bgroup;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	private void argumentsCheck(String id2, ForceLaws fl) {
		// TODO Auto-generated method stub                     //se comrpueba que que ningun elemento sea nulo y el id no sea vacio
		if(id2 == null || fl == null || id2.isEmpty()) throw new IllegalArgumentException("[ERROR]: Illegal arguments for bodies Group");
		if(id2.trim().length()==0) throw new IllegalArgumentException("[ERROR]: id not including a character not void");
	}
	
	public List<Body> getBodies() {
		List<Body> l = new ArrayList<>();
		Iterator<Body> it = this.iterator();
		while(it.hasNext()) {
			l.add(it.next());
		}
		return l;
	}

	@Override
	public Iterator<Body> iterator() {
		// TODO Auto-generated method stub
		return bodiesRO.iterator();
	}															
}
