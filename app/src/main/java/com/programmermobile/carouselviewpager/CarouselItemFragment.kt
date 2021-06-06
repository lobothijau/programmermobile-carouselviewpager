package com.programmermobile.carouselviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class CarouselItemFragment : Fragment() {
    lateinit var imageView: ImageView
    lateinit var titleTextView: TextView

    var title: String = ""
    var drawable: Int = 0

    companion object {
        const val TITLE = "TITLE"
        const val DRAWABLE = "DRAWABLE"

        fun newInstance(title: String, drawable: Int): CarouselItemFragment {

            val fragment = CarouselItemFragment()
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            bundle.putInt(DRAWABLE, drawable)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_carousel_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.image)
        titleTextView = view.findViewById(R.id.title)

        title = arguments?.getString(TITLE, "").toString()
        drawable = arguments?.getInt(DRAWABLE, 0)!!

        imageView.setImageResource(drawable)
        titleTextView.text = title
    }
}