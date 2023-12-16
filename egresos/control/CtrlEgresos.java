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
		ventana.comboBoxTipo.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getListaDestinos()));
		ventana.comboBoxTipo.setSelectedIndex(0);
		ventana.comboBoxPago.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getFormasPago()));
		ventana.comboBoxPago.setSelectedIndex(0);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			nuevo();
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
	}
	
	private void editar() {
		
		
	}
	
	private void nuevo() {
		
		if(ventanaNuevoEgreso != null)
			ventanaNuevoEgreso.dispose();
		ventanaNuevoEgreso = new Cargar("Carga de un nuevo egreso", ventana.getX(), ventana.getY());
		CtrlCargarEgreso ctrlCargarEgreso = new CtrlCargarEgreso(ventanaNuevoEgreso);
		ctrlCargarEgreso.iniciar();
	}
}