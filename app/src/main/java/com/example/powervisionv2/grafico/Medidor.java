package com.example.powervisionv2.grafico;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Medidor {

    //Genera el Grafico de Intensidad
    public ArcGauge GeneratorGraphicsIntensidad(ArcGauge retorno)
    {
        com.ekn.gruzer.gaugelibrary.Range Rango_1, Rango_2, Rango_3;
        Rango_1=new com.ekn.gruzer.gaugelibrary.Range();
        Rango_2 = new com.ekn.gruzer.gaugelibrary.Range();
        Rango_3 = new com.ekn.gruzer.gaugelibrary.Range();

        Rango_1.setFrom(0);Rango_1.setTo(2);
        Rango_2.setFrom(2);Rango_2.setTo(3);
        Rango_3.setFrom(3);Rango_3.setTo(4);

        Rango_1.setColor(Color.GREEN);
        Rango_2.setColor(Color.YELLOW);
        Rango_3.setColor(Color.RED);


        retorno.setMinValue(0);
        retorno.setMaxValue(4);
        //  retorno.setValue(30.5);

        retorno.addRange(Rango_1);
        retorno.addRange(Rango_2);
        retorno.addRange(Rango_3);
        return retorno;
    }
    //Genera el Grafico de Corriente
    public ArcGauge GeneratorGraphicsCorriente(ArcGauge retorno)
    {
        com.ekn.gruzer.gaugelibrary.Range Rango_1, Rango_2, Rango_3;
        Rango_1=new com.ekn.gruzer.gaugelibrary.Range();
        Rango_2 = new com.ekn.gruzer.gaugelibrary.Range();
        Rango_3 = new com.ekn.gruzer.gaugelibrary.Range();

        Rango_1.setFrom(0);Rango_1.setTo(200);
        Rango_2.setFrom(200);Rango_2.setTo(300);
        Rango_3.setFrom(300);Rango_3.setTo(400);

        Rango_1.setColor(Color.GREEN);
        Rango_2.setColor(Color.YELLOW);
        Rango_3.setColor(Color.RED);

        retorno.setMinValue(0);
        retorno.setMaxValue(400);
        //  retorno.setValue(30.5);

        retorno.addRange(Rango_1);
        retorno.addRange(Rango_2);
        retorno.addRange(Rango_3);
        return retorno;
    }
    // Gráfico estatico de consumo
    public LineChart GeneratorGraphicsHistory(LineChart retorno, Map<String, Float> data, String labr){
        List<Entry> entries = new ArrayList<>();
        int index = 0;
        for (Float value : data.values()) {
            entries.add(new Entry(index++, value));
        }
        LineDataSet dataSet = new LineDataSet(entries, labr);
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//version 02
        // Añadir relleno degradado
        dataSet.setDrawFilled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Drawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                    new int[]{Color.rgb(255,128,0), Color.TRANSPARENT});
            dataSet.setFillDrawable(gradientDrawable);
        } else {
            dataSet.setFillColor(Color.rgb(255,128,0));
        }
        LineData lineData = new LineData(dataSet);
        retorno.setData(lineData);
        retorno.invalidate(); // Refrescar el gráfico
        return retorno;
    }
}
