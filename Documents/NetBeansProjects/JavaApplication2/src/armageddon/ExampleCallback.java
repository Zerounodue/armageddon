/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armageddon;

import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.IPConnection;
import java.util.ArrayList;

public class ExampleCallback {
        private final static int NOT = 2;
        private static String[] UIDs = {"qFm", "qFz", "qMz", "qFs"};
        private static ArrayList<Runnable> threads = new ArrayList<>();
     
	// Note: To make the example code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
            
            for (int i = 0; i < NOT; i++) {
                String uid = UIDs[i];
                MeasureThread t = new MeasureThread(uid);
                t.run();
                threads.add(t);
            }

            /*
            MeasureThread t2 = new MeasureThread(UID2);
               
		IPConnection ipcon1 = new IPConnection(); // Create IP connection
		BrickletBarometer b1 = new BrickletBarometer(UID1, ipcon1); // Create device object
                IPConnection ipcon2 = new IPConnection(); // Create IP connection
		BrickletBarometer b2 = new BrickletBarometer(UID2, ipcon2); // Create device object

		ipcon1.connect(HOST, PORT); // Connect to brickd
                ipcon2.connect(HOST, PORT); // Connect to brickd

		// Don't use device before ipcon is connected

		// Set Period for air pressure and altitude callbacks to 1s (1000ms)
		// Note: The air pressure and altitude callbacks are only called every second
		//       if the air pressure or altitude has changed since the last call!
		b1.setAirPressureCallbackPeriod(500);
		b2.setAirPressureCallbackPeriod(500);

                //b.setAltitudeCallbackPeriod(1000);

		// Add and implement air pressure listener (called if air pressure changes)
		b1.addAirPressureListener(new BrickletBarometer.AirPressureListener() {
			public void airPressure(int airPressure) {
                            if(Math.abs((int)(airPressure/10- oldBar1 ))>=2){
                                
				//System.out.println("Air Pressure 1: " + (int)(airPressure/10) + " cbar " + (int)(airPressure/10- oldBar1 ) + "diff" );
                                bigChange1=(int)(airPressure/10);
                            }
                            else{bigChange1=0;}
                                oldBar1=(int)(airPressure/10);
                                
			}
		});
                b2.addAirPressureListener(new BrickletBarometer.AirPressureListener() {
			public void airPressure(int airPressure) {
                                
				if(Math.abs((int)(airPressure/10- oldBar2 ))>=2){
                                    if(bigChange1!=0){
                                      System.out.println("Air Pressure 2: " + (int)(airPressure/10) + " cbar " + (int)(airPressure/10- oldBar2 ) + "diff" );

                                    }
                                
				//System.out.println("Air Pressure 2: " + (int)(airPressure/10) + " cbar " + (int)(airPressure/10- oldBar2 ) + "diff" );
                            }
                                oldBar2=(int)(airPressure/10);
			}
		});

		// Add and implement altitude listener (called if altitude changes)
		/*
                b.addAltitudeListener(new BrickletBarometer.AltitudeListener() {
			public void altitude(int altitude) {
				System.out.println("Altitude: " + altitude/100.0 + " m");
			}
		});
                        */
/*
		System.out.println("Press key to exit"); System.in.read();
		ipcon1.disconnect();
                ipcon2.disconnect();
                */

	}
}
