package com.example.assignmentornet.presentation.ui.Adapter
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignmentornet.R
import com.example.assignmentornet.data.model.NewsModel
import com.example.assignmentornet.databinding.CustomCardLayoutBinding

class PostAdapter(private val clickListener: (NewsModel) -> Unit, private val likeListener: (NewsModel) -> Unit, private val dislikeListener: (NewsModel) -> Unit) :
    ListAdapter<NewsModel, PostAdapter.PostViewHolder>(PostDiffCallback) {

    class PostViewHolder(private val binding: CustomCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: NewsModel, clickListener: (NewsModel) -> Unit, likeListener: (NewsModel) -> Unit, dislikeListener: (NewsModel) -> Unit) {
            val imageUri: Uri =Uri.parse(post.newsBanner)

            binding.titleTextView.text = post.newsTitle
            binding.description.text = post.newsDesc
            binding.likeCountTextView.text=post.likes.toString()
            binding.dislikeCountTextView.text=post.dislike.toString()
            Glide.with(binding.root)
                .load(imageUri)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.bannerImageView)
            binding.readMoreButton.setOnClickListener {
                clickListener(post)
            }
            binding.likeButton.setOnClickListener {
                likeListener(post)
            }

            binding.dislikeButton.setOnClickListener {
                dislikeListener(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CustomCardLayoutBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post, clickListener, likeListener, dislikeListener)
    }
}

object PostDiffCallback : DiffUtil.ItemCallback<NewsModel>() {
    override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem.newsId == newItem.newsId
    }

    override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem == newItem
    }
}
