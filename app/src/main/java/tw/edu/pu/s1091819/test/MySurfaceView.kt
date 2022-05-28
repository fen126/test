package tw.edu.pu.s1091819.test

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class MySurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView
    (context, attrs), SurfaceHolder.Callback {
    lateinit var surfaceHolder: SurfaceHolder
    lateinit var BG: Bitmap
    lateinit var SuperMan:Bitmap
    init {
        surfaceHolder = getHolder()
        BG = BitmapFactory.decodeResource(getResources(), R.drawable.background)
        surfaceHolder.addCallback(this)
    }
    override fun surfaceCreated(p0: SurfaceHolder) {
        var canvas: Canvas = surfaceHolder.lockCanvas()
        drawSomething(canvas)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    fun drawSomething(canvas:Canvas) {
        var SrcRect: Rect = Rect(0, 0, BG.width, BG.height) //裁切
        var w: Double = BG.width / 1.75
        var h: Double = BG.height / 2.40
        var DestRect:Rect = Rect(0, 0, w.toInt(), h.toInt())
        canvas.drawBitmap(BG, SrcRect, DestRect, null)
    }
}