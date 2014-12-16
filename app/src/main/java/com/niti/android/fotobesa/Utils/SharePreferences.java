package com.niti.android.fotobesa.Utils;

import android.content.SharedPreferences;

import com.niti.android.fotobesa.FotoBesa;
import com.niti.android.fotobesa.provider.SharedPrefFiles;
import com.niti.android.fotobesa.provider.SharedPrefKeys;
import com.niti.fotobesa.lib.views.FotoBesaLayout;

/**
 * Created by jpardogo on 23/02/2014.
 */
public class SharePreferences {

    public static void saveCustomization(SharedPrefKeys prefKey, int progress) {
        SharedPreferences customize_pref = getCustomizePref();
        SharedPreferences.Editor editor = customize_pref.edit();
        editor.putInt(prefKey.toString(), progress);
        editor.commit();
    }

    public static int getValue(SharedPrefKeys prefKey) {
        SharedPreferences customize_pref = getCustomizePref();
        int defaultValue = getDefaultValue(prefKey);
        return customize_pref.getInt(prefKey.toString(), defaultValue);
    }

    private static SharedPreferences getCustomizePref() {
        return FotoBesa.getAppContext().getSharedPreferences(SharedPrefFiles.CUSTOMIZE_SETTINGS.toString(), 0);
    }

    private static int getDefaultValue(SharedPrefKeys prefKey) {
        int defaultValue = 0;
        switch (prefKey) {
            case GAP_PROGRESS:
                defaultValue = FotoBesa.getAppContext().getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
                break;
            case SPEED_PROGRESS:
                defaultValue = FotoBesaLayout.DEFAULT_SPEED;
                break;
            case DIV_HEIGHT_PROGRESS:
                defaultValue = FotoBesa.getAppContext().getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
                break;
        }
        return defaultValue;
    }

    public static void reset() {
        for (SharedPrefKeys key : SharedPrefKeys.values()) {
            saveCustomization(key, getDefaultValue(key));
        }
    }
}