package com.example.fruitdetectionnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.fruitdetectionnews.viewmodel.PostModel

class DetailNewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)
        val story = intent.getParcelableExtra<PostModel>("Data") as PostModel

        val news_paper_article_date : TextView = findViewById(R.id.news_paper_article_date)
        val news_paper_article_image: ImageView = findViewById(R.id.news_paper_article_image)
        val news_paper_article_title: TextView = findViewById(R.id.news_paper_article_title)
        val news_paper_article_author: TextView = findViewById(R.id.news_paper_article_author)
        val news_paper_article_text: TextView = findViewById(R.id.news_paper_article_text)
        val news_paper_article_url: TextView = findViewById(R.id.news_paper_article_url)

        Glide.with(this).load(story.NewsImage).into(news_paper_article_image)
        news_paper_article_title.text = story.title
        news_paper_article_author.text = story.author
        news_paper_article_text.text = story.ShortDesc
        news_paper_article_url.text = story.NewsLink
        news_paper_article_date.text = story.NewsDate
    }
}