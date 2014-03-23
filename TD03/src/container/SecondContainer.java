package container;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.io.File;

public class SecondContainer {
	public static String MAIN_PROPERTIES_FILE = "config" + File.separator
			+ "second.properties";

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		Profile p = null;
		try {
			p = new ProfileImpl(MAIN_PROPERTIES_FILE);
			AgentContainer ac = rt.createAgentContainer(p);
			AgentController chatController = ac.createNewAgent("ChatClient",
					"agents.ChatAgent", null);
			chatController.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
