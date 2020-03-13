package edu.msu.project1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewUserActivity extends AppCompatActivity {

    private static final String MAGIC = "NechAtHa6RuzeR8x";
    private static final String LOAD_URL = "http://webdev.cse.msu.edu/~ferna194/cse476/project2/users.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    public void createUser(View view) {
        StrictMode.enableDefaults();

        EditText userId = findViewById(R.id.useridText);
        EditText password = findViewById(R.id.passwordText);

        String p1 = userId.getText().toString();
        String p2 = password.getText().toString();

        if ( TextUtils.isEmpty(p1) || TextUtils.isEmpty(p2)) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Error")
                    .setMessage("Username and/or password cannot be empty. Please try again.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        }

        String result = checkLogin(p1,p2);

        if (result.equals("SUCCESS")) {
            Intent myIntent = new Intent(this, MainActivity.class);
            myIntent.putExtra("userId", p1);
            startActivity(myIntent);
        } else {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Error")
                    .setMessage("Failed to create user. Please try again.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    private String checkLogin(String userId, String password) {
        // Create a get query


        String query = LOAD_URL + "?user=" + userId + "&magic=" + MAGIC + "&pw=" + password + "&action=register";

        try {
            URL url = new URL(query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream stream = conn.getInputStream();

            return readStream(stream);

        } catch (MalformedURLException e) {
            // Should never happen
            return null;
        } catch (IOException ex) {
            return null;
        }
    }
    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}
