import java.util.ArrayList;
import java.util.*;
import java.util.Stack;

public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Counter counter = new Counter(scanner.next());
        try{
            counter.deal();
            counter.exchange();
            double res=counter.calculate();
            System.out.println(res);
        }catch (NumberFormatException e){
            System.out.println("字符串转换成数字异常"+e);
        }catch (ArithmeticException e){
            System.out.println("运算异常"+e);
        }catch(StringIndexOutOfBoundsException e){
            System.out.println("字符串越界"+e);
        }catch(Exception e){
            System.out.println("其他异常"+e);
        }
    }
}

class Counter{
    String string;
    String [] strings;
    ArrayList<String> list;
    int con=0;//   括号记数

    public Counter(String string) {
        this.string = string;
        list=new ArrayList<>();
    }

    public void deal(){
        int i=0;
        StringBuilder stringBuilder=new StringBuilder(string);
        while(i< stringBuilder.length()){
            if(!Character.isDigit(stringBuilder.charAt(i))){
                if(stringBuilder.charAt(i)=='.'){
                    i++;
                }
                else if(stringBuilder.charAt(i)=='('){
                    do{
                        con++;
                        i++;
                    }while(stringBuilder.charAt(i)=='(');
                        stringBuilder.insert(i," ");
                }else {
                    stringBuilder.insert(i, ' ');
                    stringBuilder.insert(i + 2, ' ');
                    i = i + 2;
                }
            }
            i++;
        }
        string =stringBuilder.toString();
        strings=string.split(" ");
    }
    public static int priority(String sign){
        if("*".equals(sign)||"/".equals(sign)){
            return 1;
        }else if("+".equals(sign)||"-".equals(sign)){
            return 0;
        }
        return -1;
    }

    public void exchange(){
        Stack<String> preStack=new Stack<>();
        for(String s:strings){
            if(Character.isDigit(s.charAt(0))){
                list.add(s);
            }else if("(".equals(s)||preStack.isEmpty()){
                preStack.push(s);
            }else if(")".equals(s)){
                while(!"(".equals(preStack.peek())){
                    list.add(preStack.pop());
                }
                preStack.pop();
            }else if(priority(s)>priority(preStack.peek())){
                preStack.push(s);
            }else if(priority(s)<=priority(preStack.peek())){
                do{
                    list.add(preStack.pop());
                }while(!preStack.isEmpty()&&priority(s)<=priority(preStack.peek()));
                preStack.push(s);
            }
        }
        while(!preStack.isEmpty()){
            list.add(preStack.pop());
        }
        System.out.println(1);
    }

    public double calculate(){
        Stack<Double> stack=new Stack<>();
        for(String s:list){
            if(Character.isDigit(s.charAt(0))){
                stack.push(Double.parseDouble(s));
            }else if("+".equals(s)) {
                double i=stack.pop();
                double j=stack.pop();
                stack.push(i+j);
            }else if("-".equals(s)) {
                double i=stack.pop();
                double j=stack.pop();
                stack.push(j-i);
            }else if("*".equals(s)) {
                double i=stack.pop();
                double j=stack.pop();
                stack.push(i*j);
            }else if("/".equals(s)) {
                double i=stack.pop();
                double j=stack.pop();
                stack.push(j/i);
            }
        }

        return stack.pop();

    }
}