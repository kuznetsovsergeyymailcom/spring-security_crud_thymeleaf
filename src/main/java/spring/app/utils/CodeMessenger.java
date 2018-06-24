package spring.app.utils;


public class CodeMessenger {

	private static ErrorCode code;

	public static ErrorCode getCode(){
		ErrorCode cod = code;
		code = null;
		return cod;
	}

	public static void setCode(ErrorCode cod) {
		code = cod;
	}
}
