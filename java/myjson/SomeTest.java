package myjson;

public class SomeTest {
    public static void main(String[] args) {
        B b = new B("1");
    }

    static class A {
        public A(){
            System.out.println("A");
        }
    }

    static class B extends A {
        public B(String s) {
            System.out.println("B");
        }
    }
}
