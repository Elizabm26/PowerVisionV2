package com.example.powervisionv2.grafico;

import android.graphics.Color;

import com.ekn.gruzer.gaugelibrary.ArcGauge;

public class Medidor {

    //Genera el Grafico de Intensidad
    public ArcGauge GeneratorGraphicsIntensidad(ArcGauge retorno)
    {
        com.ekn.gruzer.gaugelibrary.Range Rango_1;
        Rango_1=new com.ekn.gruzer.gaugelibrary.Range();

        Rango_1.setFrom(0);Rango_1.setTo(4);
        Rango_1.setColor(Color.YELLOW);

        retorno.setMinValue(0);
        retorno.setMaxValue(4);
        //  retorno.setValue(30.5);

        retorno.addRange(Rango_1);
        return retorno;
    }
    //Genera el Grafico de Corriente
    public ArcGauge GeneratorGraphicsCorriente(ArcGauge retorno)
    {
        com.ekn.gruzer.gaugelibrary.Range Rango_1;
        Rango_1=new com.ekn.gruzer.gaugelibrary.Range();

        Rango_1.setFrom(0);Rango_1.setTo(400);
        Rango_1.setColor(Color.GREEN);

        retorno.setMinValue(0);
        retorno.setMaxValue(400);
        //  retorno.setValue(30.5);

        retorno.addRange(Rango_1);
        return retorno;
    }
}
