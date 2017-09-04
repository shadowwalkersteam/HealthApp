package com.example.zohai.healthapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;

public class OfflineMode extends AppCompatActivity {

    private TextView heart_rate;
    private TextView blood_pressure;
    private TextView temperature;
    private Button TurnOn, TurnOff;

    String sensor0;
    String sensor1;
    String sensor2;

    Handler bluetoothIn;

    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        heart_rate = (TextView) findViewById(R.id.heart);
        blood_pressure = (TextView) findViewById(R.id.blood);
        temperature = (TextView) findViewById(R.id.temp);
        TurnOn = (Button) findViewById(R.id.bton);
        TurnOff = (Button) findViewById(R.id.btoff);

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {										//if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);      								//keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
//                        txtString.setText("Data Received = " + dataInPrint);
                        int dataLength = dataInPrint.length();							//get length of data received
//                        txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')								//if it starts with # we know it is what we are looking for
                        {
                             sensor0 = recDataString.substring(1,5);             //get sensor value from string between indices 1-5
//                             sensor1 = recDataString.substring(4,6);            //same again...
//                             sensor2 = recDataString.substring(6, 9);
//                            String sensor3 = recDataString.substring(16, 20);

                            heart_rate.setText(sensor0);	//update the textviews with sensor values
                            heart_rate.invalidate();
//                            temperature.setText(sensor1);
//                            blood_pressure.setText(sensor2);
//                            sensorView2.setText(" Sensor 2 Voltage = " + sensor2 + "V");
//                            sensorView3.setText(" Sensor 3 Voltage = " + sensor3 + "V");
                        }

                        if (recDataString.charAt(6) == '%')
                        {
                            sensor1 = recDataString.substring(7,10);
                            temperature.setText(sensor1);
                            temperature.invalidate();
                        }

                        if (recDataString.charAt(11) == '&')
                        {
                            sensor2 = recDataString.substring(12,16);
                            blood_pressure.setText(sensor2);
                            blood_pressure.invalidate();
                        }

                            recDataString.delete(0, recDataString.length()); 					//clear all string data
                        // strIncom =" ";
//                        dataInPrint = " ";
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        // get Bluetooth adapter
        checkBTState();

        TurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConnectedThread.write("1");
                Toast.makeText(getApplicationContext(),"Turned On",Toast.LENGTH_SHORT).show();
            }
        });

        TurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConnectedThread.write("0");
                Toast.makeText(getApplicationContext(),"Turned Off",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }
    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(BluetoothDeviceList.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }
    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }


    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }
}
