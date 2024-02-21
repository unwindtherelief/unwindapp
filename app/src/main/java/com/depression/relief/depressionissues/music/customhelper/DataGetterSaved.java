package com.depression.relief.depressionissues.music.customhelper;

import android.content.Context;
import android.content.SharedPreferences;

import com.depression.relief.depressionissues.music.modelizer.ItemMixer;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataGetterSaved {
    private static final String LIST_MIX = "List_mix_custom";
    private static final String LIST_MIX_PREMIUM = "List_mix_premium";
    private static final String LIST_SOUND_PREMIUM = "List_sound_premium";
    private static final String PREFS_TAG = "MyCustomPrefs";

    public static List<ItemMixer> getCustomMixList(Context context) {
        Gson gson = new Gson();
        new ArrayList();
        return (List) gson.fromJson(context.getSharedPreferences(PREFS_TAG, 0).getString(LIST_MIX, ""), new TypeToken<List<ItemMixer>>() {
        }.getType());
    }

    public static void addCustomMixInJSONArray(Context context, ItemMixer mixItem) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_TAG, 0);
        String string = sharedPreferences.getString(LIST_MIX, "");
        String json = gson.toJson((Object) mixItem);
        JSONArray jSONArray = new JSONArray();
        try {
            if (string.length() != 0) {
                jSONArray = new JSONArray(string);
            }
            jSONArray.put(new JSONObject(json));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(LIST_MIX, jSONArray.toString());
        edit.commit();
    }

    public static void removeCustomMixInJSONArray(Context context, ItemMixer mixItem) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_TAG, 0);
        String string = sharedPreferences.getString(LIST_MIX, "");
        gson.toJson((Object) mixItem);
        JSONArray jSONArray = new JSONArray();
        try {
            if (string.length() != 0) {
                jSONArray = new JSONArray(string);
            }
            for (int i = 0; i < jSONArray.length(); i++) {
                if (((ItemMixer) gson.fromJson(jSONArray.get(i).toString(), ItemMixer.class)).getMixSoundId() == mixItem.getMixSoundId()) {
                    jSONArray.remove(i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(LIST_MIX, jSONArray.toString());
        edit.commit();
    }
}
