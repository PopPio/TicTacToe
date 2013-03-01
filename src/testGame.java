import java.util.*;

public class testGame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Server s = new Server();
		GameClient c = new GameClient();
		c.Connect("127.0.0.1", 4);
		PassingObject p = null;
		while(true){
			p = new PassingObject();
			int position = sc.nextInt();
			p.sendGame("x", position);
			c.sendObject(p);
			p = new PassingObject();
			p.sendChat("Touch", "hello");
			c.sendObject(p);
			p = new PassingObject();
			p.leave();
			c.sendObject(p);
		}
	}

}
