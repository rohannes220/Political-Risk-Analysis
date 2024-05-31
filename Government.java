import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Government {

    private static String findCountryCode(String inputCountry) {
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

    public static String governmentEffectivenessReport(String country) {
        country = country.toLowerCase().trim();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Updated_Effectiveness_Final.csv";
        ArrayList<GovernmentEffectiveness> answer = GovernmentEffectiveness.loadCountryData(fileName);
        for (GovernmentEffectiveness g : answer) {
            String currentCountry = g.getCountry().toLowerCase().trim();
            if (currentCountry.equals(country)) {
                double rank = g.getScore();
                String designation = g.getDesignation();
                if (designation.equals("Upper Income")) {
                    if (rank >= 75) {
                        return "Good";
                    }
                    if (rank >= 50 && rank < 75) {
                        return "Fair";
                    } else {
                        return "Poor";
                    }
                } else {
                    if (rank >= 50) {
                        return "Good";
                    }
                    if (rank >= 25 && rank < 50) {
                        return "Fair";
                    } else {
                        return "Poor";
                    }
                }

            }
        }
        return "Nothing";
    }

    public static HashMap<String, String> continentGovernmentReports(String continent) {
        ArrayList<String> countries = Country.allCountries(continent);
        HashMap<String, String> reports = new HashMap<>();
        for (String country : countries) {
            String report = governmentEffectivenessReport(country);
            reports.put(country, report);
        }
        return reports;
    }

    public static String corruptionReport(String country) {
        country = country.toLowerCase().trim();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Updated_Corruption_Final.csv";
        ArrayList<Corruption> answer = Corruption.loadCountryData(fileName);
        for (Corruption c : answer) {
            String currentCountry = c.getCountry().toLowerCase().trim();
            if (currentCountry.equals(country)) {
                double rank = c.getScore();
                String designation = c.getDesignation();
                if (designation.equals("Upper Income")) {
                    if (rank > 70) {
                        return "Good";
                    }
                    if (rank >= 50 && rank <= 70) {
                        return "Fair";
                    } else {
                        return "Poor";
                    }
                } else {
                    if (rank > 50) {
                        return "Good";
                    }
                    if (rank >= 30 && rank <= 50) {
                        return "Fair";
                    } else {
                        return "Poor";
                    }
                }

            }
        }
        return "Nothing";
    }

    public static HashMap<String, String> continentCorruptionReports(String continent) {
        ArrayList<String> countries = Country.allCountries(continent);
        HashMap<String, String> reports = new HashMap<>();
        for (String country : countries) {
            String report = corruptionReport(country);
            reports.put(country, report);
        }
        return reports;
    }

    public static String politicalStabilityReport(String country) {
        country = country.toLowerCase().trim();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Updated_PoliticalStability_Final.csv";
        ArrayList<PoliticalStability> answer = PoliticalStability.loadCountryData(fileName);
        for (PoliticalStability p : answer) {
            String currentCountry = p.getCountry().toLowerCase().trim();
            if (currentCountry.equals(country)) {
                double rank = p.getScore();
                String designation = p.getDesignation();
                if (designation.equals("Upper Income")) {
                    if (rank > 1) {
                        return "Good";
                    }
                    if (rank >= 0 && rank < 1) {
                        return "Fair";
                    } else {
                        return "Poor";
                    }
                } else {
                    if (rank > 0.5) {
                        return "Good";
                    }
                    if (rank >= -1 && rank <= 0.5) {
                        return "Fair";
                    } else {
                        return "Poor";
                    }
                }

            }
        }
        return "Nothing";
    }

    public static HashMap<String, String> continentStabilityReports(String continent) {
        ArrayList<String> countries = Country.allCountries(continent);
        HashMap<String, String> reports = new HashMap<>();
        for (String country : countries) {
            String report = politicalStabilityReport(country);
            reports.put(country, report);
        }
        return reports;
    }

    public static String regulatoryReport(String country) {
        country = country.toLowerCase().trim();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Updated_Regulatory_Final.csv";
        ArrayList<Regulatory> answer = Regulatory.loadCountryData(fileName);
        for (Regulatory r : answer) {
            String currentCountry = r.getCountry().toLowerCase().trim();
            if (currentCountry.equals(country)) {
                double rank = r.getScore();
                String designation = r.getDesignation();
                if (designation.equals("Upper Income")) {
                    if (rank > 80) {
                        return "Good";
                    }
                    if (rank >= 50 && rank <= 80) {
                        return "Fair";
                    } else {
                        return "Poor";
                    }
                } else {
                    if (rank > 60) {
                        return "Good";
                    }
                    if (rank >= 30 && rank <= 60) {
                        return "Fair";
                    } else {
                        return "Poor";
                    }
                }

            }
        }
        return "Nothing";
    }

    public static HashMap<String, String> continentRegulatoryReports(String continent) {
        ArrayList<String> countries = Country.allCountries(continent);
        HashMap<String, String> reports = new HashMap<>();
        for (String country : countries) {
            String report = regulatoryReport(country);
            reports.put(country, report);
        }
        return reports;
    }

    public static String ruleOfLawReport(String country) {
        country = country.toLowerCase().trim();
        String fileName = "/Users/god/Desktop/Political Risk Tool/Data/Updated_RuleofLaw_Final.csv";
        ArrayList<RuleofLaw> answer = RuleofLaw.loadCountryData(fileName);
        for (RuleofLaw l : answer) {
            String currentCountry = l.getCountry().toLowerCase().trim();
            if (currentCountry.equals(country)) {
                double rank = l.getScore();
                String designation = l.getDesignation();
                if (designation.equals("Upper Income")) {
                    if (rank > 80) {
                        return "Good";
                    }
                    if (rank >= 50 && rank <= 80) {
                        return "Fair";
                    } else {
                        return "Poor";
                    }
                } else {
                    if (rank > 60) {
                        return "Good";
                    }
                    if (rank >= 30 && rank <= 60) {
                        return "Fair";
                    } else {
                        return "Poor";
                    }
                }

            }
        }
        return "Nothing";
    }

    public static HashMap<String, String> continentRuleofLawReports(String continent) {
        ArrayList<String> countries = Country.allCountries(continent);
        HashMap<String, String> reports = new HashMap<>();
        for (String country : countries) {
            String report = ruleOfLawReport(country);
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

    public static HashMap<String, String> generateAllGovernmentReports(String continent) {
        HashMap<String, String> map = new HashMap<>();
        String corruptionReport = conditionCounter(continentCorruptionReports(continent));
        String regulatoryReport = conditionCounter(continentRegulatoryReports(continent));
        String safetyReport = conditionCounter(continentStabilityReports(continent));
        String ruleOfLawReport = conditionCounter(continentRuleofLawReports(continent));
        String effectivenessReport = conditionCounter(continentGovernmentReports(continent));
        map.put("Corruption Report :", corruptionReport);
        map.put("Regulatory Report:", regulatoryReport);
        map.put("Safety Report:", safetyReport);
        map.put("Rule of Law Report:", ruleOfLawReport);
        map.put("Effectivess Report:", effectivenessReport);
        return map;
    }

    public static ArrayList<String> whichGovernmentIsTheBest(String continent,
            HashMap<String, String> corruptionMap,
            HashMap<String, String> regulatoryMap,
            HashMap<String, String> stabilityMap,
            HashMap<String, String> ruleOfLawMap,
            HashMap<String, String> governmentMap) {

        HashMap<String, Integer> scores = new HashMap<>();

        updateScoresGovernment(scores, corruptionMap);
        updateScoresGovernment(scores, regulatoryMap);
        updateScoresGovernment(scores, stabilityMap);
        updateScoresGovernment(scores, ruleOfLawMap);
        updateScoresGovernment(scores, governmentMap);

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

    private static void updateScoresGovernment(HashMap<String, Integer> scores, HashMap<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String country = entry.getKey();
            String classification = entry.getValue();
            if (classification.equals("Good")) {
                scores.put(country, scores.getOrDefault(country, 0) + 1);
            }
        }
    }

    public static ArrayList<String> compareEverything() {
        String[] continents = { "North America", "South America", "Europe", "Asia", "Africa", "Oceania" };
        ArrayList<String> finaList = new ArrayList<>();
        for (String continent : continents) {
            HashMap<String, String> corruptionMap = continentCorruptionReports(continent);
            HashMap<String, String> regulatoryMap = continentRegulatoryReports(continent);
            HashMap<String, String> stabilityMap = continentStabilityReports(continent);
            HashMap<String, String> ruleOfLawMap = continentRuleofLawReports(continent);
            HashMap<String, String> governmentMap = continentGovernmentReports(continent);
            ArrayList<String> bestCountries = whichGovernmentIsTheBest(continent, corruptionMap, regulatoryMap,
                    stabilityMap,
                    ruleOfLawMap, governmentMap);
            for (String country : bestCountries) {
                finaList.add(country);
            }
        }
        return finaList;
    }

    public static void main(String[] args) {

        ArrayList<String> bestCountries = compareEverything();
        for (String c : bestCountries) {
            System.out.println(c);
        }
        ;
    }
}
