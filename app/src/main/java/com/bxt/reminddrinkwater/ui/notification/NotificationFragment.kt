package com.bxt.reminddrinkwater.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bxt.reminddrinkwater.databinding.FragmentNotificationBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    @Inject lateinit var adapter: NotificationAdapter
    private val viewModel: NotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListMessage()
    }

    private fun setupListMessage() {
        binding.listMsg.adapter = adapter
        viewModel.listMsg.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotificationFragment()
    }
}
