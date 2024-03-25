package com.example.submissionaplikasigithubuser.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionaplikasigithubuser.adapter.FollowUserAdapter
import com.example.submissionaplikasigithubuser.data.model.FollowResponse
import com.example.submissionaplikasigithubuser.databinding.FragmentFollowingsBinding
import com.example.submissionaplikasigithubuser.ui.main.UserDetailActivity
import com.example.submissionaplikasigithubuser.viewmodel.FollowingsViewModel

class FollowingsFragment : Fragment() {
    private val viewModel: FollowingsViewModel by viewModels()
    private val adapter = FollowUserAdapter()

    private var bindings: FragmentFollowingsBinding? = null
    private val binding get() = bindings!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = FragmentFollowingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.setHasFixedSize(true)
        binding.rvFollow.adapter = adapter

        adapter.setOnItemClickCallback { data -> selectedUser(data) }
    }

    private fun observeViewModel() {
        val username = UserDetailActivity.username
        if (username != null) {
            viewModel.setFollowingUsers(username)
            viewModel.getFollowings.observe(viewLifecycleOwner) { followings ->
                if (followings.isNotEmpty()) {
                    adapter.setData(followings)
                } else {
                    Toast.makeText(context, "No follower found", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "No username found", Toast.LENGTH_SHORT).show()
        }
        viewModel.getLoadingBar.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarFollow.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

    }

    private fun selectedUser(user: FollowResponse) {
        val intent = Intent(activity, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.EXTRA_USER, user.login)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindings = null
    }
}
