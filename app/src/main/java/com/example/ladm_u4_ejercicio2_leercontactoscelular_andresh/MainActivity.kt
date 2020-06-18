package com.example.ladm_u4_ejercicio2_leercontactoscelular_andresh

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val siLecturaContactos = 1
    val calllog = 2
    val phoneCalls = 3
    val phoneState = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS),siLecturaContactos)
        }

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALL_LOG),calllog)
        }

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ANSWER_PHONE_CALLS),phoneCalls)
        }

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.CALL_PHONE),phoneState)
        }


    }

 /*   private fun cargarListaContactos() {
        var resultado = ""

        val cursorContactos = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)

        if(cursorContactos!!.moveToFirst()){
            do {
                var idContacto =
                    cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts._ID))
                var nombreContacto =
                    cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                var telefonosContactos = ""
                if(cursorContactos.getInt(cursorContactos.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))>0){
                    var cursorCel = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf<String>(idContacto.toString()),null)
                    while (cursorCel!!.moveToNext()){
                        telefonosContactos += cursorCel.getString(cursorCel.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))+"\n"
                    }
                    cursorCel.close()
                } else {

                }
                resultado += "ID: "+idContacto+"\nNombre: "+nombreContacto+"\nTelefonos:\n"+telefonosContactos+"\n----------------------------------------\n"
            }while (cursorContactos.moveToNext())

        } else{
            resultado = "Contactos:\nNo hay contactos"
        }
      //  textView.setText(resultado)
    }*/

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== siLecturaContactos){
            setTitle("Permiso otorgado")
        }
    }
}
