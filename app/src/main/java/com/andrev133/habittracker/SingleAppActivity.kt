package com.andrev133.habittracker;

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.andrev133.habittracker.databinding.ActivitySingleAppBinding
import java.util.UUID

class SingleAppActivity : AppCompatActivity(), OpenHabitEditorFragmentByUuid, ShowMenu, HideMenu {
    private var _binding: ActivitySingleAppBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySingleAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.apply {
            setOnItemSelectedListener { item ->
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragmentContainerWithBottomNavigation,
                        when (item.itemId) {
                            R.id.menuItemGood -> HabitListFragment.newInstanceTypeGood(this@SingleAppActivity)
                            R.id.menuItemBad -> HabitListFragment.newInstanceTypeBad(this@SingleAppActivity)
                            else -> throw IllegalArgumentException("Unknown menu item id")
                        },
                        "fragmentWithBottomNavigation"
                    )
                    .commit()
                true
            }
            selectedItemId = R.id.menuItemGood
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragmentContainerWithBottomNavigation,
                    HabitListFragment.newInstanceTypeGood(this@SingleAppActivity),
                    "fragmentWithBottomNavigation"
                )
                .commit()
        }
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

    override fun showMenu() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    override fun hideMenu() {
        binding.bottomNavigation.visibility = View.GONE
    }
}
