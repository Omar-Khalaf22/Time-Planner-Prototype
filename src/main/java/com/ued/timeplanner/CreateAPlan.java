package com.ued.timeplanner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.util.TimeUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.security.Key;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static android.view.View.AUTOFILL_TYPE_TEXT;

public class CreateAPlan extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    TextView dateTextView; TextView firstQTxtView1; TextView secondQTxtView1;
    RadioGroup radioGroup1 ;RadioButton activity1YesRadio; RadioButton activity1NoRadio;
    LinearLayout activity1Layout; LinearLayout activity2Layout; LinearLayout activity3Layout; LinearLayout activity4Layout; LinearLayout activity5Layout;LinearLayout activity6Layout; LinearLayout activity7Layout;
    LinearLayout activity8Layout; LinearLayout activity9Layout; LinearLayout activity10Layout; LinearLayout activity11Layout; LinearLayout activity12Layout;LinearLayout activity13Layout; LinearLayout activity14Layout;
    LinearLayout activity15Layout; LinearLayout activity16Layout; LinearLayout activity17Layout; LinearLayout activity18Layout;LinearLayout activity19Layout; LinearLayout activity20Layout;
    LinearLayout dayPlanLayout; LinearLayout mainLayout2; LinearLayout endingLayout; RelativeLayout planModeLayout;
    EditText activity1StartTime;
    EditText activity1EndTime; AutoCompleteTextView activityNameEditText1;
    EditText maxTimeInMinsActivity1;
    TextView maxTimeTxtView1;
    TextView effortTextView1; Button deleteYesBtn; Button saveBtnNo; Button saveBtnYes;
    TextView importanceTextView1;
    TimePickerDialog timePickerDialogStartTime1;
    TimePickerDialog timePickerDialogEndTime1;
    Calendar calendar; Button deleteBtn;
    Spinner effortScale1; Spinner importanceScale1; Spinner numOfTasks;
    int currentHour; int counter; int selectedTaskNum;
    int currentMinute;
    int yesCounter=0; int noCounter=0;
    String amPm;
    RadioGroup modePlanGroup; RadioButton wideLevelBtn; RadioButton moderateLevelBtn; Button wideLevelInfoBtn; Button moderateLevelInfoBtn; Button compactLevelInfoBtn;
    RadioButton compactLevelBtn; TextView planModeQuestion; RelativeLayout wideInfoBtnLayout;RelativeLayout moderateInfoBtnLayout;RelativeLayout compactedInfoBtnLayout;
    TextView wideInfoBtnTxt;TextView moderateInfoBtnTxt;TextView compactedInfoBtnTxt; Button closeLayoutInfoBtn; RelativeLayout currentInfoLayout; RelativeLayout previousInfoLayout;
    Button createThePlanBtn; Button cancelThePlanBtn; TextView blankTxtView;
    ArrayList<String>activityNames=new ArrayList<>(); ArrayList<String>activityNamesYes=new ArrayList<>(); ArrayList<String>startingTimes=new ArrayList<>();
    ArrayList<String>endingTimes=new ArrayList<>(); ArrayList<String>maxTimes=new ArrayList<>(); ArrayList<String>effortNumList=new ArrayList<>(); ArrayList<String>importanceNumList=new ArrayList<>();
    ArrayAdapter<String>activityNamesAdapter=null; ArrayAdapter<String>activityNamesYesAdapter=null; ArrayAdapter<String>startingTimeAdapter=null; ArrayAdapter<String>endingTimeAdapter=null;
    ArrayAdapter<String>effortNumAdapter=null; ArrayAdapter<String>importanceNumAdapter=null; ArrayAdapter<String>maxTimesAdapter=null;
    SharedPreferences activityNamesPreferences =null; SharedPreferences activityNamesYesPreferences =null; SharedPreferences startingTimePreferences =null; SharedPreferences endingTimePreferences =null;
    SharedPreferences maxTimesPreferences =null; SharedPreferences effortValsPreferences =null;SharedPreferences importanceValsPreferences =null;
    ArrayList<String>tempActivityList = null; ArrayList<String>tempActivityNoList = null; ArrayList<String>finalActivityNames=new ArrayList<>();
    TextView sleepQTxtView; RadioGroup sleepRadioGroup; RadioButton sleepYesRadio; RadioButton sleepNoRadio;
    EditText sleepingStartTime; EditText sleepingEndTime; LinearLayout sleepTimeLayout; Button selectDateBtn; Boolean validDateIsPicked=false; int totalT; int remainingT;
    Button nightOwlInfo; Button earlyBirdInfo; Button closeInfoPersonaBtn; RelativeLayout nightOwlInfoLayout; RelativeLayout earlyBirdInfoLayout;
    TextView nightOwlInfoTxt; TextView earlyBirdInfoTxt; RelativeLayout currentPersonaInfoLayout; LinearLayout personaTypeLayout; RadioGroup personaTypeGroup;
    RadioButton nightOwlRadio; RadioButton earlyBirdRadio; LinearLayout highestValLayout; ArrayList<LinearLayout>noActivities; ArrayList<LinearLayout>yesActivities;
    ArrayList<String>generalTopTimesWide; ArrayList<Integer>intervalRatingWideNight;


    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public int convertDpToPx(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public void deleteBtnYesFunc(final int currentLayoutYesID) {
        deleteYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainLayout2 = findViewById(R.id.mainLayout2);
                new AlertDialog.Builder(CreateAPlan.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirmation Message")
                        .setMessage("If you remove this task, you can't recover the information that you have entered in it unless you have save the task into your dashboard.")
                        .setPositiveButton("Remove the task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LinearLayout currentLayout = null;
                                if (currentLayoutYesID == 1) {
                                    currentLayout = activity1Layout;
                                } else if (currentLayoutYesID == 3) {
                                    currentLayout = activity2Layout;
                                } else if (currentLayoutYesID == 5) {
                                    currentLayout = activity3Layout;
                                } else if (currentLayoutYesID == 7) {
                                    currentLayout = activity4Layout;
                                } else if (currentLayoutYesID == 9) {
                                    currentLayout = activity5Layout;
                                } else if (currentLayoutYesID == 11) {
                                    currentLayout = activity6Layout;
                                } else if (currentLayoutYesID == 13) {
                                    currentLayout = activity7Layout;
                                } else if (currentLayoutYesID == 15) {
                                    currentLayout = activity8Layout;
                                } else if (currentLayoutYesID == 17) {
                                    currentLayout = activity9Layout;
                                } else if (currentLayoutYesID == 19) {
                                    currentLayout = activity10Layout;
                                } else if (currentLayoutYesID == 21) {
                                    currentLayout = activity11Layout;
                                } else if (currentLayoutYesID == 23) {
                                    currentLayout = activity12Layout;
                                } else if (currentLayoutYesID == 25) {
                                    currentLayout = activity13Layout;
                                } else if (currentLayoutYesID == 27) {
                                    currentLayout = activity14Layout;
                                } else if (currentLayoutYesID == 29) {
                                    currentLayout = activity15Layout;
                                } else if (currentLayoutYesID == 31) {
                                    currentLayout = activity16Layout;
                                } else if (currentLayoutYesID == 33) {
                                    currentLayout = activity17Layout;
                                } else if (currentLayoutYesID == 35) {
                                    currentLayout = activity18Layout;
                                } else if (currentLayoutYesID == 37) {
                                    currentLayout = activity19Layout;
                                } else if (currentLayoutYesID == 39) {
                                    currentLayout = activity20Layout;
                                }
                                mainLayout2.removeView(currentLayout);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    public ArrayList<String> extractActivityNamesList(){
        try {
            activityNames=(ArrayList<String>) ObjectSerializer.deserialize(activityNamesPreferences.getString("Activity names", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activityNames;
    }
    public ArrayList<String> extractActivityNamesYesList(){
        try {
            activityNamesYes=(ArrayList<String>) ObjectSerializer.deserialize(activityNamesYesPreferences.getString("Activity names yes", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activityNamesYes;
    }

    public ArrayList<String> extractStartingTimesList(){
        try {
            startingTimes=(ArrayList<String>) ObjectSerializer.deserialize(startingTimePreferences.getString("Starting times", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
            startingTimes=new ArrayList<>();
        }
        return startingTimes;
    }

    public ArrayList<String> extractEndingTimesList(){
        try {
            endingTimes=(ArrayList<String>) ObjectSerializer.deserialize(endingTimePreferences.getString("Ending times", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
            endingTimes=new ArrayList<>();
        }
        return endingTimes;
    }

    public ArrayList<String> extractEffortList(){
        try {
            effortNumList=(ArrayList<String>) ObjectSerializer.deserialize(effortValsPreferences.getString("Effort values", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
            effortNumList=new ArrayList<>();
        }
        return effortNumList;
    }

    public ArrayList<String> extractImportanceList(){
        try {
            importanceNumList=(ArrayList<String>) ObjectSerializer.deserialize(importanceValsPreferences.getString("Importance values", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
            importanceNumList=new ArrayList<>();
        }
        return importanceNumList;
    }

    public ArrayList<String> extractMaxTimesList(){
        try {
            maxTimes=(ArrayList<String>) ObjectSerializer.deserialize(maxTimesPreferences.getString("Max times", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
            maxTimes=new ArrayList<>();
        }
        return maxTimes;
    }

    public void saveBtnYesFunc(int currentYesBtnId){
        LinearLayout currentLayout = null;
        if (currentYesBtnId==1){currentLayout=activity1Layout;}else if (currentYesBtnId==3){currentLayout=activity2Layout;}else if (currentYesBtnId==5){currentLayout=activity3Layout;}
        else if (currentYesBtnId==7){currentLayout=activity4Layout;}else if (currentYesBtnId==9){currentLayout=activity5Layout;}else if (currentYesBtnId==11){currentLayout=activity6Layout;}
        else if (currentYesBtnId==13){currentLayout=activity7Layout;}else if (currentYesBtnId==15){currentLayout=activity8Layout;}else if (currentYesBtnId==17){currentLayout=activity9Layout;}
        else if (currentYesBtnId==19){currentLayout=activity10Layout;}else if (currentYesBtnId==21){currentLayout=activity11Layout;}else if (currentYesBtnId==23){currentLayout=activity12Layout;}
        else if (currentYesBtnId==25){currentLayout=activity13Layout;}else if (currentYesBtnId==27){currentLayout=activity14Layout;}else if (currentYesBtnId==29){currentLayout=activity15Layout;}
        else if (currentYesBtnId==31){currentLayout=activity16Layout;}else if (currentYesBtnId==33){currentLayout=activity17Layout;}else if (currentYesBtnId==35){currentLayout=activity18Layout;}
        else if (currentYesBtnId==37){currentLayout=activity19Layout;}else if (currentYesBtnId==39){currentLayout=activity20Layout;}

        LinearLayout finalCurrentLayout = currentLayout;
        EditText currentNameActivityEdit= (EditText) finalCurrentLayout.getChildAt(1);
        EditText currentStartTimeEdit=(EditText)finalCurrentLayout.getChildAt(4);
        EditText currentEndTimeEdit=(EditText)finalCurrentLayout.getChildAt(5);
        final String currentActivityNameYes= currentNameActivityEdit.getText().toString();
        final String currentStartTime= currentStartTimeEdit.getText().toString();
        final String currentEndTime= currentEndTimeEdit.getText().toString();
        startingTimePreferences=getApplicationContext().getSharedPreferences("Starting times", 0);
        endingTimePreferences=getApplicationContext().getSharedPreferences("Ending times", 0);

        extractActivityNamesYesList();
        extractStartingTimesList();
        extractEndingTimesList();

        activityNamesYesAdapter=new ArrayAdapter(CreateAPlan.this, android.R.layout.simple_list_item_1, activityNamesYes);
        startingTimeAdapter=new ArrayAdapter(CreateAPlan.this, android.R.layout.simple_list_item_1, startingTimes);
        endingTimeAdapter=new ArrayAdapter(CreateAPlan.this, android.R.layout.simple_list_item_1, endingTimes);

        new AlertDialog.Builder(CreateAPlan.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Do you want to save this activity's information?")
                .setMessage("By doing so, this information will be automatically filled in futures plans once you type the activity's name in. This will save you the time it takes to fill out an activity's information.")
                .setPositiveButton("Save the activity's information", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activityNamesYes.add(currentActivityNameYes);
                        startingTimes.add(currentStartTime); endingTimes.add(currentEndTime);
                        Log.i("Current activity name Y", activityNamesYes.toString());
                        Log.i("Starting times: ", startingTimes.toString());
                        Log.i("Ending times: ", endingTimes.toString());
                        Toast.makeText(CreateAPlan.this, "The activity's information has been saved", Toast.LENGTH_SHORT).show();
                        for (int x=1; x<=selectedTaskNum; x++){
                            switch (x){
                                case 1:
                                    AutoCompleteTextView tempChild=(AutoCompleteTextView) activity1Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 2:
                                    tempChild=(AutoCompleteTextView) activity2Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 3:
                                    tempChild=(AutoCompleteTextView) activity3Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 4:
                                    tempChild=(AutoCompleteTextView) activity4Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 5:
                                    tempChild=(AutoCompleteTextView) activity5Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 6:
                                    tempChild=(AutoCompleteTextView) activity6Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 7:
                                    tempChild=(AutoCompleteTextView) activity7Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 8:
                                    tempChild=(AutoCompleteTextView) activity8Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 9:
                                    tempChild=(AutoCompleteTextView) activity9Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 10:
                                    tempChild=(AutoCompleteTextView) activity10Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 11:
                                    tempChild=(AutoCompleteTextView) activity11Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 12:
                                    tempChild=(AutoCompleteTextView) activity12Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 13:
                                    tempChild=(AutoCompleteTextView) activity13Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 14:
                                    tempChild=(AutoCompleteTextView) activity14Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 15:
                                    tempChild=(AutoCompleteTextView) activity15Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 16:
                                    tempChild=(AutoCompleteTextView) activity16Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 17:
                                    tempChild=(AutoCompleteTextView) activity17Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 18:
                                    tempChild=(AutoCompleteTextView) activity18Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 19:
                                    tempChild=(AutoCompleteTextView) activity19Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                                case 20:
                                    tempChild=(AutoCompleteTextView) activity20Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesYesAdapter);
                                    break;
                            }
                        }
                        try {
                            activityNamesYesPreferences.edit().putString("Activity names yes",ObjectSerializer.serialize(activityNamesYes)).apply();
                            startingTimePreferences.edit().putString("Starting times",ObjectSerializer.serialize(startingTimes)).apply();
                            endingTimePreferences.edit().putString("Ending times",ObjectSerializer.serialize(endingTimes)).apply();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                })
                .setNegativeButton("Cancel",null )
                .show();
    }

    private Boolean validationSuccessYes(int currentYesBtnID, LinearLayout finalCurrentLayout){

        ArrayList<Integer>radioYesIDs=new ArrayList<Integer>();
        for (int x=1; x<=39; x=x+2){
            radioYesIDs.add(x);
        }

        Log.i("yes btn ID clicked", Integer.toString(currentYesBtnID));
        if(activityNameEditText1.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Please enter an activity name",Toast.LENGTH_SHORT).show();
            return false;
        }
        activity1StartTime= (EditText) finalCurrentLayout.getChildAt(4);
        activity1EndTime= (EditText) finalCurrentLayout.getChildAt(5);

        if(currentYesBtnID<=0){
            return null;
        }else{
            for (int x=0; x<radioYesIDs.size(); x++){
                if(currentYesBtnID==radioYesIDs.get(x)){
                    //IMPORTANT NOTE: WHEN WE GET THE TEXT SIZE, IT ALSO COUNTS THE SIZE OF THE HINT IF NOTHING HAS BEEN ENTERED WHICH MESSES UP EVERYTHING
                    //fIND A SOLUTION TO STOP THE HINT COUNT FROM AFFECTING THIS FUNCTION
                    if(activity1StartTime.getText().length()!=36){
                        Log.i("Start time size", String.valueOf(activity1StartTime.getText().length()));
                        Log.i("Start time size 2", Integer.toString((int) activity1StartTime.getTextSize()));
                        return false;
                    }
                    if(activity1EndTime.getText().length()!=34){
                        Log.i("ALERT", "END TIME ERROR");
                        return false;
                    }
                }
            }

        }
        return true;
    }

    private Boolean validationSuccessNo(int currentNoBtnID)  {

        ArrayList<Integer>radioNoIDs=new ArrayList<Integer>();
        for (int x=2; x<=40; x=x+2){
            radioNoIDs.add(x);
        }

        if(activityNameEditText1.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Please enter an activity name",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(currentNoBtnID<=0){
            return null;
        }else{
            for (int x=0; x<radioNoIDs.size(); x++){
                if(currentNoBtnID==radioNoIDs.get(x)){
                    if(maxTimeInMinsActivity1.getText().toString().equalsIgnoreCase("")){
                        return false;

                    }
                    if(effortScale1.getSelectedItemPosition()==0 || effortScale1.getSelectedItemPosition()==1 ){
                        Toast.makeText(getApplicationContext(), "Please select the level of effort required to complete " + activityNameEditText1.getText(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if(importanceScale1.getSelectedItemPosition()==0 || importanceScale1.getSelectedItemPosition()==1 ){
                        Toast.makeText(getApplicationContext(), "Please select the importance level of " + activityNameEditText1.getText(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }

        }


        /*if(spinner.getSelectedItemPosition()==0){
            Toast.mak0eText(getApplicationContext();"Please select Depatrment",0).show();
            return false;
        }*/
        return true;
    }




    public ArrayList<String> saveBtnNoFunc(int currentNoBtnId){
        LinearLayout currentLayout = null;
        if (currentNoBtnId == 2) { currentLayout = activity1Layout; } else if (currentNoBtnId == 4) { currentLayout = activity2Layout; } else if (currentNoBtnId == 6) { currentLayout = activity3Layout; }
        else if (currentNoBtnId == 8) { currentLayout = activity4Layout; } else if (currentNoBtnId == 10) { currentLayout = activity5Layout; } else if (currentNoBtnId == 12) {currentLayout = activity6Layout; }
        else if (currentNoBtnId == 14) { currentLayout = activity7Layout; } else if (currentNoBtnId == 16) { currentLayout = activity8Layout; } else if (currentNoBtnId == 18) { currentLayout = activity9Layout; }
        else if (currentNoBtnId == 20) { currentLayout = activity10Layout; } else if (currentNoBtnId == 22) { currentLayout = activity11Layout; } else if (currentNoBtnId == 24) { currentLayout = activity12Layout; }
        else if (currentNoBtnId == 26) { currentLayout = activity13Layout; } else if (currentNoBtnId == 28) { currentLayout = activity14Layout; } else if (currentNoBtnId == 30) { currentLayout = activity15Layout; }
        else if (currentNoBtnId == 32) { currentLayout = activity16Layout; } else if (currentNoBtnId == 34) { currentLayout = activity17Layout; } else if (currentNoBtnId == 36) { currentLayout = activity18Layout; }
        else if (currentNoBtnId == 38) { currentLayout = activity19Layout; } else if (currentNoBtnId == 40) { currentLayout = activity20Layout;}

        LinearLayout finalCurrentLayout = currentLayout;
        EditText currentNameActivityEdit= (EditText) finalCurrentLayout.getChildAt(1);
        EditText currentMaxTime=(EditText)finalCurrentLayout.getChildAt(5);
        Spinner currentEffortScale=(Spinner)finalCurrentLayout.getChildAt(7);
        Spinner currentImportanceScale=(Spinner)finalCurrentLayout.getChildAt(9);
        final String currentActivityName= currentNameActivityEdit.getText().toString();
        final String currentMaxTimeStr= currentMaxTime.getText().toString();
        final String currentEffortScaleNum= currentEffortScale.getSelectedItem().toString();
        Log.i("Extracted effort value", currentEffortScaleNum);
        final String currentImportanceScaleNum= currentImportanceScale.getSelectedItem().toString();

        effortValsPreferences=getApplicationContext().getSharedPreferences("Effort values", 0);
        importanceValsPreferences=getApplicationContext().getSharedPreferences("Importance values", 0);
        maxTimesPreferences=getApplicationContext().getSharedPreferences("Max times", 0);

        extractActivityNamesList();
        extractEffortList(); extractImportanceList(); extractMaxTimesList();

        activityNamesAdapter=new ArrayAdapter(CreateAPlan.this, android.R.layout.simple_list_item_1, activityNames);
        importanceNumAdapter=new ArrayAdapter(CreateAPlan.this, android.R.layout.simple_list_item_1, importanceNumList);
        effortNumAdapter=new ArrayAdapter(CreateAPlan.this, android.R.layout.simple_list_item_1, effortNumList);
        maxTimesAdapter=new ArrayAdapter(CreateAPlan.this, android.R.layout.simple_list_item_1, maxTimes);

        new AlertDialog.Builder(CreateAPlan.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Do you want to save this activity's information?")
                .setMessage("By doing so, this information will be automatically filled in futures plans once you type the activity's name in. This will save you the time it takes to fill out an activity's information.")
                .setPositiveButton("Save the activity's information", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activityNames.add(currentActivityName);
                        maxTimes.add(currentMaxTimeStr); effortNumList.add(currentEffortScaleNum); importanceNumList.add(currentImportanceScaleNum);
                        Log.i("Current activity name", activityNames.toString());
                        Log.i("Effort values serious", effortNumList.toString());
                        Log.i("importance vals serious", importanceNumList.toString());
                        Toast.makeText(CreateAPlan.this, "The activity's information has been saved", Toast.LENGTH_SHORT).show();
                        for (int x=1; x<=selectedTaskNum; x++){
                            switch (x){
                                case 1:
                                    AutoCompleteTextView tempChild=(AutoCompleteTextView) activity1Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 2:
                                    tempChild=(AutoCompleteTextView) activity2Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 3:
                                    tempChild=(AutoCompleteTextView) activity3Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 4:
                                    tempChild=(AutoCompleteTextView) activity4Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 5:
                                    tempChild=(AutoCompleteTextView) activity5Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 6:
                                    tempChild=(AutoCompleteTextView) activity6Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 7:
                                    tempChild=(AutoCompleteTextView) activity7Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 8:
                                    tempChild=(AutoCompleteTextView) activity8Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 9:
                                    tempChild=(AutoCompleteTextView) activity9Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 10:
                                    tempChild=(AutoCompleteTextView) activity10Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 11:
                                    tempChild=(AutoCompleteTextView) activity11Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 12:
                                    tempChild=(AutoCompleteTextView) activity12Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 13:
                                    tempChild=(AutoCompleteTextView) activity13Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 14:
                                    tempChild=(AutoCompleteTextView) activity14Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 15:
                                    tempChild=(AutoCompleteTextView) activity15Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 16:
                                    tempChild=(AutoCompleteTextView) activity16Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 17:
                                    tempChild=(AutoCompleteTextView) activity17Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 18:
                                    tempChild=(AutoCompleteTextView) activity18Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 19:
                                    tempChild=(AutoCompleteTextView) activity19Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                                case 20:
                                    tempChild=(AutoCompleteTextView) activity20Layout.getChildAt(1);
                                    tempChild.setAdapter(activityNamesAdapter);
                                    break;
                            }
                        }
                        try {
                            activityNamesPreferences.edit().putString("Activity names",ObjectSerializer.serialize(activityNames)).apply();
                            maxTimesPreferences.edit().putString("Max times",ObjectSerializer.serialize(maxTimes)).apply();
                            effortValsPreferences.edit().putString("Effort values",ObjectSerializer.serialize(effortNumList)).apply();
                            importanceValsPreferences.edit().putString("Importance values",ObjectSerializer.serialize(importanceNumList)).apply();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(CreateAPlan.this, "Saving prefrences failed", Toast.LENGTH_LONG).show();
                        }


                    }
                })
                .setNegativeButton("Cancel",null )
                .show();
        return activityNames;
    }

    public void deleteBtnNoFunc(final int currentLayoutID){
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainLayout2=findViewById(R.id.mainLayout2);
                new AlertDialog.Builder(CreateAPlan.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirmation Message")
                        .setMessage("If you remove this task, you can't recover the information that you have entered in it unless you have save the task into your dashboard.")
                        .setPositiveButton("Remove the task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LinearLayout currentLayout = null;
                                if (currentLayoutID == 2) { currentLayout = activity1Layout; } else if (currentLayoutID == 4) { currentLayout = activity2Layout; } else if (currentLayoutID == 5) { currentLayout = activity3Layout;}
                                else if (currentLayoutID == 8) { currentLayout = activity4Layout; } else if (currentLayoutID == 10) { currentLayout = activity5Layout; } else if (currentLayoutID == 12) { currentLayout = activity6Layout;}
                                else if (currentLayoutID == 14) { currentLayout = activity7Layout; } else if (currentLayoutID == 16) { currentLayout = activity8Layout; } else if (currentLayoutID == 18) { currentLayout = activity9Layout;}
                                else if (currentLayoutID == 20) { currentLayout = activity10Layout; } else if (currentLayoutID == 22) { currentLayout = activity11Layout; } else if (currentLayoutID == 24) { currentLayout = activity12Layout;}
                                else if (currentLayoutID == 26) { currentLayout = activity13Layout; } else if (currentLayoutID == 28) { currentLayout = activity14Layout; } else if (currentLayoutID == 30) { currentLayout = activity15Layout;}
                                else if (currentLayoutID == 32) { currentLayout = activity16Layout; } else if (currentLayoutID == 34) { currentLayout = activity17Layout; } else if (currentLayoutID == 36) { currentLayout = activity18Layout;}
                                else if (currentLayoutID == 38) { currentLayout = activity19Layout; } else if (currentLayoutID == 40) { currentLayout = activity20Layout; }
                                mainLayout2.removeView(currentLayout);
                            }
                        })
                        .setNegativeButton("Cancel",null )
                        .show();
            }
        });
    }

    public ArrayList<String> yesRadioFunc(final int currentYesBtnId){
        tempActivityList = activityNames;

        activity1YesRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                activity1StartTime = new EditText(CreateAPlan.this);
                activity1EndTime = new EditText(CreateAPlan.this);
                deleteYesBtn = new Button(CreateAPlan.this);
                saveBtnYes=new Button(CreateAPlan.this);


                int previousLayout=0;
                Log.i("previousLayoutValue: ", Integer.toString(previousLayout));
                //activity1Layout=findViewById(R.id.activity1Layout);
                activity1StartTime.setHint("Long tap to select the starting time...");
                activity1StartTime.setHeight(convertDpToPx(CreateAPlan.this, (float) 35));
                activity1StartTime.setWidth(convertDpToPx(CreateAPlan.this, (float) 350));
                activity1StartTime.setTextSize(14);
                activity1StartTime.setEms(10);
                //ADD THE PADDING OR MARGING START IF NECESSARY
                activity1EndTime.setHint("Long tap to select the ending time...");
                LinearLayout.LayoutParams activity1EndTimeParams=new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float) 420),
                        convertDpToPx(CreateAPlan.this, (float) 35));
                activity1EndTimeParams.topMargin=convertDpToPx(CreateAPlan.this, (float)5); activity1EndTime.setLayoutParams(activity1EndTimeParams);
                activity1EndTime.setTextSize(14);
                activity1EndTime.setEms(10);
                //DELTE BUTTON
                LinearLayout.LayoutParams deleteBtnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, convertDpToPx(CreateAPlan.this, (float) 75));
                deleteBtnParams.leftMargin = convertDpToPx(CreateAPlan.this, (float) 115);
                deleteBtnParams.topMargin = convertDpToPx(CreateAPlan.this, (float) 7);
                deleteYesBtn.setLayoutParams(deleteBtnParams);
                deleteYesBtn.setBackgroundColor(Color.parseColor("#000000"));
                deleteYesBtn.setBackground(getDrawable(R.drawable.ic_delete_img));
                deleteBtnYesFunc(currentYesBtnId);
                //SAVE BUTTON
                LinearLayout.LayoutParams saveBtnParanms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, convertDpToPx(CreateAPlan.this, (float) 75));
                saveBtnParanms.leftMargin = convertDpToPx(CreateAPlan.this, (float) 220);
                saveBtnParanms.topMargin = convertDpToPx(CreateAPlan.this, (float) -75);
                saveBtnYes.setLayoutParams(saveBtnParanms);
                saveBtnYes.setBackground(getDrawable(R.drawable.ic_save_black_24dp));


                LinearLayout currentLayout = null;
                if (currentYesBtnId==1){currentLayout=activity1Layout;}else if (currentYesBtnId==3){currentLayout=activity2Layout;}else if (currentYesBtnId==5){currentLayout=activity3Layout;}
                else if (currentYesBtnId==7){currentLayout=activity4Layout;}else if (currentYesBtnId==9){currentLayout=activity5Layout;}else if (currentYesBtnId==11){currentLayout=activity6Layout;}
                else if (currentYesBtnId==13){currentLayout=activity7Layout;}else if (currentYesBtnId==15){currentLayout=activity8Layout;}else if (currentYesBtnId==17){currentLayout=activity9Layout;}
                else if (currentYesBtnId==19){currentLayout=activity10Layout;}else if (currentYesBtnId==21){currentLayout=activity11Layout;}else if (currentYesBtnId==23){currentLayout=activity12Layout;}
                else if (currentYesBtnId==25){currentLayout=activity13Layout;}else if (currentYesBtnId==27){currentLayout=activity14Layout;}else if (currentYesBtnId==29){currentLayout=activity15Layout;}
                else if (currentYesBtnId==31){currentLayout=activity16Layout;}else if (currentYesBtnId==33){currentLayout=activity17Layout;}else if (currentYesBtnId==35){currentLayout=activity18Layout;}
                else if (currentYesBtnId==37){currentLayout=activity19Layout;}else if (currentYesBtnId==39){currentLayout=activity20Layout;}
                Log.i("Current selected ID: ", Integer.toString(currentYesBtnId));

                final LinearLayout finalCurrentLayout = currentLayout;
                saveBtnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validationSuccessYes(currentYesBtnId, finalCurrentLayout)) {
                            Log.i("Start time size", String.valueOf(activity1StartTime.getText()));
                            saveBtnYesFunc(currentYesBtnId);
                        } else {
                            new AlertDialog.Builder(CreateAPlan.this)
                                    .setTitle("Saving Error")
                                    .setMessage("Please make sure that all of the fields are completed correctly in order to save the activity's information")
                                    .setNegativeButton("close", null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }

                       /*activityNames=*/
                    }
                });

                if(previousLayout==currentYesBtnId) {
                    yesCounter = yesCounter + 1;
                }else{
                    yesCounter=0; yesCounter=yesCounter +1 ;
                }
                previousLayout=currentYesBtnId;
                //---------------------------
                if(yesCounter==1 && noCounter==0) {
                    currentLayout.addView(activity1StartTime); currentLayout.addView(activity1EndTime); currentLayout.addView(deleteYesBtn);
                    currentLayout.addView(saveBtnYes);
                }else if(yesCounter-noCounter>=2) {
                    if ((yesCounter - noCounter) % 2 == 0) {
                        currentLayout.removeView(activity1StartTime);
                        currentLayout.removeView(activity1EndTime);
                        currentLayout.removeView(deleteYesBtn);currentLayout.removeView(saveBtnYes);
                    }else if((yesCounter-noCounter)%2 > 0){
                        currentLayout.addView(activity1StartTime); currentLayout.addView(activity1EndTime); currentLayout.addView(deleteYesBtn);
                        currentLayout.addView(saveBtnYes);
                    }
                }
                else {
                    currentLayout.removeView(maxTimeTxtView1); currentLayout.removeView(maxTimeInMinsActivity1); currentLayout.removeView(effortTextView1);
                    currentLayout.removeView(effortScale1); currentLayout.removeView(importanceTextView1); currentLayout.removeView(importanceScale1);
                    currentLayout.removeView(deleteBtn);currentLayout.removeView(saveBtnNo);
                    currentLayout.addView(activity1StartTime); currentLayout.addView(activity1EndTime); currentLayout.addView(deleteYesBtn);
                    currentLayout.addView(saveBtnYes);
                }

                //-------------------------------
                //Todo: sometimes the following else option is activated since i haven't set everything for some of the previous task and so the indexes are messed up. This can be solved by making every field required.
                activityNamesYesPreferences=CreateAPlan.this.getSharedPreferences("com.ued.timeplanner", MODE_PRIVATE);
                extractActivityNamesYesList();

                activityNameEditText1=(AutoCompleteTextView) currentLayout.getChildAt(1);
                tempActivityList=activityNamesYes;
                startingTimePreferences=getApplicationContext().getSharedPreferences("Starting times", 0);
                endingTimePreferences=getApplicationContext().getSharedPreferences("Ending times", 0);
                extractStartingTimesList(); extractEndingTimesList();
                String currentActivityName=activityNameEditText1.getText().toString();
                int position=-1; //if that item isn't in the list, it will return -1
                position=tempActivityList.indexOf(currentActivityName);
                Log.i("Current activity name", currentActivityName);
                Log.i("FINAL TEST:", Integer.toString(position));
                Log.i("FINAL TEST 2:", tempActivityList.toString());

                if (position!=-1){
                    String currentStartingTime=startingTimes.get(position);
                    Log.i("Starting time:", currentStartingTime);
                    activity1StartTime.setText(currentStartingTime); //activity1StartTime.setBackground(getDrawable(R.color.colorAutofill));
                    String currentEndingTime=endingTimes.get(position);
                    activity1EndTime.setText(currentEndingTime); //activity1EndTime.setBackground(getDrawable(R.color.colorAutofill));
                }//Todo: get rid of that else branch by making the startingTimeEditText and the endingTimeEditText required fields when clicking on the save button
                else {
                    Toast.makeText(CreateAPlan.this, "You haven't entered any starting and ending times when you saved that activity's information", Toast.LENGTH_LONG).show();
                }
                activity1StartTime.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        calendar=Calendar.getInstance();
                        currentHour=calendar.get(Calendar.HOUR_OF_DAY);
                        currentMinute=calendar.get(Calendar.MINUTE);
                        timePickerDialogStartTime1=new TimePickerDialog(CreateAPlan.this, new OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                                if(hourOfDay>=12){
                                    amPm="PM";
                                }else{
                                    amPm="AM";
                                }
                                activity1StartTime.setText("Time the activity starts at: " +String.format("%02d:%02d", hourOfDay, minutes) +amPm);
                            }
                        }, currentHour, currentMinute, false);
                        timePickerDialogStartTime1.show();
                        return Boolean.parseBoolean(null);
                    }
                });

                activity1EndTime.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        calendar=Calendar.getInstance();
                        currentHour=calendar.get(Calendar.HOUR_OF_DAY);
                        currentMinute=calendar.get(Calendar.MINUTE);
                        timePickerDialogEndTime1=new TimePickerDialog(CreateAPlan.this, new OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                                if(hourOfDay>=12){
                                    amPm="PM";
                                }else{
                                    amPm="AM";
                                }
                                activity1EndTime.setText("Time the activity ends at: " +String.format("%02d:%02d", hourOfDay, minutes) +amPm);
                            }
                        }, currentHour, currentMinute, false);
                        timePickerDialogEndTime1.show();
                        return Boolean.parseBoolean(null);
                    }
                });

            }
        });
        Log.i("FINAL NAMES:", tempActivityList.toString());
        return tempActivityList;
        //Todo: DON'T FORGET TO SOLVE THE RADIO BUTTONS PROBLEM, TO REFRESH YOUR MEMORY LOOK AT THE COMMENTED OUT CODES. VERY IMPORTANT
    }
    public static void selectSpinnerItemByValue(Spinner spnr, long value) {
        SimpleCursorAdapter adapter = (SimpleCursorAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItemId(position) == value) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    public ArrayList<String> NoRadioFunc(final int currentNoBtnId) {
        tempActivityNoList=activityNames;

        activity1NoRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxTimeTxtView1 = new TextView(CreateAPlan.this);
                maxTimeInMinsActivity1 = new EditText(CreateAPlan.this);
                effortTextView1 = new TextView(CreateAPlan.this);
                effortScale1 = new Spinner(CreateAPlan.this);
                importanceTextView1 = new TextView(CreateAPlan.this);
                importanceScale1 = new Spinner(CreateAPlan.this);
                deleteBtn = new Button(CreateAPlan.this);
                saveBtnNo=new Button(CreateAPlan.this);

                int previousLayout=0;
                Log.i("previousLayoutValue: ", Integer.toString(previousLayout));
                //activity1Layout=findViewById(R.id.activity1Layout);
                maxTimeTxtView1.setText("What is the maximum time that this task would require?");
                maxTimeTxtView1.setTextSize(16);
                LinearLayout.LayoutParams maxTimeTextViewParams = new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float) 350), ViewGroup.LayoutParams.WRAP_CONTENT);
                maxTimeTextViewParams.leftMargin = convertDpToPx(CreateAPlan.this, (float) 30);
                maxTimeTextViewParams.rightMargin = convertDpToPx(CreateAPlan.this, (float) 30);
                maxTimeTextViewParams.topMargin = convertDpToPx(CreateAPlan.this, (float) 4);
                maxTimeTxtView1.setLayoutParams(maxTimeTextViewParams);
                maxTimeTxtView1.setTextColor(Color.parseColor("#000000"));
                //editText answer to first question maxTimeInMinsActivity1
                LinearLayout.LayoutParams maxTimeEditTextparams = new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float) 270), ViewGroup.LayoutParams.WRAP_CONTENT);
                maxTimeEditTextparams.leftMargin = convertDpToPx(CreateAPlan.this, (float) 70);
                maxTimeInMinsActivity1.setLayoutParams(maxTimeEditTextparams);
                maxTimeInMinsActivity1.setHint("Type in the time in minutes...");
                maxTimeInMinsActivity1.setTextColor(Color.parseColor("#000000"));
                maxTimeInMinsActivity1.setInputType(InputType.TYPE_CLASS_NUMBER);
                //TxtView how much effort question
                effortTextView1.setText("How much effort would this activity require on a scale from 1( not much effort) to 5 (a lot of effort)?");
                effortTextView1.setTextColor(Color.parseColor("#000000"));
                effortTextView1.setTextSize(16);
                LinearLayout.LayoutParams effortTextViewQParams = new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float) 350), ViewGroup.LayoutParams.WRAP_CONTENT);
                effortTextViewQParams.leftMargin = convertDpToPx(CreateAPlan.this, (float) 30);
                effortTextViewQParams.rightMargin = convertDpToPx(CreateAPlan.this, (float) 30);
                effortTextViewQParams.topMargin = convertDpToPx(CreateAPlan.this, (float) 4);
                effortTextView1.setLayoutParams(effortTextViewQParams);
                //DROP DOWN LIST FOR THE EFFORT SCALE
                LinearLayout.LayoutParams effortScaleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                effortScaleParams.topMargin = convertDpToPx(CreateAPlan.this, (float) 6);
                effortScaleParams.leftMargin = convertDpToPx(CreateAPlan.this, 175);
                effortScale1.setLayoutParams(effortScaleParams);
                ArrayAdapter<CharSequence> effortAdapter1 = ArrayAdapter.createFromResource(CreateAPlan.this, R.array.effortScaleNums, android.R.layout.simple_spinner_item);
                effortAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                effortScale1.setAdapter(effortAdapter1);
                effortScale1.setOnItemSelectedListener(CreateAPlan.this);
                //HOW IMPORTANT IS THE ACTIVITY TO THE USER QUESTION TEXTVIEWffortScale1.setAdapter(effortAdapter1); effortScale1.setOnItemSelectedListener(CreateAPlan.this);
                importanceTextView1.setText("How important is this activity to you on a scale from 1(least important) to 5 (most important)?");
                importanceTextView1.setTextColor(Color.parseColor("#000000"));
                importanceTextView1.setTextSize(16);
                LinearLayout.LayoutParams importanceTextViewQParams = new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float) 350), ViewGroup.LayoutParams.WRAP_CONTENT);
                importanceTextViewQParams.leftMargin = convertDpToPx(CreateAPlan.this, (float) 30);
                importanceTextViewQParams.rightMargin = convertDpToPx(CreateAPlan.this, (float) 30);
                importanceTextViewQParams.topMargin = convertDpToPx(CreateAPlan.this, (float) 7);
                importanceTextView1.setLayoutParams(importanceTextViewQParams);
                //DROP DOWN LIST FOR THE IMPORTANCE SCALE
                LinearLayout.LayoutParams importanceScaleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                importanceScaleParams.topMargin = convertDpToPx(CreateAPlan.this, (float) 8);
                importanceScaleParams.leftMargin = convertDpToPx(CreateAPlan.this, 175);
                importanceScale1.setLayoutParams(importanceScaleParams);
                ArrayAdapter<CharSequence> importanceAdapter1 = ArrayAdapter.createFromResource(CreateAPlan.this, R.array.effortScaleNums, android.R.layout.simple_spinner_item);
                importanceAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                importanceScale1.setAdapter(importanceAdapter1);
                importanceScale1.setOnItemSelectedListener(CreateAPlan.this);
                //DELETE BUTTON
                LinearLayout.LayoutParams deleteBtnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, convertDpToPx(CreateAPlan.this, (float) 75));
                deleteBtnParams.leftMargin = convertDpToPx(CreateAPlan.this, (float) 115);
                deleteBtnParams.topMargin = convertDpToPx(CreateAPlan.this, (float) 20);
                deleteBtn.setLayoutParams(deleteBtnParams);
                deleteBtn.setBackgroundColor(Color.parseColor("#000000"));
                deleteBtn.setBackground(getDrawable(R.drawable.ic_delete_img));
                deleteBtnNoFunc(currentNoBtnId);
                //save button
                LinearLayout.LayoutParams saveBtnParanms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, convertDpToPx(CreateAPlan.this, (float) 75));
                saveBtnParanms.leftMargin = convertDpToPx(CreateAPlan.this, (float) 220);
                saveBtnParanms.topMargin = convertDpToPx(CreateAPlan.this, (float) -75);
                saveBtnNo.setLayoutParams(saveBtnParanms);
                saveBtnNo.setBackground(getDrawable(R.drawable.ic_save_black_24dp));

                //-----------------------------------
                LinearLayout currentLayout = null;
                if (currentNoBtnId == 2) { currentLayout = activity1Layout; } else if (currentNoBtnId == 4) { currentLayout = activity2Layout; } else if (currentNoBtnId == 6) { currentLayout = activity3Layout; }
                else if (currentNoBtnId == 8) { currentLayout = activity4Layout; } else if (currentNoBtnId == 10) { currentLayout = activity5Layout; } else if (currentNoBtnId == 12) {currentLayout = activity6Layout; }
                else if (currentNoBtnId == 14) { currentLayout = activity7Layout; } else if (currentNoBtnId == 16) { currentLayout = activity8Layout; } else if (currentNoBtnId == 18) { currentLayout = activity9Layout; }
                else if (currentNoBtnId == 20) { currentLayout = activity10Layout; } else if (currentNoBtnId == 22) { currentLayout = activity11Layout; } else if (currentNoBtnId == 24) { currentLayout = activity12Layout; }
                else if (currentNoBtnId == 26) { currentLayout = activity13Layout; } else if (currentNoBtnId == 28) { currentLayout = activity14Layout; } else if (currentNoBtnId == 30) { currentLayout = activity15Layout; }
                else if (currentNoBtnId == 32) { currentLayout = activity16Layout; } else if (currentNoBtnId == 34) { currentLayout = activity17Layout; } else if (currentNoBtnId == 36) { currentLayout = activity18Layout; }
                else if (currentNoBtnId == 38) { currentLayout = activity19Layout; } else if (currentNoBtnId == 40) { currentLayout = activity20Layout;}

                if(previousLayout==currentNoBtnId) {
                    noCounter = noCounter + 1;
                }else{
                    noCounter=0; noCounter=noCounter +1 ;
                }
                previousLayout=currentNoBtnId;

                saveBtnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validationSuccessNo(currentNoBtnId)) {
                            saveBtnNoFunc(currentNoBtnId);
                        } else {
                            new AlertDialog.Builder(CreateAPlan.this)
                                    .setTitle("Saving Error")
                                    .setMessage("Please make sure that all of the fields are completed correctly in order to save the activity's information")
                                    .setNegativeButton("close", null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }

                        /*activityNames=*/
                    }
                });

                Log.i("CurrentnoCounter value:", Integer.toString(noCounter));
                Log.i("CurrentYesCounterValue:", Integer.toString(yesCounter));
               if (noCounter==1 && yesCounter == 0) {
                    assert currentLayout != null;
                    currentLayout.addView(maxTimeTxtView1);
                    currentLayout.addView(maxTimeInMinsActivity1);
                    currentLayout.addView(effortTextView1);
                    currentLayout.addView(effortScale1);
                    currentLayout.addView(importanceTextView1);
                    currentLayout.addView(importanceScale1);
                    currentLayout.addView(deleteBtn);  currentLayout.addView(saveBtnNo);
               }else if(noCounter-yesCounter>=2) {
                   if ((noCounter - yesCounter) % 2 == 0) {
                       currentLayout.removeView(maxTimeTxtView1); currentLayout.removeView(maxTimeInMinsActivity1);currentLayout.removeView(effortTextView1);
                       currentLayout.removeView(effortScale1); currentLayout.removeView(importanceTextView1); currentLayout.removeView(importanceScale1);
                       currentLayout.removeView(deleteBtn);currentLayout.removeView(saveBtnNo);
                   }else if((noCounter-yesCounter)%2 > 0){
                       currentLayout.addView(maxTimeTxtView1); currentLayout.addView(maxTimeInMinsActivity1); currentLayout.addView(effortTextView1);
                       currentLayout.addView(effortScale1); currentLayout.addView(importanceTextView1); currentLayout.addView(importanceScale1);
                       currentLayout.addView(deleteBtn);currentLayout.addView(saveBtnNo);
                   }

               }/*else if(noCounter>=1 && yesCounter<noCounter-1){
                       currentLayout.removeView(maxTimeTxtView1); currentLayout.removeView(maxTimeInMinsActivity1); currentLayout.removeView(effortTextView1);
                       currentLayout.removeView(effortScale1); currentLayout.removeView(importanceTextView1); currentLayout.removeView(importanceScale1);
                       currentLayout.removeView(deleteBtn);
               }*/else {
                    currentLayout.removeView(activity1StartTime);
                    currentLayout.removeView(activity1EndTime); currentLayout.removeView(deleteYesBtn); currentLayout.removeView(saveBtnYes);
                    currentLayout.addView(maxTimeTxtView1);
                    currentLayout.addView(maxTimeInMinsActivity1);
                    currentLayout.addView(effortTextView1);
                    currentLayout.addView(effortScale1);
                    currentLayout.addView(importanceTextView1);
                    currentLayout.addView(importanceScale1);
                    currentLayout.addView(deleteBtn);currentLayout.addView(saveBtnNo);
                }
                //-------------------------------------

                activityNamesPreferences=CreateAPlan.this.getSharedPreferences("com.ued.timeplanner", MODE_PRIVATE);
                extractActivityNamesList();

                activityNameEditText1=(AutoCompleteTextView) currentLayout.getChildAt(1);
                tempActivityNoList=activityNames;
                effortValsPreferences=getApplicationContext().getSharedPreferences("Effort values", 0);
                importanceValsPreferences=getApplicationContext().getSharedPreferences("Importance values", 0);
                maxTimesPreferences=getApplicationContext().getSharedPreferences("Max times", 0);
                extractEffortList(); extractImportanceList(); extractMaxTimesList();

                String currentActivityName=activityNameEditText1.getText().toString();
                int position; //if that item isn't in the list, it will return -1
                position=activityNames.indexOf(currentActivityName);
                if (position!=-1){
                    String currentMaxTime=maxTimes.get(position);
                    maxTimeInMinsActivity1.setText(currentMaxTime); //activity1StartTime.setBackground(getDrawable(R.color.colorAutofill));
                    int currentEffortVal=Integer.parseInt(effortNumList.get(position))+1; //Todo: There is a potential error in here to check for later. There might be other same values of that position int that we are searching for as a string in the arrayList which may result in an error
                    effortScale1.setSelection(currentEffortVal); //activity1EndTime.setBackground(getDrawable(R.color.colorAutofill));
                    int currentImportanceVal=Integer.parseInt(importanceNumList.get(position))+1;
                    importanceScale1.setSelection(currentImportanceVal); //activity1EndTime.setBackground(getDrawable(R.color.colorAutofill));
                }//Todo: get rid of that else branch by making the startingTimeEditText and the endingTimeEditText required fields when clicking on the save button
                else {
                    Toast.makeText(CreateAPlan.this, "You haven't entered any of the following when you saved the activity's information: max time needs, effort required, or importance level of the activity.", Toast.LENGTH_LONG).show();
                }
            }
        });
        return tempActivityNoList;
    }

        //radioGroupActivity1=findViewById(R.id.radioGroupActivity1);
        //int radioId=radioGroupActivity1.getCheckedRadioButtonId();
        //radioButtonActivity1=findViewById (radioId);
        //----------------------------------------------------
        /*
        radioGroupActivity1 = findViewById(R.id.radioGroupActivity1);
        activity1YesRadio = findViewById(R.id.activity1YesRadio);
        activity1NoRadio = findViewById(R.id.activity1NoRadio);
        radioGroupActivity1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activity1YesRadio:

                        EditText testEditText=new EditText(this);
                        break;
                    case R.id.activity1NoRadio:
                        Toast.makeText(getApplicationContext(), "You clicked no", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

           --------------------------------------
        if(radioButtonActivity1.getText()=="Yes"){
            EditText startFixedTimeActivity1=new EditText(CreateAPlan.this);
            linearLayout=findViewById(R.id.dayPlanLayout);
            startFixedTimeActivity1.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            startFixedTimeActivity1.setHint("Enter the time that the activity will start at");
        }
        });
         */
        //Todo: solve the radio buttons problem where if 2 are clicked the first one's UI should be reoved and add the new one's UI. Possible solutions are try and catch or setting a counter for each button, if the number of times it is clicked on is odd then we will add the UI; if it is even then we will remove the UI. ALll of this will happen while the try and catch methods that I have done are still

    public void sleepNoFunc(){
        sleepingStartTime=new EditText(CreateAPlan.this); sleepingEndTime=new EditText(CreateAPlan.this);

        sleepingStartTime.setHint("Long tap to select the time that you usually start sleeping at...");
        sleepingStartTime.setHeight(convertDpToPx(CreateAPlan.this, (float) 35));
        sleepingStartTime.setWidth(convertDpToPx(CreateAPlan.this, (float) 350));
        sleepingStartTime.setTextSize(14);
        sleepingStartTime.setEms(10);
        //ADD THE PADDING OR MARGING START IF NECESSARY
        sleepingEndTime.setHint("Long tap to select the time that you usually wake up at...");
        LinearLayout.LayoutParams sleepingEndTimeParams=new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float) 420),
                convertDpToPx(CreateAPlan.this, (float) 35));
        sleepingEndTimeParams.topMargin=convertDpToPx(CreateAPlan.this, (float)5); sleepingEndTime.setLayoutParams(sleepingEndTimeParams);
        sleepingEndTime.setTextSize(14);
        sleepingEndTime.setEms(10);

        sleepingStartTime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                calendar=Calendar.getInstance();
                currentHour=calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute=calendar.get(Calendar.MINUTE);
                timePickerDialogStartTime1=new TimePickerDialog(CreateAPlan.this, new OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                        if(hourOfDay>=12){
                            amPm="PM";
                        }else{
                            amPm="AM";
                        }
                        sleepingStartTime.setText("Time the activity starts at: " +String.format("%02d:%02d", hourOfDay, minutes) +amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialogStartTime1.show();
                return Boolean.parseBoolean(null);
            }
        });

        sleepingEndTime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                calendar=Calendar.getInstance();
                currentHour=calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute=calendar.get(Calendar.MINUTE);
                timePickerDialogEndTime1=new TimePickerDialog(CreateAPlan.this, new OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                        if(hourOfDay>=12){
                            amPm="PM";
                        }else{
                            amPm="AM";
                        }
                        sleepingEndTime.setText("Time the activity ends at: " +String.format("%02d:%02d", hourOfDay, minutes) +amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialogEndTime1.show();
                return Boolean.parseBoolean(null);
            }
        });

        sleepTimeLayout.addView(sleepingStartTime);sleepTimeLayout.addView(sleepingEndTime);

    }
    //Todo: USE CLEARCHECK FUNCTION FOR A RADIOGROUP LATER TO UNCHECK A RADIO BUTTON WHEN IT IS CLICKED ON EVEN TIMES CONSECUTIVELY

    public boolean activityLayoutValidation(LinearLayout currentLayout){
        radioGroup1=(RadioGroup)currentLayout.getChildAt(3);
        if (radioGroup1.getCheckedRadioButtonId()==-1){
            return false;
        }else{
            if((radioGroup1.getCheckedRadioButtonId())%2 == 0){
                validationSuccessNo(radioGroup1.getCheckedRadioButtonId());
            }else {
                validationSuccessYes(radioGroup1.getCheckedRadioButtonId(), currentLayout);
            }
        }
        return true;
    }

    public int activityLayoutTValidation(LinearLayout currentLayout,int totalT2){
        radioGroup1=(RadioGroup)currentLayout.getChildAt(3);
        totalT=totalT2;

        if((radioGroup1.getCheckedRadioButtonId())%2 == 0){
            EditText currentTInput=(EditText)currentLayout.getChildAt(5);
            int T= Integer.parseInt(currentTInput.getText().toString());
            totalT= totalT+T;
        }else {
            EditText currentStartTime=(EditText)currentLayout.getChildAt(4);
            EditText currentEndTime=(EditText)currentLayout.getChildAt(5);
            String endTime=currentEndTime.getText().toString(); String subStr1Hr=endTime.substring(27,28); String subStr1Min=endTime.substring(30,31);
            String startTime=currentStartTime.getText().toString(); String subStr2Hr=startTime.substring(29,30); String subStr2Min=startTime.substring(32,33);
            int sub1Hr=Integer.parseInt(subStr1Hr)*60; int finalStartTime=sub1Hr+ Integer.parseInt(subStr1Min);
            int sub2Hr=Integer.parseInt(subStr2Hr)*60; int finalEndTime=sub2Hr+ Integer.parseInt(subStr2Min);
            int T2= Math.abs(finalEndTime)- Math.abs(finalStartTime);
            totalT=totalT+T2;
        }
        return totalT;
    }

    //Todo: when the fab button is clicked more than one time (meaning that the activity info templates are deleted then added again) the array adapter that fills the activity names autofilling system is doubled (or multipled by the num of times the btn was clicked on) so it replicated the names unneccarliy. Solve that.

    public boolean finalValidationCheck(RadioGroup modePlanGroup2, int tasksNum){
        Boolean validationVal=true;

        //Checking for plan mode radio groups
        if(modePlanGroup2.getCheckedRadioButtonId()==-1){
            return false;
        }
        if(personaTypeGroup.getCheckedRadioButtonId()==-1){
            return false;
        }

        if(validDateIsPicked==false){
            return false;
        }

        //Checking for sleep times question
        if(sleepRadioGroup.getCheckedRadioButtonId()==-1){
            return false;
        }else{
            int x=200;
            if(sleepRadioGroup.getCheckedRadioButtonId()==x){
                if(sleepingStartTime.getText().length()!=36){
                    Log.i("Start time size", String.valueOf(sleepingStartTime.getText().length()));
                    Log.i("Start time size 2", Integer.toString((int) sleepingStartTime.getTextSize()));
                    return false;
                }
                if(sleepingEndTime.getText().length()!=34){
                    Log.i("ALERT", "END TIME ERROR");
                    return false;
                }
            }
        }
        //Checking for a valid number of tasks //Todo: consider changing that name into 'activities' so they can all be one name
        if(tasksNum==0){ return false; }
        //Checking for all of the activities' valid info
        for(int x=1; x<=selectedTaskNum; x++){
            switch (x){
                case 1:
                    validationVal=activityLayoutValidation(activity1Layout);
                    break;
                case 2:
                    validationVal=activityLayoutValidation(activity2Layout);
                    break;
                case 3:
                    validationVal=activityLayoutValidation(activity3Layout);
                    break;
                case 4:
                    validationVal=activityLayoutValidation(activity4Layout);
                    break;
                case 5:
                    validationVal=activityLayoutValidation(activity5Layout);
                    break;
                case 6:
                    validationVal=activityLayoutValidation(activity6Layout);
                    break;
                case 7:
                    validationVal=activityLayoutValidation(activity7Layout);
                    break;
                case 8:
                    validationVal=activityLayoutValidation(activity8Layout);
                    break;
                case 9:
                    validationVal=activityLayoutValidation(activity9Layout);
                    break;
                case 10:
                    validationVal=activityLayoutValidation(activity10Layout);
                    break;
                case 11:
                    validationVal=activityLayoutValidation(activity11Layout);
                    break;
                case 12:
                    validationVal=activityLayoutValidation(activity12Layout);
                    break;
                case 13:
                    validationVal=activityLayoutValidation(activity13Layout);
                    break;
                case 14:
                    validationVal=activityLayoutValidation(activity14Layout);
                    break;
                case 15:
                    validationVal=activityLayoutValidation(activity15Layout);
                    break;
                case 16:
                    validationVal=activityLayoutValidation(activity16Layout);
                    break;
                case 17:
                    validationVal=activityLayoutValidation(activity17Layout);
                    break;
                case 18:
                    validationVal=activityLayoutValidation(activity18Layout);
                    break;
                case 19:
                    validationVal=activityLayoutValidation(activity19Layout);
                    break;
                case 20:
                    validationVal=activityLayoutValidation(activity20Layout);
                    break;
            }
            return validationVal;
        }



        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_aplan);

        personaTypeLayout=findViewById(R.id.personaTypeLayout); nightOwlInfo=findViewById(R.id.nightOwlInfo); earlyBirdInfo=findViewById(R.id.earlyBirdInfo);
        personaTypeGroup=findViewById(R.id.personaTypeRadioGroup); nightOwlRadio=findViewById(R.id.nightOwlRadio); earlyBirdRadio=findViewById(R.id.nightOwlRadio);
        final int nightOwl = 1000;
        final int earlyBird=2000; nightOwlRadio.setId(nightOwl); earlyBirdRadio.setId(earlyBird);

        closeInfoPersonaBtn=new Button(CreateAPlan.this); closeInfoPersonaBtn.setBackground(getDrawable(R.drawable.ic_close_red_btn_24dp));
        RelativeLayout.LayoutParams closeInfoPersonaBtnParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        closeInfoPersonaBtnParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)215); closeInfoPersonaBtn.setLayoutParams(closeInfoPersonaBtnParams);
        closeInfoPersonaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personaTypeLayout.removeView(currentPersonaInfoLayout);
                nightOwlInfo.setClickable(true);earlyBirdInfo.setClickable(true);nightOwlRadio.setClickable(true);earlyBirdRadio.setClickable(true);
            }
        });
        //Below are the textView and the relative layout that will present the info on nightOwl
        nightOwlInfoTxt=new TextView(CreateAPlan.this); nightOwlInfoTxt.setBackgroundColor(Color.parseColor("#000000"));
        nightOwlInfoTxt.setTextColor(Color.parseColor("#FFFFFF")); nightOwlInfoTxt.setTextSize(20);
        RelativeLayout.LayoutParams nightOwlInfoTxtParams=new RelativeLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)275),
                convertDpToPx(CreateAPlan.this, (float)520));
        nightOwlInfoTxtParams.rightMargin=convertDpToPx(CreateAPlan.this, (float)3);nightOwlInfoTxtParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)3);
        nightOwlInfoTxt.setLayoutParams(nightOwlInfoTxtParams); nightOwlInfoTxt.setText(
                " Night Owl:\n  \n  People who are identified as night owls tend to be more energetic" +
                        "during the night than in the morning. The night is the most productive time for these people, and they tend to stay up late during the night. If you tend" +
                        " to be more aware and energetic during the night more than the morning, choose this option. This will help us assign to you the right tasks to you at the right time" );


        nightOwlInfoLayout=new RelativeLayout(CreateAPlan.this);
        RelativeLayout.LayoutParams nightOwlInfoLayoutParams= new RelativeLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)330),
                convertDpToPx(CreateAPlan.this, (float)470));
        nightOwlInfoLayoutParams.topMargin=convertDpToPx(CreateAPlan.this, (float)10); nightOwlInfoLayoutParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)60);
        nightOwlInfoLayout.setLayoutParams(nightOwlInfoLayoutParams); nightOwlInfoLayout.addView(nightOwlInfoTxt); nightOwlInfoLayout.setAlpha((float)0.93);

        //Below are the textView and the relative layout that will present the info on earlyBird
        earlyBirdInfoTxt=new TextView(CreateAPlan.this); earlyBirdInfoTxt.setBackgroundColor(Color.parseColor("#000000"));
        earlyBirdInfoTxt.setTextColor(Color.parseColor("#FFFFFF")); earlyBirdInfoTxt.setTextSize(20);
        RelativeLayout.LayoutParams earlyBirdInfoTxtParams=new RelativeLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)275),
                convertDpToPx(CreateAPlan.this, (float)520));
        earlyBirdInfoTxt.setLayoutParams(earlyBirdInfoTxtParams); earlyBirdInfoTxt.setText(
                " Night Owl:\n  \n  People who are identified as early birds tend to be more energetic" +
                        "during the day -especially during the morning- than in the night. The morning is the most productive time for these people, and they tend to wake up early. If you tend" +
                        " to be more aware and energetic during the morning than in the night, choose this option. This will help us assign to you the right tasks to you at the right time" );

        earlyBirdInfoLayout=new RelativeLayout(CreateAPlan.this);
        RelativeLayout.LayoutParams earlyBirdInfoLayoutParams= new RelativeLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)330),
                convertDpToPx(CreateAPlan.this, (float)470));
        earlyBirdInfoLayoutParams.topMargin=convertDpToPx(CreateAPlan.this, (float)20); earlyBirdInfoLayoutParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)60);
        earlyBirdInfoLayout.setLayoutParams(earlyBirdInfoLayoutParams); earlyBirdInfoLayout.addView(earlyBirdInfoTxt); earlyBirdInfoLayout.setAlpha((float)0.93);

        nightOwlInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (earlyBirdInfoLayout.getParent()!=null){
                    personaTypeLayout.removeView(earlyBirdInfoLayout);
                }
                personaTypeLayout.addView(nightOwlInfoLayout);
                previousInfoLayout=currentPersonaInfoLayout;
                currentPersonaInfoLayout=nightOwlInfoLayout;
                if (nightOwlInfoLayout==currentPersonaInfoLayout){
                    if(previousInfoLayout!=null){
                        previousInfoLayout.removeView(closeInfoPersonaBtn);
                    }
                    nightOwlInfoLayout.addView(closeInfoPersonaBtn);
                }
                nightOwlInfo.setClickable(false);earlyBirdInfo.setClickable(false);nightOwlRadio.setClickable(false);earlyBirdRadio.setClickable(false);
            }
        });
        earlyBirdInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nightOwlInfoLayout.getParent()!=null){
                    personaTypeLayout.removeView(nightOwlInfoLayout);
                }
                personaTypeLayout.addView(earlyBirdInfoLayout);
                previousInfoLayout=currentPersonaInfoLayout;
                currentPersonaInfoLayout=earlyBirdInfoLayout;
                if (earlyBirdInfoLayout==currentPersonaInfoLayout){
                    if(previousInfoLayout!=null){
                        previousInfoLayout.removeView(closeInfoPersonaBtn);
                    }
                    earlyBirdInfoLayout.addView(closeInfoPersonaBtn);
                }
                nightOwlInfo.setClickable(false);earlyBirdInfo.setClickable(false);nightOwlRadio.setClickable(false);earlyBirdRadio.setClickable(false);
            }
        });

        selectDateBtn=findViewById(R.id.selectDateBtn);
        selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        numOfTasks=findViewById(R.id.numOfTasks);
        final ArrayAdapter<CharSequence> numOfTasksAdapter=ArrayAdapter.createFromResource(CreateAPlan.this, R.array.taskNum, android.R.layout.simple_spinner_item);
        numOfTasksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numOfTasks.setAdapter(numOfTasksAdapter);
        numOfTasks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTaskNum=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        activity1YesRadio = new RadioButton(CreateAPlan.this);
        activity1NoRadio = new RadioButton(CreateAPlan.this);
        dateTextView=findViewById(R.id.dateTextView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        dayPlanLayout=findViewById(R.id.dayPlanLayout); mainLayout2=findViewById(R.id.mainLayout2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //LinearLayout info
                counter=1; mainLayout2.removeAllViews(); int previousNumYesId=1; int previousNumNoId=2;
                activity1Layout=new LinearLayout(CreateAPlan.this); activity1Layout.setOrientation(LinearLayout.VERTICAL); activity2Layout=new LinearLayout(CreateAPlan.this); activity2Layout.setOrientation(LinearLayout.VERTICAL);
                activity3Layout=new LinearLayout(CreateAPlan.this); activity3Layout.setOrientation(LinearLayout.VERTICAL);  activity4Layout=new LinearLayout(CreateAPlan.this); activity4Layout.setOrientation(LinearLayout.VERTICAL);
                activity5Layout=new LinearLayout(CreateAPlan.this); activity5Layout.setOrientation(LinearLayout.VERTICAL);activity6Layout=new LinearLayout(CreateAPlan.this); activity6Layout.setOrientation(LinearLayout.VERTICAL);
                activity7Layout=new LinearLayout(CreateAPlan.this); activity7Layout.setOrientation(LinearLayout.VERTICAL); activity8Layout=new LinearLayout(CreateAPlan.this); activity8Layout.setOrientation(LinearLayout.VERTICAL);counter=1;
                activity9Layout=new LinearLayout(CreateAPlan.this); activity9Layout.setOrientation(LinearLayout.VERTICAL); activity10Layout=new LinearLayout(CreateAPlan.this); activity10Layout.setOrientation(LinearLayout.VERTICAL);
                activity11Layout=new LinearLayout(CreateAPlan.this); activity11Layout.setOrientation(LinearLayout.VERTICAL);  activity12Layout=new LinearLayout(CreateAPlan.this); activity12Layout.setOrientation(LinearLayout.VERTICAL);
                activity13Layout=new LinearLayout(CreateAPlan.this); activity13Layout.setOrientation(LinearLayout.VERTICAL);activity14Layout=new LinearLayout(CreateAPlan.this); activity14Layout.setOrientation(LinearLayout.VERTICAL);
                activity15Layout=new LinearLayout(CreateAPlan.this); activity15Layout.setOrientation(LinearLayout.VERTICAL); activity16Layout=new LinearLayout(CreateAPlan.this); activity16Layout.setOrientation(LinearLayout.VERTICAL);
                activity17Layout=new LinearLayout(CreateAPlan.this); activity17Layout.setOrientation(LinearLayout.VERTICAL); activity18Layout=new LinearLayout(CreateAPlan.this); activity18Layout.setOrientation(LinearLayout.VERTICAL);
                activity19Layout=new LinearLayout(CreateAPlan.this); activity19Layout.setOrientation(LinearLayout.VERTICAL); activity20Layout=new LinearLayout(CreateAPlan.this); activity20Layout.setOrientation(LinearLayout.VERTICAL);

                activityNamesPreferences=CreateAPlan.this.getSharedPreferences("com.ued.timeplanner", MODE_PRIVATE);
                activityNamesYesPreferences=CreateAPlan.this.getSharedPreferences("com.ued.timeplanner", MODE_PRIVATE);
                extractActivityNamesYesList();
                extractActivityNamesList();

                for (int x=1; x<=activityNames.size(); x++){
                    finalActivityNames.add(activityNames.get(x-1));
                }
                for (int x=1; x<=activityNamesYes.size(); x++){
                    finalActivityNames.add(activityNamesYes.get(x-1));
                }

                activityNamesAdapter=null;
                activityNamesAdapter=new ArrayAdapter(CreateAPlan.this, android.R.layout.simple_list_item_1, finalActivityNames);

                while (counter <= selectedTaskNum) {
                    //First Question textview info
                    firstQTxtView1 = new TextView(CreateAPlan.this);
                    firstQTxtView1.setTextColor(Color.parseColor("#000000"));
                    LinearLayout.LayoutParams firstQTxtView1Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    firstQTxtView1Params.topMargin = convertDpToPx(CreateAPlan.this, (float) 15);
                    firstQTxtView1.setLayoutParams(firstQTxtView1Params);
                    firstQTxtView1.setText("Write the name of the activity(ex: exercise, study for the math test, etc.):");
                    firstQTxtView1.setTextSize(20);

                    //Second textview answer radioGroup Info
                    radioGroup1 = new RadioGroup(CreateAPlan.this);
                    LinearLayout.LayoutParams radioGroup1Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    radioGroup1Params.topMargin = convertDpToPx(CreateAPlan.this, (float) 4);
                    radioGroup1.setLayoutParams(radioGroup1Params);
                    //Radio button YES
                    activity1YesRadio = new RadioButton(CreateAPlan.this);
                    activity1YesRadio.setText("Yes");
                    LinearLayout.LayoutParams activity1YesRadioParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    activity1YesRadioParams.topMargin = convertDpToPx(CreateAPlan.this, (float) 1);
                    activity1YesRadioParams.leftMargin = convertDpToPx(CreateAPlan.this, (float) 130);
                    activity1YesRadio.setLayoutParams(activity1YesRadioParams);
                    activity1YesRadio.setId(previousNumYesId); previousNumYesId=previousNumYesId+2;
                    yesRadioFunc(activity1YesRadio.getId());
                    radioGroup1.addView(activity1YesRadio);
                    //Radio button NO
                    activity1NoRadio = new RadioButton(CreateAPlan.this);
                    activity1NoRadio.setText("No");
                    LinearLayout.LayoutParams activity1NoRadioParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    activity1NoRadioParams.topMargin = convertDpToPx(CreateAPlan.this, (float) -32);
                    activity1NoRadioParams.leftMargin = convertDpToPx(CreateAPlan.this, (float) 230);
                    activity1NoRadio.setLayoutParams(activity1NoRadioParams);
                    activity1NoRadio.setId(previousNumNoId); previousNumNoId=previousNumNoId+2;
                    NoRadioFunc(activity1NoRadio.getId());
                    radioGroup1.addView(activity1NoRadio);

                    //First question answer EditText info
                    activityNameEditText1 = new AutoCompleteTextView(CreateAPlan.this);
                    activityNameEditText1.setHint("Activity name...");
                    LinearLayout.LayoutParams activityNameEditText1Params = new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float) 350),
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    activityNameEditText1Params.topMargin = convertDpToPx(CreateAPlan.this, (float) 10);
                    activityNameEditText1Params.leftMargin = convertDpToPx(CreateAPlan.this, (float) 30);
                    activityNameEditText1.setLayoutParams(activityNameEditText1Params);
                    activityNameEditText1.setEms(10);
                    activityNameEditText1.setAdapter(activityNamesAdapter);
                    activityNameEditText1.setCompletionHint("Activity Name");
                    activityNameEditText1.setThreshold(1);
                    activityNameEditText1.setInputType(AUTOFILL_TYPE_TEXT);
                    //Second TextView question info
                    secondQTxtView1 = new TextView(CreateAPlan.this);
                    secondQTxtView1.setTextSize(17);
                    secondQTxtView1.setTextColor(Color.parseColor("#000000"));
                    LinearLayout.LayoutParams secondQTxtView1Params = new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float) 270), ViewGroup.LayoutParams.WRAP_CONTENT);
                    secondQTxtView1Params.topMargin = convertDpToPx(CreateAPlan.this, (float) 5);
                    secondQTxtView1Params.leftMargin = convertDpToPx(CreateAPlan.this, (float) 70);
                    secondQTxtView1.setLayoutParams(secondQTxtView1Params);
                    secondQTxtView1.setText("Is there a fixed time that the activity starts at? (ex: work time-8 am to 3pm)");
                    //adding the views to the linear layout
                    //TODO: END IS HERE

                    findViewById(R.id.selectDateBtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDatePickerDialog();
                        }
                    });

                    if (counter == 1) {
                        activity1Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity1Layout.addView(firstQTxtView1); activity1Layout.addView(activityNameEditText1); activity1Layout.addView(secondQTxtView1); activity1Layout.addView(radioGroup1);
                        mainLayout2.addView(activity1Layout);
                    }else if (counter == 2) {
                        activity2Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity2Layout.addView(firstQTxtView1); activity2Layout.addView(activityNameEditText1); activity2Layout.addView(secondQTxtView1); activity2Layout.addView(radioGroup1);
                        mainLayout2.addView(activity2Layout);
                    }else if (counter == 3) {
                        activity3Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity3Layout.addView(firstQTxtView1); activity3Layout.addView(activityNameEditText1); activity3Layout.addView(secondQTxtView1); activity3Layout.addView(radioGroup1);
                        mainLayout2.addView(activity3Layout);
                    }else if (counter == 4) {
                        activity4Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity4Layout.addView(firstQTxtView1); activity4Layout.addView(activityNameEditText1); activity4Layout.addView(secondQTxtView1); activity4Layout.addView(radioGroup1);
                        mainLayout2.addView(activity4Layout);
                    }else if (counter == 5) {
                        activity5Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity5Layout.addView(firstQTxtView1); activity5Layout.addView(activityNameEditText1); activity5Layout.addView(secondQTxtView1); activity5Layout.addView(radioGroup1);
                        mainLayout2.addView(activity5Layout);
                    }else if (counter == 6) {
                        activity6Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity6Layout.addView(firstQTxtView1); activity6Layout.addView(activityNameEditText1); activity6Layout.addView(secondQTxtView1); activity6Layout.addView(radioGroup1);
                        mainLayout2.addView(activity6Layout);
                    }else if (counter == 7) {
                        activity7Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity7Layout.addView(firstQTxtView1); activity7Layout.addView(activityNameEditText1); activity7Layout.addView(secondQTxtView1); activity7Layout.addView(radioGroup1);
                        mainLayout2.addView(activity7Layout);
                    }else if (counter == 8) {
                        activity8Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity8Layout.addView(firstQTxtView1); activity8Layout.addView(activityNameEditText1); activity8Layout.addView(secondQTxtView1); activity8Layout.addView(radioGroup1);
                        mainLayout2.addView(activity8Layout);
                    }else if (counter == 9) {
                        activity9Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity9Layout.addView(firstQTxtView1); activity9Layout.addView(activityNameEditText1); activity9Layout.addView(secondQTxtView1); activity9Layout.addView(radioGroup1);
                        mainLayout2.addView(activity9Layout);
                    }else if (counter == 10) {
                        activity10Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity10Layout.addView(firstQTxtView1); activity10Layout.addView(activityNameEditText1); activity10Layout.addView(secondQTxtView1); activity10Layout.addView(radioGroup1);
                        mainLayout2.addView(activity10Layout);
                    }else if (counter == 11) {
                        activity11Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity11Layout.addView(firstQTxtView1); activity11Layout.addView(activityNameEditText1); activity11Layout.addView(secondQTxtView1); activity11Layout.addView(radioGroup1);
                        mainLayout2.addView(activity11Layout);
                    }else if (counter == 12) {
                        activity12Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity12Layout.addView(firstQTxtView1); activity12Layout.addView(activityNameEditText1); activity12Layout.addView(secondQTxtView1); activity12Layout.addView(radioGroup1);
                        mainLayout2.addView(activity12Layout);
                    }else if (counter == 13) {
                        activity13Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity13Layout.addView(firstQTxtView1); activity13Layout.addView(activityNameEditText1); activity13Layout.addView(secondQTxtView1); activity13Layout.addView(radioGroup1);
                        mainLayout2.addView(activity13Layout);
                    }else if (counter == 14) {
                        activity14Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity14Layout.addView(firstQTxtView1); activity14Layout.addView(activityNameEditText1); activity14Layout.addView(secondQTxtView1); activity14Layout.addView(radioGroup1);
                        mainLayout2.addView(activity14Layout);
                    }else if (counter == 15) {
                        activity15Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity15Layout.addView(firstQTxtView1); activity15Layout.addView(activityNameEditText1); activity15Layout.addView(secondQTxtView1); activity15Layout.addView(radioGroup1);
                        mainLayout2.addView(activity15Layout);
                    }else if (counter == 16) {
                        activity16Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity16Layout.addView(firstQTxtView1); activity16Layout.addView(activityNameEditText1); activity16Layout.addView(secondQTxtView1); activity16Layout.addView(radioGroup1);
                        mainLayout2.addView(activity16Layout);
                    }else if (counter == 17) {
                        activity17Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity17Layout.addView(firstQTxtView1); activity17Layout.addView(activityNameEditText1); activity17Layout.addView(secondQTxtView1); activity17Layout.addView(radioGroup1);
                        mainLayout2.addView(activity17Layout);
                    }else if (counter == 18) {
                        activity18Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity18Layout.addView(firstQTxtView1); activity18Layout.addView(activityNameEditText1); activity18Layout.addView(secondQTxtView1); activity18Layout.addView(radioGroup1);
                        mainLayout2.addView(activity18Layout);
                    }else if (counter == 19) {
                        activity19Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity19Layout.addView(firstQTxtView1); activity19Layout.addView(activityNameEditText1); activity19Layout.addView(secondQTxtView1); activity19Layout.addView(radioGroup1);
                        mainLayout2.addView(activity19Layout);
                    }else if (counter == 20) {
                        activity20Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        activity20Layout.addView(firstQTxtView1); activity20Layout.addView(activityNameEditText1); activity20Layout.addView(secondQTxtView1); activity20Layout.addView(radioGroup1);
                        mainLayout2.addView(activity20Layout);
                    }
                counter=counter+1;
                }
                //GENERATE UI ENDING FOR THE APPLICATION left margin:396
                //INFO BUTTONS LAYOUTS
                closeLayoutInfoBtn=new Button(CreateAPlan.this); closeLayoutInfoBtn.setBackground(getDrawable(R.drawable.ic_close_red_btn_24dp));
                RelativeLayout.LayoutParams closeLayoutInfoBtnParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                closeLayoutInfoBtnParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)215); closeLayoutInfoBtn.setLayoutParams(closeLayoutInfoBtnParams);
                closeLayoutInfoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        planModeLayout.removeView(currentInfoLayout);
                        wideLevelBtn.setClickable(true); moderateLevelBtn.setClickable(true); compactLevelBtn.setClickable(true);
                        wideLevelInfoBtn.setClickable(true);moderateLevelInfoBtn.setClickable(true);compactLevelInfoBtn.setClickable(true);
                    }
                });

                wideInfoBtnTxt=new TextView(CreateAPlan.this); wideInfoBtnTxt.setBackgroundColor(Color.parseColor("#000000"));
                wideInfoBtnTxt.setTextColor(Color.parseColor("#FFFFFF")); wideInfoBtnTxt.setTextSize(20);
                RelativeLayout.LayoutParams wideInfoBtnTxtParams=new RelativeLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)275),
                        convertDpToPx(CreateAPlan.this, (float)520));
                wideInfoBtnTxt.setLayoutParams(wideInfoBtnTxtParams); wideInfoBtnTxt.setText(
                        "  Wide Plan Mode:\n  \n  In this mode, the tasks will be spread as wide as possible across the day." +
                                "\n  \n  Advantages: the tasks will mostly require short time to be done." +
                                "\n  \n  Disadvantages: Since the tasks will be spread across the day, they may take up the whole day leaving you with little to no free time after providing breaks between tasks." );
                moderateInfoBtnTxt=new TextView(CreateAPlan.this); moderateInfoBtnTxt.setBackgroundColor(Color.parseColor("#000000"));
                moderateInfoBtnTxt.setTextColor(Color.parseColor("#FFFFFF")); moderateInfoBtnTxt.setTextSize(20);
                RelativeLayout.LayoutParams moderateInfoBtnTxtParams=new RelativeLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)275),
                        convertDpToPx(CreateAPlan.this, (float)520));
                moderateInfoBtnTxt.setLayoutParams(moderateInfoBtnTxtParams);moderateInfoBtnTxt.setText(
                        "  Moderate Plan Mode:\n \n  In this mode, tasks will be spread moderately across the day" +
                                "\n \n Advantages: Will mostly provide a balance to your day between working on tasks and having free time. This mode works best for plans that have a moderate number of activities that are not urgent but important" +
                                "\n \n Disadvantages: Finishing tasks will require on average more time and effort than the Wide Plan Mode.");
                compactedInfoBtnTxt=new TextView(CreateAPlan.this); compactedInfoBtnTxt.setBackgroundColor(Color.parseColor("#000000"));
                compactedInfoBtnTxt.setTextColor(Color.parseColor("#FFFFFF")); compactedInfoBtnTxt.setTextSize(18);
                RelativeLayout.LayoutParams compactedInfoBtnTxtParams=new RelativeLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)275),
                        convertDpToPx(CreateAPlan.this, (float)520));
                compactedInfoBtnTxt.setLayoutParams(compactedInfoBtnTxtParams); compactedInfoBtnTxt.setText(
                        "  Compact Plan Mode: \n \n In this mode, tasks will usually be concentrated at a specific part of the day (thus the word compact). The higher number of activities that you have entered, the wider the time frame of that specific part of the day gets." +
                                "\n \n  Advantages: Tasks can be done faster since this mode requires more effort. This mode works best for days that contain multiple urgent tasks that need to be done" +
                                "\n \n  Disadvantages: This mode requires the most effort and time out of the rest modes. If the tasks that you have entered are a lot, this mode may take up your whole day requiring your effort providing you with short number of breaks");

                wideInfoBtnLayout=new RelativeLayout(CreateAPlan.this);
                RelativeLayout.LayoutParams wideInfoBtnLayoutParams= new RelativeLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)330),
                        convertDpToPx(CreateAPlan.this, (float)470));
                wideInfoBtnLayoutParams.topMargin=convertDpToPx(CreateAPlan.this, (float)140); wideInfoBtnLayoutParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)60);
                wideInfoBtnLayout.setLayoutParams(wideInfoBtnLayoutParams); wideInfoBtnLayout.addView(wideInfoBtnTxt); wideInfoBtnLayout.setAlpha((float)0.93);
                moderateInfoBtnLayout=new RelativeLayout(CreateAPlan.this);
                RelativeLayout.LayoutParams moderateInfoBtnLayoutParams= new RelativeLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)330),
                        convertDpToPx(CreateAPlan.this, (float)470));
                moderateInfoBtnLayoutParams.topMargin=convertDpToPx(CreateAPlan.this, (float)140); moderateInfoBtnLayoutParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)60);
                moderateInfoBtnLayout.setLayoutParams(moderateInfoBtnLayoutParams); moderateInfoBtnLayout.addView(moderateInfoBtnTxt); moderateInfoBtnLayout.setAlpha((float)0.93);
                compactedInfoBtnLayout=new RelativeLayout(CreateAPlan.this);
                RelativeLayout.LayoutParams compactedInfoBtnLayoutParams= new RelativeLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)330),
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                compactedInfoBtnLayoutParams.topMargin=convertDpToPx(CreateAPlan.this, (float)140); compactedInfoBtnLayoutParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)60);
                compactedInfoBtnLayout.setLayoutParams(compactedInfoBtnLayoutParams); compactedInfoBtnLayout.addView(compactedInfoBtnTxt); compactedInfoBtnLayout.setAlpha((float)0.93);

                //parentLayout Info
                endingLayout=new LinearLayout(CreateAPlan.this); endingLayout.setOrientation(LinearLayout.VERTICAL);
                endingLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                //SLEEP TIME LAYOUT
                sleepTimeLayout=new LinearLayout(CreateAPlan.this); sleepTimeLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams sleepTimeLayoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                sleepTimeLayoutParams.topMargin=convertDpToPx(CreateAPlan.this, (float)45);sleepTimeLayoutParams.rightMargin=convertDpToPx(CreateAPlan.this, (float)10);
                sleepTimeLayout.setLayoutParams(sleepTimeLayoutParams);
                //SLEEP TIME QUESTION
                sleepQTxtView = new TextView(CreateAPlan.this);
                sleepQTxtView.setTextSize(23);
                sleepQTxtView.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams sleepQTxtViewParams = new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float) 350), ViewGroup.LayoutParams.WRAP_CONTENT);
                sleepQTxtViewParams.topMargin = convertDpToPx(CreateAPlan.this, (float) 1);
                sleepQTxtViewParams.leftMargin = convertDpToPx(CreateAPlan.this, (float) 45);
                sleepQTxtView.setLayoutParams(sleepQTxtViewParams);
                sleepQTxtView.setText("Is your sleeping time between 10:00 P.M - 06:00 A.M  ? This will help us create a reasonable plan.");
                //SLEEP TIME RADIO GROUP
                sleepRadioGroup = new RadioGroup(CreateAPlan.this);
                LinearLayout.LayoutParams sleepRadioGroupParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                sleepRadioGroupParams.topMargin = convertDpToPx(CreateAPlan.this, (float) 4);
                sleepRadioGroup.setLayoutParams(sleepRadioGroupParams);
                //Radio button YES
                sleepYesRadio = new RadioButton(CreateAPlan.this);
                sleepYesRadio.setText("Yes");
                LinearLayout.LayoutParams sleepYesRadioParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                sleepYesRadioParams.topMargin = convertDpToPx(CreateAPlan.this, (float) 1);
                sleepYesRadioParams.leftMargin = convertDpToPx(CreateAPlan.this, (float) 130);
                sleepYesRadio.setLayoutParams(sleepYesRadioParams);
                int sleepYesID=100; sleepYesRadio.setId(sleepYesID);
                sleepRadioGroup.addView(sleepYesRadio);
                //Radio button NO
                sleepNoRadio = new RadioButton(CreateAPlan.this);
                sleepNoRadio.setText("No");
                LinearLayout.LayoutParams sleepNoRadioParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                sleepNoRadioParams.topMargin = convertDpToPx(CreateAPlan.this, (float) -32);
                sleepNoRadioParams.leftMargin = convertDpToPx(CreateAPlan.this, (float) 230);
                sleepNoRadio.setLayoutParams(sleepNoRadioParams);
                int sleepNoID=200; sleepNoRadio.setId(sleepNoID);
                sleepNoRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sleepNoFunc();
                    }
                });
                sleepRadioGroup.addView(sleepNoRadio);

                sleepTimeLayout.addView(sleepQTxtView);sleepTimeLayout.addView(sleepRadioGroup);
                //planModeLayout info
                planModeLayout=new RelativeLayout(CreateAPlan.this);
                RelativeLayout.LayoutParams planModeLayoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                planModeLayoutParams.topMargin=convertDpToPx(CreateAPlan.this, (float)60); planModeLayout.setLayoutParams(planModeLayoutParams);
                //UI INFO inside the planModeLayout
                //plan mode textView Question
                planModeQuestion=new TextView(CreateAPlan.this); planModeQuestion.setTextSize(20); planModeQuestion.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams planModeQuestionParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                planModeQuestionParams.topMargin=convertDpToPx(CreateAPlan.this, (float) 10);planModeQuestionParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)4);
                planModeQuestion.setLayoutParams(planModeQuestionParams);
                planModeQuestion.setText("Choose from the following options what mode you would like your plan to be based on (click on the info button beside each mode choice to learn more about it and its advantages and disadvantages).");

                //plan mode radio group
                modePlanGroup=new RadioGroup(CreateAPlan.this);
                LinearLayout.LayoutParams modePlanGroupParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                modePlanGroupParams.topMargin=convertDpToPx(CreateAPlan.this, (float)130);
                modePlanGroup.setLayoutParams(modePlanGroupParams);

                //WIDE LEVEL BTN/ WIDE LEVEL BTN INFO
                wideLevelBtn=new RadioButton(CreateAPlan.this);
                LinearLayout.LayoutParams wideLevelBtnParams=new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)250),
                        convertDpToPx(CreateAPlan.this, (float)130));
                wideLevelBtnParams.topMargin=convertDpToPx(CreateAPlan.this, (float)10); wideLevelBtnParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)75);
                wideLevelBtn.setLayoutParams(wideLevelBtnParams); wideLevelBtn.setBackground(getDrawable(R.drawable.radio_selecter));
                wideLevelBtn.setButtonDrawable(android.R.color.transparent); wideLevelBtn.setText("Wide Plan Mode"); wideLevelBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                wideLevelBtn.setTextSize(20); wideLevelBtn.setTextColor(CreateAPlan.this.getResources().getColorStateList(R.color.radio_text_selector) );
                int wideRID=1; wideLevelBtn.setId(wideRID);
                wideLevelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CreateAPlan.this, "You selected the wide plan mode", Toast.LENGTH_SHORT).show();
                    }
                });
                wideLevelInfoBtn=new Button(CreateAPlan.this);
                RelativeLayout.LayoutParams wideLevelInfoBtnParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                wideLevelInfoBtnParams.topMargin=convertDpToPx(CreateAPlan.this, (float)185); wideLevelInfoBtnParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)330);
                wideLevelInfoBtn.setLayoutParams(wideLevelInfoBtnParams); wideLevelInfoBtn.setBackground(getDrawable(R.drawable.ic_info_black_24dp));
                wideLevelInfoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (moderateInfoBtnLayout.getParent()!=null){
                            planModeLayout.removeView(moderateInfoBtnLayout);
                        }
                        if(compactedInfoBtnLayout.getParent()!=null){
                            planModeLayout.removeView(compactedInfoBtnLayout);
                        }
                        planModeLayout.addView(wideInfoBtnLayout);
                        previousInfoLayout=currentInfoLayout;
                        currentInfoLayout=wideInfoBtnLayout;
                        if (wideInfoBtnLayout==currentInfoLayout){
                            if(previousInfoLayout!=null){
                                previousInfoLayout.removeView(closeLayoutInfoBtn);
                            }
                            wideInfoBtnLayout.addView(closeLayoutInfoBtn);
                        }
                        wideLevelBtn.setClickable(false); moderateLevelBtn.setClickable(false);compactLevelBtn.setClickable(false);
                        wideLevelInfoBtn.setClickable(false);moderateLevelInfoBtn.setClickable(false);compactLevelInfoBtn.setClickable(false);
                    }
                });
                //Moderate level BTN/ MODERATE LEVEL BTN INFO
                moderateLevelBtn=new RadioButton(CreateAPlan.this);
                RelativeLayout.LayoutParams moderateLevelBtnParams=new RelativeLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)250),
                        convertDpToPx(CreateAPlan.this, (float)130));
                moderateLevelBtnParams.topMargin=convertDpToPx(CreateAPlan.this, (float)10); moderateLevelBtnParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)75);
                moderateLevelBtn.setLayoutParams(moderateLevelBtnParams); moderateLevelBtn.setBackground(getDrawable(R.drawable.radio_selecter));
                moderateLevelBtn.setButtonDrawable(android.R.color.transparent); moderateLevelBtn.setText("Moderate Plan Mode"); moderateLevelBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                moderateLevelBtn.setTextSize(20); moderateLevelBtn.setTextColor(CreateAPlan.this.getResources().getColorStateList(R.color.radio_text_selector) );
                int moderateRID=2; moderateLevelBtn.setId(moderateRID);
                moderateLevelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CreateAPlan.this, "You selected the moderate plan mode", Toast.LENGTH_SHORT).show();
                    }
                });
                moderateLevelInfoBtn=new Button(CreateAPlan.this);
                RelativeLayout.LayoutParams moderateLevelInfoBtnParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                moderateLevelInfoBtnParams.topMargin=convertDpToPx(CreateAPlan.this, (float)325); moderateLevelInfoBtnParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)330);
                moderateLevelInfoBtn.setLayoutParams(moderateLevelInfoBtnParams); moderateLevelInfoBtn.setBackground(getDrawable(R.drawable.ic_info_black_24dp));
                moderateLevelInfoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (wideInfoBtnLayout.getParent()!=null){
                            planModeLayout.removeView(wideInfoBtnLayout);
                        }
                        if(compactedInfoBtnLayout.getParent()!=null){
                            planModeLayout.removeView(compactedInfoBtnLayout);
                        }
                        planModeLayout.addView(moderateInfoBtnLayout);
                        previousInfoLayout=currentInfoLayout;
                        currentInfoLayout=moderateInfoBtnLayout;
                        if (moderateInfoBtnLayout==currentInfoLayout) {
                            if(previousInfoLayout!=null){
                                previousInfoLayout.removeView(closeLayoutInfoBtn);
                            }
                            moderateInfoBtnLayout.addView(closeLayoutInfoBtn);
                        }
                        wideLevelBtn.setClickable(false); moderateLevelBtn.setClickable(false);compactLevelBtn.setClickable(false);
                        wideLevelInfoBtn.setClickable(false);moderateLevelInfoBtn.setClickable(false);compactLevelInfoBtn.setClickable(false);
                    }
                });
                //Compact Level plan
                compactLevelBtn=new RadioButton(CreateAPlan.this);
                LinearLayout.LayoutParams compactLevelBtnParams=new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)250),
                        convertDpToPx(CreateAPlan.this, (float)130));
                compactLevelBtnParams.topMargin=convertDpToPx(CreateAPlan.this, (float)10); compactLevelBtnParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)75);
                compactLevelBtn.setLayoutParams(compactLevelBtnParams); compactLevelBtn.setBackground(getDrawable(R.drawable.radio_selecter));
                compactLevelBtn.setButtonDrawable(android.R.color.transparent); compactLevelBtn.setText("Compact Plan Mode"); compactLevelBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                compactLevelBtn.setTextSize(20); compactLevelBtn.setTextColor(CreateAPlan.this.getResources().getColorStateList(R.color.radio_text_selector) );
                int compactRID=3; compactLevelBtn.setId(compactRID);
                compactLevelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CreateAPlan.this, "You selected the compact plan mode", Toast.LENGTH_SHORT).show();
                    }
                });
                compactLevelInfoBtn=new Button(CreateAPlan.this);
                LinearLayout.LayoutParams compactLevelInfoParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                compactLevelInfoParams.topMargin=convertDpToPx(CreateAPlan.this, (float)470); compactLevelInfoParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)330);
                compactLevelInfoBtn.setLayoutParams(compactLevelInfoParams); compactLevelInfoBtn.setBackground(getDrawable(R.drawable.ic_info_black_24dp));
                compactLevelInfoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (moderateInfoBtnLayout.getParent()!=null){
                            planModeLayout.removeView(moderateInfoBtnLayout);
                        }
                        if(wideInfoBtnLayout.getParent()!=null){
                            planModeLayout.removeView(wideInfoBtnLayout);
                        }
                        planModeLayout.addView(compactedInfoBtnLayout);
                        previousInfoLayout=currentInfoLayout;
                        currentInfoLayout=compactedInfoBtnLayout;
                        if (compactedInfoBtnLayout==currentInfoLayout){
                            if(previousInfoLayout!=null){
                                previousInfoLayout.removeView(closeLayoutInfoBtn);
                            }
                            compactedInfoBtnLayout.addView(closeLayoutInfoBtn);
                        }
                        wideLevelBtn.setClickable(false); moderateLevelBtn.setClickable(false);compactLevelBtn.setClickable(false);
                        wideLevelInfoBtn.setClickable(false);moderateLevelInfoBtn.setClickable(false);compactLevelInfoBtn.setClickable(false);
                    }
                });

                modePlanGroup.addView(wideLevelBtn);modePlanGroup.addView(moderateLevelBtn);modePlanGroup.addView(compactLevelBtn);
                planModeLayout.addView(planModeQuestion); planModeLayout.addView(wideLevelInfoBtn);planModeLayout.addView(moderateLevelInfoBtn);planModeLayout.addView(compactLevelInfoBtn);
                planModeLayout.addView(modePlanGroup);
                //REST OF ENDING UI
                cancelThePlanBtn=new Button(CreateAPlan.this); cancelThePlanBtn.setBackground(getDrawable(R.drawable.cancel_the_plan_btnshape));
                LinearLayout.LayoutParams cancelThePlanBtnParams=new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this,(float)150),
                        convertDpToPx(CreateAPlan.this,(float)60));
                cancelThePlanBtnParams.topMargin=convertDpToPx(CreateAPlan.this, (float)45); cancelThePlanBtnParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)50);
                cancelThePlanBtn.setLayoutParams(cancelThePlanBtnParams); cancelThePlanBtn.setText("Cancel the Plan"); cancelThePlanBtn.setTextColor(Color.parseColor("#FFFFFF"));
                cancelThePlanBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                cancelThePlanBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(CreateAPlan.this)
                                .setIcon(android.R.drawable.ic_menu_close_clear_cancel)
                                .setTitle("Are you sure that you want to cancel the plan?")
                                .setMessage("If you exit this view, your plan will be discarded")
                                .setPositiveButton("Cancel the plan", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentClosePlan=new Intent(getApplicationContext(), MainActivity.class);
                                        intentClosePlan.setType("close plan");
                                        startActivity(intentClosePlan);
                                    }
                                })
                                .setNegativeButton("Close",null )
                                .show();
                    }
                });

                remainingT=1440; //24 hrs in mins
                //--------Here, I am adding the top 40 min intervals of each ultradian cycle which will be used later
                //------------------Wide Plan Mode------------------------------
                generalTopTimesWide=new ArrayList<>(); generalTopTimesWide.add("1040-1120");generalTopTimesWide.add("1250-1330");generalTopTimesWide.add("1500-1540");generalTopTimesWide.add("1710-1750");
                generalTopTimesWide.add("1920-2000");generalTopTimesWide.add("2130-2210");generalTopTimesWide.add("2340-0020");generalTopTimesWide.add("0150-0230");generalTopTimesWide.add("0400-0440");
                generalTopTimesWide.add("0610-0650");generalTopTimesWide.add("0825-0905"); //It repeats after that in the same cycle

                intervalRatingWideNight=new ArrayList<>();
                intervalRatingWideNight.add(9);intervalRatingWideNight.add(7);intervalRatingWideNight.add(5);intervalRatingWideNight.add(6);intervalRatingWideNight.add(7);
                intervalRatingWideNight.add(9);intervalRatingWideNight.add(10);intervalRatingWideNight.add(10);intervalRatingWideNight.add(7);intervalRatingWideNight.add(7);
                intervalRatingWideNight.add(8);


                //----------------------------------------------

                createThePlanBtn=new Button(CreateAPlan.this); createThePlanBtn.setBackground(getDrawable(R.drawable.create_the_plan_btnshape));
                LinearLayout.LayoutParams createThePlanBtnParams=new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this,(float)150),
                        convertDpToPx(CreateAPlan.this,(float)60));
                createThePlanBtnParams.topMargin=convertDpToPx(CreateAPlan.this, (float)-60); createThePlanBtnParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)220);
                createThePlanBtn.setLayoutParams(createThePlanBtnParams); createThePlanBtn.setText("Create the plan"); createThePlanBtn.setTextColor(Color.parseColor("#FFFFFF"));
                createThePlanBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                createThePlanBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("Button info", "BUTTON IS CLICKED");
                        Log.i("Selected task num", Integer.toString(selectedTaskNum));
                        totalT=0;

                        if(finalValidationCheck(modePlanGroup, selectedTaskNum)){
                            ArrayList<Object>extractAddMinsFuncValsList=null;

                            Log.i("Validation info", "FINAL VALIDATION RETURNED TRUE");
                            for(int x=1; x<=selectedTaskNum; x++){
                                switch (x){
                                    case 1:
                                        totalT=totalT + activityLayoutTValidation(activity1Layout, totalT);
                                        break;
                                    case 2:
                                        totalT=totalT + activityLayoutTValidation(activity2Layout, totalT);
                                        break;
                                    case 3:
                                        totalT=totalT + activityLayoutTValidation(activity3Layout, totalT);
                                        break;
                                    case 4:
                                        totalT=totalT + activityLayoutTValidation(activity4Layout, totalT);
                                        break;
                                    case 5:
                                        totalT=totalT + activityLayoutTValidation(activity5Layout, totalT);
                                        break;
                                    case 6:
                                        totalT=totalT + activityLayoutTValidation(activity6Layout, totalT);
                                        break;
                                    case 7:
                                        totalT=totalT + activityLayoutTValidation(activity7Layout, totalT);
                                        break;
                                    case 8:
                                        totalT=totalT + activityLayoutTValidation(activity8Layout, totalT);
                                        break;
                                    case 9:
                                        totalT=totalT + activityLayoutTValidation(activity9Layout, totalT);
                                        break;
                                    case 10:
                                        totalT=totalT + activityLayoutTValidation(activity10Layout, totalT);
                                        break;
                                    case 11:
                                        totalT=totalT + activityLayoutTValidation(activity11Layout, totalT);
                                        break;
                                    case 12:
                                        totalT=totalT + activityLayoutTValidation(activity12Layout, totalT);
                                        break;
                                    case 13:
                                        totalT=totalT + activityLayoutTValidation(activity13Layout, totalT);
                                        break;
                                    case 14:
                                        totalT=totalT + activityLayoutTValidation(activity14Layout, totalT);
                                        break;
                                    case 15:
                                        totalT=totalT + activityLayoutTValidation(activity15Layout, totalT);
                                        break;
                                    case 16:
                                        totalT=totalT + activityLayoutTValidation(activity16Layout, totalT);
                                        break;
                                    case 17:
                                        totalT=totalT + activityLayoutTValidation(activity17Layout, totalT);
                                        break;
                                    case 18:
                                        totalT=totalT + activityLayoutTValidation(activity18Layout, totalT);
                                        break;
                                    case 19:
                                        totalT=totalT + activityLayoutTValidation(activity19Layout, totalT);
                                        break;
                                    case 20:
                                        totalT=totalT + activityLayoutTValidation(activity20Layout, totalT);
                                        break;
                                }
                            }

                            HashMap<String, String> finalTimesMap= new HashMap<>();
                            NumberFormat f = new DecimalFormat("00");
                            for (int z=0; z<=23; z++){
                                String currentHour=f.format(z);
                                for(int w=0; w<=59; w++){
                                    String currentMinute=f.format(w);
                                    String finalTimeStr=currentHour +":" + currentMinute;
                                    String keyVal=currentHour+currentMinute;
                                    finalTimesMap.put(keyVal, finalTimeStr);
                                }
                            }

                            NumberFormat f1 = new DecimalFormat("0000");

                            int x=100; int y=200;
                            if(sleepRadioGroup.getCheckedRadioButtonId()==x){
                                //600 mins is 10 hrs (8 hours for sleeping if yes has been chosen + 2 hrs margin of error. This ME can be changed)
                                remainingT=remainingT-630-totalT;
                                if(remainingT<=60){
                                    new AlertDialog.Builder(CreateAPlan.this)
                                            .setTitle("An error happened while creating your plan.")
                                            .setMessage("It seems that with every information you have entered, you won't be capable to accomplish all of these activities in one day. The total time that it will take you to finish" +
                                                    " all of these activities (factoring out sleep and your leisure times) is " + totalT/60 + " minutes. Make some space for yourself to breath!")
                                            .setNegativeButton("Ok", null)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }else{
                                    //Todo: complete the whole creating a plan algorithm/function in here where we have checked that everything is fine. Remember to also check for all of the activities' time condition (specified in the notedbook)

                                    finalTimesMap.remove("0000");
                                    for (int sleepSet=1320; sleepSet<=1439; sleepSet++){
                                        int sleepSetHr=sleepSet/60; int sleepSetMins=sleepSet%60;  //Here, we turn minutes into hours and minutes format (hh:mm)
                                        String key= f.format(sleepSetHr) + f.format(sleepSetMins);
                                        finalTimesMap.remove(key);
                                    }
                                    for (int sleepSet=0001; sleepSet<=0360; sleepSet++){
                                        int sleepSetHr=sleepSet/60; int sleepSetMins=sleepSet%60;
                                        String key= f.format(sleepSetHr) + f.format(sleepSetMins);
                                        finalTimesMap.remove(key);
                                        //finalTimesMap.remove(f1.format(sleepSet));
                                    }
                                    try{
                                        Log.i("ERROR", "THE VALUES THAT WE TRIED TO DElETE FROM THE HASHMAP WEREN'T DELETED PROPERLY. Hash value: " + finalTimesMap.get("0025"));
                                        Log.i("Hash value 2", finalTimesMap.get("0345"));
                                    }catch (NullPointerException nullException){
                                        Log.i("SUCCESS!!", "Values were deleted properly");
                                    }
                                }
                            }else if(sleepRadioGroup.getCheckedRadioButtonId()==y){
                                String endTime=sleepingEndTime.getText().toString(); String subStr1Hr=endTime.substring(27,29); String subStr1Min=endTime.substring(30,32);
                                String startTime=sleepingStartTime.getText().toString(); String subStr2Hr=startTime.substring(29,31); String subStr2Min=startTime.substring(32,34);
                                Log.i("subStr1Hr", subStr1Hr); Log.i("subStr1Min", subStr1Min);Log.i("subStr2Hr", subStr2Hr); Log.i("subStr2Min", subStr2Min);
                                int sub1Hr=Integer.parseInt(subStr1Hr)*60; int finalEndTime=sub1Hr+ Integer.parseInt(subStr1Min);
                                int sub2Hr=Integer.parseInt(subStr2Hr)*60; int finalStartTime=sub2Hr+ Integer.parseInt(subStr2Min);
                                int T2= Math.abs(finalEndTime)- Math.abs(finalStartTime);
                                remainingT=remainingT-T2-totalT;
                                if(remainingT<=60){
                                    new AlertDialog.Builder(CreateAPlan.this)
                                            .setTitle("An error happened while creating your plan.")
                                            .setMessage("It seems that with every information you have entered, you won't be capable to accomplish all of these activities in one day. The total time that it will take you to finish" +
                                                    " all of these activities (factoring out sleep and your leisure times) is " + totalT/60 + " minutes. Make some space for yourself to breath!")
                                            .setNegativeButton("Ok", null)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }else{
                                    //todo: There is a bit of redunduncy in the first 2 lines here. Work on it later.
                                    //THE SOLUTION FOR THE CONVERSION PROBLEM IS TO TURN THE WHOLE TIME WE GOT INTO MINS (EX: 02:03 WILL BE 63 MINS), AND THEN WE LOOP THROUGH THESE VALUES
                                    // INSIDE OF THE LOOP, WE RETURN THOSE VALUES BACK TO THE FORMAT THAT THEY SHOULD BE AS IN THE SUBSTRS. AFTER THAT, PROCEED WITH THE FUNCTIONS
                                    String subStrCondEnd=Character.toString(subStr1Hr.charAt(0));
                                    String subStrCondStart=Character.toString(subStr2Hr.charAt(0));
                                    //Log.i("Final Start Time check", Integer.toString(finalStartTime));

                                    //subStrCondEnd=="0" is the problem
                                    if(Integer.parseInt(subStrCondEnd)==0 && Integer.parseInt(subStrCondStart)!=0){
                                        Log.i("Final Start Time check", Integer.toString(finalStartTime));
                                        finalTimesMap.remove("0000");
                                        for (int sleepSet=finalStartTime; sleepSet<=1439; sleepSet++){
                                            int sleepSetHr=sleepSet/60; int sleepSetMins=sleepSet%60;  //Here, we turn minutes into hours and minutes format (hh:mm)
                                            String key= f.format(sleepSetHr) + f.format(sleepSetMins);
                                            finalTimesMap.remove(key);
                                        }
                                        for (int sleepSet=0001; sleepSet<=finalEndTime; sleepSet++){
                                            int sleepSetHr=sleepSet/60; int sleepSetMins=sleepSet%60;
                                            String key= f.format(sleepSetHr) + f.format(sleepSetMins);
                                            finalTimesMap.remove(key);
                                        }

                                        try{
                                            Log.i("ERROR", "THE VALUES THAT WE TRIED TO DElETE FROM THE HASHMAP WEREN'T DELETED PROPERLY. Hash value: " + finalTimesMap.get("0925"));
                                            Log.i("Hash value 2", finalTimesMap.get("2145"));
                                        }catch (NullPointerException nullException){
                                            Log.i("SUCCESS!!", "Values were deleted properly");
                                        }
                                    }else{
                                        for (int sleepSet=finalStartTime; sleepSet<=finalEndTime; sleepSet++){
                                            int sleepSetHr=sleepSet/60; int sleepSetMins=sleepSet%60;
                                            String key= f.format(sleepSetHr) + f.format(sleepSetMins);
                                            finalTimesMap.remove(key);
                                        }
                                    }
                                }
                            }

                            noActivities=new ArrayList<>();
                            yesActivities=new ArrayList<>();
                            for(int counter=1; counter<=selectedTaskNum; counter++){
                                switch (counter){
                                    case 1:
                                        if(checkActivity(activity1Layout, finalTimesMap)!=null){
                                            //meaning that this activity is a no activity
                                            noActivities.add(checkActivity(activity1Layout, finalTimesMap));
                                        }else{
                                            //meaning that this activity is a yes activity
                                            yesActivities.add(activity1Layout);
                                        }
                                        break;
                                    case 2:
                                        if(checkActivity(activity2Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity2Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity2Layout); }
                                        break;
                                    case 3:
                                        if(checkActivity(activity3Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity3Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity3Layout); }
                                        break;
                                    case 4:
                                        if(checkActivity(activity4Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity4Layout, finalTimesMap));
                                        }else{yesActivities.add(activity4Layout);}
                                        break;
                                    case 5:
                                        if(checkActivity(activity5Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity5Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity5Layout); }
                                        break;
                                    case 6:
                                        if(checkActivity(activity6Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity6Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity6Layout); }
                                        break;
                                    case 7:
                                        if(checkActivity(activity7Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity7Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity7Layout); }
                                        break;
                                    case 8:
                                        if(checkActivity(activity8Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity8Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity8Layout);}
                                        break;
                                    case 9:
                                        if(checkActivity(activity9Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity9Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity9Layout); }
                                        break;
                                    case 10:
                                        if(checkActivity(activity10Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity10Layout, finalTimesMap));
                                        }else{yesActivities.add(activity10Layout); }
                                        break;
                                    case 11:
                                        if(checkActivity(activity11Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity11Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity11Layout); }
                                        break;
                                    case 12:
                                        if(checkActivity(activity12Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity12Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity12Layout); }
                                        break;
                                    case 13:
                                        if(checkActivity(activity13Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity13Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity13Layout); }
                                        break;
                                    case 14:
                                        if(checkActivity(activity14Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity14Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity14Layout); }
                                        break;
                                    case 15:
                                        if(checkActivity(activity15Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity15Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity15Layout); }
                                        break;
                                    case 16:
                                        if(checkActivity(activity16Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity16Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity16Layout);}
                                        break;
                                    case 17:
                                        if(checkActivity(activity17Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity17Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity17Layout); }
                                        break;
                                    case 18:
                                        if(checkActivity(activity18Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity18Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity18Layout); }
                                        break;
                                    case 19:
                                        if(checkActivity(activity19Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity19Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity19Layout); }
                                        break;
                                    case 20:
                                        if(checkActivity(activity20Layout, finalTimesMap)!=null){
                                            noActivities.add(checkActivity(activity20Layout, finalTimesMap));
                                        }else{ yesActivities.add(activity20Layout); }
                                        break;
                                }
                            }

                            //YES ACTIVITY LOCATION
                            while (yesActivities.size()!=0){
                                for(int z=0; z<yesActivities.size();z++){
                                    LinearLayout currentLayout=yesActivities.get(z);
                                    EditText currentActivityNameEdit=(EditText)currentLayout.getChildAt(1);
                                    EditText currentStartTimeYes=(EditText)currentLayout.getChildAt(4);
                                    EditText currentEndTimeYes=(EditText)currentLayout.getChildAt(5);
                                    String currentActivityName= currentActivityNameEdit.getText().toString();
                                    String endTimeYes=currentEndTimeYes.getText().toString(); String subStr1HrYes=endTimeYes.substring(27,29); String subStr1MinYes=endTimeYes.substring(30,32);
                                    String startTimeYes=currentStartTimeYes.getText().toString(); String subStr2HrYes=startTimeYes.substring(29,31); String subStr2MinYes=startTimeYes.substring(32,34);
                                    Log.i("subStr1HrYes", subStr1HrYes); Log.i("subStr1MinYes", subStr1MinYes);Log.i("subStr2HrYes", subStr2HrYes); Log.i("subStr2MinYes", subStr2MinYes);
                                    int sub1HrYes=Integer.parseInt(subStr1HrYes)*60; int sub2HrYes=Integer.parseInt(subStr2HrYes)*60;
                                    int finalStartTimeYesAct=sub2HrYes+ Integer.parseInt(subStr2MinYes); int finalEndTimeYesAct=sub1HrYes+ Integer.parseInt(subStr1MinYes);
                                    String subStrCondEndYes=subStr1HrYes.substring(1); String subStrCondStartYes=subStr2HrYes.substring(1);
                                    NumberFormat yesForm = new DecimalFormat("00");
                                    if(subStrCondEndYes=="0" && subStrCondStartYes!="0"){
                                        finalTimesMap.remove("0000");
                                        for (int sleepSetYes=finalStartTimeYesAct; sleepSetYes<=1439; sleepSetYes++){
                                            int sleepSetHr=sleepSetYes/60; int sleepSetMins=sleepSetYes%60;  //Here, we turn minutes into hours and minutes format (hh:mm)
                                            String keyYes= f.format(sleepSetHr) + f.format(sleepSetMins);
                                            if(finalTimesMap.get(keyYes)==null){
                                                CollapsedTimeDialog(currentActivityName);
                                            }else {
                                                finalTimesMap.remove(keyYes);
                                                //completion of other operations (like linking that time to the activity) should follow this
                                            }
                                        }
                                        for (int sleepSetYes=0001; sleepSetYes<=finalEndTimeYesAct; sleepSetYes++){
                                            int sleepSetHr=sleepSetYes/60; int sleepSetMins=sleepSetYes%60;
                                            String keyYes= f.format(sleepSetHr) + f.format(sleepSetMins);
                                            if(finalTimesMap.get(keyYes)==null){
                                                CollapsedTimeDialog(currentActivityName);
                                            }else {
                                                finalTimesMap.remove(keyYes);
                                                //completion of other operations (like linking that time to the activity) should follow this
                                            }                                        }
                                    }else{
                                        for (int sleepSetYes=finalStartTimeYesAct; sleepSetYes<=finalEndTimeYesAct; sleepSetYes++){
                                            int sleepSetHr=sleepSetYes/60; int sleepSetMins=sleepSetYes%60;
                                            String keyYes= f.format(sleepSetHr) + f.format(sleepSetMins);
                                            if(finalTimesMap.get(keyYes)==null){
                                                CollapsedTimeDialog(currentActivityName);
                                            }else {
                                                finalTimesMap.remove(keyYes);
                                                //completion of other operations (like linking that time to the activity) should follow this
                                            }                                        }
                                    }

                                    yesActivities.remove(z);
                                }
                            }
                            //===========================END OF YESACTIVITIES=========================1
                            try{
                                Log.i("yes check poo", String.valueOf(finalTimesMap.get("1030")));
                            }catch (NullPointerException e){
                                Log.i("yes check poo", "null");
                            }

                            int highestTotal=0; LinearLayout highestLayoutVal=null;
                            while (noActivities.size()!=0) {
                                highestTotal=0;
                                for (int r = 0; r < noActivities.size(); r++) {
                                    if (extractTotalVal(noActivities.get(r)) > highestTotal) {
                                        highestTotal = extractTotalVal(noActivities.get(r));
                                        highestLayoutVal = noActivities.get(r);
                                    }
                                }
                                noActivities.remove(highestLayoutVal);
                                //Here, we will start the algorithm where we will try to find the most optimal place for the current noActivity(highestLayoutVal)
                                //we should start by if branching checking what type of personality the user chose (night owl or early bird), then we will proceed from there
                                //There are some notes and suggestions on the notebook. Check quadrants first
                                Spinner currentEffortSpin= (Spinner) highestLayoutVal.getChildAt(7);
                                Spinner currentImpSpin= (Spinner) highestLayoutVal.getChildAt(9);
                                int currentEffort=Integer.parseInt(currentEffortSpin.getSelectedItem().toString());
                                int currentImp= Integer.parseInt(currentImpSpin.getSelectedItem().toString());

                                if(currentEffort>=3 && currentImp>=3){ //fourth quadrant in the notebook
                                    //check out the earlybird val, there might be a potential error
                                    int earlyBird=2131296322; int nightOwl=2000;
                                    Log.i("nightowl ID", Integer.toString(personaTypeGroup.getCheckedRadioButtonId()));

                                    if(personaTypeGroup.getCheckedRadioButtonId()==nightOwl){
                                        //here, i should create the arraylist that will have the index ratings  depeding on the persona
                                        //Todo: IMP AFFFF!!!!---- WHEN I AM CHECKING WHETHER THIS INTERVAL IS REMOVED OR NOT, MAKE AN IF BRANCH THAT WILL FIRST DETERMINE WHICH PLAN MODE WAS CHOSEN. BASED ON THAT, ELEMINATE THE CORRECT INDEX (OR CHECK FOR IT) OF THE TIME INTERVAL FROM THE CORRECT ARRAYLIST (WIDE, MODERATE, OR COMPACT ONES)
                                        //Todo: make that branch here. check for the type of mode plan then create the correct index rating arraylist (remember that times will change according to the plan mode, so the rating may differ slightly)
                                        int one=1; int two=2; int three=3;
                                        //generalTopTimesWide.add("1040-1120")-9;generalTopTimesWide.add("1250-1330")-7;generalTopTimesWide.add("1500-1540")-5;generalTopTimesWide.add("1710-1750")-6;
                                        //generalTopTimesWide.add("1920-2000")-7;generalTopTimesWide.add("2130-2210")-9;generalTopTimesWide.add("2340-0020")-10;generalTopTimesWide.add("0150-0230")-10;generalTopTimesWide.add("0400-0440")-7;
                                        //generalTopTimesWide.add("0610-0650")-7;generalTopTimesWide.add("0825-0905")-8;

                                        if (modePlanGroup.getCheckedRadioButtonId()==one){
                                            ArrayList<String> generalTopTimesWideClone= (ArrayList<String>) generalTopTimesWide.clone();
                                            ArrayList<Integer>intervalRatingWideNightClone= (ArrayList<Integer>) intervalRatingWideNight.clone();
                                            //!!!!!!!!!!!!FLAG!!!!: IN THIS LOOP, THE CLONE LISTS (RATING + GENERALTOPTIMES) WILL BE CREATED FROM THEIR MAIN ONES

                                            EditText maxTimeEdit= (EditText) highestLayoutVal.getChildAt(5);
                                            int chosenMaxTime=Integer.parseInt(maxTimeEdit.getText().toString());
                                            Boolean successfulPlacement=false;

                                            if(chosenMaxTime<=40){
                                                while (successfulPlacement!=true){
                                                    Log.i("Success!!!", "the branch has been entered successfuly");
                                                    String currentBestInterval= (String) findTheBestInterval(intervalRatingWideNightClone, generalTopTimesWideClone).get(0);
                                                    int bestIntervalIndex=(Integer) findTheBestInterval(intervalRatingWideNightClone, generalTopTimesWideClone).get(1);
                                                    String startTime=currentBestInterval.substring(0,4); String endTime=currentBestInterval.substring(5);
                                                    Log.i("start time", startTime); Log.i("end time", endTime);

                                                    int currentStartTime = (Integer.parseInt(startTime.substring(0, 2)) * 60) + Integer.parseInt(startTime.substring(2));
                                                    int currentEndTime = (Integer.parseInt(endTime.substring(0, 2)) * 60) + Integer.parseInt(endTime.substring(2));
                                                    Log.i("current start time", Integer.toString(currentStartTime));
                                                    Log.i("current end time", Integer.toString(currentEndTime));
                                                    int unplacedActivityMins = chosenMaxTime;
                                                    int finalStartTime=currentStartTime+20; int finalEndTime=currentEndTime-20;//here, we start at the peak of the interval by taking its middle val
                                                    finalStartTime=finalStartTime - Math.round(chosenMaxTime/2); finalEndTime=finalEndTime +Math.round(chosenMaxTime/2);
                                                    int usedMinsCounter=0; int firstUsedMin=0; int lastUsedMin=0;
                                                    Log.i("final start time", f.format(finalStartTime / 60) + f.format(finalStartTime % 60));
                                                    Log.i("final end time",  f.format(finalEndTime / 60) + f.format(finalEndTime % 60));
                                                    String subStrCondEnd=Character.toString(endTime.charAt(0));
                                                    String subStrCondStart=Character.toString(startTime.charAt(0));
                                                    ArrayList<String>tempList=new ArrayList<>();

                                                    if(Integer.parseInt(subStrCondEnd)==0 && Integer.parseInt(subStrCondStart)!=0){
                                                        //no activity in case the interval is between an hour before midnight and after midnight (like- start: 11:40 PM  end: 12:20 AM)
                                                        Log.i("Current interval", currentBestInterval);
                                                        for (int sleepSet = finalStartTime; sleepSet <= 1139; sleepSet++) {
                                                            Log.i("branching successful", "you've entered the forloop correctly");
                                                            String key = f.format(sleepSet / 60) + f.format(sleepSet % 60);
                                                            //todo: START OF PROCEDURE=================================
                                                            if (finalTimesMap.get(key) == null) {   //meaning that this specific minute is already used up
                                                                usedMinsCounter++;
                                                                if (firstUsedMin == 0) {
                                                                    firstUsedMin = sleepSet;
                                                                }
                                                                lastUsedMin = sleepSet;
                                                            } else {
                                                                tempList.add(key);
                                                                //complete here, there are still some missing pieces

                                                                unplacedActivityMins--;
                                                            }
                                                        }
                                                        for (int sleepSet = 0; sleepSet <= finalEndTime; sleepSet++) {
                                                            Log.i("branching successful", "you've entered the forloop correctly");
                                                            String key = f.format(sleepSet / 60) + f.format(sleepSet % 60);
                                                            if (finalTimesMap.get(key) == null) {   //meaning that this specific minute is already used up
                                                                usedMinsCounter++;
                                                                if (firstUsedMin == 0) {
                                                                    firstUsedMin = sleepSet;
                                                                }
                                                                lastUsedMin = sleepSet;
                                                            } else {
                                                                tempList.add(key);
                                                                //complete here, there are still some missing pieces

                                                                unplacedActivityMins--;
                                                            }
                                                            if (chosenMaxTime <= 30 && usedMinsCounter > 10) {
                                                                successfulPlacement = false;
                                                                //TODO: IMPORTANT AF!!!!!!!!!!!!!  In the next 2 lines where i am removing the values that associate with the current best interval (ratings and the time interval),
                                                                /*i have made some changed since i found out that using the method "list.remove" removes the index of the element, and instead of giving it an index to remove...,I gave it a useless string
                                                                so i changed it into "list.remove (generalList.indexOf(useless string))" to get an index val.
                                                                The previous wrong code before the edit is the following:
                                                                  generalTopTimesWideClone.remove(currentBestInterval);
                                                                intervalRatingWideNightClone.remove(currentBestInterval);

                                                                UPDATE ANY PIECE OF CODE THAT HAS SUCH SIMILAR ERRORS
                                                                */
                                                                intervalRatingWideNightClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                                                                generalTopTimesWideClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                                                                Log.i("interval error", currentBestInterval + " isn't appropriate for this activity, should switch");
                                                                break;
                                                            } else if (chosenMaxTime > 30 && usedMinsCounter > 5) {
                                                                successfulPlacement = false;
                                                                intervalRatingWideNightClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                                                                generalTopTimesWideClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                                                                Log.i("interval error", currentBestInterval + " isn't appropriate for this activity, should switch");
                                                                break;
                                                            }
                                                        }
                                                    }else {
                                                        //opposite of the first if branch's description
                                                        Log.i("Current interval", currentBestInterval);
                                                        for (int sleepSet = finalStartTime; sleepSet <= finalEndTime; sleepSet++) {
                                                            String key = f.format(sleepSet / 60) + f.format(sleepSet % 60);
                                                            if (finalTimesMap.get(key) == null) {   //meaning that this specific minute is already used up
                                                                usedMinsCounter++;
                                                                if (firstUsedMin == 0) {
                                                                    Log.i("choco 2.0",  f.format(sleepSet/60) + f.format(sleepSet%60));
                                                                    firstUsedMin = sleepSet;
                                                                }
                                                                lastUsedMin = sleepSet;
                                                            } else {
                                                                //todo:!!!!!Just a warning, when i logged the size of the tempList that we get from the for loop, it turns out that it got 31 minutes and not 30 (as specified in the activity).It's not a major problem, but keep it in mind in case any future errors might result from it. It's simple to solve
                                                                tempList.add(key);
                                                                Log.i("IMP test 3", Integer.toString(tempList.size()));
                                                                //complete here, there are still some missing pieces

                                                                unplacedActivityMins--;
                                                            }

                                                            if (chosenMaxTime <= 30 && usedMinsCounter > 10) {
                                                                successfulPlacement = false;
                                                                intervalRatingWideNightClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                                                                generalTopTimesWideClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                                                                Log.i("interval error", currentBestInterval + " isn't appropriate for this activity, should switch");
                                                                break;
                                                            } else if (chosenMaxTime > 30 && usedMinsCounter > 5) {
                                                                successfulPlacement = false;
                                                                intervalRatingWideNightClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                                                                generalTopTimesWideClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                                                                Log.i("interval error", currentBestInterval + " isn't appropriate for this activity, should switch");
                                                                break;
                                                            }
                                                        }
                                                        Log.i("usedMinsCounter", String.valueOf(usedMinsCounter));
                                                        Log.i("choco firstUsedMin", f.format(firstUsedMin/60) + f.format(firstUsedMin%60));
                                                        try{
                                                            Log.i("potato test", String.valueOf(finalTimesMap.get("0150")));
                                                        }catch (NullPointerException e){
                                                            Log.i("potato test", "returned val was null");
                                                        }
                                                    }
                                                    //THIS IS WHERE WE FINISH UP THE REST IF THERE WERE ANY USED UP TIMES INSIDE OF AN INTERVAL
                                                    //Using the firstUsed min and the lastUsedMin vars, I can find out how long the range of the used times is (and where is the best place to add these extra mins -i.e end or beginning of the interval.)
                                                    //create the second loop for extra mins. Remember that we won't have extra mins everytime.
                                                    String newIntervalStart; String newIntervalEnd;

                                                    //In the following try and catch method, we're looping through the vals of the tempList (the list tht contains all of the valid values of times that we will remove from main lists and maps). Tha vals are accumulated from the prev branches
                                                    Log.i("tempList size", Integer.toString(tempList.size()));
                                                    try {
                                                        for (int counter = 0; tempList.size() == 0; counter++) {
                                                            Log.i("IMP test 2", tempList.get(counter));
                                                            finalTimesMap.remove(tempList.get(counter));
                                                            tempList.remove(counter);
                                                            //complete here, there are still some missing pieces
                                                        }
                                                    }catch (IndexOutOfBoundsException e){
                                                        Log.i("IMP test 2", "failed");
                                                    }
                                                    try{
                                                        Log.i("IMP test", String.valueOf(finalTimesMap.get("1055")));
                                                    }catch(NullPointerException e){
                                                        Log.i("IMP test", "null");
                                                    }

                                                    if(usedMinsCounter== 0){  // meaning that weren't any used up times
                                                        successfulPlacement=true;
                                                        intervalRatingWideNight.remove(generalTopTimesWide.indexOf(currentBestInterval));
                                                        generalTopTimesWide.remove(currentBestInterval);
                                                        //todo: find if there's anything more to add here
                                                    }else{  //there were used up times
                                                        if(chosenMaxTime <=30 && usedMinsCounter<=10){
                                                            extractAddMinsFuncValsList=addExtraMins(firstUsedMin, finalStartTime, finalEndTime, usedMinsCounter, finalTimesMap, generalTopTimesWideClone,
                                                                    generalTopTimesWide,intervalRatingWideNight ,intervalRatingWideNightClone, currentBestInterval);

                                                            intervalRatingWideNight=(ArrayList<Integer>)extractAddMinsFuncValsList.get(0);
                                                            intervalRatingWideNightClone=(ArrayList<Integer>)extractAddMinsFuncValsList.get(1);
                                                            generalTopTimesWide= (ArrayList<String>) extractAddMinsFuncValsList.get(2);
                                                            //todo:
                                                            // make sure that these global vals (generalTopTimesWide and the intervalRating, etc) are actually updated by the new results thatarefoubnd in this var. I am concerned that the vals only update within this branch then it returns back to the default set of vals outta this branch, which is the opposite of what we need
                                                            generalTopTimesWideClone= (ArrayList<String>) extractAddMinsFuncValsList.get(3);
                                                            successfulPlacement= (Boolean) extractAddMinsFuncValsList.get(4);
                                                            newIntervalStart= (String) extractAddMinsFuncValsList.get(5);
                                                            newIntervalEnd=(String) extractAddMinsFuncValsList.get(6);
                                                            Log.i("new interval start", newIntervalStart);Log.i("new interval end", newIntervalEnd);
                                                            Log.i("IMP val update", String.valueOf(generalTopTimesWide));
                                                            //Todo: I believe we should update the vars/lists that will be manipulated in the above function into this function (test it first before trying this)
                                                            //like: generalTopTimesWideClone= (the val that was update inside of the function, find a way.)
                                                        }else if(chosenMaxTime>30 && usedMinsCounter<=5){
                                                            extractAddMinsFuncValsList=addExtraMins(firstUsedMin, finalStartTime, finalEndTime, usedMinsCounter, finalTimesMap, generalTopTimesWideClone,
                                                                    generalTopTimesWide, intervalRatingWideNight ,intervalRatingWideNightClone, currentBestInterval);

                                                            intervalRatingWideNight=(ArrayList<Integer>)extractAddMinsFuncValsList.get(0);
                                                            intervalRatingWideNightClone=(ArrayList<Integer>)extractAddMinsFuncValsList.get(1);
                                                            generalTopTimesWide= (ArrayList<String>) extractAddMinsFuncValsList.get(2);
                                                            generalTopTimesWideClone= (ArrayList<String>) extractAddMinsFuncValsList.get(3);
                                                            successfulPlacement= (Boolean) extractAddMinsFuncValsList.get(4);
                                                            newIntervalStart= (String) extractAddMinsFuncValsList.get(5);
                                                            newIntervalEnd=(String) extractAddMinsFuncValsList.get(6);
                                                            Log.i("new interval start", newIntervalStart);Log.i("new interval end", newIntervalEnd);
                                                            Log.i("IMP val update", String.valueOf(generalTopTimesWide));
                                                        }
                                                        //todo: i believe that the algorithm has been successfully updated as i wanted where we don't take any permanent removing actions ubnless that we're sure that the current interval is fit for the activity (if problems arised later on when testing it, consider adding an "else" branch to the previous 2 branches to make sure that this interval is fit. then move the place of the try and catch branch to the appropriate locations to avoid errors

                                                    }
                                                    //Todo: apparently there is something wrong with the autofill/save system when i click on the no radio button of the task "main addExtraMins Func trigger". It raised an index out of band error which is weird. Check it out. It did work for the radio yes buttones
                                                    //Todo: IMPORTANT ASFF!!!!!!!!::: After we're finally done checking that this specific interval is suitable, find a way to connect the final allocated time interval for this activity with the activity itself (maybe through a hashMap). MAKE SURE THAT IF THERE IS ANY EXTRA TIME TO THE INTERVAL (like the ones from the addExtraMins Func) TO ADD IT TO THE FINAL INTERVAL OF THE ACTIVITY ITSELF.
                                                }
                                            }else{
                                                //check for the inaddterval + and - the extra time/2  (anything above 40, we divide by 2 since we are talking about 2 sides)
                                                //if there are decimals: either convert them to seconds (a lot of work), or just round them (won't have a big effect)
                                            }
                                        }else if (modePlanGroup.getCheckedRadioButtonId()==two){

                                        }else if (modePlanGroup.getCheckedRadioButtonId()==three){

                                        }

                                    }else if(personaTypeGroup.getCheckedRadioButtonId()==earlyBird){

                                    }

                                }else if(currentEffort<=2 && currentImp<=2){  //first quadrant

                                    if(personaTypeGroup.getCheckedRadioButtonId()==nightOwl){

                                    }else if(personaTypeGroup.getCheckedRadioButtonId()==earlyBird){

                                    }

                                }else if(currentEffort>=3 && currentImp<=2){  //second quadrant

                                    if(personaTypeGroup.getCheckedRadioButtonId()==nightOwl){

                                    }else if(personaTypeGroup.getCheckedRadioButtonId()==earlyBird){

                                    }

                                }else if(currentEffort<=2 && currentImp>=3){  //third quadrant

                                    if(personaTypeGroup.getCheckedRadioButtonId()==nightOwl){

                                    }else if(personaTypeGroup.getCheckedRadioButtonId()==earlyBird){

                                    }

                                }
                            }
                            try {
                                Log.i("final test imp", String.valueOf(finalTimesMap.get("1100")));
                            }catch (NullPointerException e){
                                Log.i("final test imp", "null, success");
                            }

                        }else{
                            new AlertDialog.Builder(CreateAPlan.this)
                                    .setTitle("An error happened while creating your/ plan")
                                    .setMessage("Please make sure that all of the fields are completed correctly in order to create your plan.")
                                    .setNegativeButton("Ok", null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }
                });

                blankTxtView=new TextView(CreateAPlan.this); blankTxtView.setVisibility(View.INVISIBLE); blankTxtView.setClickable(false);
                LinearLayout.LayoutParams blankTxtViewParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                blankTxtViewParams.topMargin=convertDpToPx(CreateAPlan.this, (float)50); blankTxtView.setLayoutParams(blankTxtViewParams);
                endingLayout.addView(sleepTimeLayout);
                endingLayout.addView(planModeLayout); endingLayout.addView(cancelThePlanBtn); endingLayout.addView(createThePlanBtn); endingLayout.addView(blankTxtView);
                mainLayout2.addView(endingLayout);

            }
        });
    }



    public ArrayList<Object> addExtraMins(int firstUsedMin, int finalStartTime, int finalEndTime, int usedMinsCounter, HashMap finalTimesMap,ArrayList<String> generalTopTimesWideClone,  ArrayList<String> generalTopTimesWide,
                                ArrayList<Integer> intervalRatingWideNight,ArrayList<Integer> intervalRatingWideNightClone,String currentBestInterval ){
        NumberFormat numFor = new DecimalFormat("00");
        ArrayList<Object>finalResultsList = new ArrayList<>();
        Boolean placementStatus=true;
        ArrayList<String>tempList=new ArrayList<>();
        int newIntervalStartInt;
        int newIntervalEndInt;
        String newIntervalStart="";
        String newIntervalEnd="";
        Log.i("FUNCTION TRIGGERED", "the addExtraMins function is triggered successfully ");

        Log.i("firstUsed/endTime/start", numFor.format(firstUsedMin/60) + numFor.format(firstUsedMin%60) + " " +
                numFor.format(finalEndTime/60) + numFor.format(finalEndTime%60)+ numFor.format(finalStartTime/60) + numFor.format(finalStartTime%60));
        Log.i("firstUsed-endTime=", String.valueOf(firstUsedMin-finalEndTime));
        //Todo: execute the same method of elemination that i did here in the main body where i call this function (the place where i remove the values while i am looping through the interval, before adding the extra mins)
        if (firstUsedMin-finalStartTime<=5){   //USED TIMES WERE AT THE BEGGING, SO WE ADD THEM TO BEYOND THE END
            Log.i("poopies", String.valueOf(finalTimesMap));
            newIntervalStartInt=firstUsedMin+1;
            newIntervalStart= numFor.format(newIntervalStartInt/60) + numFor.format(newIntervalStartInt%60);
            for (int extraMins=finalEndTime+1; extraMins<=finalEndTime+usedMinsCounter; extraMins++){
                String key = numFor.format(extraMins/60) + numFor.format(extraMins%60);

                if(extraMins==finalEndTime+usedMinsCounter){
                    newIntervalEnd=key;
                }

                if(finalTimesMap.get(key)==null){   //meaning that this specific minute is already used up
                    intervalRatingWideNightClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                    generalTopTimesWideClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                    placementStatus=false;
                    break;
                }else{
                    tempList.add(key);
                }
            }
            //Todo: IMPORTANT ASF!!!!!!---- instead of removing the key val from the generalTopTimesWide and the finalTimeMap permanently right away from the above loop (check the next comment)
            //without even checking whether the interval is a suitable one or not, we created a temporary list that will contain these vals (that will be removed later on). After we checked that the interval is suitable
            //for this activity, we go on (in the end before returning the main function val) to loop through the vals of the tempList one by one so we
            //can remove them permanently from the original lists. CONSIDER EDITING ALL OF THE SIMILAR FUNCTIONS IN THIS PROGRAM TO DO THIS SAME MOVE TO AVOID ERRORS
            //THIS FUNCTION NEEDS TO BE TESTED AND CONFIREMD FIRST BEFORE INSTALLING IT INTO THE OTHER FUNCTIONS!!!!
        }else if (firstUsedMin-finalEndTime>=-10){  //USED TIMES WERE AT THE END, SO WE ADD THEM TO BEFORE THE BEGINNING
            newIntervalEndInt=firstUsedMin-1;
            newIntervalEnd= numFor.format(newIntervalEndInt/60) + numFor.format(newIntervalEndInt%60);
            for (int extraMins=finalStartTime-1; extraMins>=finalStartTime-usedMinsCounter; extraMins--){
                String key = numFor.format(extraMins/60) + numFor.format(extraMins%60);
                if(extraMins==finalStartTime-usedMinsCounter){
                    newIntervalEnd=key;
                }
                if(finalTimesMap.get(key)==null){   //meaning that this specific minute is already used up
                    intervalRatingWideNightClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                    generalTopTimesWideClone.remove(generalTopTimesWideClone.indexOf(currentBestInterval));
                    placementStatus=false;
                    break;
                }else{
                    tempList.add(key);
                    //Todo:LATEST UPDATE--- THE REASON WHY I KEPT GETTING THIS ERROR THAT SAYS THAT temmpList has a size of 0 therefore i get an indexOutOfBoundException is because i was rubnning the whole experiment in a wrong way that would cause not filling the tempList. This means that there was nothing wrong with its application so far
                    //
                    //complete here, there are still some missing pieces


                }
            }
        }
        Log.i("TEMP LIST VAL", tempList.get(0));
        try {
            for (int counter = 0; tempList.size() == 0; counter++) {
                finalTimesMap.remove(tempList.get(counter));
                tempList.remove(counter);
                placementStatus = true;
                //complete here, there are still some missing pieces
            }
            intervalRatingWideNight.remove(generalTopTimesWide.indexOf(currentBestInterval));
            generalTopTimesWide.remove(currentBestInterval);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        Log.i("Interval rating/Clone ", String.valueOf(intervalRatingWideNight) + String.valueOf(intervalRatingWideNightClone));
        finalResultsList.add(intervalRatingWideNight);finalResultsList.add(intervalRatingWideNightClone);
        finalResultsList.add(generalTopTimesWide);finalResultsList.add(generalTopTimesWideClone);finalResultsList.add(placementStatus);
        finalResultsList.add(newIntervalStart);finalResultsList.add(newIntervalEnd);
        return finalResultsList;
    }

    public void CollapsedTimeDialog(String currentActivityName){
        new AlertDialog.Builder(CreateAPlan.this)
                .setTitle("An error happened while creating your plan.")
                .setMessage("Hmmmmmm...It seems that " + currentActivityName + "'s time is already taken by another activity's time (an activity that has a fixed time) or by your sleeping time. Please make sure that each activity gets its own specific time.")
                .setNegativeButton("Ok", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public int extractTotalVal(LinearLayout currentLayout){
        Spinner currentEffortSpin= (Spinner) currentLayout.getChildAt(7);
        Spinner currentImpSpin= (Spinner) currentLayout.getChildAt(9);
        int currentEffort= Integer.parseInt(currentEffortSpin.getSelectedItem().toString());
        int currentImp=Integer.parseInt(currentImpSpin.getSelectedItem().toString());
        return currentEffort+currentImp;
    }

    public LinearLayout checkActivity(LinearLayout currentLayout, HashMap<String, String> finalTimesMap){
        //checking the type of activity (yes/no?)
        radioGroup1=(RadioGroup)currentLayout.getChildAt(3);
        LinearLayout currentNoLayout=null;

        if((radioGroup1.getCheckedRadioButtonId())%2 == 0) { //meaning that this is a no activity
            currentNoLayout = currentLayout;
        }

        /*else {
            EditText currentTInput=(EditText)currentLayout.getChildAt(5);
            int T= Integer.parseInt(currentTInput.getText().toString());
            totalT= totalT+T;
        }  */
        return currentNoLayout;
    }

    public ArrayList<Object> findTheBestInterval(ArrayList<Integer> intervalRating, ArrayList<String> generalTopTimesWideClone){
        ArrayList<Object>bestIntervalResults=new ArrayList<>();
        Log.i("INTERVAL RATING FRESH", String.valueOf(intervalRating));
        int maxRating= Collections.max(intervalRating, null); int maxRatingIndex=intervalRating.indexOf(maxRating);
        String currentBestInterval=generalTopTimesWideClone.get(maxRatingIndex);
        Log.i("INTV MAX RATING FRESH", Integer.toString(maxRating));
        bestIntervalResults.add(currentBestInterval); bestIntervalResults.add(maxRatingIndex);
        return bestIntervalResults;
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

    public void returnGlobalBooleanDate(){
        validDateIsPicked=true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int realMonth=month+1;
        String date="month/day/year: "+ realMonth  + "/" +dayOfMonth + "/" +year;
        dateTextView.setText(date);
        returnGlobalBooleanDate();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text= (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


/*
                                                //check for the interval only
                                                while (successfulPlacement!=true) {
                                                    String currentBestInterval=findTheBestInterval(intervalRating, generalTopTimesWideClone);
                                                    String startTime=currentBestInterval.substring(0,4); String endTime=currentBestInterval.substring(5);

                                                    int finalStartTime = (Integer.parseInt(startTime.substring(0, 2)) * 60) + Integer.parseInt(startTime.substring(2));  // Todo: maybe a potential error (not tested yet)
                                                    int finalEndTime = (Integer.parseInt(endTime.substring(0, 2)) * 60) + Integer.parseInt(endTime.substring(2));
                                                    int usedMinsCounter = 0;
                                                    int remainingIntervalTime = 40;
                                                    int unplacedActivityMins = chosenMaxTime;
                                                    for (int sleepSet = finalStartTime; sleepSet <= finalEndTime; sleepSet++) {
                                                        int sleepSetHr = sleepSet / 60;
                                                        int sleepSetMins = sleepSet % 60;
                                                        String key = f.format(sleepSetHr) + f.format(sleepSetMins);
                                                        if (finalTimesMap.get(key) == null) {  // if it returns null, that means that is it already used. THE THRESHOLD THAT WILL MAKE US REJECT THE CURRENT INTERVAL IS IF 10 MINS FROM THE INTERVAL (MAX) ARE USED UP
                                                            usedMinsCounter++;
                                                            remainingIntervalTime--;
                                                            //finalEndTime++; //Todo: Check whether we can update a for loop control var from within the loop itself (through testing or research)
                                                        }else {   //means that this time isn't used up yet
                                                            finalTimesMap.remove(key);

                                                            unplacedActivityMins--;
                                                            remainingIntervalTime--;
                                                        }
                                                        //WARNING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!: THE FOLLOWING BRANCH IS STILL UNDER REVIEW, NOT PROVED TO WORK AS WE WANT
                                                        if (remainingIntervalTime - unplacedActivityMins < -5) {  //here, we're making sure that no more than 5 mins in the interval are used up. If that's the case, we brake out of the loop and look for the next best fitting interval
                                                            successfulPlacement=false;
                                                            //int tempIndex=generalTopTimesWide.indexOf(currentBestInterval);
                                                            //generalTopTimesWideClone.remove(tempIndex); intervalRating.remove(tempIndex);
                                                            //find a way to break out of the for loop
                                                        }
                                                    }
                                                    //ADD THE EXTRA FOR LOOP THAT WILL TAKE CARE OF THE EXTRA TIME USING THE 3 VARS I TALKED ABOUT IN THE COMPYBOOK (MAX 5 MINS HERE)
                                                    for (){

                                                    }

                                                    //successfulPlacement=true;
                                                    //int tempIndex=generalTopTimesWide.indexOf(currentBestInterval);
                                                    //intervalRating.remove(tempIndex); generalTopTimes.remove(tempIndex
                                                    //generalTopTimesWideClone.remove(tempIndex);
                                                }

                                            */



/* MAKING A LIST OF ALL POSSIBLE TIMES FOR THE ALGORITHM!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

public class MyClass {


    public static double getRndmIntRange(int min, int max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    public String randomKeyGenerator(){
        String randomKey=alphabet.get(getRndmIntRange(0,25))) + alphabet.get(getRndmIntRange(0,25)))+alphabet.get(getRndmIntRange(0,25)))+alphabet.get(getRndmIntRange(0,25)))+alphabet.get(getRndmIntRange(0,25)));
        return randomKey;
    }

    public static void main(String args[]) {
      ArrayList<String> alphabet=new ArrayList();
      ArrayList.add("A"); ArrayList.add("B"); ArrayList.add("C"); ArrayList.add("D"); ArrayList.add("E"); ArrayList.add("F");ArrayList.add("G"); ArrayList.add("H"); ArrayList.add("I"); ArrayList.add("J"); ArrayList.add("K"); ArrayList.add("L"); ArrayList.add("M"); ArrayList.add("N"); ArrayList.add("O"); ArrayList.add("P"); ArrayList.add("Q"); ArrayList.add("R"); ArrayList.add("S");ArrayList.add("T");ArrayList.add("U");ArrayList.add("V");ArrayList.add("W");ArrayList.add("X");ArrayList.add("Y");ArrayList.add("Z");
      HashMap <Integer, String> finalTimesMap=new HashMap<Integer, String>();
      NumberFormat f = new DecimalFormat("00");
      for (int x=0; x<=23; x++){
          String currentHour=Integer.toString(f.format(x));
          for(int y=0; y<=59; y++){
              String currentMinute=Integer.toString(f.format(y));
              String finalTimeStr=currentHour +":" + currentMinute;
              finalTimesMap.put(randomKeyGenerator(), finalTimeStr);
          }
      }
    }
}

*/









/*
                }else if(noCounter>=1 && yesCounter<noCounter-1){
                    linearLayout.removeView(maxTimeTxtView1); linearLayout.removeView(maxTimeInMinsActivity1); linearLayout.removeView(effortTextView1);
                    linearLayout.removeView(effortScale1); linearLayout.removeView(importanceTextView1); linearLayout.removeView(importanceScale1);
                } */
/*
findViewById(R.id.selectDateBtn).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        showDatePickerDialog();
        }
        });
        activity1StartTime=new EditText(CreateAPlan.this); activity1EndTime=new EditText(CreateAPlan.this);
        maxTimeInMinsActivity1=new EditText(CreateAPlan.this); maxTimeTxtView1=new TextView(CreateAPlan.this);
        effortTextView1=new TextView(CreateAPlan.this); effortScale1=new Spinner(CreateAPlan.this);
        importanceTextView1=new TextView(CreateAPlan.this); importanceScale1=new Spinner(CreateAPlan.this);
        deleteBtn=new Button(CreateAPlan.this);

        activity1YesRadio=findViewById(R.id.activity1YesRadio);
        activity1NoRadio=findViewById(R.id.activity1NoRadio);
        yesCounter=0; noCounter=0;
        activity1YesRadio.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v){
        activity1Layout=findViewById(R.id.activity1Layout);
        activity1StartTime.setHint("Long tap to select the starting time...");
        activity1StartTime.setHeight(convertDpToPx(CreateAPlan.this, (float) 35));
        activity1StartTime.setWidth(convertDpToPx(CreateAPlan.this, (float) 300));
        activity1StartTime.setTextSize(14);
        activity1StartTime.setEms(10);
        //ADD THE PADDING OR MARGING START IF NECESSARY
        activity1EndTime.setHint("Long tap to select the ending time...");
        activity1EndTime.setHeight(convertDpToPx(CreateAPlan.this, (float) 50));
        activity1EndTime.setWidth(convertDpToPx(CreateAPlan.this, (float) 300));
        activity1EndTime.setTextSize(14);
        activity1EndTime.setEms(10);

public void onClick(View v) {
        activity1Layout=findViewById(R.id.activity1Layout);
        maxTimeTxtView1.setText("What is the maximum time that this task would require?"); maxTimeTxtView1.setTextSize(16);
        LinearLayout.LayoutParams maxTimeTextViewParams=new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)350), ViewGroup.LayoutParams.WRAP_CONTENT);
        maxTimeTextViewParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)30); maxTimeTextViewParams.rightMargin=convertDpToPx(CreateAPlan.this, (float)30);
        maxTimeTextViewParams.topMargin=convertDpToPx(CreateAPlan.this, (float) 4);
        maxTimeTxtView1.setLayoutParams(maxTimeTextViewParams);
        maxTimeTxtView1.setTextColor(Color.parseColor("#000000"));
        //editText answer to first question maxTimeInMinsActivity1
        LinearLayout.LayoutParams maxTimeEditTextparams=new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)270), ViewGroup.LayoutParams.WRAP_CONTENT);
        maxTimeEditTextparams.leftMargin=convertDpToPx(CreateAPlan.this, (float)70);
        maxTimeInMinsActivity1.setLayoutParams(maxTimeEditTextparams);
        maxTimeInMinsActivity1.setHint("Type in the time in minutes...");
        maxTimeInMinsActivity1.setTextColor(Color.parseColor("#000000"));
        maxTimeInMinsActivity1.setInputType(InputType.TYPE_CLASS_NUMBER);
        //TxtView how much effort question
        effortTextView1.setText("How much effort would this activity require on a scale from 1( not much effort) to 5 (a lot of effort)?");
        effortTextView1.setTextColor(Color.parseColor("#000000")); effortTextView1.setTextSize(16);
        LinearLayout.LayoutParams effortTextViewQParams= new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)350), ViewGroup.LayoutParams.WRAP_CONTENT);
        effortTextViewQParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)30); effortTextViewQParams.rightMargin=convertDpToPx(CreateAPlan.this, (float)30);
        effortTextViewQParams.topMargin=convertDpToPx(CreateAPlan.this, (float) 4); effortTextView1.setLayoutParams(effortTextViewQParams);
        //DROP DOWN LIST FOR THE EFFORT SCALE
        LinearLayout.LayoutParams effortScaleParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        effortScaleParams.topMargin=convertDpToPx(CreateAPlan.this, (float)6); effortScaleParams.leftMargin=convertDpToPx(CreateAPlan.this, 175);
        effortScale1.setLayoutParams(effortScaleParams);
        ArrayAdapter<CharSequence> effortAdapter1=ArrayAdapter.createFromResource(CreateAPlan.this, R.array.effortScaleNums, android.R.layout.simple_spinner_item);
        effortAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        effortScale1.setAdapter(effortAdapter1); effortScale1.setOnItemSelectedListener(CreateAPlan.this);
        //HOW IMPORTANT IS THE ACTIVITY TO THE USER QUESTION TEXTVIEW
        importanceTextView1.setText("How important is this activity to you on a scale from 1(least important) to 5 (most important)?");
        importanceTextView1.setTextColor(Color.parseColor("#000000")); importanceTextView1.setTextSize(16);
        LinearLayout.LayoutParams importanceTextViewQParams= new LinearLayout.LayoutParams(convertDpToPx(CreateAPlan.this, (float)350), ViewGroup.LayoutParams.WRAP_CONTENT);
        importanceTextViewQParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)30); importanceTextViewQParams.rightMargin=convertDpToPx(CreateAPlan.this, (float)30);
        importanceTextViewQParams.topMargin=convertDpToPx(CreateAPlan.this, (float) 7); importanceTextView1.setLayoutParams(importanceTextViewQParams);
        //DROP DOWN LIST FOR THE IMPORTANCE SCALE
        LinearLayout.LayoutParams importanceScaleParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        importanceScaleParams.topMargin=convertDpToPx(CreateAPlan.this, (float)8); importanceScaleParams.leftMargin=convertDpToPx(CreateAPlan.this, 175);
        importanceScale1.setLayoutParams(importanceScaleParams);
        ArrayAdapter<CharSequence> importanceAdapter1=ArrayAdapter.createFromResource(CreateAPlan.this, R.array.effortScaleNums, android.R.layout.simple_spinner_item);
        importanceAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        importanceScale1.setAdapter(importanceAdapter1); importanceScale1.setOnItemSelectedListener(CreateAPlan.this);
        //DELETE BUTTON
        LinearLayout.LayoutParams deleteBtnParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, convertDpToPx(CreateAPlan.this, (float)65));
        deleteBtnParams.leftMargin=convertDpToPx(CreateAPlan.this, (float)130); deleteBtnParams.topMargin=convertDpToPx(CreateAPlan.this, (float)7);
        deleteBtn.setLayoutParams(deleteBtnParams); deleteBtn.setBackgroundColor(Color.parseColor("#000000"));
        deleteBtn.setBackgroundResource(R.drawable.custom_delete_button);deleteBtn.setBackground(getDrawable(R.drawable.ic_delete_img));
        //-----------------------------------
        if(yesCounter==0) {
        activity1Layout.addView(maxTimeTxtView1); activity1Layout.addView(maxTimeInMinsActivity1); activity1Layout.addView(effortTextView1);
        activity1Layout.addView(effortScale1); activity1Layout.addView(importanceTextView1); activity1Layout.addView(importanceScale1);
        activity1Layout.addView(deleteBtn);
        }else {
        activity1Layout.removeView(activity1StartTime); activity1Layout.removeView(activity1EndTime);
        activity1Layout.addView(maxTimeTxtView1); activity1Layout.addView(maxTimeInMinsActivity1); activity1Layout.addView(effortTextView1);
        activity1Layout.addView(effortScale1); activity1Layout.addView(importanceTextView1); activity1Layout.addView(importanceScale1);
        activity1Layout.addView(deleteBtn);
        }
        noCounter=noCounter+1;
        if(noCounter>=1 && yesCounter<noCounter-1){
        activity1Layout.removeView(maxTimeTxtView1); activity1Layout.removeView(maxTimeInMinsActivity1); activity1Layout.removeView(effortTextView1);
        activity1Layout.removeView(effortScale1); activity1Layout.removeView(importanceTextView1); activity1Layout.removeView(importanceScale1);
        activity1Layout.removeView(deleteBtn);
        }
        //-------------------------------------
        }
        });
        */