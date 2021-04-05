package com.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tony_fire.imagesearch.databinding.UnsplashPhotoLoadStateFooderBinding

class UnsplashPhotoStateLoadAdapter(private val retry:() -> Unit): LoadStateAdapter<UnsplashPhotoStateLoadAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = UnsplashPhotoLoadStateFooderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.setData(loadState)

    }



     inner class LoadStateViewHolder(private val binding:UnsplashPhotoLoadStateFooderBinding):
            RecyclerView.ViewHolder(binding.root){
        init {
            binding.buttonRetry.setOnClickListener{
              retry.invoke()
            }

        }

                fun setData(loadState: LoadState){
                    binding.apply {
                        progressLoadBar.isVisible = loadState is LoadState.Loading
                        buttonRetry.isVisible = loadState !is LoadState.Loading
                        textLoadViewError.isVisible = loadState !is LoadState.Loading

                    }
                }

            }
}