package com.example.to_dolist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    RecyclerView recyclerView;
    ArrayList<String> list;
    ArrayList<Integer> ids;
    TodoAdapter adapter; // Switched to TodoAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.todoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.fabAdd).setOnClickListener(v -> showDialog());
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            new com.google.android.material.dialog.MaterialAlertDialogBuilder(MainActivity.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Logout", (dialog, which) -> {
                        // Secure Logout: Clear the activity stack so user cannot go back
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Stay", null)
                    .show();
        });

        loadData();
    }

    void loadData() {
        list = new ArrayList<>();
        ids = new ArrayList<>();
        Cursor c = db.getTasks();
        while (c.moveToNext()) {
            ids.add(c.getInt(0));
            // Title + Desc + Date
            list.add(c.getString(1) + "\n" + c.getString(2) + "\n" + c.getString(3));
        }

        // Pass the list and a click listener to the adapter
        // Inside loadData() in MainActivity.java
        adapter = new TodoAdapter(list, position -> {
            // Show a confirmation dialog before deleting
            new com.google.android.material.dialog.MaterialAlertDialogBuilder(MainActivity.this)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to permanently delete this task?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        // Perform the delete using the ID stored in our 'ids' list
                        db.deleteTask(ids.get(position));
                        loadData(); // Refresh the list

                        // Show a Snackbar for feedback
                        com.google.android.material.snackbar.Snackbar.make(
                                recyclerView, "Task deleted permanently",
                                com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        recyclerView.setAdapter(adapter);
    }

    void showDialog() {
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder =
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(this);

        View view = getLayoutInflater().inflate(R.layout.add_dialog, null);

        EditText t = view.findViewById(R.id.editTitle);
        EditText d = view.findViewById(R.id.editDesc);
        EditText dt = view.findViewById(R.id.editDate);

        // Make the Date field non-editable by typing
        dt.setFocusable(false);
        dt.setClickable(true);

        // Show Date Picker when clicking the Date field
        dt.setOnClickListener(v -> {
            final java.util.Calendar c = java.util.Calendar.getInstance();
            int year = c.get(java.util.Calendar.YEAR);
            int month = c.get(java.util.Calendar.MONTH);
            int day = c.get(java.util.Calendar.DAY_OF_MONTH);

            android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(
                    MainActivity.this,
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        // Format: DD-MM-YYYY
                        dt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);
                    }, year, month, day);
            datePickerDialog.show();
        });

        builder.setView(view);
        builder.setPositiveButton("Save Task", (di, i) -> {
            String title = t.getText().toString();
            if(!title.isEmpty()) {
                db.insertTask(title, d.getText().toString(), dt.getText().toString());
                loadData();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}