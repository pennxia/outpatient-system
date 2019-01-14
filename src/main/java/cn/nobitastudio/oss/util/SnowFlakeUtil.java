package cn.nobitastudio.oss.util;

import cn.nobitastudio.common.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 21:37
 * @description 雪花算法实现, 采用单例模式, 高并发下, 多个该实例对象, 可能产生相同的id, 保证全局只有一个对象即可
 */
public class SnowFlakeUtil {

    /**
     * 起始的时间戳
     */
    private final static long START_STAMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数  可调整
    private final static long DATA_CENTER_BIT = 5;//数据中心占用的位数 可调整

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIME_STMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private long dateCenterId;  //数据中心  // 业务号,,visit,
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastTimestamp = -1L;//上一次时间戳

    private static final long DEFAULT_MACHINE_ID = 5;  // 分布式中,默认该台服务器服务的机器id是5
    private static final long DEFAULT_DATA_CENTER_ID = 5;  // 分布式中,默认该台服务器服务的数据中心id（业务id）是 5

    private static SnowFlakeUtil snowFlake = new SnowFlakeUtil();  //  默认实例

    private SnowFlakeUtil() {
        this(DEFAULT_DATA_CENTER_ID, DEFAULT_MACHINE_ID);
    }

    private SnowFlakeUtil(long dateCenterId, long machineId) {
        if (dateCenterId > MAX_DATA_CENTER_NUM || dateCenterId < 0) {
            throw new IllegalArgumentException("dateCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dateCenterId = dateCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID，调用该方法的方法必须上锁
     *
     * @return
     */
    public long nextId() {
        long currentTimestamp = getNewTimestamp();
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currentTimestamp == lastTimestamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currentTimestamp = getNextMill();   // 高并发控制
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        return (currentTimestamp - START_STAMP) << TIME_STMP_LEFT //时间戳部分
                | dateCenterId << DATA_CENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewTimestamp();
        while (mill <= lastTimestamp) {
            mill = getNewTimestamp();
        }
        return mill;
    }

    private long getNewTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 使用默认的machineId,dataCenterId,获得唯一id
     * @return
     */
    public static synchronized Long getUniqueId() {
        snowFlake.setDateCenterId(DEFAULT_DATA_CENTER_ID); // 初始化为默认的
        snowFlake.setMachineId(DEFAULT_MACHINE_ID); // 初始化为默认的
        return snowFlake.nextId();
    }

    /**
     * 使用自定义的dateCenterId 获得唯一id
     * @return
     */
    public static synchronized Long getUniqueId(long dateCenterId) {
        snowFlake.setMachineId(DEFAULT_MACHINE_ID); // 初始化为默认的
        snowFlake.setDateCenterId(dateCenterId);
        return snowFlake.nextId();
    }

    /**
     * 使用自定义的machineId,dataCenterId改变获得唯一id
     * @return
     */
    public static synchronized Long getUniqueId(long dateCenterId,long machineId) {
        snowFlake.setDateCenterId(dateCenterId);
        snowFlake.setMachineId(machineId);
        return snowFlake.nextId();
    }

    public long getDateCenterId() {
        return dateCenterId;
    }

    private void setDateCenterId(long dateCenterId) {
        this.dateCenterId = dateCenterId;
    }

    public long getMachineId() {
        return machineId;
    }

    private void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(snowFlake.getClass());
        HashSet<Long> set = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                Long id = getUniqueId();
                //logger.info("当前时间戳：" + System.currentTimeMillis() + ",id : " + id);
                if (!set.add(id)) {
                    logger.info("重复的id:" + id);
                    throw new AppException("重复的id:" + id);
                }
            }).start();
        }
        while (true) {
            logger.info("size:" + set.size() + "");
            Thread.sleep(2000);
        }
    }
}
