package gui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import model.ChatMessage;
import agents.ReceiveBehaviour;

@SuppressWarnings("serial")
public class ChatFrame extends JFrame {

	// composants
	private final JList<ChatMessage> jList;
	private final JTextField jTextField;

	// choix de fichier
	final JFileChooser fc = new JFileChooser();

	public ChatFrame(ReceiveBehaviour receiveBehaviour) {
		super();
		this.setSize(500, 200);
		setTitle("Chat");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// layout
		Container pane = getContentPane(); // on recupere le pane de base de la
											// page
		BoxLayout baseLayout = new BoxLayout(pane, BoxLayout.PAGE_AXIS);
		pane.setLayout(baseLayout);
		// initialisation de la liste
		jList = new JList<ChatMessage>(receiveBehaviour.getListModel());
		jList.setCellRenderer(new ListCell());
		JScrollPane scrollPane = new JScrollPane(jList);
		scrollPane.setPreferredSize(new Dimension(500, 100));

		// textfield
		JPanel panelText = new JPanel();
		jTextField = new JTextField();
		panelText.setPreferredSize(new Dimension(500, 100));
		panelText.setLayout(new BoxLayout(panelText, BoxLayout.PAGE_AXIS));
		jTextField.setPreferredSize(new Dimension(500, 100));
		panelText.add(jTextField);
		// separateur
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				scrollPane, panelText);
		pane.add(splitPane);

		setVisible(true);

	}

	public JList<ChatMessage> getjList() {
		return jList;
	}

	public JTextField getjTextField() {
		return jTextField;
	}
	

}
