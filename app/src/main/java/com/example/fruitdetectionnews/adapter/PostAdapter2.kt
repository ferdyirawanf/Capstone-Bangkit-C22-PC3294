package com.example.fruitdetectionnews.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fruitdetectionnews.viewmodel.ArticlesItem
import com.example.fruitdetectionnews.DetailNewsActivity2
import com.example.fruitdetectionnews.databinding.AnotherRowBinding

class PostAdapter2(private val postModel2: List<ArticlesItem>): RecyclerView.Adapter<PostAdapter2.PostViewHolder2>() {
    class PostViewHolder2(private var binding: AnotherRowBinding): RecyclerView.ViewHolder (binding.root){
        fun bindView(postModel2: ArticlesItem){
            Glide.with(itemView.context)
                .load(postModel2.urlToImage)
                .into(binding.imgRow)
            binding.authorRow.text = postModel2.author
            binding.publishedAt.text = postModel2.publishedAt
            binding.titleRow.text = postModel2.title
            binding.descRow.text = postModel2.description
            binding.sourceRow.text = postModel2.source?.name

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailNewsActivity2::class.java)
                intent.putExtra(DetailNewsActivity2.AUTHOR, postModel2.author)
                intent.putExtra(DetailNewsActivity2.ARTICLEIMAGE, postModel2.urlToImage as String)
                intent.putExtra(DetailNewsActivity2.TITLE, postModel2.title)
                intent.putExtra(DetailNewsActivity2.DESCRIPTION, postModel2.description)
                intent.putExtra(DetailNewsActivity2.ARTICLEURL, postModel2.url)
                intent.putExtra(DetailNewsActivity2.PUBLISHEDAT, postModel2.publishedAt)

                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder2 {
        val binding = AnotherRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder2(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder2, position: Int) {
        return holder.bindView(postModel2[position])
    }

    override fun getItemCount(): Int {
        return postModel2.size
    }
}

