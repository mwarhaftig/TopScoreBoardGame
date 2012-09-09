package tsbg;

import tsbg.TopScoreBoardGame;

public class TsbgActionFixture extends fit.Fixture{
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
