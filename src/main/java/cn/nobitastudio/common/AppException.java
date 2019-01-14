package cn.nobitastudio.common;

public class AppException extends RuntimeException{

	private static final long serialVersionUID = 2649271829485681945L;
	
	public AppException(String msg) {
		super(msg);
	}

}
