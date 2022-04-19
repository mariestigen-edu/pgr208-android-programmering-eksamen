package no.kristiania.reverseimagesearch.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import no.kristiania.reverseimagesearch.databinding.SavedSearchItemBinding
import no.kristiania.reverseimagesearch.model.entity.RequestImage

class SavedSearchItemAdapter : ListAdapter<RequestImage, SavedSearchItemAdapter.SavedSearchItemViewHolder>(SavedSearchDiffItemCallback()) {

    class SavedSearchItemViewHolder(val binding: SavedSearchItemBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): SavedSearchItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SavedSearchItemBinding.inflate(layoutInflater, parent, false)
                return SavedSearchItemViewHolder(binding)
            }
        }
        fun bind(item: RequestImage) {
            Log.d("bind", "Binding")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSearchItemViewHolder {
        return SavedSearchItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: SavedSearchItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
