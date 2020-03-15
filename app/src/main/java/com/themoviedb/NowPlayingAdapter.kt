package com.themoviedb

import Results
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext


class NowPlayingAdapter(private var context: Context , private var movies: MutableList<Results>?) : RecyclerView.Adapter<ViewHolder?>() {

    private var isLoadingAdded = true

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> viewHolder = getDataViewHolder(parent, inflater)
            else -> viewHolder = getLoadingViewHolder(parent, inflater)
        }
        return viewHolder
    }

    private fun getDataViewHolder(parent: ViewGroup, inflater: LayoutInflater): ViewHolder {
        val view: View = inflater.inflate(R.layout.adapter_item_now_playing, parent, false)
        return MovieVH(view)
    }

    private fun getLoadingViewHolder(parent: ViewGroup, inflater: LayoutInflater): ViewHolder {
        val view: View = inflater.inflate(R.layout.adapter_item_progress, parent, false)
        return LoadingVH(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val movieVH = holder as MovieVH
                movieVH.tvTitle.text = movie.title
                movieVH.tvReleaseDate.text = movie.release_date
                movieVH.tvPercent.text = (movie.vote_average * 10).toInt().toString()
                movieVH.progressBar.progress = (movie.vote_average * 10).toInt()
                Glide.with(context).load(DataConstant.IMAGE_BASE_URL + movie.backdrop_path)
                    .into(movieVH.backgroundImage)
            }
            LOADING -> {

            }
        }
    }

    override fun getItemCount(): Int {
        return if (movies == null) 0 else movies!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == movies!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun add(mc: Results) {
        movies!!.add(mc)
        notifyItemInserted(movies!!.size - 1)
    }

    fun addAll(mcList: List<Results>) {
        for (mc in mcList) {
            add(mc)
        }
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = movies!!.size - 1
        val item = getItem(position)
        if (item != null) {
            movies!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): Results {
        return movies!![position]
    }

    private inner class MovieVH(itemView: View) : ViewHolder(itemView) {
        val backgroundImage: ImageView = itemView.findViewById(R.id.background_img)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvReleaseDate: TextView = itemView.findViewById(R.id.tv_release_date)
        val tvPercent: TextView = itemView.findViewById(R.id.tv_percent)
        val progressBar: ProgressBar = itemView.findViewById(R.id.circularProgressbar)
    }

    private inner class LoadingVH(itemView: View) : ViewHolder(itemView)


}