package com.devtech.islamicdictionary.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.devtech.islamicdictionary.R
import com.devtech.islamicdictionary.databinding.LoginFragmentBinding
import com.devtech.islamicdictionary.utils.clearInput
import com.devtech.islamicdictionary.utils.inputError
import com.devtech.islamicdictionary.utils.toogleActionbar


class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initView()
        initAction()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).toogleActionbar(show = false, back = false, title = "")
    }

    private fun initView(){
        binding.apply {
            etPhone.clearInput(inputPhone)
            etPassword.clearInput(inputPassword)
        }
    }

    private fun initAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                val validation = arrayOfNulls<Boolean>(2)
                validation[0] = binding.inputPhone.inputError(
                    etPhone.text.toString(),
                    resources.getString(
                        R.string.empty_fields,
                        resources.getString(R.string.nomor_handphone)
                    )
                )
                validation[1] = binding.inputPassword.inputError(
                    etPassword.text.toString(),
                    resources.getString(
                        R.string.empty_fields,
                        resources.getString(R.string.password)
                    )
                )
                if (!validation.contains(false)) {
                    val pref =
                        PreferenceManager.getDefaultSharedPreferences(requireContext()).edit()
                            .putBoolean(getString(R.string.key_login), true).commit()
                    if (pref) {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }
    }

}