package classtest;

import java.lang.reflect.ParameterizedType;

public abstract class AbClass1<T> {

	Class<?> tClass = (Class<?>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
}
