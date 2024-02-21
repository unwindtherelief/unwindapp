package com.depression.relief.depressionissues.music.customhelper;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.music.abilityutility.TillsUtils;
import com.depression.relief.depressionissues.music.modelizer.AudioItem;
import com.depression.relief.depressionissues.music.modelizer.CategoryMixer;
import com.depression.relief.depressionissues.music.modelizer.ItemCoverer;
import com.depression.relief.depressionissues.music.modelizer.ItemMixer;
import com.depression.relief.depressionissues.music.modelizer.SetterItem;
import com.facebook.imageutils.JfifUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import kotlinx.coroutines.scheduling.WorkQueueKt;

public class MixDataParcer {
    private static SparseArray<ItemCoverer> mixCoverItemSparseArray = new SparseArray<>();
    private static SparseArray<ItemMixer> mixItemSparseArray = new SparseArray<>();
    public static int numerTab = 1;
    private static SparseArray<AudioItem> soundItemSparseArray = new SparseArray<>();
    private static List<AudioItem> soundItems = new ArrayList();

    public static void createData(Context context) {
        soundItemSparseArray = new SparseArray<>();
        soundItems = new ArrayList();
        mixItemSparseArray = new SparseArray<>();
        createSoundData(context);
        createMixCoverItemData(context);
        createMixItemData(context);
    }

