package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ForceLawsDialog extends JDialog implements SimulatorObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private JComboBox<String> _laws;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	private int _status;
	
	ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		_ctrl = ctrl;
		initGUI();
		// TODO registrar this como observer;
		ctrl.addObserver(this);
	}


	private void initGUI() {
		// TODO Auto-generated method stub
		
		_status = 0;
		
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JPanel JBPanel = new JPanel();
		JBPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
	
		
		JLabel infoLabel = new JLabel("<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> "
                + "(default values are used for parametes with no value).</p></html>");
		
		infoLabel.setAlignmentX(RIGHT_ALIGNMENT);
	
		
		// _forceLawsInfo se usará para establecer la información en la tabla
		_forceLawsInfo = _ctrl.getForceLawsInfo();
		
		
		// TODO crear un JTable que use _dataTableModel, y añadirla al panel
		_dataTableModel = new DefaultTableModel() {
				/**
			 * 
			 **/
			private static final long serialVersionUID = 1L;
			

				@Override
				public boolean isCellEditable(int row, int column) {
					// TODO hacer editable solo la columna 1
					return column == 1;
				}
		};
		_dataTableModel.setColumnIdentifiers(_headers);
		_dataTableModel.addRow(_headers);

		printtable(_dataTableModel);
		JTable tableM = new JTable(_dataTableModel);
		tableM.setPreferredSize(new Dimension(500, 230));
	
			
		//DialogTable t = new DialogTable(_headers, _ctrl);
		
		JLabel lawsLabel = new JLabel("Force Law: ");
		JBPanel.add(lawsLabel);
		
		_lawsModel = new DefaultComboBoxModel<>();
		// TODO añadir la descripción de todas las leyes de fuerza a _lawsModel
		
		for(JSONObject i : _ctrl.getForceLawsInfo()) {
			_lawsModel.addElement(i.getString("desc"));
		}
		// TODO crear un combobox que use _lawsModel y añadirlo al panel
		
		
		_laws = new JComboBox<>(_lawsModel);
		JBPanel.add(_laws);
		
		_laws.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String forceLaw = (String) _laws.getSelectedItem();
				JSONObject data = getJSONForceLaw(forceLaw).getJSONObject("data");
				Set<String> keyset = data.keySet();
				int tam = _dataTableModel.getRowCount();
				
				for(int i = 0; i < tam; i++) { 
					_dataTableModel.removeRow(0);
				}
				
				_dataTableModel.addRow(_headers);

				
				for(String i : keyset) { 
					String [] rowData = {i, " ", data.getString(i)};
					_dataTableModel.addRow(rowData);
					
				}
				
			}
		});
		
		JLabel groupsLabel = new JLabel("Group: ");
		JBPanel.add(groupsLabel);
		
		_groupsModel = new DefaultComboBoxModel<>();
		// TODO crear un combobox que use _groupsModel y añadirlo al panel
		
		JComboBox<String> _groupsCB = new JComboBox<String>(_groupsModel);
		JBPanel.add(_groupsCB);
		
		JButton okB = new JButton("OK");
		okB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JSONObject cv = new JSONObject();
				JSONObject js = new JSONObject();
				for(int i = 0; i < _dataTableModel.getRowCount(); i++) { 
					cv.put(_dataTableModel.getValueAt(i, 0).toString(), _dataTableModel.getValueAt(i, 1));
				}
				
				js.put("type", _forceLawsInfo.get(_laws.getSelectedIndex()).getString("type"));
				js.put("data", cv);
				
				_ctrl.setForcesLaws(_groupsCB.getSelectedItem().toString(), js);
				
				_status = 1;
				setVisible(false);
				
			}
		});
		
		JButton cancelB = new JButton("Cancel");
		cancelB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_status = 0;
				setVisible(false);
			}
		});
		
		buttonsPanel.add(cancelB);
		buttonsPanel.add(okB);
		
		mainPanel.add(infoLabel);
		mainPanel.add(tableM);
		mainPanel.add(JBPanel);
		mainPanel.add(buttonsPanel);
		// TODO crear los botones OK y Cancel y añadirlos al panel
		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	private void printtable(DefaultTableModel _dataTableModel2) {
		// TODO Auto-generated method stub

		JSONObject data = _forceLawsInfo.get(0);
		JSONObject datita = (JSONObject) data.get("data");
		Set<String> keyset = datita.keySet();
		
		for(String i : keyset) { 
			String [] rowData = {i, " ", datita.get(i).toString()};
			_dataTableModel.addRow(rowData);
		}
	}


	public int open() {
		if (_groupsModel.getSize() == 0)
			return _status;
		
		// TODO Establecer la posición de la ventana de diálogo de tal manera que se
		// abra en el centro de la ventana principal
		
		//setLocationRelativeTo(null);
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);

		
		pack();
		setVisible(true);
		return _status;

	}


	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		for(BodiesGroup i : groups.values()) {
			_groupsModel.addElement(i.getId());
		}
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		for(BodiesGroup i : groups.values()) {
			_groupsModel.addElement(i.getId());
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		for(BodiesGroup i : groups.values()) {
			_groupsModel.addElement(i.getId());
		}
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
	
	private JSONObject getJSONForceLaw(String desc) {       

		for(JSONObject j: _forceLawsInfo) {
			if(desc.equals(j.getString("desc")))
				return j;
		}
		return null;
	}

}
