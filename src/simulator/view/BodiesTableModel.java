package simulator.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	private String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force"};
	private List<Body> _bodies;
	
	BodiesTableModel(Controller c){
		_bodies = new ArrayList<Body>();
		c.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}

	@Override
	public Object getValueAt(int f, int c) {
		Object o = null;
		
		switch(c) {
			case 0:
				o = _bodies.get(f).getId();
				break;
			case 1:
				o = _bodies.get(f).getgId();
				break;
			case 2:
				o = _bodies.get(f).getMass();
				break;
			case 3:
				o = _bodies.get(f).getVelocity();
				break;
			case 4:
				o = _bodies.get(f).getPosition();
				break;
			case 5:
				o = _bodies.get(f).getForce();
				break;
		}
		
		return o;
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		updateList(groups);
		fireTableDataChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		updateList(groups);
		fireTableDataChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		updateList(groups);
		fireTableDataChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		updateList(groups);
		fireTableDataChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		updateList(groups);
		fireTableDataChanged();
	}

	private void updateList(Map<String, BodiesGroup> groups) {
		for(Map.Entry<String, BodiesGroup> grupo : groups.entrySet()) {
			Iterator<Body> it = grupo.getValue().getIterator();
			while(it.hasNext()) {
				_bodies.add(it.next());
			}
		}
	}

}
