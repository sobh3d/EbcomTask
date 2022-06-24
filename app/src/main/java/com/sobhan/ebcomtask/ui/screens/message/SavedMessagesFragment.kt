package com.sobhan.ebcomtask.ui.screens.message

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.sobhan.ebcomtask.databinding.FragmentSavedMessagesBinding
import com.sobhan.ebcomtask.model.Message
import com.sobhan.ebcomtask.ui.screens.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedMessagesFragment : Fragment(), MessageAdapter.MessageAdapterContract {
    private var _binding: FragmentSavedMessagesBinding? = null
    private val binding get() = _binding!!
    private val messageViewModel: MessageViewModel by activityViewModels()
    private var adapter = MessageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvMessages.adapter = adapter
            adapter.setOnBookmarkClickListeners(this@SavedMessagesFragment)
            messageViewModel.bookmarkMessages.observe(viewLifecycleOwner, Observer {
                if (!it.isNullOrEmpty()) {
                    showData(it)
                } else {

                    showEmptyView()


                }

            })

            messageViewModel.error.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_LONG).show()
            })

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun bookmarkMessage(message: Message, isBookmarked: Boolean) {
        val savedMessage = Message(
            message.title, message.description,
            message.image, message.id, message.unread, false
        )
        messageViewModel.update(savedMessage)
        messageViewModel.getBookmarkedMessages()
    }

    override fun onLongClick() {
        //ignore
    }

    override fun onSelectMessage(message: Message, isSelected: Boolean) {
        //ignore
    }

    override fun onReadMessage(message: Message) {
        val savedMessage = Message(
            message.title, message.description,
            message.image, message.id, false, message.bookmarked
        )
        messageViewModel.update(savedMessage)
        messageViewModel.decreaseUnreadMessageCount()
    }

    override fun onShareMessage(message: Message) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message.description)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }


    private fun showEmptyView() {
        binding.tvNoData.visibility = View.VISIBLE
        binding.ivNoData.visibility = View.VISIBLE
        binding.rvMessages.visibility = View.GONE
    }

    private fun showData(messages: List<Message>) {
        binding.rvMessages.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
        binding.ivNoData.visibility = View.GONE
        adapter.setResults(messages)
    }


}