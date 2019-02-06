package engine.utils;

import android.util.Log;

public class Utils {

    public static Boolean PRINT_LOG = true;
    public static String TAG = "GMTool";

    public static String MapToString() {
        return "";
    }

    public static void log(String message)
    {
        if (PRINT_LOG) Log.d(TAG, message);
    }
}
