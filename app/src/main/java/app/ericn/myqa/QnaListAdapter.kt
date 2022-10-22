package app.ericn.myqa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.ericn.myqa.databinding.ListItemBinding

class QnaListAdapter: RecyclerView.Adapter<QnaListAdapter.QaViewHolder>() {
    class QaViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListUiItem) {
            binding.question.text = item.question
            when (item) {
                is ListUiItem.MultiChoice -> binding.answers.text = item.answers.joinToString(", ") { it }
                is ListUiItem.Text -> binding.answers.text = item.answer
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
    fun submitList(list: List<ListUiItem.Text>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}