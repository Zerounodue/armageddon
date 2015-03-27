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
    private int index;
    private int oldBar1 = 0;
    
    //only with this parameter peaks are recognised
    //Air pressure moving average lenght
    private final short apmal =1;
    //Air pressure average lenght
    private final short apal=10;
    //Temperature average lenght
    private final short tal=5;
    
    public MeasureThread(String uid, int i){
        UId = uid;
        index = i;
    }
    
    @Override
    public void run() {
        IPConnection ipcon1 = new IPConnection(); // Create IP connection
	BrickletBarometer b1 = new BrickletBarometer(UId, ipcon1); // Create device object
       
               
        try {
            ipcon1.connect(HOST, PORT); // Connect to brickd
            //set parameters
            b1.setAveraging(apmal, apal, tal);
            b1.setAirPressureCallbackPeriod(500);
            b1.addAirPressureListener(new BrickletBarometer.AirPressureListener() {
			public void airPressure(int airPressure) {
                            ExampleCallback.report(airPressure, oldBar1,  UId, index);
                            oldBar1 = airPressure;

			}
		});

            
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

    private void setAveraging(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
