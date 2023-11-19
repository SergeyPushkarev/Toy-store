import Exception.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.*;

public class Model {
    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    private static Boolean onlyNumbersInLine(String string) throws CustomInvalidValue {
        for(int i = 0; i < string.length(); i++) {
            if(!Character.isDigit(string.charAt(i))) {
                throw new CustomInvalidValue("Введены запрещенные символы (должны быть только цифры)");
            }
        }
        return true;
    }

    /**
     *
     * @throws IOException ошибка при открытии/закрытии файла с игрушками
     * @throws ParseException ошибка в структуре json файла, при парсировании в json объект
     * @throws InvalidAmountValue ошибка ввода количества игрушки
     * @throws InvalidWinningFrequencyValue ошибка ввода вероятности розыгрыша игрушки
     */
    public static void inputOrEditToy() throws IOException, ParseException, InvalidAmountValue, InvalidWinningFrequencyValue {
        final String dir = System.getProperty("user.dir") + "\\src\\main\\java\\";
        final String fileName = "Toys.Json";

        Scanner cs = new Scanner(System.in);

        View.enterID();
        String id = cs.nextLine();

        String fileInString = readFile(dir + fileName, StandardCharsets.UTF_8);

        JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(fileInString);
        JSONObject foundToy = (JSONObject) jsonObject.get(id);

        if (foundToy == null) {
            foundToy = new JSONObject();

            View.toyNotFound(id);
            View.enteringName();
            foundToy.put("name", cs.nextLine());

            View.enteringAmount();
            String amountTemp = cs.nextLine();
            try {
                onlyNumbersInLine(amountTemp);
                foundToy.put("amount", amountTemp);
            } catch (CustomInvalidValue e) {
                throw new InvalidAmountValue("При вводе количества введены запрещенные символы.");
            }

            View.enteringWinningFrequency();
            String winningFrequencyTemp = cs.nextLine();
            try {
                onlyNumbersInLine(winningFrequencyTemp);
                foundToy.put("winningFrequency", winningFrequencyTemp);
            } catch (CustomInvalidValue e) {
                throw new InvalidWinningFrequencyValue("При вводе вероятности розыгрыша введены запрещенные символы.");
            }
        } else {
            String oldName = foundToy.get("name").toString();
            String oldAmount = foundToy.get("amount").toString();
            String oldWinningFrequency = foundToy.get("winningFrequency").toString();

            View.toyFound(id);
            View.enteringName(oldName);
            foundToy.put("name", cs.nextLine());

            View.enteringAmount(oldAmount);
            String amountTemp = cs.nextLine();
            try {
                onlyNumbersInLine(amountTemp);
                foundToy.put("amount", amountTemp);
            } catch (CustomInvalidValue e) {
                throw new InvalidAmountValue("При вводе количества введены запрещенные символы.");
            }

            View.enteringWinningFrequency(oldWinningFrequency);
            String winningFrequencyTemp = cs.nextLine();
            try {
                onlyNumbersInLine(winningFrequencyTemp);
                foundToy.put("winningFrequency", winningFrequencyTemp);
            } catch (CustomInvalidValue e) {
                throw new InvalidWinningFrequencyValue("При вводе вероятности розыгрыша введены запрещенные символы.");
            }
        }
        jsonObject.put(id, foundToy);

        try(FileWriter writer = new FileWriter(dir + fileName, false))
        {
            writer.write(jsonObject.toString());
        } catch (IOException e) {
            throw new IOException();
        }
    }
    public static void recordWonToy(String keyWinner, String dir, Map<String, String> toyValue) throws IOException, ParseException {
        final String fileName = "ToysForDelivery.Json";

        String fileInString = readFile(dir + fileName, StandardCharsets.UTF_8);

        JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(fileInString);
        JSONObject foundToy = (JSONObject) jsonObject.get(keyWinner);

        if (foundToy == null) {
            foundToy = new JSONObject();
            foundToy.put("name", toyValue.get("name"));
            foundToy.put("amount", "1");
            foundToy.put("winningFrequency", toyValue.get("winningFrequency"));
        } else {
            String oldAmount = foundToy.get("amount").toString();

            foundToy.put("amount", String.valueOf(Integer.parseInt(oldAmount)+1));
        }
        jsonObject.put(keyWinner, foundToy);

        try(FileWriter writer = new FileWriter(dir + fileName, false))
        {
            writer.write(jsonObject.toString());
        } catch (IOException e) {
            throw new IOException();
        }
    }
    public static void writeDownSubtractionOfToy(String keyWinner, String dir, String fileName) throws IOException, ParseException {
        String fileInString = readFile(dir + fileName, StandardCharsets.UTF_8);

        JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(fileInString);
        JSONObject foundToy = (JSONObject) jsonObject.get(keyWinner);

        String oldAmount = foundToy.get("amount").toString();

        foundToy.put("amount", String.valueOf(Integer.parseInt(oldAmount)-1));
        jsonObject.put(keyWinner, foundToy);

        try(FileWriter writer = new FileWriter(dir + fileName, false))
        {
            writer.write(jsonObject.toString());
        } catch (IOException e) {
            throw new IOException();
        }
    }
    public static void playToy() throws IOException, ParseException {
        final String dir = System.getProperty("user.dir") + "\\src\\main\\java\\";
        final String fileName = "Toys.Json";
        Gson gson = new Gson();

        String fileInString = readFile(dir + fileName, StandardCharsets.UTF_8);

        Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
        Map<String, Map<String, String>> mapToys = gson.fromJson(fileInString, type);

        Map<String, Integer> mapToysLimit = new LinkedHashMap<>();
        int prevTopLimit = 0;

        for (var entry : mapToys.entrySet()) {
            if (prevTopLimit == 0) {
                mapToysLimit.put(entry.getKey(), Integer.parseInt(entry.getValue().get("winningFrequency")));
            } else {
                mapToysLimit.put(entry.getKey(), prevTopLimit + Integer.parseInt(entry.getValue().get("winningFrequency")));
            }

            prevTopLimit = mapToysLimit.get(entry.getKey());
        }

        String keyWinner = "";
        Random random = new Random();
        int randomInt = random.nextInt(0, prevTopLimit);

        for (var entry : mapToysLimit.entrySet()) {
           if (randomInt < entry.getValue()) {
               keyWinner = entry.getKey();
               break;
           }
        }

        View.youWonAToy(mapToys.get(keyWinner).get("name"));
        recordWonToy(keyWinner, dir, mapToys.get(keyWinner));
        writeDownSubtractionOfToy(keyWinner, dir, fileName);
    }
    public static void showAllToys() throws ParseException, IOException {
        final String dir = System.getProperty("user.dir") + "\\src\\main\\java\\";
        final String fileName = "Toys.Json";

        Gson gson = new Gson();

        String fileInString = readFile(dir + fileName, StandardCharsets.UTF_8);

        Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
        Map<String, Map<String, String>> mapToys = gson.fromJson(fileInString, type);

        for (var entry : mapToys.entrySet()) {
            System.out.println("[" + entry.getKey() + "] "
                    + entry.getValue().get("name")
                    +" в количестве " + entry.getValue().get("amount")
                    + " штук. Вероятность розыгрыша: " + entry.getValue().get("winningFrequency") );
        }
    }
}