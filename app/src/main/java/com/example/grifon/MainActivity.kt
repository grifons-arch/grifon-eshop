package com.example.grifon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.carousel_view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.carousel_indicator)

        val images = listOf(
            R.drawable.veroza,
            R.drawable.diakosmitika_keramikago,
            R.drawable.fvtidtika,
            R.drawable.polwesterika,
        )

        viewPager.adapter = CarouselAdapter(images)

        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
            // Indicator dots only.
        }.attach()
    }
}
