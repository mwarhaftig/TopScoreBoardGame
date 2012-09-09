package tsbg;

import tsbg.TopScoreBoardGame;
import fit.ColumnFixture;

public class TsbgColumnFixture extends ColumnFixture{
    private static TopScoreBoardGame theGame;

    public boolean initializeGame() {
       theGame = new TopScoreBoardGame();
        return true;
      }
    
    public int playerAMove(){
        return theGame.playerATurn();
    }
    
    public int playerBMove(){
        return theGame.playerBTurn();
    }
    
    public boolean hasWinner(){
        char winner=theGame.chooseWinningPlayer();
        return (winner=='A' || winner=='B' || winner==' ')? true : false; 
        
    }
    
}
