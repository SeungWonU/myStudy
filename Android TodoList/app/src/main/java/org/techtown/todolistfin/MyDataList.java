package org.techtown.todolistfin;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todolist")
public class MyDataList {
    @PrimaryKey//(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "todo")
    private String todo;

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
