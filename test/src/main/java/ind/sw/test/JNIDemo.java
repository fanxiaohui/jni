package ind.sw.test;

public class JNIDemo {
	public native void sayHello();  
    public static void main(String[] args){  
        //调用动态链接库  
        System.loadLibrary("JNIDemo");  
        JNIDemo jniDemo = new JNIDemo();  
        jniDemo.sayHello();  
    }  
}
