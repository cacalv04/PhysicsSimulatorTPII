package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class GroupsTableModel extends AbstractTableModel implements SimulatorObserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] _header = { "Id", "Force Laws", "Bodies"};
	List<BodiesGroup> _groups;
	
	GroupsTableModel(Controller c){
		_groups = new ArrayList<>();
		c.addObserver(this);
	}
	
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return _groups.size();
	}

	
	public String getColumnName(int column) {
		return _header[column];
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _header.length;
	}



	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch(columnIndex) {
		case 0: 
			return _groups.get(rowIndex).getId();
		case 1: 
			return _groups.get(rowIndex).getForceLawsInfo();
		case 2: 
			List<Body> l = _groups.get(rowIndex).getBodies();
			String r = new String();
			if(l.isEmpty()) {return r;}
			r = l.get(0).getId();
			for(int i = 1; i < _groups.get(rowIndex).getBodies().size(); i++) {
				 r += ", " + l.get(i).getId();
			}
			return r;
		}
		return null;
	}

	

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub

		for(BodiesGroup i : groups.values()) {
			if(!_groups.contains(i)) {
				_groups.add(i);
			}
		}
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_groups = new ArrayList<>();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_groups = new ArrayList<>();
		
		
		
		for(BodiesGroup i : groups.values()) {
			if(!_groups.contains(i)) {
				_groups.add(i);
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		for(BodiesGroup i : groups.values()) {
			if(!_groups.contains(i)) {
				_groups.add(i);
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		for(BodiesGroup i : groups.values()) {
			if(!_groups.contains(i)) {
				_groups.add(i);
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
		for(BodiesGroup i : _groups) {
			if(i.getId() == g.getId()) {
				i = g;
			}
		}
		fireTableStructureChanged();
	}



	

}
