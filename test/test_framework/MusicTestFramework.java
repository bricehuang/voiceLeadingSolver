package test_framework;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import chord_data.ContextTag;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;
import music.Note;

public class MusicTestFramework {
    
    // BasicNote 
    public static final BasicNote C = new BasicNote(0,0);
    public static final BasicNote Cs = new BasicNote(0,1);
    public static final BasicNote Db = new BasicNote(1,1);
    public static final BasicNote D = new BasicNote(1,2);
    public static final BasicNote E = new BasicNote(2,4);
    public static final BasicNote Eb = new BasicNote(2,3);
    public static final BasicNote F = new BasicNote(3,5);
    public static final BasicNote G = new BasicNote(4,7);
    public static final BasicNote Ab = new BasicNote(5,8);
    public static final BasicNote A = new BasicNote(5,9);
    public static final BasicNote Bb = new BasicNote(6,10);
    public static final BasicNote B = new BasicNote(6,11);
    
    // Note
    public static final Note C5 = new Note(C, 5); 
    public static final Note B4 = new Note(B, 4); 
    public static final Note E4 = new Note(E, 4); 
    public static final Note D4 = new Note(D, 4); 
    public static final Note C4 = new Note(C, 4);  
    public static final Note G3 = new Note(G, 3); 
    public static final Note F3 = new Note(F, 3);
    public static final Note E3 = new Note(E, 3);
    public static final Note Eb3 = new Note(Eb, 3);
    public static final Note C3 = new Note(C, 3);  
    public static final Note G2 = new Note(G, 2);   

    
    // Key
    public static final Key C_MAJOR = new Key(C, true);
    public static final Key C_MINOR = new Key(C, false);
    public static final Key G_MAJOR = new Key(G, true);
    
    // ChordType
    public static final ChordType MAJ = ChordType.MAJ;
    public static final ChordType MIN = ChordType.MIN;
    public static final ChordType DOM7 = ChordType.DOM7;
    public static final ChordType MAJ7 = ChordType.MAJ7;
    public static final ChordType MIN7 = ChordType.MIN7;
    public static final ChordType DIM7 = ChordType.DIM7;
    
    // PrimitiveChord
    public static final PrimitiveChord C_MAJ_ROOT 
        = new PrimitiveChord(C, MAJ, 0);
    public static final PrimitiveChord C_MAJ_64
        = new PrimitiveChord(C, MAJ, 2);
    public static final PrimitiveChord C_MIN_ROOT
        = new PrimitiveChord(C, MIN, 0);
    public static final PrimitiveChord Cs_DIM7_ROOT
        = new PrimitiveChord(Cs, DIM7, 0);
    public static final PrimitiveChord Db_MAJ7_ROOT 
        = new PrimitiveChord(Db, MAJ7, 0);
    public static final PrimitiveChord Eb_DOM7_42
        = new PrimitiveChord(Eb, DOM7, 3);
    public static final PrimitiveChord E_MIN_ROOT
        = new PrimitiveChord(E, MIN, 0);
    public static final PrimitiveChord G_MAJ_ROOT  
        = new PrimitiveChord(G, MAJ, 0);
    public static final PrimitiveChord G_MIN_64  
        = new PrimitiveChord(G, MIN, 2);
    public static final PrimitiveChord G_DOM7_ROOT  
        = new PrimitiveChord(G, DOM7, 0);
    public static final PrimitiveChord A_MIN7_ROOT  
        = new PrimitiveChord(A, MIN7, 0);
    
    // ContextTag sets
    public static final Set<ContextTag> CADENCE 
        = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(ContextTag.CADENCE)));
    public static final Set<ContextTag> NO_CONTEXTS 
        = Collections.unmodifiableSet(new HashSet<>());

}
