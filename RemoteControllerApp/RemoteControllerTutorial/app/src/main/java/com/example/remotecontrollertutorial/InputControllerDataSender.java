package com.example.remotecontrollertutorial;

import android.util.Log;

import com.example.remotecontrollertutorial.models.InputModel;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputControllerDataSender {
    private int serverPort;
    private InputModel data;
    private AtomicBoolean sendingData;
    private InetAddress address;

    public InputControllerDataSender(int serverPort, InputModel data) {
        this.serverPort = serverPort;
        sendingData = new AtomicBoolean(false);
        this.data = data;
    }

    public void setAddress(InetAddress address){
        this.address = address;
    }

    public void sendData() {
        if (sendingData.get()) {
            throw new IllegalStateException("Ya se están enviando datos");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                sendingData.set(true);
                sendDataToSocket();
            }
        }).start();
    }

    public void stopSendingData() {
        sendingData.set(false);
    }

    public boolean isSendingData() {
        return sendingData.get();
    }

    private void sendDataToSocket() {
        String json;

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(1000);
            if(address == null){
                address = InetAddress.getLoopbackAddress();
            }
            while (sendingData.get()) {
                json = data.toJSON().toString();
                byte[] content = json.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(
                        content, content.length, address, serverPort);

                socket.send(datagramPacket);
                Thread.sleep(15);
            }
        } catch (Exception e) {
            sendingData.set(false);
            Log.d("SOCKETE", e.getMessage());
        }
    }
}
