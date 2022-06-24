package com.sobhan.ebcomtask.ui.utils



import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.card.MaterialCardView
import com.sobhan.ebcomtask.R


@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView,url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .placeholder(R.drawable.ic_place_holder)
        .override(imageView.maxWidth,imageView.maxHeight)
        .into(object : SimpleTarget<Drawable>(){
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                imageView.background = resource

            }

        })
}





@BindingAdapter("changeBackground")
fun bindBackground(cardView: MaterialCardView,unread: Boolean) {
    if(unread){
        cardView.backgroundTintList = cardView.context.resources.getColorStateList(R.color.white)
    }else cardView.backgroundTintList = cardView.context.resources.getColorStateList(R.color.message_read_color)

}

@BindingAdapter("changeBookmark")
fun bindBookmark(imageView: ImageView,bookmarked: Boolean) {
    if(bookmarked){
        imageView.setImageResource(R.drawable.ic_colored_bookmark)
    }else{
        imageView.setImageResource(R.drawable.ic_bookmark)
    }

}


















