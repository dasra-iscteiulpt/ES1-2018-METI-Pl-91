import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WindowMessage {

	// VARIABLES
	private JFrame windowFrame;
	private JLabel dateM;
	private JLabel fromM;
	private JLabel titleM;
	private JTextField contentM;
	private JButton sendM;
	private ArrayList<JPanel> panels;

	// CONSTRUCTOR
	public WindowMessage(String date, String from, String title, String content, String canal) {
		windowFrame = new JFrame(canal);
		dateM = new JLabel("Date: " + date);
		fromM = new JLabel("From: " + from);
		titleM = new JLabel("Subject: " + title);
		contentM = new JTextField(content);
		sendM = new JButton("Reply");
		configWindow();
		endConfigWindow();
	}

	// AUXILIARY METHODS
	private void addPanels() {
		panels = new ArrayList<>();
		panels.add(new JPanel()); // 0 SOUTH
		panels.add(new JPanel()); // 1 WEST
		panels.add(new JPanel()); // 2 EAST
		panels.add(new JPanel()); // 3 NORTH
	}

	/** 
	 * Construction of the main window structure
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	private void configWindow() {
		addPanels();
		windowFrame.setSize(700, 200);

		// JPANEL CONFIGURATION IN WINDOWFRAME
		windowFrame.add(panels.get(0), BorderLayout.SOUTH);
		windowFrame.add(panels.get(1), BorderLayout.WEST);
		windowFrame.add(panels.get(2), BorderLayout.EAST);

		windowFrame.add(panels.get(3), BorderLayout.NORTH);

		// COMPONENTS CONFIGURATION
		panels.get(3).add(dateM);
		panels.get(3).add(fromM);
		panels.get(3).add(titleM);
		windowFrame.add(contentM, BorderLayout.CENTER);
		panels.get(0).add(sendM);
	}

	/** 
	 * Construction of the main window structure
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	private void endConfigWindow() {
		// WINDOW FRAME CONFIGURATION
		windowFrame.setLocationRelativeTo(null);
		windowFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		windowFrame.setResizable(false);
		windowFrame.validate();
		windowFrame.setVisible(true);

		sendM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendEmail sMail = new SendEmail();
				String emailToFinal = new String ();

				// JOPTION PANE
				JTextField emailTo = new JTextField();
				JTextField contentTo = new JTextField();
				if(fromM.getText().contains("<")) {
					String fromMOnlyEmail = fromM.getText().split("<")[1];
					emailToFinal = fromMOnlyEmail.substring(0, fromMOnlyEmail.length()-1).toLowerCase();
				}
				else {
					String fromMOnlyEmail = fromM.getText().split(" ")[1];
					emailToFinal = fromMOnlyEmail.substring(0, fromMOnlyEmail.length()).toLowerCase();
				}
				emailTo.setText(emailToFinal);
				emailTo.setEditable(false);
				Object[] f = {"E-mail to: ", emailTo, "Content reply: ", contentTo};
				int okOrCancel = JOptionPane.showConfirmDialog(null, f, "This is a header", JOptionPane.OK_CANCEL_OPTION);

				String toEmail = emailTo.getText().toString();
				String toContent = contentTo.getText().toString();

				try {
					if(okOrCancel == JOptionPane.CANCEL_OPTION) {
						windowFrame.setVisible(false);
					} else {
						if(!toEmail.isEmpty() && !toContent.isEmpty()) {
							String fromEmail = ReadXMLfile.userData[0];
							String fromPWEmail = ReadXMLfile.userData[1];
							int resultMail = sMail.senderMail(toEmail, fromEmail, fromPWEmail, toContent, titleM.getText());
							if(resultMail == 1) {
								JOptionPane.showMessageDialog(null, "E-mail sent to: " + toEmail + " and content is " + toContent);
								windowFrame.setVisible(false);
							} else {
								JOptionPane.showMessageDialog(null, "E-mail not sent. Please try again.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "There are empty fields.");
							sendM.doClick();
						}
					}
				} catch (Exception excep) {
					System.out.println("Button cancel." + excep);
				}
			}
		});
	}

}