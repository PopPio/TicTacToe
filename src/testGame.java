
public class testGame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TheGame a = new TheGame();
		System.out.println(a.clicked("x", 0));
		System.out.println(a.clicked("o", 2));
		System.out.println(a.clicked("x", 1));
		System.out.println(a.clicked("o", 3));
		System.out.println(a.clicked("x", 5));
		System.out.println(a.clicked("o", 4));
		System.out.println(a.clicked("x", 6));
		System.out.println(a.clicked("o", 7));
		System.out.println(a.clicked("X", 8));
	}

}
