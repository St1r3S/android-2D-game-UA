package com.example.android_2d_game_ua.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.android_2d_game_ua.R
import com.example.android_2d_game_ua.repositories.UserRepository
import com.example.android_2d_game_ua.view_models.GameViewModel
import com.example.android_2d_game_ua.view_models.factories.GameViewModelFactory
import kotlinx.android.synthetic.main.fragment_game.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class GameFragment : Fragment() {
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        val circleCard = view.circleCard
        val circleStart = view.circleStart
        view.button_to_menu.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_gameFragment_to_menuFragment)
        }

        var defaultScore = 0L

        viewModel = ViewModelProvider(
            this,
            GameViewModelFactory(UserRepository())
        ).get(GameViewModel::class.java)

        viewModel.getUserData()
        viewModel.user.observe(viewLifecycleOwner) {
            view.tv_best_score.text = "${it.score}"
            val currentUserScore = it.score
            val currentUserUID = it.userId

            val backgroundColorToWin = ContextCompat.getColor(view.context, R.color.yellow)
            val backgroundColorToLose = ContextCompat.getColor(view.context, R.color.blue)

            viewLifecycleOwner.lifecycleScope.launch {
                var loop = true
                circleStart.setOnClickListener {
                    circleStart.visibility = View.GONE
                    circleCard.visibility = View.VISIBLE
                    loop = true
                }

                while (loop) {
                    withContext(Dispatchers.Main) {
                        when (Random.nextBoolean()) {
                            true -> {
                                circleCard.setCardBackgroundColor(backgroundColorToLose)
                                view.score_btn_1.text = getString(R.string.do_not_click)
                                delay(
                                    TimeUnit.MILLISECONDS.toMillis(
                                        Random.nextLong(
                                            500,
                                            1200
                                        )
                                    )
                                )
                            }
                            false -> {
                                circleCard.setCardBackgroundColor(backgroundColorToWin)
                                view.score_btn_1.text = getString(R.string.click)
                                delay(
                                    TimeUnit.MILLISECONDS.toMillis(
                                        Random.nextLong(
                                            200,
                                            400
                                        )
                                    )
                                )
                            }
                        }
                        circleCard.setOnClickListener {
                            if (circleCard.cardBackgroundColor.defaultColor == backgroundColorToWin) {
                                defaultScore += 100
                                view.tv_current_score.text = defaultScore.toString()
                                circleCard.setCardBackgroundColor(backgroundColorToLose)
                            } else {
                                if (defaultScore > (currentUserScore ?: 0L)) {
                                    viewModel.setScore(currentUserUID.toString(), defaultScore)
                                }
                                defaultScore = 0
                                view.tv_current_score.text = defaultScore.toString()
                                circleStart.visibility = View.VISIBLE
                                circleCard.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
        return view
    }
}