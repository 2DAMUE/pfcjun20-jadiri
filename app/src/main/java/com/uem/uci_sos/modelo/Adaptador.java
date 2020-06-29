package com.uem.uci_sos.modelo;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uem.uci_sos.R;
import com.uem.uci_sos.modelo.entidad.Hospital;
import com.uem.uci_sos.Buscar;

import java.util.List;

/**
 * Adaptador del RecyclerView. Se encarga de llenar el RecyclerView.
 */
public class Adaptador extends RecyclerView.Adapter<Adaptador.MyHolder> {

    /**
     * Lista de elementos para cargar en el RecyclerView
     *
     * @see Hospital
     */
    private List<Hospital> hospitales;

    private OnClickCustom onClickCustom;

    /**
     * ViewHolder del RecylerView. Se encarga de cargar el layout de cada tarjeta el RecyclerView. Contiene todos los elementos de cada tarjeta.
     *
     * @see Adaptador
     */
    public static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * TextView de cada tarjeta
         */
        private TextView lblHospital, lblUCI, lblPlanta, lblUrgencias, lblDisponible;

        private OnClickCustom onClickCustom;

        public MyHolder(@NonNull View v, OnClickCustom onClickCustom) {
            super(v);

            lblHospital = v.findViewById(R.id.lblHospital);

            lblUCI = v.findViewById(R.id.lblUCI);
            lblUrgencias = v.findViewById(R.id.lblUrgencias);
            lblPlanta = v.findViewById(R.id.lblPlanta);
            lblDisponible = v.findViewById(R.id.lblDisponible);

            v.setOnClickListener(this);

            this.onClickCustom = onClickCustom;
        }

        @Override
        public void onClick(View v) {
            onClickCustom.click(getAdapterPosition());
        }
    }

    /**
     * Constructor del adaptador en él le pasamos los objetos con los que se van a construir las tarjetas
     *
     * @param hospitales lista de hospitales
     * @see Hospital
     */
    public Adaptador(List<Hospital> hospitales, OnClickCustom onClickCustom) {
        this.hospitales = hospitales;
        this.onClickCustom = onClickCustom;
    }


    @NonNull
    @Override
    public Adaptador.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adaptador, parent, false);
        return new MyHolder(v, onClickCustom);
    }

    /**
     * Carga los datos en los elementos de cada ventana
     *
     * @param holder   objeto de la clase MyHolder en el que se inicializan los elementos de cada tarjeta
     * @param position posición del List qe recorre en ese momento
     * @see Adaptador#hospitales
     * @see MyHolder
     */
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Hospital h = hospitales.get(position);

        int camasDisponibles = h.getCamasPlantaLibres() + h.getCamasUrgenciasLibres() + h.getCamasUciLibres();

        holder.lblHospital.setText(h.getNombre());
        holder.lblHospital.setTypeface(holder.lblHospital.getTypeface(), Typeface.BOLD);

        holder.lblUCI.setText("UCI: " + String.valueOf(h.getCamasUciLibres()));
        holder.lblPlanta.setText("PLanta: " + String.valueOf(h.getCamasPlantaLibres()));
        holder.lblUrgencias.setText("Urgencias: " + String.valueOf(h.getCamasUrgenciasLibres()));

        holder.lblDisponible.setText("Disponibles: " + String.valueOf(camasDisponibles));
        holder.lblDisponible.setTypeface(holder.lblDisponible.getTypeface(), Typeface.BOLD);
    }

    /**
     * Cantidad de items que se le pasan al adaptador
     *
     * @return size del List de hospitales
     * @see Adaptador#hospitales
     */
    @Override
    public int getItemCount() {
        return hospitales.size();
    }

    /**
     * Interfaz personalizada para añadir un listener al RecyclerView de la ventana Buscar
     *
     * @see Buscar
     */
    public interface OnClickCustom {
        /**
         * Método a implementar que se ejecuta al hacer click en un elemento del RecyclerView
         *
         * @param position posición del elemento del RecyclerView
         */
        void click(int position);
    }
}
