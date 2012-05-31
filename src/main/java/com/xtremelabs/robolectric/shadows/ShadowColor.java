package com.xtremelabs.robolectric.shadows;

import android.graphics.Color;
import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

import java.util.HashMap;
import java.util.Locale;

@Implements(Color.class)
public class ShadowColor {

    private static final HashMap<String, Integer> sColorNameMap;

    static {
        sColorNameMap = new HashMap<String, Integer>();
        sColorNameMap.put("black", Color.BLACK);
        sColorNameMap.put("darkgray", Color.DKGRAY);
        sColorNameMap.put("gray", Color.GRAY);
        sColorNameMap.put("lightgray", Color.LTGRAY);
        sColorNameMap.put("white", Color.WHITE);
        sColorNameMap.put("red", Color.RED);
        sColorNameMap.put("green", Color.GREEN);
        sColorNameMap.put("blue", Color.BLUE);
        sColorNameMap.put("yellow", Color.YELLOW);
        sColorNameMap.put("cyan", Color.CYAN);
        sColorNameMap.put("magenta", Color.MAGENTA);
    }
    @Implementation
    public static int rgb(int red, int green, int blue) {
        return argb(0xff, red, green, blue);
    }

    @Implementation
    public static int argb(int alpha, int red, int green, int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    @Implementation
    public static int parseColor(String colorString) {
        if (colorString.charAt(0) == '#') {
            // Use a long to avoid rollovers on #ffXXXXXX
            long color = Long.parseLong(colorString.substring(1), 16);
            if (colorString.length() == 7) {
                // Set the alpha value
                color |= 0x00000000ff000000;
            } else if (colorString.length() != 9) {
                throw new IllegalArgumentException("Unknown color");
            }
            return (int) color;
        } else {
            Integer color = sColorNameMap.get(colorString.toLowerCase(Locale.US));
            if (color != null) {
                return color;
            }
        }
        throw new IllegalArgumentException("Unknown color");
    }
}