package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类
 * @author miang
 *
 */
public class ProxyTest implements InvocationHandler{
	
	//实际执行对象
	private Object obj = null;
	
	public Object bind(Object obj){
		this.obj = obj;
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("方法执行前");
		Object result = method.invoke(obj, args);
		System.out.println("方法执行后");
		return result;
	}

}
