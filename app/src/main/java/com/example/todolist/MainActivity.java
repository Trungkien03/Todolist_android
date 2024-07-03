package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.UI.AddNewTask;
import com.example.todolist.adapters.TodoAdapter;
import com.example.todolist.models.TodoModel;
import com.example.todolist.utils.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private DatabaseHelper mydb;
    private List<TodoModel> mlist;
    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.taskRecyclerView);
        mFab = findViewById(R.id.fab);
        mydb = new DatabaseHelper(MainActivity.this);
        mlist = new ArrayList<>();
        adapter = new TodoAdapter(mydb, MainActivity.this);


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);


        mlist = mydb.getAllTasks();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDialogClose(DialogInterface dialog) {
        mlist = mydb.getAllTasks();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);
        adapter.notifyDataSetChanged();


    }
}