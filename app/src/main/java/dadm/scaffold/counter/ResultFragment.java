package dadm.scaffold.counter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;

public class ResultFragment extends BaseFragment {
    Button play_again,go_start,exit;
    TextView score;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        play_again=rootView.findViewById(R.id.result_play_again);
        go_start=rootView.findViewById(R.id.result_go_start);
        exit=rootView.findViewById(R.id.result_exit);
        score=rootView.findViewById(R.id.result_score);
        score.setText(String.valueOf(Communicator.getScore()));

        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScaffoldActivity)getActivity()).startGame();
            }
        });
        go_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScaffoldActivity)getActivity()).goMainMenu();            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScaffoldActivity)getActivity()).finish();
                System.exit(0);
            }
        });

return rootView;
    }
}
