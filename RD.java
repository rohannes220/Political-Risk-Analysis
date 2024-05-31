import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class RD {

    String country;
    int year;
    double value;

    public String getCountry() {
        return country;
    }

    public int getYear() {
        return year;
    }

    public double getSpending() {
        return value;
    }

    public String toString() {
        return country + " : " + year + " : " + value;
    }

    public RD(String nation, int currYear, double spending) {
        this.country = nation;
        this.year = currYear;
        this.value = spending;
    }

    public static ArrayList<RD> loadCountryData(String fileName) {
        ArrayList<RD> answer = new ArrayList<>();
        try (FileReader fr = new FileReader(fileName);
                @SuppressWarnings("deprecation")
                CSVParser csvParser = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .parse(fr)) {

            for (CSVRecord record : csvParser) {
                String name = record.get("Country");
                int year = Integer.parseInt(record.get("Time"));
                double percentage = Double.parseDouble(record.get("Value"));
                RD r = new RD(name, year, percentage);
                answer.add(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static void main(String[] args) {
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Final_Sorted_R&DSpending.csv";
        ArrayList<RD> entries = loadCountryData(fileName);
        for (RD entry : entries) {
            System.out.println(entry);
        }
    }

}