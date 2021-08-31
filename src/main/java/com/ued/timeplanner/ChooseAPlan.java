package com.ued.timeplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class ChooseAPlan extends AppCompatActivity {

    public void dayPlanFunc(View view){
        Intent dayPlanIntent=new Intent(getApplicationContext(), CreateAPlan.class);
        startActivity(dayPlanIntent);
    }
    public void weekPlanFunc(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_aplan);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure that you want to go back?")
                .setMessage("If you exit this view, your plan will be discarded")
                .setPositiveButton("Exit the View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",null )
                .show();
        return super.onOptionsItemSelected(item);
    }
}
