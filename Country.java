import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Country {
    String continent;
    String country;
    String twoLetterCode;
    String threeLetterCode;
    String designation;

    public String getCountry() {
        return country;
    }

    public String getTwoDigitCode() {
        return twoLetterCode;
    }

    public String getThreeDigitCode() {
        return threeLetterCode;
    }

    public String getContinent() {
        return continent;
    }

    public String getDesignation() {
        return designation;
    }

    public Country(String continent, String country, String twoLetterCode, String threeLetterCode,
            String classification) {
        this.continent = continent;
        this.country = country;
        this.twoLetterCode = twoLetterCode;
        this.threeLetterCode = threeLetterCode;
        this.designation = classification;
    }

    public String toString() {
        return country + " : " + continent + " : " + twoLetterCode + " : " + threeLetterCode + " : " + designation;
    }

    public static ArrayList<Country> loadCountryData(String fileName) {
        ArrayList<Country> answer = new ArrayList<>();
        try (FileReader fr = new FileReader(fileName);
                @SuppressWarnings("deprecation")
                CSVParser csvParser = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .parse(fr)) {

            for (CSVRecord record : csvParser) {
                String continent = record.get("Continent");
                String name = record.get("Country");
                String code2 = record.get("cca2");
                String code3 = record.get("cca3");
                String classification = record.get("Classification");
                Country c = new Country(name, continent, code2, code3, classification);
                answer.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static ArrayList<String> allCountries(String continent) {
        continent = continent.toLowerCase().trim();
        ArrayList<String> answer = new ArrayList<>();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/country-codes.csv";
        ArrayList<Country> allCountries = loadCountryData(fileName);
        for (Country c : allCountries) {
            String currentContinent = c.getCountry().toLowerCase();
            String country = c.getContinent().toLowerCase();
            if (currentContinent.contains(continent)) {
                answer.add(country);
            }
        }
        return answer;
    }

    public static String getClassification(String country) {
        country = country.toLowerCase().trim();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/country-codes.csv";
        ArrayList<Country> allCountries = loadCountryData(fileName);
        for (Country c : allCountries) {
            String currCountry = c.getContinent().toLowerCase();
            if (currCountry.equals(country)) {
                return c.getDesignation();
            }
        }
        return null;
    }

    public static void main(String[] args) {
      ArrayList<String> answer = allCountries("Asia"); 
      for(String c: answer){
        System.out.println(c);
      }
    }
}
