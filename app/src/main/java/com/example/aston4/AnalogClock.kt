package com.example.aston4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.icu.util.Calendar
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class AnalogClock @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attr, defStyleAttr) {

    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0
    private var padding = 0
    private var isInit = false
    private val paint = Paint()

    private fun initClock() {
        centerX = (width / 2).toFloat()
        centerY = (height / 2).toFloat()
        padding = 20
        radius = (min(height, width)) / 2 - padding
        isInit = true
    }

    private fun setPaintAttr(colorPaint: Int, mStokeWidth: Float) {
        paint.apply {
            reset()
            color = colorPaint
            style = Paint.Style.STROKE
            strokeWidth = mStokeWidth
            isAntiAlias = true
        }
    }

    private fun drawCircle(canvas: Canvas) {
        setPaintAttr(Color.BLACK, 25f)
        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()
        canvas.drawCircle(centerX, centerY, radius.toFloat(), paint)
    }

    private fun drawNumLines(canvas: Canvas) {
        setPaintAttr(Color.BLACK, 25f)
        paint.strokeCap = Paint.Cap.BUTT
        for (i in 1..12) {
            canvas.rotate(30F, centerX, centerY)
            canvas.drawLine(centerX, 70F, centerY, 20F, paint)
        }
    }

    private fun drawSecondsHand(canvas: Canvas, location: Float) {
        setPaintAttr(Color.BLACK, 25f)
        val angle = (location * 6)
        canvas.rotate(angle, centerX, centerY)
        canvas.drawLine(centerX, centerY + 60, centerX, centerY - 240, paint)
        canvas.rotate(-angle, centerX, centerY)
    }

    private fun drawMinutesHand(canvas: Canvas, location: Float) {
        setPaintAttr(Color.RED, 20f)
        val angle = (location * 6)
        canvas.rotate(angle, centerX, centerY)
        canvas.drawLine(centerX, centerY + 60, centerX, centerY - 200, paint)
        canvas.rotate(-angle, centerX, centerY)
    }

    private fun drawHoursHand(canvas: Canvas, location: Float) {
        setPaintAttr(Color.BLUE, 15f)
        val angle = (location * 6)
        canvas.rotate(angle, centerX, centerY)
        canvas.drawLine(centerX, centerY + 60, centerX, centerY - 160, paint)
        canvas.rotate(-angle, centerX, centerY)
    }

    private fun drawHands(canvas: Canvas) {
        val calendar: Calendar = Calendar.getInstance()
        var hours = calendar.get(Calendar.HOUR_OF_DAY)
        if (hours > 12) hours -= 12
        drawHoursHand(canvas, (hours + calendar.get(Calendar.MINUTE) / 60) * 5.toFloat())
        drawMinutesHand(canvas, calendar.get(Calendar.MINUTE).toFloat())
        drawSecondsHand(canvas, calendar.get(Calendar.SECOND).toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isInit) initClock()
        drawCircle(canvas)
        drawNumLines(canvas)
        drawHands(canvas)
        postInvalidateDelayed(500)
    }
}