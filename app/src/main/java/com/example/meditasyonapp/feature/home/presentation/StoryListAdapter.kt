package com.example.meditasyonapp.feature.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.meditasyonapp.R
import com.example.meditasyonapp.base.loadImage
import com.example.meditasyonapp.base.model.StoriesItemModel
import com.example.meditasyonapp.databinding.StoriesItemViewBinding
import com.example.meditasyonapp.feature.home.domain.StoryItemClickListener

class StoryListAdapter(
    private var storyList: List<StoriesItemModel>,
    private val listener: StoryItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: StoriesItemViewBinding

    class StoryListViewHolder(val binding: StoriesItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.stories_item_view, parent, false)
        return StoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StoryListViewHolder) {
            holder.binding.run {
                storyName.text = storyList[position].name
                text.text = storyList[position].text
                val imageUrl = storyList[position].image?.small
                loadImage(root, image, imageUrl)
                container.setOnClickListener {
                    listener.onStoryItemSelected(storyList[position])
                }
            }
        }
    }

    override fun getItemCount(): Int = storyList.size
}