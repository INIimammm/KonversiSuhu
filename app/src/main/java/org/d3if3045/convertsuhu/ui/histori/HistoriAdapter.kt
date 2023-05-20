package org.d3if3045.convertsuhu.ui.histori




import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if3045.convertsuhu.R
import org.d3if3045.convertsuhu.databinding.ItemHistoriBinding
import org.d3if3045.convertsuhu.db.SuhuEntity
import org.d3if3045.convertsuhu.model.hitungSuhu
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter : ListAdapter<SuhuEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<SuhuEntity>() {
                override fun areItemsTheSame(
                    oldData: SuhuEntity, newData: SuhuEntity
                ): Boolean {
                    return oldData.id == newData.id
                }
                override fun areContentsTheSame(
                    oldData: SuhuEntity, newData: SuhuEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )
        fun bind(item: SuhuEntity) = with(binding) {
            val hasilKonversi = item.hitungSuhu()
            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            suhuTextView.text = root.context.getString(
                R.string.hasil_x,
                hasilKonversi.result)
            dataTextView.text = root.context.getString(R.string.data_x,
                item.value, item.from, item.to)
        }
    }
}