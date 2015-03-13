/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String HOST = "localhost";
	private static final int PORT = 4223;
	private static final String UID1 = "qMZ"; // Change to your UID
        private static final String UID2 = "qMZ"; // Change to your UID

        private static int old =0;

	// Note: To make the example code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
               
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
				System.out.println("Air Pressure: " + (int)(airPressure) + " mbar " + "diff "+(int)(airPressure - 979828) );
                               // old=(int)(airPressure);
			}
		});
                b2.addAirPressureListener(new BrickletBarometer.AirPressureListener() {
			public void airPressure(int airPressure) {
				System.out.println("Air Pressure: " + (int)(airPressure) + " mbar " + "diff "+(int)(airPressure - 979828) );
                               // old=(int)(airPressure);
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

		System.out.println("Press key to exit"); System.in.read();
		ipcon1.disconnect();
                ipcon2.disconnect();

	}
}
