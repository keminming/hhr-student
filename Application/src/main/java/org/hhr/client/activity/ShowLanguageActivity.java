package org.hhr.client.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import org.hhr.client.R;
import org.hhr.client.provider.LanguageContentProvider;
import org.hhr.client.provider.LanguageTableColumns;

/**
 * Created by ke on 7/10/2015.
 */
public class ShowLanguageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_main);
        String query = getIntent().getStringExtra(SearchManager.QUERY);
        String selection = LanguageTableColumns.COLUMN_NAME_CODE + "MATCH ?";
        String[] selectionArgs = new String[] {query};

        Cursor cursor = getContentResolver().query(LanguageContentProvider.LANGUAGE_CONTENT_URI, null, selection, selectionArgs, null);

        if (cursor == null) {
            finish();
        } else {
            cursor.moveToFirst();

            TextView word = (TextView) findViewById(R.id.search_result);
            word.setText(cursor.getString(cursor.getColumnIndex(LanguageTableColumns.COLUMN_NAME_CODE)));
        }
    }
}
