package reflect;

import java.lang.reflect.Field;

public class FieldTest {
	
	public short sh1 = 0;
	public Short sh2 = 0;
	public int id = 0;
	public Integer count = 0;
	public long l = 0L;
	public Long l2 = 0L;
	public float f = 0.0f;
	public Float f2 = 0.0f;
	public double d = 0.0d;
	public Double d2 = 0.0d;
	public String s = "s";

	public static void main(String[] args) {
		Object integer = null;
		Integer integer2 = (Integer) integer;
		// TODO Auto-generated method stub
		Field[] fields = FieldTest.class.getDeclaredFields();
		for(Field field : fields) {
			initField(field.getType().getName(), "0");
		}
	}
	
	public static void initField(String filed, String filedClassName) {
		switch (filed) {
		case "short":
			System.out.println("short=" + filed);
			break;
		case "java.lang.Short":
			System.out.println("Short=" + filed);
			break;
		case "int":
			System.out.println("int=" + filed);
			break;
		case "java.lang.Integer":
			System.out.println("Integer=" + filed);
			break;
		case "long":
			System.out.println("long=" + filed);
			break;
		case "java.lang.Long":
			System.out.println("Long=" + filed);
			break;
		case "float":
			System.out.println("float=" + filed);
			break;
		case "java.lang.Float":
			System.out.println("Float=" + filed);
			break;
		case "double":
			System.out.println("double=" + filed);
			break;
		case "java.lang.Double":
			System.out.println("Double=" + filed);
			break;
		case "java.lang.String":
			System.out.println("String=" + filed);
			break;
		default:
			break;
		}
	}

}
