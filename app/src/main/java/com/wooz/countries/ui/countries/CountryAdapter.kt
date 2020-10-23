package com.wooz.countries.ui.countries

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.wooz.countries.R
import com.wooz.countries.databinding.CountryRowBinding
import com.wooz.countries.domain.entity.Country
import java.util.*
import kotlin.collections.ArrayList


/**
 * @author wooz
 * @since 10/10/2020
 */
class CountryAdapter constructor(
    private val countries: MutableList<Country>,
    private val context: Context?
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>(), Filterable {

    private var countriesFiltered = countries

    private var listener: ((Country) -> Unit)? = null
    fun setItemClickListener(listener: ((Country) -> Unit)) {
        this.listener = listener
    }

    fun swapData(countries: List<Country>) {
        this.countries.clear()
        this.countries.addAll(countries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = countriesFiltered.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countriesFiltered[position]

        holder.binding.textViewName.text =
            String.format(Locale.getDefault(), "%s", country.name)
        holder.binding.textViewCapital.text = country.capital

        val requestBuilder = GlideToVectorYou
            .init()
            .with(context)
            .requestBuilder

        requestBuilder
            .load(Uri.parse(country.flag))
            .into(holder.binding.imageViewFlag)

        holder.binding.contianer.setOnClickListener {
            listener?.invoke(country)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CountryRowBinding.bind(itemView)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                Log.i(TAG, "performFiltering: $constraint")
                val charString: String = constraint.toString()
                countriesFiltered = if (charString.isEmpty()) {
                    countries
                } else {
                    val filteredList: ArrayList<Country> = ArrayList()
                    for (row: Country in countries) {
                        if (row.name.toLowerCase().contains(charString.toLowerCase()) ||
                            row.capital.toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = countriesFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                Log.i(TAG, "publishResults: $constraint")
                countriesFiltered = results?.values as ArrayList<Country>
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val TAG = "CountryAdapter"
    }
}