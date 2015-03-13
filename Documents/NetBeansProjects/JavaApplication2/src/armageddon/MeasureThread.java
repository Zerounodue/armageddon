/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armageddon;

import javaapplication2.*;
import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.IPConnection;
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
    
    public MeasureThread(String uid){
        UId = uid;
    }
    
    @Override
    public void run() {
        IPConnection ipcon1 = new IPConnection(); // Create IP connection
	BrickletBarometer b1 = new BrickletBarometer(UId, ipcon1); // Create device object
        try {
            ipcon1.connect(HOST, PORT); // Connect to brickd
            
        } catch (IOException ex) {
            Logger.getLogger(MeasureThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyConnectedException ex) {
            Logger.getLogger(MeasureThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
}
