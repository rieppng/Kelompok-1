package com.mycompany.praktikum_5;

/**
 *
 * @author arief
 */

public class Tugas_Praktikum_5 {
    public static void main(String[] args){
        StrikeAircraft plane1 = new StrikeAircraft();
        StrikeAircraft plane2 = new StrikeAircraft("Me 262", "German", 1943);
        StrikeAircraft plane3 = new StrikeAircraft();
        
        plane1.startEngine("P51D Mustang", "USA", 1944);
        plane2.startEngine();
        plane3.startEngine();
    }
}
