package custom;

// 사용자 정의 예외는 반드시 둘 중에 하나의 예외클래스를 상속받아야 함
// #1 일반 예외 Exception을 상속

class MyException extends Exception {
    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }
}
// #2. 실행예외 RuntimeException을 상속
class MyRTException extends RuntimeException {
    public MyRTException() {
        super();
    }

    public MyRTException(String message) {
        super(message);
    }
}
class A {
    void setAge(int age) {
        try {
            if (age >= 0) {
                System.out.println("정상동작");
            } else {
                // 예외발생. 반드시 사용자정의 예외의 객체를 만들어서 던져야함.
                throw new MyException("나이가 음수입니다");
            }
        }catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}
class B {
    void setAge(int age) throws MyException {
        if (age >= 0) {
            System.out.println("정상동작");
        } else {
            // 예외발생. 반드시 사용자정의 예외의 객체를 만들어서 던져야함.
            throw new MyException("나이가 음수입니다");
        }
    }
    void callSetAge(int age) {
        try {
            setAge(age);
        }catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}

public class CustomException_1 {
    public static void main(String[] args) {
        A a = new A(); // 예외 직접 처리의 예제
        a.setAge(-1);
        B b = new B(); // 예외 전가의 예제
        b.callSetAge(-1);
    }
}
