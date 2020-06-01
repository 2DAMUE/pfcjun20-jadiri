package com.example.uci_sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uci_sos.modelo.entidad.Hospital;
import com.example.uci_sos.modelo.entidad.Referencias;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MiHospitall extends AppCompatActivity implements View.OnClickListener {
    LinearLayout reservar;
    LinearLayout miscamas;
    LinearLayout mihospital;
    BarChart barChart;
    TextView name;
    float plantalibres;
    float ucilibres;
    float emerglibres;
    float plantaocupadas;
    float uciocupadas;
    float emergocupadas;
    TextView numeroplanta;
    TextView numerouci;
    TextView numerourgen;
    private DatabaseReference mDataBase;
    ArrayList<String> estados;
    float[] numeros = {250, 102, 50, 230, 30, 2, 56, 43, 0};
    ArrayList<BarEntry> barEntryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityhospital);
        int colores[] = new int[]{getResources().getColor(R.color.colorOcupadas), getResources().getColor(R.color.colorNoDisponible), getResources().getColor(R.color.colorLibres)};
        barChart = (BarChart) findViewById(R.id.graficobarras);
        name=findViewById(R.id.nombre);
        FirebaseDatabase mDataBase= FirebaseDatabase.getInstance();
        final DatabaseReference hospital = mDataBase.getReference(Referencias.HOSPITALES);
        hospital.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numeroplanta = findViewById(R.id.lbl_numCamasPlanta);
                numerouci = findViewById(R.id.lbl_numCamasUcis);
                numerourgen = findViewById(R.id.lbl_numCamasUrgencia);
                Hospital h = dataSnapshot.child("10").getValue(Hospital.class);
                plantalibres = h.getCamasPlantaLibres();
                ucilibres = h.getCamasUciLibres();
                emerglibres=h.getCamasUrgenciasLibres();
                plantaocupadas=h.getListaCamasPlanta().size()-plantalibres;
                uciocupadas=h.getListaCamasUCI().size()-ucilibres;
                emergocupadas=h.getListaCamasPlanta().size()-emerglibres;
                Log.e("NOMBRe", ""+h.getNombre());
                Log.e("PLANTA LIBRE: ", String.valueOf(plantalibres));
                Log.e("PLANTA LIBRE: ", String.valueOf(ucilibres));
                barChart.setDrawBarShadow(false);
                barChart.setDrawValueAboveBar(true);
                barChart.setMaxVisibleValueCount(50);
                barChart.setPinchZoom(false);
                barChart.setDrawGridBackground(true);

                String[] listas = {"UCI", "PLANTA", "URGENCIAS"};
                ArrayList<BarEntry> barEntries1 = new ArrayList<>();
                ArrayList<BarEntry> barEntries2 = new ArrayList<>();
                ArrayList<BarEntry> barEntries3 = new ArrayList<>();
                ArrayList<BarEntry> barEntries4 = new ArrayList<>();
                ArrayList<BarEntry> barEntries5 = new ArrayList<>();
                ArrayList<BarEntry> barEntries6 = new ArrayList<>();
                ArrayList<BarEntry> barEntries7 = new ArrayList<>();
                ArrayList<BarEntry> barEntries8 = new ArrayList<>();
                ArrayList<BarEntry> barEntries9 = new ArrayList<>();
                barEntries1.add(new BarEntry(1, plantalibres));
                Log.e("KK", String.valueOf(plantalibres));
                barEntries2.add(new BarEntry(2, plantaocupadas));
                barEntries3.add(new BarEntry(3, 0));
                barEntries4.add(new BarEntry(2, ucilibres));
                Log.e("KK", String.valueOf(ucilibres));
                barEntries5.add(new BarEntry(1, uciocupadas));
                barEntries6.add(new BarEntry(3, 0));
                barEntries7.add(new BarEntry(3, emerglibres));
                barEntries8.add(new BarEntry(2, emergocupadas));
                barEntries9.add(new BarEntry(2, 0));


                BarDataSet plantalibre = new BarDataSet(barEntries1, "");
                plantalibre.setColors(getResources().getColor(R.color.colorOcupadas));
                BarDataSet plantaocupada = new BarDataSet(barEntries2, "");
                plantaocupada.setColors(getResources().getColor(R.color.colorLibres));
                BarDataSet plantanodis = new BarDataSet(barEntries3, "");
                plantanodis.setColors(getResources().getColor(R.color.colorNoDisponible));
                BarDataSet ucilibre = new BarDataSet(barEntries4, "");
                ucilibre.setColors(getResources().getColor(R.color.colorOcupadas));
                BarDataSet uciopcuapada = new BarDataSet(barEntries5, "");
                uciopcuapada.setColors(getResources().getColor(R.color.colorLibres));
                BarDataSet ucinodisp = new BarDataSet(barEntries6, "");
                ucinodisp.setColors(getResources().getColor(R.color.colorNoDisponible));
                BarDataSet urgendisp = new BarDataSet(barEntries7, "");
                urgendisp.setColors(getResources().getColor(R.color.colorOcupadas));
                BarDataSet urgenocupada = new BarDataSet(barEntries8, "");
                urgenocupada.setColors(getResources().getColor(R.color.colorLibres));
                BarDataSet urgenodis = new BarDataSet(barEntries9, "");
                urgenodis.setColors(getResources().getColor(R.color.colorNoDisponible));
                Description description = new Description();
                description.setText("");
                barChart.setDescription(description);
                BarData data = new BarData(plantalibre, plantaocupada, plantanodis, ucilibre, uciopcuapada , ucinodisp, urgendisp, urgenocupada, urgenodis);
                Log.e("NUMERO", ""+h.getListaCamasPlanta().size());
                numeroplanta.setText(h.getListaCamasPlanta().size()+"");
                numerouci.setText(h.getListaCamasUCI().size()+"");
                numerourgen.setText(h.getListaCamasUrgencias().size()+"");
                float groupSpace = 0.1f;
                float barSpace = 0.02f;
                float barWidth = 0.23f;
                barChart.setData(data);
                barChart.getLegend().setEnabled(false);

                data.setBarWidth(barWidth);
                barChart.groupBars(1, groupSpace, barSpace);

                data.setDrawValues(false);
                String[] camillas = new String[]{"", "Planta", "Urgencias", "UCI"};
                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(camillas));
                xAxis.setPosition(XAxis.XAxisPosition.TOP);
                xAxis.setDrawAxisLine(true);
                xAxis.setDrawGridLines(false);
                xAxis.setGranularity(1f);
                xAxis.setCenterAxisLabels(true);
                xAxis.setAxisMinimum(1);
                xAxis.setLabelRotationAngle(20);
                barChart.animateY(1000);
                barChart.invalidate();
                xAxis.setLabelCount(camillas.length);

                cargarVista();
                cargarListeners();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void cargarListeners() {
        reservar.setOnClickListener(this);
        miscamas.setOnClickListener(this);
        mihospital.setOnClickListener(this);

    }


    private void cargarVista() {
        reservar = findViewById(R.id.btnReservarHospital);
        miscamas = findViewById(R.id.btnMisCamasHospital);
        mihospital = findViewById(R.id.btnHospitalHospital);
        //numeroplanta = findViewById(R.id.lbl_numCamasPlanta);
        //numerouci = findViewById(R.id.lbl_numCamasUcis);
        //numerourgen = findViewById(R.id.lbl_numCamasUrgencia);
        //graficocircular = findViewById(R.id.grafico_circulo);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnReservarHospital:
                toReservar();
                break;
            case R.id.btnMisCamasHospital:
                toCamas();
                break;

        }
    }


    private void toReservar() {
        Intent inte = new Intent(this.getApplicationContext(), Reservar.class);
        startActivity(inte);
    }

    private void toCamas() {
        Intent inte = new Intent(this.getApplicationContext(), MisCamas.class);
        finish();
        startActivity(inte);
    }

    private void toPlanta() {
        Intent inte = new Intent(this.getApplicationContext(), ConfigPlanta.class);
        finish();
        startActivity(inte);
    }

    private void toHospital() {
        Intent inte = new Intent(this.getApplicationContext(), Login.class);
        finish();
        startActivity(inte);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


}