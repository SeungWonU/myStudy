package org.techtown.todolistfin;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addData(MyDataList myDataList);

    @Query("select * from todolist")
    public List<MyDataList>getMyData();

    @Delete
    public void delete(MyDataList myDataList);

    @Update
    public void update(MyDataList myDataList);

    @Query("delete from todolist where id =:id")
    abstract  void deleteitem(int id);

}
