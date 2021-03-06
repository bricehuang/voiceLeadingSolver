package solver;

import org.junit.Test;

public class MainTest {

    private void playSampleSATB(String filename) {
        Main.main(new String[]{"sample_satb/" + filename});
    }

    private static final boolean PLAY_REALIZATIONS = false;

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void ii65i64viTest(){
		if (PLAY_REALIZATIONS){
	        playSampleSATB("ii65-i64-v-i-cadence.satb");
	        try{
	            Thread.sleep(8000);
	        }
	        catch(InterruptedException e){
	            e.printStackTrace();
	        }
		}
    }

    @Test
    public void neapolitanCadenceTest(){
        if (PLAY_REALIZATIONS){
            playSampleSATB("neapolitan-cadence.satb");
            try{
                Thread.sleep(8000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }    

    @Test
    public void realization1Test(){
		if (PLAY_REALIZATIONS) {
	        playSampleSATB("realization1.satb");
	        try{
	            Thread.sleep(22000);
	        }
	        catch(InterruptedException e){
	            e.printStackTrace();
	        }
		}
    }

    @Test
    public void realization2Test(){
		if (PLAY_REALIZATIONS) {
	        playSampleSATB("realization2.satb");
	        try{
	            Thread.sleep(20000);
	        }
	        catch(InterruptedException e){
	            e.printStackTrace();
	        }
		}
    }

    @Test
    public void realization3Test(){
    	if (PLAY_REALIZATIONS) {
            playSampleSATB("realization3.satb");
	        try{
	            Thread.sleep(17000);
	        }
	        catch(InterruptedException e){
	            e.printStackTrace();
	        }
		}
    }

    @Test
    public void realization4Test(){
        if (PLAY_REALIZATIONS) {
            playSampleSATB("realization4.satb");
            try{
                Thread.sleep(12000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void realization5Test(){
        if (PLAY_REALIZATIONS) {
            playSampleSATB("realization5.satb");
            try{
                Thread.sleep(28000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void realization6Test(){
        if (PLAY_REALIZATIONS) {
            playSampleSATB("realization6.satb");
            try{
                Thread.sleep(34000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void realization7Test(){
        if (PLAY_REALIZATIONS) {
            playSampleSATB("realization7.satb");
            try{
                Thread.sleep(24000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void realization8Test(){
        if (PLAY_REALIZATIONS) {
            playSampleSATB("realization8.satb");
            try{
                Thread.sleep(14000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
