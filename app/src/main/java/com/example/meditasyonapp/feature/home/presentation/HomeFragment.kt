package com.example.meditasyonapp.feature.home.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.meditasyonapp.R
import com.example.meditasyonapp.base.model.DetailItemModel
import com.example.meditasyonapp.base.model.MeditationItemModel
import com.example.meditasyonapp.base.model.StoriesItemModel
import com.example.meditasyonapp.databinding.FragmentHomeBinding
import com.example.meditasyonapp.feature.home.domain.MeditationItemClickListener
import com.example.meditasyonapp.feature.home.domain.StoryItemClickListener
import com.example.meditasyonapp.feature.login.presentation.LoginFragment.Companion.PREF_NAME
import com.example.meditasyonapp.feature.login.presentation.LoginFragment.Companion.USERNAME_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), StoryItemClickListener, MeditationItemClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private val meditationListAdapter = MeditationListAdapter(arrayListOf(), this)
    private val storyListAdapter = StoryListAdapter(arrayListOf(), this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkConnection().takeIf { it }?.let {
            initUI()
            observeViewModel()
            viewModel.getHomeData()
        } ?: run {
            showNetworkErrorDialog()
        }
    }

    private fun checkConnection(): Boolean {
        val cm: ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }

    private fun initUI() {
        binding.run {
            meditationList.adapter = meditationListAdapter
            storyList.adapter = storyListAdapter
            bannerText.text = setBannerText()
        }
    }

    private fun observeViewModel() {
        viewModel.run {
            meditationListToAdapterLiveData.observe(viewLifecycleOwner, { list ->
                list.takeIf { it.isNullOrEmpty() }?.let {
                    binding.meditationList.visibility = View.GONE
                } ?: run {
                    list?.let { binding.meditationList.adapter = MeditationListAdapter(it, this@HomeFragment) }
                }
            })
            storyListToAdapterLiveData.observe(viewLifecycleOwner, { list ->
                list.takeIf { it.isNullOrEmpty() }?.let {
                    binding.storyList.visibility = View.GONE
                } ?: run {
                    list?.let { binding.storyList.adapter = StoryListAdapter(it, this@HomeFragment) }
                }
            })
        }
    }

    private fun setBannerText(): String {
        val pref = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val username = pref?.getString(USERNAME_KEY, "")
        return getString(R.string.banner_text, username?.replaceFirst(username[0], username[0].toUpperCase(), false))
    }

    override fun onStoryItemSelected(selectedStory: StoriesItemModel) {
        val detailItem = DetailItemModel(
            date = selectedStory.date,
            title = selectedStory.name,
            detailText = selectedStory.text,
            imageUrl = selectedStory.image?.small
        )
        val action = HomeFragmentDirections.actionHomeFragmentToMeditationDetailFragment(detailItem)
        this@HomeFragment.findNavController().navigate(action)
    }

    override fun onMeditationItemSelected(selectedMeditation: MeditationItemModel) {
        val detailItem = DetailItemModel(
            date = selectedMeditation.releaseDate,
            title = selectedMeditation.title,
            detailText = selectedMeditation.content,
            imageUrl = selectedMeditation.image?.small
        )
        val action = HomeFragmentDirections.actionHomeFragmentToMeditationDetailFragment(detailItem)
        this@HomeFragment.findNavController().navigate(action)
    }

    private fun showNetworkErrorDialog() {
        context?.let {
            AlertDialog.Builder(it).run {
                setTitle(getString(R.string.network_error_title))
                setMessage(getString(R.string.network_error_message))
                setCancelable(false)
                setPositiveButton(getString(R.string.done_button)) { _, _ -> activity?.onBackPressed()}
            }.show()
        }
    }

}