package app.ericn.myqa

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
                    val answerMap = item.answers.associateBy { it.optionId }
                    item.options.forEach { option ->
                        val chipView = Chip(itemView.context).apply {
                            text = "${option.id}:${option.text}"
                            if (answerMap.containsKey(option.id)) {
                                chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.purple_500))
                                setTextColor(Color.WHITE)
                            } else {
                                chipBackgroundColor = ColorStateList.valueOf(Color.LTGRAY)
                                setTextColor(Color.BLACK)
                            }
                        }
                        binding.chips.addView(chipView)

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