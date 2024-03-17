package com.example.assignmentornet.presentation.ui.screen.Activities

import android.R
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.assignmentornet.MainActivity
import com.example.assignmentornet.data.model.NewsModel
import com.example.assignmentornet.databinding.ActivityViewPostBinding
import com.example.assignmentornet.presentation.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPost : AppCompatActivity() {
    companion object {
        const val EXTRA_POST_ID = "extra_post_id"
    }
    private  val viewModel: PostViewModel by viewModels()
    private lateinit var binding: ActivityViewPostBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val postId = intent.getLongExtra(EXTRA_POST_ID, 0L)
        Log.i("POSTID",postId.toString())
        binding.homeIcon.setOnClickListener{
            navigateToMainActivity()
        }
        binding.EditBtn.setOnClickListener {
            val intent = Intent(this@ViewPost, EditPost::class.java)
            intent.putExtra(EXTRA_POST_ID, postId)
            startActivity(intent)
        }

        viewModel.getOneFromDb(postId).observe(this) { post ->
            updateUI(post)

        }





        fun deleteItem(postid:Long) {
            viewModel.deletePost(postid)
            navigateToMainActivity()
        }
        fun showConfirmationDialog(postid: Long) {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Are you sure?")
            alertDialogBuilder.setMessage("Are you sure you want to delete?")

            alertDialogBuilder.setPositiveButton("Yes") { dialog, _ ->
                deleteItem(postid)
                dialog.dismiss()
            }


            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->

                dialog.dismiss()
            }

            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        binding.DelBtn.setOnClickListener {
            showConfirmationDialog(postId)
        }

    }
    private fun updateUI(post: NewsModel) {
        binding.textViewTitleContent.text = post.newsTitle
        binding.textViewDescContent.text = post.newsDesc
        binding.textViewAuthorContent.text = post.createdBy
        Glide.with(binding.root)
            .load(Uri.parse(post.newsBanner))
            .into(binding.imagePlaceholder)
        if (post.updatedOn!=null){
            binding.textDate.setText("UpdatedOn:")
            binding.textViewDateContent.setText(post.updatedOn.toString())
        }else{
            binding.textDate.setText("Created On:")
            binding.textViewDateContent.setText(post.createdOn.toString())
        }
        binding.likeCountTextView.text = post.likes.toString()
        binding.dislikeCountTextView.text = post.dislike.toString()
        binding.textViewCategoryContent.text=post.category.toString()
        binding.textViewRegionContent.text=post.region.toString()
        when(post.status){
            0->{
                binding.textViewStatusContent.text="Default"
            }
            1->{
                binding.textViewStatusContent.text="Approved"
            }
            2->{
                binding.textViewStatusContent.text="Published"
            }
        }
    }

    fun navigateToMainActivity() {
        val intent = Intent(this@ViewPost, MainActivity::class.java)
        startActivity(intent)

    }

}
