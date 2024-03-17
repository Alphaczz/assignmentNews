package com.example.assignmentornet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentornet.databinding.ActivityMainBinding
import com.example.assignmentornet.presentation.ui.Adapter.PostAdapter
import com.example.assignmentornet.presentation.ui.screen.Activities.CreatePost
import com.example.assignmentornet.presentation.ui.screen.Activities.ViewPost
import com.example.assignmentornet.presentation.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val postAdapter = PostAdapter(
            clickListener = { post ->
                val intent = Intent(this@MainActivity, ViewPost::class.java)
                intent.putExtra(ViewPost.EXTRA_POST_ID, post.newsId)
                startActivity(intent)
                Log.d("MainActivity", "Clicked on post: ${post.newsTitle}")
            },
            likeListener = { post ->
                viewModel.incrementLikes(post)
            },
            dislikeListener = { post ->
                viewModel.incrementDislikes(post)
            }
        )
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }

        viewModel.postLiveData.observe(this) { posts ->
            postAdapter.submitList(posts)
        }

    }

    fun navigateToCreatePost(view: View) {
        val intent = Intent(this@MainActivity, CreatePost::class.java)
        startActivity(intent)
    }

}