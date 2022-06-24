package com.sobhan.ebcomtask.ui.screens.message


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.sobhan.ebcomtask.R
import com.sobhan.ebcomtask.databinding.MessageWithImageBinding
import com.sobhan.ebcomtask.databinding.MessageWithoutImageBinding
import com.sobhan.ebcomtask.model.Message


class MessageAdapter() : ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {
    private var data = ArrayList<Message?>()
    private var onMessageClick: MessageAdapterContract? = null
    private var showCheckBox = false


    fun setResults(data: List<Message?>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (data[position]?.image.isNullOrEmpty()) {
            return 1
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemBinding = MessageWithImageBinding.inflate(layoutInflater, parent, false)
                MessageWithImageViewHolder(itemBinding, onMessageClick!!)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemBinding = MessageWithoutImageBinding.inflate(layoutInflater, parent, false)
                MessageWithoutImageViewHolder(itemBinding, onMessageClick!!)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = data[position]
        when (holder.itemViewType) {
            0 -> {
                val messageWithImage = holder as MessageWithImageViewHolder
                data?.let { messageWithImage.bind(it, showCheckBox) }
            }
            else -> {
                val messageWithoutImage = holder as MessageWithoutImageViewHolder
                data?.let { messageWithoutImage.bind(it, showCheckBox) }
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MessageWithImageViewHolder(
        private val binding: MessageWithImageBinding,
        private val onBookmarkClick: MessageAdapterContract,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Message, showCheckBox: Boolean) {
            binding.message = obj
            binding.executePendingBindings()
            if (showCheckBox) {
                binding.checkbox.visibility = View.VISIBLE
            } else {
                binding.checkbox.visibility = View.GONE
                binding.checkbox.isChecked = false
            }

            binding.checkbox.setOnCheckedChangeListener { p0, checked ->
                onBookmarkClick.onSelectMessage(
                    obj,
                    checked
                )
            }

            binding.cardView.setOnLongClickListener { view ->
                if (view != null) {
                    onBookmarkClick.onLongClick()
                }
                true
            }

            binding.ivExpandable.setOnClick {
                if (obj.unread) {
                    onBookmarkClick.onReadMessage(obj)
                }

            }



            binding.ivBookmark.setOnClickListener {
                if (obj.bookmarked) {
                    onBookmarkClick.bookmarkMessage(obj, false)
                } else onBookmarkClick.bookmarkMessage(obj, true)
            }


        }

        @SuppressLint("ClickableViewAccessibility")
        fun View.setOnClick(clickEvent: () -> Unit) {
            this.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    clickEvent.invoke()
                }
                false
            }
        }


    }

    class MessageWithoutImageViewHolder(
        private val binding: MessageWithoutImageBinding,
        private val onBookmarkClick: MessageAdapterContract
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Message, showCheckBox: Boolean) {
            binding.message = obj
            binding.executePendingBindings()
            if (showCheckBox) {
                binding.checkbox.visibility = View.VISIBLE
            } else {
                binding.checkbox.visibility = View.GONE
                binding.checkbox.isChecked = false
            }

            with(binding) {
                message = obj
                executePendingBindings()
                if (showCheckBox) {
                    checkbox.visibility = View.VISIBLE
                } else {
                    checkbox.visibility = View.GONE
                    checkbox.isChecked = false
                }

                checkbox.setOnCheckedChangeListener { p0, checked ->
                    onBookmarkClick.onSelectMessage(
                        obj,
                        checked
                    )
                }
            }

            binding.cardView.setOnLongClickListener { view ->
                if (view != null) {
                    onBookmarkClick.onLongClick()
                }
                true
            }


            binding.ivBookmark.setOnClickListener {
                if (obj.bookmarked) {
                    onBookmarkClick.bookmarkMessage(obj, false)
                } else onBookmarkClick.bookmarkMessage(obj, true)
            }

            binding.ivExpandable.setOnClick {
                if (obj.unread) {
                    onBookmarkClick.onReadMessage(obj)

                }

            }
        }

        @SuppressLint("ClickableViewAccessibility")
        fun View.setOnClick(clickEvent: () -> Unit) {
            this.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    clickEvent.invoke()
                }
                false
            }
        }

    }

    fun setOnBookmarkClickListeners(onMessageClick: MessageAdapterContract?) {
        this.onMessageClick = onMessageClick
    }




    fun updateView(showCheckBox: Boolean) {
          this.showCheckBox = showCheckBox
          notifyDataSetChanged()
    }

    interface MessageAdapterContract {
        fun bookmarkMessage(message: Message, isBookmarked: Boolean)
        fun onLongClick()
        fun onSelectMessage(message: Message, isSelected: Boolean)
        fun onReadMessage(message: Message)
    }



}

private class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return newItem == oldItem
    }
}


