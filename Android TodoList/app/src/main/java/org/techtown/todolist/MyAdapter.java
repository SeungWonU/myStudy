package org.techtown.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Todo> mList;
    private Context mContext;
    public MyAdapter(Context context, ArrayList<Todo> list){
        mList = list;
        mContext = context;
    }
    void setmList(List<Todo> todo){
        mList = todo;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener{

        protected TextView str;
        protected TextView num;
        protected  CheckBox checkBox;

        public MyViewHolder(@NonNull View view) {
            super(view);
            this.str=(TextView)view.findViewById(R.id.textString);
            this.num=(TextView)view.findViewById(R.id.textNum);
            this.checkBox = (CheckBox)view.findViewById(R.id.checkBox);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem Edit = contextMenu.add(Menu.NONE,1001,1,"편집");
            MenuItem Delete = contextMenu.add(Menu.NONE,1002,2,"삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }
        final AppDatabase db = Room.databaseBuilder(mContext,AppDatabase.class,"todo-db").allowMainThreadQueries().build();


        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case 1001:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_box,null,false);
                        builder.setView(view);
                        final Button buttonSubmit = (Button)view.findViewById(R.id.button_dialog_submit);
                        final EditText editStr = (EditText)view.findViewById(R.id.editText_dialog);
                        final EditText editNum = (EditText)view.findViewById(R.id.editText_num);
                      //  final CheckBox checkBox1 = (CheckBox)view.findViewById(R.id.checkBox);

                        editStr.setText(mList.get(getAdapterPosition()).getTodoString());
                        editNum.setText(mList.get(getAdapterPosition()).getTodoNum());
                     //   checkBox1.setChecked(mList.get(getAdapterPosition()).isSelected());

                       final AlertDialog dialog = builder.create();
                        buttonSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String textStr = editStr.getText().toString();
                                String textNum = editNum.getText().toString();
                                Todo too = new Todo(textStr,textNum);

                                Todo to = new Todo(textStr,textNum,false);
                                mList.set(getAdapterPosition(),to);
                                db.todoDao().update(to);
                                notifyItemChanged(getAdapterPosition());

                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;

                    case 1002:
                        mList.remove(getAdapterPosition());
                        db.todoDao().deleteAll();
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(),mList.size());

                        break;
                }
                return true;
            }
        };
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
         //  holder.str.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
         //  holder.num.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);

            holder.str.setGravity(Gravity.CENTER);
            holder.num.setGravity(Gravity.CENTER);
            holder.checkBox.setChecked(mList.get(position).isSelected());
        //h older.checkBox.setChecked(mList.get(position).isSelected());
            holder.str.setText(mList.get(position).getTodoString());
            holder.num.setText(mList.get(position).getTodoNum());
            holder.checkBox.setTag(mList.get(position));

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override //이거바꾸자
                public void onClick(View view) {
                    CheckBox cb = (CheckBox) view;
                    Todo to = (Todo)cb.getTag();

                    to.setSelected(cb.isSelected());
                    mList.get(position).setSelected(cb.isChecked());
                    if(mList.get(position).isSelected()==true) {
                        holder.str.setPaintFlags(holder.str.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.str.setBackgroundColor(Color.RED);
                    }
                    else if(mList.get(position).isSelected()==false) {
                        holder.str.setPaintFlags(0);
                        holder.str.setBackgroundColor(Color.parseColor("#2196F3"));
                    }
                    notifyDataSetChanged();

                }
            });
    }

    @Override
    public int getItemCount() {
        return (null !=mList ? mList.size() :0);
    }
}
