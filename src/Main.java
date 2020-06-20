import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Integer> data = Files.lines(Paths.get("src/test_data.txt")).map(Integer::valueOf).collect(Collectors.toList());
        checkIndicators(data);
    }

    public static void checkIndicators(List<Integer> data) {
        data.remove(0);
        int controlValue = data.remove(data.size() - 1);
        Integer min = data.stream().filter(x -> x % 2 == 0 && x % 3 != 0).min(Integer::compareTo).orElse(null);
        Integer max = data.stream().filter(x -> x % 2 == 0 && x % 3 != 0).max(Integer::compareTo).orElse(null);
        int averageValue = (min != null && max != null) ? (min + max) / 2 : 0;
        System.out.println("Среднее значение между максимальным и минимальным элементом = " + averageValue);
        System.out.println("Полученное контрольное значение  = " + controlValue);
        String controlCheckMessage = "Контроль пройдет/ Не пройден: ";
        if (averageValue == controlValue) {
            controlCheckMessage += "Контроль пройден";
        } else {
            controlCheckMessage += "Контроль не пройден";
        }
        System.out.println(controlCheckMessage);
    }

}
