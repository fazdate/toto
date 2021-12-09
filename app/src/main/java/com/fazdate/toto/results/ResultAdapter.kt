package com.fazdate.toto.results

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fazdate.toto.R


class ResultAdapter(private val providedOutcomes: ArrayList<ResultsModel>, private val correctOutcomes: ArrayList<ResultsModel>, private val listener: OnItemClickListener) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.resultsitem, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return providedOutcomes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.outcome.text = providedOutcomes[position].outcome
        holder.matchCount.text = "Match #" + (position+1)
        holder.correctOutcome.text = correctOutcomes[position].outcome

        if (providedOutcomes[position].outcome == correctOutcomes[position].outcome) {
            holder.itemView.setBackgroundColor(Color.parseColor("#90be6d"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#f94144"))
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val outcome: TextView = itemView.findViewById(R.id.outcome)
        val matchCount: TextView = itemView.findViewById(R.id.match_count)
        val correctOutcome: TextView = itemView.findViewById(R.id.correctOutcome)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}