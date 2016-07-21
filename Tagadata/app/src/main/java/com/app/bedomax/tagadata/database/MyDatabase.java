package com.app.bedomax.tagadata.database;

import android.net.Uri;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.provider.ContentProvider;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;

/**
 * Created by Jorge on 20/07/16.
 */
@ContentProvider(authority = MyDatabase.AUTHORITY,
        database = MyDatabase.class,
        baseContentUri = MyDatabase.BASE_CONTENT_URI)
@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {

    public static final String NAME = "FavoritesBase";

    public static final int VERSION = 2;

    public static final String AUTHORITY = "com.app.bedomax.tagadata.provider";

    public static final String BASE_CONTENT_URI = "content://";

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = Uri.parse(MyDatabase.BASE_CONTENT_URI + MyDatabase.AUTHORITY).buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(name = NewProviderModel.ENDPOINT, contentProvider = MyDatabase.class)
    public static class NewProviderModel {

        public static final String ENDPOINT = "NewModel";

        @ContentUri(path = NewProviderModel.ENDPOINT,
                type = ContentUri.ContentType.VND_MULTIPLE + ENDPOINT)
        public static final Uri CONTENT_URI = buildUri(ENDPOINT);
    }

}
