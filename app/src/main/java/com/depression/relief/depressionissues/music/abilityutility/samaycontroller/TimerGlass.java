package com.depression.relief.depressionissues.music.abilityutility.samaycontroller;

import android.os.Handler;
import android.os.Message;

public abstract class TimerGlass implements TimerGlassListener {
    private static final int INTERVAL = 1000;
    private static final int MSG = 1;
    /* access modifiers changed from: private */
    public Handler handler;
    /* access modifiers changed from: private */
    public long interval;
    /* access modifiers changed from: private */
    public boolean isPaused;
    private boolean isRunning;
    /* access modifiers changed from: private */
    public long localTime;
    /* access modifiers changed from: private */
    public long time;

    static /* synthetic */ long access$114(TimerGlass hourglass, long j) {
        long j2 = hourglass.localTime + j;
        hourglass.localTime = j2;
        return j2;
    }

    public TimerGlass() {
        init(0, 1000);
    }

    public TimerGlass(long j) {
        init(j, 1000);
    }

    public TimerGlass(long j, long j2) {
        init(j, j2);
    }

    private void init(long j, long j2) {
        setTime(j);
        setInterval(j2);
        initHourglass();
    }

    private void initHourglass() {
        this.handler = new Handler() {
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what == 1 && !TimerGlass.this.isPaused) {
                    if (TimerGlass.this.localTime <= TimerGlass.this.time) {
                        TimerGlass hourglass = TimerGlass.this;
                        hourglass.onTimerTick(hourglass.time - TimerGlass.this.localTime);
                        TimerGlass hourglass2 = TimerGlass.this;
                        TimerGlass.access$114(hourglass2, hourglass2.interval);
                        sendMessageDelayed(TimerGlass.this.handler.obtainMessage(1), TimerGlass.this.interval);
                        return;
                    }
                    TimerGlass.this.stopTimer();
                }
            }
        };
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void startTimer() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.isPaused = false;
            this.localTime = 0;
            Handler handler2 = this.handler;
            handler2.sendMessage(handler2.obtainMessage(1));
        }
    }

    public void stopTimer() {
        this.isRunning = false;
        this.handler.removeMessages(1);
        onTimerFinish();
    }

    public void stopTimerWithNoAction() {
        this.isRunning = false;
        this.handler.removeMessages(1);
    }

    public synchronized boolean isPaused() {
        return this.isPaused;
    }

    private synchronized void setPaused(boolean z) {
        this.isPaused = z;
    }

    public synchronized void pauseTimer() {
        setPaused(true);
    }

    public synchronized void resumeTimer() {
        setPaused(false);
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(1));
    }

    public void setTime(long j) {
        if (!this.isRunning) {
            if (this.time <= 0 && j < 0) {
                j *= -1;
            }
            this.time = j;
        }
    }

    public long getRemainingTime() {
        if (this.isRunning) {
            return this.time;
        }
        return 0;
    }

    public void setInterval(long j) {
        if (!this.isRunning) {
            if (this.interval <= 0 && j < 0) {
                j *= -1;
            }
            this.interval = j;
        }
    }
}
