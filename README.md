
# Political Risk Tool

## Overview

The Political Risk Tool is a Java-based application designed to analyze and report on various factors affecting countries and continents. It leverages economic and political data to provide insights into different regions, helping users understand the relative performance and risk profiles of different countries based on key indicators.

## Features

- Evaluate countries based on GDP growth, research and development, labor force, corporate tax rates, and patent publications.
- Assess the corruption levels in different countries.
- Retrieve and compare corporate tax rates across different countries.
- Load and manage country data from CSV files.
- Generate reports for continents based on selected economic or political factors.

## Setup and Usage

### Prerequisites

- Java Development Kit (JDK) installed
- IDE or text editor for Java development

### Running the Application

1. Clone the repository to your local machine.
2. Open the project in your preferred IDE.
3. Ensure the CSV data files are available in the specified paths.
4. Compile and run the main class (`OpeningPage.java`) to start the application.

### Example Usage

To generate a report on the best economies in Europe:

1. Select "Europe" as the continent.
2. Choose "Economic" as the factor.
3. The application will display the best countries in Europe based on World Bank economic standards.

## Data Files

- **country-codes.csv**: Contains country names, codes, and classifications.
- Additional CSV files for specific analyses (e.g., GDP, R&D, labor force) should be placed in the appropriate directory.

## Contributing

Contributions to the Political Risk Tool are welcome. Please fork the repository and submit pull requests with your improvements.

## License

This project is licensed under the MIT License.

-
