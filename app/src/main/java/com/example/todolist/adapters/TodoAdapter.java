package com.example.todolist.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.MainActivity;
import com.example.todolist.R;
import com.example.todolist.models.TodoModel;
import com.example.todolist.utils.DatabaseHelper;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {

    private List<TodoModel> mList;
    private MainActivity activity;
    private DatabaseHelper mydb;

    public TodoAdapter(DatabaseHelper mydb, MainActivity activity) {
        this.mydb = mydb;
        this.activity = activity;
        mList = mydb.getAllTasks();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TodoModel item = mList.get(position);
        holder.mCheckBox.setText(item.getTask());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mydb.updateStatus(item.getId(), 1);
                } else {
                    mydb.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public boolean toBoolean (int num) {
        return num != 0;
    }

    public Context getContext() {
        return activity;
    }

    public void setTasks(List<TodoModel> tasks) {
        mList = tasks;
        notifyDataSetChanged();
    }

    public void deleteTask(int position) {
        TodoModel item = mList.get(position);
        mydb.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask (int position) {
        TodoModel item = mList.get(position);

        Bundle bundle = new Bundle();
        bundle.getInt("id", item.getId());
        bundle.getString("task", item.getTask());

        mydb.updateTask(item.getId(), item.getTask());
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.todoCheckbox);
        }
    }

}
