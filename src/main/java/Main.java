import Exception.*;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        boolean flag = true;
        while (flag) {
            try {
                flag = Controller.start();
            } catch (InvalidWinningFrequencyValue e) {
                System.out.println("При вводе вероятности розыгрыша введены не только цифры!");
            } catch (IOException e) {
                System.out.println("Ошибка при открытии JSON файла с игрушками1");
            } catch (ParseException e) {
                System.out.println("Ошибка в структуре JSON файла с игрушками!");
            } catch (InvalidAmountValue e) {
                System.out.println("При вводе количества введены не только цифры!");
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }
}