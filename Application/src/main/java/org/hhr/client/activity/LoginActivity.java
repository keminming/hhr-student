package org.hhr.client.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.hhr.client.R;
import org.hhr.client.global.Session;
import org.hhr.client.global.SyncService;
import org.hhr.client.global.SyncServiceFactory;
import org.hhr.client.global.SyncTask;
import org.hhr.client.provider.LanguageContentProvider;
import org.hhr.client.provider.UserTableColumns;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends ActionBarActivity {
    private EditText username;

    private EditText password;

    private Button signin;

    private Button signup;

    int numberOfRemainingLoginAttempts = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.editText2);
        password = (EditText)findViewById(R.id.editText);

        signin = (Button)findViewById(R.id.button);
        signup = (Button)findViewById(R.id.button2);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selection = UserTableColumns.USER_NAME + " = ?";
                String[] selectionArgs = new String[] {username.getText().toString()};

                Cursor cursor = getContentResolver().query(LanguageContentProvider.USER_CONTENT_URI, null, selection, selectionArgs, null);

                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Wrong Username, Signup First", Toast.LENGTH_SHORT).show();
                } else {
                    syncData();

                    cursor.moveToFirst();
                    String pwd = cursor.getString(cursor.getColumnIndex(UserTableColumns.PASS_WORD));
                    if(pwd.trim().equals(password.getText().toString())){
                        Session session = Session.getInstance();
                        session.setLoginTime(System.currentTimeMillis());
                        Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                        Intent wordIntent = new Intent(v.getContext(), org.hhr.client.activity.MainActivity.class);
                        startActivity(wordIntent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Wrong Password",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ContentValues content = new ContentValues();
                content.put(UserTableColumns.USER_NAME,username.getText().toString());
                content.put(UserTableColumns.PASS_WORD, password.getText().toString());

                Session session = Session.getInstance();
                session.setUsername(username.getText().toString());

                syncData();

                Uri uri = getContentResolver().insert(LanguageContentProvider.USER_CONTENT_URI, content);
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent wordIntent = new Intent(v.getContext(), org.hhr.client.activity.MainActivity.class);
                startActivity(wordIntent);
            }
        });
    }

    private void syncData(){
        SyncService syncService = SyncServiceFactory.getSyncService(getApplicationContext());
        JSONObject jsObj = new JSONObject();
        try{
            jsObj.put("user", username.getText().toString());
            jsObj.put("login_time",new SimpleDateFormat().format(new Date(System.currentTimeMillis())));
        }catch(org.json.JSONException e){
            e.printStackTrace();
        }

        new SyncTask(syncService, SyncService.Type.LOGIN).execute(jsObj);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}

