package com.john_ap.verificatuinquilino

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //funcion para  logiar al usuario
        //login por medio del correo y la contraseña
        login()

        //login por medio del correo de google
        btn_google.setOnClickListener {
            loginGoogle()
        }

        registrarse.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finishAffinity()
        }
    }

    private fun login() {

        btn_entrar.setOnClickListener {
            if (TextUtils.isEmpty(txtUser.text)) {
                Toast.makeText(this, R.string.inputCorreo, Toast.LENGTH_SHORT).show()
                txtUser.requestFocus()
            } else if (TextUtils.isEmpty(txtPassword.text)) {
                Toast.makeText(this, R.string.input_contraseña, Toast.LENGTH_SHORT).show()
                txtPassword.requestFocus()
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    txtUser.text.toString(),
                    txtPassword.text.toString()
                ).addOnCompleteListener {
                    //Comprobamos si la operacion se completo Correctamente
                    if (it.isSuccessful) {
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }

    }

    private fun loginGoogle() {
        // Configuracion
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)

        //delogiamos cualquier cuenta previa que aya podido estar autenticada en ese momento

        googleClient.signOut()

        resultLauncher.launch(googleClient.signInIntent)
    }

    private var resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)

                //creamos un try chat para caturar posibles execiones
                try {
                    //recuperamos la cuenta selecionada por el usuario
                    val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)

                    //verificamos qeu no sea una cuenta nula
                    if (account != null) {
                        //recuperamos la credencial de la cuenta
                        val credential: AuthCredential =
                            GoogleAuthProvider.getCredential(account.idToken, null)

                        //se envia la credencial del correo electronico de google relisando un reguistro de este correo
                        //si se puede reguistrar o ya esta reguistardp el task.isSuccessful retorna true y pasa al home,
                        // si no se puede registrat o verificar la cuenta muestra una alerta
                        //de error ya que no se pudo autenticar al usuario.
                        FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    showHome(account.email ?: " ", ProviderType.GOOGLE)
                                } else {
                                    showAlert()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Cuenta Nula", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                    showAlert()
                }

            }
        }

    private fun showHome(email: String, provider: ProviderType) {

        val homeIntent = Intent(this, HomeActivity::class.java)
        homeIntent.putExtra("email", email)
        homeIntent.putExtra("provider", provider.name)
        startActivity(homeIntent)
        finishAffinity()
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.showalert_title))
        builder.setMessage(getString(R.string.showalert_message))
        builder.setPositiveButton(getString(R.string.showalert_positivebutton), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}