package tw.edu.pu.s1091819.test

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class MySurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView
    (context, attrs), SurfaceHolder.Callback, GestureDetector.OnGestureListener {
    lateinit var surfaceHolder: SurfaceHolder
    lateinit var BG: Bitmap
    var BGmoveX:Int = 0
    var fly:Fly
    var gDetector: GestureDetector
    var mper: MediaPlayer

    init {
        surfaceHolder = getHolder()
        BG = BitmapFactory.decodeResource(getResources(), R.drawable.background)
        surfaceHolder.addCallback(this)
        fly = Fly(context!!)
        gDetector = GestureDetector(context, this)
        mper = MediaPlayer()
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
        //canvas.drawBitmap(BG, SrcRect, DestRect, null)

        BGmoveX -= 2
        var BGnewX: Double = w + BGmoveX

        // 如果已捲動整張圖，則重新開始
        if (BGnewX <= 0) {
            BGmoveX = 0
            // only need one draw
            canvas.drawBitmap(BG, SrcRect, DestRect, null)
        } else {
            // need to draw original and wrap
            DestRect = Rect(BGmoveX, 0, (BGmoveX+w).toInt(), h.toInt())
            canvas.drawBitmap(BG, SrcRect, DestRect, null)
            DestRect = Rect(BGnewX.toInt(), 0, (BGnewX+w).toInt(), h.toInt())
            canvas.drawBitmap(BG, SrcRect, DestRect, null)
        }

        fly.draw(canvas)

    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {
        if (e!!.x >= 0 && e!!.x <= fly.w && e!!.y >= fly.y && e!!.y <= fly.y + fly.w) {
            fly.fire = 1
            mper = MediaPlayer.create(context, R.raw.shoot)
            mper.start()
        }
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, p2: Float, p3: Float): Boolean {
        fly.y = e2!!.y.toInt() - fly.h/2
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gDetector.onTouchEvent(event)
        return true
    }
}