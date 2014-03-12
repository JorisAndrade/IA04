package container;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.io.File;


public class MainContainer {
	public static String MAIN_PROPERTIES_FILE = "config"+ File.separator +"main.properties";

	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		Profile p = null;
		try{
			p = new ProfileImpl(MAIN_PROPERTIES_FILE);
			AgentContainer mc = rt.createMainContainer(p);
			AgentController factController = mc.createNewAgent("Fact", "fact.Fact", null);
			factController.start();
			AgentController mult1Controller = mc.createNewAgent("Mult1", "mult.Mult", null);
			mult1Controller.start();
			AgentController mult2Controller = mc.createNewAgent("Mult2", "mult.Mult", null);
			mult2Controller.start();
		} catch(Exception ex) {

		}
	}

}

