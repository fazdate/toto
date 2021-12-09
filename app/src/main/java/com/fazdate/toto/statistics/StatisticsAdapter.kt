package com.fazdate.toto.statistics

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fazdate.toto.R

class StatisticsAdapter (private val list : List<GameObject>) : RecyclerView.Adapter<StatisticsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.statisticsitem, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.gameCount.text = "Game #" + list[position].numberOfGames.toString()
        holder.correctGuesses.text = list[position].numberOfCorrectGuesses.toString()
        holder.prizeMoney.text = "$" + list[position].prizeMoney.toString()

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val prizeMoney: TextView = itemView.findViewById(R.id.prizeMoney)
        val gameCount: TextView = itemView.findViewById(R.id.gameCount)
        val correctGuesses: TextView = itemView.findViewById(R.id.correctGuesses)
    }
}
