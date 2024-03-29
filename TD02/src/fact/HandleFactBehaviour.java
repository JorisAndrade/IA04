package fact;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import models.CalcQuery;
import models.CalcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class HandleFactBehaviour extends Behaviour {
	private final int fact;
	private int step;
	private int savedValue;
	private final int conversationId;
	ObjectMapper mapper = new ObjectMapper();

	public HandleFactBehaviour(int fact, int conversationId) {
		this.fact = fact;
		step = 1;
		savedValue = 1;
		this.conversationId = conversationId;
	}

	public void sendMultiplication() {
		System.out.println("STEP " + step);
		CalcQuery calcQuery = new CalcQuery();
		List<Integer> mults = new ArrayList<Integer>();
		mults.add(step);
		mults.add(savedValue);
		calcQuery.setNumbers(mults);
		calcQuery.setAction(CalcQuery.MULTIPLICATION);
		try {
			StringWriter stringWriter = new StringWriter();
			mapper.writeValue(stringWriter, calcQuery);
			String sendString = stringWriter.toString();
			System.out.println(stringWriter.toString());
			AID receiver = ((Fact) myAgent).getReceiver();
			ACLMessage messageResponse = new ACLMessage(ACLMessage.REQUEST);
			if (receiver != null) {
				messageResponse.addReceiver(receiver);
				messageResponse.setConversationId(String
						.valueOf(conversationId));
				messageResponse.setContent(sendString);
				myAgent.send(messageResponse);
			} else {
				System.out.println(myAgent.getLocalName() + "--> No receiver");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onStart() {
		System.out.println("ID: " + conversationId);
		sendMultiplication();
	}

	@Override
	public int onEnd() {
		System.out.println("Resultat " + savedValue);
		// On remet les valeurs par defaut
		return super.onEnd();
	}

	@Override
	public void action() {
		System.out.println("conversationID " + conversationId);
		MessageTemplate template = MessageTemplate.and(MessageTemplate
				.MatchPerformative(ACLMessage.INFORM), MessageTemplate
				.MatchConversationId(String.valueOf(conversationId)));
		ACLMessage message = myAgent.receive(template);
		System.out.println("WAIT FOR MESSAGE");
		if (message != null) {
			System.out.println("RECEIVE MESSAGE");
			String messageContent = message.getContent();
			try {
				CalcResult calcResult = mapper.readValue(messageContent,
						CalcResult.class);
				savedValue = calcResult.getResult();
				step++;
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (step <= fact) {
				sendMultiplication();
			}

		} else {
			block();
		}
	}

	@Override
	public boolean done() {
		return step > fact;
	}

}
