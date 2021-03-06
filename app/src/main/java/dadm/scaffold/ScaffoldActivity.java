package dadm.scaffold;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import dadm.scaffold.counter.Communicator;
import dadm.scaffold.counter.ConfigFragment;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.counter.MainMenuFragment;
import dadm.scaffold.counter.ResultFragment;
import dadm.scaffold.sound.SoundManager;

public class ScaffoldActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT = "content";
int ship=R.drawable.player_ship_red;
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaffold);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenuFragment(), TAG_FRAGMENT)
                    .commit();
        }
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundManager = new SoundManager(getApplicationContext());
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public void startGame() {
        // Navigate the the game fragment, which makes the start automatically
        Communicator.setScore(0);
        try {
            soundManager.changeMusic(getApplicationContext().getAssets().openFd("sfx/battleinthestars_game_music.ogg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        navigateToFragment( new GameFragment());
    }
    public void goMainMenu(){

        try {
            soundManager.changeMusic(getApplicationContext().getAssets().openFd("sfx/skyfire_title_screen.ogg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        navigateToFragment(new MainMenuFragment());
    }
    public void goResults(){
        try {
            soundManager.changeMusic(getApplicationContext().getAssets().openFd("sfx/bravepilots_end_screen.ogg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        navigateToFragment(new ResultFragment());
    }
    public void goConfig(){
        navigateToFragment(new ConfigFragment());
    }
    private void navigateToFragment(BaseFragment dst) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, dst, TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        final BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null || !fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void navigateBack() {
        // Do a push on the navigation history
        super.onBackPressed();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE);
            }
            else {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

public void setPlayerShip(int ship){this.ship = ship;}
public int getPlayerShip(){return ship;}
}
