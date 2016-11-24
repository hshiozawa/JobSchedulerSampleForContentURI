package com.hjm.jobschedulersample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


public class CameraBroadcastReceiver extends BroadcastReceiver {
    private final static String TAG = CameraBroadcastReceiver.class.toString();

    @Override
    public void onReceive(Context context, final Intent intent) {
        Log.i(TAG, "onReceive()");
        String action = intent.getAction();
        if (isCamera(action)) {
            // do in background thread
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... parameters) {
                    doProcess(intent);
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                }
            }.execute();
        }
    }

    private static void doProcess(Intent intent) {
        Uri uri = intent.getData();
        if (uri != null) {
            Log.i(TAG, " - " + uri.toString());
        } else {
            Log.i(TAG, "no content");
        }
    }

    private static boolean isCamera(String action) {
        return android.hardware.Camera.ACTION_NEW_PICTURE.equals(action)
                || android.hardware.Camera.ACTION_NEW_VIDEO.equals(action);
    }

    public static void register(Context context) {
        Log.i(TAG, "register");
        IntentFilter filter = new IntentFilter();
        filter.addAction(android.hardware.Camera.ACTION_NEW_PICTURE);
        filter.addAction(android.hardware.Camera.ACTION_NEW_VIDEO);

        try {
            filter.addDataType("image/*");
            filter.addDataType("video/*");
        } catch (Exception e) {
            Log.e(TAG, "Fail: + " + e.getMessage());
        }

        CameraBroadcastReceiver receiver = new CameraBroadcastReceiver();
        context.registerReceiver(receiver, filter);
    }
}
