package castaneda.raul.cardview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.mi_targeta.view.*


class LugarAdapter(lugares:ArrayList<Lugar>, context:Context): RecyclerView.Adapter<LugarAdapter.ViewHolder>() {
    var lugares:ArrayList<Lugar>?=null
    var context:Context?=null

    init{
        this.lugares=lugares
        this.context=context

    }

    override fun getItemCount(): Int {

        return lugares!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vistalugar:View = LayoutInflater.from(context).inflate(R.layout.mi_targeta,parent,false)
        val lugarViewHolder= ViewHolder(vistalugar)
        vistalugar.tag=lugarViewHolder
        return lugarViewHolder
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.nombre!!.text=lugares!![position].nombre
        holder.descripcion!!.text=lugares!![position].descipcion
        Picasso.get().load(lugares!![position].foto).into(holder.imagen)

    }

    class ViewHolder(vista:View):RecyclerView.ViewHolder(vista) {
        var imagen:ImageView?=null
        var nombre:TextView?=null
        var descripcion:TextView?=null

        init {
            imagen = vista.image
            nombre = vista.encabezados
            descripcion = vista.descripciones

        }



    }

}
