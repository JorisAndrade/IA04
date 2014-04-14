package td05;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

@SuppressWarnings("serial")
public class KBAgent extends Agent {
	OntModel model;
	String foafPrefix;
	String td5Prefix;

	@Override
	protected void setup() {
		model = ModelFactory.createOntologyModel();
		model.read("file:./foaf.n3", null, "TURTLE");
		model.read("file:./persons.ttl", null, "TURTLE");
		foafPrefix = model.getNsPrefixURI("foaf");
		td5Prefix = model.getNsPrefixURI("td5");
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
						switch (request.getRequest()) {
						case 1:
							rep.setContent(iteratorToString(getStatements(request
									.getParam())));
							break;
						case 2:
							rep.setContent(iteratorToString(getStatementsFromFirstName(request
									.getParam())));
							break;
						case 3:
							rep.setContent(iteratorToString(getKnows(request
									.getParam())));
							break;
						default:
							rep.setPerformative(ACLMessage.FAILURE);
							rep.setContent("Methode inconnue");
							break;
						}
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

	public ExtendedIterator<Statement> getStatements(String URI) {

		if (!URI.contains(td5Prefix)) {
			URI = td5Prefix + URI;
		}

		Resource person = model.getResource(URI);
		if (person == null) {
			return null;
		} else {
			return model.listStatements(new SimpleSelector(person, null,
					(RDFNode) null));
		}

	}

	public ExtendedIterator<Statement> getKnows(String name) {
		Resource person = getPersonFromFirstName(name);
		Property knows = model.getProperty(foafPrefix + "knows");
		if (person == null) {
			return null;
		} else {
			return model.listStatements(new SimpleSelector(person, knows,
					(Resource) null));
		}
	}

	public Resource getPersonFromFirstName(String name) {
		Property firstname = model.getProperty(foafPrefix + "firstname");
		ExtendedIterator<Statement> it = model
				.listStatements(new SimpleSelector((Resource) null, firstname,
						name));
		if (it.hasNext()) {
			return it.next().getSubject();
		}
		return null;
	}

	public ExtendedIterator<Statement> getStatementsFromFirstName(String name) {
		Resource person = getPersonFromFirstName(name);
		if (person == null) {
			return null;
		} else {
			return getStatements(person.toString());
		}
	}

	public String iteratorToString(ExtendedIterator<Statement> it) {
		String value = "";
		if (it == null) {
			return "";
		}

		while (it.hasNext()) {
			value += it.next().toString() + "\n";
		}

		return value;
	}
}
