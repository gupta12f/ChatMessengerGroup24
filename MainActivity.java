package com.example.android.chatclient;

import android.os.Bundle;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import android.view.Gravity;
import java.net.InetSocketAddress;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import android.os.StrictMode;
public class MainActivity extends Activity {
    private boolean isRunning = false;
    private String serverIpAddress = "192.168.1.136";
    final int SERVER_PORT = 10005;
    BufferedReader input;
    Socket socket = new Socket();
    EditText etMessage;
    EditText etMessage1;
    Button bSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Socket s = new Socket();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bSend.setOnClickListener(this);
        if (!isRunning) {
            try {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new
                            StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                Toast toast = Toast.makeText(MainActivity.this, serverIpAddress.toString(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                socket.connect(new InetSocketAddress(serverIpAddress, SERVER_PORT));
                // socket.connect(new InetSocketAddress(serverIpAddress, SERVER_PORT),500000);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                etMessage1 = (EditText) findViewById(R.id.etMessage1);
                String txt1 = etMessage1.getText().toString();
                out.println(txt1);
                isRunning = true;
            }
            catch (IOException e) {
                Toast toast = Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                e.printStackTrace();
                isRunning = false;
            }
        }
        bSend = (Button) findViewById(R.id.bSend);
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    etMessage = (EditText) findViewById(R.id.etMessage);
                    String txt = etMessage.getText().toString();
                    out.println(txt);
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (Exception e) {
                    Toast toast = Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    e.printStackTrace();
                }
            }
        });
    }
}