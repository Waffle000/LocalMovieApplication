package com.waffle.localmovieapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.waffle.localmovieapplication.R
import com.waffle.localmovieapplication.databinding.ItemPopularBinding
import com.waffle.localmovieapplication.local.entity.PopularEntity
import com.waffle.localmovieapplication.ui.discover.MovieDetailFragment
import com.waffle.localmovieapplication.utils.loadImage

class PopularAdapter(private val view : Fragment): PagingDataAdapter<PopularEntity, PopularAdapter.RecyclerViewHolder>(
    DiscoverComparator
) {
    object DiscoverComparator : DiffUtil.ItemCallback<PopularEntity>() {
        override fun areItemsTheSame(
            oldItem: PopularEntity,
            newItem: PopularEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PopularEntity,
            newItem: PopularEntity
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    inner class RecyclerViewHolder(private val binding: ItemPopularBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularEntity) {
            binding.apply {
                tvMovieTitle.text = item.name
                tvMovieRating.text = item.star.toString()
                tvMovieYear.text = item.date
                tvMoviePopularity.text = item.popularity.toString()
                ivThumbnail.loadImage(item.posterPath)
                root.setOnClickListener{
                    view.findNavController().navigate(
                        R.id.action_homeFragment_to_discoverDetailFragment, bundleOf(
                        MovieDetailFragment.DATA to item))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val data = getItem(position)!!
        holder.apply {
            bind(data)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemBinding = ItemPopularBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecyclerViewHolder(itemBinding)
    }
}