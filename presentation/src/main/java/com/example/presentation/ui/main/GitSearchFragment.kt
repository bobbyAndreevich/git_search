package com.example.presentation.ui.main

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.LoadState
import com.example.presentation.R
import com.example.presentation.dagger.DaggerApp
import kotlinx.android.synthetic.main.fragment_git_search.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class GitSearchFragment : Fragment(R.layout.fragment_git_search), LifecycleOwner {

    @Inject
    lateinit var viewModel: GitSearchViewModel
    private var countDownTimer: CountDownTimer? = null

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        GitUsersAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().application as DaggerApp).applicationComponent.inject(this)
        return inflater.inflate(R.layout.fragment_git_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        users_list.adapter = adapter.withLoadStateFooter(
            UsersLoaderStateAdapter()
        )

        git_search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                countDownTimer?.cancel()

                countDownTimer = object : CountDownTimer(600, 600) {
                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {
                        if (newText != null && newText.isNotEmpty())
                            viewModel.setQuery(newText)
                    }
                }
                countDownTimer?.start()

                return false
            }
        })

        adapter.addLoadStateListener {
            users_list.isVisible = it.refresh != LoadState.Loading
            progress.isVisible = it.refresh != LoadState.Loading
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.users.collectLatest {
                adapter.submitData(it)
            }
        }

        viewModel.searchQuery
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach(::updateSearchQuery)
            .launchIn(lifecycleScope)
    }

    private fun updateSearchQuery(searchQuery: String) {
        git_search.apply {
            if ((query?.toString() ?: "") != searchQuery) {
                setQuery(searchQuery, false)
            }
        }
    }
}