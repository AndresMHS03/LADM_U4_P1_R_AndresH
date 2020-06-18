package com.example.ladm_u4_ejercicio2_leercontactoscelular_andresh

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.ContactsContract
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.core.content.ContextCompat

class IncomingCallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try{

            val estado =intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val numero =intent.extras!!.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if(estado.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                try{
                    if(!estaEnContactos(numero!!,context)){
                        endCall(context)
                        Toast.makeText(context,"Se colgó llamada del numero $numero",Toast.LENGTH_LONG)
                    }
                } catch (e : Exception){
                    e.printStackTrace()
                }
            }
            Toast.makeText(context,"Timbrando del número $numero",Toast.LENGTH_LONG)
        } catch (e : Exception){
            e.printStackTrace()
        }

    }


    fun endCall(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            if (telecomManager != null && ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ANSWER_PHONE_CALLS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                telecomManager.endCall()
                return true
            }
        }
        return false
    }

    fun estaEnContactos(numero : String, context : Context) : Boolean {
        val contentResolver = context.contentResolver
        val cursorContactos = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)

        if(cursorContactos!!.moveToFirst()){
            do {
                var idContacto =
                    cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts._ID))

                if(cursorContactos.getInt(cursorContactos.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))>0){
                    var cursorCel = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf<String>(idContacto.toString()),null)
                    while (cursorCel!!.moveToNext()){
                        if(numero == cursorCel.getString(cursorCel.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).toString()){
                            return true
                        }
                    }
                    cursorCel.close()
                } else {

                }
            }while (cursorContactos.moveToNext())

        } else{
        }
        return false
    }

}