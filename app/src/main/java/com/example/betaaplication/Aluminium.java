package com.example.betaaplication;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

public class Aluminium {
    public Future<Float> getAlumCost(float width, float height, String color, String line, Context context){    //las medidas reciben cm
        return Executors.newSingleThreadExecutor().submit(()->{
            AppDatabase db = AppDatabase.getInstance(context);
            if (color == null || color.isEmpty()) {
                throw new IllegalArgumentException("El color no puede ser nulo o vacío." + color);
            }

            float widthm = width/100;
            float heightm = height/100;
            //obtener medida de los perfiles
            float rielSup = widthm-0.012f;
            float rielInf = widthm-0.012f;
            float zocalo = (widthm/2)*2;
            float cabezal = (widthm/2)*2;
            float batiente = (heightm-0.035f)*2;
            float traslapo = (heightm-0.035f)*2;
            float jamba = heightm*2;

            //buscar el preciolineacompleta del aluminio en base al color
            float preciolineacompleta = 0;
             switch (color){
                case "Blanco":
                    preciolineacompleta= parseDbValue(db.daoData().getOneDataValue("preciolinea20blanco"));
                    break;
                case "Bronce (Negro)":
                    preciolineacompleta=parseDbValue(db.daoData().getOneDataValue("preciolinea20bronce"));
                    break;
                case "Madera":
                    preciolineacompleta=parseDbValue(db.daoData().getOneDataValue("preciolinea20madera"));
                    break;
                case "Mate (Aluminio)":
                    preciolineacompleta=parseDbValue(db.daoData().getOneDataValue("preciolinea20mate"));
                    break;
                case "Titaneo":
                    preciolineacompleta=parseDbValue(db.daoData().getOneDataValue("preciolinea20titaneo"));
                    break;
            }
            //buscar el kg/m de cada perfil
            float kgmetrorielsup20 = Float.parseFloat(db.daoData().getOneDataValue("kgmetrorielsup20").get(0).toString());
            float kgmetrorielinf20 = Float.parseFloat(db.daoData().getOneDataValue("kgmetrorielinf20").get(0).toString());
            float kgmetrozocalo20 = Float.parseFloat(db.daoData().getOneDataValue("kgmetrozocalo20").get(0).toString());
            float kgmetrocabezal20 = Float.parseFloat(db.daoData().getOneDataValue("kgmetrocabezal20").get(0).toString());
            float kgmetrobatiente20 = Float.parseFloat(db.daoData().getOneDataValue("kgmetrobatiente20").get(0).toString());
            float kgmetrotraslapo20 = Float.parseFloat(db.daoData().getOneDataValue("kgmetrotraslapo20").get(0).toString());
            float kgmetrojamba20 = Float.parseFloat(db.daoData().getOneDataValue("kgmetrojamba20").get(0).toString());
            //Obtener el total de kg/m de una linea completa
            float totalkgm=kgmetrorielsup20+kgmetrorielinf20+kgmetrozocalo20+kgmetrocabezal20+kgmetrobatiente20+kgmetrotraslapo20+kgmetrojamba20;
            //calcular el costo de cada perfil
            float costoUsadoRs = ((rielSup*(kgmetrorielsup20*preciolineacompleta))/totalkgm)/6;
            float costoUsadoRi = ((rielInf*(kgmetrorielinf20*preciolineacompleta))/totalkgm)/6;
            float costoUsadoZo = ((zocalo*(kgmetrozocalo20*preciolineacompleta))/totalkgm)/6;
            float costoUsadoCa = ((cabezal*(kgmetrocabezal20*preciolineacompleta))/totalkgm)/6;
            float costoUsadoBa = ((batiente*(kgmetrobatiente20*preciolineacompleta))/totalkgm)/6;
            float costoUsadoTr = ((traslapo*(kgmetrotraslapo20*preciolineacompleta))/totalkgm)/6;
            float costoUsadoJa = ((jamba*(kgmetrojamba20*preciolineacompleta))/totalkgm)/6;
            //calcular el costo total del aluminio
            float costoTotal = costoUsadoRs+costoUsadoRi+costoUsadoZo+costoUsadoCa+costoUsadoBa+costoUsadoTr+costoUsadoJa;
            //agregar la perdida
            return costoTotal*1.15f;

        });
    }
    private float parseDbValue(List<String> dbResult) {
        if (dbResult == null || dbResult.isEmpty() || dbResult.get(0) == null) {
            throw new IllegalStateException("Dato no encontrado en la base de datos.");
        }
        return Float.parseFloat(dbResult.get(0));
    }
}
