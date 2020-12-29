package com.wooz.countries.ui.countries

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.wooz.countries.databinding.ItemCountryBinding
import com.wooz.countries.domain.entity.Country
import java.util.*


/**
 * @author wooz
 * @since 10/10/2020
 */
class CountryAdapter(private val clickListener: CountryClickListener) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>(), Filterable {

    var data = listOf<Country>()
        set(value) {
            field = value.sortedByDescending { it.favorite }
            countriesFiltered = data
            notifyDataSetChanged()
        }

    private var countriesFiltered = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int = countriesFiltered.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countriesFiltered[position]
        holder.bind(clickListener, country)
    }

    class ViewHolder private constructor(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: CountryClickListener, item: Country) {
            binding.country = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCountryBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                Log.i(TAG, "performFiltering: $constraint")

                val charString: String = constraint.toString()

                countriesFiltered = if (charString.isEmpty()) {
                    data.toMutableList()
                } else {
                    data.filter {
                        it.name.toLowerCase(Locale.getDefault())
                            .contains(charString.toLowerCase(Locale.getDefault()))
                                || it.capital.toLowerCase(Locale.getDefault())
                            .contains(charString.toLowerCase(Locale.getDefault()))
                    }.toMutableList()

                }

                (countriesFiltered as MutableList<Country>).sortByDescending { it.favorite }
                return FilterResults().apply {
                    values = countriesFiltered
                    count = countriesFiltered.size
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val TAG = "CountryAdapter"
    }
}

class CountryClickListener(val clickListener: (code: String) -> Unit) {
    fun onClick(country: Country) = clickListener(country.code)
}