package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import modelo.DtosConfiguracion;

public class Principal extends VentanaModelo{

	private static final long serialVersionUID = 1L;
	private JPanel principal;
	public JButton btnEgresos;
	public JButton btnIngresos;
	public JButton btnInversiones;
	public JButton btnAnalisis;
	public JButton btnSalir;
	
	public Principal(String nombreVentana) {
		
		super(nombreVentana);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		principal = new JPanel();
		principal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(10, 10, 640, 480);
		setContentPane(principal);
		principal.setLayout(null);
		
		JLabel lblEgresos = new JLabel("Egresos");
		lblEgresos.setHorizontalAlignment(SwingConstants.CENTER);
		lblEgresos.setBounds(35, 30, 104 , 20);
		principal.add(lblEgresos);	

		btnEgresos = new JButton("");
		btnEgresos.setBounds(35, 50, 104, 94);
		btnEgresos.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Compras.png"));
		principal.add(btnEgresos);
		
		JLabel lblIngresos = new JLabel("Ingresos");
		lblIngresos.setHorizontalAlignment(SwingConstants.CENTER);
		lblIngresos.setBounds(245, 30, 104 , 20);
		principal.add(lblIngresos);
		
		btnIngresos = new JButton("");
		btnIngresos.setBounds(245, 50, 104, 94);
		btnIngresos.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Pagos.png"));
		principal.add(btnIngresos);
		
		JLabel lblInversiones = new JLabel("Inversiones");
		lblInversiones.setHorizontalAlignment(SwingConstants.CENTER);
		lblInversiones.setBounds(450, 30, 104, 20);
		principal.add(lblInversiones);
		
		btnInversiones = new JButton("");
		btnInversiones.setBounds(450, 50, 104, 94);
		btnInversiones.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Estadisticas.png"));
		principal.add(btnInversiones);		
		
		JLabel lblAnalisis = new JLabel("Análisis");
		lblAnalisis.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnalisis.setBounds(35, 190, 104, 20);
		principal.add(lblAnalisis);
		
		btnAnalisis = new JButton("");
		btnAnalisis.setBounds(35, 210, 104, 94);
		btnAnalisis.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Admin.png"));
		principal.add(btnAnalisis);

		btnSalir = new JButton("");
		btnSalir.setBounds(538, 361, 76, 69);
		btnSalir.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Salir.png"));
		principal.add(btnSalir);
	}
}