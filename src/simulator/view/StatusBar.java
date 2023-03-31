package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel timeLabel; // for current time
	private JLabel groupsLabel; // for groups
	   
	StatusBar(Controller c){
		initGUI();
		c.addObserver(this);
	}
	
	private void initGUI() {
		// TODO Auto-generated method stub
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		// TODO Crear una etiqueta de tiempo y añadirla al panel
		timeLabel = new JLabel();
		this.add(timeLabel);
		
		JSeparator s = new JSeparator(JSeparator.VERTICAL);
		s.setPreferredSize(new Dimension(10, 20));
		this.add(s);
		
		// TODO Crear la etiqueta de número de grupos y añadirla al panel
		groupsLabel = new JLabel("Groups: ");
		this.add(groupsLabel);
		
		// TODO Utilizar el siguiente código para añadir un separador vertical
		//
		// JSeparator s = new JSeparator(JSeparator.VERTICAL);
		// s.setPreferredSize(new Dimension(10, 20));
		// this.add(s);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		timeLabel.setText("Time: " + time);
		groupsLabel.setText("Groups: " + groups.size());
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		timeLabel.setText("Time: " + time);
		groupsLabel.setText("Groups: " + groups.size());
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		timeLabel.setText("Time: " + time);
		groupsLabel.setText("Groups: " + groups.size());
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		groupsLabel.setText("Groups: " + groups.size());
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
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
