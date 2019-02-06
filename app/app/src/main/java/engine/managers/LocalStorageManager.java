package engine.managers;

import android.app.Activity;
import android.content.Intent;

public class LocalStorageManager {
    public static final int READ_REQUEST_CODE = 42;

    public static void performFileSearch(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(intent, READ_REQUEST_CODE);
    }
}
