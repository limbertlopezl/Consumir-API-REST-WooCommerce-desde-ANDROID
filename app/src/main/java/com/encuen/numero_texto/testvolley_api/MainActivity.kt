package com.encuen.numero_texto.testvolley_api

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.Base64

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var usuario:String="generado_WooCommerce"
        var clave:String="generado_WooCommerce"
        var url:String="Dominio/wp-json/wc/v2/products"

        var rq:RequestQueue=Volley.newRequestQueue(this)

        var sr=object :JsonArrayRequest(Request.Method.GET,url,null,Response.Listener { response ->
            var listaProductos:String=""
            for (x in 0..response.length()-1) {
                listaProductos += "Producto: " + response.getJSONObject(x).getString("name") +"\n"
                listaProductos += "Precio: " + response.getJSONObject(x).getString("price") +"\n \n"
            }
            textView.text=listaProductos

        },Response.ErrorListener{error ->
            textView.text=error.message
        })
        {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                val auth = Base64.getEncoder().encode("$usuario:$clave".toByteArray()).toString(Charsets.UTF_8)
                headers.put("Authorization", "Basic $auth")
                return headers
            }
        }

        rq.add(sr)
    }
}
