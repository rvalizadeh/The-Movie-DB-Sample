package com.themoviedb.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.themoviedb.R
import com.themoviedb.databinding.AdapterItemNowPlayingBinding
import com.themoviedb.databinding.AdapterItemProgressBinding
import com.themoviedb.model.Results
import com.themoviedb.util.DataConstant
import com.themoviedb.util.Utils


class NowPlayingAdapter(
    private var activity: AppCompatActivity,
    private var movies: MutableList<Results>?
) : RecyclerView.Adapter<ViewHolder?>() , Filterable{

    private var isLoadingAdded = true
    private var filterMovies: MutableList<Results>? = movies

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: ViewHolder
        when (viewType) {
            ITEM -> viewHolder = getDataViewHolder(
                AdapterItemNowPlayingBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> viewHolder = getLoadingViewHolder(
                AdapterItemProgressBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
        return viewHolder
    }

    private fun getDataViewHolder(binding: AdapterItemNowPlayingBinding): ViewHolder {
        return MovieVH(binding)
    }

    private fun getLoadingViewHolder(binding: AdapterItemProgressBinding): ViewHolder {
        return LoadingVH(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> {
                (holder as MovieVH).bind(filterMovies!![position])
            }
            LOADING -> {

            }
        }
    }

    override fun getItemCount(): Int {
        return if (filterMovies == null) 0 else filterMovies!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == filterMovies!!.size - 1 && isLoadingAdded) LOADING else ITEM
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
        val position = filterMovies!!.size - 1
        filterMovies!!.removeAt(position)
        notifyItemRemoved(position)
    }

    private inner class MovieVH(var binding: AdapterItemNowPlayingBinding) :
        ViewHolder(binding.root) {
        fun bind(movie: Results) {

            binding.tvTitle.text = movie.title
            binding.tvReleaseDate.text = movie.release_date
            binding.tvPercent.text = (movie.vote_average * 10).toInt().toString()
            binding.circularProgressbar.progress = (movie.vote_average * 10).toInt()
            Glide.with(activity).load(DataConstant.IMAGE_BASE_URL + movie.backdrop_path)
                .into(binding.backgroundImg)

            binding.cardView.setOnClickListener {

                clickHandler(movie)

            }
        }
    }

    private inner class LoadingVH(binding: AdapterItemProgressBinding) : ViewHolder(binding.root)

    private fun clickHandler(movie: Results) {

        Utils.hideKeyboard(activity)
        val ft = activity.supportFragmentManager.beginTransaction()
        val detailFragment: Fragment = DetailFragment()
        val bundle = Bundle()

        bundle.putParcelable(DataConstant.RESULT_KEY, movie)
        detailFragment.arguments = bundle

        ft.replace(R.id.fragment, detailFragment, detailFragment.toString())
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filterMovies = when(charSequence.isEmpty()){
                    true -> movies
                    false -> {
                        val filteredList: MutableList<Results> = ArrayList()
                        filteredList.addAll(movies!!.filter { x -> x.title.toLowerCase().contains(charString.toLowerCase()) })
                        filteredList
                    }
                }


                val filterResults = FilterResults()
                filterResults.values = filterMovies
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                filterMovies = filterResults.values as MutableList<Results>
                notifyDataSetChanged()
            }
        }
    }

}