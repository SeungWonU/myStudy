package org.techtown.todolist;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodoDao todoDao(); //todo를 조작함
}
