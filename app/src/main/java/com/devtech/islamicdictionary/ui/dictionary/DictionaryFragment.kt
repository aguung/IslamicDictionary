package com.devtech.islamicdictionary.ui.dictionary

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.devtech.islamicdictionary.adapter.dictionary.DictionaryAdapter
import com.devtech.islamicdictionary.adapter.dictionary.DictionaryLoadStateAdapter
import com.devtech.islamicdictionary.databinding.DictionaryFragmentBinding
import com.devtech.islamicdictionary.utils.toogleActionbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@AndroidEntryPoint
class DictionaryFragment : Fragment() {
    private val viewModel by viewModels<DictionaryViewModel>()

    @Inject
    lateinit var adapter: DictionaryAdapter
    private lateinit var binding: DictionaryFragmentBinding
    private val args: DictionaryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DictionaryFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initAdapter()
        initSwipeToRefresh()
        initSearch()
        initViewModel()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).toogleActionbar(
            show = true,
            back = true,
            title = args.menu?.nama
        )
        viewModel.showSearch(args.position.toString(), "")
    }


    private fun initAdapter() {
        binding.apply {
            list.adapter = adapter.withLoadStateHeaderAndFooter(
                header = DictionaryLoadStateAdapter(adapter::retry),
                footer = DictionaryLoadStateAdapter(adapter::retry)
            )

            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        adapter.addLoadStateListener {
            binding.apply {
                if (it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && adapter.itemCount < 1) {
                    binding.lytEmpty.isVisible = true
                    binding.list.isVisible = false
                } else {
                    binding.lytEmpty.isVisible = false
                    binding.list.isVisible = true
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

    @ExperimentalCoroutinesApi
    @FlowPreview
    @ExperimentalPagingApi
    private fun initViewModel() {
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
    }

    private fun updatedSubredditFromInput() {
        binding.input.text.trim().toString().also {
            if (viewModel.shouldShowSearch(it)) {
                viewModel.showSearch(args.position.toString(), it)
            }
        }
    }
}