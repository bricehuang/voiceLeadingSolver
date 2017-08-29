package test_framework;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import chord_data.ChordWithContext;
import chord_data.ContextTag;
import chords.Chord;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicInterval;
import music.BasicNote;
import music.Interval;
import music.Key;
import music.Note;
import score_data.ChordPenaltyType;
import score_data.TransitionPenaltyType;
import scorer_chords.ChordScorer;
import scorer_transitions.TransitionScorer;

public class MusicTestFramework {

    // BasicNote
    public static final BasicNote Cbb = new BasicNote(0, -2);
    public static final BasicNote Cb = new BasicNote(0, -1);
    public static final BasicNote C = new BasicNote(0, 0);
    public static final BasicNote Cs = new BasicNote(0, 1);
    public static final BasicNote Css = new BasicNote(0, 2);
    public static final BasicNote Dbb = new BasicNote(1, 0);
    public static final BasicNote Db = new BasicNote(1, 1);
    public static final BasicNote D = new BasicNote(1, 2);
    public static final BasicNote Ebb = new BasicNote(2, 2);
    public static final BasicNote Eb = new BasicNote(2, 3);
    public static final BasicNote E = new BasicNote(2, 4);
    public static final BasicNote Es = new BasicNote(2, 5);
    public static final BasicNote Fb = new BasicNote(3, 4);
    public static final BasicNote F = new BasicNote(3, 5);
    public static final BasicNote Fs = new BasicNote(3, 6);
    public static final BasicNote Fss = new BasicNote(3, 7);
    public static final BasicNote Gbb = new BasicNote(4, 5);
    public static final BasicNote Gb = new BasicNote(4, 6);
    public static final BasicNote G = new BasicNote(4, 7);
    public static final BasicNote Gs = new BasicNote(4, 8);
    public static final BasicNote Gss = new BasicNote(4, 9);
    public static final BasicNote Ab = new BasicNote(5, 8);
    public static final BasicNote A = new BasicNote(5, 9);
    public static final BasicNote Bbb = new BasicNote(6, 9);
    public static final BasicNote Bb = new BasicNote(6, 10);
    public static final BasicNote B = new BasicNote(6, 11);
    public static final BasicNote Bs = new BasicNote(6, 12);
    public static final BasicNote Bss = new BasicNote(6, 13);

    // Note
    public static final Note G5 = new Note(G, 5);
    public static final Note F5 = new Note(F, 5);
    public static final Note E5 = new Note(E, 5);
    public static final Note Eb5 = new Note(Eb, 5);
    public static final Note D5 = new Note(D, 5);
    public static final Note Db5 = new Note(Db, 5);
    public static final Note Cs5 = new Note(Cs, 5);
    public static final Note C5 = new Note(C, 5);
    public static final Note B4 = new Note(B, 4);
    public static final Note Bb4 = new Note(Bb, 4);
    public static final Note Bbb4 = new Note(Bbb, 4);
    public static final Note A4 = new Note(A, 4);
    public static final Note Ab4 = new Note(Ab, 4);
    public static final Note Gs4 = new Note(Gs, 4);
    public static final Note G4 = new Note(G, 4);
    public static final Note Fs4 = new Note(Fs, 4);
    public static final Note F4 = new Note(F, 4);
    public static final Note Fb4 = new Note(Fb, 4);
    public static final Note E4 = new Note(E, 4);
    public static final Note Eb4 = new Note(Eb, 4);
    public static final Note D4 = new Note(D, 4);
    public static final Note Cs4 = new Note(Cs, 4);
    public static final Note C4 = new Note(C, 4);
    public static final Note Cb4 = new Note(Cb, 4);
    public static final Note Cbb4 = new Note(Cbb, 4);
    public static final Note Bss3 = new Note(Bss, 3);
    public static final Note Bs3 = new Note(Bs, 3);
    public static final Note B3 = new Note(B, 3);
    public static final Note Bb3 = new Note(Bb, 3);
    public static final Note A3 = new Note(A, 3);
    public static final Note Ab3 = new Note(Ab, 3);
    public static final Note Gs3 = new Note(Gs, 3);
    public static final Note G3 = new Note(G, 3);
    public static final Note Fss3 = new Note(Fss, 3);
    public static final Note Fs3 = new Note(Fs, 3);
    public static final Note F3 = new Note(F, 3);
    public static final Note E3 = new Note(E, 3);
    public static final Note Eb3 = new Note(Eb, 3);
    public static final Note D3 = new Note(D, 3);
    public static final Note Cs3 = new Note(Cs, 3);
    public static final Note C3 = new Note(C, 3);
    public static final Note B2 = new Note(B, 2);
    public static final Note C2 = new Note(C, 2);
    public static final Note A2 = new Note(A, 2);
    public static final Note Ab2 = new Note(Ab, 2);
    public static final Note G2 = new Note(G, 2);
    public static final Note F2 = new Note(F, 2);
    public static final Note A1 = new Note(A, 1);

