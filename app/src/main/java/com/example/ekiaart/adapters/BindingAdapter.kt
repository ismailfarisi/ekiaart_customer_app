package com.example.ekiaart.adapters


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.storage.FirebaseStorage


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
        Glide.with(view.context)
            .load(storageReference)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }

}
