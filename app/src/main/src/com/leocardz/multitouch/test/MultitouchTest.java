package com.leocardz.multitouch.test;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

@SuppressLint("NewApi")
public class MultitouchTest extends ActionBarActivity {
    static int screenHeight, screenWidth, screenDensity;
    int androidVersion = Build.VERSION.SDK_INT;
    static boolean lines = true, rings = false, colorChanging = false, numberShowing = true,
            coordinates = false, density = true, vibration = false, draw = false;
    static String densityText, centerMessage, currentTouches;
    public static String APP_SHARED_PREFS = "com.leocardz.multitouch.test.Preferences";
    public static SharedPreferences preferences, settings, getPreference;
    public static Editor editor;
    public static ActionBar ab;
    private MultiTouch mv;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
/* Aningsk remove this if-else 2016-01-04.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
*/
        setMetrics();

        centerMessage = getString(R.string.center_message);
        densityText = getString(R.string.density_text);
        currentTouches = getString(R.string.current_touches);

        setContentView(R.layout.main);

        ab = getSupportActionBar();

        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.app_name);

        mv = new MultiTouch(this);
        setContentView(mv);

    }

    private void setMetrics() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            screenHeight = metrics.heightPixels;
            screenWidth = metrics.widthPixels;
        } else {
            screenHeight = metrics.heightPixels;
            screenWidth = metrics.widthPixels;
        }

        screenDensity = metrics.densityDpi;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (lines)
            menu.findItem(R.id.lines).setChecked(true);

        if (draw)
        	menu.findItem(R.id.draw).setChecked(true);

        if (numberShowing)
            menu.findItem(R.id.number_showing).setChecked(true);

        if (coordinates)
            menu.findItem(R.id.coordinates).setChecked(true);
        
        if (rings)
            menu.findItem(R.id.rings).setChecked(true);
        
        if (density)
            menu.findItem(R.id.density_menu).setChecked(true);

        if (colorChanging)
            menu.findItem(R.id.color_changing).setChecked(true);
        
        if (vibration)
            menu.findItem(R.id.vibration).setChecked(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.lines:
                if (!item.isChecked())
                    lines = true;
                else
                    lines = false;
                item.setChecked(!item.isChecked());
                break;
            case R.id.coordinates:
                if (!item.isChecked())
                    coordinates = true;
                else
                    coordinates = false;
                item.setChecked(!item.isChecked());
                break;
            case R.id.number_showing:
                if (!item.isChecked())
                    numberShowing = true;
                else
                    numberShowing = false;
                item.setChecked(!item.isChecked());
                break;
            case R.id.density_menu:
                if (!item.isChecked())
                    density = true;
                else
                    density = false;
                mv.invalidate();
                item.setChecked(!item.isChecked());
                break;
            case R.id.color_changing:
                if (!item.isChecked())
                    colorChanging = true;
                else
                    colorChanging = false;
                item.setChecked(!item.isChecked());
                break;
            case R.id.vibration:
                if (!item.isChecked())
                    vibration = true;
                else
                    vibration = false;
                item.setChecked(!item.isChecked());
                break;
            case R.id.rings:
                if (!item.isChecked())
                    rings = true;
                else
                    rings = false;
                item.setChecked(!item.isChecked());
                break;
            case R.id.draw:
            	if (!item.isChecked())
            		draw = true;
            	else
            		draw = false;
            	item.setChecked(!item.isChecked());
            	break;
        }
        savePrefs();
        return super.onOptionsItemSelected(item);
    }

    public class DialogSelectionClickHandler implements
            DialogInterface.OnMultiChoiceClickListener {
        public void onClick(DialogInterface dialog, int position,
                            boolean selected) {

        }
    }

    public void redirectTo(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_out_instant,
                R.anim.fade_in_instant);
        finish();
    }

    @Override
    protected void onStop() {
        savePrefs();
        super.onStop();
    }

    @Override
    protected void onPause() {
        savePrefs();
        super.onPause();
    }

    private void savePrefs() {
        settings = getSharedPreferences(APP_SHARED_PREFS, 0);
        editor = settings.edit();

        editor.putBoolean("lines", lines);
        editor.putBoolean("coordinates", coordinates);
        editor.putBoolean("numberShowing", numberShowing);
        editor.putBoolean("density", density);
        editor.putBoolean("vibration", vibration);
        editor.putBoolean("colorChanging", colorChanging);
        editor.putBoolean("rings", rings);
        editor.putBoolean("draw", draw);

        editor.commit();
    }

    @Override
    public void onResume() {
        getPreference = getSharedPreferences(APP_SHARED_PREFS, 0);

        lines = getPreference.getBoolean("lines", lines);
        coordinates = getPreference.getBoolean("coordinates", coordinates);
        numberShowing = getPreference.getBoolean("numberShowing", numberShowing);
        density = getPreference.getBoolean("density", density);
        vibration = getPreference.getBoolean("vibration", vibration);
        colorChanging = getPreference.getBoolean("colorChanging", colorChanging);
        rings = getPreference.getBoolean("rings", rings);
        draw = getPreference.getBoolean("draw", draw);

        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setMetrics();
        mv.invalidate();
        super.onConfigurationChanged(newConfig);
    }
}