/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armageddon;

import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.IPConnection;
import java.time.Clock;
import java.util.ArrayList;

public class ExampleCallback {
        private final static int NOT = 4;
        private static String[] UIDs = {"qMZ", "qFs", "qFm", "qFz"};
        private static ArrayList<Runnable> threads = new ArrayList<>();
     
        private static boolean t1;
        private static boolean t2;
        
        private static int count = NOT;
        private static int[][] pressures = new int[NOT][2];
            
        
	// Note: To make the example code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
            
            for (int i = 0; i < NOT; i++) {
                String uid = UIDs[i];
                MeasureThread t = new MeasureThread(uid, i);
                t.run();
                threads.add(t);
            }
            
            System.out.println("Press key to exit"); System.in.read();

	}
        
        public static synchronized void report(int p,int oldp, String uId, int index){
            
            count--;
            boolean all=true;
            
            pressures[index][0] = p;
            pressures[index][1] = oldp;
           // System.out.println(oldp);
  
            if(count == 0){
               // System.out.println("yup");
                for(int i=0; i<pressures.length; i++){
                    //System.out.println("now "+pressures[i][0]+" old "+pressures[i][1]);
                    System.out.println(Math.abs(pressures[i][0]- pressures[i][1]));
                    if(Math.abs((int)(pressures[i][0]- pressures[i][1] ))>2){
                        //System.out.println("now "+pressures[i][0]+" old "+pressures[i][1]);
                        
                        all=all&&true;
                    }
                    else{all=all&&false;}
                }
                System.out.println("");
                
                count = NOT;
                if(all){
                    System.out.println("All changed");
                }
                all=true;
            }
                    
        }
}
