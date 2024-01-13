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
import modelo.DtosMercadoFiat;
import vista.Cargar;
import vista.Listado;

public class CtrlMercadoFiat implements ActionListener {
	
	private Listado ventana;
	private DtosMercadoFiat dtosMercadoFiat;
	private Cargar ventanaComprar;
	private Cargar ventanaVender;
	private Cargar ventanaHistorial;
	private int elemento = -1;
	private boolean nuevaCotizacion = false;

	public CtrlMercadoFiat(Listado vista) {
		
		this.ventana = vista;
		this.dtosMercadoFiat = new DtosMercadoFiat();
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
		ventana.comboBoxAño.setModel(new DefaultComboBoxModel<>(dtosMercadoFiat.getListaAños()));
		ventana.comboBoxMes.setModel(new DefaultComboBoxModel<>(DtosComunes.getListaMeses("Seleccione uno")));
		ventana.comboBoxMes.setSelectedIndex(DtosComunes.getMesActual());
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
		ventana.setVisibleSegundaTabla();
		
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
		
		if(ventana.isVisible()) {
			
			actualizar();
			ventana.setVisible(true);
		}
		
		if(!ventana.chkBxDolares.isSelected() && !ventana.chkBxEuros.isSelected())
			ventana.chkBxDolares.setSelected(true);
		ventana.btnGuardar.setEnabled(false);
		ventana.btnCotizar.setEnabled(true);
	}
	
	private void actualizar() {
	
		elemento = -1;
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		
		
		ventana.tabla.setModel(dtosMercadoFiat.getListadoOperaciones((String)ventana.comboBoxAño.getSelectedItem(), 
																	ventana.comboBoxMes.getSelectedIndex()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(40);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(70);
		ventana.tabla.getColumnModel().getColumn(1).setPreferredWidth(60);
		ventana.tabla.getColumnModel().getColumn(1).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(2).setMinWidth(40);
		ventana.tabla.getColumnModel().getColumn(2).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
		
		for(int i = 3; i < ventana.tabla.getColumnCount(); i++) {
		
			ventana.tabla.getColumnModel().getColumn(i).setMinWidth(50);
			ventana.tabla.getColumnModel().getColumn(i).setMaxWidth(100);
			ventana.tabla.getColumnModel().getColumn(i).setPreferredWidth(60);
			ventana.tabla.getColumnModel().getColumn(i).setCellRenderer(derecha);
		}
		ventana.tabla1.setModel(dtosMercadoFiat.getTablaCotizaciones((String)ventana.comboBoxAño.getSelectedItem(), 
																ventana.comboBoxMes.getSelectedIndex(), 
																nuevaCotizacion));
		ventana.tabla1.getColumnModel().getColumn(0).setMinWidth(70);
		ventana.tabla1.getColumnModel().getColumn(0).setMaxWidth(100);
		ventana.tabla1.getColumnModel().getColumn(0).setPreferredWidth(80);
		ventana.tabla1.getColumnModel().getColumn(1).setMinWidth(40);
		ventana.tabla1.getColumnModel().getColumn(1).setMaxWidth(70);
		ventana.tabla1.getColumnModel().getColumn(1).setPreferredWidth(60);
		ventana.tabla1.getColumnModel().getColumn(1).setCellRenderer(derecha);
		
		for(int i = 2; i < ventana.tabla1.getColumnCount(); i++) {
		
			ventana.tabla1.getColumnModel().getColumn(i).setMinWidth(50);
			ventana.tabla1.getColumnModel().getColumn(i).setMaxWidth(100);
			ventana.tabla1.getColumnModel().getColumn(i).setPreferredWidth(60);
			ventana.tabla1.getColumnModel().getColumn(i).setCellRenderer(derecha);
		}
		ventana.txtSuma.setText(dtosMercadoFiat.getSuma());
		ventana.txtCant.setText(dtosMercadoFiat.getCantFiat());
	}
	
	private void ingreso() {
		
		if(ventanaComprar !=null)
			ventanaComprar.dispose();
		ventanaComprar = new Cargar("Carga de compra de dólares y euros", ventana.getX(), ventana.getY());
		ventanaComprar.btnVolver.addActionListener(this);
		CtrlCompraFiat ctrlCompraFiat = new CtrlCompraFiat(ventanaComprar);
		ctrlCompraFiat.iniciar();
	}
	
	private void egreso() {

		if(ventanaVender != null)
			ventanaVender.dispose();
		ventanaVender = new Cargar("Carga de venta de dólares y euros", ventana.getX(), ventana.getY());
		ventanaVender.btnVolver.addActionListener(this);
		CtrlVentaFiat ctrlVentaFiat = new CtrlVentaFiat(ventanaVender);
		ctrlVentaFiat.iniciar();
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