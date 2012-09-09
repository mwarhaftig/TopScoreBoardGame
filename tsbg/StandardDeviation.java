/* 
 * Matt Warhaftig
 * 07-01-2012
 * Helper math class for TopScoreBoardGame.
*/

package tsbg;

import java.util.ArrayList;

public class StandardDeviation {
    
    // Calculate average (scores / available moves).
    private static double getAvg(int scoreValue,int availMoves){
        if(availMoves==0){ return 0; }
        return new Double(scoreValue)/new Double(availMoves); 
    }
    
    // Calculate population standard deviation.
    public static double getStdDev(int scoreValue,int availMoves, ArrayList<Double> inputValues){
        double avg=getAvg(scoreValue,availMoves);
        // Use really high SD if no available moves.
        if(avg==0){ return 100; }
        
        // Do the SD formula.
        double values=0;
        for(int i=0;i<inputValues.size();i++){
            values=values+Math.pow(inputValues.get(i)-avg,2);
        }
        return Math.sqrt(values/inputValues.size());
    }    
    
}
