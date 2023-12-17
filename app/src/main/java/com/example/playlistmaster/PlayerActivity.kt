package com.example.playlistmaster

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaster.databinding.ActivityPlayerBinding
import com.google.android.material.internal.ViewUtils.dpToPx

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val radiusImage = dpToPx(8f, this@PlayerActivity)

        binding.toolbarPlayer.setOnClickListener {
            finish()
        }

        val track = intent.getParcelableExtra<Track>(TRACK)

        Glide.with(this@PlayerActivity)
            .load(track?.getCoverArtwork())
            .placeholder(R.drawable.placeholder_track)
            .centerCrop()
            .transform(RoundedCorners(radiusImage))
            .into(binding.imageAlbum)

        track?.apply {
            binding.trackTimeMills.text = trackTimeMillis.toString()
            binding.releaseDate.text = getDataRealize()
            binding.primaryGenreName.text = primaryGenreName
            binding.country.text = country
            binding.artistName.text = artistName
            binding.collectionName.text = collectionName
            binding.trackNamePlayer.text = trackName
            binding.trackTimeMills.text = getFormattedTrackTime()
        }
    }

    private fun dpToPx(
        dp: Float,
        context: Context,
    ): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    companion object {
        const val TRACK = "track_info"
    }
}
