import java.util.*;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		GameClient c = new GameClient();
		c.Connect("169.254.117.207",  4);
		PassingObject p = null;
		while(true){
			p = new PassingObject();
			p.sendGame("o", sc.nextInt());
			c.sendObject(p);
			p = new PassingObject();
			p.sendChat("pop", "FUCKKKK");
			c.sendObject(p);
		}

	}

}
