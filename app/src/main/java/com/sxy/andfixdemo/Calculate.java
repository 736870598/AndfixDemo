package com.sxy.andfixdemo;

import com.alipay.euler.andfix.annotation.MethodReplace;

/**
 * Created by sunxiaoyu on 2017/9/14.
 */
public class Calculate {

    @MethodReplace(clazz = "com.sxy.andfixdemo.Calculate", method = "calculateResult")
    public int calculateResult(){
        int i = 0;
        int j = 10;

        return j / i;
    }

}
