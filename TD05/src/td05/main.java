package td05;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.io.File;

public class main {
	public static String MAIN_PROPERTIES_FILE = "config" + File.separator
			+ "main.properties";

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		try {
			ProfileImpl profile = new ProfileImpl(MAIN_PROPERTIES_FILE);
			AgentContainer mc = rt.createMainContainer(profile);
			AgentController ac;

			ac = mc.createNewAgent("AppAgent", "td05.AppAgent",
					new Object[] { 4 });
			ac.start();

			ac = mc.createNewAgent("KBAgent", "td05.KBAgent",
					new Object[] { 4 });
			ac.start();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
