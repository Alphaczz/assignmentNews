package com.example.assignmentornet.presentation.ui.screen.Activities

import android.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.assignmentornet.MainActivity
import com.example.assignmentornet.data.model.NewsModel
import com.example.assignmentornet.databinding.ActivityEditPostBinding

import com.example.assignmentornet.presentation.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class EditPost : AppCompatActivity() {
    var imagePath:String?=""

    private  val viewModel: PostViewModel by viewModels()
    private lateinit var binding: ActivityEditPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.homeIcon.setOnClickListener{
            navigateToMainActivity()
        }
        val categorySpinner = binding.spinnerCategory
        val regionSpinner = binding.spinnerRegion
        val statusSpinner = binding.spinnerStatus
        val titleEditText: EditText = binding.editTextPostTitle
        val descriptionEditText: EditText = binding.editTextPostDescription
        val authorEditText: EditText = binding.editTextPostAuthor
        val imageViewSelectedImage: ImageView = binding.imageViewSelectedImage
        val buttonSelectImage: Button = binding.buttonSelectImage
        buttonSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, CreatePost.PICK_IMAGE_REQUEST_CODE)
        }
        val categories = arrayOf("Politics", "Education", "Health", "Business", "Sports", "Entertainment", "Technology", "Science", "Travel", "Miscellaneous")
        val categoryAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        // Populate region spinner (assuming regions are fetched from a list or database)
        val regions = arrayOf("Region 1", "Region 2", "Region 3") // Example regions
        val regionAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, regions)
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        regionSpinner.adapter = regionAdapter
        val statuses = arrayOf("Draft", "Published", "Archived") // Example statuses
        val statusAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, statuses)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        statusSpinner.adapter = statusAdapter

        val newsId = intent.getLongExtra(EXTRA_POST_ID, 0L)
        Log.i("POSTIDINEDIT",newsId.toString())
       var likes:Int=0
        var dislike:Int=0
        viewModel.getOneFromDb(newsId).observe(this) { post ->
            updateUI(post)
            likes=post.likes!!
             dislike=post.dislike!!
        }

        binding.buttonSubmit.setOnClickListener {
            val newpost = NewsModel(
                newsId = newsId,
                newsTitle = binding.editTextPostTitle.text.toString(),
                newsDesc = binding.editTextPostDescription.text.toString(),
                createdBy = binding.editTextPostAuthor.text.toString(),
                likes = likes,
                dislike = dislike,
                category = categorySpinner.selectedItem.toString(),
                region = regionSpinner.selectedItem.toString(),
                status = 0,
                newsBanner = imagePath ,
                UpdatedBy = binding.editTextPostAuthor.text.toString(),
                city = regionSpinner.selectedItem.toString(),
                country = "India",
                createdOn = Date().toString(),
                language = "eng",
                updatedOn = Date().toString()

            )

            viewModel.updatePost(newpost)
            navigateToMainActivity()
        }

    }



    fun navigateToMainActivity() {
        val intent = Intent(this@EditPost, MainActivity::class.java)
        startActivity(intent)

    }
    private fun updateUI(post: NewsModel) {
        binding.editTextPostTitle.setText(post.newsTitle)
        binding.editTextPostDescription.setText(post.newsDesc)
        binding.editTextPostAuthor.setText(post.createdBy)
        Glide.with(binding.root)
            .load(Uri.parse(post.newsBanner))
            .placeholder(R.mipmap.sym_def_app_icon)
            .error(R.mipmap.sym_def_app_icon)
            .into(binding.imageViewSelectedImage)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            binding.imageViewSelectedImage.setImageURI(selectedImageUri)
            // Save the image path to your NewsModel or wherever you're storing it
            imagePath = selectedImageUri?.toString()
            // Handle saving imagePath to your NewsModel or ViewModel
        }
    }
    companion object {
        const val EXTRA_POST_ID = "extra_post_id"
        private const val PICK_IMAGE_REQUEST_CODE = 100
    }
}