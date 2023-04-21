package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; // utilizado en los botones de run/stop
	private JButton _quitButton, loadB, flDialogB, viewB, stopB, runB;
	// TODO añade más atributos aquí …
	private ForceLawsDialog fld;
	private JTextField dtTF;
	private JSpinner stepsJS;
	private ViewerWindow vw;
	
	
	ControlPanel(Controller c){
		_ctrl = c;
		initGUI();
		c.addObserver(this);
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);	
				
		loadB = new JButton();
		loadB.setIcon(new ImageIcon("resources/icons/open.png"));
		loadB.setToolTipText("Load an input file into the simulator");
		loadB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				loadBottomListener();
			}
		});
		
		flDialogB = new JButton();
		flDialogB.setIcon(new ImageIcon("resources/icons/physics.png"));
		flDialogB.setToolTipText("Select force laws for groups");
		flDialogB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changePhysicsListener();
			}
		});
		
		viewB = new JButton();
		viewB.setIcon(new ImageIcon("resources/icons/viewer.png"));
		viewB.setToolTipText("Open viewer window");
		viewB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createViewListener();
			}
		});
		
		stopB = new JButton();
		stopB.setIcon(new ImageIcon("resources/icons/stop.png"));
		stopB.setToolTipText("Stop the simulator");
		stopB.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stopButtonListener();
			}
		});
		
		runB = new JButton();
		runB.setIcon(new ImageIcon("resources/icons/run.png"));
		runB.setToolTipText("Run the simulator");
		runB.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				runButtonListener();
			}
		});
		
		
		JLabel labelSteps = new JLabel("Steps:");
		JLabel labelDeltaTime = new JLabel("Delta-time:");
		Dimension d = new Dimension(200, 40);
		stepsJS = new JSpinner(new SpinnerNumberModel(10000, 1, 10000, 100));
		stepsJS.setToolTipText("Simulation steps to run: 1-10000");
        stepsJS.setMaximumSize(d);
        dtTF = new JTextField();
        dtTF.setToolTipText("Real time (seconds) corresponding to a step");
        dtTF.addKeyListener(new KeyAdapter()
        {
           public void keyTyped(KeyEvent e)
           {
              char caracter = e.getKeyChar();

              // Verifica si la tecla pulsada no es un digito
              if(((caracter < '0') ||
                 (caracter > '9')) &&
                 (caracter != '\b'))
              {
                 e.consume();  // ignorar el evento de teclado
              }
           }
        });
        dtTF.setText("2500");
        dtTF.setPreferredSize(d);
        //dtTF.setMaximumSize(d);
        //dtTF.setMinimumSize(d);

		

		
		// Quit Button
		_toolaBar.add(Box.createGlue()); // this aligns the button to the right
		_toolaBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Exit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		
		
		
		_toolaBar.add(loadB);
		_toolaBar.addSeparator();
		_toolaBar.add(flDialogB);
		_toolaBar.addSeparator();
		_toolaBar.add(viewB);
		_toolaBar.addSeparator();
		_toolaBar.add(runB);
		_toolaBar.addSeparator();
		_toolaBar.add(stopB);
		_toolaBar.addSeparator();
		_toolaBar.add(labelSteps);
		_toolaBar.add(stepsJS);
		_toolaBar.addSeparator();
		_toolaBar.add(labelDeltaTime);
		_toolaBar.add(dtTF);
		_toolaBar.addSeparator();
		_toolaBar.add(_quitButton);
		
		_toolaBar.setFloatable(false);
	}		

	protected void stopButtonListener() {
		// TODO Auto-generated method stub
		_stopped = true;
	}
	
	protected void runButtonListener() {
		// TODO Auto-generated method stub
		loadB.setEnabled(false);
		runB.setEnabled(false);
		flDialogB.setEnabled(false);
		_quitButton.setEnabled(false);
		viewB.setEnabled(false);
		
		_stopped = false;
		String dtString = dtTF.getText();
		int steps = (int) stepsJS.getValue();
				
		double dt = Double.parseDouble(dtString);
		
		_ctrl.setDeltaTime(dt);
		run_sim(steps);
	}

	private void run_sim(int n) {
		// TODO Auto-generated method stub
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);                                     //AQUI PUEDE QUE FALTE ALGO, COMPRUEBALO
			} catch(Exception e) {
				// TODO llamar a Utils.showErrorMsg con el mensaje de error que
				// corresponda
				Utils.showErrorMsg(null);
				// TODO activar todos los botones
				loadB.setEnabled(true);
				runB.setEnabled(true);
				flDialogB.setEnabled(true);
				_quitButton.setEnabled(true);
				viewB.setEnabled(true);
				
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		}
		else {
			// TODO llamar a Utils.showErrorMsg con el mensaje de error que
			// corresponda
			// TODO activar todos los botones
			loadB.setEnabled(true);
			runB.setEnabled(true);
			flDialogB.setEnabled(true);
			_quitButton.setEnabled(true);
			viewB.setEnabled(true);
			
			_stopped = true;
		}
	}

	
	protected void createViewListener() {
		// TODO Auto-generated method stub
		vw = new ViewerWindow((JFrame)SwingUtilities.getWindowAncestor(this), _ctrl);
	}

	protected void changePhysicsListener() {
		// TODO Auto-generated method stub
		if(fld == null) {
			 fld = new ForceLawsDialog((Frame)SwingUtilities.getWindowAncestor(this), _ctrl);
		}
		int status = fld.open();
		
		if (status == 1) {
			try {
				
			}
			catch(Exception e) {
				//JOptionPane.showMessageDialog(null, , , JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	protected void loadBottomListener() {
		// TODO Auto-generated method stub
		InputStream in = null;
		File file = new File("C:\\Users\\34657\\eclipse-workspace\\PSMio\\resources\\examples\\input");
		_fc = new JFileChooser(file);
		_fc.showOpenDialog(Utils.getWindow(this));
		_fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		 
		 try {             //CAMBIAR EXCEPCION
				in = new FileInputStream(_fc.getSelectedFile());
				_ctrl.reset();
				_ctrl.loadData(in);;
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "No se ha encontrado o marcado el archivo");
			}
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {  //REVISAR
		// TODO Auto-generated method stub
		//dtTF. = dt;
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

	
}
