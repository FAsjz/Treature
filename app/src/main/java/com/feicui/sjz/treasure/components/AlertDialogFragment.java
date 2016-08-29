package com.feicui.sjz.treasure.components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Administrator on 16-7-13.
 */
public class AlertDialogFragment extends DialogFragment {

    public static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    public static AlertDialogFragment instance(String resTitle,String msg){
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE,resTitle);
        bundle.putString(KEY_MESSAGE, msg);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity(),getTheme())
                .setTitle(getArguments().getString(KEY_TITLE))
                .setMessage(getArguments().getString(KEY_MESSAGE))
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();


    }
}
