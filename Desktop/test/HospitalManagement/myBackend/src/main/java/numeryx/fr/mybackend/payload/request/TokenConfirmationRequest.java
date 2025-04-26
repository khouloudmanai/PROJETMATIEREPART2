package numeryx.fr.mybackend.payload.request;


public class TokenConfirmationRequest {

	private String confirmationToken;

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

}