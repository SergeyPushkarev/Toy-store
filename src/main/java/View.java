public class View {
    public static void menu() {
        System.out.println("\nМеню магазина игрушек. Выберите действие: \n" +
                "1. Добавить/изменить игрушку. \n" +
                "2. Разыграть игрушку. \n" +
                "3. Показать все игрушки. \n" +
                "0. Выход. \n");
    }
    public static void enterID() {
        System.out.println("\nВведите ID уже существующей игрушки, либо несуществующий ID, для создания новой: ");
    }
    public static void toyNotFound(String id) {
        System.out.println("Игрушка с идентификатором [" + id + "] не была найдена. Заполните поля для новой игрушки далее.");
    }
    public static void toyFound(String id) {
        System.out.println("Игрушка с идентификатором [" + id + "] была найдена. Измените необходимые поля далее.");
    }
    public static void enteringName(String oldName) {
        System.out.println("Название. Старое значение: [" + oldName + "]. Введите новое значение, либо оставьте пустым, чтобым оставить неизменным:");
    }
    public static void enteringName() {
        System.out.println("Название. Введите значение:");
    }
    public static void enteringAmount(String oldAmount) {
        System.out.println("Количество. Старое значение: [" + oldAmount + "]. Введите новое значение, либо оставьте пустым, чтобым оставить неизменным:");
    }
    public static void enteringAmount() {
        System.out.println("Количество. Введите значение:");
    }
    public static void enteringWinningFrequency(String oldWinningFrequency) {
        System.out.println("Вероятность розыгрыша. Старое значение: [" + oldWinningFrequency + "]. Введите новое значение, либо оставьте пустым, чтобым оставить неизменным:");
    }
    public static void enteringWinningFrequency() {
        System.out.println("Вероятность розыгрыша. Введите значение:");
    }
    public static void youWonAToy(String toyName) {
        System.out.println("Вы выиграли игрушку - " + toyName);
    }
    public static void noThatNumber() {
        System.out.println("Такого номера в меню нет!");
    }
}
