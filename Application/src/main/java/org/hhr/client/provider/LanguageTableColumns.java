package org.hhr.client.provider;

/**
 * Created by ke on 7/9/2015.
 */

import android.app.SearchManager;
import android.provider.BaseColumns;

/**
 * Created by ke on 7/9/2015.
 */
    /* Inner class that defines the table contents */
public abstract class LanguageTableColumns implements BaseColumns{
    public static final String TABLE_NAME = "language";
    public static final String COLUMN_NAME_CODE = "code";
    public static final String COLUMN_NAME_PATH = "path";
    public static final String COLUMN_NAME_INSTALLED = "installed";
    public static final String SUGGEST_COLUMN_INTENT_DATA_ID = SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID;
}