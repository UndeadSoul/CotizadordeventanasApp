package com.example.betaaplication;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Crystal {
    public Future<Float> getCrystalCost(float width, float height, String type, Context context){ //las medidas son recibidas en cm
        return Executors.newSingleThreadExecutor().submit(()->{
            AppDatabase db = AppDatabase.getInstance(context);
            float widthm = width/100;
            float heightm = height/100;
            //Obtener medida de los vidrios
            float crysWidth = (widthm/2)-0.054f;
            float crysHeight = heightm-0.107f;
            float crysM2 = crysWidth*crysHeight*2;
            //obtener cantidad de burlete
            float mBurlete = (widthm*2)+(widthm*4);

            //obtener tamaño plancha y el precio de la plancha en base al tipo
            float planchaSize = Float.parseFloat(db.daoData().getOneDataValue("m2plancha").get(0).toString());
            float precioPlancha = 0;
            float precioBurlete = 0;
            float mrolloburlete = 0;
            switch (type){
                case "Incoloro (4mm)":
                    precioPlancha=Float.parseFloat(db.daoData().getOneDataValue("precioplanchainc4").get(0).toString());
                    precioBurlete=Float.parseFloat(db.daoData().getOneDataValue("preciorolloburlete302").get(0).toString());
                    mrolloburlete=Float.parseFloat(db.daoData().getOneDataValue("mrolloburlete302").get(0).toString());
                    break;
                case "Incoloro (5mm)":
                    precioPlancha=Float.parseFloat(db.daoData().getOneDataValue("precioplanchainc5").get(0).toString());
                    precioBurlete=Float.parseFloat(db.daoData().getOneDataValue("preciorolloburlete506").get(0).toString());
                    mrolloburlete=Float.parseFloat(db.daoData().getOneDataValue("mrolloburlete506").get(0).toString());
                    break;
                case "Bronce (4mm)":
                    precioPlancha=Float.parseFloat(db.daoData().getOneDataValue("precioplanchabronce4").get(0).toString());
                    precioBurlete=Float.parseFloat(db.daoData().getOneDataValue("preciorolloburlete302").get(0).toString());
                    mrolloburlete=Float.parseFloat(db.daoData().getOneDataValue("mrolloburlete302").get(0).toString());
                    break;
                case "Bronce (5mm)":
                    precioPlancha=Float.parseFloat(db.daoData().getOneDataValue("precioplanchabronce5").get(0).toString());
                    precioBurlete=Float.parseFloat(db.daoData().getOneDataValue("preciorolloburlete506").get(0).toString());
                    mrolloburlete=Float.parseFloat(db.daoData().getOneDataValue("mrolloburlete506").get(0).toString());
                    break;
                case "Solarcool (4mm)":
                    precioPlancha=Float.parseFloat(db.daoData().getOneDataValue("precioplanchasolar4").get(0).toString());
                    precioBurlete=Float.parseFloat(db.daoData().getOneDataValue("preciorolloburlete302").get(0).toString());
                    mrolloburlete=Float.parseFloat(db.daoData().getOneDataValue("mrolloburlete302").get(0).toString());
                    break;
                case "Solarcool (5mm)":
                    precioPlancha=Float.parseFloat(db.daoData().getOneDataValue("precioplanchasolar5").get(0).toString());
                    precioBurlete=Float.parseFloat(db.daoData().getOneDataValue("preciorolloburlete506").get(0).toString());
                    mrolloburlete=Float.parseFloat(db.daoData().getOneDataValue("mrolloburlete506").get(0).toString());
                    break;
            }
            //obtener el costo de los vidrios
            float costoUsadoVidrio=crysM2*(precioPlancha/planchaSize);
            float costoUsadoBurlete=mBurlete*(precioBurlete/mrolloburlete);
            //calcular el costo total de los vidrios
            float costoTotal=costoUsadoVidrio*1.20f;
            costoTotal=costoTotal+costoUsadoBurlete;
            return costoTotal;

        });
    }
}
