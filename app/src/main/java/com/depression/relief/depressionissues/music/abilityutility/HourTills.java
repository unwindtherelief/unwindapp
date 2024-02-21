package com.depression.relief.depressionissues.music.abilityutility;

public class HourTills {
    public static String mstostringConverter(long j) {
        int i = (int) (j / 1000);
        int i2 = i / 60;
        int i3 = i % 60;
        if (i2 >= 60) {
            return String.format("%d:%02d:%02d", new Object[]{Integer.valueOf(i2 / 60), Integer.valueOf(i2 % 60), Integer.valueOf(i3)});
        }
        return String.format("%d:%02d", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3)});
    }
}
