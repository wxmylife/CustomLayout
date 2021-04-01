package com.abby.customlayout;

import android.text.TextUtils;

public class CNinputFilter implements PhoneInputFilter {

  public static final String CODE_NAME = "CN";
  public static final String CODE_NUMBER = "86";
  public static final int[] index = { 3, 7 };
  private static final int maxLength = 12;
  private static final int validLength = 12;
  public boolean isValid = false;

  @Override public String getPhonePrefix() {
    return CODE_NUMBER;
  }

  @Override public boolean isValid() {
    return isValid;
  }

  @Override public boolean isValid(String str) {
    boolean z = false;
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    if (str.length() == 13) {
      z = true;
    }
    this.isValid = z;
    return this.isValid;
  }

  @Override public String onFilter(String input) {
    System.out.println("text" + input);
    boolean isMobile = false;

    // 如果为空则验证失败
    if (TextUtils.isEmpty(input)) {
      this.isValid = false;
      return "";
    }

    // 循环遍历拿到手机号
    char[] inputArray = input.toCharArray();
    StringBuilder sb = new StringBuilder();
    for (char c : inputArray) {
      if (c != ' ') {
        sb.append(c);
      }
    }

    char[] phoneArray = sb.toString().toCharArray();
    StringBuilder inputBuilder = new StringBuilder();
    int emptyCount = 0;
    for (int i = 0; i < phoneArray.length; i++) {
      System.out.println(phoneArray[i]);
      inputBuilder.append(phoneArray[i]);

      // 如果出现空格数 小于 添加空格下标集合 && 遍历的下标是要添加的位置 && 并且不是最后一个位置
      if (emptyCount < index.length && i == index[emptyCount] - 1 && i != phoneArray.length - 1) {
        inputBuilder.append(' ');
        emptyCount++;
      }
    }

    // 将输入长度控制在13位
    String content = inputBuilder.toString();
    if (content.length() > 12) {
      content = content.substring(0, 13);
    }
    if (content.length() == 13) {
      isMobile = true;
    }
    this.isValid = isMobile;
    System.out.println("aaa" + content);
    return content;
  }
}
