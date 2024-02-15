package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosComunes;
import modelo.DtosMercadoInmobiliario;
import vista.Cargar;
import vista.Listado;

public class CtrlMercadoInmobiliario implements ActionListener {
	
	private Listado ventana;
	private DtosMercadoInmobiliario dtosInversiones;
	private Cargar ventanaComprar;
	private Cargar ventanaVender;
	private Cargar ventanaHistorial;
	private int elemento = -1;

	public CtrlMercadoInmobiliario(Listado vista) {
		
		this.ventana = vista;
		this.dtosInversiones = new DtosMercadoInmobiliario();
		this.ventana.comboBoxAño.addActionListener(this);
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnCargar.addActionListener(this);
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

		ventana.comboBoxMes.setVisible(false);
		ventana.comboBoxPago.setVisible(false);
		ventana.comboBoxTipo.setVisible(false);
		ventana.chkBxPesos.setVisible(false);
		ventana.chkBxDolares.setVisible(false);
		ventana.chkBxEuros.setVisible(false);
		ventana.txtBusqueda.setVisible(false);
		ventana.btnNuevo.setText("Cargar");
		ventana.btnCargar.setText("Venta");
		ventana.btnCargar.setVisible(true);
		ventana.comboBoxAño.setModel(new DefaultComboBoxModel<String>(dtosInversiones.getListaAños()));
		ventana.comboBoxAño.setSelectedIndex(0);
		ventana.comboBoxMes.setModel(new DefaultComboBoxModel<String>(DtosComunes.getListaMeses("Elija uno")));
		ventana.comboBoxMes.setSelectedIndex(DtosComunes.getMesActual());
		ventana.chkBxDolares.setSelected(true);
		ventana.chkBxEuros.setSelected(true);
		actualizar();
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
		
		if(ventana.isVisible()) {
			
			actualizar();
			ventana.setVisible(true);
		}
	}
	
	private void actualizar() {
	
		elemento = -1;
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ventana.tabla.doLayout();
		ventana.tabla.setModel(dtosInversiones.getTablaValores((String)ventana.comboBoxAño.getSelectedItem(), 
																ventana.comboBoxMes.getSelectedIndex()));
/*		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(50);
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
		
			ventana.tabla.getColumnModel().getColumn(i).setMinWidth(30);
			ventana.tabla.getColumnModel().getColumn(i).setMaxWidth(120);
			ventana.tabla.getColumnModel().getColumn(i).setCellRenderer(derecha);
			
			if(nuevaCotizacion && i < ventana.tabla.getColumnCount() -2)	
				ventana.tabla.getColumnModel().getColumn(i).setPreferredWidth(30);
			else
				ventana.tabla.getColumnModel().getColumn(i).setPreferredWidth(100);
		}
		ventana.txtSuma.setText(dtosInversiones.getSuma());
		ventana.txtCant.setText(dtosInversiones.getCantValores());
*/		
	}
	
	private void compra() {
		
		if(ventanaComprar !=null)
			ventanaComprar.dispose();
		ventanaComprar = new Cargar("Carga de compra de terrenos / aporte capital", ventana.getX(), ventana.getY());
		ventanaComprar.btnVolver.addActionListener(this);
		CtrlCompraInmobiliario ctrlCompraInmobiliario = new CtrlCompraInmobiliario(ventanaComprar);
		ctrlCompraInmobiliario.iniciar();
	}
	
	private void venta() {

		if(ventanaVender != null)
			ventanaVender.dispose();
		ventanaVender = new Cargar("Carga de venta de terrenos", ventana.getX(), ventana.getY());
		ventanaVender.btnVolver.addActionListener(this);
	}

	private void detalle() {
   	
		if(elemento == -1 || elemento == ventana.tabla.getRowCount() - 1)
			return;
		
		if(ventanaHistorial != null)
			ventanaHistorial.dispose();
		dtosInversiones.setPropiedad(elemento);
		elemento = -1;
		ventanaHistorial = new Cargar("Detalle de movimientos", ventana.getX(), ventana.getY());
	}
}