    // Key
    public static final Key C_MAJOR = new Key(C, true);
    public static final Key C_MINOR = new Key(C, false);
    public static final Key Db_MAJOR = new Key(Db, true);
    public static final Key D_MAJOR = new Key(D, true);
    public static final Key D_MINOR = new Key(D, false);
    public static final Key E_MAJOR = new Key(E, true);
    public static final Key E_MINOR = new Key(E, false);
    public static final Key F_MAJOR = new Key(F, true);
    public static final Key Fs_MINOR = new Key(Fs, false);
    public static final Key G_MAJOR = new Key(G, true);
    public static final Key G_MINOR = new Key(G, false);
    public static final Key A_MAJOR = new Key(A, true);
    public static final Key A_MINOR = new Key(A, false);
    public static final Key Bb_MAJOR = new Key(Bb, true);

    // BasicInterval
    public static final BasicInterval PFT_UNISON = new BasicInterval(0, 0);
    public static final BasicInterval MIN_2ND = new BasicInterval(1, 1);
    public static final BasicInterval MAJ_2ND = new BasicInterval(1, 2);
    public static final BasicInterval PFT_4TH = new BasicInterval(3, 5);
    public static final BasicInterval DIMDIM_5TH = new BasicInterval(4, 5);
    public static final BasicInterval PFT_5TH = new BasicInterval(4, 7);
    public static final BasicInterval DIM_6TH = new BasicInterval(5, 7);

    // Interval
    public static final Interval UP_MIN_2ND = new Interval(MIN_2ND, 0, true);
    public static final Interval DN_MIN_2ND = new Interval(MIN_2ND, 0, false);
    public static final Interval UP_MAJ_2ND = new Interval(MAJ_2ND, 0, true);
    public static final Interval DN_MAJ_2ND = new Interval(MAJ_2ND, 0, false);
    public static final Interval UP_PFT_5TH = new Interval(PFT_5TH, 0, true);
    public static final Interval DN_PFT_5TH = new Interval(PFT_5TH, 0, false);

    // ChordType
    public static final ChordType MAJ = ChordType.MAJ;
    public static final ChordType MIN = ChordType.MIN;
    public static final ChordType DIM = ChordType.DIM;
    public static final ChordType DOM7 = ChordType.DOM7;
    public static final ChordType MAJ7 = ChordType.MAJ7;
    public static final ChordType MIN7 = ChordType.MIN7;
    public static final ChordType DIM7 = ChordType.DIM7;
    public static final ChordType HDIM7 = ChordType.HDIM7;

