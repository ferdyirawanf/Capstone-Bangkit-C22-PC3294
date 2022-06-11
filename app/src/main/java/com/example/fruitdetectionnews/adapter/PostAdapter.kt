package com.example.fruitdetectionnews.adapter
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fruitdetectionnews.DetailNewsActivity
import com.example.fruitdetectionnews.viewmodel.PostModel
import com.example.fruitdetectionnews.R

class PostAdapter(val postModel: MutableList<PostModel>):RecyclerView.Adapter<PostViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        return holder.bindView(postModel[position])
    }

    override fun getItemCount(): Int {
        return postModel.size
    }
}

class PostViewHolder(itemView: View): RecyclerView.ViewHolder (itemView){

    val button:ImageButton =  itemView.findViewById(R.id.imageButton2)
    private val tvTitle2: TextView = itemView.findViewById(R.id.tvTitle2)
    private val tvSource2: TextView = itemView.findViewById(R.id.tvSource2)
    private val tvImg2: ImageView = itemView.findViewById(R.id.tvImg2)
    private val tvDate2: TextView = itemView.findViewById(R.id.tvDate2)



    fun bindView(postModel: PostModel){


        tvTitle2.text = postModel.title
        tvSource2.text = postModel.source
        tvDate2.text = postModel.NewsDate
        Glide.with(itemView.context).load(postModel.NewsImage).into(tvImg2)

        button.setOnClickListener {
            val intent = Intent(itemView.context, DetailNewsActivity::class.java)
            intent.putExtra("Data", postModel)
            itemView.context.startActivity(intent)
        }

    }
}