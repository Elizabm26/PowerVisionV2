package com.example.powervisionv2.grafico;

import android.graphics.Color;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.ekn.gruzer.gaugelibrary.Range;

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
}
