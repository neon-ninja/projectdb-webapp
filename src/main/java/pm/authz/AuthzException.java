package pm.authz;

public class AuthzException extends Exception {

	private String customMsg;
	
	public AuthzException(String s) {
		this.customMsg = s;

	}
	
	public String getCustomMsg() {
		return this.customMsg;
	}
}
