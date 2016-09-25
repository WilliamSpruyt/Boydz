package uk.ac.reading.sis05kol.mooc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private static final int MENU_RESUME = 1;
    private static final int MENU_START = 2;
    private static final int MENU_STOP = 3;
    private int georgeNum = 2;
    private int harryNum = 2;
    private GameThread mGameThread;
    private GameView mGameView;
    private String picFile="blank";
    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        mGameView = (GameView) findViewById(R.id.gamearea);
        mGameView.setStatusView((TextView) findViewById(R.id.text));
        mGameView.setScoreView((TextView) findViewById(R.id.score));

        this.startGame(mGameView, null, savedInstanceState);
    }

    private void startGame(GameView gView, GameThread gThread, Bundle savedInstanceState) {
        final ImageView greenbut = (ImageView) findViewById(R.id.greeny);
        final ImageView bluebut = (ImageView) findViewById(R.id.bluey);
        final ImageView redbut = (ImageView) findViewById(R.id.reddy);
        final ImageView pref = (ImageView) findViewById(R.id.taxt);
        //Set up a new game, we don't care about previous states
        mGameThread = new TheGame(mGameView);

        mGameView.setThread(mGameThread);

        mGameThread.setState(GameThread.STATE_READY);
       // mGameThread.setState(georgeNum,harryNum);
        Bundle bundle = getIntent().getExtras();

//Extract the data…
        if (bundle != null) {
            georgeNum = bundle.getInt("george");
            harryNum = bundle.getInt("harry");
            picFile=bundle.getString("pic");
            String stringo = "Yeah HI er..";
            Toast.makeText(this, stringo,
                    Toast.LENGTH_LONG).show();
           // mGameThread.setState(georgeNum,harryNum);
        }

        mGameView.startSensor((SensorManager) getSystemService(Context.SENSOR_SERVICE));
        if (greenbut != null) {
            greenbut.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mGameThread.setMode(0);
                }
            });
        }
        if (redbut != null) {
            redbut.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mGameThread.getMode() != 1) {
                        mGameThread.setMode(1);
                    } else {
                        mGameThread.setMode(1);
                    }
                }
            });
        }
        if (bluebut != null) {
            bluebut.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mGameThread.setMode(2);
                }
            });
        }
        if (pref != null) {
            pref.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    displayInfo(v);
                }
            });
        }

    }

	/*
     * Activity state functions
	 */

    @Override
    protected void onPause() {
        super.onPause();

       // if (mGameThread.getMode() == GameThread.STATE_RUNNING) {
       //     mGameThread.setState(GameThread.STATE_PAUSE);
       // }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mGameView.cleanup();
        mGameView.removeSensor((SensorManager) getSystemService(Context.SENSOR_SERVICE));
        mGameThread = null;
        mGameView = null;
    }
    
    /*
     * UI Functions
     */

    @Override
    protected void onStart() {
        super.onStart();
        //mGameThread.setState(georgeNum,harryNum);
        Bundle bundle = getIntent().getExtras();

//Extract the data…
        if (bundle != null) {
            georgeNum = bundle.getInt("george");
            harryNum = bundle.getInt("harry");
            picFile= bundle.getString("pic");
            String stringo = "Tap Screen to Populate it";
            Toast.makeText(this, stringo,
                    Toast.LENGTH_LONG).show();
            mGameThread.setState(georgeNum,harryNum);
            mGameThread.setScore(picFile);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, MENU_START, 0, R.string.menu_start);
        menu.add(0, MENU_STOP, 0, R.string.menu_stop);
        menu.add(0, MENU_RESUME, 0, R.string.menu_resume);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_START:
                mGameThread.doStart();
                return true;
            case MENU_STOP:
                mGameThread.setState(GameThread.STATE_LOSE, getText(R.string.message_stopped));
                return true;
            case MENU_RESUME:
                mGameThread.unpause();
                return true;
        }

        return false;
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // Do nothing if nothing is selected
    }

    public void displayInfo(View view) {


        Intent i = new Intent(getApplicationContext(), ActivityPreferences.class);

        Bundle bundle = new Bundle();

//Add your data to bundle
        // bundle.putStringArray("Stuff", infoString);
        // bundle.putIntArray("pics",pics);
        //bundle.putSerializable("stuff", set1);


//Add the bundle to the intent
        // i.putExtras(bundle);


        startActivity(i);

    }


}


// This file is part of the course "Begin Programming: Build your first mobile game" from futurelearn.com
// Copyright: University of Reading and Karsten Lundqvist
// It is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// It is is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// 
// You should have received a copy of the GNU General Public License
// along with it.  If not, see <http://www.gnu.org/licenses/>.
