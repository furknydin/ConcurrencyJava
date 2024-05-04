package Thread;

public class ThreadExample extends Thread{

    @Override
    public void run() {
        for (int i = 1; i < 10000; i++) {
            try {
                sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("--------------" + i + "---------------------");
        }
    }
    public static void main(String[] args) {
        ThreadExample threadExample = new ThreadExample();
        threadExample.start();
        try{
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            Thread.sleep(10000L);
            System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
        }catch (InterruptedException e){
            System.out.println("Interrupted handled");
        }
    }

}
