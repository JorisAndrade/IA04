package mult;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.StringWriter;

import models.CalcQuery;
import models.CalcResult;

import com.fasterxml.jackson.databind.ObjectMapper;


@SuppressWarnings("serial")
public class MultBehavior extends CyclicBehaviour{

	ObjectMapper mapper = new ObjectMapper();
	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (message != null) {
			String messageContent = message.getContent();
			ObjectMapper mapper = new ObjectMapper();	
			CalcQuery query = null;
			CalcResult calcResult = null;
			StringWriter stringWriter = new StringWriter();
			
			try {
				query = mapper.readValue(messageContent, CalcQuery.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(query != null){
				if(query.getAction().equals(CalcQuery.MULTIPLICATION)){
					int result = 1;
					for(Integer i : query.getNumbers()){
						result = result * i;
					}
					calcResult = new CalcResult(result, CalcResult.OK);
				}
				else {
					calcResult = new CalcResult(0, CalcResult.ERROR);	
				}
			} else {
				calcResult = new CalcResult(0, CalcResult.ERROR);
			}
			
			
			try {
				mapper.writeValue(stringWriter, calcResult);
				String sendString = stringWriter.toString();
//				System.out.println(sendString);
				ACLMessage reply = message.createReply();
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent(sendString);
				myAgent.send(reply);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				
		} else {
			block();
		}
	}

}
