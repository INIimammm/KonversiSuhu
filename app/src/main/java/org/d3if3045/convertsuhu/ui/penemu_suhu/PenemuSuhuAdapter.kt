package org.d3if3045.convertsuhu.ui.penemu_suhu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3045.convertsuhu.R
import org.d3if3045.convertsuhu.databinding.ItemPenemuSuhuBinding
import org.d3if3045.convertsuhu.model.PenemuSuhu
import org.d3if3045.convertsuhu.network.PenemuSuhuApi

class PenemuSuhuAdapter : RecyclerView.Adapter<PenemuSuhuAdapter.ViewHolder>(){

    private val data = mutableListOf<PenemuSuhu>()
    fun updateData(newData: List<PenemuSuhu>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemPenemuSuhuBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(penemuSuhu: PenemuSuhu) = with(binding) {
            tvName.text = penemuSuhu.nama
            tvDescription.text = penemuSuhu.deskripsi
            Glide.with(ivPeopleImg.context)
                .load(PenemuSuhuApi.getPenemuSuhuUrl(penemuSuhu.imageId))
                .error(R.drawable.baseline_broken_image_24)
                .into(ivPeopleImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPenemuSuhuBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}