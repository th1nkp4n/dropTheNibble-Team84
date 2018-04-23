package edu.gatech.shelterme.controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class BadRegistrationRoleAlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Select a role to continue.")
                .setNegativeButton("Okay", (dialog, id) -> {});
        return builder.create();
    }
}
