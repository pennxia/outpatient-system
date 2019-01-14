package cn.nobitastudio.common.util;

import cn.nobitastudio.common.view.EditableView;
import cn.nobitastudio.common.view.ListView;
import cn.nobitastudio.common.view.ReadableView;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页查询用数据封装对象
 *
 * @param <T>
 */
public class PageData<T> implements Serializable{

	private static final long serialVersionUID = -4897168692278164075L;

	/**
	 * 查询的总记录数
	 */
	private int count;

	/**
	 * 查询的结果列表
	 */
	@JsonView({ListView.class, ReadableView.class, EditableView.class})
	private List<T> data;
	/**
	 * yuanjun add
	 * 查询的结果列表-源数据信息
	 */
     private List<T> rawData;

	public PageData() {
	    count = 0;
	    data = Collections.emptyList();
	    rawData = Collections.emptyList();
	}

	public PageData(int count, List<T> data) {
		this.count = count;
		this.data = data;
	}

	public PageData(int count, List<T> data, List<T> rawData) {
		this.count = count;
		this.data = data;
		this.rawData = rawData;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public boolean isEmpty() {
		return data==null || data.isEmpty();
	}

	public List<T> getRawData() {
		return rawData;
	}

	public void setRawData(List<T> rawData) {
		this.rawData = rawData;
	}

}