    // PrimitiveChord
    public static final PrimitiveChord C_MAJ_ROOT = new PrimitiveChord(C, MAJ, 0);
    public static final PrimitiveChord C_MAJ_6 = new PrimitiveChord(C, MAJ, 1);
    public static final PrimitiveChord C_MAJ_64 = new PrimitiveChord(C, MAJ, 2);
    public static final PrimitiveChord C_MIN_ROOT = new PrimitiveChord(C, MIN, 0);
    public static final PrimitiveChord C_MIN_6 = new PrimitiveChord(C, MIN, 1);
    public static final PrimitiveChord C_MIN_64 = new PrimitiveChord(C, MIN, 2);
    public static final PrimitiveChord C_MAJ7_ROOT = new PrimitiveChord(C, MAJ7, 0);
    public static final PrimitiveChord C_MIN7_ROOT = new PrimitiveChord(C, MIN7, 0);
    public static final PrimitiveChord C_DOM7_ROOT = new PrimitiveChord(C, DOM7, 0);
    public static final PrimitiveChord C_MIN7_65 = new PrimitiveChord(C, MIN7, 0);
    public static final PrimitiveChord Cs_DIM7_ROOT = new PrimitiveChord(Cs, DIM7, 0);
    public static final PrimitiveChord Db_MAJ_6 = new PrimitiveChord(Db, MAJ, 1);
    public static final PrimitiveChord Db_MAJ7_ROOT = new PrimitiveChord(Db, MAJ7, 0);
    public static final PrimitiveChord D_MAJ_ROOT = new PrimitiveChord(D, MAJ, 0);
    public static final PrimitiveChord D_MAJ_6 = new PrimitiveChord(D, MAJ, 1);
    public static final PrimitiveChord D_MIN_ROOT = new PrimitiveChord(D, MIN, 0);
    public static final PrimitiveChord D_MIN_6 = new PrimitiveChord(D, MIN, 1);
    public static final PrimitiveChord D_DIM_6 = new PrimitiveChord(D, DIM, 1);
    public static final PrimitiveChord D_DOM7_ROOT = new PrimitiveChord(D, DOM7, 0);
    public static final PrimitiveChord D_MIN7_65 = new PrimitiveChord(D, MIN7, 1);
    public static final PrimitiveChord D_HDIM7_ROOT = new PrimitiveChord(D, HDIM7, 0);
    public static final PrimitiveChord D_HDIM7_65 = new PrimitiveChord(D, HDIM7, 1);
    public static final PrimitiveChord Eb_DOM7_42 = new PrimitiveChord(Eb, DOM7, 3);
    public static final PrimitiveChord E_MAJ_ROOT = new PrimitiveChord(E, MAJ, 0);
    public static final PrimitiveChord E_MIN_ROOT = new PrimitiveChord(E, MIN, 0);
    public static final PrimitiveChord E_DIM_ROOT = new PrimitiveChord(E, DIM, 0);
    public static final PrimitiveChord E_DOM7_ROOT = new PrimitiveChord(E, DOM7, 0);
    public static final PrimitiveChord E_DOM7_65 = new PrimitiveChord(E, DOM7, 1);
    public static final PrimitiveChord E_DOM7_43 = new PrimitiveChord(E, DOM7, 2);
    public static final PrimitiveChord E_DOM7_42 = new PrimitiveChord(E, DOM7, 3);
    public static final PrimitiveChord F_MAJ_ROOT = new PrimitiveChord(F, MAJ, 0);
    public static final PrimitiveChord F_MAJ_6 = new PrimitiveChord(F, MAJ, 1);
    public static final PrimitiveChord F_MIN_ROOT = new PrimitiveChord(F, MIN, 0);
    public static final PrimitiveChord Fs_DIM_ROOT = new PrimitiveChord(Fs, DIM, 0);
    public static final PrimitiveChord Fs_DIM_6 = new PrimitiveChord(Fs, DIM, 1);
    public static final PrimitiveChord Fs_DIM7_65 = new PrimitiveChord(Fs, DIM7, 1);
    public static final PrimitiveChord Fs_HDIM7_65 = new PrimitiveChord(Fs, HDIM7, 1);
    public static final PrimitiveChord G_MAJ_ROOT = new PrimitiveChord(G, MAJ, 0);
    public static final PrimitiveChord G_MAJ_6 = new PrimitiveChord(G, MAJ, 1);
    public static final PrimitiveChord G_MAJ_64 = new PrimitiveChord(G, MAJ, 2);
    public static final PrimitiveChord G_MIN_64 = new PrimitiveChord(G, MIN, 2);
    public static final PrimitiveChord G_DOM7_ROOT = new PrimitiveChord(G, DOM7, 0);
    public static final PrimitiveChord G_DOM7_63 = new PrimitiveChord(G, DOM7, 1);
    public static final PrimitiveChord G_DOM7_43 = new PrimitiveChord(G, DOM7, 2);
    public static final PrimitiveChord Gs_DIM_6 = new PrimitiveChord(Gs, DIM, 1);
    public static final PrimitiveChord Ab_MAJ_ROOT = new PrimitiveChord(Ab, MAJ, 0);
    public static final PrimitiveChord Ab_MAJ_6 = new PrimitiveChord(Ab, MAJ, 1);
    public static final PrimitiveChord Ab_DOM7_ROOT = new PrimitiveChord(Ab, DOM7, 0);
    public static final PrimitiveChord A_MAJ_ROOT = new PrimitiveChord(A, MAJ, 0);
    public static final PrimitiveChord A_MAJ_6 = new PrimitiveChord(A, MAJ, 1);
    public static final PrimitiveChord A_MIN_ROOT = new PrimitiveChord(A, MIN, 0);
    public static final PrimitiveChord A_MIN_6 = new PrimitiveChord(A, MIN, 1);
    public static final PrimitiveChord A_MIN7_ROOT = new PrimitiveChord(A, MIN7, 0);
    public static final PrimitiveChord Bb_MAJ_ROOT = new PrimitiveChord(Bb, MAJ, 0);
    public static final PrimitiveChord Bb_MAJ_6 = new PrimitiveChord(Bb, MAJ, 1);
    public static final PrimitiveChord B_MAJ_ROOT = new PrimitiveChord(B, MAJ, 0);
    public static final PrimitiveChord B_DIM_ROOT = new PrimitiveChord(B, DIM, 0);
    public static final PrimitiveChord B_DIM7_ROOT = new PrimitiveChord(B, DIM7, 0);
    public static final PrimitiveChord B_DIM7_65 = new PrimitiveChord(B, DIM7, 1);
    public static final PrimitiveChord B_DIM7_43 = new PrimitiveChord(B, DIM7, 2);
    public static final PrimitiveChord B_DIM7_42 = new PrimitiveChord(B, DIM7, 3);

