package com.barackbao.aicalligraphy.view.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.barackbao.aicalligraphy.R
import com.barackbao.aicalligraphy.model.FriendsCircleItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_friends_circle.view.*

class FriendCircleItem : FrameLayout {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        View.inflate(context, R.layout.item_friends_circle, this)
    }

    fun setData(data: FriendsCircleItem) {
        Glide.with(context).load(R.drawable.barack).into(user_avatar_img)
        Glide.with(context).load(R.drawable.barack).into(circle_cover_img)
        user_name_tv.text = data?.user.userName
        release_date_tv.text = data?.releaseDate
        item_text_tv.text = data?.itemText
        item_like_num_tv.text = "#点赞：" + data?.likeNum
        item_share_num_tv.text = "#分享：" + data.shareNum
    }
}