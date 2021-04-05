package com.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.data.UnSplashPhoto
import com.tony_fire.imagesearch.R
import com.tony_fire.imagesearch.databinding.ItemUnsplashPhotoBinding

class UnsplashPhotoAdapter(private val listener:OnItemClickListener): PagingDataAdapter<UnSplashPhoto,UnsplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARE) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem!=null){
            holder.setData(currentItem)
        }

    }


     inner class PhotoViewHolder(private val binding:ItemUnsplashPhotoBinding)
        : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener{
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if(item!= null){
                        listener.onItemClick(item)
                    }
                }


            }
        }

            fun setData(photo:UnSplashPhoto){
                binding.apply {
                    Glide.with(itemView)
                        .load(photo.urls.regular)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_error)
                        .into(imageView)

                    textUser.text = photo.user.name
                }
            }

        }

   interface OnItemClickListener {
       fun onItemClick(photo: UnSplashPhoto)
   }

    companion object{
        private val PHOTO_COMPARE = object: DiffUtil.ItemCallback<UnSplashPhoto>(){
            override fun areItemsTheSame(oldItem: UnSplashPhoto, newItem: UnSplashPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UnSplashPhoto,
                newItem: UnSplashPhoto
            ) = oldItem == newItem

        }
    }
}
