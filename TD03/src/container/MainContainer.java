package container;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.io.File;

public class MainContainer {
	public static String MAIN_PROPERTIES_FILE = "config" + File.separator
			+ "main.properties";

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		Profile p = null;
		try {
			p = new ProfileImpl(MAIN_PROPERTIES_FILE);
			AgentContainer mc = rt.createMainContainer(p);
			AgentController chatController = mc.createNewAgent("ChatAgent1",
					"agents.ChatAgent", null);
			chatController.start();
			AgentController chatController2 = mc.createNewAgent("ChatAgent2",
					"agents.ChatAgent", null);
			chatController2.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
