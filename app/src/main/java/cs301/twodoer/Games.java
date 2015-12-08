package cs301.twodoer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Games extends AppCompatActivity {
    int pointCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        //Retrieves the value of the points counter from the shared preference
        SharedPreferences app_preferences =
                getSharedPreferences("temp", getApplicationContext().MODE_PRIVATE);
        pointCount = app_preferences.getInt("counter", 0);

        //Displays the points counter with the proper value
        TextView pointView = (TextView) findViewById(R.id.editText3);
        pointView.setText(pointCount + "");
    }

    //Makes button launch Breakout
    //This will also include the functionality required to remove points from the counter in the future
    public void buttonOnClick(View v) {
        Button button=(Button) v;
        //Clicking the Breakout button navigates to the Breakout game
        startActivity(new Intent(getApplicationContext(), BreakoutGame.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Creates the options menu for the Games screen
        getMenuInflater().inflate(R.menu.menu_games, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Creates the 2-Do List option in the options menu
            case R.id.action_2_do_list:
                //Clicking the 2-Do List menu option will navigate to the to-do list screen
                startActivity(new Intent(getApplicationContext(), Home.class));
                return true;
            default:
                return false;
        }
    }

}
