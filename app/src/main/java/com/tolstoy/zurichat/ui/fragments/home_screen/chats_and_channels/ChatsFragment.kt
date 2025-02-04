package com.tolstoy.zurichat.ui.fragments.home_screen.chats_and_channels

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tolstoy.zurichat.R
import com.tolstoy.zurichat.databinding.FragmentChatsBinding
import com.tolstoy.zurichat.models.Message
import com.tolstoy.zurichat.ui.fragments.home_screen.HomeScreenFragment
import com.tolstoy.zurichat.ui.fragments.home_screen.HomeScreenFragmentDirections
import com.tolstoy.zurichat.ui.fragments.home_screen.HomeScreenViewModel
import com.tolstoy.zurichat.ui.fragments.home_screen.adapters.ChatsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatsFragment : Fragment(R.layout.fragment_chats) {
    private lateinit var binding: FragmentChatsBinding
    val viewModel: HomeScreenViewModel by lazy {
        (parentFragment as HomeScreenFragment).viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChatsBinding.bind(view)
        setupObservers()
        setupUI()
    }

    private fun setupUI() = with(binding){
        viewModel.userRooms.observe(viewLifecycleOwner){ rooms ->
            val chats = rooms.map { room ->
                val otherId = room.roomUserIds.first { it != viewModel.user!!.id }
                // TODO: Resolve the name of each chat by getting the name of the user that holds this id
                ChatsAdapter.Chat("Mark", Message(senderId = otherId,
                    roomId = room.id, message = "Hey what's good"), (Math.random() * 10).toInt())
            }
            listChats.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.adapter = ChatsAdapter(chats).apply {
                    setItemClickListener { chat ->
                        val action = HomeScreenFragmentDirections
                            .actionHomeScreenFragmentToDmFragment(chat.message.roomId,
                                viewModel.user!!.id, chat.message.senderId)
                        requireView().findNavController().navigate(action)
                    }
                }
            }
        }

        fabAddChat.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_new_channel_nav_graph)
        }

        viewModel.searchQuery.observe(viewLifecycleOwner){ query ->
            Log.d(TAG, "query: $query")
            val adapter = listChats.adapter as ChatsAdapter
            adapter.differ.submitList(adapter.chats.filter {
                query.lowercase() in it.sender.lowercase()
            })
        }
    }

    private fun setupObservers() = with(viewModel){
        getRooms()
    }

    companion object{
        val TAG = ChatsFragment::class.simpleName
    }
}