    // ContextTag sets
    public static final Set<ContextTag> APPLIED_DOMINANT = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(ContextTag.APPLIED_DOMINANT)));
    public static final Set<ContextTag> CADENCE = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(ContextTag.CADENCE)));
    public static final Set<ContextTag> CADENTIAL_I64 = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_I64)));
    public static final Set<ContextTag> CADENTIAL_V = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_V)));
    public static final Set<ContextTag> CADENTIAL_PREDOMINANT = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_PREDOMINANT)));
    public static final Set<ContextTag> NEAPOLITAN_PREDOMINANT = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(
                    ContextTag.CADENTIAL_PREDOMINANT, ContextTag.NEAPOLITAN
            )));
    public static final Set<ContextTag> NO_CONTEXTS = Collections.unmodifiableSet(new HashSet<>());

    // ChordPenalty
    public static final ChordPenaltyType BAD_TRIPLING = ChordPenaltyType.BAD_TRIPLING;
    public static final ChordPenaltyType CADENCE_DOUBLING = ChordPenaltyType.CADENCE_DOUBLING;
    public static final ChordPenaltyType DOUBLE_DOUBLING = ChordPenaltyType.DOUBLE_DOUBLING;
    public static final ChordPenaltyType DOUBLED_LEADING_TONE = ChordPenaltyType.DOUBLED_LEADING_TONE;
    public static final ChordPenaltyType BAD_DIM_DOUBLING = ChordPenaltyType.BAD_DIM_DOUBLING;
    public static final ChordPenaltyType BAD_DOUBLING_OK_BECAUSE_DIM = ChordPenaltyType.BAD_DOUBLING_OK_BECAUSE_DIM;
    public static final ChordPenaltyType VOICE_OVERLAP = ChordPenaltyType.VOICE_OVERLAP;

    // TransitionPenalty
    public static final TransitionPenaltyType BAD_NEAPOLITAN_RES = TransitionPenaltyType.BAD_NEAPOLITAN_RES;
    public static final TransitionPenaltyType BAD_TRITONE_RESOLUTION = TransitionPenaltyType.BAD_TRITONE_RESOLUTION;
    public static final TransitionPenaltyType CADENTIAL_II7_SUSPEND = TransitionPenaltyType.CADENTIAL_II7_SUSPEND;
    public static final TransitionPenaltyType DIM_SEVEN_RES = TransitionPenaltyType.DIM_SEVEN_RES;
    public static final TransitionPenaltyType DIRECT = TransitionPenaltyType.DIRECT;
    public static final TransitionPenaltyType DOM_SEVEN_RES = TransitionPenaltyType.DOM_SEVEN_RES;
    public static final TransitionPenaltyType MELODIC_INTERVAL = TransitionPenaltyType.MELODIC_INTERVAL;
    public static final TransitionPenaltyType MOVE_FOURTH = TransitionPenaltyType.MOVE_FOURTH;
    public static final TransitionPenaltyType MOVE_FIFTH = TransitionPenaltyType.MOVE_FIFTH;
    public static final TransitionPenaltyType MOVE_BIG = TransitionPenaltyType.MOVE_BIG;
    public static final TransitionPenaltyType MOVE_BIG_BASS = TransitionPenaltyType.MOVE_BIG_BASS;
    public static final TransitionPenaltyType MOVE_BIG_CADENCE = TransitionPenaltyType.MOVE_BIG_CADENCE;
    public static final TransitionPenaltyType NEAPOLITAN_BIG_MOVE_OK = TransitionPenaltyType.NEAPOLITAN_BIG_MOVE_OK;
    public static final TransitionPenaltyType NEAPOLITAN_DIM_3RD_OK = TransitionPenaltyType.NEAPOLITAN_DIM_3RD_OK;
    public static final TransitionPenaltyType PARALLEL = TransitionPenaltyType.PARALLEL;
    public static final TransitionPenaltyType VOICE_CROSSING = TransitionPenaltyType.VOICE_CROSSING;
    
    // Util methods
    public static ChordWithContext makeChordWithContext(Note soprano, Note alto, Note tenor, Note bass,
            PrimitiveChord primitiveChord, Key key, Set<ContextTag> contexts) {
        Chord chord = new Chord(soprano, alto, tenor, bass, primitiveChord);
        return new ChordWithContext(chord, key, contexts);
    }

    public static Map<ChordPenaltyType, Integer> computeChordPenalties(ChordScorer scorer, ChordWithContext chord) {
        return scorer.scoreChord(chord).getPenaltyCount();
    }

    public static Map<TransitionPenaltyType, Integer> computeTransitionPenalties(TransitionScorer scorer,
            ChordWithContext previous, ChordWithContext current) {
        return scorer.scoreTransition(previous, current).getPenaltyCount();
    }

}
