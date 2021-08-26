package com.example.meditasyonapp.feature.login.presentation

import android.content.Context
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
import com.example.meditasyonapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        const val PREF_NAME = "SAVE_USER"
        const val USERNAME_KEY = "usernameKey"
    }

    private val viewModel : LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            continueButton.setOnClickListener { handleContinueProcess() }
        }
    }

    private fun handleContinueProcess() {
        viewModel.run {
            binding.run {
                if (checkUsernameValid(usernameEdit.text) && checkPasswordValid(passwordEdit.text)) {
                    saveUserCredentials()
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    this@LoginFragment.findNavController().navigate(action)
                } else {
                    showErrorDialog()
                }
            }
        }
    }

    private fun showErrorDialog() {
        context?.let {
            AlertDialog.Builder(it).run {
                setTitle(getString(R.string.credential_error_title))
                setMessage(getString(R.string.credential_error_message))
                setCancelable(false)
                setPositiveButton(getString(R.string.done_button)) { _, _ -> }
            }.show()
        }
    }

    private fun saveUserCredentials() {
        val sharedPref = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) ?: return
        sharedPref.edit().apply {
            putString(USERNAME_KEY, binding.usernameEdit.text.toString())
        }.apply()
    }
}
