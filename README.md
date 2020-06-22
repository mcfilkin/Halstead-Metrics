# Метрики Холстеда (в процессе)
Программа и анализ метрик Холстеда для экзамена по дисциплине "Метрология и качество программного обеспечения информационных систем"

**Выполнил:** Максим Филькин, гр.3938  
**Вариант:** 6

**Цель работы:** разработать программу и оценить её сложность, используя метрики Холстеда.

**Задание:** Система «Электростатик» сканирует ячейку с квитанции оплаты электроэнергии и составляет статистику по полученным данным.  В конце проверки контроллер передает контрольное значение, по которому программа выдает соответствие с фактической цифрой учета и предполагаемой. Необходимо найти в заданной серии сканируемых показаний прибором минимальное и максимальное значение, кратное двум и не кратное трем, а затем сравнить их среднее арифметическое с контрольным значением. Количество энергии, получаемое прибором за минуту, не превышает 10000 условных единиц. Общее количество показаний прибора в серии может быть больше 10 000. Если такого значения не найдено, то программа должна вывести «0».

Программа должна вывести контроллеру отчет по следующей форме:
```
Среднее значение между максимальным и минимальным элементом =
Полученное контрольное значение =
Контроль пройдет/ Не пройден
```

Входные данные представлены следующим образом. В первой строке задаётся число N - общее количество показаний прибора. В каждой из следующих N строк задаётся одно неотрицательное целое число - очередное сканируемое значение. В последней строке передается контрольное значение. 

Пример входных данных:  
```
11  
12  
4  
5  
4  
25  
23  
21  
20  
10  
12  
26  
15
```
***
**Код программы (Main.java):**
```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Integer> data = Files.lines(Paths.get("src/test_data.txt"))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        checkIndicators(data);
    }

    public static void checkIndicators(List<Integer> data) {
        data.remove(0);
        int controlValue = data.remove(data.size() - 1);
        Integer min = data.stream()
                .filter(x -> x % 2 == 0 && x % 3 != 0)
                .min(Integer::compareTo)
                .orElse(null);
        Integer max = data.stream()
                .filter(x -> x % 2 == 0 && x % 3 != 0)
                .max(Integer::compareTo)
                .orElse(null);
        int averageValue = (min != null && max != null) ? (min + max) / 2 : 0;
        System.out.println("Среднее значение между максимальным и минимальным элементом = " + averageValue);
        System.out.println("Полученное контрольное значение  = " + controlValue);
        String controlCheckMessage = "Контроль пройдет/ Не пройден: ";
        controlCheckMessage += averageValue == controlValue ? "Контроль пройден" : "Контроль не пройден";
        System.out.println(controlCheckMessage);
    }

}
```
**Тестовые данные (test_data.txt):**
```
11  
12  
4  
5  
4  
25  
23  
21  
20  
10  
12  
26  
15
```
**Результат выполнения:**
```
Среднее значение между максимальным и минимальным элементом = 15
Полученное контрольное значение  = 15
Контроль пройдет/ Не пройден: Контроль пройден
```
**Расчет метрик Холстеда**
| Номер строки | Словарь операторов (n1)      | Словарь операндов (n2)                                                  | Общее число операторов (N1)                   | Общее число операндов (N2)                                                                |
| ------------ |:----------------------------:|:-----------------------------------------------------------------------:|:---------------------------------------------:|:-----------------------------------------------------------------------------------------:|
|1             |call                          |java, io, IOException                                                    |call                                           |java, io, IOException                                                                      |
|2             |                              |nio, file, Files                                                         |call                                           |java, nio, file, Files                                                                     |
|3             |                              |Paths                                                                    |call                                           |java, nio, file, Paths                                                                     |
|4             |                              |util, List                                                               |call                                           |java, util, List                                                                           |
|5             |                              |stream, Collectors                                                       |call                                           |java, util, stream, Collectors                                                             |
|6             |                              |                                                                         |                                               |                                                                                           |
|7             |                              |Main                                                                     |                                               |Main                                                                                       |
|8             |                              |                                                                         |                                               |                                                                                           |
|9             |                              |main, args, IOException                                                  |                                               |                                                                                           |
|10            |=                             |data, "src/test_data.txt"                                                |=, call, call                                  |data, "src/test_data.txt", Files, Paths                                                    |
|11            |::                            |Integer                                                                  |call, call                                     |Integer                                                                                    |
|12            |                              |                                                                         |call, call                                     |Collectors                                                                                 |
|13            |                              |                                                                         |call                                           |data                                                                                       |
|14            |                              |                                                                         |                                               |                                                                                           |
|15            |                              |                                                                         |                                               |                                                                                           |
|16            |                              |checkIndicators                                                          |                                               |checkIndicators, data                                                                      |
|17            |                              |0                                                                        |call                                           |data, 0                                                                                    |
|18            |-                             |controlValue, 1                                                          |=, -, call, call                               |controlValue, data, data, 1                                                                |
|19            |                              |min                                                                      |=, call                                        |min, data                                                                                  |
|20            |->, %, ==, &&, !=             |x, 2, 3                                                                  |call, ->, %, %, ==, &&, !=                     |x, 2, 3, 0                                                                                 |
|21            |                              |                                                                         |call, call                                     |Integer                                                                                    |
|22            |                              |null                                                                     |call                                           |null                                                                                       |
|23            |                              |max                                                                      |=, call                                        |max, data                                                                                  |
|24            |                              |                                                                         |call, ->, %, %, ==, &&, !=                     |x, 2, 3, 0                                                                                 |
|25            |                              |                                                                         |call, call                                     |Integer                                                                                    |
|26            |                              |                                                                         |call                                           |null                                                                                       |
|27            |?, :, /                       |averageValue                                                             |=, !=, &&, !=, ?, +, /, :                      |averageValue, min, min, max, max, null, null, 2, 0                                         |
|28            |                              |"Среднее значение между максимальным и минимальным элементом = ", System |call, call, +                                  |System, "Среднее значение между максимальным и минимальным элементом = ", averageValue     |
|29            |                              |"Полученное контрольное значение  = "                                    |call, call, +                                  |"Полученное контрольное значение  = ", System, controlValue                                |
|30            |                              |controlCheckMessage, "Контроль пройдет/ Не пройден: "                    |=                                              |controlCheckMessage, "Контроль пройдет/ Не пройден: "                                      |
|31            |+=                            |"Контроль пройден", "Контроль не пройден"                                |+=, ==, ?, :                                   |controlCheckMessage, averageValue, controlValue, "Контроль пройден", "Контроль не пройден" |
|32            |                              |                                                                         |call, call                                     |System, controlCheckMessage                                                                |
|33            |                              |                                                                         |                                               |                                                                                           |
|34            |                              |                                                                         |                                               |                                                                                           |
|35            |                              |                                                                         |                                               |                                                                                           |
|Количество    |-                             |-                                                                        |-                                              |-                                                                                          |
