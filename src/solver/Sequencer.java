package solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import chord_data.ChordProgressionWithContext;
import chord_data.ChordWithContext;
import score_data.ChordScore;
import score_data.TransitionScore;
import scorer_chords.ChordScorer;
import scorer_transitions.TransitionScorer;

public class Sequencer {
    
    /**
     * Sequences the first chord
     * @param chordSet ways to sing the first chord
     * @param scorer scorer in the desired key
     * @return a BestList of ways to sing the first chord, with scores
     */
    private static BestProgressionsByLast sequenceFirst(
        Set<ChordWithContext> chordsAndContexts
    ){
        BestProgressionsByLast best = new BestProgressionsByLast();
        for (ChordWithContext chordAndContext : chordsAndContexts){
            ChordProgressionWithContext progression = 
                ChordProgressionWithContext.empty().append(chordAndContext);
            ChordScore chordScore = ChordScorer.score(chordAndContext);
            best.addProgression(
                new ScoredProgression(progression, chordScore.totalScore())
            );
        }
        return best;
    }
    
    /**
     * Sequences the second through last chords
     * @param previousBest BestList of ways to sing the chords up to, but 
     * excluding, this one, with scores
     * @param chordSet ways to sing this chord
     * @param scorer scorer in the desired key
     * @return a BestList of ways to sing the chords up to, and including,
     * this one, with scores
     */
    private static BestProgressionsByLast sequenceRest(
        BestProgressionsByLast previousBest, Set<ChordWithContext> chordsAndContexts
    ){
        BestProgressionsByLast best = new BestProgressionsByLast();
        for (ChordWithContext currentChord : chordsAndContexts){
            ChordScore chordScore = ChordScorer.score(currentChord);
            
            for (ChordWithContext previousChord : previousBest.getEndingChords()){
                TransitionScore transitionScore = TransitionScorer.score(
                    previousChord, currentChord
                );
                
                for (ScoredProgression scoredProgression : previousBest.getProgressions(previousChord)){
                    int totalScore = scoredProgression.getScore() 
                                   + chordScore.totalScore() 
                                   + transitionScore.totalScore();
                    
                    ChordProgressionWithContext prevProgression = scoredProgression.getChordProg();
                    ChordProgressionWithContext concatenated = prevProgression.append(currentChord);
                    ScoredProgression concatenatedWithScore = new ScoredProgression(
                        concatenated, totalScore
                    );
                    
                    best.addProgression(concatenatedWithScore);
                }
            }
        }
        return best;
    }
    
    /**
     * Finds the best chords from a BestList
     * @param bestList a BestList
     * @return a SortedFiniteProgList of the best chords from bestList
     */
    private static List<ScoredProgression> getBestProgressions(BestProgressionsByLast bestByLast){
        BestProgressionList bestProgressions = new BestProgressionList();
        for (ChordWithContext lastChord : bestByLast.getEndingChords()){
            for (ScoredProgression progression : bestByLast.getProgressions(lastChord)){
                bestProgressions.addProgression(progression);
            }
        }
        return bestProgressions.getProgressions();
    }
    
    /**
     * Computes the best chord progressions
     * @param waysToSingChords a list of ways to sing chords. The ith item
     * is a set of all the ways to sing the ith chord.    
     * @param keys a list of keys, such that the ith item is the key
     * in which the ith chord should be considered.  We say that 
     * pivot chords are considered in the old key.  
     * @return The best way to sing this chord progression
     */
    public static List<ChordProgressionWithContext> findBestChordProgressions(
            List<Set<ChordWithContext>> possibleChordVoicings
    ){
        assert(possibleChordVoicings.size() > 0);

        BestProgressionsByLast currentBest = sequenceFirst(possibleChordVoicings.get(0));
        for (int i=1; i<possibleChordVoicings.size(); i++){
            currentBest = sequenceRest(currentBest, possibleChordVoicings.get(i));
        }
        
        // TODO this is more cleanly implemented with mapreduce
        List<ChordProgressionWithContext> progressions = new ArrayList<>();
        for (ScoredProgression scoredProgression : getBestProgressions(currentBest)) {
            progressions.add(scoredProgression.getChordProg());
        }
        return progressions;
    }
    
    
    /*********************
     * PENALTY REPORTING *
     *********************/
    
    private static final String DIVIDE = "--------------------------------------------------\n";
    
    private static String chordPenaltyReport(ChordWithContext chord) {
        return "Scoring Chord " + chord.toString() + ":\n"
               + ChordScorer.score(chord).toString() + "\n" 
               + DIVIDE;        
    }

    private static String transitionPenaltyReport(
        ChordWithContext previous, ChordWithContext current
    ) {
        return "Scoring Transition " + previous.toString() + " --> " + current.toString() + ":\n"  
                + TransitionScorer.score(previous, current) + "\n"
                + DIVIDE;
    }
    
    /**
     * Gets a report of all penalties incurred by a progression
     * @param progression a progression
     * @return a String reporting all penalties this prorgression incurred
     */
    public static String getPenaltyReport(ChordProgressionWithContext progression) {
        assert (progression.length() > 0);
        
        if (progression.length()==1) {
            return chordPenaltyReport(progression.getLast());
        } else {
            ChordWithContext previous = progression.getStart().getLast();
            ChordWithContext current = progression.getLast();
            return getPenaltyReport(progression.getStart()) 
                + transitionPenaltyReport(previous, current) 
                + chordPenaltyReport(current);
        }
    }

}
