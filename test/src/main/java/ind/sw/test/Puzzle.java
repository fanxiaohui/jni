package ind.sw.test;

public class Puzzle {

	public static void main(String[] args) {
		Object o = fff();
		System.out.println(o);
	}

	public static Object fff() {
		Object o = new Object();
		System.out.println(o);
		return o;
	}

	public static void f() {
		System.out.println(10 % 3);
		int[] i = new int[5];
		int[] ii = { 1, 2 };
		i = ii;
		System.out.println(i.length);

		System.out.println(2 - 1.1);
		System.out.println(7 ^ 0);
		System.out.println(Math.pow(10, -2));
	}

	public static void ff() {
		final int a = 10;
		int b = a;
		b = 3;
		System.out.println(a);
		System.out.println(b);
	}
}
