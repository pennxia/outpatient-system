package cn.nobitastudio.common;

import cn.nobitastudio.common.view.EditableView;
import cn.nobitastudio.common.view.ListView;
import cn.nobitastudio.common.view.ReadableView;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * 前端统一返回结构正常返回时 state = 0, 其他情况:
 * 业务异常 1，未捕获异常 2, 未授权 3
 * @param <T> 返回类型
 */
public class ServiceResult<T> {
	
	public static final int STATE_SUCCESS = 0;
	public static final int STATE_APP_EXCEPTION = 1;
	public static final int STATE_EXCEPTION = 2;
	public static final int STATE_NO_SESSION = 3;

	public static ServiceResult<?> noSession = new ServiceResult<>(null, STATE_NO_SESSION);

	@JsonInclude(Include.NON_NULL)
	@JsonView({ListView.class, ReadableView.class, EditableView.class})
	private T result;

	@JsonInclude(Include.NON_NULL)
	private Object error;
	
	private int state;

	public ServiceResult() {
	}

	public ServiceResult(int state) {
		this.state = state;
	}

	public ServiceResult(T result, int state) {
		this.result = result;
		this.state = state;
	}
	
	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}

	public static <T> ServiceResult<T> success(T result) {
		return new ServiceResult<>(result, STATE_SUCCESS);
	}

	public static <T> ServiceResult<T> failure(Object result) {
		ServiceResult<T> f = new ServiceResult<>(null, STATE_APP_EXCEPTION);
		f.setError(result);
		return f;
	}
	
}
