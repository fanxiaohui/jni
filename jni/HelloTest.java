public class HelloTest{
	public static void main(String[] args){
		HelloNative.greeting();
	}
	static{
		System.loadLibrary("HelloNative");
	}
}