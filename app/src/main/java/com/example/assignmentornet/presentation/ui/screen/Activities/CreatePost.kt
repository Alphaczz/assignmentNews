package com.example.assignmentornet.presentation.ui.screen.Activities

import android.R
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.assignmentornet.MainActivity
import com.example.assignmentornet.databinding.ActivityCreatePostBinding
import com.example.assignmentornet.domain.util.createPost

import com.example.assignmentornet.presentation.viewmodel.CreatePostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreatePost : AppCompatActivity() {
    private  val viewModel: CreatePostViewModel by viewModels()
    var imagePath:String?=""

    private lateinit var binding: ActivityCreatePostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }

        // Populate category spinner
        val categories = arrayOf("Politics", "Education", "Health", "Business", "Sports", "Entertainment", "Technology", "Science", "Travel", "Miscellaneous")
        val categoryAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        // Populate region spinner (assuming regions are fetched from a list or database)
        val regions = arrayOf("Region 1", "Region 2", "Region 3") // Example regions
        val regionAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, regions)
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        regionSpinner.adapter = regionAdapter
        val statuses = arrayOf("Draft", "Published", "Archived") // Example statuses
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statuses)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        statusSpinner.adapter = statusAdapter

        val saveButton: Button = binding.buttonSubmit
        binding.homeIcon.setOnClickListener {
            navigateToMainActivity()
        }
        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val author = authorEditText.text.toString()
            val category=categorySpinner.selectedItem.toString()
            val region= regionSpinner.selectedItem.toString()
            val status=statusSpinner.selectedItemPosition
            fun isValidInput(title: String, description: String, author: String): Boolean {
                return when {
                    title.isEmpty() -> {
                        titleEditText.error = "Title cannot be empty"
                        false
                    }
                    description.isEmpty() -> {
                        descriptionEditText.error = "Description cannot be empty"
                        false
                    }
                    author.isEmpty() -> {
                        authorEditText.error = "Author cannot be empty"
                        false
                    }
                    else -> true
                }
            }
            if (isValidInput(title, description, author)) {

                val post = createPost(title, imagePath,description, author,category, newsStatus = status, newsRegion = region)

                CoroutineScope(Dispatchers.Default).launch {
                    viewModel.saveNews(post)
                    navigateToMainActivity()
                }
            } else {
                var errorMessage = "Please fix the following issues:\n"

                if (title.isEmpty()) {
                    errorMessage += "- Title cannot be empty\n"
                }

                if (description.isEmpty()) {
                    errorMessage += "- Description cannot be empty\n"
                }

                if (author.isEmpty()) {
                    errorMessage += "- Author cannot be empty\n"
                }

                // Display a Toast with the error message
                Toast.makeText(this@CreatePost, errorMessage, Toast.LENGTH_LONG).show()
            }
        }

    }
    fun navigateToMainActivity() {
        val intent = Intent(this@CreatePost, MainActivity::class.java)
        startActivity(intent)

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
        const val PICK_IMAGE_REQUEST_CODE = 100
    }

}


