package utilities;

import java.io.File;
import java.io.IOException;

public class SelfHealingReport {

    private static final String REPORT_DIRECTORY = "reports";
    private static final String REPORT_PREFIX = "self-healing-report";
    private static final String REPORT_EXTENSION = ".html";

    /**
     * Creates the reports directory if it does not already exist.
     */
    private static void createReportsDirectory() {
        File directory = new File(REPORT_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Finds the highest existing report number and returns the next number.
     */
    private static int getNextReportNumber() {
        createReportsDirectory();

        File directory = new File(REPORT_DIRECTORY);
        int highestNumber = 0;
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();

                if (fileName.startsWith(REPORT_PREFIX) && fileName.endsWith(REPORT_EXTENSION)) {
                    String numberPart = fileName.substring(
                            REPORT_PREFIX.length(),
                            fileName.length() - REPORT_EXTENSION.length()
                    );

                    try {
                        int number = Integer.parseInt(numberPart);
                        if (number > highestNumber) {
                            highestNumber = number;
                        }
                    } catch (NumberFormatException ignored) {
                        // Ignore files with invalid report numbers.
                    }
                }
            }
        }

        return highestNumber + 1;
    }

    /**
     * Creates and returns the path for the next self-healing report.
     */
    public static String createNewReportFile() throws IOException {
        int reportNumber = getNextReportNumber();
        String fileName = String.format(
                "%s%02d%s",
                REPORT_PREFIX,
                reportNumber,
                REPORT_EXTENSION
        );

        File reportFile = new File(REPORT_DIRECTORY, fileName);

        if (!reportFile.createNewFile()) {
            throw new IOException(
                    "Unable to create report file: " + reportFile.getAbsolutePath()
            );
        }

        return reportFile.getPath();
    }
}