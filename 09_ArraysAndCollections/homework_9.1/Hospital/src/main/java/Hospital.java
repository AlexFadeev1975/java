public class Hospital {

    public static float[] generatePatientsTemperatures(int patientsCount) {

        //TODO: напишите метод генерации массива температур пациентов
        float [] temperatureDate = new float[patientsCount];
        float maxTemp = 40;
        float minTemp = 32;
        float diffTemp = maxTemp - minTemp;

        for (int i = 0; i< patientsCount; i++) {

            temperatureDate [i] = (float) (Math.random() * diffTemp + minTemp);
        }

        return temperatureDate;
    }

    public static String getReport(float[] temperatureData) {
        /*
        TODO: Напишите код, который выводит среднюю температуру по больнице,количество здоровых пациентов,
            а также температуры всех пациентов.
        */
        float sumTempetarure = 0;
        int countPatient = 0;
        float maxHealthTemp = 36.9f;
        float minHealthTemp = 36.2f;
        String arrayTostring ="";

        for (float temperatureDatum : temperatureData) {

            arrayTostring = arrayTostring + temperatureDatum + " ";

            sumTempetarure += temperatureDatum;
            countPatient = (temperatureDatum <= maxHealthTemp && temperatureDatum >= minHealthTemp) ? countPatient + 1 : countPatient;
        }

        return "Температуры пациентов: " +  arrayTostring.trim() +
                "\nСредняя температура: " + String.format("%.2f", (sumTempetarure / temperatureData.length)) +
                "\nКоличество здоровых: " + countPatient;
    }
}
