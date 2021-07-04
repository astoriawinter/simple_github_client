package com.tatiana.rodionova.presentation.github_list.adapter.decorator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.tatiana.rodionova.presentation.R

class OffsetDividerItemDecoration(
    context: Context,
    private val offset: Int
) : RecyclerView.ItemDecoration() {
    private val drawable: Drawable? = AppCompatResources.getDrawable(
        context,
        R.drawable.shape_divider
    )
    private val bounds = Rect()

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state:
        RecyclerView.State
    ) {
        with(outRect) {
            top = offset / 2
            left = offset
            right = offset
            bottom = offset / 2
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager != null && drawable != null) {
            canvas.save()

            val childCount = parent.childCount

            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight

            for (i in 0 until childCount - 1) {
                val child = parent.getChildAt(i)
                parent.getDecoratedBoundsWithMargins(child, bounds)

                val top = bounds.bottom
                val bottom = top + drawable.intrinsicHeight

                drawable.setBounds(left, top, right, bottom)
                drawable.draw(canvas)
            }

            canvas.restore()
        }
    }
}