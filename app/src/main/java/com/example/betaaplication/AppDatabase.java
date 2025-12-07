package com.example.betaaplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {Data.class, Client.class, Project.class, Ventana.class},
        version = 7
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoData daoData();
    public abstract ClientDao clientDao();
    public abstract ProjectDao projectDao();
    public abstract VentanaDao ventanaDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "dbApp")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
