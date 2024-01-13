package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosComunes;
import modelo.DtosIngresos;
import vista.Cargar;
import vista.Listado;

public class CtrlIngresos implements ActionListener {
	
	private Listado ventana;
	private DtosIngresos dtosIngreso;
	private int elemento = -1;
	private Cargar ventanaNuevoIngreso;
	private Cargar ventanaEditarIngreso;

	public CtrlIngresos(Listado vista) {
		
		this.ventana = vista;
		this.dtosIngreso = new DtosIngresos();
		this.ventana.comboBoxAño.addActionListener(this);
		this.ventana.comboBoxMes.addActionListener(this);
		this.ventana.comboBoxTipo.addActionListener(this);
		this.ventana.comboBoxPago.addActionListener(this);
		this.ventana.chkBxPesos.addActionListener(this);
		this.ventana.chkBxDolares.addActionListener(this);
		this.ventana.chkBxEuros.addActionListener(this);
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	editar();
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.comboBoxAño.setModel(new DefaultComboBoxModel<String>(dtosIngreso.getListaAños()));
		ventana.comboBoxAño.setSelectedIndex(0);
		ventana.comboBoxMes.setModel(new DefaultComboBoxModel<String>(DtosComunes.getListaMeses("Todos")));
		ventana.comboBoxMes.setSelectedIndex(DtosComunes.getMesActual());
		ventana.comboBoxTipo.setModel(new DefaultComboBoxModel<String>(dtosIngreso.getListaConceptos("Todos")));
		ventana.comboBoxTipo.setSelectedIndex(0);
		ventana.comboBoxPago.setModel(new DefaultComboBoxModel<String>(dtosIngreso.getFormasCobro("Todos")));
		ventana.comboBoxPago.setSelectedIndex(0);
		ventana.chkBxDolares.setSelected(true);
		ventana.chkBxEuros.setSelected(true);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			nuevo();
			return;
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				f.printStackTrace();
			}
			return;
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			if(ventanaEditarIngreso != null)
				ventanaEditarIngreso.dispose();
			
			if(ventanaNuevoIngreso != null)
				ventanaNuevoIngreso.dispose();
			ventana.dispose();
			return;
		}
		
		if(ventana.isVisible()) {
			
			actualizar();
			ventana.setVisible(true);
		}
	}
	
	private void actualizar() {
		
		if(!ventana.chkBxPesos.isSelected() && !ventana.chkBxDolares.isSelected() && !ventana.chkBxEuros.isSelected())
			ventana.chkBxPesos.setSelected(true);
		String monedas = "";
		
		if(ventana.chkBxPesos.isSelected())
			monedas = "P";
		
		if(ventana.chkBxDolares.isSelected())
			monedas += "U";
		
		if(ventana.chkBxEuros.isSelected())
			monedas += "E";
		
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.setModel(dtosIngreso.getTablaIngresos((String)ventana.comboBoxAño.getSelectedItem(), 
														  ventana.comboBoxMes.getSelectedIndex(), 
														  ventana.comboBoxTipo.getSelectedIndex(), 
														  ventana.comboBoxPago.getSelectedIndex(), 
														  monedas ,
														  ventana.txtBusqueda.getText()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(90);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
		ventana.tabla.getColumnModel().getColumn(4).setMinWidth(80);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(150);
		ventana.tabla.getColumnModel().getColumn(4).setPreferredWidth(90);
		ventana.tabla.getColumnModel().getColumn(4).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(5).setMinWidth(80);
		ventana.tabla.getColumnModel().getColumn(5).setMaxWidth(150);
		ventana.tabla.getColumnModel().getColumn(5).setPreferredWidth(90);
		ventana.tabla.getColumnModel().getColumn(5).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(6).setMinWidth(80);
		ventana.tabla.getColumnModel().getColumn(6).setMaxWidth(110);
		ventana.tabla.getColumnModel().getColumn(6).setPreferredWidth(90);
		ventana.tabla.getColumnModel().getColumn(6).setCellRenderer(derecha);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.txtCant.setText(dtosIngreso.getCantidadElementos());
		ventana.txtSuma.setText(dtosIngreso.getSuma());
		elemento = -1;
	}
	
	private void editar() {
		
		if(elemento == -1)
			return;
		dtosIngreso.seleccionarIngreso(elemento);
		elemento = -1;
		
		if(ventanaEditarIngreso != null)
			ventanaEditarIngreso.dispose();
		ventanaEditarIngreso = new Cargar("Editar ingreso de dinero", ventana.getX(), ventana.getY());
		ventanaEditarIngreso.btnVolver.addActionListener(this);
		CtrlEditarIngreso ctrlEditarIngreso = new CtrlEditarIngreso(ventanaEditarIngreso);
		ctrlEditarIngreso.iniciar();
	}
	
	private void nuevo() {
		
		if(ventanaNuevoIngreso != null)
			ventanaNuevoIngreso.dispose();
		ventanaNuevoIngreso = new Cargar("Carga de un nuevo ingreso de dinero", ventana.getX(), ventana.getY());
		ventanaNuevoIngreso.btnVolver.addActionListener(this);
		CtrlCargarIngreso ctrlCargarIngreso = new CtrlCargarIngreso(ventanaNuevoIngreso);
		ctrlCargarIngreso.iniciar();
	}
}