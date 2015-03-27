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
        //Number Of Threads
        private final static int NOT = 4;
        //
        private final static int MINIMAL_CHANGE = 20;
        private static String[] UIDs = {"qMZ", "qFs", "qFm", "qFz"};
        private static ArrayList<Runnable> threads = new ArrayList<>();
     
        private static boolean t1;
        private static boolean t2;
        
        private static int count = NOT;
        private static int[][] pressures = new int[NOT][2];
        
        private static volatile int one =0;
        
      
            
        
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
            int allPlus=0;
            int allMinus=0;
            
           
            
            pressures[index][0] = p;
            pressures[index][1] = oldp;
           // System.out.println(oldp);
  
            if(count == 0){
                
               // System.out.println("yup");
                for(int i=0; i<pressures.length; i++){
                    if((pressures[i][0] - pressures[i][1])>10000){break;}
                    //System.out.println(Math.abs(pressures[i][0]- pressures[i][1]));
                     //System.out.println(pressures[i][0] - pressures[i][1]);
                    if((int)(pressures[i][0]- pressures[i][1] )>MINIMAL_CHANGE){  
                        allPlus++;
                    }
                    else if((int)(pressures[i][0]- pressures[i][1] )<-MINIMAL_CHANGE){  
                        allMinus++;
                    }
                    //else{all=all&&false;}
                }
                
                
                
                count = NOT;
                if(allPlus>=2){
                    
                   // System.out.println("AllPlus one= "+one);
                    
                    
                    if(one==-1){
                        //two="close";
                        System.out.println("open");
                        one=0;
                    }
                    else{
                    one =1;
                    }
                    
                   
                }
                else if(allMinus>=2){
                   // System.out.println("AllMinus one ="+one);
                    if(one==1){
                        System.out.println("close");
                        //two="";
                        one=0;
                    }
                    else{
                    one= -1;
                    }
                    
                }
                //System.out.println("");
                allMinus=0;
                allPlus=0;
                
                
                
                   
            }
                    
        }
}
