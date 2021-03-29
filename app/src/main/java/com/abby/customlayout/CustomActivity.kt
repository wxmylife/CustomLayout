package com.abby.customlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CustomActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    val rootView = layoutInflater.inflate(R.layout.activity_custom, null) as ViewGroup
//    rootView.addView(TheLayout(this))
    setContentView(TheLayout(this))
  }

}