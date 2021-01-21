package com.example.velor.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class AddDialogFragment extends DialogFragment {
    EditText name, discipline, grade;
    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        // Get the layout inflater
        final Context context = builder.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.dialog_add, null);

        name = view.findViewById(R.id.et1);
        discipline = view.findViewById(R.id.et2);
        grade = view.findViewById(R.id.et3);

        final SimpleDatabaseHelper sql = new SimpleDatabaseHelper(requireActivity());

        // Inflate and set the layout for the dialog
        builder.setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String n = name.getText().toString();
                        String d = discipline.getText().toString();
                        String g = grade.getText().toString();
                        sql.add(n, d, g);
                        ServerAdd sadd = new ServerAdd(n,d,g);
                        sadd.execute((Void)null);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public class ServerAdd extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final String mDiscipline;
        private final String mGrade;

        ServerAdd(String name, String discipline, String grade) {
            mName = name;
            mDiscipline = discipline;
            mGrade = grade;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                //Thread.sleep(2000);
                String link="http://myapptest.freetzi.com/add.php";
                String data  = URLEncoder.encode("name", "UTF-8") + "=" +
                        URLEncoder.encode(mName, "UTF-8");
                data += "&" + URLEncoder.encode("discipline", "UTF-8") + "=" +
                        URLEncoder.encode(mDiscipline, "UTF-8");
                data += "&" + URLEncoder.encode("grade", "UTF-8") + "=" +
                        URLEncoder.encode(mGrade, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.close();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line;

                // Read Server Response
                if((line = reader.readLine()) != null) {
                    sb.append(line);
                    line = reader.readLine();
                    sb.append(line);
                    line = reader.readLine();
                    sb.append(line);
                }

                Log.d("AddServer", sb.toString());
                return true;
            } catch (Exception e) {
                Log.d("AddServer", e.getMessage());
                return false;
            }
        }
    }
}