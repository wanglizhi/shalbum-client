package com.app.qin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import edu.nju.shalbum.R;

public class CameraFragement extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("发布照片")
               .setItems(R.array.camera, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                   // The 'which' argument contains the index position
                   // of the selected item
               }
        });
        return builder.create();
    }
}