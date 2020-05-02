package com.example.to_doproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private static final String                TAG   = "****TaskAdapter****";
    private              List<Task>            tasks = new ArrayList<>();
    private              TaskItemEventListener eventListener;

    public TaskAdapter(TaskItemEventListener eventListener) { this.eventListener = eventListener; }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) { holder.bindTask(tasks.get(position)); }

    @Override
    public int getItemCount() { return tasks.size(); }

    public void EditItem(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                notifyItemChanged(i);
                Log.i(TAG, "EditItem: on recycle list");
                break;
            }
        }
    }

    public void addItem(Task task) {
        tasks.add(0, task);
        notifyItemInserted(0);
        Log.i(TAG, "addItem: on recycle list position 0");
    }

    public void addItems(List<Task> tasks) {
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
        Log.i(TAG, "addItems: refresh list recycler");
    }

    public void deleteItem(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.remove(i);
                notifyItemRemoved(i);
                Log.i(TAG, "deleteItem: on recycle list by position" + i + " id=" + task.getId());
                break;
            }
        }
    }

    public void clearItems() {
        Log.i(TAG, "clearItems: clear all task on recycler list");
        tasks.clear();
        notifyDataSetChanged();
    }

    public interface TaskItemEventListener {
        //every action on item view
        void onDeleteItemClick(Task task);

        void onEditItemClick(Task task);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private View     deleteBtn;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox  = itemView.findViewById(R.id.chk_item_task);
            deleteBtn = itemView.findViewById(R.id.imBtn_item_delete);
        }

        public void bindTask(final Task task) {
            checkBox.setChecked(task.isCompleted());
            checkBox.setText(task.getTitle());

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: eventListener onDeleteItem send task selected for deleted...");
                    eventListener.onDeleteItemClick(task);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.i(TAG, "onLongClick: eventListener onEditItem send task selected for edited...");
                    eventListener.onEditItemClick(task);
                    return false;
                }
            });
        }
    }
}
