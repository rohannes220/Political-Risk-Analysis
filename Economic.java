import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class Economic {

    String country;
    int startYear;
    int endYear;
    String aspect;

    public Economic(String nation, int start, int finish, String specifics) {
        this.country = nation;
        this.startYear = start;
        this.endYear = finish;
        this.aspect = specifics;
    }

    private static String findCode(String inputCountry) {
        inputCountry = inputCountry.toLowerCase();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/country-codes.csv";
        ArrayList<Country> countries = Country.loadCountryData(fileName);
        for (Country country : countries) {
            String currentCountry = country.getContinent().toLowerCase().trim();
            String currentTwoDigit = country.getTwoDigitCode().toLowerCase().trim();
            String currentThreeDigit = country.getThreeDigitCode().toLowerCase().trim();
            if (currentCountry.equals(inputCountry)) {
                String answer = country.getTwoDigitCode();
                return answer;
            }
            if (currentTwoDigit.equals(inputCountry)) {
                String answer = country.getTwoDigitCode();
                return answer;
            }
            if (currentThreeDigit.equals(inputCountry)) {
                String answer = country.getTwoDigitCode();
                return answer;
            }
        }
        return "Not found";
    }

    public static TreeMap<String, String> gdpData(String country) {
        int startYear = 2012;
        int endYear = 2022;
        String code = findCode(country);
        TreeMap<String, String> answer = new TreeMap<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            String baseUrl = "http://api.worldbank.org/v2/country/" + code + "/indicator/NY.GDP.MKTP.CD?date="
                    + startYear + ":" + endYear + "&format=json";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(response.body());

            if (jsonArray.length() > 1 && jsonArray.get(1) instanceof JSONArray) {
                JSONArray dataArray = jsonArray.getJSONArray(1);
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    String year = dataObject.getString("date");
                    double gdpDouble = dataObject.optDouble("value", 0);
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    String gdpFormatted = numberFormat.format(Math.round(gdpDouble));
                    answer.put(year, gdpFormatted);
                }
            } else {
                System.out.println("");
            }
        } catch (Exception e) {
        }
        return answer;
    }

    public static String countryGDPReport(String country) {
        TreeMap<String, String> answer = gdpData(country);
        double growth = 0;
        String previousYear = null;
        double prevValue = 0;
        int yearsCounted = 0;

        for (Entry<String, String> entry : answer.entrySet()) {
            if (previousYear == null) {
                previousYear = entry.getValue();
                prevValue = Double.parseDouble(previousYear.replaceAll(",", ""));
                continue;
            }

            String currentYear = entry.getValue();
            double currValue = Double.parseDouble(currentYear.replaceAll(",", ""));

            double annualGrowth = ((currValue - prevValue) / prevValue) * 100;
            growth += annualGrowth;
            prevValue = currValue;
            yearsCounted++;
        }

        double averageGrowth = growth / yearsCounted;
        String designation = Country.getClassification(country);
        if (designation != null && designation.equals("Lower Income")) {
            if (averageGrowth > 5) {
                return "Good";
            } else if (averageGrowth >= 3 && averageGrowth <= 5) {
                return "Fair";
            } else {
                return "Poor";
            }
        } else {
            if (averageGrowth > 3) {
                return "Good";
            } else if (averageGrowth >= 1 && averageGrowth <= 3) {
                return "Fair";
            } else {
                return "Poor";
            }
        }

    }

    public static HashMap<String, String> continentGDPReports(String continent) {
        ArrayList<String> countries = Country.allCountries(continent);
        HashMap<String, String> reports = new HashMap<>();
        for (String country : countries) {
            String report = countryGDPReport(country);
            reports.put(country, report);
        }
        return reports;
    }

    public static TreeMap<String, String> laborData(String country, int startYear, int endYear) {
        String code = findCode(country); //
        TreeMap<String, String> answer = new TreeMap<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            String baseUrl = "http://api.worldbank.org/v2/country/" + code + "/indicator/SL.TLF.TOTL.IN?date="
                    + startYear + ":" + endYear + "&format=json";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(response.body());
            if (jsonArray.length() > 1) {
                JSONArray dataArray = jsonArray.getJSONArray(1);
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    String year = dataObject.getString("date");
                    double laborForce = dataObject.optDouble("value", 0);
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    String laborForceFormatted = numberFormat.format(Math.round(laborForce));
                    answer.put(year, laborForceFormatted);
                }
            } else {
                System.out.println("");
            }
        } catch (Exception e) {
        }
        return answer;
    }

    public static String countryLaborReport(String country) {
        TreeMap<String, String> answer = laborData(country, 2012, 2022);
        double growth = 0;
        String currentYear = null;
        String previousYear = null;
        for (Entry<String, String> entry : answer.entrySet()) {
            String year = entry.getKey();
            if (year.equals("2012")) {
                previousYear = entry.getValue();
                continue;
            }
            if (currentYear == null) {
                currentYear = entry.getValue();
            } else {
                previousYear = currentYear;
                currentYear = entry.getValue();
                double prevValue = Double.parseDouble(previousYear.replaceAll(",", ""));
                double currValue = Double.parseDouble(currentYear.replaceAll(",", ""));
                growth += ((currValue - prevValue) / prevValue) * 100;
            }
        }
        double averageGrowth = growth / (answer.size() - 1);
        String designation = Country.getClassification(country);
        if (designation != null && designation.equals("Lower Income")) {
            if (averageGrowth > 3) {
                return "Good";
            } else if (averageGrowth >= 1.5 && averageGrowth < 3) {
                return "Fair";
            } else {
                return "Poor";
            }
        } else {
            if (averageGrowth > 2) {
                return "Good";
            } else if (averageGrowth > 0.5 && averageGrowth < 2) {
                return "Fair";
            } else {
                return "Poor";
            }
        }
    }

    public static HashMap<String, String> continentLaborReports(String continent) {
        ArrayList<String> countries = Country.allCountries(continent);
        HashMap<String, String> reports = new HashMap<>();
        for (String country : countries) {
            String report = countryLaborReport(country);
            reports.put(country, report);
        }

        return reports;

    }

    public static TreeMap<Integer, Double> rdSpending(String country) {
        country = country.toLowerCase().trim();
        TreeMap<Integer, Double> answer = new TreeMap<>();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Final_Sorted_R&DSpending.csv";
        ArrayList<RD> entries = RD.loadCountryData(fileName);
        for (RD entry : entries) {
            String currentCountry = entry.getCountry().toLowerCase().trim();
            if (currentCountry.equals(country)) {
                int currYear = entry.getYear();
                double value = entry.getSpending();
                answer.put(currYear, value);
            }
        }
        return answer;
    }

    public static String rdCountryReport(String country) {
        country = country.toLowerCase().trim();
        String designation = Country.getClassification(country);
        System.out.println(designation);
        TreeMap<Integer, Double> answer = rdSpending(country);
        int yes = 0;
        int no = 0;
        for (Entry<Integer, Double> entry : answer.entrySet()) {
            Double currentValue = entry.getValue();
            System.out.println(currentValue);
            if (designation != null && designation.equals("High Income")) {
                if (currentValue > 2) {
                    yes += 1;
                } else if (currentValue > 1 && currentValue <= 2) {
                    yes += 1;
                } else {
                    no += 1;
                }
            } else {
                if (currentValue > 0.5) {
                    yes += 1;
                } else if (currentValue > 0.2 && currentValue <= 0.5) {
                    yes += 1;
                } else {
                    no += 1;
                }
            }
        }
        if (yes > no) {
            return "Good";
        }
        if (yes < no) {
            return "Poor";
        } else {
            return "Fair";
        }

    }

    public static HashMap<String, String> continentRDReports(String continent) { // fck
        ArrayList<String> countries = Country.allCountries(continent);
        HashMap<String, String> reports = new HashMap<>();
        for (String country : countries) {
            String report = countryResearchersReport(country);
            reports.put(continent, report);
        }
        return reports;
    }

    public static TreeMap<Integer, Double> researchers(String country) {
        country = country.toLowerCase().trim();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Final_Modified_Researchers.csv";
        TreeMap<Integer, Double> answer = new TreeMap<>();
        ArrayList<Researchers> stats = Researchers.loadData(fileName);
        for (Researchers researcher : stats) {
            String currCountry = researcher.getNation().toLowerCase().trim();
            int year = researcher.getYear();
            double value = researcher.getNumbers();
            if (currCountry.contains(country)) {
                answer.put(year, value);
            }
        }
        return answer;
    }

    public static String countryResearchersReport(String country) {
        country = country.toLowerCase().trim();
        TreeMap<Integer, Double> answer = researchers(country);
        int yes = 0;
        int no = 0;
        for (Entry<Integer, Double> entry : answer.entrySet()) {
            Double currentValue = entry.getValue();
            String designation = Country.getClassification(country);
            if (designation != null && designation.equals("High Income Country")) {
                if (currentValue > 2000) {
                    yes += 1;
                } else if (currentValue == 2000) {
                    yes += 1;
                } else {
                    no += 1;
                }
            }
        }
        if (yes > no) {
            return "Good";
        }
        if (yes < no) {
            return "Poor";
        } else {
            return "Fair";
        }

    }

    public static HashMap<String, String> continentResearcherReports(String continent) {
        ArrayList<String> countries = Country.allCountries(continent);
        HashMap<String, String> reports = new HashMap<>();
        for (String country : countries) {
            String report = countryResearchersReport(country);
            reports.put(country, report);
        }

        return reports;

    }

    public static String countryTaxReport(String country) {
        country = country.toLowerCase().trim();
        if (country.equals("vatican city")) {
            return "Good";
        }
        if (country.equals("north korea")) {
            return "Poor";
        }
        if (country.equals("united arab emirates")) {
            return "Good";
        }
        if (country.equals("bahrain")) {
            return "Poor";
        }
        String result = null;
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Final_Cleaned_Corporate_Tax_Rates_2022.csv";
        ArrayList<CorporateTax> taxes = CorporateTax.loadData(fileName);
        for (CorporateTax t : taxes) {
            String currentCountry = t.getNation().toLowerCase().trim();
            if (currentCountry.equals(country)) {
                double taxRate = t.getNumbers();
                if (taxRate > 20 && taxRate <= 25) {
                    result = "Good";
                }
                if (taxRate > 25 && taxRate < 30) {
                    result = "Fair";
                } else {
                    result = "Poor";
                }
            }
        }
        return result;
    }

    public static HashMap<String, String> continentTaxReports(String continent) {
        ArrayList<String> countries = Country.allCountries(continent);
        HashMap<String, String> reports = new HashMap<>();
        for (String country : countries) {
            String report = countryTaxReport(country);
            reports.put(country, report);
        }
        return reports;
    }

    public static String countryPatentReport(String country) {
        country = country.toLowerCase().trim();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/patent_data.csv";
        ArrayList<Patents> applications = Patents.loadData(fileName);
        String result = null;
        for (Patents application : applications) {
            String currNation = application.getNation().toLowerCase().trim();
            if (currNation.equals(country)) {
                String designation = Country.getClassification(country);
                double patents = application.getPatents();
                if (designation != null && designation.equals("Upper Income")) {
                    if (patents > 500) {
                        result = "Good";
                    } else if (patents >= 100) {
                        result = "Fair";
                    } else {
                        result = "Poor";
                    }
                } else {
                    if (patents > 50) {
                        result = "Good";
                    } else if (patents > 10) {
                        result = "Fair";
                    } else {
                        result = "Poor";
                    }
                }
            }
        }

        return result;
    }

    public static HashMap<String, Double> patents(String country) {
        country = country.toLowerCase().trim();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/patent_data.csv";
        HashMap<String, Double> answer = new HashMap<>();
        ArrayList<Researchers> stats = Researchers.loadData(fileName);
        for (Researchers researcher : stats) {
            String currCountry = researcher.getNation().toLowerCase().trim();
            double value = researcher.getNumbers();
            if (currCountry.contains(country)) {
                answer.put(currCountry, value);
            }
        }
        return answer;
    }

    public static HashMap<String, String> continentPatentReports(String continent) {
        ArrayList<String> countries = Country.allCountries(continent);
        HashMap<String, String> reports = new HashMap<>();
        for (String country : countries) {
            String report = countryPatentReport(country);
            reports.put(country, report);
        }
        return reports;
    }

    private static String conditionCounter(HashMap<String, String> allData) {
        int good = 0;
        int fair = 0;
        int poor = 0;

        for (Map.Entry<String, String> entry : allData.entrySet()) {
            String value = entry.getValue();
            if (value == null) {
                fair++;
            } else if (value.equals("Good")) {
                good++;
            } else if (value.equals("Fair")) {
                fair++;
            } else if (value.equals("Poor")) {
                poor++;
            } else {
                fair++;
            }
        }
        if (good > fair && good > poor) {
            return "Good";
        } else if (poor > good && poor > fair) {
            return "Poor";
        } else {
            return "Fair";
        }
    }

    public static HashMap<String, String> generateAllEconomicReports(String continent) {
        HashMap<String, String> map = new HashMap<>();
        String gdpReport = conditionCounter(continentGDPReports(continent));
        String rdReport = conditionCounter(continentRDReports(continent));
        String laborReport = conditionCounter(continentLaborReports(continent));
        String researchReport = conditionCounter(continentResearcherReports(continent));
        String taxReport = conditionCounter(continentTaxReports(continent));
        map.put("GDP Report :", gdpReport);
        map.put("RD Report:", rdReport);
        map.put("Labor Report:", laborReport);
        map.put("Research Report:", researchReport);
        map.put("Tax Report:", taxReport);
        return map;
    }

    public static ArrayList<String> whichEconomyIsTheBest(String continent,
            HashMap<String, String> gdpMap,
            HashMap<String, String> researchMap,
            HashMap<String, String> laborMap,
            HashMap<String, String> taxMap,
            HashMap<String, String> rdMap) {

        HashMap<String, Integer> scores = new HashMap<>();

        updateScoresEconomic(scores, gdpMap);
        updateScoresEconomic(scores, researchMap);
        updateScoresEconomic(scores, laborMap);
        updateScoresEconomic(scores, taxMap);
        updateScoresEconomic(scores, rdMap);

        int maxGoodCount = 0;
        for (int count : scores.values()) {
            if (count > maxGoodCount) {
                maxGoodCount = count;
            }
        }

        ArrayList<String> bestCountries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            if (entry.getValue() == maxGoodCount) {
                bestCountries.add(entry.getKey());
            }
        }

        return bestCountries;
    }

    private static void updateScoresEconomic(HashMap<String, Integer> scores, HashMap<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String country = entry.getKey();
            String classification = entry.getValue();
            if (classification != null && classification.equals("Good")) {
                scores.put(country, scores.getOrDefault(country, 0) + 1);
            }
        }
    }

    public static ArrayList<String> compareEveryEconomy() {
        String[] continents = { "North America", "South America", "Europe", "Asia", "Africa", "Oceania" };
        ArrayList<String> finaList = new ArrayList<>();
        for (String continent : continents) {
            HashMap<String, String> gdpMap = continentGDPReports(continent);
            HashMap<String, String> rdMap = continentRDReports(continent);
            HashMap<String, String> laborMap = continentLaborReports(continent);
            HashMap<String, String> researchMap = continentResearcherReports(continent);
            HashMap<String, String> taxMap = continentTaxReports(continent);
            ArrayList<String> bestCountries = whichEconomyIsTheBest(continent,
                    gdpMap, rdMap,
                    laborMap,
                    researchMap, taxMap);
            for (String country : bestCountries) {
                finaList.add(country);
            }
        }
        return finaList;
    }

    public static void main(String[] args) {
        String continent = "Europe";
        HashMap<String, String> gdpMap = continentGDPReports(continent);
        HashMap<String, String> rdMap = continentRDReports(continent);
        HashMap<String, String> laborMap = continentLaborReports(continent);
        HashMap<String, String> researchMap = continentResearcherReports(continent);
        HashMap<String, String> taxMap = continentTaxReports(continent);
        ArrayList<String> answer = whichEconomyIsTheBest(continent, gdpMap, researchMap, laborMap, taxMap, rdMap);
        System.out.println(answer);
    }

}
