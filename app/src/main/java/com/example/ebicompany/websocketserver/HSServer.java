package com.example.ebicompany.websocketserver;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

/**
 * Created by ebicompany on 9/15/14.
 */
public class HSServer extends WebSocketServer {

    Activity context;
    EditText clientMessageArea;

    public HSServer( int port , Activity ctx) throws UnknownHostException {
        super( new InetSocketAddress( port ) );
        this.context = ctx;
    }

    public HSServer( InetSocketAddress address ,Activity ctx) {
        super( address );
        this.context = ctx;
        clientMessageArea = (EditText)this.context.findViewById(R.id.editText);
        clientMessageArea.setLines(10);
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        //Toast.makeText(this.context.getApplicationContext(),"New client is connected", Toast.LENGTH_LONG);
        clientMessageArea.post(new Runnable() {
            @Override
            public void run() {
                clientMessageArea.setText("New client is connected");
            }
        });
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        Toast.makeText(this.context.getApplicationContext(),"A client is disconnected", Toast.LENGTH_LONG);
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        final String message = s;
        clientMessageArea.post(new Runnable() {
            @Override
            public void run() {
                clientMessageArea.setText(message);
            }
        });
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        //Toast.makeText(this.context.getApplicationContext(),"New client is connected", Toast.LENGTH_LONG);
        String message = e.getMessage();
        System.out.println(message);
    }

    public void sendToAll( String text ) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                c.send( text );
            }
        }
    }
}
