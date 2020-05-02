package com.example.to_doproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditTaskDialog extends DialogFragment {
    private static final String TAG = "**EditTaskDialog**";

    private TextInputEditText titleEt;
    private TextInputLayout   inputLayout;
    private View              saveBtn;
    private EditTaskCallback  callback;
    private Task              task;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (EditTaskCallback) context;
        task     = getArguments().getParcelable("task");
        if (task == null) {
            dismiss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View                view    = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_task, null, false);

        titleEt     = view.findViewById(R.id.et_dialogEdit_title);
        inputLayout = view.findViewById(R.id.etl_dialogEdit_title);
        saveBtn     = view.findViewById(R.id.btn_dialogEdit_save);
        titleEt.setText(task.getTitle());
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleEt.getText().toString().trim().length() > 0) {
                    task.setTitle(titleEt.getText().toString().trim());
                    Log.i(TAG, "onClick save btn : onEdit callback send task from dialog ");
                    callback.onEditTask(task);
                    dismiss();
                    // TODO: 5/2/2020 hide keyboard
                }
                else {
                    inputLayout.setError("خالی نباشد");
                }
            }
        });
        builder.setView(view);
        return builder.create();
    }

    interface EditTaskCallback {
        void onEditTask(Task task);
    }
}