    private static void createMixItemData(Context context) {
        ItemMixer mixItem = new ItemMixer(201, 2, context.getResources().getString(R.string.str_theme_rain_on_leaves), createMixCoverItem(201), createListSoundItem(new int[]{103}, new int[]{60}));
        ItemMixer mixItem2 = new ItemMixer(202, 2, context.getResources().getString(R.string.str_theme_spring_rain), createMixCoverItem(202), createListSoundItem(new int[]{109, 106}, new int[]{50, 30}));
        ItemMixer mixItem3 = new ItemMixer(203, 2, context.getResources().getString(R.string.str_theme_rain_on_roof), createMixCoverItem(203), createListSoundItem(new int[]{104}, new int[]{50}));
        ItemMixer mixItem4 = new ItemMixer(204, 2, context.getResources().getString(R.string.str_theme_rain_on_tent), createMixCoverItem(204), createListSoundItem(new int[]{105, 106}, new int[]{50, 50}));
        ItemMixer mixItem5 = new ItemMixer(205, 2, context.getResources().getString(R.string.str_theme_ocean_rain), createMixCoverItem(205), createListSoundItem(new int[]{107, 109}, new int[]{40, 40}));
        ;
        ItemMixer mixItem6 = new ItemMixer(205, 2, context.getResources().getString(R.string.str_theme_ocean_rain), createMixCoverItem(205), createListSoundItem(new int[]{107, 109}, new int[]{40, 40}));
        ItemMixer mixItem7 = new ItemMixer(206, 2, context.getResources().getString(R.string.str_theme_rain_on_window), createMixCoverItem(206), createListSoundItem(new int[]{108, 106}, new int[]{50, 50}));
        ItemMixer mixItem8 = new ItemMixer(207, 2, context.getResources().getString(R.string.str_theme_rainy_evening), createMixCoverItem(207), createListSoundItem(new int[]{109, 110}, new int[]{40, 50}));
        ItemMixer mixItem9 = new ItemMixer(207, 2, context.getResources().getString(R.string.str_theme_rainy_evening), createMixCoverItem(207), createListSoundItem(new int[]{109, 110}, new int[]{40, 50}));
        ItemMixer mixItem10 = new ItemMixer(JfifUtil.MARKER_RST0, 2, context.getResources().getString(R.string.str_theme_thunderstorm), createMixCoverItem(JfifUtil.MARKER_RST0), createListSoundItem(new int[]{113, 106}, new int[]{50, 50}));
        ItemMixer mixItem11 = new ItemMixer(209, 2, context.getResources().getString(R.string.str_theme_wind_rain), createMixCoverItem(209), createListSoundItem(new int[]{109, 111}, new int[]{40, 60}));
        ;
        ItemMixer mixItem12 = new ItemMixer(209, 2, context.getResources().getString(R.string.str_theme_wind_rain), createMixCoverItem(209), createListSoundItem(new int[]{109, 111}, new int[]{40, 60}));
        ItemMixer mixItem13 = new ItemMixer(210, 2, context.getResources().getString(R.string.str_theme_rain_crickets), createMixCoverItem(210), createListSoundItem(new int[]{109, 112}, new int[]{50, 20}));
        ItemMixer mixItem14 = new ItemMixer(211, 2, context.getResources().getString(R.string.str_theme_rainy_day), createMixCoverItem(211), createListSoundItem(new int[]{109, 102, 106}, new int[]{50, 50, 40}));
        ItemMixer mixItem15 = new ItemMixer(211, 2, context.getResources().getString(R.string.str_theme_rainy_day), createMixCoverItem(211), createListSoundItem(new int[]{109, 102, 106}, new int[]{50, 50, 40}));
        ItemMixer mixItem16 = new ItemMixer(212, 2, context.getResources().getString(R.string.str_theme_rain_piano), createMixCoverItem(212), createListSoundItem(new int[]{101, 115}, new int[]{50, 40}));
        ItemMixer mixItem17 = new ItemMixer(213, 2, context.getResources().getString(R.string.str_theme_rain_forest), createMixCoverItem(213), createListSoundItem(new int[]{103, 106}, new int[]{60, 30}));
        ItemMixer mixItem18 = new ItemMixer(213, 2, context.getResources().getString(R.string.str_theme_rain_forest), createMixCoverItem(213), createListSoundItem(new int[]{103, 106}, new int[]{60, 30}));
        ItemMixer mixItem19 = new ItemMixer(214, 2, context.getResources().getString(R.string.str_theme_urban_rain), createMixCoverItem(214), createListSoundItem(new int[]{114}, new int[]{50}));
        ItemMixer mixItem20 = new ItemMixer(JfifUtil.MARKER_RST7, 2, context.getResources().getString(R.string.str_theme_light_rain), createMixCoverItem(JfifUtil.MARKER_RST7), createListSoundItem(new int[]{101, 102}, new int[]{50, 50}));
        mixItemSparseArray.put(201, mixItem);
        mixItemSparseArray.put(202, mixItem2);
        mixItemSparseArray.put(203, mixItem3);
        mixItemSparseArray.put(204, mixItem4);
        mixItemSparseArray.put(205, mixItem5);
        mixItemSparseArray.put(206, mixItem7);
        mixItemSparseArray.put(207, mixItem8);
        mixItemSparseArray.put(JfifUtil.MARKER_RST0, mixItem10);
        mixItemSparseArray.put(209, mixItem11);
        mixItemSparseArray.put(210, mixItem13);
        mixItemSparseArray.put(211, mixItem14);
        mixItemSparseArray.put(212, mixItem16);
        mixItemSparseArray.put(213, mixItem17);
        mixItemSparseArray.put(214, mixItem19);
        mixItemSparseArray.put(JfifUtil.MARKER_RST7, mixItem20);
        List<ItemMixer> customMixList = DataGetterSaved.getCustomMixList(context);
        if (customMixList != null && customMixList.size() > 0) {
            for (ItemMixer next : customMixList) {
                mixItemSparseArray.put(next.getMixSoundId(), next);
                Log.e("CUSTOM_MIX", next.getMixSoundId() + "");
            }
        }
    }

