package simulator.model;

import simulator.misc.Vector2D;

public abstract class Body {

	protected String ID;
	protected String GID;
	protected Vector2D speed;
	protected Vector2D force;
	protected Vector2D pos;
	protected double mass;
	
	private Body(String ID, String GID, Vector2D speed, Vector2D pos, double mass) {
		try {
			validArguements(ID, GID, speed, pos, mass);
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

	private void validArguements(String iD2, String gID2, Vector2D speed2, Vector2D pos2, double mass2) {
		// TODO Auto-generated method stub
		if(iD2 == null|| gID2 == null || speed2 == null || pos2 == null || mass2 < 0 ) throw new IllegalArgumentException("[ERROR]: an argument value is null or negative");
		if (iD2.trim().length()>0 || gID2.trim().length()>0) throw new IllegalArgumentException("[ERROR]: id or gid not including a character not void");
	}
	
}
