/* 
 * Matt Warhaftig
 * 05-30-2012
 * Rules:
 * Create m*n 2D board with each square having a value from 0 to 9.  
 * Two players, one turn each, high score wins.
 * First player chooses 3*3 section of the board and gets assigned a random value from that area.
 * Second player, knows first player's score and chooses 3*3 section of the board to try and get a higher value.
*/

package tsgb;

import java.util.ArrayList;
import java.util.Random;
import tsgb.StandardDeviation;

public class TopScoreBoardGame {

    private int[][] board;
    public int boardSizeN;
    public int boardSizeM;
    private Random generator = new Random();
    
    // Construct random size board (lengths between 0 and 20) and populate with values.
    TopScoreBoardGame(){
        do{
            boardSizeN=generator.nextInt(20);
        }while(boardSizeN<3);
        
        do{
            boardSizeM=generator.nextInt(20);
        }while(boardSizeM<3);
        
        board=new int[boardSizeM][boardSizeN];
        // Assign each cell a random value between 0 and 9.
        for(int m=0;m<board.length;m++){
            for(int n=0;n<board[m].length;n++){
                board[m][n]=generator.nextInt(10);
            }
        }
    }
    
    // Print out the game board. m is y-axis, n is x-axis.
    public void printBoard(){
        for(int m=0;m<boardSizeM;m++){
            for(int n=0;n<boardSizeN;n++){ 
                System.out.print(board[m][n]+" ");
            }   
            System.out.println();
        }
    }
    
    // For inputed coordinates get a 3x3 block and choose a random value from it.
     private int getComputeRandomScore(int m,int n){
        
        // Choose random m and n values from the 3x3 section.
        int randomM=generator.nextInt(3)-1; //int between -1 and +1.
        randomM=m-randomM;
        
        int randomN=generator.nextInt(3)-1; //int between -1 and +1.
        randomN=n-randomN;
        
        System.out.println("Randomly choosen box is:  "+randomM+","+randomN);
        
        // If chosen location falls off board then it is "0"
        if(randomM<0 || randomN<0){
            System.out.println("Randomly chosen box off board.");
            return 0;
        }
        if(randomM>boardSizeM-1 || randomN>boardSizeN-1){
            System.out.println("Randomly chosen box off board.");
            return 0;
        }
        return board[randomM][randomN];
        
    }
    
    // Player A's turn.  Chooses middle of 3x3 group with highest average points.
    public int[] playerATurn(){
        int mStart;
        int mEnd;
        int nStart;
        int nEnd;
        int bestScore=0;
        int bestM=0;
        int bestN=0;
        
        for (int m=0;m<boardSizeM;m++){
            // Define 3x3 grid accounting for edges of board.
            mStart=(m==0)?0:m-1;
            mEnd=(m==boardSizeM-1)?boardSizeM-1:m+1;
            for (int n=0;n<boardSizeN;n++){
                nStart=(n==0)?0:n-1;
                nEnd=(n==boardSizeN-1)?boardSizeN-1:n+1;
                
                int scoreValue=0;
                for(int smallM=mStart;smallM<=mEnd;smallM++){
                    for(int smallN=nStart;smallN<=nEnd;smallN++){
                        scoreValue=scoreValue + board[smallM][smallN];
                    } 
                }
                // Keep best score and it m,n values.
                if(scoreValue>bestScore){
                    bestScore=scoreValue;
                    bestM=m;
                    bestN=n;
                }
            }
        }
        
        System.out.println("Player A chooses: ["+bestM+","+bestN+"]");
        return new int[]{bestM,bestN};
    }
    
    // Player B's turn.  Get the most squares with a value greater than Player A's value.  If squares tie use lowest standard deviation.
    public int[] playerBTurn(int PlayerAScore){
        int mStart;
        int mEnd;
        int nStart;
        int nEnd;
        int bestM=0;
        int bestN=0;
        int bestAvailMoves=0;
        double bestStdDev=100;
        ArrayList<Double> boxes=null;
        
        // Define 3x3 grid accounting for edges of board.
        for (int m=0;m<boardSizeM;m++){
            mStart=(m==0)?0:m-1;
            mEnd=(m==boardSizeM-1)?boardSizeM-1:m+1;
            for (int n=0;n<boardSizeN;n++){
                nStart=(n==0)?0:n-1;
                nEnd=(n==boardSizeN-1)?boardSizeN-1:n+1;
                
                int scoreValue=0;
                int availMoves=0;
                boxes=new ArrayList<Double>();
                double stdDev=0;
                
                for(int smallM=mStart;smallM<=mEnd;smallM++){
                    for(int smallN=nStart;smallN<=nEnd;smallN++){
                        // Only record cell values that beat Player A.
                        int value=board[smallM][smallN];
                        if(value<=PlayerAScore){ continue;}
                        scoreValue=scoreValue + value;
                        availMoves++;
                        boxes.add((double)value);
                    } 
                }
                
                // Calculate standard deviation for cells that beat Player A.
                stdDev=StandardDeviation.getStdDev(scoreValue,availMoves,boxes);
                //System.out.println("Center=["+m+","+n+"] has availMoves="+availMoves+" and stdDev="+stdDev);
                
                // If more better moves are available or same and better StdDev then it is the best move.
                if((bestAvailMoves<availMoves)||(bestAvailMoves==availMoves && stdDev<bestStdDev)){
                    bestAvailMoves=availMoves;
                    bestStdDev=stdDev;
                    bestM=m;
                    bestN=n;
                }
            }
        }
        
        System.out.println("Player B chooses: ["+bestM+","+bestN+"]");
        return new int[]{bestM,bestN};
    }
    
    public static void main(String[] args) {
        // Instantiate and print the board.
        TopScoreBoardGame tsbg=new TopScoreBoardGame();
        System.out.println("The board:");
        tsbg.printBoard();
        System.out.println();
        
        // Have both players take their turn.
        int[] playerAMove=tsbg.playerATurn();
        int playerAScore=tsbg.getComputeRandomScore(playerAMove[0],playerAMove[1]);
        System.out.println("Player A scored:  "+playerAScore+"\n");

        int[] playerBMove=tsbg.playerBTurn(playerAScore);
        int playerBScore=tsbg.getComputeRandomScore(playerBMove[0],playerBMove[1]);
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