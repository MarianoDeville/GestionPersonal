package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosEgresos;
import vista.Cargar;
import vista.Listado;
import vista.Resumen;

public class CtrlEgresos implements ActionListener {
	
	private Listado ventana;
	private DtosEgresos dtosEgreso;
	private int elemento = -1;
	private Cargar ventanaNuevoEgreso;
	private Cargar ventanaEditarEgreso;
	private Resumen ventanaResumen;

	public CtrlEgresos(Listado vista) {
		
		this.ventana = vista;
		this.dtosEgreso = new DtosEgresos();
		this.ventana.comboBoxAño.addActionListener(this);
		this.ventana.comboBoxMes.addActionListener(this);
		this.ventana.comboBoxTipo.addActionListener(this);
		this.ventana.comboBoxPago.addActionListener(this);
		this.ventana.chkBxPesos.addActionListener(this);
		this.ventana.chkBxDolares.addActionListener(this);
		this.ventana.chkBxEuros.addActionListener(this);
		this.ventana.chkBxFinanciado.addActionListener(this);
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnCargar.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.txtBusqueda.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
	
				actualizar();
			}
		});
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

		ventana.btnCargar.setText("Resumen");
		ventana.btnCargar.setVisible(true);
		ventana.comboBoxAño.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getListaAños()));
		ventana.comboBoxAño.setSelectedIndex(0);
		ventana.comboBoxMes.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getListaMeses()));
		ventana.comboBoxMes.setSelectedIndex(dtosEgreso.getMesActual());
		ventana.comboBoxTipo.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getListaDestinos("Todos")));
		ventana.comboBoxTipo.setSelectedIndex(0);
		ventana.comboBoxPago.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getFormasPago("Todos")));
		ventana.comboBoxPago.setSelectedIndex(0);
		ventana.chkBxDolares.setSelected(true);
		ventana.chkBxEuros.setSelected(true);
		ventana.chkBxFinanciado.setVisible(true);
		ventana.chkBxFinanciado.setSelected(true);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			nuevo();
			return;
		}
		
		if(e.getSource() == ventana.btnCargar) {
			
			resumen();
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
			
			if(ventanaEditarEgreso != null)
				ventanaEditarEgreso.dispose();
			
			if(ventanaNuevoEgreso != null)
				ventanaNuevoEgreso.dispose();
			
			if(ventanaResumen != null)
				ventanaResumen.dispose();
			ventana.dispose();
			return;
		}
		
		if(ventana.isVisible())
			actualizar();
	}
	
	private void actualizar() {
		
		ventana.btnCargar.setEnabled(ventana.comboBoxMes.getSelectedIndex() == 0? false:true);
		
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
		ventana.tabla.setModel(dtosEgreso.getTablaEgresos((String)ventana.comboBoxAño.getSelectedItem(), 
														  ventana.comboBoxMes.getSelectedIndex(), 
														  ventana.comboBoxTipo.getSelectedIndex(), 
														  ventana.comboBoxPago.getSelectedIndex(), 
														  monedas ,
														  ventana.txtBusqueda.getText(), 
														  ventana.chkBxFinanciado.isSelected()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(3).setMinWidth(80);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(150);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(90);
		ventana.tabla.getColumnModel().getColumn(3).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(4).setMinWidth(80);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(150);
		ventana.tabla.getColumnModel().getColumn(4).setPreferredWidth(90);
		ventana.tabla.getColumnModel().getColumn(4).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(5).setMinWidth(100);
		ventana.tabla.getColumnModel().getColumn(5).setMaxWidth(150);
		ventana.tabla.getColumnModel().getColumn(5).setPreferredWidth(110);
		ventana.tabla.getColumnModel().getColumn(5).setCellRenderer(derecha);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.txtCant.setText(dtosEgreso.getCantidadElementos());
		ventana.txtSuma.setText(dtosEgreso.getSuma());
		elemento = -1;
	}
	
	private void editar() {
		
		if(elemento == -1)
			return;
		dtosEgreso.seleccionarEgreso(elemento);
		elemento = -1;
		
		if(ventanaEditarEgreso != null)
			ventanaEditarEgreso.dispose();
		ventanaEditarEgreso = new Cargar("Editar egreso de dinero", ventana.getX(), ventana.getY());
		ventanaEditarEgreso.btnVolver.addActionListener(this);
		CtrlEditarEgreso ctrlEditarEgreso = new CtrlEditarEgreso(ventanaEditarEgreso);
		ctrlEditarEgreso.iniciar();
	}
	
	private void nuevo() {
		
		if(ventanaNuevoEgreso != null)
			ventanaNuevoEgreso.dispose();
		ventanaNuevoEgreso = new Cargar("Carga de un nuevo egreso de dinero", ventana.getX(), ventana.getY());
		ventanaNuevoEgreso.btnVolver.addActionListener(this);
		CtrlCargarEgreso ctrlCargarEgreso = new CtrlCargarEgreso(ventanaNuevoEgreso);
		ctrlCargarEgreso.iniciar();
	}
	
	private void resumen() {
		
		if(ventanaResumen != null)
			ventanaResumen.dispose();
		dtosEgreso.setAño((String)ventana.comboBoxAño.getSelectedItem());
		dtosEgreso.setMes(ventana.comboBoxMes.getSelectedIndex());
		ventanaResumen = new Resumen("Resumen de egresos", ventana.getX(), ventana.getY());
		CtrlResumen ctrlResumen = new CtrlResumen(ventanaResumen);
		ctrlResumen.iniciar();
	}
}