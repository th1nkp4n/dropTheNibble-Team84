package edu.gatech.shelterme.controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;


public class BadSingleCheckInAlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You have exceeded the shelter's individual capacity.")
                .setNegativeButton("Okay", (dialog,  id) ->{});
        return builder.create();
    }
}
