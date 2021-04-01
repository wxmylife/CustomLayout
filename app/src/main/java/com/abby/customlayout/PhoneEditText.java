package com.abby.customlayout;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import java.util.Arrays;

public class PhoneEditText extends AppCompatEditText {

  private static final String SPACE = " ";
  private static final int[] DEFAULT_PATTERN = new int[] { 3, 4, 4 };

  private OnTextChangeListener listener;
  private TextWatcher mTextWatcher;
  private int preLength;
  private int[] pattern; // 模板
  private int[] intervals; // 根据模板控制分隔符的插入位置
  private String separator = SPACE; // 默认使用空格分割
  // 根据模板自动计算最大输入长度，超出输入无效。使用pattern时无需在xml中设置maxLength属性，若需要设置时应注意加上分隔符的数量
  private int maxLength;

  public PhoneEditText(Context context) {
    super(context);
    init();
  }

  public PhoneEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PhoneEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    // 如果设置 inputType="number" 的话是没法插入空格的，所以强行转为inputType="phone"
    if (getInputType() == InputType.TYPE_CLASS_NUMBER) { setInputType(InputType.TYPE_CLASS_PHONE); }
    setPattern(DEFAULT_PATTERN);

    mTextWatcher = new MyTextWatcher();
    this.addTextChangedListener(mTextWatcher);
  }

  /**
   * 自定义分割模板
   *
   * @param pattern 每一段的字符个数的数组
   */
  public void setPattern(@NonNull int[] pattern) {
    if (pattern == null) {
      throw new IllegalArgumentException("pattern can't be null !");
    }
    this.pattern = pattern;

    intervals = new int[pattern.length];
    int count = 0;
    int sum = 0;
    for (int i = 0; i < pattern.length; i++) {
      sum += pattern[i];
      intervals[i] = sum + count;
      if (i < pattern.length - 1) { count++; }
    }
    maxLength = intervals[intervals.length - 1];
    Log.e("xox", Arrays.toString(intervals));
    Log.e("xox", "maxLength ---" + maxLength);
  }

  /**
   * 自定义分隔符
   */
  public void setSeparator(@NonNull String separator) {
    if (separator == null) {
      throw new IllegalArgumentException("separator can't be null !");
    }
    this.separator = separator;
  }

  /**
   * 输入待转换格式的字符串
   */
  public void setTextToSeparate(CharSequence c) {
    if (c == null || c.length() == 0) { return; }

    setText("");
    for (int i = 0; i < c.length(); i++) {
      append(c.subSequence(i, i + 1));
    }
  }

  /**
   * 获得除去分割符的输入框内容
   */
  public String getNonSeparatorText() {
    return getText().toString().replaceAll(separator, "");
  }

  // =========================== MyTextWatcher ================================
  private class MyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      preLength = s.length();
      if (listener != null) { listener.beforeTextChanged(s, start, count, after); }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
       int currLength = s.length();
      // if (isNoSeparator) maxLength = currLength;

      if (currLength > maxLength) {
        getText().delete(currLength - 1, currLength);
        return;
      }

      for (int i = 0; i < pattern.length; i++) {
        if (currLength == intervals[i]) {
          if (currLength > preLength) { // 正在输入
            if (currLength < maxLength) {
              removeTextChangedListener(mTextWatcher);
              mTextWatcher = null;
              getText().insert(currLength, separator);
            }
          } else if (preLength <= maxLength) { // 正在删除
            removeTextChangedListener(mTextWatcher);
            mTextWatcher = null;
            getText().delete(currLength - 1, currLength);
          }

          if (mTextWatcher == null) {
            mTextWatcher = new MyTextWatcher();
            addTextChangedListener(mTextWatcher);
          }

          break;
        }
      }

      if (listener != null) { listener.onTextChanged(s, start, before, count); }
    }

    @Override
    public void afterTextChanged(Editable s) {

      if (listener != null) { listener.afterTextChanged(s); }
    }
  }

  public void setOnTextChangeListener(OnTextChangeListener listener) {
    this.listener = listener;
  }

  public interface OnTextChangeListener {

    void beforeTextChanged(CharSequence s, int start, int count, int after);

    void onTextChanged(CharSequence s, int start, int before, int count);

    void afterTextChanged(Editable s);
  }
}

// <com.xueersi.parentsmeeting.module.fusionlogin.widget.phoneinput.PhoneInputEdit
// <com.xueersi.parentsmeeting.module.fusionlogin.widget.AuthCodeLayout
// <com.xueersi.parentsmeeting.module.fusionlogin.widget.LogInTextView