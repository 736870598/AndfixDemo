package com.sxy.andfixdemo;

import android.content.Context;
import android.os.Build;

import com.alipay.euler.andfix.annotation.MethodReplace;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

/**
 *
 * Created by sunxiaoyu on 2017/9/14.
 */
public class FixManager {

    private FixManager(){}

    static class FixManagerHolder{
        static FixManager manager = new FixManager();
    }

    public static FixManager getManager(){
        return FixManagerHolder.manager;
    }

    private Context context;

    public void setContext(Context context){
        this.context = context;
    }

    /**
     * 加载dex文件，取出dex文件中的class判断class中的方法是否需要修复
     */
    public void loadFile(File file){
        try {
            DexFile dexFile = DexFile.loadDex(file.getAbsolutePath(),
                    new File(context.getCacheDir(), "opt").getAbsolutePath(), Context.MODE_PRIVATE);

            //获取dex中全部的class文件
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()){
                //获取全类名
                String className = entries.nextElement();
                Class clazz = dexFile.loadClass(className, context.getClassLoader());
                if (clazz != null){
                    fixClazzMethed(clazz);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修复class中的方法
     * @param realClazz
     */
    private void fixClazzMethed(Class realClazz) {
        Method[] methods = realClazz.getDeclaredMethods();
        for (Method rightMethod : methods){
            //该方法有注解，说明该方法是修复的方法
            MethodReplace replace = rightMethod.getAnnotation(MethodReplace.class);
            if (replace == null){
                continue;
            }
            String wrongClazz = replace.clazz();
            String wrongMethodName = replace.method();
            try {
                Class clazz = Class.forName(wrongClazz);
                Method wrongMethed = clazz.getDeclaredMethod(wrongMethodName, rightMethod.getParameterTypes());

                //5.0之前的android及使用的Dalvik虚拟机，5.0以后使用的art虚拟机

                int sdk_version = Build.VERSION.SDK_INT;

                if (sdk_version <= 19){
                    replaceDalvik(sdk_version, wrongMethed, rightMethod);
                }else if(sdk_version >= 24){
                    replaceArt7_0(sdk_version, wrongMethed, rightMethod);
                }else{
                    replaceArt(sdk_version, wrongMethed, rightMethod);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    static {
        System.loadLibrary("lib-andfix");
    }


    public native void replaceDalvik(int sdk, Method wrongMethod, Method rightMethod);

    public native void replaceArt(int sdk, Method wrongMethod, Method rightMethod);

    public native void replaceArt7_0(int sdk, Method wrongMethod, Method rightMethod);

}
