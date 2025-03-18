package com.andrev133.habittracker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.andrev133.habittracker.databinding.ActivitySingleAppBinding
import java.util.UUID

class SingleAppActivity : AppCompatActivity(), OpenHabitEditorFragmentByUuid {
    private var _binding: ActivitySingleAppBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySingleAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragmentContainerWithBottomNavigation,
                HabitListFragment.newInstance(),
                "fragmentWithBottomNavigation"
            )
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun openHabitEditorFragmentByUuid(uuid: UUID?) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainerWithBottomNavigation,
                HabitEditorFragment.newInstance(this, uuid),
                "habitEditorFragment"
            )
            .addToBackStack("habitEditorFragment")
            .commit()
    }
}
