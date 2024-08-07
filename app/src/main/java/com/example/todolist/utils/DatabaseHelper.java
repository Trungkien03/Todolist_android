package com.example.todolist.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todolist.models.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "STATUS";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, STATUS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTask (TodoModel model) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, model.getTask());
        values.put(COL_3, model.getStatus());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateTask (int id, String task) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, task);
        db.update(TABLE_NAME, values, "ID = ?", new String[] { String.valueOf(id) });
        db.close();
    }


    public void updateStatus (int id, int status) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3, status);
        db.update(TABLE_NAME, values, "ID = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTask (int id) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    @SuppressLint("Range")
    public List<TodoModel> getAllTasks() {
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<TodoModel> todoList = new ArrayList<>();
        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        TodoModel model = new TodoModel();
                        model.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        model.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                        model.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
                        todoList.add(model); // Thêm model vào danh sách
                    } while (cursor.moveToNext());}
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
        }
        return todoList;
    }
}
