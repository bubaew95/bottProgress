package pro.webshishan.recyclerbotomprogressbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import pro.webshishan.recyclerbotomprogressbar.activitys.MainActivity;

/**
 * Created by shiShan.inc on 25.03.2017.
 */

public class SplashDisplay extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);

            }
        }, 3000);


    }
}
