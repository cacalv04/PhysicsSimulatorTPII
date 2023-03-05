package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public abstract class Body {

	protected String ID;
	protected String GID;
	protected Vector2D speed;
	protected Vector2D force;
	protected Vector2D pos;
	protected double mass;
	
	public Body(String ID, String GID, Vector2D pos, Vector2D speed, Double mass) {
		try {
			arguementsCheck(ID, GID, speed, pos, mass);   //funcion con la que se comprueba la validez de los datos de entrada
			this.ID = ID;
			this.GID = GID;
			this.speed = speed;
			this.pos = pos;
			this.mass = mass;
			this.force = new Vector2D(0, 0);
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}
	
	public String getId() {
		return ID;
	}

	public String getgId() {
		return GID;
	}

	public Vector2D getVelocity() {
		return speed;
	}

	public Vector2D getForce() {
		return force;
	}

	public Vector2D getPosition() {
		return pos;
	}

	public double getMass() {
		return mass;
	}
	
	void addForce(Vector2D f) {
		force = force.plus(f);
	}
	
	void resetForce() {
		force = new Vector2D(0, 0);
	}
	
	abstract void advance(double dt);
	
	public JSONObject getState() {
		JSONObject JS = new JSONObject();
		
		JS.put("id", this.ID);
		JS.put("m", this.mass);
		JS.put("p", getPosition().asJSONArray());
		JS.put("v", getVelocity().asJSONArray());
		JS.put("f", getForce().asJSONArray());
		
		return JS;
	}
	
	public String toString() {
		return getState().toString();
	}

	private void arguementsCheck(String iD2, String gID2, Vector2D speed2, Vector2D pos2, Double mass2) {
		// TODO Auto-generated method stub		//se compriba que ningun elemento sea nulo, que el id y gid no sean vacios y que la masa tenga valor positivo
		if(iD2 == null) throw new IllegalArgumentException("[ERROR]: an argument value is null or negative IID");
		if(gID2 == null) throw new IllegalArgumentException("[ERROR]: an argument value is null or negative GID");
		if(speed2 == null) throw new IllegalArgumentException("[ERROR]: an argument value is null or negative SPEED");
		if(pos2 == null) throw new IllegalArgumentException("[ERROR]: an argument value is null or negative POS");
		if(mass2 == null ) throw new IllegalArgumentException("[ERROR]: an argument value is null or negative MASS");

		if (iD2.trim().length()==0 || gID2.trim().length()==0) throw new IllegalArgumentException("[ERROR]: id or gid not including a character not void");
		if(mass2 <= 0) throw new IllegalArgumentException("[ERROR]: mass < 0");
	}
	
}
