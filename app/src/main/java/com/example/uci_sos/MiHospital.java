package com.example.uci_sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

/*import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;*/

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MiHospital extends AppCompatActivity implements View.OnClickListener{
    LinearLayout reservar;
    LinearLayout miscamas;
    LinearLayout mihospital;
    AnyChartView graficocircular;
    String[]estados={"Libres","Ocupadas","No disponibles"};
    int[]numeros={250,102,50};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityhospital);
        setupPieChart();
        cargarVista();
        cargarListeners();

        /*Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("John", 10000));
        data.add(new ValueDataEntry("Jake", 12000));
        data.add(new ValueDataEntry("Peter", 18000));

        pie.data(data);

        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);*/
    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i=0; i < estados.length; i++){
            pieEntries.add(new PieEntry(numeros[i], estados[i]));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "Estado??");
        PieData data = new PieData(dataSet);
        dataSet.setColors(getResources().getColor(R.color.colorLibres), getResources().getColor(R.color.colorOcupadas), getResources().getColor(R.color.colorNoDisponible));

        PieChart chart = (PieChart) findViewById(R.id.grafico_circulo);
        chart.setData(data);
        chart.invalidate();
        chart.animateXY(1000,1000);
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