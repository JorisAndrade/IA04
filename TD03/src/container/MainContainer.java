package container;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
