package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import modelo.DtosEgresos;
import vista.Cargar;
import vista.Listado;

public class CtrlEgresos implements ActionListener {
	
	private Listado ventana;
	private DtosEgresos dtosEgreso;
	private int elemento = -1;
	private Cargar ventanaNuevoEgreso;
	private Cargar ventanaEditarEgreso;

	public CtrlEgresos(Listado vista) {
		
		this.ventana = vista;
		this.dtosEgreso = new DtosEgresos();
		this.ventana.comboBoxAño.addActionListener(this);
		this.ventana.comboBoxMes.addActionListener(this);
		this.ventana.comboBoxTipo.addActionListener(this);
		this.ventana.comboBoxPago.addActionListener(this);
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

		ventana.comboBoxAño.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getListaAños()));
		ventana.comboBoxAño.setSelectedIndex(0);
		ventana.comboBoxMes.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getListaMeses()));
		ventana.comboBoxMes.setSelectedIndex(dtosEgreso.getMesActual());
		ventana.comboBoxTipo.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getListaDestinos("Todos")));
		ventana.comboBoxTipo.setSelectedIndex(0);
		ventana.comboBoxPago.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getFormasPago("Todos")));
		ventana.comboBoxPago.setSelectedIndex(0);
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
			
			ventana.dispose();
			return;
		}
		
		if(ventana.isVisible())
			actualizar();
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosEgreso.getTablaEgresos((String)ventana.comboBoxAño.getSelectedItem(), 
														  ventana.comboBoxMes.getSelectedIndex(), 
														  ventana.comboBoxTipo.getSelectedIndex(), 
														  ventana.comboBoxPago.getSelectedIndex()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(3).setMinWidth(100);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(150);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(110);
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
		ventanaEditarEgreso = new Cargar("Editar egreso", ventana.getX(), ventana.getY());
		ventanaEditarEgreso.btnVolver.addActionListener(this);
		CtrlEditarEgreso ctrlEditarEgreso = new CtrlEditarEgreso(ventanaEditarEgreso);
		ctrlEditarEgreso.iniciar();
	}
	
	private void nuevo() {
		
		if(ventanaNuevoEgreso != null)
			ventanaNuevoEgreso.dispose();
		ventanaNuevoEgreso = new Cargar("Carga de un nuevo egreso", ventana.getX(), ventana.getY());
		ventanaNuevoEgreso.btnVolver.addActionListener(this);
		CtrlCargarEgreso ctrlCargarEgreso = new CtrlCargarEgreso(ventanaNuevoEgreso);
		ctrlCargarEgreso.iniciar();
	}
}