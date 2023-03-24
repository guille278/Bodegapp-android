package com.example.bodegapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.helper.widget.Carousel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bodegapp.databinding.ActivityWarehouseDetailBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.text.NumberFormat
import java.util.*

class WarehouseDetail : AppCompatActivity() {

    private lateinit var binding: ActivityWarehouseDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarehouseDetailBinding.inflate(layoutInflater)

        val id = intent.getStringExtra("id")

        binding.toolbarWarehouseDetail.title = ""

        setSupportActionBar(binding.toolbarWarehouseDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val queue = Volley.newRequestQueue(baseContext)

        val request = JsonObjectRequest("${getString(R.string.api_url)}/storages/${id}", { data ->
            val list = mutableListOf<CarouselItem>()

            (0 until data.getJSONArray("images").length()).forEach {
                list.add(
                    CarouselItem(
                        imageUrl = resources.getString(R.string.image_url) + data.getJSONArray("images").getJSONObject(it).getString("src")
                    )
                )
            }

            binding.detailTitle.setText(data.getString("title"))
            binding.detailPrice.setText(NumberFormat.getCurrencyInstance(Locale.US).format(data.getDouble("price")))
            binding.detailDescription.setText(data.getString("description"))
            binding.detailM2.setText("Metros cuadrados: ${data.getString("m2")}")

            binding.rvDetailServices.adapter = ServiceAdapter(data.getJSONArray("services"))
            binding.rvDetailServices.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.carousel.setData(list)
        }, {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        })

        queue.add(request)


        binding.checkout.setOnClickListener {
            val intent = Intent(this, Checkout::class.java)
            intent.putExtra("storage_id", id)
            startActivity(intent)
        }


        val view = binding.root
        setContentView(view)
    }
}