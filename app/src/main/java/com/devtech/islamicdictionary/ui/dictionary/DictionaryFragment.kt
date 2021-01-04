package com.devtech.islamicdictionary.ui.dictionary

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.devtech.islamicdictionary.R
import com.devtech.islamicdictionary.adapter.dictionary.PostsLoadStateAdapter
import com.devtech.islamicdictionary.adapter.dictionary.DictionaryAdapter
import com.devtech.islamicdictionary.databinding.DictionaryFragmentBinding
import com.devtech.islamicdictionary.utils.gone
import com.devtech.islamicdictionary.utils.toogleActionbar
import com.devtech.islamicdictionary.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*


@AndroidEntryPoint
class DictionaryFragment : Fragment() {

    companion object {
        const val MENU = "MENU"
        const val TITLE = "TITLE"
    }

    private val viewModel by viewModels<DictionaryViewModel>()
    private lateinit var adapter: DictionaryAdapter
    private val binding: DictionaryFragmentBinding by lazy {
        DictionaryFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
        initSwipeToRefresh()
        initSearch()
    }

    private fun initView() {
        (activity as AppCompatActivity).toogleActionbar(show = true, back = true, title = arguments?.getString(TITLE))
        viewModel.showSubreddit(arguments?.getString(MENU)!!, "")
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    @ExperimentalPagingApi
    private fun initAdapter() {
        adapter = DictionaryAdapter()
        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PostsLoadStateAdapter(adapter),
            footer = PostsLoadStateAdapter(adapter)
        )

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.posts.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    binding.list.scrollToPosition(0)
                }
        }

        adapter.addLoadStateListener {
            binding.apply {
                if (it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && adapter.itemCount < 1) {
                    binding.txtNotFound.visible()
                    binding.swipeRefresh.gone()
                } else {
                    binding.txtNotFound.gone()
                    binding.swipeRefresh.visible()
                }
            }
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    private fun initSearch() {
        binding.input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updatedSubredditFromInput()
                true
            } else {
                false
            }
        }
        binding.input.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updatedSubredditFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updatedSubredditFromInput() {
        binding.input.text.trim().toString().also {
            if (viewModel.shouldShowSubreddit(it)) {
                viewModel.showSubreddit(arguments?.getString(MENU)!!, it)
            }
        }
    }
}