package com.example.playlistmaster

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaster.databinding.ActivityPlayerBinding

const val INFO_TRACK = "Track"
class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            binding.toolbarMediaLibrary .setOnClickListener {
                finish()
            }
            //val track = intent.getParcelableExtra<Track>(INFO_TRACK)
            //не совместим с 26 мин вер. нужна 33
            val track = intent.getParcelableExtra("track",Track::class.java)

            Glide.with(this@PlayerActivity)
                .load(track?.getCoverArtwork())
                .placeholder(R.drawable.placeholder_track)
                .centerCrop()
                .transform()
                .into(binding.imageView)

            track?.apply {
                binding.trackTimeMills.text = trackTimeMillis.toString()
                binding.releaseDate.text = releaseDate
                binding.primaryGenreName.text = primaryGenreName
                binding.country.text = country
                binding.artistName.text = artistName
                binding.collectionName.text = collectionName
                //timeTrack.text = getTimeTrack()
                //visibleGroup.isVisible = collectionName != null
            }
        }
    }

}