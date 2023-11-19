public class Toys {
    private Integer id;
    private String name;
    private Integer amount;
    private Integer winningFrequency;

    private Toys(Integer id, String name, Integer amount, Integer winningFrequency) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.winningFrequency = winningFrequency;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getWinningFrequency() {
        return winningFrequency;
    }

    public Toys create(Integer id, String name, Integer amount, Integer winningFrequency) {
        return new Toys(id, name, amount, winningFrequency);
    }
}