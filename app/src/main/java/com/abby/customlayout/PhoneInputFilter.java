package com.abby.customlayout;

public interface PhoneInputFilter {

  String getPhonePrefix();

  boolean isValid();

  boolean isValid(String str);

  String onFilter(String str);
}
