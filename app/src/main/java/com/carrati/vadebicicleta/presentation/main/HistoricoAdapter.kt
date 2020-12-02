package com.carrati.vadebicicleta.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carrati.vadebicicleta.R
import com.carrati.vadebicicleta.domain.models.Aluguel
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class HistoricoAdapter(private val list: List<Aluguel>): RecyclerView.Adapter<HistoricoAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLista: View = LayoutInflater.from(parent.context).inflate(R.layout.item_aluguel, parent, false)
        return MyViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Aluguel = list[position]

        holder.data.text = item.data
        holder.valor.text = "R$ ${item.custo.toString().replace(".", ",")}"
        holder.periodo.text = item.periodo
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var data: TextView = itemView.findViewById(R.id.tv_data)
        var valor: TextView = itemView.findViewById(R.id.tv_valor)
        var periodo: TextView = itemView.findViewById(R.id.tv_periodo)
    }
}