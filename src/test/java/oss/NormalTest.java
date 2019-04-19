package oss;

import com.iwellmass.core.util.MD5;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/17 10:58
 * @description
 */
public class NormalTest {

    Logger logger = LoggerFactory.getLogger(NormalTest.class);


    @Test
    public void test1() {
//        int[] moneyTypes = new int[]{1, 4, 16, 64}; // 币种的类型  4 的 0 1 2 3 指数 分别对应 结余 1张 4张 16张 4元
//        final int allMoney = 1024; // 总计
//        int productMoney = new Scanner(System.in).nextInt();  // 输入消费多少
        int leftMoney = 1024 - new Scanner(System.in).nextInt();
        int allNum4 = leftMoney / 4;  // 需要的所有4块钱总数
        int allNum1 = leftMoney - 4 * allNum4;  // 1块钱总数

        //64
        int n64 = allNum4 / 16; // 64 块钱总数
        // 16
        int n16 = (allNum4 - (16 * n64)) / 4; // 16 块钱总数
        // 4 元
        int n4 = allNum4 - 16 * n64 - 4 * n16; // 4块钱总数
        System.out.println(allNum1 + n64 + n16 + n4);
    }

    @Test
    public void test2() {
        int count = new Scanner(System.in).nextInt();
//        if (count < 1) {
//            return;
//        }
        // 接收参数
        List<String> waitFormat = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            waitFormat.add(new Scanner(System.in).nextLine());
        }
        // 开始处理
        waitFormat = waitFormat.stream().parallel().map(item -> {
            // aaab -> 去掉一个 a aab
            char[] chars = item.toCharArray();
//            item.replaceAll();
//            deleteTriple(chars);
            return String.valueOf(chars);
        }).collect(Collectors.toList());

        // 输出
        for (String formatedStr : waitFormat) {
            System.out.println(formatedStr);
        }
    }

    @Test
    public void test3() {
        String a = "abbbc";
        System.out.println(a.replaceAll("bbb", "d"));
    }

    @Test
    public void test4() {
        int count = 2;
//        if (count < 1) {
//            return;
//        }
        // 接收参数
        List<String> waitFormat = new ArrayList<>();
        waitFormat.add("helllo");
        waitFormat.add("wwwwooob");
//        for (int i = 0; i < count; i++) {
//            waitFormat.add(new Scanner(System.in).nextLine());
//        }
        String[] source = new String[]{"aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk", "lll", "mmm", "nnn",
                "ooo", "ppp", "qqq", "rrr", "sss", "ttt", "uuu", "vvv", "www", "xxx", "yyy", "zzz"};
        String[] target = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj", "kk", "ll", "mm", "nn",
                "oo", "pp", "qq", "rr", "ss", "tt", "uu", "vv", "ww", "xx", "yy", "zz"};
        List<String> targetStr = new ArrayList<>();
        boolean cont = true;
        while (cont) {
            for (int i = 0; i < 26; i++) {
                for (String wf : waitFormat) {
                    if (wf.contains(source[i])) {
                        cont = true;
                        targetStr.add(wf.replaceAll(source[i], target[i]));
                        break;
                    }
                    if (targetStr.size() == waitFormat.size()) {
                        break;
                    }
                }
            }
        }

        System.out.println(targetStr);
    }

    @Test
    public void test5() {
        System.out.println(formatAAA("abbbbbbbbbbbccddddddddddde"));
    }

    String formatAAA(String sourceStr) {
        String[] source = new String[]{"aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk", "lll", "mmm", "nnn",
                "ooo", "ppp", "qqq", "rrr", "sss", "ttt", "uuu", "vvv", "www", "xxx", "yyy", "zzz"};
        String[] target = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj", "kk", "ll", "mm", "nn",
                "oo", "pp", "qq", "rr", "ss", "tt", "uu", "vv", "ww", "xx", "yy", "zz"};
//        String s = "abbbbbbbbbbbccddddddddddde";
        String a = sourceStr;
        boolean cont = false;
        for (int i = 0; i < 26; i++) {
            if (sourceStr.contains(source[i])) {
                cont = true;
                a = sourceStr.replaceAll(source[i], target[i]);
                break;
            }
        }
        if (cont) {
            return formatAAA(a);
        } else {
            return a;
        }
    }

    String formatAABB(String sourceStr) {
        String[] source = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj", "kk", "ll", "mm", "nn",
                "oo", "pp", "qq", "rr", "ss", "tt", "uu", "vv", "ww", "xx", "yy", "zz"};
        int one, two = 0;
        for (int i = 0; i < 26; i++) {
            if (sourceStr.contains(source[i])) {
                one = i;
                break;
            }
        }
        for (int j = 0; j < 26; j++) {
            if (sourceStr.contains(source[j])) {

            }
        }
        return null;
    }

    @Test
    public void test6() {
        int all = new Scanner(System.in).nextInt();
        int need = new Scanner(System.in).nextInt();
        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i < all; i++) {
            lines.add(new Scanner(System.in).nextInt());
        }
        lines = lines.stream().sorted((o1, o2) -> o1 > o2 ? -1 : 1).collect(Collectors.toList());
        if (need <= all) {
            int a = all - need;
            System.out.println(lines.get(need));
        } else {
            int a = need / all + 1; // 需要拆成几截
            int b = need - all;
            int lenth = (lines.get(b - 1) / a);
        }
    }

    @Test
    public void test7() {
        List<Integer> lines = Arrays.asList(3, 5, 4);
        lines = lines.stream().sorted((o1, o2) -> o1 > o2 ? -1 : 1).collect(Collectors.toList());
        System.out.println(lines);
    }

    @Test
    public void test8() {
        Double a = 100.0121314;
        a = a * 100;
        System.out.println(a.longValue());

        System.out.println(MD5.calc("1=2&c=3").toUpperCase());

        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void test9() {

//        while (in.hasNextInt()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
//            int a = in.nextInt();
//            int b = in.nextInt();
//            System.out.println(a + b);
//        }
        Scanner in = new Scanner(System.in);
        List<List<Integer>> arr = new ArrayList<>();
        int n = 0;
        String s = "";
        while ((s = in.nextLine()) != "" || (s = in.nextLine()) != null) {
            List<Integer> cc = new ArrayList<>();
            String[] str = s.split(" ");
            for (String string : str) {
                cc.add(Integer.valueOf(string));
            }
            arr.add(cc);
            n++;
        }

        int min = 0;

        for (int i = 0; i < arr.size(); i++) {
            List<Integer> c = arr.get(i);
            for (int j = 0; j < c.size(); j++) {
                if (c.get(j).equals(2)) {
                    // 转换
                    if (!(i - 1 < 0)) {
                        arr.get(i - 1).set(j, 2);
                    }
                    if ( i + 1 < n) {
                        arr.get(i + 1).set(j, 2);
                    }
                    if (!(j - 1 < 0)) {
                        arr.get(i + 1).set(j, 2);
                    }
                    if ( j + 1 < n) {
                        arr.get(j + 1).set(j, 2);
                    }
                }
            }
            min++;
        }

        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.get(i).size();j++) {
                if (arr.get(i).get(j).equals(1)) {
                    System.out.println(-1);
                    return;
                }
            }
        }

        System.out.println(min);
    }

    @Test
    public void test10() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<Integer> value = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            value.add(in.nextInt());
        }


    }


}