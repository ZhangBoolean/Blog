import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入身高(m):");
        float height = s.nextFloat();
        System.out.println("请输入体重(kg):");
        float weight = s.nextFloat();
        float BMI = weight / (height * height);
        System.out.println("当前的BMI是: " + BMI);

        String status = "";

        if (BMI < 18.5)
            status = "体重过轻";
        else if ( BMI < 24)
            status = "正常范围";
        else if (BMI < 27)
            status = "体重过重";
        else if (BMI < 30)
            status = "轻度肥胖";
        else if (BMI < 35)
            status = "中度肥胖";
        else
            status = "重度肥胖";

        System.out.println("身体状态是: " + status);

    }
}
