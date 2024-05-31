import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Patents {
    String nation;
    int year;
    double patents;

    public String getNation() {
        return nation;
    }

    public int getYear() {
        return year;
    }

    public double getPatents() {
        return patents;
    }

    public String toString() {
        return nation + " : " + year + " : " + patents;
    }

    public Patents(String country, int currYear, double applications) {
        this.nation = country;
        this.year = currYear;
        this.patents = applications;
    }

    public static ArrayList<Patents> loadData(String fileName) {
        ArrayList<Patents> answer = new ArrayList<>();
        try (FileReader fr = new FileReader(fileName);
                @SuppressWarnings("deprecation")
                CSVParser csvParser = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .parse(fr)) {

            for (CSVRecord record : csvParser) {
                String name = record.get("Country");
                int year = Integer.parseInt(record.get("Year"));
                double applications = Double.parseDouble(record.get("Patent Applicants per Million"));
                Patents p = new Patents(name, year, applications);
                answer.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static void main(String[] args) {
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/patent_data.csv";
        ArrayList<Patents> answer = Patents.loadData(fileName);
        for (Patents c : answer) {
            System.out.println(c);
        }
    }
}
