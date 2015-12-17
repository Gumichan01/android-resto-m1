import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by celia on 15/12/15.
 */
public class Content_provider_project extends ContentProvider {


    //appelé la bdd

    private static final String authority = "com.project.resto";

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(authority, "Note", 0);
        matcher.addURI(authority, "Periode/*", 1);
        matcher.addURI(authority, "gps/*", 2);
        matcher.addURI(authority, "Restaurant/*", 3);
        matcher.addURI(authority, "Avoir/*", 4);
        matcher.addURI(authority, "Ouvrir/*", 5);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
