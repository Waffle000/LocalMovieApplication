package com.waffle.localmovieapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.waffle.localmovieapplication.R
import com.waffle.localmovieapplication.databinding.ItemPopularBinding
import com.waffle.localmovieapplication.local.entity.PopularEntity
import com.waffle.localmovieapplication.utils.loadImage

class HomeAdapter(
    private var projectList : List<PopularEntity>,
    private val fragment: Fragment
): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(val binding : ItemPopularBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_popular, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val mProject = projectList[position]
        holder.itemView.setOnClickListener {
            fragment.findNavController().navigate(
            R.id.action_homeFragment_to_discoverDetailFragment, bundleOf(
                HomeDetailFragment.DATA to mProject)
        )
        }
        bindDataToView(binding, mProject)
    }

    override fun getItemCount() = projectList.size

    private fun bindDataToView(binding: ItemPopularBinding, item : PopularEntity){
        binding.apply {
            tvMovieTitle.text = item.name
            tvMovieRating.text = item.star.toString()
            tvMovieYear.text = item.date
            tvMoviePopularity.text = item.popularity.toString()
            ivThumbnail.loadImage(item.posterPath)

        }
    }

}