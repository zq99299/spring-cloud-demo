package cn.mrcode.springcloud;

import feign.Feign;

import java.lang.reflect.Method;

/**
 * @author mrcode
 * @date 2022/3/19 11:08
 */
public class DemoTest {
    public static void main(String[] args) {
        Class<MyService> myServiceClass = MyService.class;
        Method[] methods = myServiceClass.getMethods();
        for (Method method : methods) {
            System.out.println(Feign.configKey(myServiceClass, method));
        }
    }
}
