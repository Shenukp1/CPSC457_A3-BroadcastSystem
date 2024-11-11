
public class CriticalSection {

    public void enter(int id) {
        System.out.println("Processor " + id + " Entered.");
       
    }

    public void exit(int id) {
        System.out.println("Processor " + id + " Exited.");
    }
}
