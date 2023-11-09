package com.example.healthapp01.board

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.healthapp01.R
import com.example.healthapp01.profile.ProfileModel
import com.example.healthapp01.utils.FBAuth

// 게시글 어댑터
class ProfileListLVAdapter(val profileList: MutableList<ProfileModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return profileList.size
    }

    override fun getItem(position: Int): Any {
        return profileList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        view = LayoutInflater.from(parent?.context).inflate(R.layout.profile_list_item, parent, false)

        val title = view?.findViewById<TextView>(R.id.titleArea)
        val content = view?.findViewById<TextView>(R.id.contentArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)

        title!!.text = profileList[position].title
        content!!.text = profileList[position].content
        time!!.text = profileList[position].time

        return view
    }
}