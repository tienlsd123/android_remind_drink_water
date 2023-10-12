package com.bxt.reminddrinkwater.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bxt.reminddrinkwater.adapter.MainPagerAdapter
import com.bxt.reminddrinkwater.databinding.FragmentPagerBinding
import com.bxt.reminddrinkwater.util.registerOnPageChange
import com.bxt.reminddrinkwater.util.setSelectItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagerFragment : Fragment() {

    private lateinit var binding: FragmentPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerBinding.inflate(layoutInflater, container, false)
        binding.pagerContainer.apply {
            adapter = MainPagerAdapter(this@PagerFragment)
            registerOnPageChange(binding.bottomNav)
        }
        binding.bottomNav.setSelectItem(binding.pagerContainer)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = PagerFragment()
    }
}