    private static List<AudioItem> createListSoundItem(int[] iArr, int[] iArr2) {
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i = 0; i < iArr.length; i++) {
            AudioItem soundItem = new AudioItem(soundItemSparseArray.get(iArr[i]));
            soundItem.setVolume(iArr2[i]);
            arrayList.add(soundItem);
        }
        return arrayList;
    }

    private static ItemCoverer createMixCoverItem(int i) {
        return mixCoverItemSparseArray.get(i);
    }

    private static void createMixCoverItemData(Context context) {
        mixCoverItemSparseArray.put(201, new ItemCoverer(201, R.drawable.rain_with_piano));
        mixCoverItemSparseArray.put(202, new ItemCoverer(202, R.drawable.png_spring_rain2));
        mixCoverItemSparseArray.put(203, new ItemCoverer(203, R.drawable.png_sleep));
        mixCoverItemSparseArray.put(204, new ItemCoverer(204, R.drawable.png_healing));
        mixCoverItemSparseArray.put(205, new ItemCoverer(205, R.drawable.png_flute));
        mixCoverItemSparseArray.put(206, new ItemCoverer(206, R.drawable.png_fire));
        mixCoverItemSparseArray.put(207, new ItemCoverer(207, R.drawable.png_thunder));
        mixCoverItemSparseArray.put(JfifUtil.MARKER_RST0, new ItemCoverer(JfifUtil.MARKER_RST0, R.drawable.png_piano));
        mixCoverItemSparseArray.put(209, new ItemCoverer(209, R.drawable.png_heavy_rain));
        mixCoverItemSparseArray.put(210, new ItemCoverer(210, R.drawable.png_guitar));
        mixCoverItemSparseArray.put(211, new ItemCoverer(211, R.drawable.png_owl));
        mixCoverItemSparseArray.put(212, new ItemCoverer(212, R.drawable.png_bird));
        mixCoverItemSparseArray.put(213, new ItemCoverer(213, R.drawable.png_wind_cheems));
        mixCoverItemSparseArray.put(214, new ItemCoverer(214, R.drawable.png_wind));
        mixCoverItemSparseArray.put(JfifUtil.MARKER_RST7, new ItemCoverer(JfifUtil.MARKER_RST7, R.drawable.png_light_rain));
    }

    private static void createSoundData(Context context) {
        AudioItem soundItem = new AudioItem(101, context.getResources().getString(R.string.str_sound_name_light_rain), "light_rain", R.drawable.light_rain, 50);
        AudioItem soundItem2 = new AudioItem(102, context.getResources().getString(R.string.str_sound_name_bird), "bird", R.drawable.birdee, 50);
        AudioItem soundItem3 = new AudioItem(103, context.getResources().getString(R.string.str_sound_name_rain_on_leaves), "rain_on_leaves", R.drawable.leafes, 50);
        AudioItem soundItem4 = new AudioItem(104, context.getResources().getString(R.string.str_sound_name_rain_on_roof), "rain_on_roof", R.drawable.rooftop, 50);
        AudioItem soundItem5 = new AudioItem(105, context.getResources().getString(R.string.str_sound_name_rain_on_tent), "rain_on_tent", R.drawable.tent_by_rain, 50);
        AudioItem soundItem6 = new AudioItem(106, context.getResources().getString(R.string.str_sound_name_thunder), "thunder", R.drawable.crack, 50);
        AudioItem soundItem7 = new AudioItem(107, context.getResources().getString(R.string.str_sound_name_tide), "ocean", R.drawable.waves, 50);
        AudioItem soundItem8 = new AudioItem(108, context.getResources().getString(R.string.str_sound_name_rain_on_window), "rain_on_window", R.drawable.khidki, 50);
        AudioItem soundItem9 = new AudioItem(109, context.getResources().getString(R.string.str_sound_name_rain), "rain", R.drawable.rainy, 50);
        AudioItem soundItem10 = new AudioItem(110, context.getResources().getString(R.string.str_sound_name_fire), "fire", R.drawable.pushpa, 50);
        AudioItem soundItem11 = new AudioItem(111, context.getResources().getString(R.string.str_sound_name_wind), "wind", R.drawable.wave_winds, 50);
        AudioItem soundItem12 = new AudioItem(112, context.getResources().getString(R.string.str_sound_name_cricket), "cricket", R.drawable.crickit, 50);
        AudioItem soundItem13 = new AudioItem(113, context.getResources().getString(R.string.str_sound_name_heavy_rain), "heavy_rain", R.drawable.most_rain, 50);
        AudioItem soundItem14 = new AudioItem(114, context.getResources().getString(R.string.str_sound_name_urban_rain), "urban_rain", R.drawable.new_less_rain, 50);
        AudioItem soundItem15 = new AudioItem(115, context.getResources().getString(R.string.str_sound_name_piano), "piano", R.drawable.banjo, 40);
        AudioItem soundItem16 = new AudioItem(116, context.getResources().getString(R.string.str_sound_name_flute), "flute", R.drawable.krishna, 50);
        AudioItem soundItem17 = new AudioItem(117, context.getResources().getString(R.string.str_sound_name_frog), "frog", R.drawable.mandook, 50);
        AudioItem soundItem18 = new AudioItem(118, context.getResources().getString(R.string.str_sound_name_thunder2), "thunder2", R.drawable.double_crack, 50);
        AudioItem soundItem19 = new AudioItem(119, context.getResources().getString(R.string.str_sound_name_guitar), "guitar", R.drawable.sitaar, 50);
        AudioItem soundItem20 = new AudioItem(120, context.getResources().getString(R.string.str_sound_name_owl), "owl", R.drawable.ghuvad, 50);
        AudioItem soundItem21 = new AudioItem(121, context.getResources().getString(R.string.str_sound_name_peacock), "peacock", R.drawable.peacock, 50);
        AudioItem soundItem22 = new AudioItem(122, context.getResources().getString(R.string.str_sound_name_wind_chimes), "wind_chimes", R.drawable.chimes_taps, 50);
        AudioItem soundItem23 = new AudioItem(123, context.getResources().getString(R.string.str_sound_name_wind_seagulls), "seagulls", R.drawable.sea_gals, 50);
        AudioItem soundItem24 = new AudioItem(124, context.getResources().getString(R.string.str_sound_name_wind_cat_purring), "cat_purring", R.drawable.billy, 50);
        AudioItem soundItem25 = new AudioItem(125, context.getResources().getString(R.string.str_sound_name_wind_wolf), "wolf", R.drawable.bhediya, 50);
        AudioItem soundItem26 = new AudioItem(126, context.getResources().getString(R.string.str_sound_name_cafe), "cafe", R.drawable.nescafe, 50);
        AudioItem soundItem34 = new AudioItem(WorkQueueKt.MASK, context.getResources().getString(R.string.str_sound_name_meditation), "meditation", R.drawable.lotus, 50);
        soundItems.add(soundItem);
        soundItems.add(soundItem2);
        soundItems.add(soundItem3);
        soundItems.add(soundItem4);
        soundItems.add(soundItem5);
        soundItems.add(soundItem6);
        soundItems.add(soundItem7);
        soundItems.add(soundItem8);
        soundItems.add(soundItem9);
        soundItems.add(soundItem10);
        soundItems.add(soundItem11);
        soundItems.add(soundItem12);
        soundItems.add(soundItem13);
        soundItems.add(soundItem14);
        soundItems.add(soundItem15);
        soundItems.add(soundItem16);
        soundItems.add(soundItem17);
        soundItems.add(soundItem18);
        soundItems.add(soundItem19);
        soundItems.add(soundItem20);
        soundItems.add(soundItem21);
        soundItems.add(soundItem22);
        soundItems.add(soundItem23);
        soundItems.add(soundItem24);
        soundItems.add(soundItem25);
        soundItems.add(soundItem26);
        for (AudioItem next : soundItems) {
            soundItemSparseArray.put(next.getSoundId(), next);
        }
    }

    public static List<ItemMixer> getListMixItem(Context context) {
        List<ItemMixer> asList = TillsUtils.asList(mixItemSparseArray);
        Collections.sort(asList, new Comparator<ItemMixer>() {
            public int compare(ItemMixer mixItem, ItemMixer mixItem2) {
                return mixItem.getMixSoundId() - mixItem2.getMixSoundId();
            }
        });
        if (asList.size() > 0) {
            for (int size = asList.size() - 1; size >= 0; size--) {
                if (((ItemMixer) asList.get(size)).getCategory() == 1) {
                    asList.remove(size);
                }
            }
        }
        return asList;
    }

    public static List<ItemMixer> getListCustomItem() {
        List<ItemMixer> asList = TillsUtils.asList(mixItemSparseArray);
        Collections.sort(asList, new Comparator<ItemMixer>() {
            public int compare(ItemMixer mixItem, ItemMixer mixItem2) {
                return mixItem.getMixSoundId() - mixItem2.getMixSoundId();
            }
        });
        if (asList.size() > 0) {
            for (int size = asList.size() - 1; size >= 0; size--) {
                if (((ItemMixer) asList.get(size)).getCategory() != 1) {
                    asList.remove(size);
                }
            }
        }
        return asList;
    }

    public static List<AudioItem> getListSoundItem(Context context) {
        return TillsUtils.asList(soundItemSparseArray);
    }

    public static List<ItemCoverer> getListMixCoverItem(Context context) {
        new ArrayList();
        return TillsUtils.asList(mixCoverItemSparseArray);
    }

    public static List<SetterItem> getListSettingItem(Context context) {
        return new ArrayList();
    }

    public static List<CategoryMixer> getListMixCategoryItem(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CategoryMixer(0, "All"));
        List<ItemMixer> customMixList = DataGetterSaved.getCustomMixList(context);
        if (customMixList == null || customMixList.size() <= 0) {
            arrayList.add(new CategoryMixer(1, "Sleep"));
            arrayList.add(new CategoryMixer(2, "Rain"));
            arrayList.add(new CategoryMixer(3, "Relax"));
            arrayList.add(new CategoryMixer(4, "Mediation"));
            arrayList.add(new CategoryMixer(5, "Work"));
        } else {
            arrayList.add(new CategoryMixer(1, "Custom"));
            arrayList.add(new CategoryMixer(2, "Sleep"));
            arrayList.add(new CategoryMixer(3, "Rain"));
            arrayList.add(new CategoryMixer(4, "Relax"));
            arrayList.add(new CategoryMixer(5, "Mediation"));
            arrayList.add(new CategoryMixer(6, "Work"));
        }
        ((CategoryMixer) arrayList.get(0)).setChecked(true);
        return arrayList;
    }

    public static AudioItem getSoundItemById(int i) {
        return soundItemSparseArray.get(i);
    }

    public static ItemMixer getMixItemById(int i) {
        return mixItemSparseArray.get(i);
    }

    public static ItemCoverer getMixCoverItemById(int i) {
        return mixCoverItemSparseArray.get(i);
    }

    public static int getResourceIDUsingsoundId(AudioItem soundItem) {

        //vector sound item adder

        switch (soundItem.getSoundId()) {


            case 102:
                return R.drawable.birdee;
            case 103:
                return R.drawable.leafes;
            case 104:
                return R.drawable.rooftop;
            case 105:
                return R.drawable.tent_by_rain;
            case 106:
                return R.drawable.crack;
            case 107:
                return R.drawable.waves;
            case 108:
                return R.drawable.khidki;
            case 109:
                return R.drawable.rainy;
            case 110:
                return R.drawable.pushpa;
            case 111:
                return R.drawable.wave_winds;
            case 112:
                return R.drawable.crickit;
            case 113:
                return R.drawable.most_rain;
            case 114:
                return R.drawable.new_less_rain;
            case 115:
                return R.drawable.banjo;
            case 116:
                return R.drawable.krishna;
            case 117:
                return R.drawable.mandook;
            case 118:
                return R.drawable.double_crack;
            case 119:
                return R.drawable.sitaar;
            case 120:
                return R.drawable.ghuvad;
            case 121:
                return R.drawable.peacock;
            case 122:
                return R.drawable.chimes_taps;
            case 123:
                return R.drawable.sea_gals;
            case 124:
                return R.drawable.billy;
            case 125:
                return R.drawable.bhediya;
            case 126:
                return R.drawable.nescafe;
            case WorkQueueKt.MASK /*127*/:
                return R.drawable.lotus;
            default:
                return R.drawable.light_rain;
        }
    }

    public static int getSmallCoverResourceID(ItemMixer mixItem) {
        switch (mixItem.getMixSoundId()) {

            // small background adder

            case 201:
                return R.drawable.rain_with_piano;
            case 202:
                return R.drawable.png_spring_rain2;
            case 203:
                return R.drawable.png_sleep;
            case 204:
                return R.drawable.png_healing;
            case 205:
                return R.drawable.png_flute;
            case 206:
                return R.drawable.png_fire;
            case 207:
                return R.drawable.png_thunder;
            case JfifUtil.MARKER_RST0 /*208*/:
                return R.drawable.png_piano;
            case 209:
                return R.drawable.png_heavy_rain;
            case 210:
                return R.drawable.png_guitar;
            case 211:
                return R.drawable.png_owl;
            case 212:
                return R.drawable.png_bird;
            case 213:
                return R.drawable.png_wind_cheems;
            case 214:
                return R.drawable.png_wind;
            case JfifUtil.MARKER_RST7 /*215*/:
                return R.drawable.png_light_rain;
            default:
                return R.drawable.change_sheets;
        }
    }
}
