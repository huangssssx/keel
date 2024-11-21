package com.huangssssx.keel;

public class dosomething {
    public static void main(String[] args) {
        String name = "黄传剑";
        byte[] bytes = name.getBytes();  // 默认使用平台默认编码（通常为UTF-8）
        System.out.println("使用默认编码（一般为UTF-8）时的字节数组长度：" + bytes.length);
    }
}
