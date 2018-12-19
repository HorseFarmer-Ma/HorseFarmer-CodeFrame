/*
 * ************************************************************
 * Class：DialogUtils.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-19 14:44:26
 * Last modified time：2018-11-19 14:44:26
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.meizu.code.frame.R;

public class DialogUtils {

    public static AlertDialog createEtAlertDialog(@NonNull Context context, String title,
                                                  final OnEtClickListener onEtClickListener) {
        return createEtAlertDialog(context,
                title,
                CodeFrameStaticResUtils.getString(R.string.text_ok),
                CodeFrameStaticResUtils.getString(R.string.text_cancel),
                onEtClickListener);
    }

    public static AlertDialog createEtAlertDialog(@NonNull Context context, String title, String positiveText,
                                                  String negativeText, final OnEtClickListener onEtClickListener) {
        final EditText editText = new EditText(context);
        int margin = CodeFrameStaticResUtils.getDimensionPixelOffset(R.dimen.common_20dp);
        FrameLayout layout = new FrameLayout(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(margin, margin, margin, 0);
        layout.addView(editText, layoutParams);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setTitle(title)
                .setView(layout)
                .setCancelable(false)
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Editable text = editText.getText();
                        if (onEtClickListener != null) {
                            onEtClickListener.onDetermine(text == null ? null : text.toString());
                        }
                        editText.setText("");
                        dialog.cancel();
                    }
                })
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onEtClickListener != null) {
                            onEtClickListener.onCancel();
                        }
                        editText.setText("");
                        dialog.cancel();
                    }
                })
                .create();
    }

    public abstract static class OnEtClickListener {
        public void onDetermine(String text) {
        }

        public void onCancel() {
        }
    }

    public abstract static class OnChooseClickListener {
        public void onDetermine() {
        }

        public void onCancel() {
        }
    }

    public static AlertDialog createChooseAlertDialog(@NonNull Context context, String title, String positiveText,
                                                      String negativeText, final OnChooseClickListener onChooseClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setTitle(title)
                .setCancelable(false)
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (onChooseClickListener != null) {
                            onChooseClickListener.onDetermine();
                        }
                        dialog.cancel();
                    }
                })
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onChooseClickListener != null) {
                            onChooseClickListener.onCancel();
                        }
                        dialog.cancel();
                    }
                })
                .create();
    }
}
