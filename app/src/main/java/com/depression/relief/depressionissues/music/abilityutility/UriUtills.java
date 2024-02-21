package com.depression.relief.depressionissues.music.abilityutility;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

public class UriUtills {
    public static final Uri resourcetoURI(Context context, int i) throws Resources.NotFoundException {
        Resources resources = context.getResources();
        return Uri.parse("android.resource://" + resources.getResourcePackageName(i) + '/' + resources.getResourceTypeName(i) + '/' + resources.getResourceEntryName(i));
    }
}
