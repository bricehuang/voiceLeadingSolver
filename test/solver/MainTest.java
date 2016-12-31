package solver;

import org.junit.Test;

public class MainTest {

    private void playSampleSATB(String filename) {
        Main.main(new String[]{"sample_satb/" + filename});
    }
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void ii65i64viTest(){
        playSampleSATB("ii65-i64-v-i-cadence.satb");
        try{
            Thread.sleep(8000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

}
