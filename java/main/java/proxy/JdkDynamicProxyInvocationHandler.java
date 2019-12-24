package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkDynamicProxyInvocationHandler implements InvocationHandler{
	
	private Object target;
	
	public JdkDynamicProxyInvocationHandler(Object obj) {
		this.target = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before");
		Object returnvalue = method.invoke(target, args);
		System.out.println("after");
		return returnvalue;
	}
}
