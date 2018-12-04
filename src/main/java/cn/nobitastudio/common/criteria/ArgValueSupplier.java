package cn.nobitastudio.common.criteria;

/**
 * 获取参数值
 */
public interface ArgValueSupplier {
	
	public Object getValue(String argName);
	
	@SuppressWarnings("unchecked")
	public default <T> T getAndCast(String argName) {
		Object v = getValue(argName);
		return (T) v;
	}
}
