package com.example.android.sunshine2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container,new DetailFragment()).commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

            if (id == R.id.action_settings) {//settings button is called
                Intent  intent = new Intent(this,SettingsActivity.class);
                this .startActivity(intent);// initiates doInBackground sending the pin
                return true;
            }
        return super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends android.support.v4.app.Fragment {

        private static final String FORECAST_SHARE_HASHTAG = "#SunshineApp";
        private String mForecastStr;
        public DetailFragment() {
            setHasOptionsMenu(true);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {



            Intent intent = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)
                    ) {

                mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);

                //System.out.println(forecastStr);
                        ((TextView) rootView.findViewById(R.id.detail_text)).setText(mForecastStr);
                Log.d("App", mForecastStr);
            }

            return rootView;
        }
        @Override
               public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                    // Inflate the menu; this adds items to the action bar if it is present.
                   inflater.inflate(R.menu.detail, menu);

                    // Retrieve the share menu item
                    MenuItem menuItem = menu.findItem(R.id.action_share);

                   // Get the provider and hold onto it to set/change the share intent.
                   ShareActionProvider mShareActionProvider =
                                       (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

                       // Attach an intent to this ShareActionProvider.  You can update this at any time,
                        // like when the user selects a new piece of data they might like to share.
                        if (mShareActionProvider != null ) {
                          mShareActionProvider.setShareIntent(createShareForecastIntent());
                       } else {
                           Log.d("SHARE", "Share Action Provider is null?");
                       }
                   }
        private Intent createShareForecastIntent()
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,mForecastStr+FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement

            if (id == R.id.action_share) {//settings button is called
                Intent  intent = createShareForecastIntent();
                this.startActivity(intent);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

    }

}
