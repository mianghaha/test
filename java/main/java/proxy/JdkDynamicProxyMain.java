package proxy;

public class JdkDynamicProxyMain{
	
	public static void main(String[] args) {
		
		TestInterface t1 = new TestInterfaceImp1();
		JdkDynamicProxyInvocationHandler handler1 = new JdkDynamicProxyInvocationHandler(t1);
		TestInterface prox1 = (TestInterface) java.lang.reflect.Proxy.newProxyInstance(JdkDynamicProxyMain.class.getClassLoader(),
				t1.getClass().getInterfaces(), handler1);
		prox1.test();
		
		
		TestInterface t2 = new TestInterfaceImp2();
		JdkDynamicProxyInvocationHandler handler2 = new JdkDynamicProxyInvocationHandler(t2);
		TestInterface prox2 = (TestInterface) java.lang.reflect.Proxy.newProxyInstance(JdkDynamicProxyMain.class.getClassLoader(),
				t1.getClass().getInterfaces(), handler1);
		prox2.test();
	}
}
