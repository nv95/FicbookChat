package com.nv95.fbchat.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.nv95.fbchat.R;
import com.nv95.fbchat.utils.LayoutUtils;
import com.nv95.fbchat.utils.ThemeUtils;

/**
 * Created by nv95 on 14.08.16.
 */

public class EditTextDialog implements DialogInterface.OnClickListener{

    private final AlertDialog mDialog;
    private final EditText mEditText;
    private final OnTextChangedListener mListener;

    public EditTextDialog(Context context, @StringRes int title, OnTextChangedListener textListener) {
        mListener = textListener;
        View v =LayoutInflater.from(context)
                .inflate(R.layout.dialog_edit, null, false);
        mEditText = (EditText) v.findViewById(android.R.id.edit);
        //ThemeUtils.paintEditText(mEditText, ChatApp.getApplicationPalette());
        mDialog = new AlertDialog.Builder(context)
                .setView(v)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        mDialog.setOnShowListener(new ThemeUtils.DialogPainter());
    }

    public void show(@StringRes int hint, @Nullable String initialValue) {
        mEditText.setHint(hint);
        mEditText.setText(initialValue);
        mDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        String s = mEditText.getText().toString().trim();
        if (s.length() != 0) {
            mListener.onTextChanged(s);
        }
    }

    public EditTextDialog multiline(int lines) {
        mEditText.setInputType(EditorInfo.TYPE_TEXT_FLAG_IME_MULTI_LINE | EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES);
        mEditText.setSingleLine(false);
        mEditText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION );
        mEditText.setMinHeight(LayoutUtils.DpToPx(mDialog.getContext().getResources(), lines * 42));
        mEditText.setGravity(Gravity.TOP);
        return this;
    }

    public interface OnTextChangedListener {
        void onTextChanged(String newText);
    }
}
