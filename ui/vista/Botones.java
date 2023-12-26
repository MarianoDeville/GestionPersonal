package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import modelo.DtosConfiguracion;

public class Botones extends VentanaModelo{

	private static final long serialVersionUID = 1L;
	private JPanel principal;
	public JLabel lbl1A;
	public JLabel lbl1B;
	public JLabel lbl1C;
	public JLabel lbl2A;
	public JLabel lbl2B;
	public JLabel lbl2C;
	public JButton btn1A;
	public JButton btn1B;
	public JButton btn1C;
	public JButton btn2A;
	public JButton btn2B;
	public JButton btn2C;
	public JButton btnSalir;
	
	public Botones(String nombreVentana) {
		
		super(nombreVentana);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		principal = new JPanel();
		principal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(10, 10, 640, 480);
		setContentPane(principal);
		principal.setLayout(null);
		
		lbl1A = new JLabel();
		lbl1A.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1A.setBounds(30, 30, 114 , 20);
		principal.add(lbl1A);	

		btn1A = new JButton();
		btn1A.setBounds(35, 50, 104, 94);
		principal.add(btn1A);
		btn1A.setVisible(false);
		
		lbl1B = new JLabel();
		lbl1B.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1B.setBounds(240, 30, 114 , 20);
		principal.add(lbl1B);
		
		btn1B = new JButton();
		btn1B.setBounds(245, 50, 104, 94);
		principal.add(btn1B);
		btn1B.setVisible(false);
		
		lbl1C = new JLabel();
		lbl1C.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1C.setBounds(445, 30, 114, 20);
		principal.add(lbl1C);
		
		btn1C = new JButton();
		btn1C.setBounds(450, 50, 104, 94);
		principal.add(btn1C);		
		btn1C.setVisible(false);
		
		lbl2A = new JLabel();
		lbl2A.setHorizontalAlignment(SwingConstants.CENTER);
		lbl2A.setBounds(30, 190, 114, 20);
		principal.add(lbl2A);
		
		btn2A = new JButton();
		btn2A.setBounds(35, 210, 104, 94);
		principal.add(btn2A);
		btn2A.setVisible(false);
		
		lbl2B = new JLabel();
		lbl2B.setHorizontalAlignment(SwingConstants.CENTER);
		lbl2B.setBounds(240, 190, 114 , 20);
		principal.add(lbl2B);
		
		btn2B = new JButton();
		btn2B.setBounds(245, 210, 104, 94);
		principal.add(btn2B);
		btn2B.setVisible(false);
		
		lbl2C = new JLabel();
		lbl2C.setHorizontalAlignment(SwingConstants.CENTER);
		lbl2C.setBounds(445, 190, 114, 20);
		principal.add(lbl2C);
		
		btn2C = new JButton();
		btn2C.setBounds(450, 210, 104, 94);
		principal.add(btn2C);	
		btn2C.setVisible(false);
		
		btnSalir = new JButton("");
		btnSalir.setBounds(538, 361, 76, 69);
		btnSalir.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Salir.png"));
		principal.add(btnSalir);
	}
}