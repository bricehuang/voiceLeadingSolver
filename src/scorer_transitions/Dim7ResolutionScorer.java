package scorer_transitions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chord_data.ChordWithContext;
import chords.Chord;
import chords.ChordType;
import music.BasicInterval;
import music.Interval;
import score_data.TransitionPenaltyType;
import score_data.TransitionScore;
import solver.SolverUtils;

public class Dim7ResolutionScorer implements TransitionScorer {
    private static final ChordType MAJOR = ChordType.MAJ;
    private static final ChordType MINOR = ChordType.MIN;
    private static final ChordType DIM_SEVEN = ChordType.DIM7;

    private static final BasicInterval m2 = new BasicInterval(1, 1);
    private static final BasicInterval M2 = new BasicInterval(1, 2);
    
    private static final Interval UP_M2 = new Interval(M2, 0, true); 
    private static final Interval UP_m2 = new Interval(m2, 0, true); 
    private static final Interval DN_m2 = new Interval(m2, 0, false); 
    private static final Interval DN_M2 = new Interval(M2, 0, false); 
    
    private static final Map<ChordType, List<Interval>> DIM_RESOLUTIONS;
    static{
        Map<ChordType, List<Interval>> dimResolutionsTmp = new HashMap<>();
        List<Interval> majorResolution = Arrays.asList(UP_m2, UP_M2, DN_m2, DN_m2);
        List<Interval> minorResolution = Arrays.asList(UP_m2, UP_m2, DN_M2, DN_m2);        
        dimResolutionsTmp.put(MAJOR, majorResolution);
        dimResolutionsTmp.put(MINOR, minorResolution);
        
        DIM_RESOLUTIONS = Collections.unmodifiableMap(dimResolutionsTmp);
    }
    
    private static List<Interval> getValidResolution (ChordType nextChordType) {
        return DIM_RESOLUTIONS.get(nextChordType);
    }
    
    @Override
    public TransitionScore scoreTransition(ChordWithContext previous, ChordWithContext current) {
        TransitionScore score = new TransitionScore();
        if (previous.getChord().getType() != DIM_SEVEN) {
            return score;
        }
        
        Chord previousChord = previous.getChord();
        Chord currentChord = current.getChord();
        List<Interval> resolution = SolverUtils.computeVoiceMovementsByPosition(
            previousChord, currentChord
        );
        List<Interval> validResolution = getValidResolution(currentChord.getType());
        if (! resolution.equals(validResolution)) {
            score.addPenalty(TransitionPenaltyType.DIM_SEVEN_RES);
        }
        return score;
    }

}
