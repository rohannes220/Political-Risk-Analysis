import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Researchers {
    String nation;
    int year;
    double value;

    public String getNation() {
        return nation;
    }

    public int getYear() {
        return year;
    }

    public double getNumbers() {
        return value;
    }

    public String toString() {
        return nation + " : " + year + " : " + value;
    }

    public Researchers(String country, int currYear, double spending) {
        this.nation = country;
        this.year = currYear;
        this.value = spending;
    }

    public static ArrayList<Researchers> loadData(String fileName) {
        ArrayList<Researchers> answer = new ArrayList<>();
        try (FileReader fr = new FileReader(fileName);
                @SuppressWarnings("deprecation")
                CSVParser csvParser = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .parse(fr)) {

            for (CSVRecord record : csvParser) {
                String name = record.get("Country");
                int year = Integer.parseInt(record.get("TIME"));
                double percentage = Double.parseDouble(record.get("Value"));
                Researchers r = new Researchers(name, year, percentage);
                answer.add(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static void main(String[] args) {
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Researchers.csv";
        ArrayList<Researchers> answer = Researchers.loadData(fileName);
        for (Researchers c : answer) {
            System.out.println(c);
        }
    }
}
