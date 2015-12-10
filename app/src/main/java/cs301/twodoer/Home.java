package cs301.twodoer;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import cs301.twodoer.TaskContract;
import cs301.twodoer.TaskDBHelper;

public class Home extends AppCompatActivity {
    private TaskDBHelper helper;
   int pointCount;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Retrieves the stored value of the points counter and sets the counter equal to it
        SharedPreferences app_preferences =
                getSharedPreferences("temp", getApplicationContext().MODE_PRIVATE);
        pointCount = app_preferences.getInt("counter", 0);
        updateUI(); //Updates the GUI when the app starts
    }


    //(Used Aldo Ziflaj's "Starting Android Development, Creating a Todo App" tutorial at http://www.sitepoint.com/starting-android-development-creating-todo-app/ to create this)
    private void updateUI() {
        //Retrieves the data from the database
        helper = new TaskDBHelper(Home.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK},
                null, null, null, null, null);

        //Puts the data into the task view layout and adds the data to the GUI
        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.task_view,
                cursor,
                new String[]{TaskContract.Columns.TASK},
                new int[]{R.id.taskTextView},
                0
        );
        //Sets up the list view for the task views
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdapter);

        //Displaysthe points counter with the proper value
        TextView pointView = (TextView) findViewById(R.id.editText3);
        pointView.setText(pointCount + "");
    }

    //Deletes a task from the list when the "Done" button is clicked  (Used Aldo Ziflaj's "Starting Android Development, Creating a Todo App" tutorial at http://www.sitepoint.com/starting-android-development-creating-todo-app/ to create this)
    public void onDoneButtonClick(final View view) {
        //Remove task menu is launched when the "Done" button is clicked
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove task");
        builder.setMessage("Are you sure you want to remove this task?");
        //Executes when the "Ok" button is clicked
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                View v = (View) view.getParent();
                TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
                String task = taskTextView.getText().toString();

                //SQL query to delete the record from the database
                String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                        TaskContract.TABLE,
                        TaskContract.Columns.TASK,
                        task);


                helper = new TaskDBHelper(Home.this);
                SQLiteDatabase sqlDB = helper.getWritableDatabase();
                sqlDB.execSQL(sql);

                //Points counter is incremented by 100
                pointCount += 100;

                //New value of the points counter is stored as a shared preference so that it can be retrieved later
                SharedPreferences app_preferences =
                        getSharedPreferences("temp", getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = app_preferences.edit();
                editor.putInt("counter", pointCount);
                editor.commit();

                //Update UI is called to update the GUI to graphically show the deletion of the task
                updateUI();
            }
        });
        //When the "Cancel" button is clicked, the menu is exited without changing anything
        builder.setNegativeButton("Cancel",null);

        //Displays the Remove Task menu
        builder.create().show();
    }


    //Adds the menu to the action bar in the Home activity   (Used Aldo Ziflaj's "Starting Android Development, Creating a Todo App" tutorial at http://www.sitepoint.com/starting-android-development-creating-todo-app/ to create this)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //Gets the selected item from the menu   (Used Aldo Ziflaj's "Starting Android Development, Creating a Todo App" tutorial at http://www.sitepoint.com/starting-android-development-creating-todo-app/ to create this)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Creates the Add Task item in the menu
            case R.id.action_add_task:
                //Creates the actual Add Task menu
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                LinearLayout lila1= new LinearLayout(this);
                lila1.setOrientation(1); //1 is for vertical orientation

                builder.setTitle("Add a task");

                final EditText inputField = new EditText(this);
                inputField.setHint("What do you want 2-do?");
                lila1.addView(inputField);

                /* Task priority items which have been commented out until priority is worked on again at a later date */
                //final EditText input1 = new EditText(this);
               // input1.setHint("Priority: 1(High) - 3(Low)");
                //lila1.addView(input1);

                builder.setView(lila1);

                //Executes when the "Add Task" button is clicked
                //Creates task in database   (Used Aldo Ziflaj's "Starting Android Development, Creating a Todo App" tutorial at http://www.sitepoint.com/starting-android-development-creating-todo-app/ to create this)
                builder.setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Stores the user's task input into a string
                        String task = inputField.getText().toString();
                        //String priority = input1.getText().toString();
                        Log.d("MainActivity",task);
                        TaskDBHelper helper = new TaskDBHelper(Home.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        //Places the user's task into the appropriate column in the database
                        values.clear();
                        values.put(TaskContract.Columns.TASK, task);

                        db.insertWithOnConflict(TaskContract.TABLE, null, values,
                                SQLiteDatabase.CONFLICT_IGNORE);
                        updateUI(); // Update GUI to show new task
                    }
                });

                //Executes when the "Cancel" button is clicked
                //Exits the menu without changing anything
                builder.setNegativeButton("Cancel",null);

                //Displays the Add Task menu
                builder.create().show();
                return true;
            //Creates the Games item in the menu
            case R.id.action_games:
                //Navigates to the Games page when the Games menu option is clicked
                startActivity(new Intent(getApplicationContext(), Games.class));
                return true;
            default:
                return false;
        }
    }


}

