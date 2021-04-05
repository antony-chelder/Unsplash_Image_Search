package com.data

import androidx.paging.PagingSource
import com.tony_fire.imagesearch.api.UnsplashApi
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE = 1
class UnsplashPagingSource (
        private val unsplashAPI: UnsplashApi,
        private val query : String
        ): PagingSource<Int,UnSplashPhoto>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnSplashPhoto> {
        val position = params.key?: UNSPLASH_STARTING_PAGE

        return try {
            val response = unsplashAPI.searchPhotos(query,position,params.loadSize)
            val photos = response.results

            LoadResult.Page(
                    data = photos,
                    prevKey = if(position == UNSPLASH_STARTING_PAGE) null else position -1,
                    nextKey = if(photos.isEmpty())null else position + 1


            )
        }catch (exception:IOException){
            LoadResult.Error(exception)

        }
        catch (exception: HttpException){
            LoadResult.Error(exception)
        }


    }

}

