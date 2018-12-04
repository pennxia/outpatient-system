package cn.nobitastudio.common.util;

import java.util.Map;

public class RequestVO<T> {
    private T data;

    private Map<String ,Object> dataMap;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public RequestVO(T data, Map<String, Object> dataMap) {
        this.data = data;
        this.dataMap = dataMap;
    }

    public RequestVO() {
    }
}
