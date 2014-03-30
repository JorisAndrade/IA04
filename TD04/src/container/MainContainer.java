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
			AgentController ac = mc.createNewAgent("AgentSimulation",
					"simulation.SimulationAgent", null);
			ac.start();
			ac = mc.createNewAgent("AgentEnvironnement",
					"environnement.EnvironnementAgent", null);
			ac.start();
			for (int i = 0; i < 27; ++i) {
				ac = mc.createNewAgent("AgentAnalyse" + i,
						"analyse.AnalyseAgent", null);
				ac.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
