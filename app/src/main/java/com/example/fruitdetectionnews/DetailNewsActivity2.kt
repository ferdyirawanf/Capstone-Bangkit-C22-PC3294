package com.example.fruitdetectionnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailNewsActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news2)
//        val story = intent.getParcelableExtra<ArticlesItem>("Data") as ArticlesItem

        val author = intent.getStringExtra(AUTHOR)
        val publishedAt = intent.getStringExtra(PUBLISHEDAT)
        val description = intent.getStringExtra(DESCRIPTION)
        val articleurl = intent.getStringExtra(ARTICLEURL)
        val articletitle = intent.getStringExtra(TITLE)
        val articleimage = intent.getStringExtra(ARTICLEIMAGE)

        val news_paper_article_date : TextView = findViewById(R.id.news_paper_article_date)
        val news_paper_article_image: ImageView = findViewById(R.id.news_paper_article_image)
        val news_paper_article_title: TextView = findViewById(R.id.news_paper_article_title)
        val news_paper_article_author: TextView = findViewById(R.id.news_paper_article_author)
        val news_paper_article_text: TextView = findViewById(R.id.news_paper_article_text)
        val news_paper_article_url: TextView = findViewById(R.id.news_paper_article_url)

        Glide.with(this).load(articleimage).into(news_paper_article_image)
        news_paper_article_title.text = articletitle

        news_paper_article_author.text = author
        news_paper_article_text.text = description
        news_paper_article_url.text = articleurl
        news_paper_article_date.text = publishedAt
    }

    companion object {
        const val AUTHOR = "author"
        const val PUBLISHEDAT = "publishetAt"
        const val DESCRIPTION = "description"
        const val ARTICLEURL = "articleurl"
        const val TITLE = "articletitle"
        const val ARTICLEIMAGE = "articleimage"
    }
}