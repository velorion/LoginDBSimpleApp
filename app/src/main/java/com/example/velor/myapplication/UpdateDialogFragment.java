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


public class UpdateDialogFragment extends DialogFragment {
    EditText iid, name, discipline, grade;
    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        // Get the layout inflater
        final Context context = builder.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.dialog_update, null);

        iid = view.findViewById(R.id.et0);
        name = view.findViewById(R.id.et1);
        discipline = view.findViewById(R.id.et2);
        grade = view.findViewById(R.id.et3);

        final SimpleDatabaseHelper sql = new SimpleDatabaseHelper(requireActivity());

        // Inflate and set the layout for the dialog
        builder.setView(view)
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Long item_id = Long.valueOf(iid.getText().toString());
                        String n = name.getText().toString();
                        String d = discipline.getText().toString();
                        String g = grade.getText().toString();
                        sql.update(item_id, n, d, g);
                        ServerUpdate supd = new ServerUpdate(item_id,n,d,g);
                        supd.execute((Void)null);
                        //m.populateListView();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UpdateDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public class ServerUpdate extends AsyncTask<Void, Void, Boolean> {

        private final Long mID;
        private final String mName;
        private final String mDiscipline;
        private final String mGrade;

        ServerUpdate(Long iid, String name, String discipline, String grade) {
            mID = iid;
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
                String link="http://myapptest.freetzi.com/update.php";
                String data  = URLEncoder.encode("id", "UTF-8") + "=" +
                        URLEncoder.encode(mID.toString(), "UTF-8");
                data += "&" + URLEncoder.encode("name", "UTF-8") + "=" +
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
                }

                Log.d("UpdateServer", sb.toString());
                return true;
            } catch (Exception e) {
                Log.d("UpdateServer", e.getMessage());
                return false;
            }
        }
    }
}
