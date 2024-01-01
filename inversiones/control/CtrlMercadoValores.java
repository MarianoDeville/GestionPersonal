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
import javax.swing.table.DefaultTableCellRenderer;

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

	public CtrlMercadoValores(Listado vista) {
		
		this.ventana = vista;
		this.dtosInversiones = new DtosMercadoValores();
		this.ventana.comboBoxAño.addActionListener(this);
		this.ventana.comboBoxMes.addActionListener(this);
		this.ventana.chkBxPesos.addActionListener(this);
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
		        if(e.getClickCount() == 1) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        } else if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	detalle();
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.btnNuevo.setText("Compra");
		ventana.btnCargar.setText("Venta");
		ventana.btnCargar.setVisible(true);
		ventana.btnGuardar.setVisible(true);
		ventana.btnGuardar.setEnabled(false);
		ventana.btnCotizar.setVisible(true);
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
		
		if(ventana.isVisible())
			actualizar();
	}
	
	private void actualizar() {
	
		elemento = -1;
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.setModel(dtosInversiones.getTablaValores((String)ventana.comboBoxAño.getSelectedItem(), ventana.comboBoxMes.getSelectedIndex()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(0).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(2).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(2).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
		
		
		
		
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
		
		if(elemento == -1) {
			JOptionPane.showMessageDialog(ventana, "Debe seleccionar un elemento");
			return;
		}
		
		if(ventanaVender != null)
			ventanaVender.dispose();
		ventanaVender = new Cargar("Carga de venta de acciones, bonos y letras", ventana.getX(), ventana.getY());
		
		
		
	}

	private void detalle() {
//////////////////////Con un doble click abro una ventana con el historial de mobimientos del instrumento seleccionado////////////////////////////////       	
		if(ventanaHistorial != null)
			ventanaHistorial.dispose();
		
	
		
	}
	
	private void cotizar() {
		
		

	}	

	private void guardar() {
		
		
		
	}
}