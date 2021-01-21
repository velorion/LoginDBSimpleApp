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


public class DeleteDialogFragment extends DialogFragment {
    EditText iid;
    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        // Get the layout inflater
        final Context context = builder.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.dialog_delete, null);

        iid = view.findViewById(R.id.et1);

        final SimpleDatabaseHelper sql = new SimpleDatabaseHelper(requireActivity());

        // Inflate and set the layout for the dialog
        builder.setView(view)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //String id = id.getText().toString();
                        Long item_id = Long.valueOf(iid.getText().toString());
                        sql.delete(item_id);
                        ServerDelete sdel = new ServerDelete(item_id);
                        sdel.execute((Void)null);
                        //m.populateListView();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public class ServerDelete extends AsyncTask<Void, Void, Boolean> {

        private final Long mID;

        ServerDelete(Long iid) {
            mID = iid;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                //Thread.sleep(2000);
                String link="http://myapptest.freetzi.com/delete.php";
                String data  = URLEncoder.encode("id", "UTF-8") + "=" +
                        URLEncoder.encode(mID.toString(), "UTF-8");

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

                Log.d("DeleteServer", sb.toString());
                return true;
            } catch (Exception e) {
                Log.d("DeleteServer", e.getMessage());
                return false;
            }
        }
    }
}
