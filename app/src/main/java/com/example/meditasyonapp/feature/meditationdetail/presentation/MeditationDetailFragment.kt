package com.example.meditasyonapp.feature.meditationdetail.presentation

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.meditasyonapp.R
import com.example.meditasyonapp.base.MEDIA_URL
import com.example.meditasyonapp.base.loadImage
import com.example.meditasyonapp.databinding.FragmentMeditationDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class MeditationDetailFragment : Fragment() {

    private lateinit var binding: FragmentMeditationDetailBinding
    private val viewModel: MeditationDetailViewModel by viewModels()

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meditation_detail, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setUIData()
        prepareMediaPlayer()
        observeViewModel()
        binding.run {
            playButton.setOnClickListener {
                playMedia()
            }
        }
    }

    private fun prepareMediaPlayer() {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(MEDIA_URL)
            setAudioAttributes(AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            )
            prepare()
        }
    }

    private fun playMedia() {
        mediaPlayer?.run {
            try {
                mediaPlayer?.isPlaying?.takeIf { !it }?.let {
                    start()
                    binding.playButton.setImageResource(R.drawable.ic_pause)
                } ?: run {
                    stop()
                    mediaPlayer?.isLooping = false
                    binding.playButton.setImageResource(R.drawable.ic_play)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                mediaPlayer = null
                mediaPlayer?.release()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.run {
            backgroundImageLiveData.observe(viewLifecycleOwner, { url ->
                view?.let { loadImage(it, binding.backgroundImage, url) }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.stop()
        binding.playButton.setImageResource(R.drawable.ic_play)
    }
}