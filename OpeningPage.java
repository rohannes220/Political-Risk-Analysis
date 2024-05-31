import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class OpeningPage {

        public static void main(String[] args) {
                SwingUtilities.invokeLater(OpeningPage::openingPage);
        }

        private static void openingPage() {
                JFrame frame = new JFrame("Political Risk Tool");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400); // Increased size for better layout

                JPanel mainPanel = new JPanel(new BorderLayout());
                mainPanel.setBackground(Color.ORANGE);
                frame.add(mainPanel);

                JPanel centerPanel = new JPanel();
                centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
                centerPanel.setBackground(Color.ORANGE);

                JLabel continentLabel = new JLabel("Continent:");
                continentLabel.setFont(new Font("Arial", Font.BOLD, 14));
                continentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JComboBox<String> continent = new JComboBox<>(new String[] {
                                "North America", "South America", "Asia", "Europe",
                                "All", "Oceania"
                });
                continent.setFont(new Font("Arial", Font.PLAIN, 14));
                continent.setAlignmentX(Component.CENTER_ALIGNMENT);
                continent.setMaximumSize(new Dimension(300, 25));

                JLabel factorsLabel = new JLabel("Risk Factors:");
                factorsLabel.setFont(new Font("Arial", Font.BOLD, 14));
                factorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JComboBox<String> factors = new JComboBox<>(new String[] {
                                "Economic", "Government", "Everything"
                });
                factors.setFont(new Font("Arial", Font.PLAIN, 14));
                factors.setAlignmentX(Component.CENTER_ALIGNMENT);
                factors.setMaximumSize(new Dimension(300, 25));

                JButton calculateButton = new JButton("Calculate");
                calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
                calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                JTextPane resultsArea = new JTextPane();
                resultsArea.setFont(new Font("Arial", Font.PLAIN, 20));
                resultsArea.setEditable(false);
                resultsArea.setBackground(Color.ORANGE);

                JScrollPane scrollPane = new JScrollPane(resultsArea);
                scrollPane.setPreferredSize(new Dimension(600, 200));
                scrollPane.setBackground(Color.ORANGE);
                scrollPane.setBorder(null);

                centerPanel.add(Box.createVerticalGlue());
                centerPanel.add(continentLabel);
                centerPanel.add(continent);
                centerPanel.add(factorsLabel);
                centerPanel.add(factors);
                centerPanel.add(calculateButton);
                centerPanel.add(Box.createVerticalGlue());

                mainPanel.add(centerPanel, BorderLayout.CENTER);
                mainPanel.add(scrollPane, BorderLayout.SOUTH);

                calculateButton.addActionListener(e -> {
                        String selectedContinent = (String) continent.getSelectedItem();
                        String selectedFactor = (String) factors.getSelectedItem();
                        resultsArea.setText(""); // Clear previous results

                        String resultText = "";

                        if (selectedContinent.equals("Asia") && selectedFactor.equals("Government")) {
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestCountries = Government.whichGovernmentIsTheBest(selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank government standards in corruption, rule of law, internal political stability, regulatory guidelines, and government efficacy are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("Asia") && selectedFactor.equals("Economic")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                ArrayList<String> bestCountries = Economic.whichEconomyIsTheBest(selectedContinent,
                                                gdpMap, rdMap,
                                                laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank economic standards in patent publication, researchers per million, corporate tax rates, research and development, and GDP growth are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("Asia") && selectedFactor.equals("Everything")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestGovernedCountries = Government.whichGovernmentIsTheBest(
                                                selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                ArrayList<String> bestEconomicCountries = Economic.whichEconomyIsTheBest(
                                                selectedContinent, gdpMap,
                                                rdMap, laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent + " (Government):<br>" + bestGovernedCountries
                                                + "<br>Best countries in " + selectedContinent + " (Economic):<br>"
                                                + bestEconomicCountries + "</div>";
                        }
                        if (selectedContinent.equals("Africa") && selectedFactor.equals("Government")) {
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestCountries = Government.whichGovernmentIsTheBest(selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank government standards in corruption, rule of law, internal political stability, regulatory guidelines, and government efficacy are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("Africa") && selectedFactor.equals("Economic")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                ArrayList<String> bestCountries = Economic.whichEconomyIsTheBest(selectedContinent,
                                                gdpMap, rdMap,
                                                laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank economic standards in patent publication, researchers per million, corporate tax rates, research and development, and GDP growth are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("Africa") && selectedFactor.equals("Everything")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestGovernedCountries = Government.whichGovernmentIsTheBest(
                                                selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                ArrayList<String> bestEconomicCountries = Economic.whichEconomyIsTheBest(
                                                selectedContinent, gdpMap,
                                                rdMap, laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent + " (Government):<br>" + bestGovernedCountries
                                                + "<br>Best countries in " + selectedContinent + " (Economic):<br>"
                                                + bestEconomicCountries + "</div>";
                        }
                        if (selectedContinent.equals("North America") && selectedFactor.equals("Government")) {
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestCountries = Government.whichGovernmentIsTheBest(selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank government standards in corruption, rule of law, internal political stability, regulatory guidelines, and government efficacy are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("North America") && selectedFactor.equals("Economic")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                ArrayList<String> bestCountries = Economic.whichEconomyIsTheBest(selectedContinent,
                                                gdpMap, rdMap,
                                                laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank economic standards in patent publication, researchers per million, corporate tax rates, research and development, and GDP growth are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("North America") && selectedFactor.equals("Everything")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestGovernedCountries = Government.whichGovernmentIsTheBest(
                                                selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                ArrayList<String> bestEconomicCountries = Economic.whichEconomyIsTheBest(
                                                selectedContinent, gdpMap,
                                                rdMap, laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent + " (Government):<br>" + bestGovernedCountries
                                                + "<br>Best countries in " + selectedContinent + " (Economic):<br>"
                                                + bestEconomicCountries + "</div>";
                        }
                        if (selectedContinent.equals("South America") && selectedFactor.equals("Government")) {
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestCountries = Government.whichGovernmentIsTheBest(selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank government standards in corruption, rule of law, internal political stability, regulatory guidelines, and government efficacy are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("South America") && selectedFactor.equals("Economic")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                ArrayList<String> bestCountries = Economic.whichEconomyIsTheBest(selectedContinent,
                                                gdpMap, rdMap,
                                                laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank economic standards in patent publication, researchers per million, corporate tax rates, research and development, and GDP growth are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("South America") && selectedFactor.equals("Everything")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestGovernedCountries = Government.whichGovernmentIsTheBest(
                                                selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                ArrayList<String> bestEconomicCountries = Economic.whichEconomyIsTheBest(
                                                selectedContinent, gdpMap,
                                                rdMap, laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent + " (Government):<br>" + bestGovernedCountries
                                                + "<br>Best countries in " + selectedContinent + " (Economic):<br>"
                                                + bestEconomicCountries + "</div>";
                        }
                        if (selectedContinent.equals("Europe") && selectedFactor.equals("Government")) {
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestCountries = Government.whichGovernmentIsTheBest(selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank government standards in corruption, rule of law, internal political stability, regulatory guidelines, and government efficacy are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("Europe") && selectedFactor.equals("Economic")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                ArrayList<String> bestCountries = Economic.whichEconomyIsTheBest(selectedContinent,
                                                gdpMap, rdMap,
                                                laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank economic standards in patent publication, researchers per million, corporate tax rates, research and development, and GDP growth are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("Europe") && selectedFactor.equals("Everything")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestGovernedCountries = Government.whichGovernmentIsTheBest(
                                                selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                ArrayList<String> bestEconomicCountries = Economic.whichEconomyIsTheBest(
                                                selectedContinent, gdpMap,
                                                rdMap, laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent + " (Government):<br>" + bestGovernedCountries
                                                + "<br>Best countries in " + selectedContinent + " (Economic):<br>"
                                                + bestEconomicCountries + "</div>";
                        }
                        if (selectedContinent.equals("Oceania") && selectedFactor.equals("Government")) {
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestCountries = Government.whichGovernmentIsTheBest(selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank government standards in corruption, rule of law, internal political stability, regulatory guidelines, and government efficacy are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("Oceania") && selectedFactor.equals("Economic")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                ArrayList<String> bestCountries = Economic.whichEconomyIsTheBest(selectedContinent,
                                                gdpMap, rdMap,
                                                laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent
                                                + " according to World Bank economic standards in patent publication, researchers per million, corporate tax rates, research and development, and GDP growth are:<br>"
                                                + bestCountries + "</div>";
                        }
                        if (selectedContinent.equals("Oceania") && selectedFactor.equals("Everything")) {
                                HashMap<String, String> gdpMap = Economic.continentGDPReports(selectedContinent);
                                HashMap<String, String> rdMap = Economic.continentRDReports(selectedContinent);
                                HashMap<String, String> laborMap = Economic.continentLaborReports(selectedContinent);
                                HashMap<String, String> researchMap = Economic
                                                .continentResearcherReports(selectedContinent);
                                HashMap<String, String> taxMap = Economic.continentTaxReports(selectedContinent);
                                HashMap<String, String> corruptionMap = Government
                                                .continentCorruptionReports(selectedContinent);
                                HashMap<String, String> regulatoryMap = Government
                                                .continentRegulatoryReports(selectedContinent);
                                HashMap<String, String> stabilityMap = Government
                                                .continentStabilityReports(selectedContinent);
                                HashMap<String, String> ruleOfLawMap = Government
                                                .continentRuleofLawReports(selectedContinent);
                                HashMap<String, String> governmentMap = Government
                                                .continentGovernmentReports(selectedContinent);
                                ArrayList<String> bestGovernedCountries = Government.whichGovernmentIsTheBest(
                                                selectedContinent,
                                                corruptionMap, regulatoryMap, stabilityMap, ruleOfLawMap,
                                                governmentMap);
                                ArrayList<String> bestEconomicCountries = Economic.whichEconomyIsTheBest(
                                                selectedContinent, gdpMap,
                                                rdMap, laborMap, researchMap, taxMap);
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries in "
                                                + selectedContinent + " (Government):<br>" + bestGovernedCountries
                                                + "<br>Best countries in " + selectedContinent + " (Economic):<br>"
                                                + bestEconomicCountries + "</div>";
                        }
                        if (selectedContinent.equals("All") && selectedFactor.equals("Government")) {
                                ArrayList<String> bestCountries = Government.compareEverything();
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best countries overall according to World Bank government standards in corruption, rule of law, internal political stability, regulatory guidelines, and government efficacy are:<br>";
                                for (String c : bestCountries) {
                                        resultText += c + "<br>";
                                }
                                resultText += "</div>";
                        }
                        if (selectedContinent.equals("All") && selectedFactor.equals("Economic")) {
                                ArrayList<String> bestEconomies = Economic.compareEveryEconomy();
                                resultText = "<div style='text-align: center; font-size: 20px;'>Best economies overall according to World Bank economic standards in patent publication, researchers per million, corporate tax rates, research and development, and GDP growth are:<br>";
                                for (String f : bestEconomies) {
                                        resultText += f + "<br>";
                                }
                                resultText += "</div>";
                        }

                        resultsArea.setContentType("text/html");
                        resultsArea.setText(resultText);
                });

                frame.setVisible(true);
        }
}
