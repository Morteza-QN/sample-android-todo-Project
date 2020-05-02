package com.example.to_doproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity implements TaskDialog.AddNewTaskCallback {
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    TaskAdapter  adapter = new TaskAdapter();
    View         addNewTaskFab;

    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteHelper = new SQLiteHelper(this);

        SQLiteStudioService.instance().start(this);                              // For SQLite studio connect
        //        SQLiteStudioService.instance().setPort(9999);                         // Using custom port number:
        //        SQLiteStudioService.instance().addIpToBlackList("*");                 // block all
        //        SQLiteStudioService.instance().addIpToWhiteList("192.168.1.110");     // my IP
        //        SQLiteStudioService.instance().setPassword("super_secret!!!");

        addNewTaskFab = findViewById(R.id.fab_main_newTask);
        recyclerView  = findViewById(R.id.rv_main_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);


        adapter.addItems(sqLiteHelper.getTasks());

        addNewTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDialog dialog = new TaskDialog();
                dialog.show(getSupportFragmentManager(), null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        SQLiteStudioService.instance().stop();
        super.onDestroy();
    }

    @Override
    public void onNewTask(Task task) {
        long taskId = sqLiteHelper.addTask(task);
        if (taskId != -1) {
            task.setId(taskId);
            adapter.addItem(task);
        }
        else {Log.e(TAG, "onNewTask: item not insert");}
    }
}
