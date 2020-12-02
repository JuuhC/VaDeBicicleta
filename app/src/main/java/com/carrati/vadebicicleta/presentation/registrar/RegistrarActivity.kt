package com.carrati.vadebicicleta.presentation.registrar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.carrati.vadebicicleta.R
import com.carrati.vadebicicleta.data.ConfigFirebase
import com.carrati.vadebicicleta.databinding.ActivityRegistrarBinding
import com.carrati.vadebicicleta.domain.models.Usuario
import com.carrati.vadebicicleta.presentation.login.LoginActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrarActivity: AppCompatActivity() {

    private val viewModel: RegistrarViewModel by viewModel()
    private lateinit var binding: ActivityRegistrarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registrar)
        supportActionBar!!.title = "Cadastro"

        binding.btRegister.setOnClickListener {
            val txtEmail: String = binding.etRegEmail.text.toString()
            val txtSenha: String = binding.etRegPass.text.toString()
            if (validAnswers(txtEmail, txtSenha)) {
                //cria objeto usuario
                val user = Usuario().apply {
                    email = txtEmail
                    pass = txtSenha
                }

                //insere no banco
                processResponse(user)
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validAnswers(txtEmail: String, txtSenha: String): Boolean {
        //validar se os campos foram preenchidos
        if (txtEmail.isNotEmpty()) {
            if (txtSenha.isNotEmpty()) {
                return true
            } else {
                Toast.makeText(this@RegistrarActivity, "Preencha a senha!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@RegistrarActivity, "Preencha o e-mail!", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun processResponse(user: Usuario) {
        viewModel.registarUsuario(user).addOnCompleteListener(this@RegistrarActivity) { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this@RegistrarActivity, "Sucesso ao cadastrar usuário!",
                    Toast.LENGTH_SHORT
                ).show()
                val idUser = task.result!!.user!!.uid
                user.id = idUser
                finish()
            } else {
                val err: String
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthWeakPasswordException) {
                    err = "Digite uma senha maior."
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    err = "Por favor, digite um e-mail válido."
                } catch (e: FirebaseAuthUserCollisionException) {
                    err = "Esta conta já foi cadastrada."
                } catch (e: Exception) {
                    err = "Erro ao cadastrar usuário!" + e.message
                    ConfigFirebase().sendThrowableToFirebase(e)
                }
                Toast.makeText(this@RegistrarActivity, err, Toast.LENGTH_SHORT).show()
            }
        }
    }
}