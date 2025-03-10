package com.andrev133.habittracker

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrev133.habittracker.databinding.ItemHabitCardBinding
import java.util.UUID

class HabitListAdapter(
    private val onDetail: (uuid: UUID) -> Unit,
    private val data: MutableList<HabitModel> = mutableListOf()
) :
    RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {

    class HabitViewHolder(
        private val binding: ItemHabitCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HabitModel, onDetail: (uuid: UUID) -> Unit) = binding.apply {
            itemHabitName.text = model.name
            itemHabitPriority.text = root.resources
                .getString(
                    R.string.priority_fmt,
                    root.resources.getStringArray(R.array.priority_array)[model.priority]
                )
            itemHabitPeriod.text = root.resources
                .getString(
                    R.string.periodicity_fmt,
                    model.quantity,
                    model.periodicity
                )
            itemHabitType.text = root.resources.getString(model.type.toResString())
            itemHabitRightPartCard.backgroundTintList = ColorStateList.valueOf(model.color)
            itemHabitDescription.text = model.description
            itemHabitDescription.visibility = if (model.isExpanded) View.VISIBLE else View.GONE
            itemHabitRightPartCard.backgroundTintList = ColorStateList.valueOf(model.color)

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
                model.uuid?.let { uuid -> onDetail(uuid) }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HabitViewHolder(
        ItemHabitCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(data[position], onDetail)
    }

    override fun getItemCount() = data.size // fixme: maybe size not update

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<HabitModel>) {
        val oldSize = data.size
        val newSize = newData.size

        data.clear()
        data.addAll(newData)

        if (oldSize + 1 == newSize) notifyItemInserted(oldSize)
        else if (oldSize - 1 == newSize) notifyItemRemoved(oldSize - 1)
        else if (oldSize == newSize) notifyDataSetChanged()
    }
}