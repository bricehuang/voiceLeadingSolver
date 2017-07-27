package scorer;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import chord_data.ContextTag;
import chords.Chord;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;
import music.Note;
import score_data.PenaltyType;
import score_data.Score;

public class ScorerTest {
    
    private static final boolean DEBUG = false;
    
    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote E = new BasicNote(2,4);
    private static final BasicNote F = new BasicNote(3,5);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote Gs = new BasicNote(4,8);
    private static final BasicNote Ab = new BasicNote(5,8);
    private static final BasicNote A = new BasicNote(5,9);
    private static final BasicNote B = new BasicNote(6,11);

    private static final Key C_MAJOR = new Key(C,true);
    private static final Key A_MINOR = new Key(A,false);
    
    private static final Key C_MINOR = new Key(C,false);

    private static final PrimitiveChord C_MAJ_ROOT = 
            new PrimitiveChord(C, ChordType.MAJ, 0);
    private static final PrimitiveChord C_MAJ_INV1 = 
            new PrimitiveChord(C, ChordType.MAJ, 1);
    private static final PrimitiveChord C_MAJ_INV2 = 
            new PrimitiveChord(C, ChordType.MAJ, 2);
    
    private static final PrimitiveChord D_MIN_ROOT = 
            new PrimitiveChord(D, ChordType.MIN, 0);
    private static final PrimitiveChord D_MIN_INV1 =
            new PrimitiveChord(D, ChordType.MIN, 1);
    private static final PrimitiveChord D_MIN7_INV1 = 
            new PrimitiveChord(D, ChordType.MIN7, 1);

    private static final PrimitiveChord E_DOM7_ROOT = 
            new PrimitiveChord(E, ChordType.DOM7, 0);
    private static final PrimitiveChord E_DOM7_INV1 = 
            new PrimitiveChord(E, ChordType.DOM7, 1);
    private static final PrimitiveChord E_DOM7_INV2 = 
            new PrimitiveChord(E, ChordType.DOM7, 2);
    private static final PrimitiveChord E_DOM7_INV3 = 
            new PrimitiveChord(E, ChordType.DOM7, 3);
    
    private static final PrimitiveChord F_MAJ_INV1 = 
            new PrimitiveChord(F, ChordType.MAJ, 1);
    private static final PrimitiveChord F_MIN_ROOT = 
            new PrimitiveChord(F, ChordType.MIN, 0);

    private static final PrimitiveChord G_MAJ_ROOT = 
            new PrimitiveChord(G, ChordType.MAJ, 0);
    private static final PrimitiveChord G_MAJ_INV1 = 
            new PrimitiveChord(G, ChordType.MAJ, 1);
    private static final PrimitiveChord G_DOM7_ROOT = 
            new PrimitiveChord(G, ChordType.DOM7, 0);
  
    private static final PrimitiveChord A_MIN_ROOT = 
            new PrimitiveChord(A, ChordType.MIN, 0);
    private static final PrimitiveChord A_MIN_INV1 =
            new PrimitiveChord(A, ChordType.MIN, 1);
    
