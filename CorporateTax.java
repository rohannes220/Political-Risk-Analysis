import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CorporateTax {
    String nation;
    int year;
    double rate;

    public String getNation() {
        return nation;
    }

    public int getYear() {
        return year;
    }

    public double getNumbers() {
        return rate;
    }

    public String toString() {
        return nation + " : " + year + " : " + rate;
    }

    public CorporateTax(String country, int currYear, double tax) {
        this.nation = country;
        this.year = currYear;
        this.rate = tax;
    }

    public static ArrayList<CorporateTax> loadData(String fileName) {
        ArrayList<CorporateTax> answer = new ArrayList<>();
        try (FileReader fr = new FileReader(fileName);
                @SuppressWarnings("deprecation")
                CSVParser csvParser = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .parse(fr)) {

            for (CSVRecord record : csvParser) {
                String name = record.get("country");
                int year = Integer.parseInt(record.get("year"));
                double percentage = Double.parseDouble(record.get("tax_rate"));
                CorporateTax c = new CorporateTax(name, year, percentage);
                answer.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static void main(String[] args) {
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Cleaned_Corporate_Tax_Rates_2022.csv";
        ArrayList<CorporateTax> answer = CorporateTax.loadData(fileName);
        for (CorporateTax c : answer) {
            System.out.println(c);
        }
    }

}
