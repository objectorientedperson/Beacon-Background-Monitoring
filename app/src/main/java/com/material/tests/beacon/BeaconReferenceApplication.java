package com.material.tests.beacon;

import android.app.Application;
import android.os.RemoteException;
import android.util.Log;

import com.material.tests.beacon.utils.Notifier;
import com.material.tests.beacon.utils.T;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Collection;

public class BeaconReferenceApplication extends Application implements BootstrapNotifier, RangeNotifier {
    private static final String TAG = BeaconReferenceApplication.class.getName();

    //private Region mAllBeaconsRegion;
    private MonitoringActivity mMonitoringActivity;
    private RangingActivity mRangingActivity;
    private BackgroundPowerSaver mBackgroundPowerSaver;
    @SuppressWarnings("unused")
    private RegionBootstrap mRegionBootstrap;
    private BeaconManager mBeaconManager;


    Region region1 = new Region(T.BeaconName, Identifier.parse(T.UUID), null, null);

    @Override
    public void onCreate() {
        //mAllBeaconsRegion = new Region("all beacons", null, null, null);

        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        //mBackgroundPowerSaver = new BackgroundPowerSaver(this);
        mRegionBootstrap = new RegionBootstrap(this, region1);


        // By default the AndroidBeaconLibrary will only find AltBeacons.  If you wish to make it
        // find a different type of beacon, you must specify the byte layout for that beacon's
        // advertisement with a line like below.  The example shows how to find a beacon with the
        // same byte layout as AltBeacon but with a beaconTypeCode of 0xaabb
        //
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        //
        // In order to find out the proper BeaconLayout definition for other kinds of beacons, do
        // a Google search for "setBeaconLayout" (including the quotes in your search.)
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> arg0, Region arg1) {
        if (arg0.size() > 0) {
            for (Beacon beacon : arg0) {
                Log.d(TAG, "Beacon " + beacon.toString() + " is about " + beacon.getDistance() + " meters away, with Rssi: " + beacon.getRssi());
            }
        }
    }

    @Override
    public void didDetermineStateForRegion(int arg0, Region arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void didEnterRegion(Region arg0) {
        //saw first time.
        try {
            Log.d(TAG, "I just saw a beacon named " + arg0.getUniqueId() + " for the first time!");
            //Log.d(TAG, "entered region.  starting ranging");
            Notifier.generateNotification(this, "I just saw a beacon named " + arg0.getUniqueId() + " for the first time!");
            mBeaconManager.startRangingBeaconsInRegion(region1);
            mBeaconManager.setRangeNotifier(this);
        } catch (RemoteException e) {
            Log.e(TAG, "Cannot start ranging");
        }
    }

    @Override
    public void didExitRegion(Region arg0) {
        Log.d(TAG, "I no longer see a beacon named " + arg0.getUniqueId());
        Notifier.generateNotification(this, "I no longer see a beacon named " + arg0.getUniqueId());
    }

    public void setMonitoringActivity(MonitoringActivity activity) {
        mMonitoringActivity = activity;
    }

    public void setRangingActivity(RangingActivity activity) {
        mRangingActivity = activity;
    }

}
