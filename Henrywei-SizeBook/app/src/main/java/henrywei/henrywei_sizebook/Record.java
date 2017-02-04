package henrywei.henrywei_sizebook;

/**
 * Created by henry on 1/31/2017.
 */

public class Record {
    String name;
    String date;
    Double neck;
    Double bust;
    Double chest;
    Double waist;
    Double hip;
    Double inseam;
    String comment;

    public Record(String name, String date, Double neck, Double bust, Double chest, Double waist, Double hip, Double inseam, String comment){
        //enter 0 into the optional fields if unknown, comment = ""
        this.name = name;
        this.date = date;
        this.neck = neck;
        this.bust = bust;
        this.chest = chest;
        this.waist = waist;
        this.hip = hip;
        this.inseam = inseam;
        this.comment = comment;
    }

    public void updateRecord(String name, String date, Double neck, Double bust, Double chest, Double waist, Double hip, Double inseam, String comment){
        this.name = name;
        this.date = date;
        this.neck = neck;
        this.bust = bust;
        this.chest = chest;
        this.waist = waist;
        this.hip = hip;
        this.inseam = inseam;
        this.comment = comment;
    }

    @Override
    public String toString(){
        String bustString, chestString, waistString, inseamString;
        if(bust == 0.0){
            bustString = "";
        }else{
            bustString = bust.toString();
        }
        if(chest == 0.0){
            chestString = "";
        }else{
            chestString = chest.toString();
        }
        if(waist == 0.0){
            waistString = "";
        }else{
            waistString = waist.toString();
        }
        if(bust == 0.0){
            inseamString = "";
        }else{
            inseamString = inseam.toString();
        }
        return name + ": " + bustString + "-" + chestString + "-"+ waistString + "-" + inseamString;
    }
}
