package com.barackbao.aicalligraphy.view.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.barackbao.aicalligraphy.R
import com.barackbao.aicalligraphy.model.FriendsCircle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

    fun setData(data: FriendsCircle) {
        var options = RequestOptions()
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.errorimg)
                .override(45, 45)
        Glide.with(context).load(data.userAvatar).apply(options).into(user_avatar_img)
        options = RequestOptions()
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.errorimg)
                .override(width, 250)
        Glide.with(context).load(data.friends_circle_item.get(0).imgUrl).into(circle_cover_img)
        user_name_tv.text = data.userName
        release_date_tv.text = data.friends_circle_item.get(0).releaseDate.substring(0, 10)
        item_text_tv.text = data?.friends_circle_item.get(0).itemText
        item_like_num_tv.text = "#点赞：" + data.friends_circle_item.get(0).likeNum
        item_share_num_tv.text = "#分享：" + data.friends_circle_item.get(0).shareNum
    }
}