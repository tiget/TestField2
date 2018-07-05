package com.example.tiget.testfield;



import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiget.testfield.RecyclerViewParts.Constructor;


public class MediaPlayerFragment extends Fragment {

    Button playBtn;
    SeekBar positionBar;
    SeekBar volumeBar;

    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    TextView SongNameTextView;
    TextView AuthorNameTextView;


    MediaPlayer mp;
    int totalTime;

    ImageView backBtn;

    public static MediaPlayerFragment newInstance(Constructor constructor) {
        MediaPlayerFragment fragnent = new MediaPlayerFragment();
        Bundle argument = new Bundle();
        argument.putSerializable("Constructor", constructor);
        fragnent.setArguments(argument);
        return fragnent;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context context = getContext();
        //создаем главную вьюшку
        View view = inflater.inflate(R.layout.media_player_fragment, container, false);
        //настраиваем

        backBtn = view.findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(new MediaPlayerFragment());
                ft.replace(R.id.FrameLayout, new RecyclerViewFragment(), "NewFragmentTag");
                ft.commit();
            }
        });


        playBtn = view.findViewById(R.id.playBtnClick);
        positionBar = view.findViewById(R.id.positionBar);
        volumeBar = view.findViewById(R.id.volumeBar);
        elapsedTimeLabel = view.findViewById(R.id.elapsedTimaLable);
        remainingTimeLabel = view.findViewById(R.id.remainingTimeLabel);
        AuthorNameTextView = view.findViewById(R.id.AuthorNameTextView);
        SongNameTextView = view.findViewById(R.id.SongNameTextView);


        Constructor constructor = (Constructor) getArguments().getSerializable("Constructor");
        String SongUri = constructor.SongUri;
        String SongName = constructor.SongName;
        String AuthorName = constructor.AuthorName;

        SongNameTextView.setText(SongName);
        AuthorNameTextView.setText(AuthorName);

        //Создаем и настраиваем Media Player
        mp = MediaPlayer.create(context, Uri.parse(SongUri));
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.3f, 0.7f);
        totalTime = mp.getDuration();

        //Настраиваем Position Bar
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mp.seekTo(i);
                    positionBar.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        //Настраиваем Volume Bar
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float volumeNum = i / 100f;
                mp.setVolume(volumeNum, volumeNum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mp.isPlaying()) {
                    mp.start();
                    playBtn.setBackgroundResource(R.drawable.stop);
                } else {
                    mp.pause();
                    playBtn.setBackgroundResource(R.drawable.play);
                }

            }
        });

        return view;

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            //Updating Position Bar
            positionBar.setProgress(currentPosition);

            //Update Labels
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime - currentPosition);
            remainingTimeLabel.setText("- " + remainingTime);

        }
    };

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;
        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }




}

