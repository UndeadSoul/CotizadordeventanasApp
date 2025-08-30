package com.example.betaaplication;

import android.content.Context;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WinAdds {
    public Future<Float> getAddsCost(float width, float height, Context context){
        return Executors.newSingleThreadExecutor().submit(()->{
            AppDatabase db = AppDatabase.getInstance(context);
            float widthm = width/100;
            float heightm = height/100;
            //obtener cantidad/precio felpa
            float mFelpa = ((widthm*6)+(heightm*6))/10;
            float precioRolloFelpa=Float.parseFloat(db.daoData().getOneDataValue("preciorollofelpa55").get(0).toString());
            float mRolloFelpa=Float.parseFloat(db.daoData().getOneDataValue("mrollofelpa55").get(0).toString());
            //obtener precio pestillos l20
            float precioPaquetePestillos=Float.parseFloat(db.daoData().getOneDataValue("preciopackpestillo20").get(0).toString());
            float cantPaquetePestillos=Float.parseFloat(db.daoData().getOneDataValue("cantporpackpestillo20").get(0).toString());
            //obtener precio caracoles l20
            float precioPaqueteCaracol=Float.parseFloat(db.daoData().getOneDataValue("preciopackcaracol20").get(0).toString());
            float cantPaqueteCaracol=Float.parseFloat(db.daoData().getOneDataValue("cantporpackcaracol20").get(0).toString());
            //obtener precio rodamientos l20
            float precioPaqueteRod=Float.parseFloat(db.daoData().getOneDataValue("preciopackrod20").get(0).toString());
            float cantPaqueteRod=Float.parseFloat(db.daoData().getOneDataValue("cantporpackrod20").get(0).toString());
            //obtener precio tornillos
            float precioPaqueteTornillos=Float.parseFloat(db.daoData().getOneDataValue("preciopacktornillo").get(0).toString());
            float cantPaqueteTornillos=Float.parseFloat(db.daoData().getOneDataValue("cantporpacktornillo").get(0).toString());
            //obtener precio silicona
            float precioPaqueteSilicona=Float.parseFloat(db.daoData().getOneDataValue("preciopacksilicona").get(0).toString());
            float cantPaqueteSilicona=Float.parseFloat(db.daoData().getOneDataValue("cantporpacksilicona").get(0).toString());
            //Calcular el costo de cada agregado
            float costoUsadoFelpa=(precioRolloFelpa*mFelpa)/mRolloFelpa;
            float costoUsadoPestillos=(precioPaquetePestillos*2)/cantPaquetePestillos;
            float costoUsadoCaracoles=(precioPaqueteCaracol*1)/cantPaqueteCaracol;
            float costoUsadoRodamientos=(precioPaqueteRod*4)/cantPaqueteRod;
            float costoUsadoTornillos=(precioPaqueteTornillos*24)/cantPaqueteTornillos;
            float costoUsadoSilicona=(precioPaqueteSilicona)/cantPaqueteSilicona;
            float costoManoDeObra=5000;
            //Calcular el costo total de los adds
            float costoTotal=costoManoDeObra+costoUsadoFelpa+costoUsadoPestillos+costoUsadoCaracoles+costoUsadoRodamientos+costoUsadoTornillos+costoUsadoSilicona;
            return costoTotal;
        });


    }
}
