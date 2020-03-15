package com.themoviedb

import Results
import android.content.Context
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class NowPlayingAdapter(private var context: Context , private var movies: MutableList<Results>?) : RecyclerView.Adapter<ViewHolder?>() {

    private var isLoadingAdded = false

    protected inner class LoadingVH(itemView: View) : ViewHolder(itemView)
    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }

    fun getMovies(): List<Results>? {
        return movies
    }

    fun setMovies(movies: MutableList<Results>?) {
        this.movies = movies
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> viewHolder = getViewHolder(parent, inflater)
            else -> {
                val v2: View = inflater.inflate(R.layout.adapter_item_progress, parent, false)
                viewHolder = LoadingVH(v2)
            }

        }
        return viewHolder
    }

    private fun getViewHolder(parent: ViewGroup, inflater: LayoutInflater): ViewHolder {
        val viewHolder: ViewHolder
        val v1: View = inflater.inflate(R.layout.adapter_item_now_playing, parent, false)
        viewHolder = MovieVH(v1)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val movieVH = holder as MovieVH?
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

    /*
   Helpers
   _________________________________________________________________________________________________
    */
    fun add(mc: Results) {
        movies!!.add(mc)
        notifyItemInserted(movies!!.size - 1)
    }

    fun addAll(mcList: List<Results>) {
        for (mc in mcList) {
            add(mc)
        }
    }

    fun remove(city: Results) {
        val position = movies!!.indexOf(city)
        if (position > -1) {
            movies!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    val isEmpty: Boolean
        get() = itemCount == 0

    fun addLoadingFooter() {
        isLoadingAdded = true
      //  add(Movie())
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
    /*
   View Holders
   _________________________________________________________________________________________________
    */
    /**
     * Main list's content ViewHolder
     */
    protected inner class MovieVH(itemView: View) : ViewHolder(itemView) {
     //   val textView: TextView

        init {
         //   textView = itemView.findViewById(R.id.item_text)
        }
    }


}