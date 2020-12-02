package com.carrati.vadebicicleta.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.carrati.vadebicicleta.R
import com.carrati.vadebicicleta.data.ConfigFirebase
import com.carrati.vadebicicleta.databinding.ActivityHistoricoBinding
import com.carrati.vadebicicleta.domain.models.Aluguel
import com.carrati.vadebicicleta.presentation.registrar.RegistrarActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class HistoricoActivity : AppCompatActivity() {

    private val viewModel: HistoricoViewModel by viewModel()
    private lateinit var binding: ActivityHistoricoBinding
    private lateinit var adapter: HistoricoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_historico)

        binding.fab.setOnClickListener {
            alugarBicicleta()
        }

        carregarHistorico()
    }

    override fun onStart() {
        super.onStart()
        verificaInstanciaUsuario()
    }

    private fun carregarHistorico(){
        val alugueis = mutableListOf<Aluguel>()
        try {
            viewModel.getHistorico().addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    alugueis.clear()
                    for (dado in dataSnapshot.children) {
                        val aluguel: Aluguel? = dado.getValue(Aluguel::class.java)
                        Log.e("aluguel", "data: " + aluguel?.data + "valor: " + aluguel?.custo);
                        if (aluguel != null) {
                            alugueis.add(aluguel)
                        }
                    }
                    adapter = HistoricoAdapter(alugueis)
                    binding.rvList.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    ConfigFirebase().sendThrowableToFirebase(Throwable(databaseError.message))
                    Toast.makeText(
                        this@HistoricoActivity,
                        "Erro ao carregar histórico.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        } catch (t: Throwable){
            ConfigFirebase().sendThrowableToFirebase(t)
            Toast.makeText(this, t.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun alugarBicicleta(){
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_rfid, null)
        AlertDialog.Builder(this)
            .setTitle("Insira o RFID")
            .setView(view)
            .setCancelable(false)
            .setPositiveButton("Confirmar") { dialog, _ ->
                val rfid = view.findViewById<TextInputEditText>(R.id.rfid).text.toString()
                if(!rfid.isNullOrEmpty()) {
                    try {
                        viewModel.alugarBicicleta(
                            Aluguel().apply {
                                this.rfid = rfid
                                data = SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis())
                                periodo = "${Random.nextInt(0, 120)} min ${Random.nextInt(0, 60)} seg"

                                val df = DecimalFormat("#.##", DecimalFormatSymbols(Locale.ENGLISH))
                                df.roundingMode = RoundingMode.HALF_UP
                                custo = df.format(Random.nextDouble(0.0, 20.9))
                            }
                        )
                    } catch (t: Throwable){
                        ConfigFirebase().sendThrowableToFirebase(t)
                        Toast.makeText(this, t.message, Toast.LENGTH_LONG).show()
                    }
                } else Toast.makeText(this, "RFID inválido!", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun verificaInstanciaUsuario() {
        val user = viewModel.usuarioLogado()
        if (user != null) {
            supportActionBar!!.title = "Olá, ${user.email.split("@").first()}"
        } else {
            startActivity(Intent(this, RegistrarActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.historico_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sair -> {
                viewModel.deslogar()
                finish()
                startActivity(Intent(this, RegistrarActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}