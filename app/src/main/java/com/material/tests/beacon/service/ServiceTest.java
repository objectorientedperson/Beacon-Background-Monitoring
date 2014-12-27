package com.material.tests.beacon.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.material.tests.beacon.BeaconReferenceApplication;
import com.material.tests.beacon.utils.T;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;

import java.util.Collection;

public class ServiceTest extends Service implements BootstrapNotifier, RangeNotifier, BeaconConsumer {

    Region region1 = new Region(T.BeaconName, Identifier.parse(T.UUID), null, null);
    private BeaconManager mBeaconManager = BeaconManager.getInstanceForApplication(this);

    BeaconReferenceApplication application = (BeaconReferenceApplication) getApplication();

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onCreate() {
        // Configure BeaconManager.
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //mBeaconManager = BeaconManager.getInstanceForApplication(this);
        mBeaconManager.bind(this);
        mBeaconManager.setBackgroundBetweenScanPeriod(1000);
        mBeaconManager.setBackgroundScanPeriod(1000);

        return START_STICKY;
    }

    @Override
    public void didEnterRegion(Region region) {

    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

    }

    @Override
    public void onBeaconServiceConnect() {
        mBeaconManager.setMonitorNotifier(this);
        try {

            mBeaconManager.startRangingBeaconsInRegion(region1);
            mBeaconManager.setRangeNotifier(application);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
