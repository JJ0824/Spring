public class Exception_2 {
    public static void main(String[] args) {
        // 다중 try~catch문
        try {
            System.out.println(3/1);
            int num = Integer.parseInt("10A"); // NumberFormatException
            int[] array = new int[3];
            array[3]=0; // ArrayIndexOutOfBoundsEx
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
            System.out.println("0으로 나눌 수 없음");
        } catch (NumberFormatException e) {
            System.out.println("숫자가 아닙니다");
        }
        // 예상 가능한 예외는 catch문을 통해서 예외처리를  해주는 게 원칙이나,
        // 만약 예상하지 못한 예외의 가능성이 있다면 제일 아래에 부모예외클래스를
        // 추가해주어 모든 예외를 처리해줄 수 있음
        catch (RuntimeException e) {
            System.out.println("기타 예외 발생.. 하지만 난 처리했음");
        }
        // 중요!! ****
        // RuntimeException이나 Exception같은 부모예외클래스를 제일 위의
        // catch문에 선언하면 안 됨
        // 모든 종류의 예외가 부모예외클래스에서 먼저 처리되므로
        // 그 아래 세부 예외처리는 실행될 수 없음!!!
    }
}
