package com.homework.hw4_2_kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CustomButtonView(context: Context, attrs: AttributeSet) :
        View(context, attrs) {
    // Paint object for coloring and styling
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Some colors for the face background, eyes and mouth.
    private var greenColor = ContextCompat.getColor(context, R.color.green)
    private var greenColor2 = Color.GREEN
    private var yellowColor = R.color.yellow
    private var blueColor = R.color.blue
    private var redColor = R.color.red
    private var lilacColor = R.color.lilac
    private var pinkColor = R.color.pink
    private var aquamarineColor = R.color.aquamarine
    private var orangeColor = R.color.orange
    private var borderColor = R.color.black

    // Face border width in pixels
    private var borderWidth = 4.0f

    // View size in pixels
    private var size = 320

    override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
        super.onDraw(canvas)
        drawTopRight(canvas)
        drawBottomRight(canvas)
        drawBottomLeft(canvas)
        drawTopLeft(canvas)
        drawCenter(canvas)
    }

    //    @SuppressLint("ResourceAsColor")
    @SuppressLint("ResourceAsColor")
    private fun drawTopRight(canvas: Canvas) {
        paint.color = greenColor
        paint.style = Paint.Style.FILL
        val radius = size / 2f
        canvas.drawCircle(size / 2f, size / 2f, radius, paint);

        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth
        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)
    }

    private fun drawBottomRight(canvas: Canvas) {}
    private fun drawBottomLeft(canvas: Canvas) {}
    private fun drawTopLeft(canvas: Canvas) {}
    private fun drawCenter(canvas: Canvas) {}
}


