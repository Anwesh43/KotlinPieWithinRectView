package ui.anwesome.com.piewithinrectview

/**
 * Created by anweshmishra on 14/03/18.
 */
import android.app.Activity
import android.content.*
import android.graphics.*
import android.view.*
class PieWithinRectView(ctx : Context) : View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = Renderer(this)
    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class State(var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0, var jDir : Int = 1) {
        val scales : Array<Float> = arrayOf(0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if (Math.abs(scales[j] - prevScale) > 1) {
                prevScale = scales[j] + dir
                j += jDir
                if (j == scales.size || j == -1) {
                    jDir *= -1
                    j += jDir
                    dir = 0f
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }
    data class Animator (var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex : Exception) {

                }
            }
        }
        fun start () {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop () {
            if (animated) {
                animated = false
            }
        }
    }
    data class PieWithinRect(var i : Int) {
        val state = State()
        fun draw (canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            val r = Math.min(w, h) / 10
            val size = Math.min(w, h) / 3
            canvas.save()
            canvas.translate(w / 2, h / 2)
            paint.color = Color.parseColor("#4CAF50")
            for (i in 0..3) {
                canvas.save()
                canvas.rotate(45f + 90f * i)
                paint.strokeWidth = Math.min(w, h)/ 60
                canvas.save()
                canvas.translate(0f, -size * state.scales[0])
                paint.style = Paint.Style.STROKE
                canvas.drawRoundRect(RectF(-r*1.2f, -r * 1.2f, r * 1.2f, r * 1.2f), r / 8 , r / 8, paint)
                paint.style = Paint.Style.FILL
                canvas.drawArc(RectF(-r, -r, r, r), 0f, 360f * state.scales[1], true, paint)
                canvas.restore()
                canvas.restore()
            }
            canvas.restore()
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer(var view : PieWithinRectView) {
        val pieWithinRect = PieWithinRect(0)
        val animator = Animator(view)
        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            pieWithinRect.draw(canvas, paint)
            animator.animate {
                pieWithinRect.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            pieWithinRect.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity : Activity) : PieWithinRectView {
            val view = PieWithinRectView(activity)
            activity.setContentView(view)
            return view
        }
    }
}