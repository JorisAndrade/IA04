package td05;

public class RequestMessage {
	private int request;
	private String param;

	public RequestMessage() {
		request = -1;
		param = "";
	}

	public RequestMessage(int request, String param) {
		this.request = request;
		this.param = param;
	}

	public int getRequest() {
		return request;
	}

	public void setRequest(int request) {
		this.request = request;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
