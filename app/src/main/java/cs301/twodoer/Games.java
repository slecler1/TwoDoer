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

        SharedPreferences app_preferences =
                getSharedPreferences("temp", getApplicationContext().MODE_PRIVATE);
        pointCount = app_preferences.getInt("counter", 0);

        TextView pointView = (TextView) findViewById(R.id.editText3);
        pointView.setText(pointCount + "");
    }

    //Makes button launch Breakout
    //Edit this so that it also removes points from the counter
    public void buttonOnClick(View v) {
        Button button=(Button) v;
        startActivity(new Intent(getApplicationContext(), BreakoutGame.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_games, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_2_do_list:
                startActivity(new Intent(getApplicationContext(), Home.class));
                return true;
            default:
                return false;
        }
    }

}
