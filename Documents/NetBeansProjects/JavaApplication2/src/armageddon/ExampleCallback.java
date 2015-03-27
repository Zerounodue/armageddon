/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armageddon;

import java.util.ArrayList;

public class ExampleCallback {
        //Number Of Threads
        private final static int NOT = 4;
        //minimal change to recognise a peak
        private final static int MINIMAL_CHANGE = 20;
        //names of barometers
        private static String[] UIDs = {"qMZ", "qFs", "qFm", "qFz"};
        //barometer thread list
        private static ArrayList<Runnable> threads = new ArrayList<>();

        private static int count = NOT;
        //stored name, old pressure and actual pressure of all barometers
        private static int[][] pressures = new int[NOT][2];
        
        private static volatile int state =0;       
      
        
	// Note: To make the example code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
            //create threads for barometers
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
            
           
            //actual pressure
            pressures[index][0] = p;
            //old pressure
            pressures[index][1] = oldp;

            //only if this method was called form all barometers...
            if(count == 0){
                
                for(int i=0; i<pressures.length; i++){
                    //first pressure is not relevant
                    if((pressures[i][0] - pressures[i][1])>10000){break;}
                    
                    //print datas from barometers
                     //System.out.println(pressures[i][0] - pressures[i][1]);
                    //one barometer measured a positive change
                    if((int)(pressures[i][0]- pressures[i][1] )>MINIMAL_CHANGE){  
                        allPlus++;
                    }
                    //one barometer measured a negative change
                    else if((int)(pressures[i][0]- pressures[i][1] )<-MINIMAL_CHANGE){  
                        allMinus++;
                    }
                }
                
                
                //    \  /
                //     \/
                //a peak is "open" if there are allMinus and then 
                
                
                //     /\
                //    /  \
                //a peak is "cose" if there are allPlus and then allMinus
                
                count = NOT;
                //at least 2 barometer mesured a positive change
                if(allPlus>=2){               
                   // System.out.println("AllPlus state= "+state);                                      
                    if(state==-1){
                        //two="close";
                        System.out.println("open");
                        state=0;
                    }
                    else{
                    state =1;
                    }
                }
                //at least 2 barometer mesured a negative change
                else if(allMinus>=2){
                   // System.out.println("AllMinus state ="+state);
                    if(state==1){
                        System.out.println("close");
                        //two="";
                        state=0;
                    }
                    else{
                    state= -1;
                    }                   
                }
                //System.out.println("");
                allMinus=0;
                allPlus=0;   
            }                
        }
}
