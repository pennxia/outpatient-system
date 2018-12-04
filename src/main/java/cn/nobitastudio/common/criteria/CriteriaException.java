package cn.nobitastudio.common.criteria;

public class CriteriaException extends RuntimeException {

	private static final long serialVersionUID = 3583720459555848817L;

	public CriteriaException() {
		super();
	}

	public CriteriaException(String message) {
		super(message);
	}

	public CriteriaException(String message, Throwable cause) {
		super(message, cause);
	}

	public CriteriaException(Throwable cause) {
		super(cause);
	}

	protected CriteriaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
