package cn.nobitastudio.common.util;

import java.util.Random;

public class Randoms {

	public static final int rand(int begin, int end) {
		int off = end - begin;
		return new Random().nextInt(off + 1) + begin;
	}
}
