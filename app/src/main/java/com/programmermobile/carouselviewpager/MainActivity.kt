package com.programmermobile.carouselviewpager

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var carouselView: ViewPager2
    private lateinit var carouselItems: List<CarouselItemFragment>
    private lateinit var carouselIndicator: LinearLayout
    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var timer: Timer
    var currentCarouselIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        carouselIndicator = findViewById(R.id.carouselIndicator)
        carouselView = findViewById(R.id.carousel)

        carouselItems = listOf(
            CarouselItemFragment.newInstance("Image One Title", R.drawable.image_1),
            CarouselItemFragment.newInstance("Image Two Title", R.drawable.image_2),
            CarouselItemFragment.newInstance("Image Three Title", R.drawable.image_3),
            CarouselItemFragment.newInstance("Image Four Title", R.drawable.image_4),
            CarouselItemFragment.newInstance("Image Five Title", R.drawable.image_5),
        )

        buildIndicator()

        carouselAdapter = CarouselAdapter(carouselItems, this)
        carouselView.adapter = carouselAdapter
        carouselView.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                var indicatorView = carouselIndicator.getChildAt(currentCarouselIndex) as ImageView
                indicatorView.setImageResource(R.drawable.dot_selected)

                indicatorView = if (currentCarouselIndex == 0) {
                    carouselIndicator.getChildAt(carouselItems.size - 1) as ImageView
                } else {
                    carouselIndicator.getChildAt(currentCarouselIndex - 1) as ImageView
                }
                indicatorView.setImageResource(R.drawable.dot_unselected)
            }
        })
    }

    private fun buildIndicator() {
        carouselItems.mapIndexed { index, carouselItemFragment ->
            val imageView = ImageView(this)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.marginEnd = 8
            imageView.layoutParams = params
            if (index == 0) imageView.setImageResource(R.drawable.dot_selected)
            else imageView.setImageResource(R.drawable.dot_unselected)
            carouselIndicator.addView(imageView)
        }
    }

    override fun onResume() {
        super.onResume()
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (currentCarouselIndex == carouselItems.size - 1) {
                    currentCarouselIndex = 0
                } else {
                    currentCarouselIndex++
                }
                runOnUiThread {
                    carouselView.setCurrentItem(currentCarouselIndex, true)
                }
            }
        }, 3000, 3000)
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }
}