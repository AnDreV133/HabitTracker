package com.andrev133.habittracker

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrev133.habittracker.databinding.ItemHabitCardBinding

class HabitListAdapter(private val data: MutableList<HabitModel> = mutableListOf()) :
    RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {

    inner class HabitViewHolder(
        private val binding: ItemHabitCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HabitModel) = binding.run {
            itemHabitName.text = model.name
            itemHabitPriority.text = root.resources.getString(R.string.priority_fmt, model.priority)
            itemHabitPeriod.text = model.periodicity
            itemHabitType.text = model.type
            itemHabitRightPartCard.backgroundTintList = ColorStateList.valueOf(model.color)
            itemHabitDescription.text = model.description
            itemHabitDescription.visibility = if (model.isExpanded) View.VISIBLE else View.GONE

            root.setOnClickListener {
                model.isExpanded = !model.isExpanded

                itemHabitDescription.animate().translationY(
                    if (model.isExpanded) 0f
                    else -itemHabitDescription.height.toFloat()
                ).setDuration(300).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationStart(animation)
                        if (model.isExpanded) itemHabitDescription.visibility = View.VISIBLE
                    }

                    override fun onAnimationEnd(animator: Animator) {
                        super.onAnimationEnd(animator)
                        if (!model.isExpanded) itemHabitDescription.visibility = View.GONE
                    }
                })

            }

            root.setOnLongClickListener {
                TODO("navigate to edit habit")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HabitViewHolder(
        ItemHabitCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<HabitModel>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}