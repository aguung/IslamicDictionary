package com.devtech.islamicdictionary.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.devtech.islamicdictionary.databinding.DetailDictionaryFragmentBinding
import com.devtech.islamicdictionary.utils.gone
import com.devtech.islamicdictionary.utils.snackbar
import com.devtech.islamicdictionary.utils.toogleActionbar
import com.devtech.islamicdictionary.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailDictionaryFragment : Fragment() {
    private val args:DetailDictionaryFragmentArgs by navArgs()
    private lateinit var binding: DetailDictionaryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailDictionaryFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initView()
        initAction()
    }

    private fun initToolbar(){
        (activity as AppCompatActivity).toogleActionbar(
            show = true,
            back = true,
            title = args.dictionary?.namaIstilah
        )
    }

    private fun initView() {
        if (args.dictionary?.bahasaArab!!.isEmpty()) {
            binding.cardArabic.gone()
        } else {
            binding.cardArabic.visible()
            binding.txtArab.text = args.dictionary?.bahasaArab
        }
        binding.txtPengertian.text = args.dictionary?.pengertianIstilahIND
    }

    private fun initAction() {
        binding.apply {
            btnLike.setOnClickListener {
                root.snackbar("Dalam pembangunan")
            }
        }
    }
}