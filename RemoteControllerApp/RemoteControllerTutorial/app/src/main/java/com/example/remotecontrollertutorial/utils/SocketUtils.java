package com.example.remotecontrollertutorial.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SocketUtils {
    private static InetAddress getInetAddress(String ipAddress) {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
        }
        return address;
    }

    public static InetAddress getAddress(final String address) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<InetAddress> callable = new Callable<InetAddress>() {
            @Override
            public InetAddress call() {
                return SocketUtils.getInetAddress(address);
            }
        };
        Future<InetAddress> future = executor.submit(callable);
        executor.shutdown();

        InetAddress inetAddress = null;
        try {
            inetAddress = future.get();
        } catch (Exception e) {
        }

        return inetAddress;
    }
}
