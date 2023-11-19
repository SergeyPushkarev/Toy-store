import Exception.*;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class Controller {
    public static Boolean start() throws IOException, ParseException, InvalidWinningFrequencyValue, InvalidAmountValue {
        Scanner cs = new Scanner(System.in);
        View.menu();

        int num = cs.nextInt();

        switch (num) {
            case 1 -> {
                Model.inputOrEditToy();
            }
            case 2 -> {
                Model.playToy();
            }
            case 3 -> {
                Model.showAllToys();
            }
            case 0 -> {
                return false;
            }
            default -> View.noThatNumber();
        }
        return true;
    }
}