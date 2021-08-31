package com.ued.timeplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//Todo: make sure to take care of the problem of making the screen resize itself without glitches due to the difference in screen sizes

public class MainActivity extends AppCompatActivity {
    ConstraintLayout p1Constraint;
    ConstraintLayout p2Constraint;
    ConstraintLayout p3Constraint;
    ConstraintLayout p4Constraint;
    ConstraintLayout p5Constraint;
    ConstraintLayout p6Constraint;

    public void nextBtn1(View view){
        p1Constraint=findViewById(R.id.p1Constraint);
        p2Constraint=findViewById(R.id.p2Constraint);
        p1Constraint.setVisibility(View.INVISIBLE);
        p2Constraint.setTranslationX(1000);
        p2Constraint.setVisibility(View.VISIBLE);
        p2Constraint.animate().translationXBy(-1000).setDuration(400);
    }
    public void nextBtn2(View view){
        p2Constraint=findViewById(R.id.p2Constraint);
        p3Constraint=findViewById(R.id.p3Constraint);
        p2Constraint.setVisibility(View.INVISIBLE);
        p3Constraint.setTranslationX(1000);
        p3Constraint.setVisibility(View.VISIBLE);
        p3Constraint.animate().translationXBy(-1000).setDuration(400);
    }
    public void nextBtn3(View view){
        p3Constraint=findViewById(R.id.p3Constraint);
        p4Constraint=findViewById(R.id.p4Constraint);
        p3Constraint=findViewById(R.id.p3Constraint);
        p4Constraint=findViewById(R.id.p4Constraint);
        p3Constraint.setVisibility(View.INVISIBLE);
        p4Constraint.setTranslationX(1000);
        p4Constraint.setVisibility(View.VISIBLE);
        p4Constraint.animate().translationXBy(-1000).setDuration(400);
    }
    public void nextBtn4(View view){
        p4Constraint=findViewById(R.id.p4Constraint);
        p5Constraint=findViewById(R.id.p5Constraint);
        p4Constraint.setVisibility(View.INVISIBLE);
        p5Constraint.setTranslationX(1000);
        p5Constraint.setVisibility(View.VISIBLE);
        p5Constraint.animate().translationXBy(-1000).setDuration(400);
    }

    public void p1NextMethod(View view){
        p5Constraint=findViewById(R.id.p5Constraint);
        p6Constraint=findViewById(R.id.p6Constraint);
        p5Constraint.setVisibility(View.INVISIBLE);
        p6Constraint.setTranslationX(1000);
        p6Constraint.setVisibility(View.VISIBLE);
        p6Constraint.animate().translationXBy(-1000).setDuration(400);
    }
    public void closeTutorial(View view){
        p6Constraint=findViewById(R.id.p6Constraint);
        p6Constraint.setVisibility(View.INVISIBLE);
    }

    //ToDo: make sure to create the other activity and call it content_create_aweek_plan.xml for the weekPlanFunc

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p1Constraint=findViewById(R.id.p1Constraint);

        SharedPreferences prefs=getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        boolean firstStart=prefs.getBoolean("firstLaunch", true);
        if(firstStart==true){
            p1Constraint.setVisibility(View.VISIBLE);
            p1Constraint.setClickable(true);
            editor.putBoolean("firstLaunch", false);
            editor.apply();

        }
        Intent receivedIntent=getIntent();
        if(receivedIntent.getType()=="close plan"){
            Toast.makeText(this, "The plan has been cancelled", Toast.LENGTH_SHORT).show();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ToDo: work on the settings design and functionality from the main menu after finishing the create a plan activity
        //Todo: Use SQL databases to save the information added, maybe add an alert or alarm option later

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), ChooseAPlan.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
