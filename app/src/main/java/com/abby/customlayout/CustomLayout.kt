package com.abby.customlayout

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

@Suppress("MemberVisibilityCanBePrivate")
abstract class CustomLayout(context: Context) : ViewGroup(context) {

  protected fun View.autoMeasure() {
    measure(
      this.defaultWidthMeasureSpec(parentView = this@CustomLayout),
      this.defaultHeightMeasureSpec(parentView = this@CustomLayout)
    )
  }

  protected fun View.layout(x: Int, y: Int, fromRight: Boolean = false) {
    if (fromRight) {
      layout(this@CustomLayout.measuredWidth - x - measuredWidth, y)
    } else {
      layout(x, y, x + measuredWidth, y + measuredHeight)
    }
  }

  protected val View.measureWidthWithMargins get() = (measuredWidth + marginLeft + marginRight)
  protected val View.measureHeightWithMargins get() = (measuredHeight + marginTop + marginBottom)

  protected fun View.defaultWidthMeasureSpec(parentView: ViewGroup): Int {
    return when (layoutParams.width) {
      ViewGroup.LayoutParams.MATCH_PARENT -> parentView.measuredWidth.toExactlyMeasureSpec()
      ViewGroup.LayoutParams.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT.toAtMostMeasureSpec()
      0 -> throw IllegalAccessException("Need special treatment for $this")
      else -> layoutParams.width.toExactlyMeasureSpec()
    }
  }

  protected fun View.defaultHeightMeasureSpec(parentView: ViewGroup): Int {
    return when (layoutParams.height) {
      ViewGroup.LayoutParams.MATCH_PARENT -> parentView.measuredHeight.toExactlyMeasureSpec()
      ViewGroup.LayoutParams.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT.toAtMostMeasureSpec()
      0 -> throw IllegalAccessException("Need special treatment for $this")
      else -> layoutParams.height.toExactlyMeasureSpec()
    }
  }

  protected fun Int.toExactlyMeasureSpec(): Int {
    return MeasureSpec.makeMeasureSpec(this, View.MeasureSpec.EXACTLY)
  }

  protected fun Int.toAtMostMeasureSpec(): Int {
    return MeasureSpec.makeMeasureSpec(this, View.MeasureSpec.AT_MOST)
  }

  protected val Int.dp: Int get() = (this * resources.displayMetrics.density + 0.5f).toInt()

  protected class LayoutParams(width: Int, height: Int) : MarginLayoutParams(width, height)
}