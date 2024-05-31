import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class RuleofLaw {
    String country;
    String twoLetterCode;
    String designation;
    double score;

    public String getCountry() {
        return country;
    }

    public String getTwoDigitCode() {
        return twoLetterCode;
    }

    public String getDesignation() {
        return designation;
    }

    public double getScore() {
        return score;
    }

    public String toString() {
        return country + "  :   " + twoLetterCode + " : " + score + " : " + designation;
    }

    public RuleofLaw(String country, String twoLetterCode, double percentileRank,
            String classification) {
        this.country = country;
        this.twoLetterCode = twoLetterCode;
        this.score = percentileRank;
        this.designation = classification;
    }

    public static ArrayList<RuleofLaw> loadCountryData(String fileName) {
        ArrayList<RuleofLaw> answer = new ArrayList<>();
        try (FileReader fr = new FileReader(fileName);
                @SuppressWarnings("deprecation")
                CSVParser csvParser = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .parse(fr)) {

            for (CSVRecord record : csvParser) {
                String name = record.get("Country");
                String code2 = record.get("Country Code");
                double percentile = Double.parseDouble(record.get("2022 [YR2022]"));
                String classification = record.get("Classification");
                RuleofLaw r = new RuleofLaw(name, code2, percentile, classification);
                answer.add(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static void main(String[] args) {
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Updated_RuleofLaw_Final.csv";
        ArrayList<RuleofLaw> answer = loadCountryData(fileName);
        for (RuleofLaw r : answer) {
            System.out.println(r);
        }
    }
}
