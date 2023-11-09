package com.example.healthapp01.comment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.healthapp01.R
import com.example.healthapp01.utils.FBAuth

// 댓글 어댑터
class CommentLVAdapter(val commentList: MutableList<CommentModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return commentList.size
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        if(view == null) {
            view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.comment_list_item, parent, false)
        }
        val title = view?.findViewById<TextView>(R.id.titleArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)

        title!!.text = commentList[position].commentTitle
        time!!.text = commentList[position].comementCreatedTime

        return  view!!
    }

}