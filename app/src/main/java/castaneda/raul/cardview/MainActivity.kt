package castaneda.raul.cardview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    var lugares:ArrayList<Lugar> ?=null
    var adapter:LugarAdapter ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        miRecycler.layoutManager = GridLayoutManager(applicationContext,1 )!!
        miRecycler.setHasFixedSize(true)
        lugares = ArrayList()
        adapter = LugarAdapter(lugares!!,this)
        miRecycler.adapter = adapter
        //miRecycler.adapter=adapter
        //miRecycler.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        //miRecycler.layoutManager=StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        val cache = DiskBasedCache(cacheDir,1024*1024)
        val network = BasicNetwork(HurlStack())

        val requestQueue = RequestQueue(cache,network).apply {
            start()
        }
        val url = "https://randomuser.me/api/?results=10"

        val jsonObjectLugares = JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener { response ->
                val resultadosJSON = response.getJSONArray("results")

                for (indice in 0..resultadosJSON.length()-1){
                    val personaJSON = resultadosJSON.getJSONObject(indice)
                    val genero = personaJSON.getString("gender")
                    val nombreJSON = personaJSON.getJSONObject("name")
                    val nombrePersona = "${nombreJSON.getString("title")} ${nombreJSON.getString("first")} ${nombreJSON.getString("last")}"
                    val fotoJSON = personaJSON.getJSONObject("picture")
                    val foto = fotoJSON.getString("large")

                    lugares!!.add(Lugar(nombrePersona,foto,genero))
                }
                //Log.d("respuesta: ", response.toString())

                adapter!!.notifyDataSetChanged()

            },Response.ErrorListener { error ->
                Log.wtf("error volley",error.localizedMessage)
            })


        requestQueue.add(jsonObjectLugares)



    }
}
