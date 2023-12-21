package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import modelo.DtosInversiones;
import vista.Cargar;
import vista.Listado;

public class CtrlInversiones implements ActionListener {
	
	private Listado ventana;
	private DtosInversiones dtosInversiones;
	private Cargar ventanaNuevaInversion;
	private int elemento = -1;


	public CtrlInversiones(Listado vista) {
		
		this.ventana = vista;
		this.dtosInversiones = new DtosInversiones();
		this.ventana.comboBoxAño.addActionListener(this);
		this.ventana.comboBoxMes.addActionListener(this);
		this.ventana.chkBxPesos.addActionListener(this);
		this.ventana.chkBxDolares.addActionListener(this);
		this.ventana.chkBxEuros.addActionListener(this);
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnCargar.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.btnCargar.setVisible(true);
		ventana.btnGuardar.setVisible(true);
		ventana.btnGuardar.setEnabled(false);
		ventana.comboBoxPago.setVisible(false);
		ventana.comboBoxTipo.setVisible(false);
		ventana.txtBusqueda.setVisible(false);
		ventana.comboBoxAño.setModel(new DefaultComboBoxModel<String>(dtosInversiones.getListaAños()));
		ventana.comboBoxAño.setSelectedIndex(0);
		ventana.comboBoxMes.setModel(new DefaultComboBoxModel<String>(dtosInversiones.getListaMeses()));
		ventana.comboBoxMes.setSelectedIndex(dtosInversiones.getMesActual());
		ventana.chkBxDolares.setSelected(true);
		ventana.chkBxEuros.setSelected(true);
		actualizar();
		ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			agregarNuevaAccion();
			return;
		}
		
		if(e.getSource() == ventana.btnCargar) {
			
			cargarCotizacionDiaria();
			return;
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardarCotizaciones();
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
			
			if(ventanaNuevaInversion != null)
				ventanaNuevaInversion.dispose();
			ventana.dispose();
			return;
		}
		
		if(ventana.isVisible())
			actualizar();
	}
	
	private void actualizar() {
		
	
		elemento = -1;
	}
	
	private void agregarNuevaAccion() {
		
		if(ventanaNuevaInversion !=null)
			ventanaNuevaInversion.dispose();
		ventanaNuevaInversion = new Cargar("Carga de nueva inversión", ventana.getX(), ventana.getY());
		ventana.btnVolver.addActionListener(this);
		CtrlCargarInversion ctrlCargarInversion = new CtrlCargarInversion(ventanaNuevaInversion);
		ctrlCargarInversion.iniciar();
	}
	
	private void cargarCotizacionDiaria() {
		
		
	}
	
	private void guardarCotizaciones() {
		
		
		
	}
}