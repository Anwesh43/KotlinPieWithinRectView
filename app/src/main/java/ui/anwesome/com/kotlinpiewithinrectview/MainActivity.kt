package ui.anwesome.com.kotlinpiewithinrectview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.piewithinrectview.PieWithinRectView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PieWithinRectView.create(this)
    }
}
