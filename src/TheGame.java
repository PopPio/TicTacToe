
public class TheGame {

	String button[];
	int turnNumber;
	
	public TheGame(){
		newGame();
	}
	
	public void newGame(){
		button = new String[9];
		for(int i = 0; i < 9; i ++){
			button[i] = "u";
		}
		turnNumber = 0;
	}
	
	public String clicked(String symbol, int position){
		button[position] = symbol;
		turnNumber += 1;
		return checkWinner();
	}
	
	public String checkWinner(){
		if(turnNumber < 5)
			return "c";
		else{
			if((button[0].equals("x") && button[1].equals("x") && button[2].equals("x"))
					|| (button[3].equals("x") && button[4].equals("x") && button[5].equals("x"))
					|| (button[6].equals("x") && button[7].equals("x") && button[8].equals("x"))
					|| (button[0].equals("x") && button[3].equals("x") && button[6].equals("x"))
					|| (button[1].equals("x") && button[4].equals("x") && button[7].equals("x"))
					|| (button[2].equals("x") && button[5].equals("x") && button[8].equals("x"))
					|| (button[0].equals("x") && button[4].equals("x") && button[8].equals("x"))
					|| (button[2].equals("x") && button[4].equals("x") && button[6].equals("x")))
				return "x";
			else if((button[0].equals("o") && button[1].equals("o") && button[2].equals("o"))
					|| (button[3].equals("o") && button[4].equals("o") && button[5].equals("o"))
					|| (button[6].equals("o") && button[7].equals("o") && button[8].equals("o"))
					|| (button[0].equals("o") && button[3].equals("o") && button[6].equals("o"))
					|| (button[1].equals("o") && button[4].equals("o") && button[7].equals("o"))
					|| (button[2].equals("o") && button[5].equals("o") && button[8].equals("o"))
					|| (button[0].equals("o") && button[4].equals("o") && button[8].equals("o"))
					|| (button[2].equals("o") && button[4].equals("o") && button[6].equals("o")))
				return "o";
			else if(turnNumber == 9)
				return "d";
		}
		return "c";
	}
}
