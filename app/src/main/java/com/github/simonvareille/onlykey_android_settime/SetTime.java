package com.github.simonvareille.onlykey_android_settime;

import android.app.Service;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.util.Pair;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

public class SetTime extends Service {

    private final ArrayList<Pair<Integer, Integer>> DEVICE_IDS = new ArrayList<>();

    private UsbDevice device;

    private final Byte[] MESSAGE_HEADER = {(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};

    private final Byte OKSETTIME = (byte) 0xe4;

    public SetTime() {
        DEVICE_IDS.add(new Pair<>(5824, 1158));
        DEVICE_IDS.add(new Pair<>(7504, 24828));
    }

    private void setTime() throws IOException {
        UsbInterface usbInterface = device.getInterface(1);
        UsbEndpoint endpoint = usbInterface.getEndpoint(1);
        UsbManager manager = (UsbManager)getSystemService(USB_SERVICE);

        UsbDeviceConnection connection = manager.openDevice(device);
        connection.claimInterface(usbInterface, true);

        long unixTime = System.currentTimeMillis() / 1000;
        // Unix time is 64bit long, but OnlyKey takes a 32bits integer.
        ByteBuffer payload = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt((int)unixTime);

        ArrayList<Byte> message = new ArrayList<>(Arrays.asList(MESSAGE_HEADER));
        message.add(OKSETTIME);
        for (byte b: payload.array()) {
            message.add(b);
        }

        byte[] msg = new byte[message.size()];
        for (int i=0; i<message.size(); i++ ) {
            msg[i] = message.get(i);
        }
        connection.bulkTransfer(endpoint, msg, message.size(), 0);
        connection.releaseInterface(usbInterface);
        connection.close();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()

        try {
            device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            if (DEVICE_IDS.contains(new Pair<>(device.getVendorId(), device.getProductId()))) {
                if (device.getSerialNumber().equals("1000000000")) {
                    setTime();
                }
                else if (device.getInterfaceCount() == 1) {
                    setTime();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        stopSelf();

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}