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
        this.countries.addAll(countries.sortedByDescending { it.favorite })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = countriesFiltered.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countriesFiltered[position]

        holder.textViewName.text =
            String.format(Locale.getDefault(), "%s", country.name)
        holder.textViewCapital.text = country.capital

        val requestBuilder = GlideToVectorYou
            .init()
            .with(context)
            .requestBuilder

        requestBuilder
            .load(Uri.parse(country.flag))
            .into(holder.imageViewFlag)

        holder.imageViewFavorite.setImageResource(
            if (country.favorite) {
                R.drawable.ic_baseline_star_24
            } else {
                R.drawable.ic_baseline_star_border_24
            }
        )

        holder.container.setOnClickListener {
            listener?.invoke(country)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CountryRowBinding.bind(itemView)
        val textViewName = binding.textViewName
        val textViewCapital = binding.textViewCapital
        val imageViewFlag = binding.imageViewFlag
        val container = binding.container
        val imageViewFavorite = binding.imageViewFavorite
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                Log.i(TAG, "performFiltering: $constraint")

                val charString: String = constraint.toString()

                countriesFiltered = if (charString.isEmpty()) {
                    countries.toMutableList()
                } else {
                    countries.filter {
                        it.name.toLowerCase(Locale.getDefault())
                            .contains(charString.toLowerCase(Locale.getDefault()))
                                || it.capital.toLowerCase(Locale.getDefault())
                            .contains(charString.toLowerCase(Locale.getDefault()))
                    }.toMutableList()

                }

                countriesFiltered.sortByDescending { it.favorite }
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