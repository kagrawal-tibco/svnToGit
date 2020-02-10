package com.tibco.be.migration.expimp.providers.csv;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 26, 2008
 * Time: 11:14:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScoreCardIndex {
    String scoreCardName;
    int scoreCardIndex;

    public ScoreCardIndex() {
    }

    public int getScoreCardIndex() {
        return scoreCardIndex;
    }

    public void setScoreCardIndex(int scoreCardIndex) {
        this.scoreCardIndex = scoreCardIndex;
    }

    public String getScoreCardName() {
        return scoreCardName;
    }

    public void setScoreCardName(String scoreCardName) {
        this.scoreCardName = scoreCardName;
    }
}
