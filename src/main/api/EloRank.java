package main.api;

/**
 * Literal of Elo rank name
 * @author Nicolas Mauger
 * @date 28 may 2017
 * https://github.com/maugern/EloApi
 * Released under the WTFPL license
 */
public enum EloRank {
    GrandMaster,
    Master,
    Expert,
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I,
    J;

    public static EloRank getRank(double eloPoints) {
        if (eloPoints < 200)
            return EloRank.J;
        else if (eloPoints < 400)
            return EloRank.I;
        else if (eloPoints < 600)
            return EloRank.H;
        else if (eloPoints < 800)
            return EloRank.G;
        else if (eloPoints < 1000)
            return EloRank.F;
        else if (eloPoints < 1200)
            return EloRank.E;
        else if (eloPoints < 1400)
            return EloRank.D;
        else if (eloPoints < 1600)
            return EloRank.C;
        else if (eloPoints < 1800)
            return EloRank.B;
        else if (eloPoints < 2000)
            return EloRank.A;
        else if (eloPoints < 2200)
            return EloRank.Expert;
        else if (eloPoints < 2400)
            return EloRank.Master;
        else
            return EloRank.GrandMaster;
    }
    
    
    
    
}
