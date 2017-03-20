package com.shangbb.studysample.sample.customview.view;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.shangbb.studysample.R;

import java.util.List;


/**
 * 自定义软键盘 Created by Administrator on 2017/3/20.
 */

public class NumKeyboard {
    private Context mContext;
    private Activity mActivity;
    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard1; //中文键盘
    private Keyboard mKeyboard2; //数字键盘
    public boolean isNumber = false;
    public boolean isUpper = false; //是否大写
    private EditText mEditText;

    public NumKeyboard(Activity activity, Context context, EditText editText) {
        mContext = context;
        mActivity = activity;
        mEditText = editText;

        mKeyboard1 = new Keyboard(mContext, R.xml.chenese);
        mKeyboard2 = new Keyboard(mContext, R.xml.symbols);

        mKeyboardView = (KeyboardView) mActivity.findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(mKeyboard1);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(true);
        mKeyboardView.setOnKeyboardActionListener(listener);
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView
            .OnKeyboardActionListener() {


        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = mEditText.getText();
            int start = mEditText.getSelectionStart();

            if (primaryCode == Keyboard.KEYCODE_CANCEL){//完成
                hideKeyboard();
            }else if (primaryCode == Keyboard.KEYCODE_DELETE){
                if(editable != null && editable.length()>0){
                    if (start > 0){
                        editable.delete(start - 1, start);
                    }
                }
            }else if (primaryCode == Keyboard.KEYCODE_SHIFT) {//大小写切换
                changeKey();
                mKeyboardView.setKeyboard(mKeyboard1);

            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {//数字键盘切换
                if (isNumber) {
                    isNumber = false;
                    mKeyboardView.setKeyboard(mKeyboard1);
                } else {
                    isNumber = true;
                    mKeyboardView.setKeyboard(mKeyboard2);
                }
            } else if (primaryCode == 57419) { // go left
                if (start > 0) {
                    mEditText.setSelection(start - 1);
                }
            } else if (primaryCode == 57421) { // go right
                if (start < mEditText.length()) {
                    mEditText.setSelection(start + 1);
                }
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    /**
     * 键盘大小写切换
     */
    private void changeKey() {
        List<Key> keyList = mKeyboard1.getKeys();

        if (isUpper) { //大写切换小写
            isUpper = false;

            for (Key key : keyList) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {    //小写切换大写
            isUpper = true;
            for (Key key : keyList) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }

    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isword(String str) {
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.contains(str.toLowerCase())) {
            return true;
        }
        return false;
    }

    public void showChinese() {
        showKeyboard();
        isNumber = false;
        mKeyboardView.setKeyboard(mKeyboard1);
    }

    public void showNumber() {
        showKeyboard();
        isNumber = true;
        mKeyboardView.setKeyboard(mKeyboard2);
    }

}
