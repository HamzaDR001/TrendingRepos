package ga.hamzabenhida.trendingrepos;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


/**
 * ReposActivity
 * Main and only Activity for the application
 * this activity will display two Fragments (Trending & Settings).
 */
public class ReposActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private TrendingFragment trendingFragment;
    private SettingsFragment settingsFragment;


    /**
     * Implementing an OnNavigationItemSelectedListener to switch
     * between fragments.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_trending:
                    fm.popBackStack();
                    ft.commit();
                    return true;
                case R.id.navigation_settings:
                    ft.add(R.id.content, settingsFragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);

        trendingFragment = new TrendingFragment();
        settingsFragment = new SettingsFragment();


        getSupportFragmentManager().beginTransaction().add(R.id.content, trendingFragment).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //this is used to change the default ActionBar to custom made one with centred title
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.centred_title_actionbar_layout);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f7f6f6")));

    }

}
