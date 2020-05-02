package com.example.to_doproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity
        implements AddTaskDialog.AddNewTaskCallback, TaskAdapter.TaskItemEventListener, EditTaskDialog.EditTaskCallback {

    private static final String TAG = "*****MainActivity*****";

    private RecyclerView recyclerView;
    private View         addNewTaskFab;
    private View         clearTasksBtn;
    private TaskAdapter  adapter = new TaskAdapter(this);
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

        clearTasksBtn = findViewById(R.id.iv_main_clearTask);
        addNewTaskFab = findViewById(R.id.fab_main_newTask);
        recyclerView  = findViewById(R.id.rv_main_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        adapter.addItems(sqLiteHelper.getTasks());

        addNewTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: btn add new task on activity , show dialog add task");
                AddTaskDialog dialog = new AddTaskDialog();
                dialog.show(getSupportFragmentManager(), null);
            }
        });

        clearTasksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: btn clear tasks on application");
                sqLiteHelper.clearAllTasks();
                adapter.clearItems();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        SQLiteStudioService.instance().stop();
        super.onDestroy();
    }

    @Override
    public void onNewTask(Task task) {
        Log.i(TAG, "onNewTask: added new task on application , implement callback add dialog");
        long taskId = sqLiteHelper.addTask(task);
        if (taskId != -1) {
            task.setId(taskId);
            adapter.addItem(task);
        }
        else {Log.e(TAG, "onNewTask: item not insert");}
    }

    @Override
    public void onDeleteItemClick(Task task) {
        Log.i(TAG, "onDeleteItemClick: deleted task on application , implement eventListener adapter");
        if (sqLiteHelper.deleteTask(task) > 0) { adapter.deleteItem(task); }
    }

    @Override
    public void onEditItemClick(Task task) {
        //main get task from adapter        //send task to edit dialog
        Log.i(TAG, "onEditItemClick: send task to edit dialog , implement eventListener adapter");
        EditTaskDialog editTaskDialog = new EditTaskDialog();
        Bundle         bundle         = new Bundle();
        bundle.putParcelable("task", task);
        editTaskDialog.setArguments(bundle);
        editTaskDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onEditTask(Task task) {
        Log.i(TAG, "onEditTask: edited title task on application , implement callback edit dialog");
        if (sqLiteHelper.updateTask(task) > 0) { adapter.EditItem(task); }
    }
}
