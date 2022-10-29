package app.ericn.myqa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.ericn.myqa.databinding.ListItemBinding
import com.google.android.material.chip.Chip

class QnaListAdapter: RecyclerView.Adapter<QnaListAdapter.QaViewHolder>() {
    class QaViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListUiItem) {
            binding.chips.removeAllViews()
            when (item) {
                is ListUiItem.MultiChoice -> {
                    binding.question.text = "${item.question.id}:${item.question.text}"
                    item.options.forEach { option ->
                        binding.chips.addView(Chip(itemView.context).apply {
                            text = "${option.id}:${option.text}"
                        })
                    }
                }
                is ListUiItem.Text -> {
                    binding.question.text = item.question
                }
            }
        }

    }

    private val items = mutableListOf<ListUiItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QaViewHolder {
        return QaViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: QaViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
    fun submitList(list: List<ListUiItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}