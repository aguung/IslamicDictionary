package com.devtech.islamicdictionary.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.devtech.islamicdictionary.data.local.entity.Dictionary
import com.devtech.islamicdictionary.databinding.DetailDictionaryFragmentBinding
import com.devtech.islamicdictionary.utils.gone
import com.devtech.islamicdictionary.utils.toogleActionbar
import com.devtech.islamicdictionary.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailDictionaryFragment : Fragment() {
    companion object {
        const val ITEM = "ITEM"
    }
    private val viewModel by viewModels<DetailDictionaryViewModel>()
    private val binding:DetailDictionaryFragmentBinding by lazy {
        DetailDictionaryFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(ITEM) }?.let {
            val data = it.getParcelable<Dictionary>(ITEM)

            (activity as AppCompatActivity).toogleActionbar(show = true, back = true, title = data?.namaIstilah)
            if(data?.bahasaArab!!.isEmpty()){
                binding.cardArabic.gone()
            }else{
                binding.cardArabic.visible()
                binding.txtArab.text = data.bahasaArab
            }
            binding.txtPengertian.text = data.pengertianIstilahIND
        }
    }

}