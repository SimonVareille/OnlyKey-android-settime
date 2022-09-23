package com.github.simonvareille.onlykey_android_settime;

import android.app.Activity;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Parcelable;

public class UsbEventReceiverActivity extends Activity
{
    public static final String ACTION_USB_DEVICE_ATTACHED = "com.github.simonvareille.onlykey_android_settime.ACTION_USB_DEVICE_ATTACHED";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Intent intent = getIntent();
        if (intent != null)
        {
            if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED))
            {
                Parcelable usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                // Create a new intent and put the usb device in as an extra
                Intent deviceAttachedIntent = new Intent(getApplicationContext(), SetTime.class);
                deviceAttachedIntent.setAction(ACTION_USB_DEVICE_ATTACHED);
                deviceAttachedIntent.putExtra(UsbManager.EXTRA_DEVICE, usbDevice);
                //deviceAttachedIntent.setClass(this, SetTime.class);

                startService(deviceAttachedIntent);
            }
        }

        // Close the activity
        finish();
    }
}
