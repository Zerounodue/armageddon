/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armageddon;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public class MeasureThread implements Runnable {
    private static final String HOST = "localhost";
    private static final int PORT = 4223;
    private String UId;
    private int oldBar1 = 0;
    
    public MeasureThread(String uid){
        UId = uid;
    }
    
    @Override
    public void run() {
        IPConnection ipcon1 = new IPConnection(); // Create IP connection
	BrickletBarometer b1 = new BrickletBarometer(UId, ipcon1); // Create device object
        try {
            ipcon1.connect(HOST, PORT); // Connect to brickd
            b1.setAirPressureCallbackPeriod(500);
            b1.addAirPressureListener(new BrickletBarometer.AirPressureListener() {
			public void airPressure(int airPressure) {
                            
                            if(Math.abs((int)(airPressure/10- oldBar1 ))>=2){
                                
				System.out.println("Air Pressure "+UId+": " + (int)(airPressure/10) + " cbar " + (int)(airPressure/10- oldBar1 ) + "diff" );
                               
                            }
                            
                                
			}
		});
            //System.out.println("Press key to exit"); System.in.read();
		//ipcon1.disconnect();
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(MeasureThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyConnectedException ex) {
            Logger.getLogger(MeasureThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            System.out.println("Timeout: " + UId);
            Logger.getLogger(MeasureThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotConnectedException ex) {
            System.out.println("Could not connect to " + UId);
            Logger.getLogger(MeasureThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
}
