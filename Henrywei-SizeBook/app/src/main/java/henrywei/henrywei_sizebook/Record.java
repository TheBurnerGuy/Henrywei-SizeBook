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
        return name + ": " + bust + "-" + chest + " "+ waist + " " + inseam;
    }
}
