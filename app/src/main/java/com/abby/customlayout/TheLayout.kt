package com.abby.customlayout

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TheLayout(context: Context) : CustomLayout(context) {


  val header = AppCompatImageView(context).apply {
    scaleType = ImageView.ScaleType.CENTER_CROP
    setImageResource(R.drawable.header)
    layoutParams = LayoutParams(MATCH_PARENT, 200.dp)
    addView(this)
  }

  val fab = FloatingActionButton(context).apply {
    setImageResource(R.drawable.ic_baseline_swap_horiz_24)
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).also {
      it.marginEnd = 12.dp
    }
    addView(this)
  }

  val share = AppCompatImageView(context).apply {
    setImageResource(androidx.appcompat.R.drawable.abc_ic_menu_share_mtrl_alpha)
    layoutParams = LayoutParams(24.dp, 24.dp).also {
      it.setMargins(24.dp)
    }
    addView(this)
  }

  val avatar = AppCompatImageView(context).apply {
    scaleType = ImageView.ScaleType.CENTER_CROP
    setImageResource(R.mipmap.ic_launcher_round)
    layoutParams = LayoutParams(48.dp, 48.dp).also {
      it.topMargin = 24.dp
      it.marginStart = 16.dp
    }
    addView(this)
  }

  val title = AppCompatTextView(context).apply {
//    setTextAppearance(R.style.TextAppearance_AppCompat_T)
    textSize = 16F
    setText(R.string.title_text)
    setTextColor(Color.BLACK)
    includeFontPadding = false
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).also {
      it.topMargin = 12.dp
      it.marginStart = 16.dp
      it.marginEnd = 16.dp
    }
    setBackgroundColor(Color.RED)
    addView(this)
  }

  val message = AppCompatTextView(context).apply {
    textSize = 13F
    setText(R.string.content_text)
    setTextColor(Color.GRAY)
    includeFontPadding = false
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).also {
      it.marginStart = 16.dp
      it.marginEnd = 16.dp
    }
    addView(this)
  }


  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    // 具体值
    // 最大值
    // 不要求
    /*header.measure(
      header.defaultWidthMeasureSpec(parentView = this),
      header.defaultHeightMeasureSpec(parentView = this)
    )*/

    /*header.let {
      it.measure(
        it.defaultWidthMeasureSpec(parentView = this),
        it.defaultHeightMeasureSpec(parentView = this)
      )
    }*/

    header.autoMeasure()
    fab.autoMeasure()
    avatar.autoMeasure()
    share.autoMeasure()

    val itemTextWidth = (measuredWidth
        - avatar.measureWidthWithMargins
        - share.measureWidthWithMargins
        - title.marginLeft
        - title.marginRight)

    title.measure(itemTextWidth.toExactlyMeasureSpec(), title.defaultHeightMeasureSpec(this))
    message.measure(itemTextWidth.toExactlyMeasureSpec(), message.defaultHeightMeasureSpec(this))

    val max = (avatar.marginTop + title.measureHeightWithMargins + message.measureHeightWithMargins).coerceAtLeast(avatar.measureHeightWithMargins)
    val wrapContentHeight = header.measureHeightWithMargins + max
    setMeasuredDimension(measuredWidth, wrapContentHeight)
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    /* header.let {
       it.layout(0, 0, it.measuredWidth, it.measuredHeight)
     }*/

    header.layout(x = 0, y = 0)
    fab.let {
      it.layout(x = it.marginRight, y = header.bottom - (it.measuredHeight / 2), fromRight = true)
    }
    avatar.let {
      it.layout(x = it.marginLeft, y = header.bottom + it.marginTop)
    }

    title.let {
      it.layout(x = avatar.right + it.marginLeft, y = avatar.top + it.marginTop)
    }
    message.let {
      it.layout(x = avatar.right + it.marginLeft, y = title.bottom + it.marginTop)
    }
    share.let {
      it.layout(x = it.marginRight, y = avatar.top + it.marginTop, fromRight = true)
    }
  }

}


