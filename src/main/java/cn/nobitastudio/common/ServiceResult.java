package cn.nobitastudio.common;

import cn.nobitastudio.common.view.EditableView;
import cn.nobitastudio.common.view.ListView;
import cn.nobitastudio.common.view.ReadableView;
import cn.nobitastudio.oss.model.Constant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * 前端统一返回结构正常返回时 state = 0, 其他情况:
 * 业务异常 1，未捕获异常 2, 未授权 3
 *
 * @param <T> 返回类型
 */
public class ServiceResult<T> {

    public static final int STATE_SUCCESS = 0;
    public static final int STATE_APP_EXCEPTION = 1;
    public static final int STATE_EXCEPTION = 2;
    public static final int STATE_NO_SESSION = 3;

    public static ServiceResult<?> noSession = new ServiceResult<>(null, STATE_NO_SESSION);

    // 结果
    @JsonInclude(Include.NON_NULL)
    @JsonView({ListView.class, ReadableView.class, EditableView.class})
    private T result;

    // 标识成功还是失败
    private int state;

    // 错误详情藐视 一般是string类型
    @JsonInclude(Include.NON_NULL)
    private Object error;

    // 错误码
    private String errorCode;

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

    public ServiceResult<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public int getState() {
        return state;
    }

    public ServiceResult<T> setState(int state) {
        this.state = state;
        return this;
    }

    public Object getError() {
        return error;
    }

    public ServiceResult<T> setError(Object error) {
        this.error = error;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public ServiceResult<T> setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public static <T> ServiceResult<T> success(T result) {
        return new ServiceResult<>(result, STATE_SUCCESS);
    }

    public static <T> ServiceResult<T> failure(Object result) {
        ServiceResult<T> f = new ServiceResult<>(null, STATE_APP_EXCEPTION);
        f.setError(result).setErrorCode(Constant.NORMAL_ERROR);
        return f;
    }

    public static <T> ServiceResult<T> failure(Object result, String errorCode) {
        ServiceResult<T> f = new ServiceResult<>(null, STATE_APP_EXCEPTION);
        f.setError(result).setErrorCode(errorCode);
        return f;
    }

    public static <T> ServiceResult<T> exception(Object result) {
        ServiceResult<T> f = new ServiceResult<>(null, STATE_EXCEPTION);
        f.setError(result);
        return f;
    }

    public static <T> ServiceResult<T> noSession(Object result) {
        ServiceResult<T> f = new ServiceResult<>(null, STATE_NO_SESSION);
        f.setError(result);
        return f;
    }

}
