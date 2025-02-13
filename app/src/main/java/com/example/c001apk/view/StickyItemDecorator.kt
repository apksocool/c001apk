package com.example.c001apk.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.appcompat.widget.ThemeUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.absinthe.libraries.utils.extensions.dp

@SuppressLint("RestrictedApi")
class StickyItemDecorator(
    context: Context,
    private val space: Int,
    private var itemCount: Int = 1,
    private val listener: SortShowListener
) :
    RecyclerView.ItemDecoration() {

    private var mPaint: Paint = Paint()

    init {
        mPaint.isAntiAlias = true
        mPaint.color =
            ThemeUtils.getThemeAttrColor(
                context,
                com.google.android.material.R.attr.colorSurfaceVariant
            )
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(view)
            if (index == itemCount - 1) {
                val dividerTop = view.bottom.toFloat() + 12.dp
                val dividerLeft = parent.paddingLeft
                val dividerBottom = view.bottom + 12.dp + 1
                val dividerRight = parent.width - parent.paddingRight
                c.drawRect(
                    dividerLeft.toFloat(), dividerTop, dividerRight.toFloat(),
                    dividerBottom.toFloat(), mPaint
                )
            } else if (index > itemCount) {
                val dividerTop = view.top.toFloat() - 1
                val dividerLeft = parent.paddingLeft
                val dividerBottom = view.top
                val dividerRight = parent.width - parent.paddingRight
                c.drawRect(
                    dividerLeft.toFloat(), dividerTop, dividerRight.toFloat(),
                    dividerBottom.toFloat(), mPaint
                )
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (parent.adapter!!.itemCount <= 0)
            return
        val index = (parent.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
        if (index >= itemCount) {
            listener.showSort(true)
        } else {
            listener.showSort(false)
        }
    }

    interface SortShowListener {
        fun showSort(show: Boolean)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position <= itemCount) {
            if (position == 0)
                outRect.top = 12.dp
            if (position < itemCount)
                outRect.bottom =
                    if (position == itemCount - 1) 12.dp + 1
                    else 12.dp
            outRect.left = 15.dp
            outRect.right = 15.dp
        }
        if (position > itemCount)
            outRect.top = 1
    }

}