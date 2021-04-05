package com.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.data.UnSplashPhoto
import com.tony_fire.imagesearch.R
import com.tony_fire.imagesearch.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.unsplash_photo_load_state_fooder.*

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery),UnsplashPhotoAdapter.OnItemClickListener {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = UnsplashPhotoAdapter(this)

        binding.apply {
            rc_view.setHasFixedSize(true)
            rcView.itemAnimator = null
            rcView.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = UnsplashPhotoStateLoadAdapter{adapter.retry()},
                    footer = UnsplashPhotoStateLoadAdapter{adapter.retry()},
            )
            b_retry_again.setOnClickListener {
                adapter.retry()
            }

        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        adapter.addLoadStateListener { loadstate ->
            binding.apply {
                progressBar.isVisible = loadstate.source.refresh is LoadState.Loading
                rcView.isVisible = loadstate.source.refresh is LoadState.NotLoading
                b_retry_again.isVisible = loadstate.source.refresh is LoadState.Error
                textViewError.isVisible = loadstate.source.refresh is LoadState.Error

                if(loadstate.source.refresh is LoadState.NotLoading && loadstate.append.endOfPaginationReached
                        && adapter.itemCount<1){
                    rcView.isVisible = false
                    textNoresult.isVisible = true
                } else{
                    textNoresult.isVisible = false
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery,menu)
        val searchitem = menu.findItem(R.id.search_photo)
        val searchview = searchitem.actionView as SearchView

        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query !=null){
                    binding.rcView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchview.clearFocus()
                }
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return true
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(photo: UnSplashPhoto) {
    val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }

}