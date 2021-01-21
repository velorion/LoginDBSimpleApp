package com.example.velor.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


public class OfflineAddDialogFragment extends DialogFragment {
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
        final MainActivity m = new MainActivity();

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
                        //m.populateListView();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        OfflineAddDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}