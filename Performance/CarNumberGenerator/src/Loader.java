import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Loader extends Thread {

    private int startNumber = 0;
    private int endNumber = 0;


    public Loader(int endNumber, int startNumber) {
        this.endNumber = endNumber;
        this.startNumber = startNumber;
    }

    public static void main(String[] args) throws Exception {
        final int COUNTTHREADS = 7;
        int partNumber, startNumber = 1;
        int endNumber;

        partNumber = 1000 / COUNTTHREADS;
        endNumber = partNumber;

        Thread[] arrThreads = new Thread[COUNTTHREADS];

        for (int i = 0; i < COUNTTHREADS; i++) {
            arrThreads[i] = new Loader(endNumber, startNumber);
            startNumber = endNumber + 1;
            endNumber = endNumber + partNumber;
        }
        for (int i = 0; i < COUNTTHREADS; i++) {

            arrThreads[i].start();
        }
    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        if (padSize != 0) {
            numberStr = (padSize == 2) ? "00" + numberStr : "0" + numberStr;
        }

        return numberStr;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        String fileName = "res/numbers" + startNumber + ".txt";
        try (PrintWriter writer = new PrintWriter(fileName)) {

            StringBuilder stringBuilder = new StringBuilder();
            char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
            for (int number = startNumber; number < endNumber; number++) {
                int regionCode = 199;
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            stringBuilder.append(firstLetter);
                            stringBuilder.append(padNumber(number, 3));
                            stringBuilder.append(secondLetter);
                            stringBuilder.append(thirdLetter);
                            stringBuilder.append(padNumber(regionCode, 2));
                            stringBuilder.append('\n');
                        }
                    }
                }
            }
            writer.write(stringBuilder.toString());
            writer.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }
}
