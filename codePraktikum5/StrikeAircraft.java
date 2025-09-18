package com.mycompany.praktikum_5;

/**
 *
 * @author arief
 */

public class StrikeAircraft {
    String type;
    String country;
    int year;
    
    StrikeAircraft(String type, String country, int year) {
        this.type = type;
        this.country = country;
        this.year = year;
    }
    
    StrikeAircraft() {
        this.type = "Tidak Diketahui";
        this.country = "Tidak Diketahui";
        this.year = 0;
    }
    
    public void startEngine(){
        System.out.println("Tipe : " + type);
        System.out.println("Negara : " + country);
        if(year == 0){
            System.out.println("Tahun peluncuran : Tidak diketahui");
        } else {
            System.out.println("Tahun peluncuran : " + year);
        }
        System.out.println("====================================================");
    }
    
    public void startEngine(String type, String country, int year){
        System.out.println("Tipe : " + type);
        System.out.println("Negara : " + country);
        System.out.println("Tahun peluncuran : " + year);
        System.out.println("====================================================");
    }
}
