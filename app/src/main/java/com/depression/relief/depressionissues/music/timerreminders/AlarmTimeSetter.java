package com.depression.relief.depressionissues.music.timerreminders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.music.abilityutility.Screentils;
import com.depression.relief.depressionissues.music.abilityutility.ShareListUtils;
import com.depression.relief.depressionissues.music.abilityutility.SharedPrefsUtils;
import com.depression.relief.depressionissues.music.custombase.NeelDownActivity;
import com.depression.relief.depressionissues.music.customhelper.Helpers;
import com.depression.relief.depressionissues.music.precausions.ServiceAudioPlayer;
import com.shawnlin.numberpicker.NumberPicker;

public class AlarmTimeSetter extends NeelDownActivity implements View.OnClickListener {
    private Intent intent;
    private LinearLayout mCancelLayout;
    private TextView mCustomTv;
    private NumberPicker picker1;
    private AppCompatImageView mSaveView;
    private NumberPicker mSetTimeNpHour;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Screentils.setFullScreenActivity(this);
        setContentView((int) R.layout.time_reminder_setter);
        alarminiyt();
        Screentils.hideActionBar(this);

//        PreloadSoundAds.showNativeAdmob(this, findViewById(R.id.nativeAds));
    }

    private void alarminiyt() {
        TextView textView = (TextView) findViewById(R.id.custom_tv);
        this.mCustomTv = textView;
        textView.setOnClickListener(this);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.set_time_np_hour);
        this.mSetTimeNpHour = numberPicker;
        numberPicker.setOnClickListener(this);
        NumberPicker numberPicker2 = (NumberPicker) findViewById(R.id.set_time_np_minute);
        this.picker1 = numberPicker2;
        numberPicker2.setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.cancel_layout);
        this.mCancelLayout = linearLayout;
        linearLayout.setOnClickListener(this);
        AppCompatImageView appCompatImageView = (AppCompatImageView) findViewById(R.id.save_view);
        this.mSaveView = appCompatImageView;
        appCompatImageView.setOnClickListener(this);
        this.mSetTimeNpHour.setDisplayedValues(getResources().getStringArray(R.array.hour_display));
        this.picker1.setDisplayedValues(getResources().getStringArray(R.array.minute_display));
        this.mSetTimeNpHour.setDisplayedValues(getResources().getStringArray(R.array.hour_display));
        this.picker1.setDisplayedValues(getResources().getStringArray(R.array.minute_display));
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancel_layout) {
            finish();
        } else if (id == R.id.save_view) {
            ShareListUtils.setbIsTimerSet(true);
            SharedPrefsUtils.setLongPreference(this, Helpers.KEY_PLAY_TIME, (long) (((this.mSetTimeNpHour.getValue() * 60) + this.picker1.getValue()) * 60 * 1000));
            Intent intent2 = new Intent(this, ServiceAudioPlayer.class);
            this.intent = intent2;
            intent2.setAction(ServiceAudioPlayer.ACTION_CMD);
            this.intent.putExtra(ServiceAudioPlayer.CMD_NAME, ServiceAudioPlayer.CMD_UPDATE_TIME);
            startService(this.intent);
            Intent intent3 = new Intent(this, RelexingSoundPlayActivity.class);
            intent3.addFlags(67108864);
            startActivity(intent3);
        }
    }
}
