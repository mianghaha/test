package proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CGLibTestMain {
	
	public CGLibTestSampleClass test = new CGLibTestSampleClass();
	
	public void test(){
        System.out.println("hello world");
    }
	
	public void test2(){
        System.out.println("22222");
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGLibTestMain.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("before method run...");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("after method run...");
                return result;
            }
        });
        CGLibTestMain sample = (CGLibTestMain) enhancer.create();
        sample.test();
        sample.test2();
    }
}
