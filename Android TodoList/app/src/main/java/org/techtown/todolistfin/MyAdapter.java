package org.techtown.todolistfin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<MyDataList>myDataLists;

    public MyAdapter(List<MyDataList>myDataLists)
    {
        this.myDataLists = myDataLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       MyDataList myDataList = myDataLists.get(position);

       holder.txtId.setText(String.valueOf(myDataList.getId()));//id값이 int이기 때문에 바꿔줌
       holder.txtType.setText("<"+myDataList.getType()+">");
       holder.txtTodo.setText(myDataList.getTodo());
    }

    @Override
    public int getItemCount() {
        return myDataLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtId,txtType,txtTodo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId =(TextView)itemView.findViewById(R.id.txt_id);
            txtType=(TextView)itemView.findViewById(R.id.txt_type);
            txtTodo=(TextView)itemView.findViewById(R.id.txt_todo);
        }
    }
}
