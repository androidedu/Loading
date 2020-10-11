package com.example.loading

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class PercentLoading:View {
    //下载的进度
    var progress = 0f
        set(value) {
            field = value
            invalidate()
        }

    //设置圆环的背景颜色
    var progressBgColor = Color.CYAN
        set(value) {
            field = value
            progressBgPaint.color = value
        }
    //设置进度条颜色
    var progressFgColor = Color.MAGENTA
        set(value) {
            field = value
            progressFgPaint.color = value
        }

    //字体颜色
    var textColor = Color.BLACK

    private val mStrokeWidth = 50f
    private var cx = 0f
    private var cy = 0f
    private var radius = 0f
    private val progressBgPaint = Paint().apply {
        color = progressBgColor
        style = Paint.Style.STROKE
        strokeWidth = mStrokeWidth
    }
    private val progressFgPaint = Paint().apply {
        color = progressFgColor
        style = Paint.Style.STROKE
        strokeWidth = mStrokeWidth
    }
    private val textPaint = Paint().apply {
        color = textColor
        style = Paint.Style.FILL
        textSize = 100f
        textAlign = Paint.Align.CENTER
    }
    //code
    constructor(context:Context): super(context){}
    //xml
    constructor(context: Context, attrs: AttributeSet?):super(context,attrs){
        //解析对应的属性
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.PercentLoading)

        //获取背景颜色
        progressBgColor = typedArray.getColor(
            R.styleable.PercentLoading_backgroundColor,
            Color.BLACK
        )

        //进度条颜色
        progressFgColor = typedArray.getColor(
            R.styleable.PercentLoading_foregroundColor,
            Color.MAGENTA
        )

        //字体颜色
        textColor = typedArray.getColor(
            R.styleable.PercentLoading_android_textColor,
            Color.BLACK
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cx = width/2f
        cy = height/2f
        radius = Math.min(width,height)/2f - mStrokeWidth
    }

    override fun onDraw(canvas: Canvas?) {
        //绘制背景圆圈
        canvas?.drawCircle(cx,cy,radius,progressBgPaint)
        //绘制进度的圆弧
        canvas?.drawArc(
            mStrokeWidth,mStrokeWidth,
            width.toFloat()-mStrokeWidth,
            height.toFloat()-mStrokeWidth,
            -90f,360*progress,
            false,progressFgPaint
        )

        //绘制文本sdaD ASDSAFDSAF ASF AF
        val text = "${(progress*100).toInt()}%"//  0.2312*100 = 23.12.toInt()
        //获取文字信息
        val metrics = textPaint.fontMetrics

        val space = (metrics.descent - metrics.ascent)/2-metrics.descent

        canvas?.drawText(text,cx,cy+space,textPaint)
    }
}