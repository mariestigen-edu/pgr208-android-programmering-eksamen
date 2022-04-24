package no.kristiania.reverseimagesearch.view.adapter

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import no.kristiania.reverseimagesearch.databinding.SearchResultItemBinding
import no.kristiania.reverseimagesearch.model.entity.ResultImage
import no.kristiania.reverseimagesearch.viewmodel.utils.BitmapUtils
import no.kristiania.reverseimagesearch.viewmodel.utils.ViewUtils


// Denne klassen forteller recyclerview hvordan den skal vise data fra databasen.
class SearchResultItemAdapter :
    ListAdapter<ResultImage, SearchResultItemAdapter.ResultItemViewHolder>(
        SearchResultDiffItemCallback()
    ) {
    val selectedImagesForSave = mutableListOf<ResultImage>()


    // Når den indre klassen under instansieres (dette fungerer som et rot-element for å stappe result_item xml-fila inn i.
    // Den blir inflatet i den indre klassen
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultItemViewHolder =
        ResultItemViewHolder.inflateFrom(parent)


    // Denne kalles for hver gang en recyclerview blir opprettet eller brukt på nytt,
    // for å legge til data i viewet. Dette skjer også i den indre klassen under
    override fun onBindViewHolder(holder: ResultItemViewHolder, position: Int) {
        Log.i("onBind", "binding item")
        val item = getItem(position)
        val image = holder.binding.image

        image.setOnClickListener {
            ViewUtils().fullSizeImage((image.drawable as BitmapDrawable).bitmap, it.context)
        }

        holder.binding.saveResult.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                item.data = BitmapUtils.bitmapToByteArray((image.drawable as BitmapDrawable).bitmap)
                selectedImagesForSave.add(item)
            } else if (!isChecked) {
                selectedImagesForSave.remove(item)
            }
        }
        holder.bind(item)
    }

    // denne klassen har ansvar for å legge til data i hvert result_item.xml som benyttes i recyclerviewet, samt å inflate de
    class ResultItemViewHolder(val binding: SearchResultItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): ResultItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchResultItemBinding.inflate(layoutInflater, parent, false)
                return ResultItemViewHolder(binding)
            }
        }

        fun bind(resultImage: ResultImage) {
            Log.i("Load image", resultImage.toString())

            Glide.with(binding.root)
                .load(resultImage.serverPath)
                .into(binding.image)
            //binding.resultItem = resultImage
        }
    }


}

/*
StfalconImageViewer.Builder<Image>(context, images) { view, image ->
    Picasso.get().load(image.url).into(view)
}.show()
 */