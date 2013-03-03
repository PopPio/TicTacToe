import java.io.Serializable;


public class PassingObjectB implements Serializable{
	private static final long serialVersionUID = 7526472295622776146L;
	char protocol;
	String symbol;
	int position;
	String name;
	String text;
	int id;
	boolean isSpec;
	
	public void sendGame(String symbol, int position){
		protocol = 'g'; //game
		this.symbol  = symbol;
		this.position = position;
	}
	
	public void sendChat(String name, String text, boolean isSpec){
		protocol = 'c'; //chat
		this.name = name;
		this.text = text;
		this.isSpec = isSpec;
	}
	
	public void leave(){
		protocol = 'e'; //end
	}
	
	public void surender(String symbol){
		protocol = 's'; //surrender
		this.symbol = symbol;
	}
	
	public void resetScore(){
		protocol = 'r'; //reset
	}
	
	public void win(String symbol){
		protocol = 'w'; //win
		this.symbol = symbol;
	}
	
	public void draw(){
		protocol = 'd'; //draw
	}
	
	public void setStart(String symbol){
		protocol = 'b';
		this.symbol = symbol;
	}
	
	public void setName(String name){
		protocol = 'n';
		this.name = name;
	}
}
