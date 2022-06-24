package com.sobhan.ebcomtask.ui.screens.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.sobhan.ebcomtask.databinding.FragmentMessagesBinding
import com.sobhan.ebcomtask.model.Message
import com.sobhan.ebcomtask.ui.screens.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MessagesFragment : Fragment(), MessageAdapter.MessageAdapterContract {
    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!
    private val messageViewModel: MessageViewModel by activityViewModels()
    private var adapter = MessageAdapter()
    private var selectedMessage = ArrayList<Message>()
    private lateinit var motionLayout: MotionLayout
    private lateinit var checkBoxMotionLayout: MotionLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvMessages.adapter = adapter
            motionLayout = binding.constraintLayout3
            adapter.setOnBookmarkClickListeners(this@MessagesFragment)
            messageViewModel.messages.observe(viewLifecycleOwner, Observer {
                if (!it.isNullOrEmpty()) {
                    showData(it)

                } else showEmptyView()


            })

            messageViewModel.error.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_LONG).show()
            })






            btnCancel.setOnClickListener {
                it.visibility = View.GONE
                binding.btnRemove.visibility = View.GONE
                adapter.updateView(false)
                selectedMessage.clear()
                motionLayout.transitionToStart()
            }

            btnRemove.setOnClickListener {
                selectedMessage.forEach { item ->
                    messageViewModel.deleteMessage(item)
                }
                messageViewModel.getBookmarkedMessages()
                adapter.updateView(false)
                btnCancel.visibility = View.GONE
                btnRemove.visibility = View.GONE
                selectedMessage.clear()
                motionLayout.transitionToStart()
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun bookmarkMessage(message: Message, isBookmarked: Boolean) {
        when (isBookmarked) {
            true -> {
                val savedMessage = Message(
                    message.title, message.description,
                    message.image, message.id, message.unread, true
                )
                messageViewModel.update(savedMessage)
                messageViewModel.getBookmarkedMessages()
            }
            else -> {
                val savedMessage = Message(
                    message.title, message.description,
                    message.image, message.id, message.unread, false
                )
                messageViewModel.update(savedMessage)
                messageViewModel.getBookmarkedMessages()

            }
        }
    }

    override fun onLongClick() {
        checkBoxMotionLayout = motionLayout
        binding.apply {
            adapter.updateView(true)
            motionLayout.transitionToEnd()
        }
    }

    override fun onSelectMessage(message: Message, isSelected: Boolean) {
        when (isSelected) {
            true -> {
                selectedMessage.add(message)
            }
            else -> {
                selectedMessage.remove(message)
            }
        }
    }

    override fun onReadMessage(message: Message) {
        val savedMessage = Message(
            message.title, message.description,
            message.image, message.id, false, message.bookmarked
        )
        messageViewModel.update(savedMessage)
        messageViewModel.decreaseUnreadMessageCount()
    }

    private fun showEmptyView() {
        binding.spinLoading.visibility = View.GONE
        binding.tvNoData.visibility = View.VISIBLE
        binding.ivNoData.visibility = View.VISIBLE
        binding.rvMessages.visibility = View.GONE
    }

    private fun showData(messages: List<Message>) {
        binding.spinLoading.visibility = View.GONE
        binding.rvMessages.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
        binding.ivNoData.visibility = View.GONE
        adapter.setResults(messages)
    }


}