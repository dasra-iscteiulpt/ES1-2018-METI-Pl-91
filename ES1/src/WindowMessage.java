import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WindowMessage {

		// ATRIBUTOS
		private JFrame windowFrame;
		private JLabel dateM;
		private JLabel fromM;
		private JLabel titleM;
		private JTextArea contentM;
		private JButton sendM;
		private ArrayList<JPanel> panels;

		public WindowMessage(String date, String from, String title, String content, String canal) {
			windowFrame = new JFrame(canal);
			dateM = new JLabel("Date: " + date);
			fromM = new JLabel("From: " + from);
			titleM = new JLabel("Subject: " + title);
			contentM = new JTextArea(content);
			if(canal.equals("E-Mail")) {
				sendM = new JButton("Reply");
			} else if(canal.equals("Twitter")) {
				sendM = new JButton("Retweet");
			} else if(canal.equals("Facebook")) {
				sendM = new JButton("Comment");
			}
			configWindow();
			endConfigWindow();
		}
		
		// M�TODOS AUXILIARES
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
			
			contentM.setPreferredSize(new Dimension(200,20));
			contentM.setLineWrap(true);
			contentM.setWrapStyleWord(true);
			contentM.setEditable(false);
						
			// CONFIGURA��O JPANEL NA WINDOWFRAME
			windowFrame.add(panels.get(0), BorderLayout.SOUTH);
			windowFrame.add(panels.get(1), BorderLayout.WEST);
			windowFrame.add(panels.get(2), BorderLayout.EAST);
			windowFrame.add(panels.get(3), BorderLayout.NORTH);

			// CONFIGURA��O DOS COMPONENTES
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
			// CONFIGURA��O WINDOW FRAME
			windowFrame.setLocationRelativeTo(null);
			windowFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			windowFrame.setResizable(false);
			windowFrame.validate();
			windowFrame.setVisible(true);

			sendM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if(windowFrame.getTitle().equals("E-Mail")) {
						
					SendEmail sMail = new SendEmail();
		
					// JOPTION PANE
					JTextField emailTo = new JTextField();
					JTextField contentTo = new JTextField();
					
					String fromMOnlyEmail = fromM.getText().split("<")[1];
					String emailToFinal = fromMOnlyEmail.substring(0, fromMOnlyEmail.length()-1).toLowerCase();
					
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
								// fromEmail && fromPWEmail est�o aqui como teste apenas
								String fromEmail = "diana.es.pl.91@gmail.com";
								String fromPWEmail = "engenhariasoftware";
								int resultMail = sMail.senderMail(toEmail, fromEmail, fromPWEmail, toContent, titleM.getText());
								if(resultMail == 1) {
									JOptionPane.showMessageDialog(null, "E-mail sent to: " + toEmail + " and content is " + toContent);
									windowFrame.setVisible(false);
								} else {
									JOptionPane.showMessageDialog(null, "E-mail n�o enviado. Tente novamente.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "Campos por preencher");
								sendM.doClick();
							}
						}
					} catch (Exception excep) {
						System.out.println("Button cancel." + excep);
					}
					} else if(windowFrame.getTitle().equals("Twitter")) {
						JTextField retweet = new JTextField();
						Object[] f = {"Indique o conte�do do Retweet:", retweet};
						int okOrCancel = JOptionPane.showConfirmDialog(null, f, "This is a header", JOptionPane.OK_CANCEL_OPTION);
						String retweetText = retweet.getText().toString();

						if(okOrCancel == JOptionPane.CANCEL_OPTION) {
							windowFrame.setVisible(false);
						} else {	
							if(!retweetText.isEmpty()) {
								Retweet rTwitter = new Retweet();
								int sucessOrInsucess = rTwitter.retweet("iscteiul", ReadXMLfile.userData[0], Long.valueOf(titleM.getText().split(" ")[1]), retweetText);	
								if(sucessOrInsucess == 0) {
									JOptionPane.showMessageDialog(null, "Retweet realizado com sucesso.");
									windowFrame.setVisible(false);
								} else {
									JOptionPane.showMessageDialog(null, "Ocorreu um erro no retweet. Tente novamente.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "Campos por preencher");
								sendM.doClick();
							}
						}
					} else if(windowFrame.getTitle().equals("Facebook")) {
						JTextField comment = new JTextField();
						Object[] f = {"Indique o conte�do do Coment�rio:", comment};
						int okOrCancel = JOptionPane.showConfirmDialog(null, f, "This is a header", JOptionPane.OK_CANCEL_OPTION);
						String commentText = comment.getText().toString();

						if(okOrCancel == JOptionPane.CANCEL_OPTION) {
							windowFrame.setVisible(false);
						} else {	
							if(!commentText.isEmpty()) {
								Comment commentFace = new Comment();
								int sucessOrInsucess = commentFace.sharePost(Long.valueOf("11"), "");
								if(sucessOrInsucess == 0) {
									JOptionPane.showMessageDialog(null, "Coment�rio realizado com sucesso.");
									windowFrame.setVisible(false);
								} else {
									JOptionPane.showMessageDialog(null, "Ocorreu um erro no coment�rio. Tente novamente.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "Campos por preencher");
								sendM.doClick();
							}
						}
					}
				}
		});
		}
		
	}