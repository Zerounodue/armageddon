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
        private static int[] pressures = new int[NOT];
            
        
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
        
        public static synchronized void report(int p, String uId, int index){
            
            count--;
            
            pressures[index] = p;
            
            /*
            switch (uId) {
                case "qMZ":
                    t1 = true;
                    pressures[i] = p;
                    break;
                case "qFs":
                    t2 = true;
                    break;
                case "qFm":
                    t2 = true;
                    break;
                case "qFz":
                    t2 = true;
                    break;
            }
                    */
            
            if(count == 0){
                System.out.println("yup");
                count = NOT;
            }
                    
        }
}
