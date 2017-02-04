package henrywei.henrywei_sizebook;

/**
 * Record class is a class to hold a person's dimensions.
 *
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
        updateRecord(name, date, neck, bust, chest, waist, hip, inseam, comment);
    }

    /** Used to instantiate a record and edit a record's parameter
     * @param name - person's name
     * @param date - last date that the record was valid
     * @param neck - person's neck dimension
     * @param bust - person's bust dimension
     * @param chest - person's chest dimension
     * @param waist - person's waist dimension
     * @param hip - person's hip dimension
     * @param inseam - person's inseam dimension
     * @param comment - textual comments
     */
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

    /**
     * Used for the listView
     * @return Printable string
     */
    @Override
    public String toString(){
        return name + ": " + checkIfZero(bust) + "-" + checkIfZero(chest) + "-"+ checkIfZero(waist) + "-" + checkIfZero(inseam);
    }

    /**
     * Used in the function toString to check and turn missing information (set as 0 in records) into a 0 length string
     * If value is not zero, display in toString.
     * @param value - number to be checked if it's a zero
     * @return Resulting string
     */
    private String checkIfZero(double value){
        if(value == 0.0){
            return "";
        }
        return Double.toString(value);
    }
}
