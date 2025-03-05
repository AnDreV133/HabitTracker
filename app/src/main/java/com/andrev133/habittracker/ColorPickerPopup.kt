package com.andrev133.habittracker

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.content.res.AppCompatResources
import com.andrev133.habittracker.databinding.ActivityHabitEditorBinding
import com.andrev133.habittracker.databinding.PopupColorPickerBinding

class ColorPickerPopup(bindingParent: ActivityHabitEditorBinding) : PopupWindow(bindingParent.root) {

    private var listener: OnColorSelectedListener? = null
    private val colorSquareSize =
        bindingParent.root.resources.getDimensionPixelSize(R.dimen.color_square_size)
    private val colorSquareDivider =
        bindingParent.root.resources.getDimensionPixelSize(R.dimen.color_squares_divider)
    private var _binding: PopupColorPickerBinding? = null
    private val binding get() = _binding!!

    interface OnColorSelectedListener {
        fun onColorSelected(color: Int)
    }

    fun setOnColorSelectedListener(listener: OnColorSelectedListener) {
        this.listener = listener
    }

    init {
        _binding = PopupColorPickerBinding
            .inflate(LayoutInflater.from(bindingParent.root.context))
        contentView = binding.root
        width = colorSquareSize * 4 + colorSquareDivider * 4
        height = LinearLayout.LayoutParams.WRAP_CONTENT

        val colorGrid = binding.colorGrid
        val maxHue = 360f
        val numOfColors = binding.colorGrid.columnCount
        val colors = mutableListOf<Int>().apply {
            val partHue = maxHue / numOfColors
            repeat(numOfColors) {
                add(Color.HSVToColor(floatArrayOf(partHue * it, 1f, 1f)))
            }
        }

        GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            colors.toIntArray()
        ).let {
            colorGrid.background = it
        }

        for (color in colors) {
            val colorView = View(binding.root.context).apply {
                layoutParams = GridLayout.LayoutParams().also { layout ->
                    layout.width = context.resources.getDimensionPixelSize(R.dimen.color_square_size)
                    layout.height = context.resources.getDimensionPixelSize(R.dimen.color_square_size)
                    layout.setMargins(
                        0,
                        0,
                        colorSquareDivider,
                        0
                    )
                }
                background = AppCompatResources.getDrawable(
                    bindingParent.root.context,
                    R.drawable.shape_rounded_color_picker_element
                )
                foreground = AppCompatResources.getDrawable(
                    bindingParent.root.context,
                    R.drawable.shape_rounded_card_stroked
                )
                foregroundTintList = ColorStateList.valueOf(color)
                setOnClickListener {
                    listener?.onColorSelected(color)
                    dismiss()
                }
            }
            colorGrid.addView(colorView)
        }
    }

    override fun dismiss() {
        _binding = null
        super.dismiss()
    }
}