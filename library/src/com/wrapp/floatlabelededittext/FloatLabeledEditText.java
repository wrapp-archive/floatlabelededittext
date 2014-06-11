package com.wrapp.floatlabelededittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;

public class FloatLabeledEditText extends LinearLayout {

    private String hint;
    private int inputType;
    private int imeOptions;
    private int imeActionId;
    private String imeActionLabel;
    private boolean singleLine;
    private ColorStateList hintColor;
    private ColorStateList textColor;

    private TextView hintTextView;
    private EditText editText;

    private Context mContext;

    public FloatLabeledEditText(Context context) {
        super(context);
        mContext = context;
        initialize();
    }

    public FloatLabeledEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setAttributes(attrs);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public FloatLabeledEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setAttributes(attrs);
        initialize();
    }

    private void setAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FloatLabeledEditText);
        inputType = EditorInfo.TYPE_NULL;
        try {
            hint = a.getString(R.styleable.FloatLabeledEditText_fletFloatingHint);
            inputType = a.getInt(R.styleable.FloatLabeledEditText_fletInputType, EditorInfo.TYPE_NULL);
            imeOptions = a.getInt(R.styleable.FloatLabeledEditText_fletImeOptions, EditorInfo.IME_ACTION_DONE);
            imeActionId = a.getInt(R.styleable.FloatLabeledEditText_fletImeActionId, -1);
            imeActionLabel = a.getString(R.styleable.FloatLabeledEditText_fletImeActionLabel);
            singleLine = a.getBoolean(R.styleable.FloatLabeledEditText_fletSingleLine, false);
            hintColor = a.getColorStateList(R.styleable.FloatLabeledEditText_fletHintTextColor);
            textColor = a.getColorStateList(R.styleable.FloatLabeledEditText_fletTextColor);
        } finally {
            a.recycle();
        }
    }

    private void initialize() {
        setOrientation(VERTICAL);
        if (isInEditMode()) {
            return;
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.widget_float_labeled_edit_text, this);

        hintTextView = (TextView) view.findViewById(R.id.FloatLabeledEditTextHint);
        editText = (EditText) view.findViewById(R.id.FloatLabeledEditTextEditText);

        if (hint != null) {
            setHint(hint);
        }

        editText.setImeOptions(imeOptions);

        if (imeActionId > -1 && !TextUtils.isEmpty(imeActionLabel)) {
            editText.setImeActionLabel(imeActionLabel, imeActionId);
        }

        editText.setSingleLine(singleLine);
        hintTextView.setTextColor(hintColor != null ? hintColor : ColorStateList.valueOf(Color.BLACK));
        editText.setTextColor(textColor != null ? textColor : ColorStateList.valueOf(Color.BLACK));

        if (inputType != EditorInfo.TYPE_NULL) {
            editText.setInputType(inputType);
        }

        hintTextView.setVisibility(View.INVISIBLE);
        AnimatorProxy.wrap(hintTextView).setAlpha(0); //Need this for compat reasons
        editText.addTextChangedListener(onTextChanged);
        editText.setOnFocusChangeListener(onFocusChanged);
    }

    private TextWatcher onTextChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            setShowHint(editable.length() != 0);
        }
    };

    private OnFocusChangeListener onFocusChanged = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean gotFocus) {
            if (gotFocus && hintTextView.getVisibility() == VISIBLE) {
                ObjectAnimator.ofFloat(hintTextView, "alpha", 0.33f, 1f).start();
            } else if (hintTextView.getVisibility() == VISIBLE){
                AnimatorProxy.wrap(hintTextView).setAlpha(1f);  //Need this for compat reasons
                ObjectAnimator.ofFloat(hintTextView, "alpha", 1f, 0.33f).start();
            }
        }
    };

    private void setShowHint(final boolean show) {
        AnimatorSet animation = null;
        if ((hintTextView.getVisibility() == VISIBLE) && !show) {
            animation = new AnimatorSet();
            ObjectAnimator move = ObjectAnimator.ofFloat(hintTextView, "translationY", 0, hintTextView.getHeight() / 8);
            ObjectAnimator fade = ObjectAnimator.ofFloat(hintTextView, "alpha", 1, 0);
            animation.playTogether(move, fade);
        } else if ((hintTextView.getVisibility() != VISIBLE) && show) {
            animation = new AnimatorSet();
            ObjectAnimator move = ObjectAnimator.ofFloat(hintTextView, "translationY", hintTextView.getHeight() / 8, 0);
            ObjectAnimator fade = ObjectAnimator.ofFloat(hintTextView, "alpha", 0, 1);
            animation.playTogether(move, fade);
        }

        if (animation != null) {
            animation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    hintTextView.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    hintTextView.setVisibility(show ? VISIBLE : INVISIBLE);
                }
            });
            animation.start();
        }
    }

    public EditText getEditText() {
        return editText;
    }

    public void setHint(String hint) {
        this.hint = hint;
        editText.setHint(hint);
        hintTextView.setText(hint);
    }

    public String getHint() {
        return editText.getHint().toString();
    }

    public Editable getText() {
        return editText.getText();
    }

    /**
     * Sets the text on the EditText. See {@link android.widget.EditText#setText(CharSequence)}.
     *
     * @param text
     */
    public void setText(CharSequence text) {
        editText.setText(text);
    }

    /**
     * See {@link android.widget.EditText#setText(CharSequence, android.widget.TextView.BufferType)}.
     *
     * @param text
     * @param type
     */
    public void setText(CharSequence text, TextView.BufferType type) {
        editText.setText(text, type);
    }

    /**
     * Convenience for {@link #setText} to get the {@link java.lang.String}
     * of what is in the EditText.
     */
    public String getTextString() {
        return editText.getText().toString();
    }

    /**
     * See {@link android.widget.TextView#setError(CharSequence)}.
     */
    public void setError(CharSequence text) {
        editText.setError(text);
    }

    /**
     * See {@link android.widget.TextView#setError(CharSequence, android.graphics.drawable.Drawable)}.
     */
    public void setError(CharSequence text, Drawable icon) {
        editText.setError(text, icon);
    }

    /**
     * See {@link android.widget.TextView#setError(CharSequence)}.
     *
     * @param resourceId
     */
    public void setErrorResource(int resourceId) {
        editText.setError(mContext.getString(resourceId));
    }

    /**
     * See {@link android.widget.TextView#setError(CharSequence, Drawable)}.
     *
     * @param resourceId
     */
    public void setErrorResource(int resourceId, Drawable icon) {
        editText.setError(mContext.getString(resourceId), icon);
    }

    /**
     * See {@link android.widget.EditText#setImeActionLabel(CharSequence, int)}.
     */
    public void setImeActionLabel(CharSequence label, int actionId) {
        editText.setImeActionLabel(label, actionId);
    }

    /**
     * See {@link android.widget.EditText#setEllipsize(android.text.TextUtils.TruncateAt)}.
     */
    public void setEllipsize(TextUtils.TruncateAt ellipsize) {
        editText.setEllipsize(ellipsize);
    }

    /**
     * See {@link android.widget.EditText#setSelection(int)}.
     */
    public void setSelection(int index) {
        editText.setSelection(index);
    }

    /**
     * See {@link android.widget.EditText#setSelection(int, int)}.
     */
    public void setSelection(int start, int stop) {
        editText.setSelection(start, stop);
    }

    /**
     * Sets the {@link android.widget.TextView.OnEditorActionListener}.
     *
     * @param listener
     */
    public void setOnEditorActionListener(TextView.OnEditorActionListener listener) {
        editText.setOnEditorActionListener(listener);
    }

    /**
     * Requests focus on the EditText.
     */
    public void requestFieldFocus() {
        editText.requestFocus();
    }

    public void setTextColor(int color) {
        editText.setTextColor(color);
    }

    public void setTextColor(ColorStateList colors) {
        editText.setTextColor(colors);
    }

    public void setHintTextColor(ColorStateList colors) {
        hintTextView.setTextColor(colors);
    }

    public void setHintTextColor(int color) {
        hintTextView.setTextColor(color);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        if (isIcsOrAbove()) {
            super.onInitializeAccessibilityEvent(event);
            editText.onInitializeAccessibilityEvent(event);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        if (isIcsOrAbove()) {
            super.onInitializeAccessibilityNodeInfo(info);
            editText.onInitializeAccessibilityNodeInfo(info);
        }
    }

    /**
     * See {@link android.widget.EditText#selectAll()}.
     */
    public void selectAll() {
        editText.selectAll();
    }

    /**
     * See {@link android.widget.EditText#extendSelection(int)}.
     */
    public void extendSelection(int index) {
        editText.extendSelection(index);
    }

    // Dealing with saving the state

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        FloatEditTextSavedState ss = new FloatEditTextSavedState(parcelable);
        ss.hint = hint;
        ss.inputType = inputType;
        ss.imeOptions = imeOptions;
        ss.imeActionId = imeActionId;
        ss.imeActionLabel = imeActionLabel;
        ss.singleLine = singleLine;
        ss.text = editText.getText().toString();
        ss.hintColor = hintColor;
        ss.textColor = textColor;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof FloatEditTextSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        FloatEditTextSavedState ss = (FloatEditTextSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        inputType = ss.inputType;
        imeOptions = ss.imeOptions;
        hint = ss.hint;
        String text = ss.text;
        if (!TextUtils.isEmpty(text)) {
            editText.setText(text);
        }
    }

    private static boolean isIcsOrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    static class FloatEditTextSavedState extends BaseSavedState {
        String text;
        String hint;
        int inputType;
        int imeOptions;
        int imeActionId;
        String imeActionLabel;
        boolean singleLine;
        ColorStateList hintColor;
        ColorStateList textColor;

        FloatEditTextSavedState(Parcelable superState) {
            super(superState);
        }

        private FloatEditTextSavedState(Parcel in) {
            super(in);
            text = in.readString();
            hint = in.readString();
            inputType = in.readInt();
            imeOptions = in.readInt();
            imeActionId = in.readInt();
            imeActionLabel = in.readString();
            singleLine = in.readInt() == 1;
            hintColor = in.readParcelable(ColorStateList.class.getClassLoader());
            textColor = in.readParcelable(ColorStateList.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(text);
            out.writeString(hint);
            out.writeInt(inputType);
            out.writeInt(imeOptions);
            out.writeInt(imeActionId);
            out.writeString(imeActionLabel);
            out.writeInt(singleLine ? 1 : 0);
            out.writeParcelable(hintColor, flags);
            out.writeParcelable(textColor, flags);
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<FloatEditTextSavedState> CREATOR =
                new Parcelable.Creator<FloatEditTextSavedState>() {
                    public FloatEditTextSavedState createFromParcel(Parcel in) {
                        return new FloatEditTextSavedState(in);
                    }

                    public FloatEditTextSavedState[] newArray(int size) {
                        return new FloatEditTextSavedState[size];
                    }
                };
    }
}
