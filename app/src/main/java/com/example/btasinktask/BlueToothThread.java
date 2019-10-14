package com.example.btasinktask;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.example.btasinktask.SignalsMonitor.btHandler;

public class BlueToothThread extends Thread {
    private InputStream btInputStream;
    private OutputStream btOutputStream ;

    private byte[] buffData = new byte[8192];  // buffer store for the stream

    BlueToothThread(BluetoothSocket btSocket) {

        try {
            btInputStream = btSocket.getInputStream();
            btOutputStream = btSocket.getOutputStream();
        } catch (IOException iOe) {
            btInputStream = null;
        }
    }

    public void run() {
        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                /*
                // Read from the InputStream
                int bytes = btInputStream.read(buffData);
                MainPlot.btHandler.obtainMessage(0, bytes, -1, buffData).sendToTarget();		// Send to message queue Handler
                */
                int bytes = btInputStream.read(buffData);        	//read bytes from input buffer
                String readMessage = new String(buffData, 0, bytes);
                // Send the obtained bytes to the UI Activity via handler
                btHandler.obtainMessage(0, bytes, -1, readMessage).sendToTarget();
            } catch (IOException e) {
                break;
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    void SendData(String message) {
        byte[] msgBuffer = message.getBytes();
        try {
            btOutputStream.write(msgBuffer);
        } catch (IOException e) {
            Log.d(" ", "...Error data send: " + e.getMessage() + "...");
        }
    }
}
