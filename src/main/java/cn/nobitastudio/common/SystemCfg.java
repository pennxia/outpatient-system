package cn.nobitastudio.common;

public class SystemCfg {

	public static String system;// 运行端类型

	public static String CENTER = "center";

	public static String BANK = "bank";

	public static String BANKAPP = "bankapp";

	public static boolean initCache;


	public static int syncPageSize = 500;
	
	public static int dapSyncPageSize = 1000;


//	@Override
//	public void afterPropertiesSet() throws Exception {
//		String str = Files.toString(activator.getConfigureFile("config.json"), Charsets.UTF_8);
//		system = JSON.parseObject(str).getString("system");
//		checkArgument(!Utils.isNullOrEmpty(system), "终端的运行类型没有配置。请检查[%s]文件是否存在，并且配置system属性。",
//				"/rainbow/conf/common/config.json");
//		initCache = JSON.parseObject(str).getBoolean("initCache");
//		syncPageSize = JSON.parseObject(str).getInteger("syncPageSize");
//		dapSyncPageSize = JSON.parseObject(str).getInteger("dapSyncPageSize");
//	}

	public static boolean isCenter() {
		return CENTER.equals(system);
	}

	public static boolean isBank() {
		return BANK.equals(system);
	}

	public static boolean isBankApp() {
		return BANKAPP.equals(system);
	}

	public static boolean isInitCache() {
		return initCache;
	}

}
