package ui.anwesome.com.piewithinrectview

/**
 * Created by anweshmishra on 14/03/18.
 */
import android.content.*
import android.graphics.*
import android.view.*
class PieWithinRectView(ctx : Context) : View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}