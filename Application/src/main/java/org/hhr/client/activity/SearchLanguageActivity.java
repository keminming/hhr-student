package org.hhr.client.activity;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;


/**
 * Created by ke on 7/7/2015.
 */
public class SearchLanguageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // Handle the normal search query case
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Intent wordIntent = new Intent(this, org.hhr.client.activity.ShowLanguageActivity.class);
            String query = intent.getStringExtra(SearchManager.QUERY);
            wordIntent.putExtra(SearchManager.QUERY,query);
            startActivity(wordIntent);
            finish();
        }
    }
}