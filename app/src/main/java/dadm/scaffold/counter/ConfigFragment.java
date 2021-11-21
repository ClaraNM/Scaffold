package dadm.scaffold.counter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;

public class ConfigFragment extends BaseFragment {
    ImageButton ship_red,ship_orange,ship_blue,ship_green;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_config, container, false);
        ship_red=rootView.findViewById(R.id.btn_ship_red);
        ship_orange=rootView.findViewById(R.id.btn_ship_orange);
        ship_blue=rootView.findViewById(R.id.btn_ship_blue);
        ship_green=rootView.findViewById(R.id.btn_ship_green);

        ship_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScaffoldActivity)getActivity()).setPlayerShip(R.drawable.player_ship_red);
                ((ScaffoldActivity)getActivity()).navigateBack();
            }
        });
        ship_orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScaffoldActivity)getActivity()).setPlayerShip(R.drawable.player_ship_orange);
                ((ScaffoldActivity)getActivity()).navigateBack();
            }
        });
        ship_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScaffoldActivity)getActivity()).setPlayerShip(R.drawable.player_ship_blue);
                ((ScaffoldActivity)getActivity()).navigateBack();
            }
        });
        ship_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScaffoldActivity)getActivity()).setPlayerShip(R.drawable.player_ship_green);
                ((ScaffoldActivity)getActivity()).navigateBack();
            }
        });

        return rootView;
    }
}
