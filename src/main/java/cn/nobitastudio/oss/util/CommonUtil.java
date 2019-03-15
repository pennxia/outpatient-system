package cn.nobitastudio.oss.util;

import cn.nobitastudio.common.exception.AppException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description
 */
public final class CommonUtil {

    /**
     * 检查对象中的属性是否包含null
     *
     * @param object
     * @return
     * @throws AppException
     */
    public static void checkObjectFieldIsNull(Object object) throws AppException, IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(object) == null) {
                // 包含null
                throw new AppException("包含未初始化参数");
            }
        }
    }

    /**
     * 将JPA关联出来的结果通过反射的方式转换成对应的结果
     * 注意关联查询出来的结果中不能有null,可以是"",但不能是null
     * 如果返回的结果中存在枚举类型,因为数据库查询出来是String类型的,需要写一下由String 到 Enum的映射的构造方法.
     *
     * @param contents     从数据查询出来的结果,带转换
     * @param clazz        转换成指定的类型
     * @param defaultClasses 当查询结果为空时,默认使用的Class,将决定选择构造器,注意sql书写的顺序,决定第几个参数可能是null,调用时应该提前创建好构造函数,知道哪些字段可能是null
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> castEntity(List<Object[]> contents, Class<T> clazz, List<DefaultClass> defaultClasses) {
        List<T> ts = new ArrayList<T>();
        if (contents.size() == 0) {
            // 没有数据
            return ts;
        }
        Object[] columns = contents.get(0);
        Class[] paramsType = new Class[columns.length]; // 记录每个列的类型
        //确定构造方法
        for (int i = 0; i < columns.length; i++) {
            if (columns[i] != null) {
                paramsType[i] = columns[i].getClass();
            } else {
                for (DefaultClass defaultClass : defaultClasses) {
                    if (defaultClass.column.equals(i)) {
                        paramsType[i] = defaultClass.defaultClass;
                        break;
                    }
                }
                if (paramsType[i] == null) {
                    throw new AppException("column ：" + i + "为Null的默认class未传入");
                }
            }
        }
        Constructor<T> constructor; // 得到相关的构造函数
        try {
            constructor = clazz.getConstructor(paramsType);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new AppException("未查找到需要的构造函数");
        }
        for (Object[] o : contents) {
            try {
                ts.add(constructor.newInstance(o));
            } catch (Exception e) {
                e.printStackTrace();
                throw new AppException("创建对象失败");
            }
        }
        return ts;
    }

    /**
     * 使用castEntity强转时结果可能是Null是传入的默认class对象,用于初始化构造函数
     */
    public static class DefaultClass {
        public DefaultClass(Integer column, Class defaultClass) {
            this.column = column;
            this.defaultClass = defaultClass;
        }

        public Integer column;  // 从0开始
        public Class defaultClass;
    }

    /**
     * @param begin
     * @param end
     * @return return begin - end random
     */
    public static Integer getRandom(Integer begin, Integer end) {
        Integer random = begin + (int) (Math.random() * (end - begin));
        return random;
    }
}
