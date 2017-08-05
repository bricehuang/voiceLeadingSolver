package scorer_transitions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chord_data.ChordWithContext;
import chords.Chord;
import chords.ChordType;
import music.BasicInterval;
import music.Interval;
import score_data.TransitionPenaltyType;
import score_data.TransitionScoreNew;
import solver.SolverUtils;

public class Dom7ResolutionScorer implements TransitionScorer {
    
    private static final ChordType MAJOR = ChordType.MAJ;
    private static final ChordType MINOR = ChordType.MIN;
    private static final ChordType DOM_SEVEN = ChordType.DOM7;

    private static final BasicInterval P1 = new BasicInterval(0, 0);
    private static final BasicInterval m2 = new BasicInterval(1, 1);
    private static final BasicInterval M2 = new BasicInterval(1, 2);
    private static final BasicInterval P4 = new BasicInterval(3, 5);
    private static final BasicInterval P5 = new BasicInterval(4, 7);
    
    private static final Interval UNIS = new Interval(P1, 0, true);
    private static final Interval UP_M2 = new Interval(M2, 0, true); 
    private static final Interval UP_m2 = new Interval(m2, 0, true); 
    private static final Interval DN_m2 = new Interval(m2, 0, false); 
    private static final Interval DN_M2 = new Interval(M2, 0, false); 
    private static final Interval UP_P4 = new Interval(P4, 0, true); 
    private static final Interval DN_P5 = new Interval(P5, 0, false);     
    
    private static final Map<Integer, Map<ChordType, Set<List<Interval>>>> DOM_RESOLUTIONS;
    static{
        Map<Integer, Map<ChordType, Set<List<Interval>>>> domResolutionsTmp = new HashMap<>();
        
        Map<ChordType, Set<List<Interval>>> rootResolutions = new HashMap<>();
        Set<List<Interval>> rootMajorResolutions = new HashSet<>(Arrays.asList(
            Arrays.asList(UP_P4, UP_m2, DN_M2, DN_m2),
            Arrays.asList(DN_P5, UP_m2, DN_M2, DN_m2)
        )); 
        Set<List<Interval>> rootMinorResolutions = new HashSet<>(Arrays.asList(
            Arrays.asList(UP_P4, UP_m2, DN_M2, DN_M2),
            Arrays.asList(DN_P5, UP_m2, DN_M2, DN_M2)                
        )); 
        rootResolutions.put(MAJOR, rootMajorResolutions);
        rootResolutions.put(MINOR, rootMinorResolutions);
        
        Map<ChordType, Set<List<Interval>>> inv1Resolutions = new HashMap<>();
        Set<List<Interval>> inv1MajorResolutions = new HashSet<>(Arrays.asList(
            Arrays.asList(UNIS, UP_m2, DN_M2, DN_m2)
        )); 
        Set<List<Interval>> inv1MinorResolutions = new HashSet<>(Arrays.asList(
            Arrays.asList(UNIS, UP_m2, DN_M2, DN_M2)
        )); 
        inv1Resolutions.put(MAJOR, inv1MajorResolutions);
        inv1Resolutions.put(MINOR, inv1MinorResolutions);
        
        Map<ChordType, Set<List<Interval>>> inv2Resolutions = new HashMap<>();
        Set<List<Interval>> inv2MajorResolutions = new HashSet<>(Arrays.asList(
            Arrays.asList(UNIS, UP_m2, DN_M2, DN_m2),
            Arrays.asList(UNIS, UP_m2, UP_M2, DN_m2)
        )); 
        Set<List<Interval>> inv2MinorResolutions = new HashSet<>(Arrays.asList(
            Arrays.asList(UNIS, UP_m2, DN_M2, DN_M2),
            Arrays.asList(UNIS, UP_m2, UP_m2, DN_M2)
        )); 
        inv2Resolutions.put(MAJOR, inv2MajorResolutions);
        inv2Resolutions.put(MINOR, inv2MinorResolutions);

        Map<ChordType, Set<List<Interval>>> inv3Resolutions = new HashMap<>();
        Set<List<Interval>> inv3MajorResolutions = new HashSet<>(Arrays.asList(
            Arrays.asList(UNIS, UP_m2, UP_M2, DN_m2)
        )); 
        Set<List<Interval>> inv3MinorResolutions = new HashSet<>(Arrays.asList(
            Arrays.asList(UNIS, UP_m2, UP_m2, DN_M2)
        )); 
        inv3Resolutions.put(MAJOR, inv3MajorResolutions);
        inv3Resolutions.put(MINOR, inv3MinorResolutions);

        domResolutionsTmp.put(0, rootResolutions);
        domResolutionsTmp.put(1, inv1Resolutions);
        domResolutionsTmp.put(2, inv2Resolutions);
        domResolutionsTmp.put(3, inv3Resolutions);
        
        DOM_RESOLUTIONS = Collections.unmodifiableMap(domResolutionsTmp);
    }
    
    private static Set<List<Interval>> getValidResolutions (
        Integer inversion, ChordType nextChordType
    ) {
        return DOM_RESOLUTIONS.get(inversion).get(nextChordType);
    }
    
    @Override
    public TransitionScoreNew scoreTransition(ChordWithContext previous, ChordWithContext current) {
        TransitionScoreNew score = new TransitionScoreNew();
        if (previous.getChord().getType() != DOM_SEVEN){
            return score;
        }
        Chord previousChord = previous.getChord();
        Chord currentChord = current.getChord();
        List<Interval> resolution = SolverUtils.computeVoiceMovementsByPosition(
            previousChord, currentChord
        );
        Set<List<Interval>> validResolutions = getValidResolutions(
            previousChord.getPrimitiveChord().getInversion(), currentChord.getType()
        );
        if (! validResolutions.contains(resolution)) {
            score.addPenalty(TransitionPenaltyType.DOM_SEVEN_RES);
        }
        return score;
    }

}
