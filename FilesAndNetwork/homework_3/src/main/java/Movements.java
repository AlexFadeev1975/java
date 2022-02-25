import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Movements {

    Set<ExtractMovements> extractMovementsSet = new HashSet<>();

    public Movements(String pathMovementsCsv) {
        try {
            parseMovement(pathMovementsCsv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseMovement(String pathMovementsCsv) throws IOException {
        Path movementFile = Paths.get(pathMovementsCsv);
        Stream<String> streamMov = Files.lines(movementFile);
        streamMov.forEach(x -> {
            try {
                makeExtractMovementsSet(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void makeExtractMovementsSet(String line) throws IOException {

        ExtractMovements extractMovements = new ExtractMovements();

        String[] parsedLine = line.split(",", 7);
        if (parsedLine[6].matches("[^а-яА-ЯёЁ]+")) {
            String tempString, credit, debit;
            String[] nextParsedLine = parsedLine[5].split("    ");
            extractMovements.setDescriptionTransaction(nextParsedLine[1]);

            if (parsedLine[6].charAt(0) == '"') {
                nextParsedLine = parsedLine[6].split(",", 3);
                String beforeComma = nextParsedLine[0].replaceAll("\"", " ").trim();
                String afterComma = nextParsedLine[1].replaceAll("\"", " ").trim();
                credit = beforeComma + "." + afterComma;
                tempString = nextParsedLine[2].replaceAll(",", "\\.").trim();
                debit = tempString.replaceAll(",", "\\.");
            } else {
                nextParsedLine = parsedLine[6].split(",", 2);
                tempString = nextParsedLine[0].replaceAll(",", "\\.");
                credit = tempString.replaceAll("\"", " ").trim();
                tempString = nextParsedLine[1].replaceAll(",", "\\.");
                debit = tempString.replaceAll("\"", " ").trim();
            }
            extractMovements.setCredit(Double.parseDouble(credit));
            extractMovements.setDebit(Double.parseDouble(debit));
            extractMovementsSet.add(extractMovements);
        }
    }

    public double getExpenseSum() {
        return extractMovementsSet.stream().mapToDouble(ExtractMovements::getDebit).sum();
    }

    public double getIncomeSum() {
        return extractMovementsSet.stream().mapToDouble(ExtractMovements::getCredit).sum();
    }

    public void print() {
        System.out.println("Сумма расходов: " + getExpenseSum());
        System.out.println("Сумма доходов: " + getIncomeSum());
        System.out.println("Сумма расходов по организациям: ");
        extractMovementsSet.stream().filter(x -> x.getDebit() != 0).forEach(x ->
                System.out.println(x.getDescriptionTransaction() + "  " + x.getDebit() + "  руб."));


    }
}
