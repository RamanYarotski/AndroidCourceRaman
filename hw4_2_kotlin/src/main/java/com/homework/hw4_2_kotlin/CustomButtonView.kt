package com.homework.hw4_2_kotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

interface myClicklListener {
    fun action ()
}

class CustomButtonView(context: Context, attrs: AttributeSet) :
        View(context, attrs) {
    companion object {
        private const val DEFAULT_FIRST_QUARTER_COLOR = Color.GREEN
        private const val DEFAULT_SECOND_QUARTER_COLOR = Color.YELLOW
        private const val DEFAULT_THIRD_QUARTER_COLOR = Color.BLUE
        private const val DEFAULT_FOURTH_QUARTER_COLOR = Color.RED
        private const val DEFAULT_CENTER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_WIDTH = 1.0f
    }
    var  kp : myClicklListener? = null

    // Paint object for coloring and styling
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Some colors
    private var greenColor = ContextCompat.getColor(context, R.color.green)
    private var yellowColor = ContextCompat.getColor(context, R.color.yellow)
    private var blueColor = ContextCompat.getColor(context, R.color.blue)
    private var redColor = ContextCompat.getColor(context, R.color.red)
    private var lilacColor = ContextCompat.getColor(context, R.color.lilac)
    private var pinkColor = ContextCompat.getColor(context, R.color.pink)
    private var aquamarineColor = ContextCompat.getColor(context, R.color.aquamarine)
    private var orangeColor = ContextCompat.getColor(context, R.color.orange)
    private var whiteColor = ContextCompat.getColor(context, R.color.white)
    private var greyColor = ContextCompat.getColor(context, R.color.grey)
    private var yellowDarkColor = ContextCompat.getColor(context, R.color.yellowDark)
    private var firstQuarterColor = DEFAULT_FIRST_QUARTER_COLOR
    private var secondQuarterColor = DEFAULT_SECOND_QUARTER_COLOR
    private var thirdQuarterColor = DEFAULT_THIRD_QUARTER_COLOR
    private var fourthQuarterColor = DEFAULT_FOURTH_QUARTER_COLOR
    private var centerColor = DEFAULT_CENTER_COLOR
    private var borderColor = DEFAULT_BORDER_COLOR
    private val myColors = arrayListOf(
            greenColor, yellowColor, blueColor, yellowDarkColor,
            lilacColor, pinkColor, aquamarineColor, orangeColor, whiteColor, greyColor, redColor,
    )

    private var radiusOfClick = 0f
    private var xyOfClick: String? = null
    private var centerX: Float = 0.0f
    private var centerY: Float = 0.0f
    private var indicator: Int = 0
    private var borderWidth = DEFAULT_BORDER_WIDTH

    // View size in pixels
    private var size = 640

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        // Obtain a typed array of attributes
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomButtonView,
                0, 0)
        // Extract custom attributes into member variables
        firstQuarterColor = typedArray.getColor(R.styleable.CustomButtonView_firstQuarterColor,
                DEFAULT_FIRST_QUARTER_COLOR)
        secondQuarterColor = typedArray.getColor(R.styleable.CustomButtonView_secondQuarterColor,
                DEFAULT_SECOND_QUARTER_COLOR)
        thirdQuarterColor = typedArray.getColor(R.styleable.CustomButtonView_thirdQuarterColor,
                DEFAULT_THIRD_QUARTER_COLOR)
        fourthQuarterColor = typedArray.getColor(R.styleable.CustomButtonView_fourthQuarterColor,
                DEFAULT_FOURTH_QUARTER_COLOR)
        centerColor = typedArray.getColor(R.styleable.CustomButtonView_centerColor,
                DEFAULT_CENTER_COLOR)
        borderColor = typedArray.getColor(R.styleable.CustomButtonView_borderColor,
                DEFAULT_BORDER_COLOR)
        borderWidth = typedArray.getDimension(R.styleable.CustomButtonView_borderWidth,
                DEFAULT_BORDER_WIDTH)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = measuredHeight.coerceAtMost(measuredWidth)
        setMeasuredDimension(size, size)
        centerX = size / 2f
        centerY = size / 2f
    }

    override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
        super.onDraw(canvas)
        drawBorder(canvas)
        drawFirstQuarter(canvas)
        drawSecondQuarter(canvas)
        drawThirdQuarter(canvas)
        drawFourthQuarter(canvas)
        drawCenter(canvas)
    }

    private fun getRectF(): RectF = RectF(centerX - size / 2 + borderWidth,
            centerY - size / 2 + borderWidth, centerX + size / 2 - borderWidth,
            centerY + size / 2 - borderWidth)

    private fun drawBorder(canvas: Canvas) {
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth
        canvas.drawCircle(centerX, centerY, size * 0.5f, paint)
    }

    private fun drawFirstQuarter(canvas: Canvas) {
        paint.color = firstQuarterColor
        paint.style = Paint.Style.FILL
        canvas.drawArc(getRectF(), -90F, 90F, true, paint)
    }

    private fun drawSecondQuarter(canvas: Canvas) {
        paint.color = secondQuarterColor
        paint.style = Paint.Style.FILL
        canvas.drawArc(getRectF(), 0F, 90F, true, paint)
    }

    private fun drawThirdQuarter(canvas: Canvas) {
        paint.color = thirdQuarterColor
        paint.style = Paint.Style.FILL
        canvas.drawArc(getRectF(), 90F, 90F, true, paint)
    }

    private fun drawFourthQuarter(canvas: Canvas) {
        paint.color = fourthQuarterColor
        paint.style = Paint.Style.FILL
        canvas.drawArc(getRectF(), 180F, 90F, true, paint)
    }

    private fun drawCenter(canvas: Canvas) {
        paint.color = centerColor
        paint.style = Paint.Style.FILL
        canvas.drawCircle(centerX, centerY, size * 0.15f, paint)
    }

    private fun changeColors() {
        firstQuarterColor = myColors.random()
        secondQuarterColor = myColors.random()
        thirdQuarterColor = myColors.random()
        fourthQuarterColor = myColors.random()
    }

    private fun changeFirstQuarterColor() {
        firstQuarterColor = myColors.random()
    }

    private fun changeSecondQuarterColor() {
        secondQuarterColor = myColors.random()
    }

    private fun changeThirdQuarterColor() {
        thirdQuarterColor = myColors.random()
    }

    private fun changeFourthQuarterColor() {
        fourthQuarterColor = myColors.random()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x - centerX
        val y = event.y - centerY
        kp?.action()
        radiusOfClick = sqrt(abs(x).pow(2) + abs(y).pow(2))
        if ((event.action == MotionEvent.ACTION_DOWN) && (radiusOfClick <= size * 0.15f)) {
            xyOfClick = "Соберите одинаковые цвета!\n" +
                    "Нажаты координаты (x, y): (${x.toInt()}, ${y.toInt()})"
            changeColors()
            indicator = 5
            invalidate()
        } else if ((event.action == MotionEvent.ACTION_DOWN) && (radiusOfClick <= size / 2)) {
            xyOfClick = "Нажаты координаты (x, y): (${x.toInt()}, ${y.toInt()})"
            if ((x > 0) && (y < 0)) {
                changeFirstQuarterColor()
                indicator = 1
            }
            if ((x > 0) && (y > 0)) {
                changeSecondQuarterColor()
                indicator = 2
            }
            if ((x < 0) && (y > 0)) {
                changeThirdQuarterColor()
                indicator = 3
            }
            if ((x < 0) && (y < 0)) {
                changeFourthQuarterColor()
                indicator = 4
            }
            invalidate()
        } else if (event.action == MotionEvent.ACTION_DOWN) {
            xyOfClick = "Мимо =)"
            indicator = 6
            invalidate()
        }
        return super.onTouchEvent(event)
    }

    fun coordinates() = xyOfClick
    fun indicator() = indicator
    fun firstQuarterColor() = firstQuarterColor
    fun secondQuarterColor() = secondQuarterColor
    fun thirdQuarterColor() = thirdQuarterColor
    fun fourthQuarterColor() = fourthQuarterColor

    fun isVictory() = (firstQuarterColor == secondQuarterColor &&
            thirdQuarterColor == fourthQuarterColor &&
            firstQuarterColor == fourthQuarterColor)

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putInt("firstQuarterColor", firstQuarterColor)
        bundle.putInt("secondQuarterColor", secondQuarterColor)
        bundle.putInt("thirdQuarterColor", thirdQuarterColor)
        bundle.putInt("fourthQuarterColor", fourthQuarterColor)
        bundle.putParcelable("superState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        var viewState = state
        if (viewState is Bundle) {
            firstQuarterColor = viewState.getInt("firstQuarterColor")
            secondQuarterColor = viewState.getInt("secondQuarterColor")
            thirdQuarterColor = viewState.getInt("thirdQuarterColor")
            fourthQuarterColor = viewState.getInt("fourthQuarterColor")
            viewState = viewState.getParcelable("superState")!!
        }
        super.onRestoreInstanceState(viewState)
    }
}


