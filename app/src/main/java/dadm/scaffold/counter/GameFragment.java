package dadm.scaffold.counter;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameView;
import dadm.scaffold.engine.ParalaxBackground;
import dadm.scaffold.game_UI.AmmoTab;
import dadm.scaffold.game_UI.ScoreTab;
import dadm.scaffold.input.JoystickInputController;
import dadm.scaffold.space.GameController;
import dadm.scaffold.game_UI.GameUI;
import dadm.scaffold.game_UI.PlayerLife;
import dadm.scaffold.space.SpaceShipPlayer;


public class GameFragment extends BaseFragment implements View.OnClickListener {
    private GameEngine theGameEngine;
    Dialog dialog;
    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dialog=new Dialog(this.getActivity());
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
        // score_ingame.setText(String.valueOf(Communicator.getScore()));
        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                //Para evitar que sea llamado múltiples veces,
                //se elimina el listener en cuanto es llamado
                observer.removeOnGlobalLayoutListener(this);
                GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                theGameEngine = new GameEngine(getActivity(), gameView);
                theGameEngine.setSoundManager(getScaffoldActivity().getSoundManager());
                theGameEngine.setTheInputController(new JoystickInputController(getView()));

                theGameEngine.addGameObject(new ParalaxBackground(theGameEngine, R.drawable.purple_bg,0));
                theGameEngine.addGameObject(new ParalaxBackground(theGameEngine, R.drawable.purple_bg,1));

                theGameEngine.addGameObject(new SpaceShipPlayer(theGameEngine,((ScaffoldActivity)getActivity()).getPlayerShip()));
               // theGameEngine.addGameObject(new FramesPerSecondCounter(theGameEngine));
                theGameEngine.addGameObject(new PlayerLife(theGameEngine));
                theGameEngine.addGameObject(new ScoreTab(theGameEngine));
                theGameEngine.addGameObject(new AmmoTab(theGameEngine));
                theGameEngine.addGameObject(new GameUI(theGameEngine));
                theGameEngine.addGameObject(new GameController(theGameEngine));
                theGameEngine.startGame();
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (theGameEngine.isRunning()){
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        theGameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (theGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog() {
        theGameEngine.pauseGame();
        dialog.setContentView(R.layout.alertdialogue_pause);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn_stop=dialog.findViewById(R.id.pause_stop);
        Button btn_resume=dialog.findViewById(R.id.pause_resume);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                theGameEngine.stopGame();
                ((ScaffoldActivity)getActivity()).goMainMenu();
            }
        });
        btn_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                theGameEngine.resumeGame();
            }
        });
        dialog.show();

        //AlertDialog.Builder pause_menu = new AlertDialog.Builder(getActivity());
        //View v = getLayoutInflater().inflate(R.layout.alertdialogue_pause,null);

       /* new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.resumeGame();
                    }
                })
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.stopGame();
                        ((ScaffoldActivity)getActivity()).navigateBack();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        theGameEngine.resumeGame();
                    }
                })
                .create()
                .show();*/

    }

    private void playOrPause() {
        Button button = (Button) getView().findViewById(R.id.btn_play_pause);
        if (theGameEngine.isPaused()) {
            theGameEngine.resumeGame();
            button.setText(R.string.pause);
        }
        else {
            theGameEngine.pauseGame();
            button.setText(R.string.resume);
        }
    }


}