    private static final PrimitiveChord B_DIM7_INV1 = 
            new PrimitiveChord(B, ChordType.DIM7, 1);
    private static final PrimitiveChord B_DIM7_INV2 = 
            new PrimitiveChord(B, ChordType.DIM7, 2);
    private static final PrimitiveChord B_DIM7_INV3 = 
            new PrimitiveChord(B, ChordType.DIM7, 3);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }
    
    private void printPenaltyChord(Chord chord, Key key, Score score){
        if(DEBUG){
            System.err.println("Scoring chord: " + chord.toString() + "\n" + score.toString());
        }
    }
    
    private void printPenaltyTransition(Chord previous, Chord current, Key key, Score score){
        if(DEBUG){
            System.err.println("Scoring transition: " + previous.toString() + "-->" + current.toString() 
                + "\n" + score.toString());
        }
    }

    
    /**********************
     * Tests for Doubling *
     **********************/
    
    @Test
    public void testGoodDoublingSD1(){
        Score score = new Score();
        Chord cMajorDoubleRoot = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
                C_MAJ_ROOT
                );
        Doubling.scoreDoubling(cMajorDoubleRoot, new HashSet<>(), C_MAJOR,  score);
        
        assertEquals(0, score.totalScore());
        printPenaltyChord(cMajorDoubleRoot, C_MAJOR, score);
    }

    @Test
    public void testBadDoublingSD2(){
        Score score = new Score();
        Chord dMinorDoubleRoot = new Chord(
                new Note(D, 5), new Note(F, 4), new Note(A, 3), new Note(D, 3), 
                D_MIN_ROOT
                );
        Doubling.scoreDoubling(dMinorDoubleRoot, new HashSet<>(), C_MAJOR,  score);
        
        assertEquals(PenaltyType.BAD_DOUBLING.value(), score.totalScore());
        printPenaltyChord(dMinorDoubleRoot, C_MAJOR, score);
    }

    @Test
    public void testBadDoublingSD3(){
        Score score = new Score();
        Chord cMajorDoubleThird = new Chord(
                new Note(E, 5), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
                C_MAJ_ROOT
                );
        Doubling.scoreDoubling(cMajorDoubleThird, new HashSet<>(), C_MAJOR,  score);
        
        assertEquals(PenaltyType.BAD_DOUBLING.value(), score.totalScore());
        printPenaltyChord(cMajorDoubleThird, C_MAJOR, score);
    }

    @Test
    public void testGoodDoublingSD4(){
        Score score = new Score();
        Chord dMinorDoubleThird = new Chord(
                new Note(A, 4), new Note(F, 4), new Note(D, 4), new Note(F, 3), 
                D_MIN_INV1
                );
        Doubling.scoreDoubling(dMinorDoubleThird, new HashSet<>(), C_MAJOR,  score);
        
        assertEquals(0, score.totalScore());
        printPenaltyChord(dMinorDoubleThird, C_MAJOR, score);
    }
    
    @Test
    public void testTripledSD1(){
        Score score = new Score();
        Chord cMajorTripleRootIncomplete= new Chord(
                new Note(C, 5), new Note(C, 4), new Note(E, 3), new Note(C, 3),  
                C_MAJ_ROOT
                );
        Doubling.scoreDoubling(cMajorTripleRootIncomplete, new HashSet<>(), C_MAJOR,  score);
        
        assertEquals(PenaltyType.OMITTED_FIFTH.value(), score.totalScore());
        printPenaltyChord(cMajorTripleRootIncomplete, C_MAJOR, score);
    }

    @Test
    public void testBadDoubleDouble(){
        Score score = new Score();
        Chord cMajorDoubleRootThirdIncomplete = new Chord(
                new Note(E, 5), new Note(E, 4), new Note(C, 4), new Note(C, 3), 
                C_MAJ_ROOT
                );
        Doubling.scoreDoubling(cMajorDoubleRootThirdIncomplete, new HashSet<>(), C_MAJOR,  score);
        
        assertEquals(PenaltyType.DOUBLE_DOUBLING.value() + PenaltyType.OMITTED_FIFTH.value(), score.totalScore());
        printPenaltyChord(cMajorDoubleRootThirdIncomplete, C_MAJOR, score);
    }
    
    @Test
    public void testDoubleLeadingTone(){
        Score score = new Score();
        Chord gMajorDoubleThird = new Chord(
                new Note(B, 4), new Note(D, 4), new Note(B, 3), new Note(G, 3), 
                G_MAJ_ROOT
                );
        Doubling.scoreDoubling(gMajorDoubleThird, new HashSet<>(), C_MAJOR,  score);
        
        assertEquals(PenaltyType.BAD_DOUBLING.value() + PenaltyType.DOUBLED_LEADING_TONE.value(), score.totalScore());
        printPenaltyChord(gMajorDoubleThird, C_MAJOR, score);
    }
    
    @Test
    public void testNotDoubleLeadingTone(){
        Score score = new Score();
        Chord gMajorDoubleRoot = new Chord(
                new Note(B, 4), new Note(D, 4), new Note(G, 3), new Note(G, 2), 
                G_MAJ_ROOT
                );
        Doubling.scoreDoubling(gMajorDoubleRoot, new HashSet<>(), C_MAJOR,  score);
        
        assertEquals(0, score.totalScore());
        printPenaltyChord(gMajorDoubleRoot, C_MAJOR, score);
    }
    
    /***************************
     * Tests for Voice Overlap *
     ***************************/
    
    @Test
    public void testVoiceOverlap(){
        Score score = new Score();
        Chord cMajorVoiceOverlap = new Chord(
                new Note(C, 5), new Note(C, 5), new Note(E, 4), new Note(G, 3), 
                C_MAJ_INV2
                );
        VoiceOverlap.scoreVoiceOverlap(cMajorVoiceOverlap, new HashSet<>(), C_MAJOR,  score);
        assertEquals(PenaltyType.VOICE_OVERLAP.value(), score.totalScore());
        printPenaltyChord(cMajorVoiceOverlap, C_MAJOR, score);
    }
    
    /*******************************
     * Tests for DoublingInCadence *
     *******************************/
    
    @Test
    public void testCadenceDoublingIGood(){
        Score score = new Score();
        Chord cMajor = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
                C_MAJ_ROOT
                );
        Set<ContextTag> tags = new HashSet<>();
        tags.add(ContextTag.CADENCE);
        DoublingInCadence.scoreDoubling(cMajor, tags, C_MAJOR,  score);
        assertEquals(0, score.totalScore());
        printPenaltyChord(cMajor, C_MAJOR, score);
    }
    
    @Test
    public void testCadenceDoublingIBad(){
        Score score = new Score();
        Chord cMajor = new Chord(
                new Note(G, 4), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
                C_MAJ_ROOT
                );
        Set<ContextTag> tags = new HashSet<>();
        tags.add(ContextTag.CADENCE);
        DoublingInCadence.scoreDoubling(cMajor, tags, C_MAJOR,  score);
        assertEquals(PenaltyType.CADENCE_DOUBLING.value(), score.totalScore());
        printPenaltyChord(cMajor, C_MAJOR, score);
    }

    @Test
    public void testCadenceDoublingVGood(){
        Score score = new Score();
        Chord gMajor = new Chord(
                new Note(B, 4), new Note(G, 4), new Note(D, 4), new Note(G, 3), 
                G_MAJ_ROOT
                );
        Set<ContextTag> tags = new HashSet<>();
        tags.add(ContextTag.CADENTIAL_V);
        DoublingInCadence.scoreDoubling(gMajor, tags, C_MAJOR,  score);
        assertEquals(0, score.totalScore());
        printPenaltyChord(gMajor, C_MAJOR, score);
    }
    
    @Test
    public void testCadenceDoublingVBad(){
        Score score = new Score();
        Chord gMajor = new Chord(
                new Note(D, 5), new Note(B, 4), new Note(D, 4), new Note(G, 3), 
                G_MAJ_ROOT
                );
        Set<ContextTag> tags = new HashSet<>();
        tags.add(ContextTag.CADENTIAL_V);
        DoublingInCadence.scoreDoubling(gMajor, tags, C_MAJOR,  score);
        assertEquals(PenaltyType.CADENCE_DOUBLING.value(), score.totalScore());
        printPenaltyChord(gMajor, C_MAJOR, score);
    }
    
    @Test
    public void testCadenceDoublingI64Good(){
        Score score = new Score();
        Chord cMajor64 = new Chord(
                new Note(C, 5), new Note(G, 4), new Note(E, 4), new Note(G, 3), 
                C_MAJ_INV2
                );
        Set<ContextTag> tags = new HashSet<>();
        tags.add(ContextTag.CADENTIAL_I64);
        DoublingInCadence.scoreDoubling(cMajor64, tags, C_MAJOR,  score);
        assertEquals(0, score.totalScore());
        printPenaltyChord(cMajor64, C_MAJOR, score);
    }
    
    @Test
    public void testCadenceDoublingI64Bad(){
        Score score = new Score();
        Chord cMajor64 = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(C, 4), new Note(G, 3), 
                C_MAJ_INV2
                );
        Set<ContextTag> tags = new HashSet<>();
        tags.add(ContextTag.CADENTIAL_I64);
        DoublingInCadence.scoreDoubling(cMajor64, tags, C_MAJOR,  score);
        assertEquals(PenaltyType.CADENCE_DOUBLING.value(), score.totalScore());
        printPenaltyChord(cMajor64, C_MAJOR, score);
    }
    
    /*****************
     * Tests for PAC *
     *****************/
    
    @Test
    public void testPacGood(){
        Score score = new Score();
        Chord cMajor = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
                C_MAJ_ROOT
                );
        Set<ContextTag> tags = new HashSet<>();
        tags.add(ContextTag.CADENCE);
        Pac.scorePAC(cMajor, tags, C_MAJOR,  score);
        assertEquals(0, score.totalScore());
        printPenaltyChord(cMajor, C_MAJOR, score);
    }
    
    @Test
    public void testPacBad(){
        Score score = new Score();
        Chord cMajor = new Chord(
                new Note(E, 5), new Note(G, 4), new Note(C, 4), new Note(C, 3), 
                C_MAJ_ROOT
                );
        Set<ContextTag> tags = new HashSet<>();
        tags.add(ContextTag.CADENCE);
        Pac.scorePAC(cMajor, tags, C_MAJOR,  score);
        assertEquals(PenaltyType.NOT_PAC.value(), score.totalScore());
        printPenaltyChord(cMajor, C_MAJOR, score);
    }
    
    /******************************
     * Composite ScoreChord tests *
     ******************************/
        
    @Test
    public void testBadDoublingSD3VoiceOverlap(){
        Chord cMajorVoiceOverlapDoubleThird = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(E, 4), new Note(G, 3), 
                C_MAJ_INV2
                );
        Score score = Scorer.scoreChord(cMajorVoiceOverlapDoubleThird, new HashSet<>(), C_MAJOR);
        assertEquals(PenaltyType.VOICE_OVERLAP.value() + PenaltyType.BAD_DOUBLING.value(), score.totalScore());
        printPenaltyChord(cMajorVoiceOverlapDoubleThird, C_MAJOR, score);
    }
    
    /*******************************
     * Tests for ParallelsDirects  *
     *******************************/

    @Test
    public void testParallels(){
        Score score = new Score();
        Chord dMinorDoubleThird = new Chord(
                new Note(F, 5), new Note(A, 4), new Note(D, 4), new Note(F, 3), 
                D_MIN_INV1
                );
        Chord cMajorDoubleRoot = new Chord(
                new Note(E, 5), new Note(G, 4), new Note(C, 4), new Note(C, 3), 
                C_MAJ_ROOT
                );
        ParallelsDirects.scoreParallels(dMinorDoubleThird, cMajorDoubleRoot, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(PenaltyType.PARALLEL.value(), score.totalScore());
        printPenaltyTransition(dMinorDoubleThird, cMajorDoubleRoot, C_MAJOR, score);
    }
    
    @Test
    public void testNotParallel(){
        Score score = new Score();
        Chord cMajor64 = new Chord(
                new Note(C, 5), new Note(G, 4), new Note(E, 4), new Note(G, 3), 
                C_MAJ_INV2
                );
        Chord gDominantSeven = new Chord(
                new Note(B, 4), new Note(F, 4), new Note(D, 4), new Note(G, 3), 
                G_DOM7_ROOT
                );
        ParallelsDirects.scoreParallels(cMajor64, gDominantSeven, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(cMajor64, gDominantSeven, C_MAJOR, score);
    }
    
    @Test
    public void testDirect(){
        Score score = new Score();
        Chord gMajorDoubleRoot = new Chord(
                new Note(G, 4), new Note(D, 4), new Note(G, 3), new Note(B, 2), 
                G_MAJ_INV1
                );
        Chord cMajorDoubleRoot = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
                C_MAJ_ROOT
                );
        ParallelsDirects.scoreDirects(gMajorDoubleRoot, cMajorDoubleRoot, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(PenaltyType.DIRECT.value(), score.totalScore());
        printPenaltyTransition(gMajorDoubleRoot, cMajorDoubleRoot, C_MAJOR, score);
    }

    @Test
    public void testNotDirect(){
        Score score = new Score();
        Chord gMajorDoubleRoot = new Chord(
                new Note(B, 4), new Note(D, 4), new Note(G, 3), new Note(G, 2), 
                G_MAJ_ROOT
                );
        Chord cMajorDoubleRoot = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
                C_MAJ_ROOT
                );
        ParallelsDirects.scoreDirects(gMajorDoubleRoot, cMajorDoubleRoot, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(gMajorDoubleRoot, cMajorDoubleRoot, C_MAJOR, score);
    }
    
    /**************************************
     * Tests for scoreDomSevenResolutions *
     **************************************/
    
    @Test
    public void testDomSevenRootGood(){
        Score score = new Score();
        Chord eDomSeven = new Chord(
                new Note(D, 5), new Note(Gs, 4), new Note(B, 3), new Note(E, 3), 
                E_DOM7_ROOT
                );
        Chord aMinor = new Chord(
                new Note(C, 5), new Note(A, 4), new Note(A, 3), new Note(A, 2), 
                A_MIN_ROOT
                );
        SevenChordResolution.scoreDomSevenResolutions(eDomSeven, aMinor, 
                new HashSet<>(), new HashSet<>(), A_MINOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(eDomSeven, aMinor, A_MINOR, score);
    }

    @Test
    public void testDomSevenRootBad(){
        Score score = new Score();
        Chord eDomSeven = new Chord(
                new Note(D, 5), new Note(Gs, 4), new Note(B, 3), new Note(E, 3), 
                E_DOM7_ROOT
                );
        Chord aMinor = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(A, 3), new Note(A, 2), 
                A_MIN_ROOT
                );
        SevenChordResolution.scoreDomSevenResolutions(eDomSeven, aMinor, 
                new HashSet<>(), new HashSet<>(), A_MINOR, score);
        assertEquals(PenaltyType.DOM_SEVEN_RES.value(), score.totalScore());
        printPenaltyTransition(eDomSeven, aMinor, A_MINOR, score);
    }

    @Test
    public void testDomSevenInv1Good(){
        Score score = new Score();
        Chord eDomSeven = new Chord(
                new Note(D, 5), new Note(B, 4), new Note(E, 4), new Note(Gs, 3), 
                E_DOM7_INV1
                );
        Chord aMinor = new Chord(
                new Note(C, 5), new Note(A, 4), new Note(E, 4), new Note(A, 3), 
                A_MIN_ROOT
                );
        SevenChordResolution.scoreDomSevenResolutions(eDomSeven, aMinor, 
                new HashSet<>(), new HashSet<>(), A_MINOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(eDomSeven, aMinor, A_MINOR, score);
    }

    @Test
    public void testDomSevenInv1Bad(){
        Score score = new Score();
        Chord eDomSeven = new Chord(
                new Note(D, 5), new Note(B, 4), new Note(E, 4), new Note(Gs, 3), 
                E_DOM7_INV1
                );
        Chord aMinor = new Chord(
                new Note(E, 5), new Note(C, 5), new Note(E, 4), new Note(A, 3), 
                A_MIN_ROOT
                );
        SevenChordResolution.scoreDomSevenResolutions(eDomSeven, aMinor, 
                new HashSet<>(), new HashSet<>(), A_MINOR, score);
        assertEquals(PenaltyType.DOM_SEVEN_RES.value(), score.totalScore());
        printPenaltyTransition(eDomSeven, aMinor, A_MINOR, score);
    }

    @Test
    public void testDomSevenInv2Good(){
        Score score = new Score();
        Chord eDomSeven = new Chord(
                new Note(E, 5), new Note(Gs, 4), new Note(D, 4), new Note(B, 2), 
                E_DOM7_INV2
                );
        Chord aMinor = new Chord(
                new Note(E, 5), new Note(A, 4), new Note(C, 4), new Note(A, 2), 
                A_MIN_ROOT
                );
        SevenChordResolution.scoreDomSevenResolutions(eDomSeven, aMinor, 
                new HashSet<>(), new HashSet<>(), A_MINOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(eDomSeven, aMinor, A_MINOR, score);
    }

    @Test
    public void testDomSevenInv2GoodAlt(){
        Score score = new Score();
        Chord eDomSeven = new Chord(
                new Note(E, 5), new Note(Gs, 4), new Note(D, 4), new Note(B, 2), 
                E_DOM7_INV2
                );
        Chord aMinor = new Chord(
                new Note(E, 5), new Note(A, 4), new Note(C, 4), new Note(C, 3), 
                A_MIN_INV1
                );
        SevenChordResolution.scoreDomSevenResolutions(eDomSeven, aMinor, 
                new HashSet<>(), new HashSet<>(), A_MINOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(eDomSeven, aMinor, A_MINOR, score);
    }

    @Test
    public void testDomSevenInv2Bad(){
        Score score = new Score();
        Chord eDomSeven = new Chord(
                new Note(E, 5), new Note(Gs, 4), new Note(D, 4), new Note(B, 2), 
                E_DOM7_INV2
                );
        Chord aMinor = new Chord(
                new Note(E, 5), new Note(A, 4), new Note(E, 4), new Note(C, 3), 
                A_MIN_INV1
                );
        SevenChordResolution.scoreDomSevenResolutions(eDomSeven, aMinor, 
                new HashSet<>(), new HashSet<>(), A_MINOR, score);
        assertEquals(PenaltyType.DOM_SEVEN_RES.value(), score.totalScore());
        printPenaltyTransition(eDomSeven, aMinor, A_MINOR, score);
    }

    @Test
    public void testDomSevenInv3Good(){
        Score score = new Score();
        Chord eDomSeven = new Chord(
                new Note(B, 4), new Note(Gs, 4), new Note(E, 4), new Note(D, 3), 
                E_DOM7_INV3
                );
        Chord aMinor = new Chord(
                new Note(C, 5), new Note(A, 4), new Note(E, 4), new Note(C, 3), 
                A_MIN_INV1
                );
        SevenChordResolution.scoreDomSevenResolutions(eDomSeven, aMinor, 
                new HashSet<>(), new HashSet<>(), A_MINOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(eDomSeven, aMinor, A_MINOR, score);
    }

    @Test
    public void testDomSevenInv3Bad(){
        Score score = new Score();
        Chord eDomSeven = new Chord(
                new Note(B, 4), new Note(Gs, 4), new Note(E, 4), new Note(D, 3), 
                E_DOM7_INV3
                );
        Chord aMinor = new Chord(
                new Note(A, 4), new Note(A, 4), new Note(E, 4), new Note(C, 3), 
                A_MIN_INV1
                );
        SevenChordResolution.scoreDomSevenResolutions(eDomSeven, aMinor, 
                new HashSet<>(), new HashSet<>(), A_MINOR, score);
        assertEquals(PenaltyType.DOM_SEVEN_RES.value(), score.totalScore());
        printPenaltyTransition(eDomSeven, aMinor, A_MINOR, score);
    }
    
    /**************************************
     * Tests for scoreDimSevenResolutions *
     **************************************/

    @Test
    public void testDimSevenGood1(){
        Score score = new Score();
        Chord bDimSeven = new Chord(
                new Note(Ab, 4), new Note(F, 4), new Note(B, 3), new Note(D, 3), 
                B_DIM7_INV1
                );
        Chord cMajor = new Chord(
                new Note(G, 4), new Note(E, 4), new Note(C, 4), new Note(E, 3), 
                C_MAJ_INV1
                );
        SevenChordResolution.scoreDimSevenResolutions(bDimSeven, cMajor, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(bDimSeven, cMajor, C_MAJOR, score);
    }
    
    @Test
    public void testDimSevenGood2(){
        Score score = new Score();
        Chord bDimSeven = new Chord(
                new Note(F, 4), new Note(D, 4), new Note(B, 3), new Note(Ab, 2), 
                B_DIM7_INV3
                );
        Chord cMajor = new Chord(
                new Note(E, 4), new Note(E, 4), new Note(C, 4), new Note(G, 2), 
                C_MAJ_INV2
                );
        SevenChordResolution.scoreDimSevenResolutions(bDimSeven, cMajor, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(bDimSeven, cMajor, C_MAJOR, score);
    }

    
    @Test
    public void testDimSevenBad1(){
        Score score = new Score();
        Chord bDimSeven = new Chord(
                new Note(Ab, 4), new Note(F, 4), new Note(B, 3), new Note(D, 3), 
                B_DIM7_INV1
                );
        Chord cMajor = new Chord(
                new Note(G, 4), new Note(E, 4), new Note(C, 4), new Note(C, 3), 
                C_MAJ_ROOT
                );
        SevenChordResolution.scoreDimSevenResolutions(bDimSeven, cMajor, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(PenaltyType.DIM_SEVEN_RES.value(), score.totalScore());
        printPenaltyTransition(bDimSeven, cMajor, C_MAJOR, score);
    }
    
    @Test
    public void testDimSevenBad2(){
        Score score = new Score();
        Chord bDimSeven = new Chord(
                new Note(D, 5), new Note(Ab, 4), new Note(B, 3), new Note(F, 3), 
                B_DIM7_INV2
                );
        Chord cMajor = new Chord(
                new Note(C, 5), new Note(G, 4), new Note(C, 4), new Note(E, 3), 
                C_MAJ_INV1
                );
        SevenChordResolution.scoreDimSevenResolutions(bDimSeven, cMajor, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(PenaltyType.DIM_SEVEN_RES.value(), score.totalScore());
        printPenaltyTransition(bDimSeven, cMajor, C_MAJOR, score);
    }
    
    /******************************
     * Tests for MelodicIntervals *
     ******************************/
    
    @Test
    public void testMelodicIntervalGood(){
        Score score = new Score();
        Chord fMinor = new Chord(
                new Note(Ab, 4), new Note(F, 4), new Note(C, 4), new Note(F, 2), 
                F_MIN_ROOT
                );
        Chord gMajor = new Chord(
                new Note(G, 4), new Note(D, 4), new Note(B, 3), new Note(G, 2), 
                G_MAJ_ROOT
                );
        MelodicIntervals.scoreMelodicIntervals(fMinor, gMajor, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(fMinor, gMajor, C_MINOR, score);
    }    
    
    @Test
    public void testMelodicIntervalBad(){
        Score score = new Score();
        Chord fMinor = new Chord(
                new Note(Ab, 4), new Note(F, 4), new Note(C, 4), new Note(F, 2), 
                F_MIN_ROOT
                );
        Chord gMajor = new Chord(
                new Note(B, 4), new Note(D, 4), new Note(G, 3), new Note(G, 2), 
                G_MAJ_ROOT
                );
        MelodicIntervals.scoreMelodicIntervals(fMinor, gMajor, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(PenaltyType.MELODIC_INTERVAL.value(), score.totalScore());
        printPenaltyTransition(fMinor, gMajor, C_MINOR, score);
    }
    
    /***************************
     * Tests for VoiceCrossing *
     ***************************/

    @Test
    public void testVoiceCrossing(){
        Score score = new Score();
        Chord gMajor = new Chord(
                new Note(B, 4), new Note(G, 4), new Note(D, 4), new Note(G, 2), 
                G_MAJ_ROOT
                );
        Chord fMajor = new Chord(
                new Note(F, 4), new Note(F, 4), new Note(C, 4), new Note(A, 2), 
                F_MAJ_INV1
                );
        VoiceCrossing.scoreVoiceCrossing(gMajor, fMajor, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(PenaltyType.VOICE_CROSSING.value(), score.totalScore());
        printPenaltyTransition(gMajor, fMajor, C_MINOR, score);
    }
    
    @Test
    public void testNotVoiceCrossing(){
        Score score = new Score();
        Chord gMajor = new Chord(
                new Note(B, 4), new Note(D, 4), new Note(G, 3), new Note(G, 2), 
                G_MAJ_ROOT
                );
        Chord cMajor = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
                C_MAJ_ROOT
                );
        VoiceCrossing.scoreVoiceCrossing(gMajor, cMajor, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(gMajor, cMajor, C_MINOR, score);
    }
    
    /***************************
     * Tests for smallMovement *
     ***************************/
    
    @Test
    public void testMovement(){
        Score score = new Score();
        Chord gMajor = new Chord(
                new Note(B, 4), new Note(G, 4), new Note(D, 4), new Note(G, 2), 
                G_MAJ_ROOT
                );
        Chord fMajor = new Chord(
                new Note(F, 4), new Note(C, 4), new Note(C, 4), new Note(A, 2), 
                F_MAJ_INV1
                );
        SmallMovement.scoreSmallMovement(gMajor, fMajor, 
                new HashSet<>(), new HashSet<>(), C_MAJOR, score);
        assertEquals(PenaltyType.MOVE_FOURTH.value()+PenaltyType.MOVE_FIFTH.value(), score.totalScore());
        printPenaltyTransition(gMajor, fMajor, C_MAJOR, score);
    }
    
    /**********************************
     * Tests for CadenceSmallMovement *
     **********************************/
    
    @Test
    public void testCadenceMovement(){
        Score score = new Score();
        Chord fMinor = new Chord(
                new Note(F, 5), new Note(Ab, 4), new Note(C, 4), new Note(F, 2), 
                F_MIN_ROOT
                );
        Chord gMajor = new Chord(
                new Note(D, 5), new Note(G, 4), new Note(B, 3), new Note(G, 2), 
                G_MAJ_ROOT
                );
        Set<ContextTag> prevContext = new HashSet<>();
        prevContext.add(ContextTag.CADENTIAL_PREDOMINANT);
        Set<ContextTag> currContext = new HashSet<>();
        currContext.add(ContextTag.CADENTIAL_V);
        CadenceSmallMovement.scoreSmallMovementCadence(fMinor, gMajor, 
                prevContext, currContext, C_MINOR, score);
        assertEquals(PenaltyType.MOVE_BIG_CADENCE.value(), score.totalScore());
        printPenaltyTransition(fMinor, gMajor, C_MINOR, score);
    }

    /**********************************
     * Tests for II7Suspension *
     **********************************/
    
    @Test
    public void testII7SuspensionGood(){
        Score score = new Score();
        Chord dMin7 = new Chord(
                new Note(C, 5), new Note(D, 4), new Note(A, 3), new Note(F, 2), 
                D_MIN7_INV1
                );
        Chord c64 = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(G, 3), new Note(G, 2), 
                C_MAJ_INV2
                );
        Set<ContextTag> prevContext = new HashSet<>();
        prevContext.add(ContextTag.CADENTIAL_PREDOMINANT);
        Set<ContextTag> currContext = new HashSet<>();
        currContext.add(ContextTag.CADENTIAL_I64);
        II7Suspension.scoreII7Suspension(dMin7, c64, 
                prevContext, currContext, C_MAJOR, score);
        assertEquals(0, score.totalScore());
        printPenaltyTransition(dMin7, c64, C_MAJOR, score);
    }
    
    @Test
    public void testII7SuspensionBad(){
        Score score = new Score();
        Chord dMin7 = new Chord(
                new Note(D, 5), new Note(A, 4), new Note(C, 4), new Note(F, 2), 
                D_MIN7_INV1
                );
        Chord c64 = new Chord(
                new Note(C, 5), new Note(G, 4), new Note(E, 4), new Note(G, 2), 
                C_MAJ_INV2
                );
        Set<ContextTag> prevContext = new HashSet<>();
        prevContext.add(ContextTag.CADENTIAL_PREDOMINANT);
        Set<ContextTag> currContext = new HashSet<>();
        currContext.add(ContextTag.CADENTIAL_I64);
        II7Suspension.scoreII7Suspension(dMin7, c64, 
                prevContext, currContext, C_MAJOR, score);
        assertEquals(PenaltyType.CADENTIAL_II7_SUSPEND.value(), score.totalScore());
        printPenaltyTransition(dMin7, c64, C_MAJOR, score);
    }
}
