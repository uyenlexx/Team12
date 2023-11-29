package com.example.team12.components.user;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;

import com.example.team12.R;
import com.example.team12.components.FragmentSearch;
import com.example.team12.components.FragmentUser;
import com.google.android.material.slider.Slider;

public class FragmentUserSettings extends Fragment {
    Toolbar toolbar3;
    SeekBar screenSlider;
    SeekBar volumeSlider;

    AudioManager audioManager;
    int maxVolume;
    int currentVolume;

    int brightness;
    ContentResolver contentResolver;
    Window window;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View userSettingsView = inflater.inflate(R.layout.fragment_user_settings, container, false);
        return userSettingsView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar3 = view.findViewById(R.id.settings_toolbar);
        screenSlider = view.findViewById(R.id.setting1_slider);
        volumeSlider = view.findViewById(R.id.setting2_slider);

        toolbar3.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to fragment user
                FragmentUser fragment = new FragmentUser();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_user, fragment)
                        .commit();
            }
        });

        contentResolver = this.getActivity().getContentResolver();
        window = this.getActivity().getWindow();
        screenSlider.setMax(255);
        screenSlider.setKeyProgressIncrement(1);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this.getActivity())) {
                Toast.makeText(this.getActivity(), "Write allowed", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(android.net.Uri.parse("package:" + this.getActivity().getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        try {
            brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
            screenSlider.setProgress(brightness);
        } catch (Settings.SettingNotFoundException e) {
            throw new RuntimeException(e);
        }

        screenSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b){
                if (progress <= 20) {
                    brightness = 20;
                } else {
                    brightness = progress;
                }
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.screenBrightness = brightness / (float) 255;
                window.setAttributes(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //do nothing
            }
        });

        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) this.getActivity().getSystemService(this.getActivity().AUDIO_SERVICE);
        volumeSlider.setMax(maxVolume);
        volumeSlider.setProgress(currentVolume);
        volumeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b){
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //do nothing
            }
        });
    }
}
