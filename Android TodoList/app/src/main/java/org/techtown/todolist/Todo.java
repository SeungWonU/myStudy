package org.techtown.todolist;
import android.widget.CheckBox;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String todoNum;
    private String todoString;
    private boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Ignore
    public Todo(String todoString, String todoNum) {
        this.todoString=todoString;
        this.todoNum=todoNum;
    }
   public Todo(String todoString,String todoNum,boolean isSelected){
        this.todoString=todoString;
        this.todoNum=todoNum;
        this.isSelected = isSelected;
    }
    public boolean isSelected(){
        return isSelected;
    }
    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
    }
    public String getTodoString() {
        return todoString;
    }

    public void setTodoString(String todoString) {
        this.todoString = todoString;
    }

    public String getTodoNum(){
        return todoNum;
    }

    public void setTodoNum(String todoNum){
        this.todoNum=todoNum;
    }
}
