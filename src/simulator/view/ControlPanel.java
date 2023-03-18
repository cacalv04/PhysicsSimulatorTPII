package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	private Controller _ctrl;
	private JToolBar _toolBar;
	private JFileChooser _fc;
	private boolean _stopped = true; // utilizado en los botones de run/stop
	
	//BOTONES
	private JButton quitB;
	private JButton loadB;
	private JButton startB;
	private JButton stopB;
	private JButton lawsB;
	private JSpinner steps;
	private JTextField delta;
	
	public ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		_toolBar = new JToolBar();
		add(_toolBar, BorderLayout.PAGE_START);
		// TODO crear los diferentes botones/atributos y añadirlos a _toolaBar.
		// Todos ellos han de tener su correspondiente tooltip. Puedes utilizar
		// _toolaBar.addSeparator() para añadir la línea de separación vertical
		// entre las componentes que lo necesiten
		

		//DECLARAMOS LAS VAINAS
		quitB = initButton("exit.png", "Quit");
		loadB = initButton("open.png", "Open");
		startB = initButton("run.png", "Run the simulator");
		stopB = initButton("stop.png", "Stop the simulator");
		lawsB = initButton("physics.png", "Force laws");
		steps = new JSpinner();
		delta = new JTextField();
		  
		JLabel stepsL = new JLabel();
		stepsL.setText("Steps: ");
		
		JLabel deltaL = new JLabel();
		deltaL.setText("Delta-Time: ");
		
		//FUNCIONES DE LOS BOTONES
		
		//quitB.addActionListener((e) -> Utils.quit(this));
		
		loadB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(new File("./resources/examples/").getAbsolutePath()));
				
				int op = fc.showOpenDialog(fc);
				if(op == fc.APPROVE_OPTION) {
					
					_ctrl.reset();
					try {
						FileInputStream is = new FileInputStream(fc.getSelectedFile());
						_ctrl.loadData(is);
						
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Error: Load Control Panel", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
		});
		
		startB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				enableToolBar(false);
				_stopped = false;
				_ctrl.setDeltaTime((Double)steps.getValue());
				run_sim((Integer)steps.getValue());
			}
			
		});
		
		stopB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				_stopped = true;
			}
			
		});
		

		//AÑADIMOS LAS VAINAS	
		_toolBar.add(Box.createGlue()); // this aligns the button to the right
		_toolBar.add(loadB);
		_toolBar.addSeparator();
		
		_toolBar.add(lawsB);
		
		_toolBar.addSeparator();
		
		_toolBar.add(startB);
		_toolBar.add(stopB);
		_toolBar.addSeparator();
		
		_toolBar.add(stepsL);
		_toolBar.add(steps);
		_toolBar.add(deltaL);
		_toolBar.add(delta);
		_toolBar.addSeparator();
		_toolBar.add(quitB);
	}
	
	private JButton initButton(String name, String desc) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon("resources/icons/" + name));
		button.setToolTipText(desc);
		return button;
	}
	
	private void enableToolBar(boolean b) {
	       loadB.setEnabled(b);
	       startB.setEnabled(b);
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
			_ctrl.run(1);
			} catch (Exception e) {
				// TODO llamar a Utils.showErrorMsg con el mensaje de error que corresponda
				// TODO (marsi) cambiar a lo que dice arriba
				JOptionPane.showMessageDialog(null, "Error: Run Control Panel", "ERROR", JOptionPane.ERROR_MESSAGE);
				// TODO activar todos los botones
				enableToolBar(true);
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
			// TODO llamar a Utils.showErrorMsg con el mensaje de error que corresponda
			// TODO activar todos los botones
			enableToolBar(true);
			_stopped = true;
		}
	}
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {}
	
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		delta.setText(Double.toString(dt));
	}
	
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		delta.setText(Double.toString(dt));
	}
	
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {}
	
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {}
	
	@Override
	public void onDeltaTimeChanged(double dt) {
		delta.setText(Double.toString(dt));
	}
	
	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
}

