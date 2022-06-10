package com.example.android_2d_game_ua.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_2d_game_ua.R
import com.example.android_2d_game_ua.repositories.UserRepository
import com.example.android_2d_game_ua.view_models.GameViewModel
import com.example.android_2d_game_ua.view_models.factories.GameViewModelFactory
import kotlinx.android.synthetic.main.fragment_game.view.*

class GameFragment : Fragment() {
    private lateinit var viewModel: GameViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        var defaultScore = 0L
        viewModel = ViewModelProvider(
            this,
            GameViewModelFactory(UserRepository())
        ).get(GameViewModel::class.java)

        viewModel.getUser(this)
        viewModel.user.observe(viewLifecycleOwner) {
            view.tv_best_score.text = "best score: ${it.score}"
            val currentUserScore = it.score
            val currentUserUID = it.userId
            view.findViewById<Button>(R.id.tv_btn_increment).setOnClickListener {
                defaultScore += 100
                view.tv_current_score.text = defaultScore.toString()
            }
            view.findViewById<Button>(R.id.tv_btn_stop).setOnClickListener {
                if (defaultScore > (currentUserScore ?: 0L)) {
                    viewModel.setScore(currentUserUID.toString(), defaultScore)
                }
                defaultScore = 0
                view.tv_current_score.text = defaultScore.toString()
            }
        }

        return view
    }
}