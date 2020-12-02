package com.carrati.vadebicicleta.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.carrati.vadebicicleta.R
import com.carrati.vadebicicleta.data.ConfigFirebase
import com.carrati.vadebicicleta.databinding.ActivityLoginBinding
import com.carrati.vadebicicleta.domain.models.Usuario
import com.carrati.vadebicicleta.presentation.main.HistoricoActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity: AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        supportActionBar!!.title = "Login"
        binding.btLogin.setOnClickListener {
            val txtEmail: String = binding.etLoginEmail.text.toString()
            val txtSenha: String = binding.etLoginPass.text.toString()

            if (validAnswers(txtEmail, txtSenha)) {
                val user = Usuario().apply {
                    email = txtEmail
                    pass = txtSenha
                }

                login(user)
            }
        }
    }

    private fun validAnswers(txtEmail: String, txtSenha: String): Boolean {
        //validar se os campos foram preenchidos
        if (txtEmail.isNotEmpty()) {
            if (txtSenha.isNotEmpty()) {
                return true
            } else {
                Toast.makeText(this@LoginActivity, "Preencha a senha!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@LoginActivity, "Preencha o e-mail!", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun login(user: Usuario) {
        viewModel.efetuarLogin(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@LoginActivity, "Sucesso ao fazer login!", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this@LoginActivity, HistoricoActivity::class.java))
                finish()
            } else {
                val err: String
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthInvalidUserException) {
                    err = "Usuário não cadastrado."
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    err = "Senha Inválida."
                } catch (e: Exception) {
                    err = "Erro ao fazer login!" + e.message
                    ConfigFirebase().sendThrowableToFirebase(e)
                }
                Toast.makeText(this@LoginActivity, err, Toast.LENGTH_SHORT).show()
            }
        }
    }
}