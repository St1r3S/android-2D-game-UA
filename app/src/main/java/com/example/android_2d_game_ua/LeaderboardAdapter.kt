package com.example.android_2d_game_ua

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class LeaderboardAdapter(
    private val inflater: LayoutInflater,
) :
    ListAdapter<LeaderBoardElem, LeaderboardAdapter.ViewHolder>(UserDiffCallback) {

    class ViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.tv_name)
        private val job = view.findViewById<TextView>(R.id.tv_score)
        private var user: LeaderBoardElem? = null

        fun bind(user: LeaderBoardElem) {
            this.user = user
            name.text = user.name
            job.text = user.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.leaderboard_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    object UserDiffCallback : DiffUtil.ItemCallback<LeaderBoardElem>() {
        override fun areItemsTheSame(
            oldItem: LeaderBoardElem,
            newItem: LeaderBoardElem
        ): Boolean {

            val res = oldItem == newItem

            return res
        }

        override fun areContentsTheSame(
            oldItem: LeaderBoardElem,
            newItem: LeaderBoardElem
        ): Boolean {

            val res = oldItem.name == newItem.name && oldItem.score == newItem.score

            return res
        }
    }
}