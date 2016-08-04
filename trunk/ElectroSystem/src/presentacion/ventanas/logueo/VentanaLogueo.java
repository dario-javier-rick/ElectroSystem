package presentacion.ventanas.logueo;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class VentanaLogueo extends JFrame {

	private static final long serialVersionUID = -4417480508461644016L;
	private JPanel contentPane;
	private JTextField tfUsuario;
	private JPasswordField pfPassword;
	private JButton btnAceptar;
	private JMenuItem mntmreestablecer;
	private JMenuItem mntmIngresarClave;
	private JLabel label_2;
	private JLabel label_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VentanaLogueo frame = new VentanaLogueo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaLogueo() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaLogueo.class.getResource("/icons/service.png")));

		try {
//			UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
			UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}

		setTitle("Acceso a usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 322, 352);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfUsuario = new JTextField();
		tfUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfUsuario.setBounds(111, 158, 121, 22);
		tfUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(tfUsuario, popupMenu);

		mntmreestablecer = new JMenuItem("<html>Reestablecer<br>superusuarios<html>");
		mntmreestablecer.setIcon(new ImageIcon(VentanaLogueo.class.getResource("/recursos/reestablecer.png")));
		popupMenu.add(mntmreestablecer);

		mntmIngresarClave = new JMenuItem("<html>Ingresar clave<br>(Solo superusuarios)</html>");
		popupMenu.add(mntmIngresarClave);

		pfPassword = new JPasswordField();
		pfPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pfPassword.setBounds(111, 198, 121, 22);
		pfPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(pfPassword);

		btnAceptar = new JButton("Ingresar");
		btnAceptar.setBounds(78, 242, 154, 29);
		btnAceptar.setIcon(null);
		btnAceptar.setHorizontalTextPosition(SwingConstants.RIGHT);
		contentPane.add(btnAceptar);

		JLabel label = new JLabel("");
		label.setBounds(113, 11, 107, 121);
		label.setIcon(new ImageIcon(VentanaLogueo.class.getResource("/icons/service.png")));
		contentPane.add(label);

		label_2 = new JLabel("");
		label_2.setBounds(78, 158, 25, 22);
		label_2.setIcon(new ImageIcon(VentanaLogueo.class.getResource("/icons/usr.png")));
		contentPane.add(label_2);

		label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(VentanaLogueo.class.getResource("/icons/pwd.png")));
		label_1.setBounds(78, 198, 25, 22);
		contentPane.add(label_1);
	}

	public JMenuItem getMntmIngresarClave() {
		return mntmIngresarClave;
	}

	public JMenuItem getMntmreestablecer() {
		return mntmreestablecer;
	}

	public JTextField getTfUsuario() {
		return tfUsuario;
	}

	public JPasswordField getPfPassword() {
		return pfPassword;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
