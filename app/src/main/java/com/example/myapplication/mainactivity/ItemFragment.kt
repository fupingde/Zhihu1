package com.example.myapplication.mainactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.R

class ItemFragment : Fragment() {
    private var images: List<String>? = null
    private var title: String? = null
    private var hint: String? = null
    private var url: String? = null

    companion object {
        fun newInstance(images: List<String>, title: String, hint: String, url: String): ItemFragment {
            val fragment = ItemFragment()
            val args = Bundle()
            args.putStringArrayList("images", ArrayList(images))
            args.putString("title", title)
            args.putString("hint", hint)
            args.putString("url", url)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            images = it.getStringArrayList("images")
            title = it.getString("title")
            hint = it.getString("hint")
            url = it.getString("url")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.custom_pager_item, container, false)
        val imageView: ImageView = view.findViewById(R.id.imageviewtop)
        val tvTitle: TextView = view.findViewById(R.id.textView1)
        val tvHint: TextView = view.findViewById(R.id.textView2)
        val tvUrl: TextView = view.findViewById(R.id.webview)

        images?.firstOrNull()?.let {
            Glide.with(this).load(it).into(imageView)
        }
        tvTitle.text = title
        tvHint.text = hint
        tvUrl.text = url

        return view
    }
}
