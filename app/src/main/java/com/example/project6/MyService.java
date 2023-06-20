package com.example.project6;

import android.app.Service;
import android.content.Intent;

import android.os.IBinder;


import androidx.annotation.Nullable;
import androidx.room.Room;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MyService extends Service {

    final class MyThread implements Runnable {

        int serviceId;
        PlaceDatabase database;
        PlaceDao placeDao;

        public MyThread(int serviceId) {
            this.serviceId = serviceId;
        }

        @Override
        public void run() {
            synchronized (this) {
                while (true) {
                    try {
                        System.out.println("Doing things");
                        database = Room.databaseBuilder(getApplicationContext(), PlaceDatabase.class, "placesDb")
                                .allowMainThreadQueries()
                                .build();
                        placeDao = database.getPlaceDao();

                        Intent intent = new Intent();
                        intent.setAction("com.example.project6");
                        for(Place p : placeDao.getAllRemindPlaces()) {
                            intent.putExtra("NAME", p.getName());
                            intent.putExtra("ADDRESS", p.getAddress());
                            intent.putExtra("DESC", p.getDesc());
                            intent.putExtra("RATING", p.getRating() + "");
                            sendBroadcast(intent);
                        }
                        wait(900000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        int NUM_CORES = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor= new ThreadPoolExecutor(NUM_CORES*2, NUM_CORES*2, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        executor.execute(new MyThread(startId));
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
