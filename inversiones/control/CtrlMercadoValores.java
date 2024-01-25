package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosComunes;
import modelo.DtosMercadoValores;
import vista.Cargar;
import vista.Listado;

public class CtrlMercadoValores implements ActionListener {
	
	private Listado ventana;
	private DtosMercadoValores dtosInversiones;
	private Cargar ventanaComprar;
	private Cargar ventanaVender;
	private Cargar ventanaHistorial;
	private int elemento = -1;
	private boolean nuevaCotizacion = false;

	public CtrlMercadoValores(Listado vista) {
		
		this.ventana = vista;
		this.dtosInversiones = new DtosMercadoValores();
		this.ventana.comboBoxAño.addActionListener(this);
		this.ventana.comboBoxMes.addActionListener(this);
		this.ventana.chkBxPesos.addActionListener(this);
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

		ventana.lblSuma.setVisible(false);
		ventana.txtSuma.setVisible(false);
		ventana.btnNuevo.setText("Compra");
		ventana.btnCargar.setText("Venta");
		ventana.btnCargar.setVisible(true);
		ventana.btnGuardar.setVisible(true);
		ventana.btnGuardar.setEnabled(false);
		ventana.btnCotizar.setVisible(true);
		ventana.comboBoxPago.setVisible(false);
		ventana.comboBoxTipo.setVisible(false);
		ventana.txtBusqueda.setVisible(false);
		ventana.chkBxPesos.setText("Existentes");
		ventana.chkBxDolares.setVisible(false);
		ventana.chkBxEuros.setVisible(false);
		ventana.comboBoxAño.setModel(new DefaultComboBoxModel<String>(dtosInversiones.getListaAños()));
		ventana.comboBoxAño.setSelectedIndex(0);
		ventana.comboBoxMes.setModel(new DefaultComboBoxModel<String>(DtosComunes.getListaMeses("Elija uno")));
		ventana.comboBoxMes.setSelectedIndex(DtosComunes.getMesActual());
		ventana.chkBxDolares.setSelected(true);
		ventana.chkBxEuros.setSelected(true);
		actualizar();
		ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			compra();
			return;
		}
		
		if(e.getSource() == ventana.btnCargar) {
			
			venta();
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
		ventana.btnGuardar.setEnabled(false);
		ventana.btnCotizar.setEnabled(true);
	}
	
	private void actualizar() {
	
		elemento = -1;
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ventana.tabla.doLayout();
		ventana.tabla.setModel(dtosInversiones.getTablaValores((String)ventana.comboBoxAño.getSelectedItem(), 
																ventana.comboBoxMes.getSelectedIndex(), 
																nuevaCotizacion,
																ventana.chkBxPesos.isSelected()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(50);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(50);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(160);
		ventana.tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
		ventana.tabla.getColumnModel().getColumn(2).setMinWidth(40);
		ventana.tabla.getColumnModel().getColumn(2).setMaxWidth(70);
		ventana.tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(2).setCellRenderer(derecha);

		for(int i = 3; i < ventana.tabla.getColumnCount(); i++) {
		
			ventana.tabla.getColumnModel().getColumn(i).setMinWidth(50);
			ventana.tabla.getColumnModel().getColumn(i).setMaxWidth(120);
			ventana.tabla.getColumnModel().getColumn(i).setPreferredWidth(100);
			ventana.tabla.getColumnModel().getColumn(i).setCellRenderer(derecha);
		}
		ventana.txtSuma.setText(dtosInversiones.getSuma());
		ventana.txtCant.setText(dtosInversiones.getCantValores());
		
		if(nuevaCotizacion)
			ventana.irFinalTabla();
	}
	
	private void compra() {
		
		if(ventanaComprar !=null)
			ventanaComprar.dispose();
		ventanaComprar = new Cargar("Carga de compra de acciones, bonos y letras", ventana.getX(), ventana.getY());
		ventanaComprar.btnVolver.addActionListener(this);
		CtrlCompraValores ctrlCargarInversion = new CtrlCompraValores(ventanaComprar);
		ctrlCargarInversion.iniciar();
	}
	
	private void venta() {

		if(ventanaVender != null)
			ventanaVender.dispose();
		ventanaVender = new Cargar("Carga de venta de acciones, bonos y letras", ventana.getX(), ventana.getY());
		ventanaVender.btnVolver.addActionListener(this);
		CtrlVentaValores ctrlVentaValores = new CtrlVentaValores(ventanaVender);
		ctrlVentaValores.iniciar();
	}

	private void detalle() {
   	
		if(elemento == -1 || elemento == ventana.tabla.getRowCount() - 1)
			return;
		
		if(ventanaHistorial != null)
			ventanaHistorial.dispose();
		dtosInversiones.setValor(elemento);
		elemento = -1;
		ventanaHistorial = new Cargar("Detalle de movimientos", ventana.getX(), ventana.getY());
		CtrlDetalleValores ctrlDetalleValores = new CtrlDetalleValores(ventanaHistorial);
		ctrlDetalleValores.iniciar();
	}
	
	private void cotizar() {
		
		ventana.btnGuardar.setEnabled(true);
		ventana.btnCotizar.setEnabled(false);
		nuevaCotizacion = true;
		actualizar();
	}	

	private void guardar() {
		
		nuevaCotizacion = false;
		
		if(!dtosInversiones.guardarCotizaciones(ventana.tabla)) {
			
			JOptionPane.showMessageDialog(ventana, dtosInversiones.getMsgError());
			return;
		}
		ventana.btnGuardar.setEnabled(false);
		actualizar();
	}
}