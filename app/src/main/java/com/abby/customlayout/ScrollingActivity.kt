package com.abby.customlayout

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ScrollingActivity : AppCompatActivity() {

  lateinit var mInputFilter: PhoneInputFilter

  lateinit var mEditText: AppCompatEditText

  private var isChange = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scrolling)
    isChange = true
    mInputFilter = CNinputFilter()
    mEditText = findViewById(R.id.phone_input_edit_text)
    setSupportActionBar(findViewById(R.id.toolbar))
    findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
    findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//        .setAction("Action", null).show()
      startActivity(Intent(this, CustomActivity::class.java))
    }

    mEditText.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (isChange) {
          val text = s.toString()
          if (TextUtils.isEmpty(text)) {
            return
          }
          if (count != 0) {
            setTextNoChange(mInputFilter.onFilter(text))
          } else if (' ' == text.get(text.length - 1)) {
            setTextWitchDelete(mInputFilter.onFilter(text.substring(0, text.length - 1)), 1);
          } else {
            setTextWitchDelete(mInputFilter.onFilter(text), 0);
          }
        } else {
//          setDeleteShow(!TextUtils.isEmpty(charSequence.toString()))
        }
      }

      override fun afterTextChanged(s: Editable?) = Unit

    })
  }


  fun getPhoneNumber(): String? {
    val text = mEditText.text ?: return ""
    val charArray = text.toString().toCharArray()
    val sb = StringBuilder()
    for (c in charArray) {
      if (' ' != c) {
        sb.append(c)
      }
    }
    return sb.toString()
  }

  /**
   * 点击删除符号可以调用这个
   */
  private fun setTextNoChange(content: String) {
    isChange = false
    mEditText.setText(content)
    mEditText.setSelection(content.length)

    isChange = true
  }

  private fun setTextWitchDelete(content: String, i: Int) {
    isChange = false
    val selectionStart: Int = mEditText.selectionStart
    mEditText.setText(content)
    val i2: Int = selectionStart - i
    if (i2 >= 0) {
      mEditText.setSelection(i2)
    } else {
      mEditText.setSelection(0)
    }

    isChange = true
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_scrolling, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.

    return when (item.itemId) {
      R.id.action_settings -> true
      else -> super.onOptionsItemSelected(item)
    }
  }
}