package cn.nobitastudio.common;

import javax.persistence.AttributeConverter;

public class FlagConverter implements AttributeConverter<Boolean, Integer>{

	@Override
	public Integer convertToDatabaseColumn(Boolean attribute) {
		return attribute ? 1 : 0;
	}

	@Override
	public Boolean convertToEntityAttribute(Integer dbData) {
		return dbData == 1 ? true : false;
	}

}
