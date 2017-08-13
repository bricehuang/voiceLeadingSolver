package scorer_transitions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chord_data.ChordWithContext;
import chord_data.ContextTag;
import chords.Chord;
import chords.ChordType;
import music.BasicInterval;
import music.BasicNote;
import music.Interval;
import music.Key;
import score_data.TransitionPenaltyType;
import score_data.TransitionScore;
import solver.SolverUtils;

public class NeapolitanResolutionScorer implements TransitionScorer {
    
    private static final BasicInterval MIN_2ND = new BasicInterval(1,1);
    private static final BasicInterval MAJ_2ND = new BasicInterval(1,2);
    private static final BasicInterval DIM_3RD = new BasicInterval(2,2);
    private static final BasicInterval MIN_3RD = new BasicInterval(2,3);
    private static final BasicInterval PFT_4TH = new BasicInterval(3,5);
    private static final BasicInterval MIN_6TH = new BasicInterval(5,8);

    private static final Interval UP_MIN_2ND = new Interval(MIN_2ND, 0, true);
    private static final Interval DN_MIN_2ND = new Interval(MIN_2ND, 0, false);
    private static final Interval DN_MAJ_2ND = new Interval(MAJ_2ND, 0, false);
    private static final Interval DN_DIM_3RD = new Interval(DIM_3RD, 0, false);
    private static final Interval DN_MIN_3RD = new Interval(MIN_3RD, 0, false);

    // correct non-Bass moves for N6 -> V 
    private static final Map<BasicInterval, Interval> MOVES_N6_TO_V;
    static{
        Map<BasicInterval, Interval> movesN6ToV = new HashMap<>();
        movesN6ToV.put(MIN_2ND, DN_DIM_3RD);
        movesN6ToV.put(PFT_4TH, DN_MIN_3RD);
        movesN6ToV.put(MIN_6TH, DN_MIN_2ND);
        MOVES_N6_TO_V = Collections.unmodifiableMap(movesN6ToV);
    }

    // correct non-Bass moves for N6 -> I64, i64 
    private static final Map<ChordType, Map<BasicInterval, Interval>> MOVES_N6_TO_I64;
    static{
        Map<ChordType, Map<BasicInterval, Interval>> tmpMoves = new HashMap<>();
        
        Map<BasicInterval, Interval> movesN6ToI64 = new HashMap<>();
        movesN6ToI64.put(MIN_2ND, DN_MIN_2ND);
        movesN6ToI64.put(PFT_4TH, DN_MIN_2ND);
        movesN6ToI64.put(MIN_6TH, DN_MIN_2ND);
        tmpMoves.put(ChordType.MAJ, movesN6ToI64);

        Map<BasicInterval, Interval> movesN6Toi64 = new HashMap<>();
        movesN6Toi64.put(MIN_2ND, DN_MIN_2ND);
        movesN6Toi64.put(PFT_4TH, DN_MAJ_2ND);
        movesN6Toi64.put(MIN_6TH, DN_MIN_2ND);
        tmpMoves.put(ChordType.MIN, movesN6Toi64);
        
        MOVES_N6_TO_I64 = Collections.unmodifiableMap(tmpMoves);
    }
    
    private static Map<BasicInterval, Interval> getExpectedMoves(
        Set<ContextTag> currentContextTags, ChordType currentType
    ) {
        if (currentContextTags.contains(ContextTag.CADENTIAL_V)) {
            assert (currentType == ChordType.MAJ || currentType == ChordType.DOM7);
            if (currentType == ChordType.MAJ) {
                return MOVES_N6_TO_V;
            } else {
                throw new RuntimeException("Unimplemented");
            }
        } else if (currentContextTags.contains(ContextTag.CADENTIAL_I64)) {
            assert (currentType == ChordType.MAJ || currentType == ChordType.MIN);
            return MOVES_N6_TO_I64.get(currentType);
        } else {
            throw new RuntimeException("Should not get here.");
        }
    }
    
    @Override
    public TransitionScore scoreTransition(ChordWithContext previous, ChordWithContext current) {
        TransitionScore score = new TransitionScore();
        
        // if previous isn't neapolitan, irrelevant
        if (!previous.getContextTags().contains(ContextTag.NEAPOLITAN)) {
            return score;
        }
        // sanity checks
        assert (previous.getKey().equals(current.getKey()));
        Key key = current.getKey();
        BasicNote tonic = key.getTonic();
        
        Chord prevChord = previous.getChord();
        assert (prevChord.getType() == ChordType.MAJ);
        assert (prevChord.getPrimitiveChord().getRoot()
                .equals(tonic.transpose(UP_MIN_2ND)));
        
        Set<ContextTag> currContexts = current.getContextTags();
        assert (currContexts.contains(ContextTag.CADENTIAL_V) || 
                currContexts.contains(ContextTag.CADENTIAL_I64));
        
        List<BasicNote> prevBasicNotes = SolverUtils.spellBasicNotes(previous.getChord());
        Set<BasicNote> prevNonBassNotes = new HashSet<>(Arrays.asList(
            prevBasicNotes.get(0), prevBasicNotes.get(1), prevBasicNotes.get(2)
        ));
        if (prevNonBassNotes.size() < 3) {
            score.addPenalty(TransitionPenaltyType.BAD_NEAPOLITAN_RES);
        } else {
            Chord currChord = current.getChord();
            Map<BasicInterval, Interval> expectedMoves = getExpectedMoves(
                currContexts, currChord.getType()
            );
            
            List<Interval> moves = SolverUtils.getVoiceMovements(prevChord, currChord);
            boolean allMovesValid = true;
            for (int i=0; i<3; i++) {
                // for all non-bass voices, check if move is valid
                BasicNote prevNote = prevBasicNotes.get(i);
                Interval move = moves.get(i);
                Interval expectedMove = expectedMoves.get(
                    BasicInterval.intervalBetween(tonic, prevNote)
                );
                allMovesValid &= (move.equals(expectedMove));
            }
            if (allMovesValid) {
                score.addPenalty(TransitionPenaltyType.NEAPOLITAN_AUG_2ND_OK);
            } else {
                score.addPenalty(TransitionPenaltyType.BAD_NEAPOLITAN_RES);
            }
        }
        return score;
    
    }

}
