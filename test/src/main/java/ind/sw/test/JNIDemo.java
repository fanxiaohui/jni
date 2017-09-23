package ind.sw.test;

public class JNIDemo {
	static{
		System.out.println(System.getProperty("java.library.path"));
		System.loadLibrary("JNIDemo");
	}
	public native void sayHello();  
    public static void main(String[] args){  
        //调用动态链接库  
        JNIDemo jniDemo = new JNIDemo();  
        jniDemo.sayHello();  
    }  
}
