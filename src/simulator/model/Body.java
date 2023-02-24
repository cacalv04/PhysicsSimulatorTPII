package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public abstract class Body {
	protected String ID;
	protected String gID;
	protected Vector2D speed;
	protected Vector2D force;
	protected Vector2D pos;
	protected Vector2D ace;
	protected double mass;
	
	public Body(String ID, String gID, Vector2D speed, Vector2D pos,  Vector2D ace, double mass) {
		try {
			validArguments(ID, gID, speed, pos, mass);
			this.ID = ID;
			this.gID = gID;
			this.speed = speed;
			this.pos = pos;
			this.ace = ace;
			this.mass = mass;
			this.force = new Vector2D(0, 0);
		}catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public String getId() {
		return ID;
	}

	public String getgId() {
		return gID;
	}

	public Vector2D getVelocity() {
		return speed;
	}
	
	public void setVelocity(Vector2D speed) {
		this.speed = speed;
	}

	public Vector2D getForce() {
		return force;
	}
	
	public void setForce(Vector2D force) {
		this.force = force;
	}

	public Vector2D getPosition() {
		return pos;
	}
	
	public void setPosition(Vector2D pos) {
		this.pos = pos;
	}
	
	public Vector2D getAcceleration() {
		return ace;
	}
	
	public void setAcceleration(Vector2D ace) {
		this.ace = ace;
	}

	public double getMass() {
		return mass;
	}
	
	void addForce(Vector2D f) {
		force.plus(f);
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

	private void validArguments(String ID, String gID, Vector2D speed, Vector2D pos, double mass) {
		// TODO Auto-generated method stub
		if(ID == null|| gID == null || speed == null || pos == null || ace == null || mass < 0 ) throw new IllegalArgumentException("[ERROR]: an argument value is null or negative");
		if (ID.trim().length() > 0 || gID.trim().length() > 0) throw new IllegalArgumentException("[ERROR]: id or gid not including a character not void");
	}
}
