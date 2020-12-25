package org.techtown.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao { //Todo에 대해 어떤 동작이 진행되는 지를 말해줌
    @Query("SELECT * FROM Todo")
    LiveData<List<Todo>> getAll();

    @Insert
    void insert(Todo todo);

    @Query("DELETE FROM Todo")
    void deleteAll();

    @Delete
    void delete(Todo todo);

    @Update
    void update(Todo todo);

    

}
