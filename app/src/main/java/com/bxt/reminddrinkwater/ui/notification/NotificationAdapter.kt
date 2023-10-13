package com.bxt.reminddrinkwater.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bxt.reminddrinkwater.data.Message
import com.bxt.reminddrinkwater.databinding.ItemNotificationBinding
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class NotificationAdapter @Inject constructor() : ListAdapter<Message, NotificationAdapter.NotificationViewHolder>(MSG_COMPARATOR) {

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) : ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val msg = getItem(position)
            binding.tvDate.text = SimpleDateFormat(" dd.MM.yyyy 'at' HH:mm", Locale.getDefault()).format(msg.time)
            binding.tvMsg.text = msg.content
        }
    }

    companion object {
        val MSG_COMPARATOR = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NotificationViewHolder(ItemNotificationBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.onBind(position)
    }
}
