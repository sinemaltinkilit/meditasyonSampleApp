package com.example.meditasyonapp.feature.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.meditasyonapp.R
import com.example.meditasyonapp.base.loadImage
import com.example.meditasyonapp.base.model.MeditationItemModel
import com.example.meditasyonapp.databinding.MeditationItemViewBinding
import com.example.meditasyonapp.feature.home.domain.MeditationItemClickListener

class MeditationListAdapter(
    private var meditationList: List<MeditationItemModel>,
    private val listener: MeditationItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: MeditationItemViewBinding

    class MeditationListViewHolder(val binding: MeditationItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.meditation_item_view, parent, false)
        return MeditationListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MeditationListViewHolder) {
            holder.binding.run {
                title.text = meditationList[position].title
                subtitle.text = meditationList[position].subtitle
                val imageUrl = meditationList[position].image?.small
                loadImage(root, image, imageUrl)
                container.setOnClickListener {
                    listener.onMeditationItemSelected(meditationList[position])
                }
            }
        }
    }

    override fun getItemCount(): Int = meditationList.size
}