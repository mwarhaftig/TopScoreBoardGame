/* 
 * Matt Warhaftig
 * 05-30-2012
 * Rules:
 * Create n*m 2D board with each square having a value from 0 to 9.  
 * Two players, one turn each, high score wins.
 * First player chooses 3*3 section of the board and gets assigned a random value from that area.
 * Second player, knows first player's score and chooses 3*3 section of the board to try and get a higher value.
*/

import java.util.Random;

public class TopScoreBoardGame {

    int[][] board;
    int boardSizeN;
    int boardSizeM;
    Random generator = new Random();
    
    // Construct random size board and populate with values.
    TopScoreBoardGame(){
        do{
            boardSizeN=generator.nextInt(10);
        }while(boardSizeN==0);
        
        do{
            boardSizeM=generator.nextInt(10);
        }while(boardSizeM==0);
        
        board=new int[boardSizeM][boardSizeN];
        for(int m=0;m<board.length;m++){
            for(int n=0;n<board[m].length;n++){
                board[m][n]=generator.nextInt(10);
            }
        }
    }
    
    // Print out the game board.
    void printBoard(){
        for(int m=0;m<board.length;m++){
            for(int n=0;n<board[m].length;n++){
                System.out.print(board[m][n]+" ");
            }   
            System.out.println();
        }
    }
    
    // Player A's turn.  Right now just chooses one random square.
    int playerATurn(){
        int m=generator.nextInt(boardSizeM);
        int n=generator.nextInt(boardSizeN);
        return board[m][n];
    }
    
    // Player B's turn.  Right now just chooses one random square.
    int playerBTurn(){
        int m=generator.nextInt(boardSizeM);
        int n=generator.nextInt(boardSizeN);
        return board[m][n];
    }
    
    public static void main(String[] args) {
        // Instantiate and print the board.
        TopScoreBoardGame tsbg=new TopScoreBoardGame();
        System.out.println("The board:");
        tsbg.printBoard();
        
        // Have both players take their turn.
        int playerAScore=tsbg.playerATurn();
        System.out.println("\nPlayer A scored:  "+playerAScore);
        int playerBScore=tsbg.playerATurn();
        System.out.println("Player B scored:  "+playerBScore);
        
        // Print out which player won.
        int scoreDiff=playerAScore-playerBScore;
        if (scoreDiff==0){
            System.out.println("Tie!");
        }else if (scoreDiff >0) {
            System.out.println("Player A Wins!");
        }else{
            System.out.println("Player B Wins!");
        }
    }
}
