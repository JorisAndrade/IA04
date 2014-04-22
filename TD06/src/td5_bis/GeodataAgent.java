package td5_bis;

import java.io.IOException;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;

@SuppressWarnings("serial")
public class GeodataAgent extends Agent{
	@Override
	protected void setup() {
		addBehaviour(new CyclicBehaviour() {
			private final ObjectMapper mapper = new ObjectMapper();

			@Override
			public void action() {
				MessageTemplate template = MessageTemplate
						.MatchPerformative(ACLMessage.REQUEST);
				ACLMessage msg = myAgent.receive(template);
				if (msg != null) {
					try {
						RequestMessage request = mapper.readValue(
								msg.getContent(), RequestMessage.class);
						ACLMessage rep = msg.createReply();
						rep.setPerformative(ACLMessage.INFORM);
						rep.setContent(executeSparqlRequest(request
									.getParam()));
						System.out.println("retour : " + rep.getContent());
						send(rep);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					block();
				}
			}
		});
	}
	public String executeSparqlRequest(String request){
		Query query = QueryFactory.create(request); 
		System.setProperty("http.proxyHost","proxyweb.utc.fr"); 
		System.setProperty("http.proxyPort","3128"); 
		System.out.println("Query sent"); 
		QueryExecution qexec = QueryExecutionFactory.sparqlService( "http://linkedgeodata.org/sparql", 
		query ); 
		ResultSet concepts = qexec.execSelect(); 
		ResultSetFormatter.out(concepts); 
		qexec.close();
		return null;
	}
}
