package utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
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

    public static void generateReport(
            String reportPath,
            String testName,
            List<String> testLogs,
            List<String> healingEvents) throws IOException {

        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><html><head>");
        html.append("<title>Self-Healing Test Report</title>");
        html.append("<style>");
        html.append("body{font-family:Arial,sans-serif;margin:30px;}");
        html.append("h1{margin-bottom:5px;}");
        html.append("h2{margin-top:25px;}");
        html.append("details{margin-top:15px;border:1px solid #ccc;border-radius:5px;padding:10px;}");
        html.append("summary{font-weight:bold;cursor:pointer;font-size:16px;}");
        html.append(".logs{background:#f7f7f7;padding:15px;margin-top:10px;border-radius:4px;}");
        html.append(".log{font-family:monospace;padding:4px 0;border-bottom:1px solid #eee;}");
        html.append("table{border-collapse:collapse;width:100%;margin-top:15px;}");
        html.append("th,td{border:1px solid #ccc;padding:10px;text-align:left;}");
        html.append("th{background:#f2f2f2;}");
        html.append(".failed{background:#fff2cc;}");
        html.append(".fallback{background:#fce4d6;}");
        html.append(".success{background:#d9ead3;}");
        html.append("</style>");
        html.append("</head><body>");

        html.append("<h1>Self-Healing Test Report</h1>");
        html.append("<h2>Test Case: ").append(escapeHtml(testName)).append("</h2>");

        // Test execution logs
        html.append("<details>");
        html.append("<summary>Test Execution Logs</summary>");
        html.append("<div class='logs'>");

        for (String log : testLogs) {
            html.append("<div class='log'>")
                    .append(escapeHtml(log))
                    .append("</div>");
        }

        html.append("</div>");
        html.append("</details>");

        // Self-healing strategy
        html.append("<details>");
        html.append("<summary>Self-Healing Strategy</summary>");

        html.append("<table>");
        html.append("<tr>");
        html.append("<th>Element</th>");
        html.append("<th>Locator</th>");
        html.append("<th>Status</th>");
        html.append("<th>Details</th>");
        html.append("</tr>");

        for (String event : healingEvents) {
            String[] parts = event.split("\\|");

            String element = parts.length > 0 ? parts[0].trim() : "";
            String locator = parts.length > 1 ? parts[1].trim() : "";
            String status = parts.length > 2 ? parts[2].trim() : "";
            String details = parts.length > 3 ? parts[3].trim() : "";

            String cssClass = "";

            if ("FAILED".equals(status)) {
                cssClass = "failed";
            } else if ("FALLBACK SUCCESS".equals(status)) {
                cssClass = "fallback";
            } else {
                cssClass = "success";
            }

            html.append("<tr class='").append(cssClass).append("'>");
            html.append("<td>").append(escapeHtml(element)).append("</td>");
            html.append("<td>").append(escapeHtml(locator)).append("</td>");
            html.append("<td>").append(escapeHtml(status)).append("</td>");
            html.append("<td>").append(escapeHtml(details)).append("</td>");
            html.append("</tr>");
        }

        html.append("</table>");
        html.append("</details>");

        html.append("</body></html>");

        java.nio.file.Files.writeString(
                java.nio.file.Paths.get(reportPath),
                html.toString()
        );
    }

    private static String escapeHtml(String text) {
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}