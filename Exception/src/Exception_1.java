public class Exception_1 {
    public static void main(String[] args) {
        // try~catch 문
        try {
            System.out.println(3 / 0);
        }catch (ArithmeticException e) {
            System.out.println("산술예외가 발생하여 종료");
        }

        // try~catch~finally 문
        try {
            System.out.println(3 / 0);
        }catch (RuntimeException e) {
            // 예외 상황에서만 실행됨
            // catch() 소괄호안에 명시한 예외만 처리할 수 있음
            // 단, RuntimeException 같은 부모예외클래스는 처리 가능
            System.out.println("숫자를 0으로 나눌 수 없습니다");
        }finally {
            // 정상, 비정상(예외의 경우 모두 실행됨
            // 자원의 회수를 위하여 사용함(예를 들어 메모리할당이 try문 안에서
            // 발생했다면 해당 메모리를 삭제하는 것
            System.out.println("프로그램 종료");
        }
    }
}
