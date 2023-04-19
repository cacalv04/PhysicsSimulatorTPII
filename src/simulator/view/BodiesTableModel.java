package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force"};
	List<Body> _bodies;
	List<BodiesGroup> _g;
	
	BodiesTableModel(Controller c){
		_bodies = new ArrayList<>();
		_g = new ArrayList<>();
		c.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _header.length;
	}
	
	public String getColumnName(int column) {
		return _header[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch(columnIndex) {
		case 0: 
			return _bodies.get(rowIndex).getId();
		case 1: 
			return _bodies.get(rowIndex).getgId();
		case 2: 
			return _bodies.get(rowIndex).getMass();
		case 3: 
			return _bodies.get(rowIndex).getVelocity();
		case 4: 
			return _bodies.get(rowIndex).getPosition();
		case 5: 
			return _bodies.get(rowIndex).getForce();
		
		}
		return null;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		for(BodiesGroup i : groups.values()) {
			if(!_g.contains(i)) {
				_g.add(i);
			}
			for(Body j : i.getBodies()) {
				if(!_bodies.contains(j)) {
					_bodies.add(j);
				}
			}
		}
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_bodies = new ArrayList<>();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		for(BodiesGroup i : groups.values()) {
			if(!_g.contains(i)) {
				_g.add(i);
			}
			for(Body j : i.getBodies()) {
				if(!_bodies.contains(j)) {
					_bodies.add(j);
				}
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		for(BodiesGroup i : groups.values()) {
			if(!_g.contains(i)) {
				_g.add(i);
			}
			for(Body j : i.getBodies()) {
				if(!_bodies.contains(j)) {
					_bodies.add(j);
				}
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		for(BodiesGroup i : groups.values()) {
			if(!_g.contains(i)) {
				_g.add(i);
			}
			for(Body j : i.getBodies()) {
				if(!_bodies.contains(j)) {
					_bodies.add(j);
				}
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

	

}
