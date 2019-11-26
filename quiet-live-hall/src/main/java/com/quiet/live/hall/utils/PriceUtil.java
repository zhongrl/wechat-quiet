package com.quiet.live.hall.utils;

import java.math.BigDecimal;

public class PriceUtil {


    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     */
    public static double changeF2Y(BigDecimal amount){
        return amount.divide(new BigDecimal(100)).doubleValue();
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount
     * @return
     */
    public static Integer changeY2F(BigDecimal amount){
        return amount.multiply(new BigDecimal(100)).intValue();
    }


//    public static void main(String[] args) {
//        int amout = 10;
//
//        System.out.println(changeF2Y(amout));   // 0.1
//        System.out.println(changeY2F(amout));   // 1000
//
//    }
}
