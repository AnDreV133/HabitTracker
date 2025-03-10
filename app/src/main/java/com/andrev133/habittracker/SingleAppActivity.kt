package com.andrev133.habittracker;

import android.os.Bundle
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

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainerWithBottomNavigation,
                    when (item.itemId) {
                        R.id.menuItemGood -> HabitListFragment.newInstanceTypeGood(this)
                        R.id.menuItemBad -> HabitListFragment.newInstanceTypeBad(this)
                        else -> throw IllegalArgumentException("Unknown menu item id")
                    },
                    "fragmentWithBottomNavigation"
                )
                .commit()
            true
        }

        binding.bottomNavigation.selectedItemId = R.id.menuItemGood
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun openHabitEditorFragmentByUuid(uuid: UUID?) {
        supportFragmentManager.beginTransaction()
//            .add(
//                HabitEditorFragment.newInstanceByUuid(uuid), "habitEditorFragment"
//            )
            .commit()
        TODO("do transaction")
    }
}
