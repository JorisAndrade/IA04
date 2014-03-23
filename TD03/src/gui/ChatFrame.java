package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import model.ChatMessage;
import agents.ChatAgent;

@SuppressWarnings("serial")
public class ChatFrame extends JFrame {

	// composants
	private final JList<ChatMessage> jList;
	private final JTextField jTextField;
	private final ChatAgent chatAgent;
	private final JButton bouton;
	// choix de fichier
	private final JFileChooser fc = new JFileChooser();

	public ChatFrame(ChatAgent chatAgent) {
		super();
		this.chatAgent = chatAgent;
		this.setSize(500, 200);
		setTitle(chatAgent.getName());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// layout
		Container pane = getContentPane(); // on recupere le pane de base de la
											// page
		BoxLayout baseLayout = new BoxLayout(pane, BoxLayout.PAGE_AXIS);
		pane.setLayout(baseLayout);
		// initialisation de la liste
		jList = new JList<ChatMessage>(chatAgent.getReceiveBehaviour()
				.getListModel());
		jList.setCellRenderer(new ListCell());
		JScrollPane scrollPane = new JScrollPane(jList);
		// textfield
		JPanel panelText = new JPanel();
		jTextField = new JTextField();
		panelText.setPreferredSize(new Dimension(500, 30));
		panelText.setLayout(new BoxLayout(panelText, BoxLayout.LINE_AXIS));
		panelText.add(jTextField);
		// bouton d'envoie
		bouton = new JButton("Envoyer");
		panelText.add(bouton);

		// separateur
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				scrollPane, panelText);
		pane.add(splitPane);
		addListeners();
		setVisible(true);

	}

	public void addListeners() {
		bouton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chatAgent.sendMessage(jTextField.getText());
				jTextField.setText("");
			}
		});
	}

	public JList<ChatMessage> getjList() {
		return jList;
	}

	public JTextField getjTextField() {
		return jTextField;
	}

}
