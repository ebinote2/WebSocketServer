package com.example.ebicompany.websocketserver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;


public class MainActivity extends Activity {

    private HSServer server;
    private int port = 8090;
    private InetSocketAddress socketAdd;

    public MainActivity()
    {
        super();
        java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
        java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
        socketAdd = new InetSocketAddress("10.0.2.15", port);/* Inet4Address.getByAddress("8090", new byte[]{10,0,2,15}); */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            if (this.server == null){
                server = new HSServer(port,this);
            }
            else{
                //this.server.stop();
                //this.server.start();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        server.start();
    }

    @Override
    protected void onDestroy()
    {
        this.server.sendToAll("0");
        try {
            this.server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.server = null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
