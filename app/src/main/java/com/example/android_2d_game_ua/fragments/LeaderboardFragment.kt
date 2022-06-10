package com.example.android_2d_game_ua.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_2d_game_ua.model.leaderboard.LeaderBoardElem
import com.example.android_2d_game_ua.R
import com.example.android_2d_game_ua.adapters.LeaderboardAdapter
import com.example.android_2d_game_ua.repositories.LeaderboardRepository
import com.example.android_2d_game_ua.view_models.LeaderboardViewModel
import com.example.android_2d_game_ua.view_models.factories.LeaderboardViewModelFactory
import kotlinx.android.synthetic.main.fragment_leaderboard.view.*

class LeaderboardFragment : Fragment() {

    private var list: MutableList<LeaderBoardElem> = mutableListOf()
    private lateinit var viewModel: LeaderboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewModel = ViewModelProvider(
            this,
            LeaderboardViewModelFactory(LeaderboardRepository())
        ).get(LeaderboardViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)

        val adapter = LeaderboardAdapter(layoutInflater)
        view.list.adapter = adapter
        view.list.layoutManager = LinearLayoutManager(context)
        adapter.submitList(null)

        viewModel.leaderBoard.observe(viewLifecycleOwner) {
            list = it.toMutableList()
            adapter.submitList(list.toList())
        }
        viewModel.getLeaders()

        return view
    }
}