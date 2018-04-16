package edu.gatech.shelterme.controllers;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class BadLoginAlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You have entered an incorrect Email or Password.")
                .setNegativeButton("Okay", (dialog,  id) ->{});
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
