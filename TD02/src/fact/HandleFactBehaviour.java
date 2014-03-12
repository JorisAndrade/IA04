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
public class HandleFactBehaviour extends Behaviour{
	private int fact;
	private int step = 1;
	private int savedValue = 1;
	public HandleFactBehaviour(int fact){
		this.fact = fact;
	}

	@Override
	public void action() {
		ObjectMapper mapper = new ObjectMapper();	
		StringWriter stringWriter = new StringWriter();
		System.out.println("STEP" + step);
			ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			System.out.println("WAIT FOR MESSAGE");
			if(message != null || step == 1){
				System.out.println("RECEIVEMESSAGE");
				if(message != null){
					String messageContent = message.getContent();
					try {
						CalcResult calcResult = mapper.readValue(messageContent, CalcResult.class);
						savedValue = calcResult.getResult();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				CalcQuery calcQuery = new CalcQuery();
				List<Integer> mults = new ArrayList<Integer>();
				mults.add(step);
				mults.add(savedValue);
				calcQuery.setNumbers(mults);
				calcQuery.setAction(CalcQuery.MULTIPLICATION);
				try {
					mapper.writeValue(stringWriter, calcQuery);
					String sendString = stringWriter.toString();
					AID receiver = ((Fact)myAgent).getReceiver();
					ACLMessage messageResponse = new ACLMessage(ACLMessage.REQUEST);
					if(receiver != null) {
						messageResponse.addReceiver(receiver);
						messageResponse.setContent(sendString);
						myAgent.send(messageResponse);
						step++;
					} else {
						System.out.println(myAgent.getLocalName() + "--> No receiver");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				block();
			}
	}

	@Override
	public boolean done() {
		return step > (fact+1);
	}
	@Override
	public int onEnd() {
		myAgent.removeBehaviour(this);
		return super.onEnd();
	}
}
