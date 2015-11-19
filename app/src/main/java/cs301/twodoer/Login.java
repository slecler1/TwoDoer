package cs301.twodoer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    /** Called when the user clicks the Login button */
    public void buttonOnClick(View v) {
        Button button=(Button) v;
        startActivity(new Intent(getApplicationContext(), Home.class));
    }
}
