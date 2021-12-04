package RashotPackage;
import java.util.Arrays;
import java.util.Scanner;

class MyException extends Exception{           //Создаем класс исключений
    public MyException(String message)
    {
        super(message);
    }
}
class operations {                             //Класс операций со строками и числами
    public static String arabToRoman(int a) {  //Переводит арабские числа в римские
        StringBuilder b = new StringBuilder();
        int x,ix,v,iv,i;
        x =a/10;                               //Находим количество десятков
        ix=a%10/9;                             //Из остатка находим количество девяток
        v =a%10%9/5;                           //Потом пятерок
        iv=a%10%9%5/4;                         //Четверок
        i =a%10%9%5%4;                         //И единиц

        b.append("X".repeat(Math.max(0, x)));  //Добавляем в конец строки десятки
        if(ix==1){
            b.append("IX");                    //Если есть, то добавляем девятки
        }
        if(v==1){
            b.append("V");                     //Если есть, то добавляем пятерки
        }
        if(iv==1){
            b.append("IV");                    //Если есть, то добавляем четверки
        }
        b.append("I".repeat(Math.max(0, i)));  //Добавляем единицы
        return (b.toString());                 //Возвращаем строку
    }

    public static int romanToArab(String a) throws MyException{  //Переводит римские числа в арабские
        if(a.contains("-"))                                      //Если число содержит минус, выбрасываем исключение
            throw new MyException("Roman number system has no numbers less than 0 or equals 0");
        String upper = a.toUpperCase(), char1, char2, char3, char4, char5;//Перевели в верхний регистр и обьявляем переменные для замены символов
        upper = upper.trim();                                    //Стираем пробелы по краям строки
        char1 = upper.replaceAll("IX", "9+");
        char2 = char1.replaceAll("IV", "4+");
        char3 = char2.replaceAll("X", "10+");    //Заменяем римские числа на арабские с добавлением плюсиков
        char4 = char3.replaceAll("V", "5+");     //Чтобы их потом сложить
        char5 = char4.replaceAll("I", "1+");

        String[] myArray = char5.split("\\+");     //Разбиваем получившуюся строку на числа
        int sum = 0;                                    //Создаем переменную для записи суммы
        try {
            for (String s : myArray) {
                sum += Integer.parseInt(s);             //Переводим каждую ячейку (число) в тип Int и...
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("Entered operation is bad");        //При ошибке выбрасываем исключение и завершаем работу
            System.exit(0);
        }
        return (sum);                                   //Если ошибок нет, то возвращаем получившуюся сумму

    }
    public static String[] separator(String st) throws MyException{ //Делит строку на числа и знак операции
        String[] substrings1 = st.split("\\+");    //Делим строку на числа
        String[] substrings3 = st.split("\\*");
        String[] substrings4 = st.split("/");
        String[] substrings2 = st.split("-");
        if(substrings1.length==2) {                      //Если нашли знак + и всего 2 числа...
            String[] substrings = Arrays.copyOf(substrings1,3);//Создаем копию массива и добавляем в него одну ячейку для знака
            substrings[2]="+";                           //Добавляем сам знак
            return (substrings);                         //Возвращаем массив с числами и знаком
        }
        if(substrings2.length==2) {                      //Если нашли знак - и всего 2 числа...
            String[] substrings = Arrays.copyOf(substrings2,3);//Создаем копию массива и добавляем в него одну ячейку для знака
            substrings[2]="-";                           //Добавляем сам знак
            return (substrings);                         //Возвращаем массив с числами и знаком
        }
        if(substrings3.length==2) {                      //Если нашли знак * и всего 2 числа...
            String[] substrings = Arrays.copyOf(substrings3,3);//Создаем копию массива и добавляем в него одну ячейку для знака
            substrings[2]="*";                           //Добавляем сам знак
            return (substrings);                         //Возвращаем массив с числами и знаком
        }
        if(substrings4.length==2) {                      //Если нашли знак / и всего 2 числа...
            String[] substrings = Arrays.copyOf(substrings4,3);//Создаем копию массива и добавляем в него одну ячейку для знака
            substrings[2]="/";                           //Добавляем сам знак
            return (substrings);                         //Возвращаем массив с числами и знаком
        }
        throw new MyException("Entered operation is bad");       //Если не нашли знаков операции или их больше одного, выбрасываем исключение
    }
}
class checker {                                    //Класс содержащий операции проверки
    public static boolean isArab(String a){        //Проверяет является ли строка арабским числом
        try {
            Integer.parseInt(a);                   //Если получается перевести строку в число, возвращаем true
            return (true);
        }
        catch (NumberFormatException e){
            return (false);                        //Если нет, то false
        }
    }
    public static void errors( int a, int b, boolean flag, String[] substrings) throws MyException{//Проверяет введенную пользователем операцию на ошибки
        if(checker.isArab(substrings[0]) && !checker.isArab(substrings[1]) ||
                !checker.isArab(substrings[0]) && checker.isArab(substrings[1]))
            throw new MyException("Number systems are cannot be different");    //Если введены числа разных числовых систем, выбрасываем исключение
        if(a>10 || b>10)
            throw new MyException("Entered number is so big");          //Если число больше 10, выбрасываем исключение
        if(a<0 || b<0)
            throw new MyException("Entered number is so small");          //Если число меньше 0, выбрасываем исключение
        if (!flag && substrings[2].equals("-") && a<=b)
            throw new MyException("Roman number system has no numbers less than 0 or equals 0");//Если при вычитании вычитаемое больше или равно уменьшаемому, выбрасываем исключение
        if(substrings[2].equals("/") && b==0)
            throw new MyException("Division by zero is impossible");//Если при делении делителем является 0, выбрасываем исключение
        if(substrings[2].equals("/") && a<b && !flag)
            throw new MyException("Roman number system has no numbers less than 0 or equals 0");//Если при делении римских чисел делитель больше делимого, выбрасываем исключение
    }
}

public class Main {                                          //Основной класс этого файла
    public static void main(String[] args) {                 //Главный исполняемый метод
        Scanner scanner = new Scanner(System.in);            //Создаем объект для чтения с клавиатуры
        int result=0;                                        //Сюда запишем результат в арабском виде
        String romanResult;                                  //А сюда в римском
        boolean flag=false;                                  //Создаем переменную для отслеживания числовой системы (true для арабской, false для римской)

        System.out.println("Enter the operation please:");
        String str = scanner.nextLine();                     //Считываем с клавиатуры операцию

        try {
            String[] substrings = operations.separator(str); //Вызываем метод для деления строки на числа
            int a,b;
            if (checker.isArab(substrings[0]) && checker.isArab(substrings[1])) {
                flag = true;                                 //Если введены арабские числа, флаг переводим в значение true
                a=Integer.parseInt(substrings[0]);           //А числа из строки переводим в Integer
                b=Integer.parseInt(substrings[1]);
            }else {
                a = operations.romanToArab(substrings[0]);   //Вызываем метод для конвертации римских чисел в арабские
                b = operations.romanToArab(substrings[1]);
            }
            checker.errors(a, b, flag, substrings);          //Вызываем метод для выявления ошибок
            if (substrings[2].equals("+"))                   //Если введен знак +, складываем числа
                result = a + b;
            if (substrings[2].equals("-"))                   //Если введен знак -, вычитаем из первого числа второе
                result = a - b;
            if (substrings[2].equals("*"))                   //Если введен знак *, перемножаем числа
                result = a * b;
            if (substrings[2].equals("/"))                   //Если введен знак /, делим первое число на второе
                result = a / b;

            if (!flag) {
                romanResult = operations.arabToRoman(result);//Если флаг выключен, переводим арабские числа в римские и выводим
                System.out.println(romanResult);
            } else
                System.out.println(result);                  //Если флаг включен, выводим результат арабскиими числами
        }
        catch (MyException e){
            System.out.println(e.getMessage());              //Получаем сообщение из исключения и выводим его
        }
    }
}