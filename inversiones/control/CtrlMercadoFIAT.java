package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import modelo.DtosMercadoFiat;
import vista.Cargar;
import vista.Listado;

public class CtrlMercadoFIAT implements ActionListener {
	
	private Listado ventana;
	private DtosMercadoFiat dtosInversiones;
	private Cargar ventanaComprar;
	private Cargar ventanaVender;
	private Cargar ventanaHistorial;
	private int elemento = -1;
	private boolean nuevaCotizacion = false;

	public CtrlMercadoFIAT(Listado vista) {
		
		this.ventana = vista;
		this.dtosInversiones = new DtosMercadoFiat();
		this.ventana.comboBoxAño.addActionListener(this);
		this.ventana.comboBoxMes.addActionListener(this);
		this.ventana.chkBxDolares.addActionListener(this);
		this.ventana.chkBxEuros.addActionListener(this);
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnCargar.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnCotizar.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	detalle();
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.comboBoxPago.setVisible(false);
		ventana.comboBoxTipo.setVisible(false);
		ventana.chkBxPesos.setVisible(false);
		ventana.txtBusqueda.setVisible(false);
		ventana.btnNuevo.setText("Ingreso");
		ventana.btnCargar.setVisible(true);
		ventana.btnCargar.setText("Egreso");
		ventana.btnCotizar.setVisible(true);
		ventana.btnGuardar.setVisible(true);
		ventana.lblCotizacion.setVisible(true);
		ventana.lblDolar.setVisible(true);
		ventana.txtDolar.setVisible(true);
		ventana.lblEuro.setVisible(true);
		ventana.txtEuro.setVisible(true);
		ventana.chkBxDolares.setSelected(true);
		ventana.chkBxEuros.setSelected(true);
		
		
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			ingreso();
			return;
		}
		
		if(e.getSource() == ventana.btnCargar) {
			
			egreso();
			return;
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
			return;
		}
		
		if(e.getSource() == ventana.btnCotizar) {
		
			cotizar();
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
			
			if(ventanaComprar != null)
				ventanaComprar.dispose();
			
			if(ventanaVender != null)
				ventanaVender.dispose();
			
			if(ventanaHistorial != null)
				ventanaHistorial.dispose();
			ventana.dispose();
			return;
		}
		nuevaCotizacion = false;
		
		if(ventana.isVisible())
			actualizar();
		
		if(!ventana.chkBxDolares.isSelected() && !ventana.chkBxEuros.isSelected())
			ventana.chkBxDolares.setSelected(true);
		ventana.btnGuardar.setEnabled(false);
		ventana.btnCotizar.setEnabled(true);
	}
	
	private void actualizar() {
	
		elemento = -1;
		ventana.btnGuardar.setEnabled(false);


	}
	
	private void ingreso() {
		
		if(ventanaComprar !=null)
			ventanaComprar.dispose();
		ventanaComprar = new Cargar("Carga de compra de acciones, bonos y letras", ventana.getX(), ventana.getY());

	}
	
	private void egreso() {

		if(ventanaVender != null)
			ventanaVender.dispose();
		ventanaVender = new Cargar("Carga de venta de acciones, bonos y letras", ventana.getX(), ventana.getY());

	}

	private void detalle() {
   	
		if(elemento == -1)
			return;
		
		if(ventanaHistorial != null)
			ventanaHistorial.dispose();

	}
	
	private void cotizar() {
		
		ventana.btnGuardar.setEnabled(true);
		ventana.btnCotizar.setEnabled(false);
		nuevaCotizacion = true;
		actualizar();
	}	

	private void guardar() {
		

		actualizar();
	}
}